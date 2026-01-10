
# MVP Project Plan

## Overview

A **member-based platform** for members and contributors.

### Roles & Access

* **Contributors**

    * Upload content from their dashboard to member pages
    * Access member pages
* **Members**

    * Access member pages only
* **Admin**

    * Approves contributors
    * Oversees content compliance

---

## MVP Requirements

### Content & Access

* Contributors can upload content from their dashboard pages to the member pages
* Contributors also have access to member pages
* Members only have access to member pages
* Contributors must be approved by an admin before gaining upload access
* If uploaded content includes more than one contributor:

    * The content should appear on each relevant contributor’s dashboard
    * Any contributor associated with the content should be able to remove it from their dashboard

### Discovery & Search

* Members can search for content by specific contributor
* Members can search for content by tags (e.g. vacation, year, nature)

---

## Nice-to-Have Features (Post-MVP)

* Contributors earn points for uploaded content over a yearly period
* Video uploads for contributors
* Extended public profile information via form (gender, age, hobbies, social media links, etc.)
* Admin view for recently published content with moderation checks
* Contributors can select content to display on free/public pages
* Social media feed integration on member pages
* Ability to like photos
* Ability to comment on photos
* Members can save or favorite content

---

## Technical Stack

* **Backend:** Java Spring Boot
* **Authentication:** Spring Security (JWT)
* **Database:** PostgreSQL
* **Frontend:** React
* **Database Versioning:** Flyway

---

## User Roles (MVP)

* Approved Contributors
* Members
* Admin

---

## Core MVP Features

* User authentication and role-based access control
* Admin approval workflow for contributors
* Contributor dashboard for content upload and management
* Member pages for content display
* Search by contributor
* Search by tags
* Shared content across multiple contributors

---

## Time Plan – MVP Development

The MVP will be developed over three phases during **LIA 1**, **LIA 2**, and the **final exam period**, with a total planned workload of approximately **200–320 hours**.

---

### LIA 1

**5 January – 13 February**<br>
**8 hours/week**<br>
**≈ 48 hours**<br>

**Focus:**

* Database setup using PostgreSQL
* Flyway integration
* Backend development with Spring Boot
* Frontend setup using React
* User authentication (sign-in / sign-out)
* Spring Security with JWT

---

### LIA 2

**16 February – 29 May**<br>
**8–16 hours/week**<br>
**≈ 120–240 hours**<br>

**Focus:**

* Core MVP functionality
* Contributor dashboards
* Member pages

---

### Final Exam Period

**1 June – 8 June**<br>
**≈ 30 hours**

**Focus:**

* Finalization of the MVP
* Completion of remaining core functionality
* Documentation
* Implementation of selected nice-to-have features if time permits
