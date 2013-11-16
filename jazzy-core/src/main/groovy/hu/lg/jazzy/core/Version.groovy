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

package hu.lg.jazzy.core
import groovy.transform.EqualsAndHashCode

import static java.lang.Integer.parseInt

@EqualsAndHashCode(includes = "versionNumbers")
class Version implements Comparable<Version> {
    public static Version INITIAL_VERSION = new Version([0])

    private static def PATTERN = ~ /[^\d]*((?:\d+\.)*(?:\d+))[^\d]*/
    private def versionNumbers = [];

    private Version(def versionNumbers) {
        this.versionNumbers = versionNumbers
    }

    static Version of(String migrationScriptName) {
        def matcher = migrationScriptName =~ PATTERN

        if (migrationScriptName ==~ PATTERN) {

            def versionNumbers = []
            //noinspection GroovyAssignabilityCheck
            (matcher[0][1] as String).tokenize('.').each {
                versionNumbers << parseInt(it)
            }

            return new Version(versionNumbers)

        } else {
            throw new IllegalArgumentException("No or multiple version number found in ${migrationScriptName}")
        }
    }


    @Override
    int compareTo(Version o) {
        if (this.is(o)) {
            return 0
        }

        if (this.is(INITIAL_VERSION)) {
            return -1
        }

        if (o.is(INITIAL_VERSION)) {
            return 1
        }

        if (versionNumbers == o.versionNumbers) {
            return 0
        }

        for (i in 0..(versionNumbers.size() - 1)) {
            if (o.versionNumbers.size() < i) {
                return 1
            } else {
                if (versionNumbers[i] != o.versionNumbers[i]) {
                    return versionNumbers[i] <=> o.versionNumbers[i];
                }
            }
        }

        return -1
    }

    String toPrettyString() {
        versionNumbers.inject { str, item -> str + "." + item }
    }

    @Override
    String toString() {
        if (this.is(INITIAL_VERSION)) {
            "InitialVersion"
        } else {
            "Version[${versionNumbers}]"
        }
    }

    def getVersionNumbers() {
        new ArrayList(versionNumbers)
    }
}