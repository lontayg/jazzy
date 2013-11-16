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

package hu.lg.jazzy.core
import groovy.beans.ListenerList
import groovy.json.JsonException
import groovy.json.JsonSlurper
import groovy.transform.TupleConstructor

class Migration {
    private def slurper = new JsonSlurper()

    private List<VersionedScript> orderedScripts
    private VersionStore versionStore
    private ErrorHandler errorHandler
    private JazzyLog log

    @SuppressWarnings("GroovyUnusedDeclaration")
    @ListenerList
    private List<MigrationEventListener> listeners


    private Migration(List<VersionedScript> orderedScripts,
                      VersionStore versionStore,
                      ErrorHandler errorHandler,
                      JazzyLog log) {
        this.orderedScripts = orderedScripts
        this.versionStore = versionStore
        this.errorHandler = errorHandler
        this.log = log;
    }

    static Migration from(Configuration configuration) {
        def versionedScripts = []

        configuration.scriptSource.scripts.each {
            versionedScripts << VersionedScript.from(it)
        }

        versionedScripts.sort { a, b ->
            a.version <=> b.version
        }

        def migration = new Migration(versionedScripts,
                            configuration.versionStore,
                            configuration.errorHandler,
                            configuration.log)

        configuration.eventListeners.each {
            migration.addMigrationEventListener it
        }

        return migration
    }


    void executeOn(JsonDocument jsonDocument) {
        log.info Migration, "Migrating ${jsonDocument}"
        fireMigrationStartedOn jsonDocument

        ParsedJsonDocument parsedJsonDocument = parse jsonDocument

        def currentVersion = versionStore.getVersionFor parsedJsonDocument

        ScriptExecutor shell = createScriptExecutorFor parsedJsonDocument

        List<VersionedScript> scriptsToExecute = getScriptsWithVersionAfter currentVersion

        for (versionedScript in scriptsToExecute) {
            shell.run versionedScript
        }

        parsedJsonDocument.updateOriginalJson()

        fireMigrationFinishedOn parsedJsonDocument
        log.info Migration, "Migration finished on ${jsonDocument}"
    }

    private ParsedJsonDocument parse(JsonDocument jsonDocument) {
        try {

            def jsonObject = slurper.parseText jsonDocument.content

            ParsedJsonDocument parsedJsonDocument = new ParsedJsonDocument(
                    jsonDocument: jsonDocument,
                    parsedJson: jsonObject
            )

            fireJsonDocumentParsed parsedJsonDocument

            return parsedJsonDocument

        } catch (IllegalArgumentException | JsonException e) {
            //this error must be handled at an upper level
            log.warn Migration, "Could not parse json: ${jsonDocument}", e
            throw e
        }
    }

    private ScriptExecutor createScriptExecutorFor(ParsedJsonDocument json) {
        GroovyShell shell = new GroovyShell()
        shell.json = json.parsedJson

        fireShellCreatedFor json

        return new ScriptExecutor(shell: shell, parsedJsonDocument: json)
    }

    def getScriptsWithVersionAfter(currentVersion) {
        return orderedScripts.findAll { it.version > currentVersion }
    }

    @TupleConstructor
    private class ScriptExecutor {
        private GroovyShell shell
        private ParsedJsonDocument parsedJsonDocument


        void run(VersionedScript versionedScript) {
            log.info Migration, "Running script ${versionedScript}"
            fireRunningScriptOn parsedJsonDocument, versionedScript

            try {

                evaluate versionedScript
                log.info Migration, "Script ${versionedScript} finished successfully"

            } catch (Exception e) {
                log.warn Migration, "Could not evaluate script [${versionedScript}] on json [${parsedJsonDocument.jsonDocument}]", e
                errorHandler.handleScriptEvaluationError parsedJsonDocument, versionedScript, e
            }
        }

        private void evaluate(VersionedScript versionedScript) {
            shell.evaluate versionedScript.script.content as String
            versionStore.storeNewVersionOf parsedJsonDocument, versionedScript.version

            fireScriptFinishedOn parsedJsonDocument, versionedScript
        }
    }
}