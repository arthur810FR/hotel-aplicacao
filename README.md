# Sistema de Gestão de Reservas de Hotéis 🏨

Este projeto é um sistema de linha de comando para gestão de reservas de hotéis. Desenvolvido em Java com o framework Spring Boot, o sistema permite cadastrar clientes, visualizar hotéis disponíveis, realizar reservas de quartos e consultar reservas existentes, tudo com tratamento de exceções e validações para garantir uma experiência amigável ao usuário.

## Funcionalidades Principais 🚀

- **Visualizar Hotéis Disponíveis**: Lista todos os hotéis com quartos disponíveis para reserva, exibindo informações sobre a localização e o número de quartos livres.
- **Cadastrar Cliente**: Permite o cadastro de clientes com validações no nome (apenas letras e no mínimo 3 caracteres) e CPF (11 dígitos numéricos).
- **Realizar Reserva**: O cliente pode escolher um hotel e o tipo de quarto, informando as datas de início e fim da reserva.
- **Consultar Reservas**: Exibe as reservas feitas por um cliente específico, identificado pelo CPF.
