-- usuários

CREATE TABLE Cliente (
    CPF VARCHAR(13) PRIMARY KEY,
    nome VARCHAR(50),
    telefone VARCHAR(16),
    rua VARCHAR(50),
    num_casa INTEGER,
    senha VARCHAR(25)
);

CREATE TABLE Gerente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50),
    senha VARCHAR(25)
);

-- inventário

CREATE TABLE Estoque ( -- Inclui serviços
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50),
    descricao VARCHAR(200),
    quantidade INTEGER,
    preço DECIMAL(10, 2)
);

-- -- -- -- --

CREATE TABLE Agendamentos (
    id SERIAL PRIMARY KEY,
    cpf_cliente VARCHAR(13),
    hora TIME,
    data_marcada DATE,
    id_venda INTEGER,
    status VARCHAR(20) DEFAULT 'Pendente'
);

CREATE TABLE Vendas ( -- Inclui serviços
	id SERIAL PRIMARY KEY,
	nome VARCHAR(50),
	descricao VARCHAR(100),
	preco DECIMAL(10,2)
);


