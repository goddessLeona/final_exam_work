
## DONE

START UP A NEW PROJECT
* Initialized a new project and connected it to GitHub
* Configured `.gitattributes` to support development across different operating systems
* Added basic code style checks via `checkstyle.xml`

CREATE DATABASE AND ENTITIES
* Implemented PostgreSQL database schema, V1__init_schema.sql using Flyway (MVP)
* Created initial entities

GET FRONTEND WORKING
* Installed Next.js and made a simple landing page, header, nav, footer, contributor page

LOGIN & LOGOUT
* LogIn using JWT-token & HttpOnly Cookies
* Logout
* Username on logged in user -  endpoint (GET "/user/username") 

SIGN UP
* SignUp contributor - endpoint (POST "user/signUp-contributor")
* simple Dashboard page for logged in contributors
* Welcome message to contributor - endpoint (GET "/contributor/welcome")
* Implemented PostgreSQL database schema, V2__init_schema.sql (Roles: CONTRIBUTOR, ADMIN , MEMBER)

CONTRIBUTOR CONSENT FORM
(for login users, to become an active contributor they need to upload 3 documents)

* Contributor Consent form - endpoint (GET "/contributor/consent")
* Contributor Consent form - endpoint (POST "/contributor/consent")

DASHBOARD ADMIN - HANDLING CONTRIBUTOR CONSENT FORM
(admin should be able to respond to the form filled in by contributor)

* Implemented PostgreSQL database schema, V3__insert_super_admin.sql (A user with role ADMIN)

* Admin Dashboard (GET "/admin/dashboard")
  Displays:
  Latest submissions with username (max 5 showing)
  Total number of consent forms (6)
  Counts per status:
  Pending (1)
  Approved (3)
  Rejected (2)

* Consent Form Details (GET "/admin/consent/{id}")
  View detailed information for a specific consent form:
  Username
  Document images:
  ID Card
  ID Face
  FFF + Face
  Review status per document

* Document Image Handling (GET "/admin/consent/{id}/document/{type}")
  Images are stored as file paths in DB
  Served securely via backend endpoint
  




