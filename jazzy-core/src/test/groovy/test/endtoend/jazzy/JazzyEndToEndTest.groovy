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

package test.endtoend.jazzy
import hu.lg.jazzy.Jazzy
import hu.lg.jazzy.impl.filesystem.DirectoryScriptSource
import hu.lg.jazzy.impl.filesystem.SingleFileJsonSource
import org.junit.After
import org.junit.Before
import org.junit.Test
import test.common.FileCopy
import test.unit.jazzy.impl.filesystem.TestFiles

import static test.common.JsonAssert.assertJsonStringsEqual

class JazzyEndToEndTest {
    Jazzy jazzy = new Jazzy()

    File originalFile = TestFiles.endToEndStartScript
    File startScript = TestFiles.emptyEndToEndScript

    @Before
    void createStartFile() {
        FileCopy.register()
        originalFile.copyTo startScript
    }

    @After
    void deleteStartFile() {
        startScript.delete()
    }

    @Test
    void runsScriptsAndStoresResult() {

        jazzy.config.with {
            jsonSource = new SingleFileJsonSource(path: startScript.path)
            scriptSource = new DirectoryScriptSource(path: TestFiles.migrationScriptDirectoryPath)
        }

        jazzy.migrate()

        assertJsonStringsEqual TestFiles.endToEndResultContent, startScript.text
    }
}
