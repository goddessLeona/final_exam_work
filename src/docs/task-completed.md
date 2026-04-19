
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
* Username on logged in user -  endpoint (@GET /user/username) 

SIGN UP
* SignUp contributor - endpoint (@Post user/signUp-contributor)
* simple Dashboard page for logged in contributors
* Welcome message to contributor - endpoint (@GET "/contributor/welcome")
* Implemented PostgreSQL database schema, V2__init_schema.sql (Roles: CONTRIBUTOR, ADMIN , MEMBER)

CONTRIBUTOR CONSENT FORM
(for login users, to become a active contributor they need to upload 3 documents)
* Contributor Consent form - endpoint (@GET "/contributor/consent")
* Contributor Consent form - endpoint (POST "/contributor/consent")

DASHBOARD ADMIN - HANDLING CONTRIBUTOR CONSENT FORM
* Implemented PostgreSQL database schema, V3__insert_super_admin.sql (A user with role ADMIN)