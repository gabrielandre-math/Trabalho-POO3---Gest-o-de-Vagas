
# Trabalho POO3 – Gestão de Vagas

**Disciplina:** POO3  
**Professor:** Fábio Luís Guia da Conceição  
**Integrantes:**  
- Gabriel André  
- Douglas Alexsander  
- Crystian Lefundes  

---

## 📖 Visão Geral

**Gestão de Vagas** é uma API REST em Java 17 com Spring Boot que permite:

- **Empresas** se registrarem, autenticarem (JWT) e publicarem vagas.  
- **Candidatos** se registrarem, autenticarem (JWT), pesquisarem vagas e se candidatarem.  
- Monitoramento de saúde via **Actuator**.  
- Documentação interativa via **Swagger UI**.

---

## 🏛️ Arquitetura

Este projeto adota principais características de **Arquitetura em Camadas** (Ports & Adapters):

```text
┌────────────────────────────┐
│   Adapters Inbound (API)   │
│ ┌─ Controllers (REST) ────┐│
└─┤                         ├┘
  │   Application Layer      │
  │ ┌─ UseCases / Services ─┐│
  │ └───────────────────────┘│
  │       Domain Model       │
  │ ┌─ Entities & DTOs ─────┐│
  │ └───────────────────────┘│
  │ Adapters Outbound (DB)   │
  │ ┌─ Repositories (JPA) ──┐│
  │ └───────────────────────┘│
  └──────────────────────────┘
  + Security Filters  
  + JWT Providers
````

### 🗺️ Diagrama Detalhado

![Diagrama Arquitetura](https://github.com/user-attachments/assets/ac6ea531-e267-40b7-acd8-354fdcc82b0f)

* **Controllers** recebem e validam requisições HTTP.
* **UseCases** implementam casos de uso puros, desacoplados de frameworks.
* **Entities/DTOs** contêm regras de domínio mínimas.
* **Repositories** abstraem acesso ao banco (PostgreSQL/H2).
* **Segurança** via JWT + **RBAC** (Roles: `CANDIDATE`, `COMPANY`) usando filtros `SecurityCandidateFilter` / `SecurityCompanyFilter`.

---

## 🚀 Funcionalidades

### Empresa

* **POST** `/company/register` – cadastrar nova empresa
* **POST** `/company/auth`     – autenticar e receber JWT
* **POST** `/company/job/new-job` – publicar vaga (autenticado)

### Candidato

* **POST** `/candidate/register` – cadastrar novo candidato
* **POST** `/candidate/auth`     – autenticar e receber JWT
* **GET**  `/candidate/job?filter={termo}` – buscar vagas
* **GET**  `/candidate/me`      – perfil do candidato (autenticado)
* **POST** `/candidate/job/apply` – candidatar-se (autenticado)

### Infra & Health

* **GET** `/actuator/health` – estado da aplicação
* **Swagger UI** em `/swagger-ui/index.html`
* **OpenAPI spec** em `/v3/api-docs`

---

## 📦 Tecnologias

* Java 17
* Spring Boot (Web, Data JPA, Security, Actuator)
* JWT (Auth0)
* PostgreSQL (produção) / H2 (testes)
* Maven
* GitHub Actions (CI)
* Swagger / OpenAPI

---

## 🛠️ Instalação & Execução

1. **Clone**

   ```bash
   git clone https://github.com/gandredev/gestao_vagas.git
   cd gestao_vagas
   ```

2. **Configurar** `src/main/resources/application.yml`

   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/gestao_vagas
       username: admin
       password: admin
     jpa:
       hibernate:
         ddl-auto: update

   security:
     token:
       secret:           gestaovagas@123#
       secret.candidate: gestaovagas@123#candidate
   ```

3. **Rodar localmente**

   ```bash
   mvn clean spring-boot:run
   ```

4. **Docker (opcional)**

   ```bash
   docker build -t gandredev/gestao_vagas:0.1 .
   docker-compose up -d
   ```

A API ficará disponível em `http://localhost:8080`.

---

## 📄 Documentação

* **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
* **OpenAPI spec**: `http://localhost:8080/v3/api-docs`

---

## ✅ Testes & CI

* **Profile** `test` usa H2 em memória (`create-drop`).
* **Testes** com JUnit 5, Mockito e AssertJ.
* **GitHub Actions**: pipeline `mvn clean verify -Dstyle.color=always` em cada push.

---

## 👥 Contribuidores

<table>
  <tr>
    <td align="center">
      <a href="https://www.linkedin.com/in/gabrielandredev/" target="_blank" rel="noopener noreferrer">
        <img
          src="https://media.licdn.com/dms/image/v2/D4D03AQHRJ5NU33UGjA/profile-displayphoto-shrink_800_800/B4DZUCNjwYG4Ac-/0/1739498864456?e=1752105600&v=beta&t=PhH0bZqYkvwO3tvn9sYWiZA6lzAzMxQ9RjSswrf0i5I"
          width="100"
          alt="Gabriel André"
        /><br/>
        <strong>Gabriel André</strong>
      </a><br/>
      Desenvolvedor Full-Stack & UI/UX
    </td>
    <td align="center">
      <a href="https://www.linkedin.com/in/alexferreira92/" target="_blank" rel="noopener noreferrer">
        <img
          src="https://media.licdn.com/dms/image/v2/D4D03AQG41SYtO7ZAYg/profile-displayphoto-shrink_800_800/profile-displayphoto-shrink_800_800/0/1693257952627?e=1752105600&v=beta&t=sfMNw8hUjfir6oHWtz9v6EkHx3-OC0f9sMoB7XPMuO4"
          width="100"
          alt="Douglas Alexsander"
        /><br/>
        <strong>Douglas Alexsander</strong>
      </a><br/>
      Desenvolvedor Full-Stack
    </td>
    <td align="center">
      <a href="https://www.linkedin.com/in/crystian-lefundes/" target="_blank" rel="noopener noreferrer">
        <img
          src="https://avatars.githubusercontent.com/u/65630225?v=4"
          width="100"
          alt="Crystian Lefundes"
        /><br/>
        <strong>Crystian Lefundes</strong>
      </a><br/>
      DevOps & Security
    </td>
  </tr>
</table>


> Clique no nome ou na foto para visitar o perfil LinkedIn de cada contribuinte.

---

## 🤝 Contribuições

1. Fork este repositório.
2. Crie uma branch (`git checkout -b feature/nome`).
3. Commit suas mudanças.
4. Abra um Pull Request.

---

## 📜 Licença

Uso acadêmico para POO3 – Prof. Fábio Luís Guia da Conceição.

*Desenvolvido por Gabriel André, Douglas Alexsander e Christian.*
