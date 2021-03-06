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

package test.integration.jazzy.mongo
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.DBObject
import com.mongodb.util.JSON
import org.junit.Test
import test.common.AbstractMongoTest

class MongoSetupTest extends AbstractMongoTest {

    static final String DB_NAME = "test"
    static final String COLLECTION_NAME = "testCollection"

    @Test
    void crudWithMongoIsWorking() {

        createTestCollectionWithDocument "{'name':'Gabor', 'age':30}"


        DBObject document = retrieveSingleDocument()
        assert document.name == 'Gabor'
        assert document.age == 30


        updateDocument document, "{'name':'Anna'}"


        document = retrieveSingleDocument()
        assert document.name == 'Anna'
        assert document.age == null


        testDbCollection.remove document

        assert testDbCollection.count == 0
    }

    void createTestCollectionWithDocument(String document) {
        DB testDb = mongoClient.getDB DB_NAME

        DBCollection dbCollection = testDb.createCollection COLLECTION_NAME, new BasicDBObject()
        dbCollection.save JSON.parse(document) as DBObject
    }

    DBObject retrieveSingleDocument() {
        DBCollection dbCollection = testDbCollection
        assert dbCollection.count() == 1L

        dbCollection.findOne()
    }

    void updateDocument(DBObject document, String newDocument) {
        testDbCollection.update document, JSON.parse(newDocument) as DBObject
    }

    DBCollection getTestDbCollection() {
        DB testDb = mongoClient.getDB DB_NAME
        testDb.getCollection COLLECTION_NAME
    }
}