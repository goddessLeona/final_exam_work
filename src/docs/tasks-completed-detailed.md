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
  * added service (GET + POST consent form)("/contributor/consent")
  * 
  * added FileStorageService + location (path) where uploaded content get stored
  * 
  * added FileStorageService + validation checking if image or not
  * added controller (GET + POST consent form)

  * added fetch in contributor.ts (GET + POST agreementForm from backend)
  * added a new components/form ContributorAgreementForm

  * modified Database (changed Enums in db to Big letters to be able to use POST )
  * added db flyway version2, so that db always have role CONTRIBUTOR, ADMIN , MEMBER (If I have to restart)
  * modified ExceptionHandler for better field response in frontend
  * css - field messages and general messages showing in form if not filled in right

## task 10. Admin Dashboard + Reply form submission
Admin should have a list of all new contributors consent form
Admin should be able to approve or reject the form and send back to contributor.
(12/4, 18/4, 19/4, 21/4, 25/4, 4/5, 5/5, 7/5)

  * Added V3__insert_super_admin.sql so there are always a super admin when the program starts up first time.
  * Added css landing page after login as a super admin

GET - dashboard main 
see list of total contributor forms 
see how many are pending, approved,rejected, not submitted and username

  * added (controller,responseDto+, mapper, service )
  * small change in v1 in db to be able to sort forms in order
  * added endpoint in security config (/admin/dashboard)
  * added very simple frontend
    (missing pagination in backend, should be max 5 and if you want to see all using link)

GET - detailed info about every form and images of documents and their status

(GET "/admin/consent/{id}")
(GET "/admin/consent/{id}/document/{type}")

  * added (responseDTO+, mapper)
  * added WebConfig to be able to get the images
  * added controller for Get consentFormData
  * added a controller for Get id connected to the consentForm data
  * added an extra service for getting the id from form
  * added a fetch to get the images connected to the consent form id
  * added some simple css

Admin should be able to response to the Consent form, approve or reject
PATCH "/admin/consent/{id}/review"

  * added requestDTO, service, controller, security config
  * added fetch in frontend
  * added new status enum for user for more flexibility 
  * added some styling

## task 11 look over and change all old boolean contributor to enum status 
(8/5)
  * update all consent-related endpoints from old boolean isContributor to use new ContributorStatus enum
  * remove old isContributor boolean from db and entities
  * add so that messages from admin get shown in form 
  * when consent form is approved it should disappear and give rome for new component/dashboard.

## task 12 dashboard for uploading PHOTOS
contributor should have a dashboard from where they easily can upload photos from.
  * should be able to publish directly, scheduled or save as draft
  * should be able to add a cover photo
(9/5, 10/5, 13/5, 15/5, 16/5)

  POST ("/contributor/upload/photo")

  ### step 1. create a minimum version. Just simple upload photos DRAFT.
        * added description in db, table photo_albums (so that user can add a text to the photos)
        * created request, response, mapper, service, controller, security config
        * created a fetch, component/form
        * added minimal styling for preview images and dashboard
        * made it more user-friendly, you can add more photos and remove before you post the album
        * made more user-friendly, you can move images around in the preview grid using right and left.
    
  ### step 2. contributor should be able to publish, save as draft, schedule content, cover image.
        * added cover photo to the db & entity
        * remade service 
        * added missing frontend logic
        * npm install lucide-react (for nicer css-styling on buttons right & left)

## Task 12 - display photo albums represented by a CoverPhoto
Pick between draft/published/scheduled sections , show cover photo visible, date and type.
(17/5)

GET ("contributor/albums/list")

  * add responseDTO, Mapper, Service, controller
  * added fetch frontend
  * added minimal styling

## Task 13 - display photo album members
(18/5, 19/5, 20/5)
Member should be able to see cover photos from all album Photo/video
click cover photo to get to the album
contributor should have access to member pages if they have uploaded content.

GET ("/member/albums")
GET ("/member/albums/{albumPublicUuid})

  * added a dummy member V4__insert_member_user-sql
  * added a .env.local in next.js for avoiding in future having to change localhost path
  * added simple css styling
  * added responseDto + mapper + service + controller
  * added member access validation
  * added fetch 
  * added minimal styling

-----------------NOW---------------
## Task 14 - edit saved albums
(21/5, 23/5 -26/5 27/5)
edit text, change cover photo, add/remove photos, reorder photos, change status

  GET contributor/albums/{albumPublicUuid}

  * add service/controller for contributor to get to album from clicking cover photo.
  * add fetch and component

  PATCH contributor/albums/{albumPublicUuid}/title-description

  * add request,response,mapper, service, controller, security config
  * add fetch and component 

  PATCH /contributor/albums/{albumPublicUuid}/cover-photo

  * add request, response, mapper, service, controller, security config
  * add fetch, 

  DELETE /contributor/albums/{albumPublicUuid}/photos

  * added request, service, controller, security config
  * added fetch and added inside edit component

(Validate user, Validate album, Validate ownership, Validate photo,
Validate photo belongs to album, Validate minimum photo count,
Detect if deleted photo is cover, Delete junction row,
Delete photo, Reindex positions, Repair cover photo if needed,
Transaction commits automatically)

  POST /contributor/albums/{albumPublicUuid}/photos

  * added request, service, controller, security config 
  * added fetch and added inside edit component

  PATCH /contributor/albums/{albumPublicUuid}/relocate

  * added request, service, controller, security config

## BREAK WRITE MD FILE ON WORKING PART OF PROJECT 
## Add minimal css all over project to look more sync 

## Task 15 -  Scheduled publisher job
every minute check if anything need to change form scheduled to published

## Task 16 - Make possible to tag photos
  * add tags -general album
  * add tags other contributors

## Task 17 - Archive photos/delete
contributor photos get archived and if not published again they get deleted after 1 month

## task 18 Look over project and clean up folders 

## task 19 - cleanup service

Planned background cleanup system for remove unused files:
* rejected uploads
* replaced files
* deleted contributor content
* banned/deleted users
* orphaned files no longer connected to database records

## task 20 - add a converter, if photos are to big resize before saving
also add in db (to optimaze and make faster to load tumbernails)

private string photoThumbernailPath and save 300px 
privet string photoMediumPath 800px
private string originalPath but max 900px (or something)

## task 21 - Improve upload photos
 add partial success handling, to show what images failed to upload and skipp them

upload valid images
skip broken ones
return list of failed files
add upload to temp folder to prevent that if upload fail a lot of photos will be left in the upload folder.