package test.common

import groovy.json.JsonSlurper

class JsonAssert {
    static def slurper = new JsonSlurper()


    static void assertJsonStringsEqual(String a, String b) {
        assert slurper.parseText(a) == slurper.parseText(b)
    }
}
