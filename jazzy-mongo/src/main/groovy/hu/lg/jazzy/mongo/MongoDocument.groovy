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

package hu.lg.jazzy.mongo

import com.mongodb.DBCollection
import com.mongodb.DBObject
import com.mongodb.util.JSON
import hu.lg.jazzy.core.JsonDocument

class MongoDocument implements JsonDocument {

    DBObject dbObject
    DBCollection dbCollection

    MongoDocument(DBObject dbObject, DBCollection dbCollection) {
        this.dbObject = dbObject
        this.dbCollection = dbCollection
    }

    @Override
    String getContent() {
        return JSON.serialize(dbObject)
    }

    @Override
    void update(String jsonAfterMigration) {
        def newDocument = JSON.parse(jsonAfterMigration) as DBObject
        dbCollection.update dbObject, newDocument
    }
}
