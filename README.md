# Sistema de Gestão de Reservas de Hotéis 🏨

Este projeto é um sistema de linha de comando para gestão de reservas de hotéis. Desenvolvido em Java com o framework Spring Boot, o sistema permite cadastrar clientes, visualizar hotéis disponíveis, realizar reservas de quartos e consultar reservas existentes, tudo com tratamento de exceções e validações para garantir uma experiência amigável ao usuário.

## Funcionalidades Principais 🚀

- **Visualizar Hotéis Disponíveis**: Lista todos os hotéis com quartos disponíveis para reserva, exibindo informações sobre a localização e o número de quartos livres.
- **Cadastrar Cliente**: Permite o cadastro de clientes com validações no nome (apenas letras e no mínimo 3 caracteres) e CPF (11 dígitos numéricos).
- **Realizar Reserva**: O cliente pode escolher um hotel e o tipo de quarto, informando as datas de início e fim da reserva.
- **Consultar Reservas**: Exibe as reservas feitas por um cliente específico, identificado pelo CPF.
## Demonstração em Vídeo 📹

https://github.com/user-attachments/assets/6ce1ea94-9f10-49a0-be20-011384f0763a

## Tecnologias Utilizadas 🛠️

- **Java 21**: Linguagem principal do projeto.
- **Spring Boot 3.2.11**: Framework para criação e configuração da aplicação.
- **Spring Data JPA**: Para mapeamento objeto-relacional e persistência de dados.
- **Lombok**: Biblioteca para reduzir o código boilerplate com anotações, como getters, setters e construtores.
- **MySQL**: Banco de dados utilizado em tempo de execução para persistência de dados.
- **Spring Boot Starter Web**: Para criar APIs RESTful e facilitar a configuração de servidores web.
- **Spring Boot Starter Test**: Para testar a aplicação com o suporte a ferramentas como JUnit e Mockito.

## Estrutura do Projeto 📂

O projeto está organizado em pacotes que facilitam a manutenção e a escalabilidade:

- **enums**: Contém enumeradores que representam o estado de disponibilidade dos quartos e o tipo dos quartos (`Disponibilidade`, `TiposQuarto`).
- **exceptions**: Define exceções personalizadas para tratamento de erros específicos, como `ClienteNaoEncontrado`, `HotelNaoEncontrado` e `QuartoIndisponivel`.
- **model**: Contém as entidades principais do sistema (`Cliente`, `Hotel`, `Quarto`, `Reserva`), que são mapeadas para o banco de dados.
- **repository**: Contém interfaces de repositório para interação com o banco de dados.
- **service**: Implementa a lógica de negócios do sistema, como criação de reservas e busca de reservas por cliente.
- **InitialDataLoader**: Classe para inicializar o banco de dados com dados de exemplo de hotéis e quartos.
- **Menu**: Classe principal de interação com o usuário através de um menu de console.

## Estrutura de Visualização do Projeto 📂
```plaintext
desafioHotel/
├── src/
│   └── main/
│       └── java/
│           └── desafio/Inc/desafioHotel/
│               ├── enums/
│               │   ├── Disponibilidade.java
│               │   └── TiposQuarto.java
│               ├── exceptions/
│               │   ├── ClienteNaoEncontrado.java
│               │   ├── HotelNaoEncontrado.java
│               │   └── QuartoIndisponivel.java
│               ├── model/
│               │   ├── Cliente.java
│               │   ├── Hotel.java
│               │   ├── Quarto.java
│               │   └── Reserva.java
│               ├── repository/
│               │   ├── ClienteRepository.java
│               │   ├── HotelRepository.java
│               │   ├── QuartoRepository.java
│               │   └── ReservaRepository.java
│               ├── service/
│               │   └── ReservaService.java
│               ├── DesafioHotelApplication.java
│               ├── InitialDataLoader.java
│               └── Menu.java
└── pom.xml
```
## Requisitos de Instalação 📋

Certifique-se de ter o seguinte instalado em sua máquina:
- Java 11+ (ou uma versão compatível especificada no projeto)
- Maven

## Executando o Projeto ▶️

### Clone o Repositório:

```bash
git clone https://github.com/arthur810FR/hotel-aplicacao.git
```
2. **Navegue até o diretório do projeto:**

