INSERT INTO tb_categoria (nome) VALUES 
('Jogos de Tabuleiro'),
('Jogos de Cartas'),
('Jogos de Estratégia'),
('Jogos de RPG'),
('Acessórios');

INSERT INTO tb_user (username, display_name, password, email) VALUES
('admin', 'Administrador', '$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bsolF5.', 'admin@trinity.com'),
('cliente', 'Cliente Teste', '$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bsolF5.', 'cliente@trinity.com');

INSERT INTO tb_produto (nome, descricao, preco, url_imagem, categoria_id) VALUES
('Catan', 'Jogo de estratégia e negociação onde os jogadores competem para colonizar uma ilha', 199.90, 'https://m.media-amazon.com/images/I/81+okm4IpfL._AC_UF1000,1000_QL80_.jpg', 1),
('Ticket to Ride', 'Jogo de tabuleiro onde os jogadores coletam cartas para reivindicar rotas ferroviárias', 249.90, 'https://m.media-amazon.com/images/I/91YNJM4oyhL._AC_UF1000,1000_QL80_.jpg', 1),
('Magic: The Gathering - Starter Kit', 'Kit inicial do famoso jogo de cartas colecionáveis', 89.90, 'https://m.media-amazon.com/images/I/81TqHwAiGtL._AC_UF1000,1000_QL80_.jpg', 2),
('Pokémon TCG - Deck Inicial', 'Deck inicial do jogo de cartas colecionáveis Pokémon', 69.90, 'https://m.media-amazon.com/images/I/81fwXEzZ3XL._AC_UF1000,1000_QL80_.jpg', 2),
('Pandemic', 'Jogo cooperativo onde os jogadores trabalham juntos para combater doenças globais', 219.90, 'https://m.media-amazon.com/images/I/814F5EyoMoL._AC_UF1000,1000_QL80_.jpg', 3),
('Dungeons & Dragons - Livro do Jogador', 'Livro essencial para jogar o RPG Dungeons & Dragons', 299.90, 'https://m.media-amazon.com/images/I/91Elg1vCTbL._AC_UF1000,1000_QL80_.jpg', 4),
('Conjunto de Dados de RPG', 'Kit com 7 dados poliédricos para jogos de RPG', 29.90, 'https://m.media-amazon.com/images/I/71Vy+g7cRlL._AC_UF1000,1000_QL80_.jpg', 5),
('Porta-cartas Premium', 'Porta-cartas de alta qualidade para proteger suas cartas colecionáveis', 49.90, 'https://m.media-amazon.com/images/I/61gizTEYVEL._AC_UF1000,1000_QL80_.jpg', 5);

INSERT INTO tb_endereco (usuario_id, logradouro, complemento, cep, cidade, uf) VALUES
(2, 'Rua dos Jogadores', 'Apto 42', '85503-500', 'Pato Branco', 'PR'),
(2, 'Avenida dos Dados', 'Casa 7', '85503-600', 'Pato Branco', 'PR');
