package hu.lg.jazzy.core
import groovy.json.JsonBuilder
import groovy.transform.TupleConstructor

@TupleConstructor
class ParsedJsonDocument {
    private JsonDocument jsonDocument
    private def parsedJson

    void updateOriginalJson() {
        jsonDocument.update new JsonBuilder(parsedJson).toPrettyString()
    }

    def getParsedJson() {
        parsedJson
    }

    JsonDocument getJsonDocument() {
        jsonDocument
    }
}
