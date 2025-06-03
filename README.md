Projeto Final - API E-commerce
Este projeto é uma API RESTful para um sistema de e-commerce, desenvolvida como trabalho final da disciplina de Desenvolvimento de APIs do Serratec – Residência em TIC - Software.

A aplicação permite o gerenciamento de produtos, categorias, usuários e pedidos, oferecendo endpoints para operações CRUD, autenticação de usuários e documentação Swagger.

Grupo 5 - Colaboradores
Arthur dos Santos Gomes

Bruno Ventura Gross

Daniel Lopes

Iara Coutinho

Laryssa Peixoto

Victor da Silva Campos

Tecnologias Utilizadas
Java 17

Spring Boot

Spring Data JPA

H2 Database (banco de dados em memória)

Maven

Swagger/OpenAPI (documentação da API)

Postman (coleção de testes)

Estrutura do Projeto
css
Copiar
Editar
projeto-final-api-ecommerce/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── camposvictor/
│   │   │           └── ecommerce/
│   │   │               ├── controllers/
│   │   │               ├── models/
│   │   │               ├── repositories/
│   │   │               └── services/
│   └── resources/
│       └── application.properties
├── pom.xml
├── README.md
└── API Restful 202501.postman_collection.json
⚙️ Como Executar o Projeto
Clone o repositório:

bash
Copiar
Editar
git clone https://github.com/CamposVictor8991/projeto-final-api-ecommerce.git
cd projeto-final-api-ecommerce
Compile com Maven:

bash
Copiar
Editar
./mvnw clean install
Execute a aplicação:

bash
Copiar
Editar
./mvnw spring-boot:run
Acesse no navegador:

http://localhost:8080

Documentação Swagger:

http://localhost:8080/swagger-ui.html

Principais Endpoints
GET /produtos – Lista produtos

POST /produtos – Cria produto

GET /categorias – Lista categorias

POST /categorias – Cria categoria

POST /usuarios – Cadastra usuário

POST /auth/login – Login com JWT

GET /pedidos – Lista pedidos

POST /pedidos – Cria pedido

Testes com Postman
Utilize o arquivo API Restful 202501.postman_collection.json para testar os endpoints no Postman.

Licença
Este projeto é de uso educacional e está disponível sob a licença MIT.
