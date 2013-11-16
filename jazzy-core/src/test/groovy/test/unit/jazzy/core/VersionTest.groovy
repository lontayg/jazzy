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

package test.unit.jazzy.core

import hu.lg.jazzy.core.Version
import org.junit.Test

class VersionTest {

    @Test(expected = IllegalArgumentException.class)
    void throwsErrorIfNoVersionFound() {
        Version.of("v.jazzy")
    }

    @Test(expected = IllegalArgumentException.class)
    void throwsErrorIfMultipleVersionsFound() {
        Version.of("v1.v2.jazzy")
    }


    @Test
    void parsesNameWithVersion() {
        assert [1] == Version.of("1.jazzy").versionNumbers
        assert [1] == Version.of("v1.jazzy").versionNumbers
        assert [10] == Version.of("v10-rename-count-to-number.jazzy").versionNumbers
        assert [10, 11] == Version.of("v10.11.jazzy").versionNumbers
        assert [10, 11, 12] == Version.of("v10.11.12-rename-count-to-number.jazzy").versionNumbers
    }

    @Test
    void comparableWithOtherVersion() {
        assert Version.of("v1.jazzy") < Version.of("v2.jazzy")
        assert Version.of("v1.jazzy") < Version.of("v1.1.jazzy")

        assert Version.of("v1.jazzy") == Version.of("v1-rename.jazzy")
        assert Version.of("v1.jazzy").hashCode() == Version.of("v1-rename.jazzy").hashCode()

        assert Version.of("v1.2.2.2.jazzy") < Version.of("v1.2.3.jazzy")
        assert Version.of("v10.11-minor-change.jazzy") < Version.of("v10.12-rename.jazzy")

        assert Version.of("v1.2.3.jazzy") > Version.of("v1.2.2.2.jazzy")
        assert Version.of("v10.12-rename.jazzy") > Version.of("v10.11-minor-change.jazzy")
    }

    @Test
    void initialVersionIsLessThanAnyOtherVersion() {
        assert Version.INITIAL_VERSION == Version.INITIAL_VERSION
        assert Version.of("v0.jazzy") > Version.INITIAL_VERSION
        assert Version.of("v-1.jazzy") > Version.INITIAL_VERSION

        assert Version.INITIAL_VERSION < Version.of("v0.jazzy")
        assert Version.INITIAL_VERSION < Version.of("v-1.jazzy")
    }

    @Test
    void createsPrettyStringAsDotSeparatedVersionNumbers() {
        assert Version.of("v12.23.34.jazzy").toPrettyString() == "12.23.34"
    }
}