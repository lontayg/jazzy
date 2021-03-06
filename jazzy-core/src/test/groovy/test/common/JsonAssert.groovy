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

package test.common

import groovy.json.JsonException
import groovy.json.JsonSlurper

class JsonAssert {
    static def slurper = new JsonSlurper()


    static void assertJsonStringsEqual(String a, String b) {
        assert parse(a) == parse(b)
    }

    private static Object parse(String a) {
        try {
            slurper.parseText(a)
        } catch (JsonException e) {
            throw new RuntimeException("Invalid json string [$a] " , e)
        }
    }
}
