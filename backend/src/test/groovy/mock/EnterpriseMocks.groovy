package mock

import model.entities.Enterprise

class EnterpriseMocks {

    static def save = [
            input : new Enterprise([
                    email      : "empresa.teste@example.com",
                    password   : "senhaSegura123",
                    name       : "Pontevi Teste",
                    description: "Empresa Top",
                    cnpj       : "11.222.333/0001-01",
                    country    : 'Brasil',
                    state      : "São Paulo",
                    postalCode : "12345-678"
            ]),
            output: "INSERT INTO users (email, password)\n" +
                    "VALUES ('empresa.teste@example.com', 'senhaSegura123');\n" +
                    "INSERT INTO enterprises (user_id, name, description, cnpj, postal_code_id)\n" +
                    "VALUES (\n" +
                    "    (SELECT id FROM users WHERE email = 'empresa.teste@example.com'),\n" +
                    "    'Pontevi Teste',\n" +
                    "    'Empresa Top',\n" +
                    "    '11.222.333/0001-01',\n" +
                    "    (SELECT id FROM postal_codes WHERE postal_code = '12345-678')\n" +
                    ");"
    ]

    static def getById = [
            input : new Enterprise([
                    id         : 1,
                    email      : "empresa.teste@example.com",
                    password   : "senhaSegura123",
                    name       : "Pontevi Teste",
                    description: "Empresa Top",
                    cnpj       : "11.222.333/0001-01",
                    country    : 'Brasil',
                    state      : "São Paulo",
                    postalCode : "12345-678"
            ]),
            output: "SELECT\n" +
                    "    e.user_id AS id,\n" +
                    "    u.email,\n" +
                    "    u.password,\n" +
                    "    e.name,\n" +
                    "    e.description,\n" +
                    "    e.cnpj,\n" +
                    "    co.name AS country,\n" +
                    "    s.name AS state,\n" +
                    "    pc.postal_code AS \"postalCode\"\n" +
                    "FROM\n" +
                    "    enterprises e\n" +
                    "        JOIN\n" +
                    "    users u ON e.user_id = u.id\n" +
                    "        JOIN\n" +
                    "    postal_codes pc ON e.postal_code_id = pc.id\n" +
                    "        JOIN\n" +
                    "    states s ON pc.state_id = s.id\n" +
                    "        JOIN\n" +
                    "    countries co ON s.country_id = co.id\n" +
                    "WHERE \n" +
                    "    e.user_id = 1\n" +
                    "GROUP BY\n" +
                    "    e.user_id,\n" +
                    "    u.email,\n" +
                    "    u.password,\n" +
                    "    e.name,\n" +
                    "    e.description,\n" +
                    "    e.cnpj,\n" +
                    "    co.name,\n" +
                    "    s.name,\n" +
                    "    pc.postal_code;"
    ]

    static def update = [
            input : [
                    original: new Enterprise([
                            id         : 1,
                            email      : "empresa.teste@example.com",
                            password   : "senhaSegura123",
                            name       : "Pontevi Teste",
                            description: "Empresa Top",
                            cnpj       : "11.222.333/0001-01",
                            country    : 'Brasil',
                            state      : "São Paulo",
                            postalCode : "12345-678"
                    ]),
                    updated : new Enterprise([
                            email      : "tech.solutions@example.com",
                            password   : "OutraSenha456!",
                            name       : "Tech Solutions LTDA",
                            description: "Especialistas em inovação digital",
                            cnpj       : "22.333.444/0001-55",
                            country    : 'Brasil',
                            state      : "Rio de Janeiro",
                            postalCode : "54321-987"
                    ])
            ],

            output: "UPDATE users SET\n" +
                    "email = 'tech.solutions@example.com',\n" +
                    "password = 'OutraSenha456!'\n" +
                    "WHERE id = 1;\n" +
                    "UPDATE enterprises SET\n" +
                    "name = 'Tech Solutions LTDA',\n" +
                    "description = 'Especialistas em inovação digital',\n" +
                    "cnpj = '22.333.444/0001-55',\n" +
                    "postal_code_id = (SELECT id FROM postal_codes WHERE postal_code = '54321-987')\n" +
                    "WHERE user_id = 1;\n" +
                    "DELETE FROM postal_codes\n" +
                    "WHERE NOT EXISTS (\n" +
                    "    SELECT 1 FROM candidates \n" +
                    "    WHERE candidates.postal_code_id = postal_codes.id\n" +
                    ") AND NOT EXISTS (\n" +
                    "    SELECT 1 FROM enterprises \n" +
                    "    WHERE enterprises.postal_code_id = postal_codes.id\n" +
                    ") AND NOT EXISTS (\n" +
                    "    SELECT 1 FROM employments \n" +
                    "    WHERE employments.postal_code_id = postal_codes.id\n" +
                    ");"
    ]

