# SantaBike
*Projeto de Engenharia de Software com banco de dados Postgres.*

## Configure seu Banco de Dados

**Antes de compilar o projeto, opte por configurar um banco de dados (Postgres)!**

Para fazer isso, **dentro da pasta app** use o arquivo `db.properties.example` para criar o arquivo `db.properties` *(ou simplesmente retirando o .example de seu nome)*, ainda dentro da pasta app.

## Como Compilar e Rodar

Para compilar use o comando:

```sh
gradle clean build
```
*Importante que esteja na pasta certa, pode checar usar "dir" ou "tree" no Windows, "ls" ou "pwd" no Linux.*

O Gradle é o gerenciador do projeto, neste comando em específico ele irá limpar o ambiente de compilações passadas, e por fim compilar mais uma vez o projeto inteiro.

Para rodar o projeto recém compilado:

```sh
gradle run -q --console=plain --warning-mode none
```

Este comando irá rodar o projeto compilado de maneira visualmente limpa, ou seja, sem avisos ou quaisquer ornamentações visuais provenientes do gerenciador de projeto (gradle).

