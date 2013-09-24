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
