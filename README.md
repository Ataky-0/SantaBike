# SantaBike
*Projeto de Engenharia de Software com banco de dados Postgres.*

## Como Compilar e Rodar

Para compilar use o comando:

```sh
gradle clean build
```

O Gradle é o gerenciador do projeto, com este comando em específico ele irá limpar o ambiente de compilações passadas, e por fim compilar mais uma vez o projeto inteiro.

Para rodar o projeto recém compilado:

```sh
gradle run -q --console=plain --warning-mode none
```

Este comando irá rodar o projeto compilado de maneira visualmente limpa, ou seja, sem avisos ou quaisquer ornamentações visuais provenientes do gerenciador de projeto (gradle).

