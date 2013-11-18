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
import com.mongodb.DBCursor
import hu.lg.jazzy.core.JsonDocument
import hu.lg.jazzy.core.JsonSource

class MongoCollectionJsonSource implements JsonSource {

    private DBCollection collection

    MongoCollectionJsonSource(DBCollection collection) {
        this.collection = collection
    }

    @Override
    List<JsonDocument> getJsonDocuments() {
        def list = []
        DBCursor cursor = collection.find()
        try {
            while(cursor.hasNext()) {
                list << new MongoDocument(cursor.next(), collection)
            }
        } finally {
            cursor.close()
        }

        return list
    }
}
