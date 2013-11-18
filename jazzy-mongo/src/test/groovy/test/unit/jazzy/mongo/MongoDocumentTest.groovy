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

package test.unit.jazzy.mongo
import com.mongodb.*
import com.mongodb.util.JSON
import hu.lg.jazzy.mongo.MongoDocument
import org.junit.Test

import static test.common.JsonAssert.assertJsonStringsEqual

class MongoDocumentTest {
    static final DBCollection DUMMY_COLLECTION = null
    def aCollection = new FakeDBCollection()
    def aDbObject = new BasicDBObject()

    @Test
    void contentIsJsonString() {
        def document = new BasicDBObject()

        document.with {
            append "name", "Gabor"
            append "age", 30
        }

        assertJsonStringsEqual new MongoDocument(document, DUMMY_COLLECTION).content,
                '{ "name":"Gabor", "age":30 }'
    }

    @Test
    void updatesContent() {
        def migratedContent = '{ "name":"Gabor", "age":30 }'

        new MongoDocument(aDbObject, aCollection).update(migratedContent)

        assert aDbObject == aCollection.oldObject
        assert JSON.parse(migratedContent) == aCollection.newObject

    }
}