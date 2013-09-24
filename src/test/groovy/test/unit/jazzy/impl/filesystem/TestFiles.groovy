package test.unit.jazzy.impl.filesystem

class TestFiles {

    static String getV1TestScriptContent() {
        "test-content"
    }

    static String getV1TestScriptDirectoryPath() {
        getClass().getResource('/test/unit').path
    }

    static String getV1TestScriptPath() {
        getClass().getResource('/test/unit/v1-testscript.jazzy').file
    }

    static File getV1TestScript() {
        new File(v1TestScriptPath)
    }

    static File getEmptyTestScript() {
        new File(v1TestScriptDirectoryPath, "testJsonDocument.jazzy")
    }

    static String getEndToEndStartScriptPath() {
        getClass().getResource('/test/endtoend/start.json').file
    }

    static String getEndToEndResultContent() {
        new File(getClass().getResource('/test/endtoend/result.json').path).text
    }


    static File getEndToEndStartScript() {
        new File(endToEndStartScriptPath)
    }

    static File getEmptyEndToEndScript() {
        new File(endToEndStartScript.parentFile, "start.jazzy")
    }

    static String getMigrationScriptDirectoryPath() {
        getClass().getResource('/migration').path
    }

}