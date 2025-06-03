# Trinity E-commerce - Servidor

Este é o servidor (back-end) do projeto Trinity E-commerce, uma plataforma de compras online de jogos de cartas e tabuleiro.

## Tecnologias Utilizadas

- Spring Boot 3.4.4
- Spring Data JPA
- Spring Security
- JWT para autenticação
- H2 Database (para desenvolvimento e testes)
- JUnit e Spring Test para testes automatizados

## Estrutura do Projeto

O projeto segue a arquitetura em camadas:

- **model**: Entidades JPA que representam as tabelas do banco de dados
- **repository**: Interfaces para acesso aos dados
- **service**: Camada de serviços com a lógica de negócio
- **controller**: Controladores REST que expõem os endpoints da API
- **dto**: Objetos de transferência de dados
- **security**: Classes relacionadas à autenticação e autorização

## Endpoints Implementados

### Usuários
- `POST /users` - Cadastro de usuário
- `GET /users` - Listar todos os usuários
- `GET /users/{id}` - Buscar usuário por ID
- `PUT /users/{id}` - Atualizar usuário
- `DELETE /users/{id}` - Remover usuário

### Categorias
- `POST /categorias` - Cadastrar categoria
- `GET /categorias` - Listar todas as categorias
- `GET /categorias/{id}` - Buscar categoria por ID
- `PUT /categorias/{id}` - Atualizar categoria
- `DELETE /categorias/{id}` - Remover categoria

### Produtos
- `POST /produtos` - Cadastrar produto
- `GET /produtos` - Listar todos os produtos
- `GET /produtos/{id}` - Buscar produto por ID
- `GET /produtos/categoria/{categoriaId}` - Listar produtos por categoria
- `PUT /produtos/{id}` - Atualizar produto
- `DELETE /produtos/{id}` - Remover produto

### Endereços
- `POST /enderecos` - Cadastrar endereço
- `GET /enderecos` - Listar todos os endereços
- `GET /enderecos/{id}` - Buscar endereço por ID
- `GET /enderecos/usuario/{usuarioId}` - Listar endereços por usuário
- `PUT /enderecos/{id}` - Atualizar endereço
- `DELETE /enderecos/{id}` - Remover endereço

### Pedidos
- `POST /pedidos` - Cadastrar pedido
- `GET /pedidos` - Listar todos os pedidos
- `GET /pedidos/{id}` - Buscar pedido por ID
- `GET /pedidos/usuario/{usuarioId}` - Listar pedidos por usuário
- `PUT /pedidos/{id}` - Atualizar pedido
- `DELETE /pedidos/{id}` - Remover pedido

### Itens de Pedido
- `POST /itens-pedido` - Cadastrar item de pedido
- `GET /itens-pedido` - Listar todos os itens de pedido
- `GET /itens-pedido/{id}` - Buscar item de pedido por ID
- `GET /itens-pedido/pedido/{pedidoId}` - Listar itens por pedido
- `PUT /itens-pedido/{id}` - Atualizar item de pedido
- `DELETE /itens-pedido/{id}` - Remover item de pedido

## Como Executar

1. Clone o repositório
2. Navegue até a pasta do projeto
3. Execute `./mvnw spring-boot:run` (Linux/Mac) ou `mvnw.cmd spring-boot:run` (Windows)
4. A API estará disponível em `http://localhost:8080`

## Dados de Exemplo

O arquivo `import.sql` contém dados iniciais para teste, incluindo:
- Categorias de produtos
- Usuários (admin/cliente)
- Produtos
- Endereços

## Testes

Foram implementados testes automatizados para os principais endpoints da API. Para executar os testes:

```
./mvnw test
```

## Próximos Passos

Para a próxima fase do projeto, será necessário:
1. Implementar o cliente React
2. Integrar o cliente com a API
3. Implementar recursos adicionais como filtros avançados e paginação
