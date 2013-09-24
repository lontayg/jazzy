package hu.lg.jazzy.core

public interface JazzyLog {

    void info(Class aClass, String msg)
    void warn(Class aClass, String msg, Exception e)

}