CREATE DATABASE IF NOT EXISTS cupcake_store
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE cupcake_store;

CREATE TABLE IF NOT EXISTS cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS produto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    preco DECIMAL(10,2) NOT NULL,
    estoque INT NOT NULL,
    imagem VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    data_pedido DATETIME NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

CREATE TABLE IF NOT EXISTS item_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_item_pedido FOREIGN KEY (pedido_id) REFERENCES pedido(id),
    CONSTRAINT fk_item_produto FOREIGN KEY (produto_id) REFERENCES produto(id)
);

INSERT INTO produto (nome, descricao, preco, estoque, imagem)
SELECT 'Cupcake Chocolate', 'Cupcake de chocolate com cobertura cremosa.', 12.50, 30, 'chocolate.jpg'
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Cupcake Chocolate');

INSERT INTO produto (nome, descricao, preco, estoque, imagem)
SELECT 'Cupcake Morango', 'Cupcake de baunilha com cobertura de morango.', 13.90, 25, 'morango.jpg'
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Cupcake Morango');

INSERT INTO produto (nome, descricao, preco, estoque, imagem)
SELECT 'Cupcake Red Velvet', 'Cupcake Red Velvet com cream cheese.', 15.90, 20, 'redvelvet.jpg'
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Cupcake Red Velvet');

INSERT INTO produto (nome, descricao, preco, estoque, imagem)
SELECT 'Cupcake Limão', 'Cupcake de limão com cobertura cítrica.', 11.90, 18, 'limao.jpg'
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Cupcake Limão');
