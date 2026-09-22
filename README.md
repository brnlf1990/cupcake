# Cupcake Store

Projeto Integrador II de Engenharia de Software.

Aplicação Web simples para uma Loja de Cupcakes Gourmet, desenvolvida com Java 21, Spring Boot, Spring MVC, Spring Data JPA, Thymeleaf, Bootstrap 5 e MySQL.

## Requisitos
- Java 21
- Maven 3.9+
- MySQL 8+

## Banco de dados
Crie o banco executando o arquivo `script.sql` ou deixe o Hibernate criar as tabelas automaticamente.

No arquivo `src/main/resources/application.properties`, ajuste usuário e senha do MySQL se necessário.

## Executar
```bash
mvn clean package
mvn spring-boot:run
```

Depois acesse `http://localhost:8080`.

## Observação
O login é propositalmente simples, sem Spring Security e sem JWT, conforme o escopo acadêmico do projeto.
