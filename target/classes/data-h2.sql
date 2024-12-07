-- Create table if it doesn't exist
CREATE TABLE IF NOT EXISTS produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    descricao VARCHAR(255),
    preco VARCHAR(255),
    quantidade_estoque INT
);

-- Insert initial data into the table
INSERT INTO produto (nome, descricao, preco, quantidade_estoque) VALUES
('Produto A', 'Descrição do Produto A', '100.00', 10),
('Produto B', 'Descrição do Produto B', '200.00', 20),
('Produto C', 'Descrição do Produto C', '300.00', 30),
('Produto D', 'Descrição do Produto D', '400.00', 40),
('Produto E', 'Descrição do Produto E', '500.00', 50);
