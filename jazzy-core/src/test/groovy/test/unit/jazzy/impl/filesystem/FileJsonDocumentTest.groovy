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

package test.unit.jazzy.impl.filesystem

import hu.lg.jazzy.impl.filesystem.FileJsonDocument
import org.junit.After
import org.junit.Before
import org.junit.Test
import test.common.FileCopy

import static org.junit.Assert.assertEquals

class FileJsonDocumentTest {

    File originalFile = TestFiles.v1TestScript
    File testFile = TestFiles.emptyTestScript

    @Before
    void createTestFile() {
        FileCopy.register()
        originalFile.copyTo testFile
    }

    @After
    void deleteTestFile() {
        testFile.delete()
    }

    @Test
    void getsContentOfTheFile() {
        assertEquals "test-content", new FileJsonDocument(testFile).content
    }

    @Test
    void updatesTheContentOfTheFile() {
        new FileJsonDocument(testFile).update("updated-text")

        assertEquals "updated-text", testFile.text
    }
}
