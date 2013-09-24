package hu.lg.jazzy.impl

import hu.lg.jazzy.core.ErrorHandler
import hu.lg.jazzy.core.JsonDocument
import hu.lg.jazzy.core.ParsedJsonDocument
import hu.lg.jazzy.core.VersionedScript

class StopOnError implements ErrorHandler {

    @Override
    void handleScriptEvaluationError(ParsedJsonDocument json, VersionedScript script, Exception e) {
        throw e
    }

    @Override
    void handleMigrationError(JsonDocument json, Exception e) throws Exception {
        throw e
    }
}
