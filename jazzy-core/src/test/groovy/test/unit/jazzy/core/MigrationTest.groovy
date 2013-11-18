/*
 * Copyright 2013 Gabor Lontay
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package test.unit.jazzy.core
import groovy.json.JsonException
import groovy.mock.interceptor.MockFor
import hu.lg.jazzy.core.*
import org.junit.Test
import test.common.JsonDocumentStub

import static test.common.JsonAssert.assertJsonStringsEqual

class MigrationTest {
    def scriptv1 = [getName: {"v1.jazzy"}, getContent: {"json.name = 'v1'"}] as MigrationScript
    def scriptv2 = [getName: {"v2.jazzy"}, getContent: {"json.newProperty = 'v2'"}] as MigrationScript
    def scriptv2WithError = [getName: {"v2.jazzy"}, getContent: {"invalidVar.newProperty = 'v2'"}] as MigrationScript
    def json = new JsonDocumentStub(originalContent: '{"name" : "A name"}')
    def scriptSourceMockContext = new MockFor(MigrationScriptSource)
    def loggerMockContext = new MockFor(JazzyLog)
    def versionStoreMockContext = new MockFor(VersionStore)
    def eventListenerMockContext = new MockFor(MigrationEventListener)
    def errorHandlerMockContext = new MockFor(ErrorHandler)
    def parsedJsonClosure = { json -> assert json.jsonDocument == this.json }
    def jsonWithVersionClosure = { json, version ->
        assert json.jsonDocument == this.json
        assert version.version.versionNumbers == [2]
    }
    def infoLogClosure = { aClass, msg -> assert aClass == Migration }


    @Test
    void executesMigrationScripts() {
        setupMocksForSimpleMigration()

        migration().executeOn json

        assertJsonStringsEqual json.after, '{ "name": "v1", "newProperty": "v2" }'
    }

    @Test
    void logsTheSteps() {
        setupMocksForSimpleMigration()
        loggerMockContext.demand.info(2 * 2 + 2, infoLogClosure)

        def configuarion = configurationWithMocks()
        //noinspection GroovyAssignabilityCheck
        configuarion.log = loggerMockContext.proxyInstance()

        Migration.from(configuarion).executeOn json

        loggerMockContext.verify configuarion.log
    }


    @Test
    void executesScriptsOnlyGreaterThanCurrentVersion() {
        scriptSourceMockContext.demand.getScripts { [scriptv1, scriptv2] }
        versionStoreMockContext.demand.getVersionFor { Version.of("1") }
        versionStoreMockContext.demand.storeNewVersionOf { a, b -> }

        migration().executeOn json

        assertJsonStringsEqual json.after, '{ "name": "A name", "newProperty": "v2" }'
    }

    @Test
    void storesCurrentVersion() {
        scriptSourceMockContext.demand.getScripts { [scriptv1, scriptv2] }
        versionStoreMockContext.demand.getVersionFor { Version.of("1") }
        versionStoreMockContext.demand.storeNewVersionOf { json, newVersion ->
            assert newVersion == Version.of("2")
            assert json.jsonDocument == this.json
        }

        def configuration = configurationWithMocks()
        def migration = Migration.from(configuration)

        migration.executeOn json

        //noinspection GroovyAssignabilityCheck
        versionStoreMockContext.verify configuration.versionStore
    }

    @Test
    void firesMigrationEvents() {
        scriptSourceMockContext.demand.getScripts { [scriptv1, scriptv2] }
        versionStoreMockContext.demand.getVersionFor { Version.of("1") }
        versionStoreMockContext.demand.storeNewVersionOf { a, b -> }

        eventListenerMockContext.demand.with {
            migrationStartedOn { json -> assert json == this.json }
            jsonDocumentParsed(parsedJsonClosure)
            shellCreatedFor(parsedJsonClosure)
            runningScriptOn(jsonWithVersionClosure)
            scriptFinishedOn(jsonWithVersionClosure)
            migrationFinishedOn(parsedJsonClosure)
        }


        migration().with {

            def eventListenerProxy = eventListenerMockContext.proxyInstance()

            //noinspection GroovyAssignabilityCheck
            addMigrationEventListener eventListenerProxy

            executeOn json

            eventListenerMockContext.verify eventListenerProxy
        }
    }

    @Test(expected = JsonException)
    void throwsExceptionIfInputJsonIsInvalid() {
        scriptSourceMockContext.demand.getScripts { [scriptv1, scriptv2] }
        migration().executeOn new JsonDocumentStub(originalContent: 'NonJsonString')
    }

    @Test
    void callsErrorHandlerIfScriptFails() {
        setupMocksForError()

        def configuration = configurationWithMocks()
        def migration = Migration.from(configuration)

        migration.executeOn json

        errorHandlerMockContext.verify configuration.errorHandler
    }

    @Test
    void logsScriptFailure() {
        setupMocksForError()
        loggerMockContext.demand.info(4, infoLogClosure)
        loggerMockContext.demand.warn { aClass, msg, e -> assert aClass == Migration }
        loggerMockContext.demand.info(1, infoLogClosure)

        def configuration = configurationWithMocks()
        //noinspection GroovyAssignabilityCheck
        configuration.log = loggerMockContext.proxyInstance()
        def migration = Migration.from(configuration)

        migration.executeOn json

        loggerMockContext.verify configuration.log
    }

    private void setupMocksForError() {
        scriptSourceMockContext.demand.getScripts { [scriptv1, scriptv2WithError] }
        versionStoreMockContext.demand.getVersionFor { Version.INITIAL_VERSION }
        versionStoreMockContext.demand.storeNewVersionOf(1) { a, b -> }
        errorHandlerMockContext.demand.handleScriptEvaluationError { a, b, c -> }
    }

    private void setupMocksForSimpleMigration() {
        scriptSourceMockContext.demand.getScripts { [scriptv1, scriptv2] }
        versionStoreMockContext.demand.getVersionFor { Version.INITIAL_VERSION }
        versionStoreMockContext.demand.storeNewVersionOf(2) { a, b -> }
    }


    private Migration migration() {
        Migration.from(configurationWithMocks())
    }

    private Configuration configurationWithMocks() {
        //noinspection GroovyAssignabilityCheck
        new Configuration(
                scriptSource: scriptSourceMockContext.proxyInstance(),
                versionStore: versionStoreMockContext.proxyInstance(),
                errorHandler: errorHandlerMockContext.proxyInstance()
        )
    }
}