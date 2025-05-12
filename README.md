### README – API de Consulta de Créditos (Backend)

---

## 🔎 Visão Geral

API RESTful para consultar créditos constituídos a partir de número de NFS-e ou número de crédito.
Implementada em Spring Boot, expõe endpoints:

* **GET** `/api/creditos/{numeroNfse}`
* **GET** `/api/creditos/credito/{numeroCredito}`

Retorna JSON com informações de cada crédito (número, data, valores, tipo, etc.).

---

## 🛠 Stack Tecnológico

* **Java 17**
* **Spring Boot 3.4.5** (Web, Data JPA)
* **Hibernate** (JPA)
* **Flyway** (migrações de esquema)
* **PostgreSQL** 
* **Apache Kafka** (publisher de eventos de consulta)
* **JUnit 5 + Mockito** (testes unitários e integração)
* **Docker** (containerização)

---

## ✔️ Pré-requisitos

* Java 17+ instalado
* Maven 3.6+
* Docker & Docker Compose (se for usar conteinerização)
* Kafka 
* PostgreSQL 15

---

## 🚀 Como Rodar Localmente (sem Docker)

1. **Configurar o banco**

    * Crie um database `creditos`.
    * Ajuste `application.yaml` em `src/main/resources` com URL, usuário e senha.

2. **Executar migrações**

    * Na primeira execução, o Flyway criará tabelas e inserirá os dados iniciais (`V1__create_credito_table.sql`).

3. **Build e Run**

   ```bash
   mvn clean package
   mvn spring-boot:run
   ```

   A API sobe em `http://localhost:8080`.

4. **Testar Endpoints**

    * `GET http://localhost:8080/api/creditos?nfse=7891011`
    * `GET http://localhost:8080/api/creditos/123456`

---

## 🐳 Rodando com Docker Compose

```bash
# na raiz do projeto
docker-compose up --build
```

Isso levantará:

* **db** (PostgreSQL)
* **kafka + zookeeper**
* **api-consulta-credito**

Acesse `http://localhost:8080`.

---

## 📑 Endpoints

| Método | URI                               | Descrição                   |
| ------ | --------------------------------- | --------------------------- |
| GET    | `/api/creditos?nfse={numeroNfse}` | Lista créditos de uma NFS-e |
| GET    | `/api/creditos/{numeroCredito}`   | Detalha crédito específico  |

**Exemplo de resposta (lista):**

```json
[
  {
    "numeroCredito": "123456",
    "numeroNfse": "7891011",
    "dataConstituicao": "2024-02-25",
    "valorIssqn": 1500.75,
    "tipoCredito": "ISSQN",
    "simplesNacional": "Sim",
    "aliquota": 5.0,
    "valorFaturado": 30000.00,
    "valorDeducao": 5000.00,
    "baseCalculo": 25000.00
  }
]
```

---

## 🔄 Migrações de Banco (Flyway)

* Os scripts ficam em `src/main/resources/db/migration`.
* Ao subir a aplicação, o Flyway executa automaticamente as versões na ordem (V1, V2…).

---

## 🧪 Testes

Para rodar testes unitários e de integração:

```bash
mvn test
```

* **Unitários**: `CreditoServiceImplTest` (Mockito)
* **Integração**: `CreditoControllerIntegrationTest` (Spring Boot Test + @AutoConfigureMockMvc)

---

## 📘 Observações Finais

* **Logging**: usa SLF4J; ver `application.yaml` para níveis de log.
* **Mensageria**: sempre que consulta por NFS-e acontece, publica evento no tópico Kafka (configurável).
* **CORS**: padrão liberado para `http://localhost:4200` (frontend Angular).

---

> **Dica**: ajuste variáveis de ambiente (`SPRING_DATASOURCE_URL`, `KAFKA_BOOTSTRAP_SERVERS`, etc.) conforme seu ambiente de produção.
