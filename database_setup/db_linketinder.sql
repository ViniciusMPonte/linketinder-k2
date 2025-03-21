CREATE TABLE users
(
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE countries
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE states
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    country_id INT REFERENCES countries(id)
);

CREATE TABLE postal_codes
(
    id SERIAL PRIMARY KEY,
    postal_code VARCHAR(100) NOT NULL,
    state_id INT REFERENCES states(id)
);

CREATE TABLE candidates
(
    user_id INT PRIMARY KEY REFERENCES users(id),
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    birthday DATE NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    postal_code_id INT REFERENCES postal_codes(id)
);

CREATE TABLE enterprises
(
    user_id INT PRIMARY KEY REFERENCES users(id),
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    cnpj VARCHAR(18) NOT NULL,
    postal_code_id INT REFERENCES postal_codes(id)
);

CREATE TABLE employments
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    postal_code_id INT REFERENCES postal_codes(id),
    enterprise_id INT REFERENCES enterprises(user_id)
);

CREATE TABLE matches
(
    candidate_id INT REFERENCES candidates(user_id),
    employment_id INT REFERENCES employments(id),
    PRIMARY KEY (candidate_id, employment_id)
);

CREATE TABLE skills
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE candidate_skill
(
    candidate_id INT REFERENCES candidates(user_id),
    skill_id INT REFERENCES skills(id)
);

CREATE TABLE employment_skill
(
    employment_id INT REFERENCES employments(id),
    skill_id INT REFERENCES skills(id)
);

INSERT INTO countries (name) VALUES ('Brasil');

INSERT INTO states (name, country_id)
VALUES
    ('Acre', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Alagoas', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Amapá', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Amazonas', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Bahia', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Ceará', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Distrito Federal', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Espírito Santo', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Goiás', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Maranhão', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Mato Grosso', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Mato Grosso do Sul', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Minas Gerais', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Pará', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Paraíba', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Paraná', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Pernambuco', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Piauí', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Rio de Janeiro', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Rio Grande do Norte', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Rio Grande do Sul', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Rondônia', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Roraima', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Santa Catarina', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('São Paulo', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Sergipe', (SELECT id FROM countries WHERE name = 'Brasil')),
    ('Tocantins', (SELECT id FROM countries WHERE name = 'Brasil'));

INSERT INTO skills (name)
VALUES
    ('Java'),
    ('Groovy'),
    ('JavaScript'),
    ('Git'),
    ('GitHub'),
    ('SQL'),
    ('NoSQL'),
    ('Angular'),
    ('Spring'),
    ('Docker');

-- Candidato 1: João Silva
INSERT INTO users (email, password)
VALUES ('candidato1@email.com', 'senha123');

INSERT INTO postal_codes (postal_code, state_id)
VALUES ('01000-000', (SELECT id FROM states WHERE name = 'São Paulo'));

INSERT INTO candidates (user_id, name, description, birthday, cpf, postal_code_id)
VALUES (
           (SELECT id FROM users WHERE email = 'candidato1@email.com'),
           'João Silva',
           'Desenvolvedor Full Stack',
           '1990-05-10',
           '123.456.789-00',
           (SELECT id FROM postal_codes WHERE postal_code = '01000-000')
       );

INSERT INTO candidate_skill (candidate_id, skill_id)
VALUES
    ((SELECT id FROM users WHERE email = 'candidato1@email.com'), (SELECT id FROM skills WHERE name = 'Java')),
    ((SELECT id FROM users WHERE email = 'candidato1@email.com'), (SELECT id FROM skills WHERE name = 'Spring')),
    ((SELECT id FROM users WHERE email = 'candidato1@email.com'), (SELECT id FROM skills WHERE name = 'Docker'));


-- Candidato 2: Maria Souza
INSERT INTO users (email, password)
VALUES ('candidato2@email.com', 'senha123');

INSERT INTO postal_codes (postal_code, state_id)
VALUES ('20000-000', (SELECT id FROM states WHERE name = 'Rio de Janeiro'));

INSERT INTO candidates (user_id, name, description, birthday, cpf, postal_code_id)
VALUES (
           (SELECT id FROM users WHERE email = 'candidato2@email.com'),
           'Maria Souza',
           'Analista de Dados',
           '1985-08-22',
           '234.567.890-11',
           (SELECT id FROM postal_codes WHERE postal_code = '20000-000')
       );

