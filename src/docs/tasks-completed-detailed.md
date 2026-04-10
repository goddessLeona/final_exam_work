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

## task 5. Log out
(31/1)

  * added a new navbar for member pages with a logout
  * added a logout in AuthController
  * added logout-button component

## task 6. Me / logged in user
(06/2 - 07/2)

  * added a new ApiException
  * added a new ErrorResponse
  * added customUserDetails
  * added ResponseDto + MapperDto + Service + controller ( @Get /user/username ) + added in security config
  * 
  * added simple css to member/page
  * added fetch Api frontend

## task 7. Sign up contributor
(08/2, 14/2, 16/2-18/2)

  * added RequestDto 
  * 
  * added dependency - Mapstruct 
  * added ContributorSignUpMapper
  * added  a custom validate - confirm password, used in RequestDto
  * moved logic from AuthController to AuthService (missed that before)
  * 
  * added a ContributorSignUp in UserService
  * added a RoleRepository
  * added a contributorResponse 
  * added endpoint in controller (@Post user/signUp-contributor)
  * added in security config
  * 
  * added a components/forms/subscribeContributorForm
  * added a lib/api/user Where I added the fetch
  * 
  * added error messages in fields

## task 8. contributor dashboard
(21/2 - 22/2)

(info -contributor)
  * added ResponseDto + Mapper + service
  * added SecurityUtils
  * added controller (GET contributor/info)
  * added endpoint in security config

(welcome message, depending on if already approved by admin or not)
  * added ResponseDto + Mapper + service
  * added controller (Get contributor/welcome)
  * added contributor nav, and contributor pages
  * added fetch in lib/api/contributor

## task 9. contributor - consent form
(22/2- 24) (8/3) (5-9/4)
Contributor should be able to fill in a form add 3 different image documentation. Should be able to refill form if
only one of the documents was approved by admin. (not empty form each try)

  * added request, response, mapper (GET + POST consent form)
  * added service (GET + POST consent form)
  * 
  * added FileStorageService + location where uploaded content get stored
  * 
  * added FileStorageService + validation checking if image or not
  * added controller (GET + POST consent form)

  * added fetch in contributor.ts (GET + POST agreementForm from backend)
  * added a new components/form ContributorAgreementForm

  * modified Database (changed Enums in db to Big letters to be able to use POST )
  * added db flyway version2, so that db always have role CONTRIBUTOR, ADMIN , MEMBER (If I have to restart)
  * modified ExeptionHandler for better field response in frontend
  * css - field messages and general messages showing in form if not filled in right

## task 10. Admin Dashboard + Reply to fom submision 
(10/4-)
Admin should have a list of all new contributors consent form
Admin should be able to approve or reject the form and send back to contributor.







