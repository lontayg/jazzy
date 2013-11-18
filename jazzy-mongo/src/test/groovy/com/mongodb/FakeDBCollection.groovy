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

package com.mongodb

class FakeDBCollection extends DBCollection {

    public DBObject oldObject
    public DBObject newObject

    FakeDBCollection() {
        super(new FakeDB(), "stub collection")
    }

    @Override
    WriteResult insert(List<DBObject> list, WriteConcern concern, DBEncoder encoder) {
        return null
    }

    @Override
    WriteResult update(DBObject q, DBObject o, boolean upsert, boolean multi, WriteConcern concern, DBEncoder encoder) {
        oldObject = q
        newObject = o
        return null
    }

    @Override
    protected void doapply(DBObject o) {
    }

    @Override
    WriteResult remove(DBObject o, WriteConcern concern, DBEncoder encoder) {
        return null
    }

    @Override
    void createIndex(DBObject keys, DBObject options, DBEncoder encoder) {
    }

    Iterator<DBObject> __find( DBObject ref , DBObject fields , int numToSkip , int batchSize , int limit, int options, ReadPreference readPref, DBDecoder decoder ) {
        return null
    }

    Iterator<DBObject> __find( DBObject ref , DBObject fields , int numToSkip , int batchSize , int limit, int options,
                               ReadPreference readPref, DBDecoder decoder, DBEncoder encoder ) {
        return null
    }

}
