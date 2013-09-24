package hu.lg.jazzy.impl.filesystem
import groovy.transform.EqualsAndHashCode
import hu.lg.jazzy.core.MigrationScript

@EqualsAndHashCode(includes = "file")
class FileScript implements MigrationScript {

    private File file;
    private String text;

    FileScript(File file) {
        assert file.exists() && file.isFile()
        this.file = file
    }

    @Override
    String getName() {
        file.getName()
    }

    @Override
    String getContent() {
        if (!text) {
            text = file.text;
        }

        return text
    }

    @Override
    String toString() {
        "FileScript[$name]"
    }
}