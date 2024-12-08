-- Create table if it doesn't exist
CREATE TABLE IF NOT EXISTS produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    descricao VARCHAR(255),
    preco BIGINT,
    quantidade_estoque INT
);

-- Insert initial data into the table
INSERT INTO produto (nome, descricao, preco, quantidade_estoque) VALUES
('Produto A', 'Descricao do Produto A', 100, 10),
('Produto B', 'Descricao do Produto B', 200, 20),
('Produto C', 'Descricao do Produto C', 300, 30),
('Produto D', 'Descricao do Produto D', 400, 40),
('Produto E', 'Descricao do Produto E', 500, 50);
