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
