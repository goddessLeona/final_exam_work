## Final exam work for my FullStack developer 2 year eduaction.

In 2024, I started a two-year Fullstack Developer education. I began from zero, and although I have learned a lot during these two years, I still have a long way to go before fully understanding everything in the field.

This project is my final project after two years of learning. I chose it because it gives me the opportunity to continue developing and maintaining the knowledge I have gained, while also helping a non-profit organization that I have been involved with for many years modernize their website and systems.

Before officially starting the project, I created an MVP as part of my exam work. The scope of the MVP became larger than expected compared to the time available, especially since I was also completing my internship during the days and could mainly work on the project during evenings and weekends. There for I was not abale to totaly complite my original MVP plan. 

The original platform I am working from has three main user roles.

The first role is the Contributors, or as the organization prefers to call them, *Activists*. These users provide content to the platform. The second role is the Admin, who is responsible for monitoring content and approving new contributors. The final role is the Members, who pay for access to the content through an external payment system.

The content is only displayed on member pages, which can be accessed by members, admins, and approved contributors. In this project, I have not yet connected the external payment system. Instead, I am currently using dummy member accounts to test and verify that everything works correctly.

One of the most urgent improvements needed on the original platform was the process for new contributors to join. Previously, people had to send emails to the organization, which could easily get lost among other emails or spam. Creating an application form directly on the website was therefore a major improvement.

This also introduced important questions about security, especially regarding how identification documents should be uploaded and protected from unauthorized access. During this project, I learned many new things, such as how to use file paths for uploaded content instead of storing files directly in the database, and how to protect those file paths. Security is still something I plan to continue improving.

For ID documents, I currently added role-based security so that only administrators are allowed to access ID documents through secured endpoints. I am planning to further improve security by including encrypted storage for sensitive documents and temporary access links that automatically expire after a limited time. 

`requestMatchers("/uploads/**").authenticated()`
`requestMatchers("/admin/consent/{id}/document/{type}").hasRole("ADMIN")`

Another feature the organization wanted improved was the ability to search for specific content using tags and contributors. This is the next planned part of the project, but unfortunately I did not manage to complete it in time. A large amount of development time instead went into improving content uploads, content editing functionality, and many smaller details that took longer than expected.

I will now walk through the project I have built so far, step by step. It has been a very educational and rewarding journey, and I plan to continue developing the platform until it is approved and ready for real-world use.

At the moment, this is only a demo version, so the original website’s content has not yet been transferred over. The content will only be migrated once the platform is fully functional and secure.

## Tech Stack

### Backend

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* Flyway (database migrations)

### Frontend

* Next.js
* React
* TypeScript

### Databas

* PostgreSql

### Libraries and Tools

* Lucide React (icons)

## Getting started
When I started the project, I began by drawing my ERD to plan how my database should be structured to support my planned MVP. It took me around two weeks before I was satisfied with the design. I ended up with 13 tables, where 7 of them were junction tables, so it was really a puzzle to get everything connected correctly. Even then, I still had to make some changes as the project progressed.

I chose PostgreSQL as my database and Flyway for database migrations. It took me some time to understand how Flyway and migrations worked, but once I learned it, I really enjoyed using it.

For the frontend, I decided to go with Next.js and TypeScript. I had not worked with either of them before, my last project was built with React only. I did some research and chose Next.js for this project since it supports more role-based features and makes it easier to structure pages.

What I also found helpful was how much clearer it became to organize the project between pages and components. Even so, it still took time to get comfortable with it, and with each component I created, I learned something new.

After setting up the database, simple frontend landing page I continued working on the login, logout, and signup functionality.



