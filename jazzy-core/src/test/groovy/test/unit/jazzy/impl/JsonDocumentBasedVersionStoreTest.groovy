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

package test.unit.jazzy.impl
import groovy.json.JsonBuilder
import hu.lg.jazzy.core.ParsedJsonDocument
import hu.lg.jazzy.core.Version
import hu.lg.jazzy.impl.JsonDocumentBasedVersionStore
import org.junit.Test
import test.common.JsonDocumentStub

import static test.common.JsonAssert.assertJsonStringsEqual

class JsonDocumentBasedVersionStoreTest {
    JsonBuilder builderWithVersion = new JsonBuilder()
    def jsonWithVersion = builderWithVersion(author: "LG", _jv: "1.1")

    JsonBuilder builderWithOutVersion = new JsonBuilder()
    def jsonWithOutVersion = builderWithOutVersion(author : "LG")

    JsonDocumentStub stubWithVersionNumber = new JsonDocumentStub(originalContent: builderWithVersion.toPrettyString())
    JsonDocumentStub stubWithOutVersionNumber = new JsonDocumentStub(originalContent: builderWithOutVersion.toPrettyString())

    ParsedJsonDocument withVersionNumber = new ParsedJsonDocument(parsedJson: jsonWithVersion, jsonDocument: stubWithVersionNumber)
    ParsedJsonDocument withOutVersionNumber = new ParsedJsonDocument(parsedJson: jsonWithOutVersion, jsonDocument: stubWithOutVersionNumber)

    JsonDocumentBasedVersionStore store = new JsonDocumentBasedVersionStore()


    @Test
    void writesTheVersionBackToTheDocument() {

        store.storeNewVersionOf(withOutVersionNumber, Version.of("1.1.2"))
        assertJsonStringsEqual '{ "author": "LG", "_jv": "1.1.2" }', stubWithOutVersionNumber.after
    }

    @Test
    void overridesExistingVersionNumber() {
        store.storeNewVersionOf(withVersionNumber, Version.of("1.1.2"))
        assertJsonStringsEqual '{ "author": "LG", "_jv": "1.1.2" }', stubWithVersionNumber.after

    }

    @Test
    void returnsInitialVersionIfVersionInfoNotFound() {
        assert Version.INITIAL_VERSION == store.getVersionFor(withOutVersionNumber)
    }

    @Test
    void readsVersionFromVersionInfo() {
        assert Version.of("1.1") == store.getVersionFor(withVersionNumber)
    }
}
