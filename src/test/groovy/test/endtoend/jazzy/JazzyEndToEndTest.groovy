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