    static def deleteById = [
            input : 1,
            output: "DELETE FROM employment_skill WHERE employment_id = 1;\n" +
                    "DELETE FROM matches WHERE employment_id = 1;\n" +
                    "DELETE FROM employments WHERE id = 1;DELETE FROM postal_codes\n" +
                    "WHERE NOT EXISTS (\n" +
                    "    SELECT 1 FROM candidates \n" +
                    "    WHERE candidates.postal_code_id = postal_codes.id\n" +
                    ") AND NOT EXISTS (\n" +
                    "    SELECT 1 FROM enterprises \n" +
                    "    WHERE enterprises.postal_code_id = postal_codes.id\n" +
                    ") AND NOT EXISTS (\n" +
                    "    SELECT 1 FROM employments \n" +
                    "    WHERE employments.postal_code_id = postal_codes.id\n" +
                    ");DELETE FROM skills\n" +
                    "WHERE NOT EXISTS (\n" +
                    "    SELECT 1 FROM candidate_skill \n" +
                    "    WHERE candidate_skill.skill_id = skills.id\n" +
                    ") AND NOT EXISTS (\n" +
                    "    SELECT 1 FROM employment_skill \n" +
                    "    WHERE employment_skill.skill_id = skills.id\n" +
                    ");DELETE FROM employment_skill WHERE employment_id = 2;\n" +
                    "DELETE FROM matches WHERE employment_id = 2;\n" +
                    "DELETE FROM employments WHERE id = 2;DELETE FROM postal_codes\n" +
                    "WHERE NOT EXISTS (\n" +
                    "    SELECT 1 FROM candidates \n" +
                    "    WHERE candidates.postal_code_id = postal_codes.id\n" +
                    ") AND NOT EXISTS (\n" +
                    "    SELECT 1 FROM enterprises \n" +
                    "    WHERE enterprises.postal_code_id = postal_codes.id\n" +
                    ") AND NOT EXISTS (\n" +
                    "    SELECT 1 FROM employments \n" +
                    "    WHERE employments.postal_code_id = postal_codes.id\n" +
                    ");DELETE FROM skills\n" +
                    "WHERE NOT EXISTS (\n" +
                    "    SELECT 1 FROM candidate_skill \n" +
                    "    WHERE candidate_skill.skill_id = skills.id\n" +
                    ") AND NOT EXISTS (\n" +
                    "    SELECT 1 FROM employment_skill \n" +
                    "    WHERE employment_skill.skill_id = skills.id\n" +
                    ");DELETE FROM enterprises WHERE user_id = 1;\n" +
                    "DELETE FROM users WHERE id = 1;DELETE FROM postal_codes\n" +
                    "WHERE NOT EXISTS (\n" +
                    "    SELECT 1 FROM candidates \n" +
                    "    WHERE candidates.postal_code_id = postal_codes.id\n" +
                    ") AND NOT EXISTS (\n" +
                    "    SELECT 1 FROM enterprises \n" +
                    "    WHERE enterprises.postal_code_id = postal_codes.id\n" +
                    ") AND NOT EXISTS (\n" +
                    "    SELECT 1 FROM employments \n" +
                    "    WHERE employments.postal_code_id = postal_codes.id\n" +
                    ");"
    ]
}
