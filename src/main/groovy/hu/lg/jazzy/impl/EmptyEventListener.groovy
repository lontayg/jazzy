package hu.lg.jazzy.impl

import hu.lg.jazzy.core.JsonDocument
import hu.lg.jazzy.core.MigrationEventListener
import hu.lg.jazzy.core.ParsedJsonDocument
import hu.lg.jazzy.core.VersionedScript

class EmptyEventListener implements MigrationEventListener {
    @Override
    void migrationStartedOn(JsonDocument jsonDocument) {
    }

    @Override
    void jsonDocumentParsed(ParsedJsonDocument parsedJsonDocument) {
    }

    @Override
    void shellCreatedFor(ParsedJsonDocument parsedJsonDocument) {
    }

    @Override
    void runningScriptOn(ParsedJsonDocument parsedJsonDocument, VersionedScript versionedScript) {
    }

    @Override
    void scriptFinishedOn(ParsedJsonDocument parsedJsonDocument, VersionedScript versionedScript) {
    }

    @Override
    void migrationFinishedOn(ParsedJsonDocument parsedJsonDocument) {
    }
}