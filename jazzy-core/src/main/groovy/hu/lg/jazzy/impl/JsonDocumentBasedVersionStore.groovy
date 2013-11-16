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
