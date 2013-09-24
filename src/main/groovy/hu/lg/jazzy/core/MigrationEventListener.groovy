package hu.lg.jazzy.core

public interface MigrationEventListener extends EventListener {

    void migrationStartedOn(JsonDocument jsonDocument)
    void jsonDocumentParsed(ParsedJsonDocument parsedJsonDocument)
    void shellCreatedFor(ParsedJsonDocument parsedJsonDocument)
    void runningScriptOn(ParsedJsonDocument parsedJsonDocument, VersionedScript versionedScript)
    void scriptFinishedOn(ParsedJsonDocument parsedJsonDocument, VersionedScript versionedScript)
    void migrationFinishedOn(ParsedJsonDocument parsedJsonDocument)

}