INSERT INTO candidate_skill (candidate_id, skill_id)
VALUES
    ((SELECT id FROM users WHERE email = 'candidato2@email.com'), (SELECT id FROM skills WHERE name = 'SQL')),
    ((SELECT id FROM users WHERE email = 'candidato2@email.com'), (SELECT id FROM skills WHERE name = 'NoSQL')),
    ((SELECT id FROM users WHERE email = 'candidato2@email.com'), (SELECT id FROM skills WHERE name = 'Git'));


-- Candidato 3: Carlos Mendes
INSERT INTO users (email, password)
VALUES ('candidato3@email.com', 'senha123');

INSERT INTO postal_codes (postal_code, state_id)
VALUES ('40000-000', (SELECT id FROM states WHERE name = 'Bahia'));

INSERT INTO candidates (user_id, name, description, birthday, cpf, postal_code_id)
VALUES (
           (SELECT id FROM users WHERE email = 'candidato3@email.com'),
           'Carlos Mendes',
           'Engenheiro de Software',
           '1992-12-15',
           '345.678.901-22',
           (SELECT id FROM postal_codes WHERE postal_code = '40000-000')
       );

INSERT INTO candidate_skill (candidate_id, skill_id)
VALUES
    ((SELECT id FROM users WHERE email = 'candidato3@email.com'), (SELECT id FROM skills WHERE name = 'Java')),
    ((SELECT id FROM users WHERE email = 'candidato3@email.com'), (SELECT id FROM skills WHERE name = 'GitHub')),
    ((SELECT id FROM users WHERE email = 'candidato3@email.com'), (SELECT id FROM skills WHERE name = 'Groovy'));


-- Candidato 4: Ana Oliveira
INSERT INTO users (email, password)
VALUES ('candidato4@email.com', 'senha123');

INSERT INTO postal_codes (postal_code, state_id)
VALUES ('70000-000', (SELECT id FROM states WHERE name = 'Distrito Federal'));

INSERT INTO candidates (user_id, name, description, birthday, cpf, postal_code_id)
VALUES (
           (SELECT id FROM users WHERE email = 'candidato4@email.com'),
           'Ana Oliveira',
           'Designer UX/UI',
           '1995-03-30',
           '456.789.012-33',
           (SELECT id FROM postal_codes WHERE postal_code = '70000-000')
       );

INSERT INTO candidate_skill (candidate_id, skill_id)
VALUES
    ((SELECT id FROM users WHERE email = 'candidato4@email.com'), (SELECT id FROM skills WHERE name = 'JavaScript')),
    ((SELECT id FROM users WHERE email = 'candidato4@email.com'), (SELECT id FROM skills WHERE name = 'Angular')),
    ((SELECT id FROM users WHERE email = 'candidato4@email.com'), (SELECT id FROM skills WHERE name = 'Git'));


-- Candidato 5: Pedro Lima
INSERT INTO users (email, password)
VALUES ('candidato5@email.com', 'senha123');

INSERT INTO postal_codes (postal_code, state_id)
VALUES ('80000-000', (SELECT id FROM states WHERE name = 'Paraná'));

INSERT INTO candidates (user_id, name, description, birthday, cpf, postal_code_id)
VALUES (
           (SELECT id FROM users WHERE email = 'candidato5@email.com'),
           'Pedro Lima',
           'Administrador de Redes',
           '1988-07-18',
           '567.890.123-44',
           (SELECT id FROM postal_codes WHERE postal_code = '80000-000')
       );

INSERT INTO candidate_skill (candidate_id, skill_id)
VALUES
    ((SELECT id FROM users WHERE email = 'candidato5@email.com'), (SELECT id FROM skills WHERE name = 'Docker')),
    ((SELECT id FROM users WHERE email = 'candidato5@email.com'), (SELECT id FROM skills WHERE name = 'SQL')),
    ((SELECT id FROM users WHERE email = 'candidato5@email.com'), (SELECT id FROM skills WHERE name = 'Spring'));


-- Empresa 1: Tech Solutions
INSERT INTO users (email, password)
VALUES ('empresa1@email.com', 'senha123');

INSERT INTO postal_codes (postal_code, state_id)
VALUES ('11000-000', (SELECT id FROM states WHERE name = 'São Paulo'));

