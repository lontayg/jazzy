package test.unit.jazzy.impl

import hu.lg.jazzy.impl.StopOnError
import org.junit.Test

class StopOnErrorTest {
    static def VERSION_SCRIPT = null
    static def JSON = null


    @Test(expected = RuntimeException)
    void reThrowsTheHandledScriptEvaluationException() {
        new StopOnError().handleScriptEvaluationError(JSON, VERSION_SCRIPT, new RuntimeException())
    }

    @Test(expected = RuntimeException)
    void reThrowsTheHandledMigrationException() {
        new StopOnError().handleMigrationError(JSON, new RuntimeException())
    }
}
