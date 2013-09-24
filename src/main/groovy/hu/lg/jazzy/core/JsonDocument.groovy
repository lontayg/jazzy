package hu.lg.jazzy.core

interface JsonDocument {

    String getContent()

    void update(String jsonAfterMigration)

}