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

package test.integration.jazzy
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.DBObject
import com.mongodb.util.JSON
import org.junit.Test
import test.common.AbstractMongoTest

class MongoSetupTest extends AbstractMongoTest {


    @Test
    void mongoIsWorking() {

        DB testDb = mongoClient.getDB "test"

        DBCollection col = testDb.createCollection "testCollection", new BasicDBObject()
        col.save JSON.parse("{'name':'Gabor', 'age':30}") as DBObject


        testDb = mongoClient.getDB "test"
        col = testDb.getCollection "testCollection"
        assert col.count() == 1L

        def document = col.findOne()
        assert document.name == 'Gabor'
        assert document.age == 30
    }
}