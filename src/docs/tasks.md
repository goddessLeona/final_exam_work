## task 0. - Start the project ##
10/1. 
* create a project in: start.spring.io + inteliji 
* get it connected with a github repository

## task 1. - multi operating system ##
* setup gitattributes for multi operating system to be able to work together

## task 2. - check style error ##  
* make a simple way to check so the code style is consistent all over the project.

## task 3 - Build up MVP db ##
(part 1 - 14/1)
* create a db and connect it to project -  application-local.properties
* add flyway migration /main/resources/db/migration
* started added tables and entities
    * table users - entity User
    * table roles - entity Role
    * table users_roles - entity (many to many) - User & Role 
    * table consent_forms - entity ConsentForm
    * table users_consent_forms - entity UsersConsentFormId + UsersConsentForm
    * enum consent_status - PENDING APPROVED REJECTED

## task 3. - Build up MVP db ##
(part 2 - 15/1)
* continued adding tables to the db
    * table photos - entity Photo
    * table photo_albums - entity PhotoAlbum
    * enum content_status - PUBLISHED DRAFT
    * index for photos and photo_albums
* added enum status converter in config folder (to be able to use BIG letters in enum and small in db)

## task 3. - Build up MVP db ##
(part 3 - 16/1 - )
* continued adding tables to the db
    * table users_photo_albums - entity UserPhotoAlbum + UserPhotoAlbumId
    * table photo_person_tags
    * table tags - entity Tag
    * table photo_contributors
    * table photo_album_tags - entity (many to many) Tag & PhotoAlbum
    * table photo_albums_photos

## task 4. - ##

* Start working in branches

BioN69