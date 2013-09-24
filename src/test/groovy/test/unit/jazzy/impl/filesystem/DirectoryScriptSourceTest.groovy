package test.unit.jazzy.impl.filesystem
import hu.lg.jazzy.impl.filesystem.DirectoryScriptSource
import hu.lg.jazzy.impl.filesystem.FileScript
import org.junit.Test

import static org.junit.Assert.assertEquals

class DirectoryScriptSourceTest {
    def script = new FileScript(TestFiles.v1TestScript)
    def directoryScriptSource = new DirectoryScriptSource(path: TestFiles.v1TestScriptDirectoryPath)


    @Test
    void findsFilesInDirectory() {
        assertEquals([script], directoryScriptSource.scripts)
    }
}
