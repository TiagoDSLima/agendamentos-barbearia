# API - Barbearia

### Agendamentos

| Método | Rota                                                               | Acesso   | Descrição                                                             |
|--------|--------------------------------------------------------------------|----------|-----------------------------------------------------------------------|
| GET    | /agendamentos                                                      | Admin    | Lista todos os agendamentos                                           |
| GET    | /agendamentos/agendamentos-dia?data=yyyy-MM-dd                     | Admin    | Lista agendamentos do dia                                             |
| GET    | /agendamentos/horarios-disponiveis?data=data=yyyy-MM-dd&servico=id | Admin    | Lista horários disponíveis na data e de acordo com o serviço escolhido|
| POST   | /agendamentos                                                      | Público  | Cria novo agendamento                                                 |
| DELETE | /agendamentos/{id}                                                 | Admin    | Exclui um agendamento                                                 |

---

### Autenticação Usando Spring Security e JWT

 Método   | Rota                       | Acesso   | Descrição                                  |
|---------|----------------------------|----------|--------------------------------------------|
| POST    | /auth/login                | Público  | Loga para a geração do token               |
| POST    | /auth/register             | Público  | Cria um usuário com senha criptografada    |

---

### Clientes

 Método   | Rota                       | Acesso   | Descrição                                  |
|---------|----------------------------|----------|--------------------------------------------|
| POST    | /clientes                  | Público  | Cria um cliente                            |
| GET     | /clientes                  | Admin    | Retorna todos os clientes                  |

---

### Serviços

 Método   | Rota                       | Acesso   | Descrição                                  |
|---------|----------------------------|----------|--------------------------------------------|
| GET     | /servicos                  | Público  | Retorna todos os serviços                  |



Use autenticação **Bearer Token** nas requisições protegidas.

Tecnologias utilizadas: ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)&nbsp;
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)&nbsp;
![Git](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)&nbsp;
![JWT](https://img.shields.io/badge/JWT-black?style=plastic&logo=JSON%20web%20tokens)&nbsp;
