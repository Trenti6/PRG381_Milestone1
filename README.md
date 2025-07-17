# PRG381_Project_Milestone1 WebApp_Group-S5
## Group Members:
- Trent Evans (M1 - backend)
- Matthew Smith (M1 - frontend)
- Gito Martin (M2 - backend)
- Morne van Rooyen (M2 - frontend)

### Can find Milestone 2 -> [Here](https://github.com/MornevanRooyen/PRG381-Project-Group_S-5)

## Student Wellness Management System WebApp

This application allows users to register, log in, and manage user sessions for a student wellness platform.

---

## Technology Used

| Technology       | Description                         |
|------------------|-------------------------------------|
| Java             | Backend programming langauge used   |
| JavaScript (JS)  | Client-side scripting               |
| JSP              | Java Server Pages for views         |
| HTML             | Markup language for structure       |
| CSS              | Styling and layout                  |
| Maven            | Project build and dependency tool   |
| Tomcat           | Servlet container and web server    |
| PostgreSQL       | DBMS used to store user information |

---

## Configuration Notes

To run the application 100% correctly, you **must** configure the following:

- Create a `config.properties` file inside the `WEB-INF` directory with your Gmail email and app password for the email system to work.
- Ensure your PostgreSQL database is properly set up.

### Example `config.properties` content:

```properties
EMAIL_USER=example@gmail.com
EMAIL_PASS=your_app_password
```
### SQL Statement for `users` table:
```sql
CREATE TABLE users
(
    student_number character varying(20) NOT NULL,
    name character varying(50) NOT NULL,
    surname character varying(50) NOT NULL,
    email character varying(100) NOT NULL,
    phone character varying(15) NOT NULL,
    password text NOT NULL,
    profile_pic bytea,
    CONSTRAINT users_pkey PRIMARY KEY (student_number),
    CONSTRAINT users_email_key UNIQUE (email)
);

```
---