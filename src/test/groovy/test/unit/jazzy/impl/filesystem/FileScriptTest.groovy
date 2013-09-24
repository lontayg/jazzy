package test.unit.jazzy.impl.filesystem

import hu.lg.jazzy.impl.filesystem.FileScript
import org.junit.Test

import static org.junit.Assert.assertEquals

class FileScriptTest {
    def script = new FileScript(TestFiles.v1TestScript)

    @Test
    void getsShortNameOfTheFile() {
        assertEquals "v1-testscript.jazzy", script.name
    }

    @Test
    void getsContentOfTheFile() {
        assertEquals TestFiles.v1TestScriptContent, script.content
    }
}