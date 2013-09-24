package hu.lg.jazzy.core


public interface VersionStore {

    Version getVersionFor(ParsedJsonDocument json)

    void storeNewVersionOf(ParsedJsonDocument json, Version newVersion)
}