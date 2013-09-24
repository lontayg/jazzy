package hu.lg.jazzy.core

import hu.lg.jazzy.impl.JsonDocumentBasedVersionStore
import hu.lg.jazzy.impl.Slf4jLog
import hu.lg.jazzy.impl.StopOnError
import hu.lg.jazzy.impl.filesystem.DirectoryScriptSource

class Configuration {

    MigrationScriptSource scriptSource = new DirectoryScriptSource(path: "migration")
    JsonSource jsonSource
    VersionStore versionStore = new JsonDocumentBasedVersionStore()
    ErrorHandler errorHandler = new StopOnError()
    JazzyLog log = new Slf4jLog()
    List<MigrationEventListener> eventListeners = []
}
