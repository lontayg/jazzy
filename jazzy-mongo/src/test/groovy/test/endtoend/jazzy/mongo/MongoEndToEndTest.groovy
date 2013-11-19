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

package test.endtoend.jazzy.mongo
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.DBObject
import com.mongodb.util.JSON
import hu.lg.jazzy.Jazzy
import hu.lg.jazzy.impl.filesystem.DirectoryScriptSource
import hu.lg.jazzy.mongo.JazzyWithMongo
import hu.lg.jazzy.mongo.MongoCollectionJsonSource
import org.junit.Test
import test.common.AbstractMongoTest

class MongoEndToEndTest extends AbstractMongoTest {
    static final String DB_NAME = "endtoend"
    static final String COLLECTION_NAME = "contacts"
    Jazzy jazzy = new Jazzy()


    @Test
    void runsMigrationOnMongoDb() {

        saveToContacts "{'name':'Gabor Lontay', 'age':30, 'phone':'+3620-555-879-45'}"


        jazzy.config.with {
            jsonSource = new MongoCollectionJsonSource(contactsCollection)
            scriptSource = new DirectoryScriptSource(path: getClass().getResource('/mongomigration').path)
        }

        //migration script v1 changes name to Full Name object with first and last name
        jazzy.migrate()


        DBObject contact = contactFromMongo

        assert !contact.containsField("name")
        assert contact.fullName.firstName == "Gabor"
        assert contact.fullName.lastName == "Lontay"

        assert contact.age == 30 //untouched
    }

    @Test
    void runsMigrationWithJazzyWithMongo() {
        saveToContacts "{'name':'Gabor Lontay', 'age':30, 'phone':'+3620-555-879-45'}"

        def mongoMigration = new JazzyWithMongo(mongoDB, getClass().getResource('/contacts').path[0..-9])
        mongoMigration.addCollection COLLECTION_NAME

        mongoMigration.migrate()

        DBObject contact = contactFromMongo

        assert contact.name == "Anna"
    }

    void saveToContacts(String data) {
        DB testDb = mongoClient.getDB DB_NAME

        DBCollection dbCollection = testDb.createCollection COLLECTION_NAME, new BasicDBObject()
        dbCollection.save JSON.parse(data) as DBObject
    }

    DBObject getContactFromMongo() {
        DBCollection collection = contactsCollection
        return collection.findOne()
    }

    private DBCollection getContactsCollection() {
        DB testDb = mongoDB
        DBCollection collection = testDb.getCollection COLLECTION_NAME
        collection
    }

    private DB getMongoDB() {
        mongoClient.getDB DB_NAME
    }
}