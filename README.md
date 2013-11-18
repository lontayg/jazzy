## Jazzy - a JSON migration tool written in Groovy
[![Build Status](https://travis-ci.org/lontayg/jazzy.png?branch=master)](https://travis-ci.org/lontayg/jazzy)

### About

Jazzy is an [open-source](LICENSE.md) data migration tool written in Groovy for documents in JSON format.
It provides schema versioning for JSON document stores (such as [Mongo DB](http://www.mongodb.org/)). Jazzy works in a similar way to agile RDBMS migration tools (e.g. [Flyway](http://flywaydb.org/)).
Although the language of the migration scripts is Groovy, Jazzy can be included (and extended) in any JVM-based project.
Currently file-based JSON document store and Mongo DB is supported.

### Usage

For further configuration details and usage info please see the end-to-end tests.

#### Plain text

#### Mongo DB


### Build from source

When you fork or clone our branch you should always be able to build the library by running

	gradle build
