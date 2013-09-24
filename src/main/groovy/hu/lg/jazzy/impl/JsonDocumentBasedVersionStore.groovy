package hu.lg.jazzy.impl

import hu.lg.jazzy.core.ParsedJsonDocument
import hu.lg.jazzy.core.Version
import hu.lg.jazzy.core.VersionStore

class JsonDocumentBasedVersionStore implements VersionStore {
    static final String DEFAULT_ID = "_jv"

    private final String id;

    JsonDocumentBasedVersionStore() {
        this(DEFAULT_ID)
    }

    JsonDocumentBasedVersionStore(String id) {
        this.id = id
    }

    @Override
    Version getVersionFor(ParsedJsonDocument json) {
        def versionProperty = json.parsedJson[id]
        if (versionProperty) {
            Version.of(versionProperty.toString())
        } else {
            Version.INITIAL_VERSION
        }
    }

    @Override
    void storeNewVersionOf(ParsedJsonDocument json, Version newVersion) {
        json.parsedJson[id] = newVersion.toPrettyString()
        json.updateOriginalJson()
    }
}
