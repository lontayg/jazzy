package hu.lg.jazzy.core

import groovy.transform.TupleConstructor

@TupleConstructor
class VersionedScript {

    private Version version
    private MigrationScript script

    static VersionedScript from(MigrationScript script) {
        def name = script.name
        def version = Version.of(name)
        return new VersionedScript(version: version, script: script)
    }

    @Override
    String toString() {
        "${version.toPrettyString()} [${script}]"
    }

    Version getVersion() {
        version
    }

    MigrationScript getScript() {
        script
    }
}
