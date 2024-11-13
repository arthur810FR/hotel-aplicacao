# Sistema de Gestão de Reservas de Hotéis 🏨

Este projeto é um sistema de linha de comando para gestão de reservas de hotéis. Desenvolvido em Java com o framework Spring Boot, o sistema permite cadastrar clientes, visualizar hotéis disponíveis, realizar reservas de quartos e consultar reservas existentes, tudo com tratamento de exceções e validações para garantir uma experiência amigável ao usuário.

## Funcionalidades Principais 🚀

- **Visualizar Hotéis Disponíveis**: Lista todos os hotéis com quartos disponíveis para reserva, exibindo informações sobre a localização e o número de quartos livres.
- **Cadastrar Cliente**: Permite o cadastro de clientes com validações no nome (apenas letras e no mínimo 3 caracteres) e CPF (11 dígitos numéricos).
- **Realizar Reserva**: O cliente pode escolher um hotel e o tipo de quarto, informando as datas de início e fim da reserva.
- **Consultar Reservas**: Exibe as reservas feitas por um cliente específico, identificado pelo CPF.
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
