package test.common

import hu.lg.jazzy.core.JsonDocument

class JsonDocumentStub implements JsonDocument {

    String originalContent;
    String after;

    @Override
    String getContent() {
        originalContent
    }

    @Override
    void update(String jsonAfterMigration) {
        after = jsonAfterMigration
    }
}