INSERT INTO enterprises (user_id, name, description, cnpj, postal_code_id)
VALUES (
           (SELECT id FROM users WHERE email = 'empresa1@email.com'),
           'Tech Solutions',
           'Empresa de tecnologia especializada em desenvolvimento web',
           '12.345.678/0001-11',
           (SELECT id FROM postal_codes WHERE postal_code = '11000-000')
       );

INSERT INTO employments (name, description, postal_code_id, enterprise_id)
VALUES (
           'Desenvolvedor Full Stack',
           'Vaga para atuar no desenvolvimento de aplicações web e APIs REST.',
           (SELECT id FROM postal_codes WHERE postal_code = '11000-000'),
           (SELECT id FROM users WHERE email = 'empresa1@email.com')
       );

INSERT INTO employment_skill (employment_id, skill_id)
VALUES
    ((SELECT id FROM employments WHERE name = 'Desenvolvedor Full Stack'), (SELECT id FROM skills WHERE name = 'Java')),
    ((SELECT id FROM employments WHERE name = 'Desenvolvedor Full Stack'), (SELECT id FROM skills WHERE name = 'Spring')),
    ((SELECT id FROM employments WHERE name = 'Desenvolvedor Full Stack'), (SELECT id FROM skills WHERE name = 'Docker'));


-- Empresa 2: Data Insights
INSERT INTO users (email, password)
VALUES ('empresa2@email.com', 'senha123');

INSERT INTO postal_codes (postal_code, state_id)
VALUES ('22000-000', (SELECT id FROM states WHERE name = 'Rio de Janeiro'));

INSERT INTO enterprises (user_id, name, description, cnpj, postal_code_id)
VALUES (
           (SELECT id FROM users WHERE email = 'empresa2@email.com'),
           'Data Insights',
           'Consultoria especializada em análise de dados e BI.',
           '23.456.789/0001-22',
           (SELECT id FROM postal_codes WHERE postal_code = '22000-000')
       );

INSERT INTO employments (name, description, postal_code_id, enterprise_id)
VALUES (
           'Analista de Dados',
           'Vaga para análise de grandes volumes de dados e criação de dashboards.',
           (SELECT id FROM postal_codes WHERE postal_code = '22000-000'),
           (SELECT id FROM users WHERE email = 'empresa2@email.com')
       );

INSERT INTO employment_skill (employment_id, skill_id)
VALUES
    ((SELECT id FROM employments WHERE name = 'Analista de Dados'), (SELECT id FROM skills WHERE name = 'SQL')),
    ((SELECT id FROM employments WHERE name = 'Analista de Dados'), (SELECT id FROM skills WHERE name = 'NoSQL')),
    ((SELECT id FROM employments WHERE name = 'Analista de Dados'), (SELECT id FROM skills WHERE name = 'Git'));


-- Empresa 3: Cloud Systems
INSERT INTO users (email, password)
VALUES ('empresa3@email.com', 'senha123');

INSERT INTO postal_codes (postal_code, state_id)
VALUES ('33000-000', (SELECT id FROM states WHERE name = 'Bahia'));

INSERT INTO enterprises (user_id, name, description, cnpj, postal_code_id)
VALUES (
           (SELECT id FROM users WHERE email = 'empresa3@email.com'),
           'Cloud Systems',
           'Empresa de soluções em computação em nuvem.',
           '34.567.890/0001-33',
           (SELECT id FROM postal_codes WHERE postal_code = '33000-000')
       );

INSERT INTO employments (name, description, postal_code_id, enterprise_id)
VALUES (
           'Engenheiro de Software',
           'Vaga para desenvolvimento e manutenção de sistemas em nuvem.',
           (SELECT id FROM postal_codes WHERE postal_code = '33000-000'),
           (SELECT id FROM users WHERE email = 'empresa3@email.com')
       );

INSERT INTO employment_skill (employment_id, skill_id)
VALUES
    ((SELECT id FROM employments WHERE name = 'Engenheiro de Software'), (SELECT id FROM skills WHERE name = 'Java')),
    ((SELECT id FROM employments WHERE name = 'Engenheiro de Software'), (SELECT id FROM skills WHERE name = 'Groovy')),
    ((SELECT id FROM employments WHERE name = 'Engenheiro de Software'), (SELECT id FROM skills WHERE name = 'GitHub'));


-- Empresa 4: UX Design Studio
INSERT INTO users (email, password)
VALUES ('empresa4@email.com', 'senha123');

INSERT INTO postal_codes (postal_code, state_id)
VALUES ('44000-000', (SELECT id FROM states WHERE name = 'Distrito Federal'));

