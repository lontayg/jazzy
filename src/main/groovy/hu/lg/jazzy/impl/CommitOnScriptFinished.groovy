package hu.lg.jazzy.impl

import hu.lg.jazzy.core.ParsedJsonDocument
import hu.lg.jazzy.core.VersionedScript

class CommitOnScriptFinished extends EmptyEventListener {

    @Override
    void scriptFinishedOn(ParsedJsonDocument parsedJsonDocument, VersionedScript versionedScript) {
        parsedJsonDocument.updateOriginalJson()
    }
}
