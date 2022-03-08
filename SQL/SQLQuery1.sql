USE caixa

CREATE TABLE produto
(
	id INTEGER IDENTITY PRIMARY KEY not null,
	nome_produto VARCHAR(70),
	preco FLOAT,
	estoque INT,
	fabricante VARCHAR(70),
	categoria VARCHAR(70)
)

CREATE TABLE cliente (
	id INT PRIMARY KEY IDENTITY,
	nome VARCHAR(100),
	cpf VARCHAR(11),
	email VARCHAR(100),
	telefone VARCHAR(20),
	genero VARCHAR(50),
	estado_civil VARCHAR(50),
	data_nascimento DATE,
	logadouro VARCHAR(50),
	numero VARCHAR(10),
	complemento VARCHAR(50),
	cep VARCHAR(10),
	estado VARCHAR(50),
	cidade VARCHAR(50),
)

CREATE TABLE ordem
(
	id INT PRIMARY KEY IDENTITY,
	id_cliente INT not null FOREIGN KEY(id_cliente) REFERENCES cliente(id),
	dataPedido DATETIME
)

CREATE TABLE ordem_pedido
(
	id_produto INT FOREIGN KEY(id_produto) REFERENCES produto(id),
	id_ordem INT FOREIGN KEY(id_ordem) REFERENCES ordem(id),
	quantidade INT,
	valor FLOAT
)




SELECT ordem.id_cliente, cliente.nome, ordem_pedido.id_produto, produto.nome_produto, ordem_pedido.quantidade, ordem_pedido.valor, ordem.id, ordem.dataPedido FROM ordem inner join cliente ON ordem.id_cliente = cliente.id 
					inner join ordem_pedido ON ordem.id = ordem_pedido.id_ordem
					inner join produto ON produto.id = ordem_pedido.id_produto 




SELECT * FROM cliente
SELECT * FROM produto

