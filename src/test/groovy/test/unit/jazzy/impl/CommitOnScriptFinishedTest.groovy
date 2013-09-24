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
import hu.lg.jazzy.impl.CommitOnScriptFinished
import org.junit.Test
import test.common.JsonDocumentStub

import static test.common.JsonAssert.assertJsonStringsEqual

class CommitOnScriptFinishedTest {

    static def VERSIONED_SCRIPT = null
    private JsonDocumentStub jsonDocument = new JsonDocumentStub()

    @Test
    void commitsOnScriptFinish() {
        def parsedJsonDocument = new ParsedJsonDocument(jsonDocument: jsonDocument, parsedJson: new JsonBuilder().author { name 'LG' })

        new CommitOnScriptFinished().scriptFinishedOn(parsedJsonDocument, VERSIONED_SCRIPT)

        assertJsonStringsEqual jsonDocument.after, '{ "author": { "name": "LG" } }'
    }
}
