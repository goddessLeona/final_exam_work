task 0  10/1. 
* create a project in in start.spring.io + inteliji 
* get it connected with a github repository

task 1. 
* setup gitattributes for multi operation system to be able to work together

task 2. 
* make a simple way to check so the code style is consistent all over the project.

task 3  14/1. 
* create a db and connect it to project -  application-local.properties
* add flyway migration /main/resources/db/migration
* started added tables and entities
    * table users - entity User
    * table roles - entity Role
    * table users_roles - entity (many to many) - User & Role 
    * table consent_forms - entity ConsentForm
    * table users_consent_forms - entity UsersConsentFormId + UsersConsentForm
    * enum ConsentStatus - PENDING APPROVED REJECTED