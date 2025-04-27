# API - Barbearia

### 📦 Agendamentos

| Método | Rota                                                 | Acesso   | Descrição                    |
|--------|------------------------------------------------------|----------|------------------------------|
| GET    | /agendamentos                                        | Admin    | Lista todos os agendamentos |
| GET    | /agendamentos/agendamentos-dia?data=yyyy-MM-dd       | Admin    | Lista agendamentos do dia   |
| POST   | /agendamentos                                        | Público  | Cria novo agendamento       |
| DELETE | /agendamentos/{id}                                   | Admin    | Exclui um agendamento       |

### 🕑 Horários Disponíveis

| Método | Rota                                                           | Acesso  | Descrição                    |
|--------|----------------------------------------------------------------|---------|------------------------------|
| GET    | /agendamentos/horarios-disponiveis?data=yyyy-MM-dd            | Público | Lista horários livres do dia |

---

### ✅ Acesso Admin

| Usuário | Senha   |
|---------|---------|
| admin   | 123456  |

Use autenticação **Basic Auth** nas requisições protegidas.