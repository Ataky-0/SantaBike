# SantaBike
*Projeto de Engenharia de Software com banco de dados Postgres.*

## Configure seu Banco de Dados

**Antes de compilar o projeto, opte por configurar um banco de dados (Postgres)!**

Para fazer isso, **dentro da pasta app** use o arquivo `db.properties.example` para criar o arquivo `db.properties` *(ou simplesmente retirando o .example de seu nome)*, ainda dentro da pasta app.

## Como Compilar e Rodar

### -> Instalar Gradle ⬇
O Gradle é o gerenciador do projeto, caso não o possua instalado em seu sistema [Clique aqui](https://gradle.org/install/) para um guia de instalação, escolha a versão "binary-only" caso esteja no Windows para instalar apenas o necessário.

### -> Compilar ⚙
Para compilar use o comando:

```sh
gradle clean build
```
*Importante que esteja na pasta certa, pode checar usando "dir" ou "tree" no Windows, "ls" ou "pwd" no Linux.*

Neste comando em específico, o Gradle irá limpar o ambiente de compilações passadas, e por fim compilar novamente o projeto inteiro.

### -> Rodar *▶*
Para rodar o projeto recém compilado:

```sh
gradle run -q --console=plain --warning-mode none
```

Este comando irá rodar o projeto em plena forma, ou seja, sem avisos ou quaisquer ornamentações visuais provenientes do gerenciador de projeto (Gradle).