INSERT INTO enterprises (user_id, name, description, cnpj, postal_code_id)
VALUES (
           (SELECT id FROM users WHERE email = 'empresa4@email.com'),
           'UX Design Studio',
           'Agência especializada em design de experiência do usuário.',
           '45.678.901/0001-44',
           (SELECT id FROM postal_codes WHERE postal_code = '44000-000')
       );

INSERT INTO employments (name, description, postal_code_id, enterprise_id)
VALUES (
           'Designer UX/UI',
           'Vaga para criar experiências digitais intuitivas e inovadoras.',
           (SELECT id FROM postal_codes WHERE postal_code = '44000-000'),
           (SELECT id FROM users WHERE email = 'empresa4@email.com')
       );

INSERT INTO employment_skill (employment_id, skill_id)
VALUES
    ((SELECT id FROM employments WHERE name = 'Designer UX/UI'), (SELECT id FROM skills WHERE name = 'JavaScript')),
    ((SELECT id FROM employments WHERE name = 'Designer UX/UI'), (SELECT id FROM skills WHERE name = 'Angular')),
    ((SELECT id FROM employments WHERE name = 'Designer UX/UI'), (SELECT id FROM skills WHERE name = 'Git'));


-- Empresa 5: CyberSec Solutions
INSERT INTO users (email, password)
VALUES ('empresa5@email.com', 'senha123');

INSERT INTO postal_codes (postal_code, state_id)
VALUES ('55000-000', (SELECT id FROM states WHERE name = 'Paraná'));

INSERT INTO enterprises (user_id, name, description, cnpj, postal_code_id)
VALUES (
           (SELECT id FROM users WHERE email = 'empresa5@email.com'),
           'CyberSec Solutions',
           'Empresa especializada em segurança cibernética.',
           '56.789.012/0001-55',
           (SELECT id FROM postal_codes WHERE postal_code = '55000-000')
       );

INSERT INTO employments (name, description, postal_code_id, enterprise_id)
VALUES (
           'Analista de Segurança da Informação',
           'Vaga para proteção de sistemas contra ameaças digitais.',
           (SELECT id FROM postal_codes WHERE postal_code = '55000-000'),
           (SELECT id FROM users WHERE email = 'empresa5@email.com')
       );

INSERT INTO employment_skill (employment_id, skill_id)
VALUES
    ((SELECT id FROM employments WHERE name = 'Analista de Segurança da Informação'), (SELECT id FROM skills WHERE name = 'Docker')),
    ((SELECT id FROM employments WHERE name = 'Analista de Segurança da Informação'), (SELECT id FROM skills WHERE name = 'SQL')),
    ((SELECT id FROM employments WHERE name = 'Analista de Segurança da Informação'), (SELECT id FROM skills WHERE name = 'Spring'));

-- Matches
-- Match 1: João Silva com a vaga Desenvolvedor Full Stack
INSERT INTO matches (candidate_id, employment_id)
VALUES
    ((SELECT user_id FROM candidates WHERE name = 'João Silva'),
     (SELECT id FROM employments WHERE name = 'Desenvolvedor Full Stack'));

-- Match 2: Maria Souza com a vaga Analista de Dados
INSERT INTO matches (candidate_id, employment_id)
VALUES
    ((SELECT user_id FROM candidates WHERE name = 'Maria Souza'),
     (SELECT id FROM employments WHERE name = 'Analista de Dados'));

-- Match 3: Carlos Mendes com a vaga Engenheiro de Software
INSERT INTO matches (candidate_id, employment_id)
VALUES
    ((SELECT user_id FROM candidates WHERE name = 'Carlos Mendes'),
     (SELECT id FROM employments WHERE name = 'Engenheiro de Software'));

-- Match 4: Ana Oliveira com a vaga Designer UX/UI
INSERT INTO matches (candidate_id, employment_id)
VALUES
    ((SELECT user_id FROM candidates WHERE name = 'Ana Oliveira'),
     (SELECT id FROM employments WHERE name = 'Designer UX/UI'));

-- Match 5: Pedro Lima com a vaga Analista de Segurança da Informação
INSERT INTO matches (candidate_id, employment_id)
VALUES
    ((SELECT user_id FROM candidates WHERE name = 'Pedro Lima'),
     (SELECT id FROM employments WHERE name = 'Analista de Segurança da Informação'));
