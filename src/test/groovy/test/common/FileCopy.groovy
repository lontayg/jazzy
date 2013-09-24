package test.common

class FileCopy {

    static void register() {
        File.metaClass.copyTo = { File out -> copy(delegate as File, out) }
    }

    private static void copy(File source, File destination) {
        destination.withDataOutputStream { os ->
            source.withDataInputStream { is ->
                os << is
            }
        }
    }
}
