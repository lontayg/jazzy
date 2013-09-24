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
