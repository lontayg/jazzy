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
import com.mongodb.BasicDBObject
import com.mongodb.DBCollection
import hu.lg.jazzy.mongo.MongoDocument
import org.junit.Ignore
import org.junit.Test

import static test.common.JsonAssert.assertJsonStringsEqual

class MongoDocumentTest {
    static final DBCollection DUMMY_COLLECTION = null



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

    @Ignore("in development")
    @Test
    void updatesContent() {
        
    }
}
