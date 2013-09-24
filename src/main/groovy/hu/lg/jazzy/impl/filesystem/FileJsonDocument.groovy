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

package hu.lg.jazzy.impl.filesystem

import groovy.transform.EqualsAndHashCode
import hu.lg.jazzy.core.JsonDocument

@EqualsAndHashCode(includes = "file")
class FileJsonDocument implements JsonDocument {
    private File file;

    FileJsonDocument(File file) {
        assert file.exists()
        this.file = file
    }

    @Override
    String getContent() {
        return file.text
    }

    @Override
    void update(String jsonAfterMigration) {
        file.text = jsonAfterMigration
    }

    @Override
    String toString() {
        "FileJsonDocument[$file.name]"
    }
}