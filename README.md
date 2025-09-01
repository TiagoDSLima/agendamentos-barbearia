# API - Barber Shop

### Appointments

| Method | Route                                                              | Access   | Description                                                                 |
|--------|--------------------------------------------------------------------|----------|-----------------------------------------------------------------------------|
| GET    | /agendamentos                                                      | Admin    | Lists all appointments                                                      |
| GET    | /agendamentos/agendamentos-dia?data=yyyy-MM-dd                     | Admin    | Lists appointments of the day                                               |
| GET    | /agendamentos/horarios-disponiveis?data=yyyy-MM-dd&servico=id      | Admin    | Lists available times on the date and according to the selected service     |
| POST   | /agendamentos                                                      | Public   | Creates a new appointment                                                   |
| DELETE | /agendamentos/{id}                                                 | Admin    | Deletes an appointment                                                      |

---

### Authentication Using Spring Security and JWT

| Method | Route                       | Access   | Description                               |
|--------|-----------------------------|----------|-------------------------------------------|
| POST   | /auth/login                 | Public   | Logs in to generate the token              |
| POST   | /auth/register              | Public   | Creates a user with an encrypted password |

---

### Clients

| Method | Route                       | Access   | Description                               |
|--------|-----------------------------|----------|-------------------------------------------|
| POST   | /clientes                   | Public   | Creates a client                          |
| GET    | /clientes                   | Admin    | Returns all clients                       |

---

### Services

| Method | Route                       | Access   | Description                               |
|--------|-----------------------------|----------|-------------------------------------------|
| GET    | /servicos                   | Public   | Returns all services                      |

---

Use **Bearer Token** authentication in protected requests.

**Technologies Used**
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)&nbsp;
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)&nbsp;
![Spring Data](https://img.shields.io/badge/Spring_data_jpa-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white)&nbsp;
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)&nbsp;
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white)&nbsp;
![Git](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)&nbsp;
![JWT](https://img.shields.io/badge/JWT-black?style=plastic&logo=JSON%20web%20tokens)&nbsp;
