package hu.lg.jazzy.impl.filesystem

import groovy.transform.Immutable
import hu.lg.jazzy.core.JsonDocument
import hu.lg.jazzy.core.JsonSource

@Immutable
class SingleFileJsonSource implements JsonSource {

    String path;

    @Override
    List<JsonDocument> getJsonDocuments() {
        def file = new File(path)
        assert file.exists() && file.isFile()

        return [new FileJsonDocument(file)]
    }
}
