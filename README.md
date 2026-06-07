## Final exam work for my FullStack developer 2 year eduaction.

In 2024, I started a two-year Fullstack Developer education. I began from zero, and although I have learned a lot during these two years, I still have a long way to go before fully understanding everything in the field.

This project is my final project after two years of learning. I chose it because it gives me the opportunity to continue developing and maintaining the knowledge I have gained, while also helping a non-profit organization that I have been involved with for many years modernize their website and systems.

Before officially starting the project, I created an MVP as part of my exam work. The scope of the MVP became larger than expected compared to the time available, especially since I was also completing my internship during the days and could mainly work on the project during evenings and weekends. There for I was not abale to totaly complite my original MVP plan. 

The original platform I am working from has three main user roles.

The first role is the Contributors, or as the organization prefers to call them, *Activists*. These users provide content to the platform. The second role is the Admin, who is responsible for monitoring content and approving new contributors. The final role is the Members, who pay for access to the content through an external payment system.

The content is only displayed on member pages, which can be accessed by members, admins, and approved contributors. In this project, I have not yet connected the external payment system. Instead, I am currently using dummy member accounts to test and verify that everything works correctly.

<img width="891" height="502" alt="Skärmbild 2026-06-07 083831" src="https://github.com/user-attachments/assets/56010277-71f2-49f3-9654-bfe678a5be71" />

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

### Login
I am using a simple form where the user fill in username and password. Right now there is a expiration time of 1h in the upcoming tasks I will add refresh tolken so that a user can stay loged in for 1 week a time. You now get rederected to login directly after 1h.

<img width="851" height="315" alt="Log in" src="https://github.com/user-attachments/assets/0c50b6e3-9ba3-43de-b78e-dbf5901812c4" />

### Logout
Settings for log out during development.

<img width="851" height="315" alt="Log out" src="https://github.com/user-attachments/assets/fa14a6d0-b018-4deb-9a80-4ea96aba3c4c" />

### Sign up – Contributor role
Users must fill in all required information before gaining access to the contributor features.
The form includes validation on all inputs to reduce incorrect submissions and improve user experience.
After a successful sign up, the user can log in and will be redirected to the contributor dashboard.

<img width="1427" height="957" alt="Skärmbild 2026-06-02 091804" src="https://github.com/user-attachments/assets/ca2cd55b-8b3e-4894-bc7c-6b851904a784" />

### Contributor - Agreement form
This was a brain nut to solve, both how to upload content in the form using filepath and how to make the form work as my MVP had requeired. It took me many evenings of failing and trying before I got it up and running. The css is still not that great but it helps the user understand how to use the form.

#### Overview

The Contributor Agreement Form is a multi-step verification workflow that allows users to submit identity documents, receive admin review feedback, and progressively complete onboarding until full approval.

The system supports partial approvals, meaning individual documents can be approved or rejected independently.

Submitted → Under Review → Rejected → Resubmitted → Approved

#### Simple explanation how it works for a user:
First time a new user with role contributor log into the page. They will get a welcome message explaining what the user have to do to do next. 
The Contributor Agreement Form is shown below it, where user can upload 3 independent documents and agree to rules. To be able to submit the form the user have to upload all documenst and agree to rules. 
status: NOT SUBMITED

<div>
<img width="666" height="219" alt="wellcome" src="https://github.com/user-attachments/assets/8b2574b7-fa04-46a0-baf9-83b4b1baacc3" />
<img width="745" height="854" alt="Skärmbild 2026-06-07 085434" src="https://github.com/user-attachments/assets/806ea738-70fa-49e6-99b0-bfa40daee3fa" />
</div>

After the form is filled in and sent away, the welcome message will change. 
status: PENDING

<img width="674" height="86" alt="pending" src="https://github.com/user-attachments/assets/f4d316c8-40ad-4fd9-864b-baf4b6c3311a" />

Admin will then look on all the documents and approve or rejct the documents. Admin can aprove 1 document and reject 2 documents. 
The user then get back the form showing what document got approved and what document got rejected and way it got rejected. 
The message on top also explain for user what to do next.
status: REJECTED

<img width="671" height="98" alt="reject" src="https://github.com/user-attachments/assets/16d91990-50d6-412b-87fb-2865cc475690" />
<img width="727" height="358" alt="Skärmbild 2026-06-07 090714" src="https://github.com/user-attachments/assets/8ba8f8c0-9a2e-4510-a162-67cd2331ab5d" />

User can then upload the missing document. and send away to admin again. Admin check that document and approve or reject. If admin have approved all the document.
The agreementform will disepair and a new dashboard for the user will take the place.

### Contributor - Upload content 
A user can upload photos in a simple form, in the future the user should be able to use the same form to upload video but did not get that far. But database is already set up for it. 

<img width="885" height="951" alt="Skärmbild 2026-06-07 095007" src="https://github.com/user-attachments/assets/9fa985a5-061c-441c-b0a2-2c7128d22e66" />

### Contributor - Edit album
Building the album editing functionality was one of the biggest challenges in the project. Contributors can update album titles and descriptions, reorder photos, change cover photos, add or remove images, and manage album status.

An album can move between several states:

* Draft
* Published
* Scheduled for future publishing
* Archived

To support all editing features for a single album, I ended up creating seven different endpoints.  
I chose to split the functionality across multiple endpoints because different parts of the album are updated independently of each other. This made the code easier to maintain and reduced the risk of unintended changes.

### Admin - Respond to Contributor form
Admin consent-dashboard show a list of all pending forms, rejected forms, and aproved forms showing te username, how many of the documents are pending, rejected and approved. If a form is pending the admin can clikc the link and get to the documents. The document that is pending the admin now can approve or reject.
If a document is rejected, the admin can provide feedback explaining why.

### Member - See all published photos
Members can browse published photo albums uploaded by contributors. The application supports viewing album covers, opening albums, and displaying published content in a structured way. I wanted to add the search for tags and search for specefic contributor for members as well but as my time was not enough to get that part finish. A member can now only see all users content as a list represented by a cover photo. Cklicking the cover photo you get to the album and klicking on a image you see the images big and can move to next or back. 

<img width="1224" height="474" alt="Skärmbild 2026-06-07 094345" src="https://github.com/user-attachments/assets/b7a094bc-f199-43da-a234-1a17cd29de00" />

### End words
I am not fully satisfied with the final result because I originally hoped to complete the entire MVP before the exam. I underestimated both the complexity of the project and the amount of time required to build something properly. I also had limited time available each day, often only working in short sessions.

Even though I did not finish everything I planned, I learned an enormous amount during the process. During the final week, I stopped adding major functionality and instead focused on improving the structure of the project:

* reorganizing folders,
* renaming files,
* fixing bugs,
* improving fetch handling,
* and making components follow a more consistent pattern.

One important lesson was realizing how to use Next.js better. Early in the project, I did not fully understand how to use its features properly, so later I had to revisit many earlier decisions and refactor large parts of the codebase.

What I learned from that is that if you want it realy good it goin to take time. You have to go back and improve all them time. You move forward and you lean new things, then you have to go back and implement it in the things you have done before. That some times set off a chain effect and thigs that worked before are no longer working. How many times have I not have paniced about a small changed thats et of a domino chain that made everything stop working and my brain expode. 

The page now look a bit like traveling circus many colors all over the place. I did not realy have time to decide color cheamas. A problem for the future. Just wanted somthing to show on the presentation. 

Even if the project is incomplete, I am proud of how much I learned throughout the process.





