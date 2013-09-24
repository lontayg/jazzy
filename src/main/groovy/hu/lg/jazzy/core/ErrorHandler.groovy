package hu.lg.jazzy.core

public interface ErrorHandler {

    void handleScriptEvaluationError(ParsedJsonDocument json, VersionedScript script, Exception e) throws Exception

    void handleMigrationError(JsonDocument json, Exception e) throws Exception
}