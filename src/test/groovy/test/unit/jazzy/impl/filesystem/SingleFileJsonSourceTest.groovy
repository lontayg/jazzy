package test.unit.jazzy.impl.filesystem

import hu.lg.jazzy.impl.filesystem.FileJsonDocument
import hu.lg.jazzy.impl.filesystem.SingleFileJsonSource
import org.junit.Test

import static org.junit.Assert.assertEquals

class SingleFileJsonSourceTest {

    def json = new FileJsonDocument(TestFiles.v1TestScript)
    def jsonSource = new SingleFileJsonSource(path: TestFiles.v1TestScriptPath)


    @Test
    void resolvesPathToFileJsonDocument() {
        assertEquals([json], jsonSource.jsonDocuments)

    }

}
