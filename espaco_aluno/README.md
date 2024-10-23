# Instruções para Iniciar a Aplicação

Este documento descreve como iniciar a aplicação Spring Boot com um banco de dados PostgreSQL em um container Docker.

## Pré-requisitos

Antes de iniciar, certifique-se de ter as seguintes ferramentas instaladas:

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Java JDK 21](https://www.azul.com/downloads/?package=jdk#zulu)
- [Maven](https://maven.apache.org/install.html) (opcional, se você não estiver usando o Docker para construir a aplicação)

## Passo 1: Configurar as Variáveis de Ambiente

Defina as variáveis de ambiente para o nome de usuário e a senha do banco de dados antes de iniciar os containers. Crie um arquivo .env na raíz do projeto contendo as variáveis ou abra um terminal execute:

```bash
export DB_USERNAME=####
export DB_PASSWORD=####
```
## Passo 2: Iniciar o Banco de Dados e a Aplicação

Navegue até o diretório onde está o arquivo docker-compose.yml e execute o seguinte comando:

```bash
docker-compose up
```