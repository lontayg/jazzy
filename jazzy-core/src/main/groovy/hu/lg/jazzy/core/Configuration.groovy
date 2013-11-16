/*
 * Copyright 2013 Gabor Lontay
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
