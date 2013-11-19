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
import com.mongodb.DB
import hu.lg.jazzy.Jazzy
import hu.lg.jazzy.impl.filesystem.DirectoryScriptSource

class JazzyWithMongo {

    DB mongoDb
    String basePath
    def collectionNames = []

    JazzyWithMongo(DB mongoDb, String basePath) {
        this.mongoDb = mongoDb
        this.basePath = basePath
    }

    JazzyWithMongo addCollection(String collectionName) {
        collectionNames << collectionName
        return this
    }

    void migrate() {
        collectionNames.each { collectionName ->
            def collection = mongoDb.getCollection collectionName

            Jazzy jazzy = new Jazzy()
            jazzy.config.with {
                jsonSource = new MongoCollectionJsonSource(collection)
                scriptSource = new DirectoryScriptSource(path: basePath + collectionName)
            }

            jazzy.migrate()
        }
    }
}