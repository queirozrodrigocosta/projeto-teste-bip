# README - Projeto teste BIP

## Arquitetura

**Backend**: Java 17, Spring Boot, JPA e H2
**Frontend**: Angular 20
**Banco de Dados**: H2 em memória
**Segurança**: Optimistic Locking com @Version
**Testes**: Testes unitários com JUnit

## Como Executar

### Backend

mvn clean install
mvn spring-boot:run
http://localhost:8080

### Swagger

http://localhost:8080/swagger-ui/index.html

### Frontend

npm install
ng serve ou npm start

http://localhost:4200

## 🔒 Funcionalidades

- CRUD Beneficiários
- Transferências entre Beneficiários
- Validações de transferências

