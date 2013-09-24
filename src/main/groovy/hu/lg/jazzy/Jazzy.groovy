package hu.lg.jazzy

import hu.lg.jazzy.core.Configuration
import hu.lg.jazzy.core.Migration

class Jazzy {

    Configuration config = new Configuration();

    static void migrate(String configFilePath) {
        //TODO: load config from script
    }

    void migrate() {

        def migration = Migration.from(config)

        config.jsonSource.jsonDocuments.each {
            try {
                migration.executeOn it
            } catch (Exception e) {
                config.errorHandler.handleMigrationError it, e
            }
        }
    }
}
