package hu.lg.jazzy.impl.filesystem

import groovy.transform.EqualsAndHashCode
import hu.lg.jazzy.core.JsonDocument

@EqualsAndHashCode(includes = "file")
class FileJsonDocument implements JsonDocument {
    private File file;

    FileJsonDocument(File file) {
        assert file.exists()
        this.file = file
    }

    @Override
    String getContent() {
        return file.text
    }

    @Override
    void update(String jsonAfterMigration) {
        file.text = jsonAfterMigration
    }

    @Override
    String toString() {
        "FileJsonDocument[$file.name]"
    }
}