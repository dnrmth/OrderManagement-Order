# Order Management - Oder

Este é um microserviço de gerenciamento de pedidos desenvolvido em **Java** utilizando o framework **Spring Boot**. O objetivo do projeto é gerenciar os pedidos que estão na fila realizando a validação de clientes, produtos, pagamentos e controle de estoque.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Maven**
- **JUnit 5** (para testes)
- **Mockito** (para mocks nos testes)
- **Kafka** (para envio de mensagens)
- **Spring Cloud** (para comunicação entre microserviços)
- **SQL** (para persistência de dados)

## Estrutura do Projeto

O projeto está organizado utilizando a arquitetura limpa (Clean Architecture) simplificada e segue as seguintes convenções de pacotes:

- `src/main/java`: Contém o código principal do sistema.
    - `com.OrderManagement.Order.domain`: Contém as classes de domínio, como `Order` e `ProductVOrder`.
    - `com.OrderManagement.Order.usecase`: Contém os casos de uso, como `CreateOrderUseCase`.
    - `com.OrderManagement.Order.controller`: Contém os controladores REST.
    - `com.OrderManagement.Order.gateway`: Contém as interfaces para comunicação com serviços externos.
- `src/test/java`: Contém os testes unitários e de integração.

## Funcionalidades

- **Criação de Pedidos**: Valida cliente, produtos, pagamento e estoque antes de criar o pedido.
- **Validação de Cliente**: Verifica se o cliente está ativo.
- **Controle de Estoque**: Atualiza a quantidade de produtos no estoque.
- **Processamento de Pagamento**: Realiza a validação e processamento do pagamento.
- **Envio de Mensagens**: Envia informações do pedido para o Kafka.

## Como Executar:
- **Docker**: O projeto pode ser executado utilizando o Docker. Certifique-se de ter o Docker instalado em sua máquina.
     
    
     docker compose up

1. Clone o repositório:
   ```bash
   git clone https://github.com/dnrmth/order-management.git
   cd order-management

<hr></hr> Desenvolvido por dnrmth.