3. **Abra o projeto na sua IDE e execute as classe DesafioHotelApplication para o projeto rodar.**

4. **Utilize o Menu no Console**

Após iniciar a aplicação, um menu será exibido no console com as seguintes opções:

```makefile
1: Ver Hotéis Disponíveis
2: Cadastrar Cliente
3: Realizar Reserva
4: Consultar Minhas Reservas
5: Sair
```
### Validações e Tratamento de Exceções ⚠️
O sistema possui validações detalhadas para assegurar que os dados inseridos são corretos:

- **Validação de Nome**: Apenas letras e espaços, com no mínimo 3 caracteres.
- **Validação de CPF**: Deve ter exatamente 11 dígitos numéricos.
- **Validação de Data**: As datas devem estar no formato `dd/MM/yyyy`.
- **Exceções Personalizadas**: Tratamento de exceções com mensagens amigáveis para o usuário em caso de cliente, hotel ou quarto não encontrados.

### Estrutura de Dados 📊

#### Entidades
- **Cliente**: Representa o cliente, com `nome` e `cpf`.
- **Hotel**: Representa o hotel, incluindo o `nome`, `localizacao` e a lista de `quartos`.
- **Quarto**: Representa um quarto de hotel, com o `tipo` (Normal ou Premium) e o estado de `disponibilidade`.
- **Reserva**: Representa uma reserva, associando um cliente a um quarto com as datas de início e fim da estadia.

#### Enumeradores
- **TiposQuarto**: Enumera os tipos de quartos disponíveis (`NORMAL`, `PREMIUM`).
- **Disponibilidade**: Enumera a disponibilidade dos quartos (`DISPONIVEL`, `INDISPONIVEL`).

### Exemplo de Uso 📖

Após iniciar a aplicação com o comando `mvn spring-boot:run`, o menu de interação será exibido no console. Abaixo está um exemplo de uso do sistema:

1. **Ver Hotéis Disponíveis**: 
   - O usuário escolhe a opção `1` para visualizar os hotéis disponíveis. O sistema exibe uma lista de hotéis, informando a localização e a quantidade de quartos disponíveis para reserva.

2. **Cadastrar Cliente**: 
   - O usuário escolhe a opção `2` para cadastrar um novo cliente.
   - O sistema solicita o nome do cliente. O usuário deve inserir um nome válido (apenas letras e espaços, com pelo menos 3 caracteres).
   - Em seguida, o sistema solicita o CPF do cliente (11 dígitos numéricos). Após a validação, o cliente é cadastrado com sucesso.

3. **Realizar Reserva**: 
   - O usuário escolhe a opção `3` para realizar uma reserva.
   - O sistema solicita o CPF do cliente e, após confirmar que o cliente está cadastrado, exibe a lista de hotéis disponíveis.
   - O usuário escolhe o hotel desejado, seguido do tipo de quarto (Normal ou Premium).
   - O sistema solicita as datas de início e fim da estadia no formato `dd/MM/yyyy`. Após validação, a reserva é confirmada, e o quarto é marcado como indisponível.

4. **Consultar Minhas Reservas**:
   - O usuário escolhe a opção `4` e insere seu CPF para visualizar todas as reservas associadas ao seu cadastro.
   - O sistema exibe uma lista detalhada das reservas, incluindo o nome do hotel, tipo de quarto, e as datas de início e fim da estadia.

5. **Sair**:
   - O usuário escolhe a opção `5` para sair do sistema.

Este fluxo demonstra como um usuário pode navegar pelo sistema, registrar-se como cliente, realizar reservas e consultar suas reservas com facilidade.

### Melhorias Futuras 🔮
- **Interface Gráfica**: Implementar uma interface gráfica (GUI) para melhorar a experiência do usuário, tornando o sistema mais acessível e visual.
- **Autenticação e Autorização**: Adicionar controle de acesso com autenticação e autorização para gerenciar diferentes permissões de usuários, como administrador e cliente, proporcionando mais segurança e controle sobre as funcionalidades disponíveis.

## 📞 Contato
**Arthur Francisco Guedes Azevedo**

- [LinkedIn](https://www.linkedin.com/in/arthur-azevedo-desenvolvedor/)
- [Email](mailto:arthurfranciscoazevedo@gmail.com)
