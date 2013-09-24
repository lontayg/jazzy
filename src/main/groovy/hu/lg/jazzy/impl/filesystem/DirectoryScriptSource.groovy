package hu.lg.jazzy.impl.filesystem

import groovy.transform.Immutable
import hu.lg.jazzy.core.MigrationScript
import hu.lg.jazzy.core.MigrationScriptSource

@Immutable
class DirectoryScriptSource implements MigrationScriptSource {

    String path

    @Override
    List<MigrationScript> getScripts() {
        def list = []

        directory().eachFile {
            if (it.file) {
                list.add(new FileScript(it))
            }
        }

        return list
    }

    private File directory() {
        def dir = new File(path)

        assert dir.exists()
        assert dir.isDirectory()

        return dir
    }
}
