
CREATE DATABASE petshop;

USE petshop;

CREATE TABLE agendamentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_dono VARCHAR(100),
    cpf VARCHAR(14),
    nome_animal VARCHAR(100),
    tipo_animal VARCHAR(50),
    raca_animal VARCHAR(100),
    servico VARCHAR(50),
    horario TIME
);
