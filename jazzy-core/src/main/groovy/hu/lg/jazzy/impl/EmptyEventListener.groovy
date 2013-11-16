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