## task 0. - Start the project ##
10/1. 
* create a project in: start.spring.io + Inteliji 
* get it connected with a GitHub repository

## task 1. - multi operating system 
* setup gitattributes for multi operating system to be able to work together

## task 2. - check style error   
* make a simple way to check so the code style is consistent all over the project.

## task 3 - Build up MVP db + Entities
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

(part 2 - 15/1)
* continued adding tables to the db
    * table photos - entity Photo
    * table photo_albums - entity PhotoAlbum
    * enum content_status - PUBLISHED DRAFT
    * index for photos and photo_albums
* added enum status converter in config folder (to be able to use BIG letters in enum and small in db)

(part 3 - 16/1 - 17/1 )
* continued adding tables to the db
    * table users_photo_albums - entity UserPhotoAlbum + UserPhotoAlbumId
    * table photo_person_tags - entity PhotoPersonTag + PhotoPersonTag
    * table tags - entity Tag
    * table photo_contributors - entity PhotoContributor + PhotoContributorId
    * table photo_album_tags - entity (many to many) Tag & PhotoAlbum
    * enum AlbumRoleStatus - OWNER, EDITOR, VIEWER
    * table photo_albums_photos - entity PhotoAlbumPhoto + PhotoAlbumPhotoId
    * index

## task 4. - Add starter page - frontend
(18/1 - 19/1 )

  * Installed Next.js
  * added components for a simple nav
  * added a simple into text on landing page
  * added a Nav, header, footer
  * added a become a contributor page

## task 5. Log in
( (22/1 - 24/1) + (26/1 -27/1 ) )

  * added simple GlobalExceptionHandler
  * added spring boots security dependency
  * added UserRepository
  * added CustomUserDetailService
  * added password encoder
  * 
  * added Jwt dependencies
  * added JwtAuthenticationFilter
  * added JwtKeyGenerator
  * added JwtService
  * 
  * added SecurityConfig
  * 
  * added login Page with a simple login form (username + password)
  * added Auth Controller +  LoginRequestDto + LoginResponseDto
  * added Spring-boot-starter-validation dependency
  * updated GlobalExceptionHandler with validation errors
  * added a member controller for testing login
  * made a simple Member-page for testing login
  * 
  * added PasswordMigrationRunner (to hash password on dummy user that was added manually in db )
  * added HttpOnlyCookies in JwtAuthenticationFilter
  * updated AuthController, LoginResponse, login page, member page


