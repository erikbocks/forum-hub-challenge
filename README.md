# ForumHub do Bock

Forum Hub é um desafio criado pela Alura, que busca consolidar os conhecimentos de boas práticas e padrões de aplicação adquiridos na formação de Spring Boot. O objetivo é criar uma API REST que implementa as quatro operações de um CRUD, tendo o foco principal na criação de tópicos com requests utilizando os verbos HTTP.

## Tecnologias e Técnicas utilizadas

- ``Java 17``
- ``Maven 3``
- ``MySQL``
- ``Flyway``
- ``Docker``
- ``Lombok``
- ``Spring Security``
- ``Java JWT``
- ``Spring Data JPA``
- ``Hibernate``

## Funcionalidades

- Operações **CRUD(CREATE, READ, UPDATE, DELETE)**.
- **Validações** conforme regras de negócio.
- Persistência de dados.
- Controle de acesso e **Autenticação** via token.

## Como usar

O arquivo **compose** do projeto cria dois containers: **database e forum-hub-app**. Você pode escolher executar apenas um deles ou os dois juntos. 

1 - Clone o projeto para a sua máquina.

2 - Abra o projeto com a sua IDE de preferência;

3 - Para executar tanto a aplicação, quanto o banco de dados, digite: ``docker compose up -d``

4 - Caso queira rodar apenas o banco de dados, digite: ``docker compose up database -d``. **Leia o passo 5!!**

5 - Se escolheu essa opção, é necessário configurar as variáveis de ambiente da IDE com o arquivo environment.env

6 - Agora, é necessário executar o método main da classe ForumHubApplication.