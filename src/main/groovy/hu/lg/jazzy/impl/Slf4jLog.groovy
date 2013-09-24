package hu.lg.jazzy.impl
import hu.lg.jazzy.core.JazzyLog

import static org.slf4j.LoggerFactory.getLogger

public class Slf4jLog implements JazzyLog {

    @Override
    void info(Class aClass, String msg) {
        getLogger(aClass).info msg
    }

    @Override
    void warn(Class aClass, String msg, Exception e) {
        getLogger(aClass).warn msg, e
    }
}
