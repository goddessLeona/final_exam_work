
## Fly way rules and guides:

Required naming pattern
V<version>__<description>.sql

V1 → version number (mandatory)
__ → double underscore (mandatory)
init_schema → free text (for humans)
.sql → migration file

v1_init_schema.sql = “Create the initial database structure”

That typically includes:
* Tables
* Primary keys
* Foreign keys
* Constraints
* Indexes

Flyway executes migrations:

V1 → V2 → V3 → V4

Not by name.
Only by version order.

## Flyway forbids changing old migrations

Simple mental model (remember this)

Flyway migrations are history.
You don’t rewrite history — you add a new chapter.

But:
Before history matters, it’s okay to reset the book.

In the build up face: build db tables and test run, if not happy drop db and do changes. 
Then create db again and run until happy.

## keep a logical order inside the file:

* Extensions
* Types (ENUMs)
* Tables without FKs
* Tables with FKs / junction tables