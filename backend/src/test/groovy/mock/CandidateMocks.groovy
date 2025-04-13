package mock

import entities.Candidate

import java.text.SimpleDateFormat

class CandidateMocks {

    static def save = [
            input : new Candidate([
                    email      : "john.doe@example.com",
                    password   : "senhaSegura123",
                    name       : "John Doe",
                    description: "Desenvolvedor backend com 5 anos de experiência",
                    cpf        : "123.456.789-00",
                    birthday   : new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-15"),
                    country    : 'Brasil',
                    state      : "São Paulo",
                    postalCode : "12345-678",
                    skills     : ["Java", "SQL", "Spring", "Groovy"]
            ]),
            output: "INSERT INTO users (email, password)\n" +
                    "VALUES ('john.doe@example.com', 'senhaSegura123');\n" +
                    "INSERT INTO candidates (user_id, name, description, birthday, cpf, postal_code_id)\n" +
                    "VALUES (\n" +
                    "    (SELECT id FROM users WHERE email = 'john.doe@example.com'),\n" +
                    "    'John Doe',\n" +
                    "    'Desenvolvedor backend com 5 anos de experiência',\n" +
                    "    '1990-05-15',\n" +
                    "    '123.456.789-00',\n" +
                    "    (SELECT id FROM postal_codes WHERE postal_code = '12345-678')\n" +
                    ");\n" +
                    "INSERT INTO candidate_skill (candidate_id, skill_id)\n" +
                    "VALUES\n" +
                    "((SELECT id FROM users WHERE email = 'john.doe@example.com'), (SELECT id FROM skills WHERE name = 'Java')),\n" +
                    "((SELECT id FROM users WHERE email = 'john.doe@example.com'), (SELECT id FROM skills WHERE name = 'SQL')),\n" +
                    "((SELECT id FROM users WHERE email = 'john.doe@example.com'), (SELECT id FROM skills WHERE name = 'Spring')),\n" +
                    "((SELECT id FROM users WHERE email = 'john.doe@example.com'), (SELECT id FROM skills WHERE name = 'Groovy'));"
    ]

    static def getById = [
            input : new Candidate([
                    id         : 1,
                    email      : "john.doe@example.com",
                    password   : "senhaSegura123",
                    name       : "John Doe",
                    description: "Desenvolvedor backend com 5 anos de experiência",
                    cpf        : "123.456.789-00",
                    birthday   : new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-15"),
                    country    : 'Brasil',
                    state      : "São Paulo",
                    postalCode : "12345-678",
                    skills     : ["Java", "SQL", "Spring", "Groovy"]
            ]),
            output: "SELECT \n" +
                    "    c.user_id AS id,\n" +
                    "    u.email,\n" +
                    "    u.password,\n" +
                    "    c.name,\n" +
                    "    c.description,\n" +
                    "    c.cpf,\n" +
                    "    c.birthday AS \"birthday\",\n" +
                    "    co.name AS country,\n" +
                    "    s.name AS state,\n" +
                    "    pc.postal_code AS \"postalCode\",\n" +
                    "    ARRAY_AGG(sk.name) FILTER (WHERE sk.name IS NOT NULL) AS skills\n" +
                    "FROM \n" +
                    "    candidates c\n" +
                    "JOIN \n" +
                    "    users u ON c.user_id = u.id\n" +
                    "JOIN \n" +
                    "    postal_codes pc ON c.postal_code_id = pc.id\n" +
                    "JOIN \n" +
                    "    states s ON pc.state_id = s.id\n" +
                    "JOIN \n" +
                    "    countries co ON s.country_id = co.id\n" +
                    "LEFT JOIN \n" +
                    "    candidate_skill cs ON c.user_id = cs.candidate_id\n" +
                    "LEFT JOIN \n" +
                    "    skills sk ON cs.skill_id = sk.id\n" +
                    "WHERE \n" +
                    "    c.user_id = 1\n" +
                    "GROUP BY \n" +
                    "    c.user_id, \n" +
                    "    u.email, \n" +
                    "    u.password, \n" +
                    "    c.name, \n" +
                    "    c.description, \n" +
                    "    c.cpf, \n" +
                    "    c.birthday, \n" +
                    "    co.name, \n" +
                    "    s.name, \n" +
                    "    pc.postal_code;"
    ]

    static def update = [
            input : [
                    original: new Candidate([
                            id         : 1,
                            email      : "john.doe@example.com",
                            password   : "senhaSegura123",
                            name       : "John Doe",
                            description: "Desenvolvedor backend com 5 anos de experiência",
                            cpf        : "123.456.789-00",
                            birthday   : new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-15"),
                            country    : 'Brasil',
                            state      : "São Paulo",
                            postalCode : "12345-678",
                            skills     : ["Java", "SQL", "Spring", "Groovy"]
                    ]),
                    updated : new Candidate([
                            email      : "john.doe.novo@example.com",
                            password   : "novaSenhaSuperSegura!456",
                            name       : "John Michael Doe",
                            description: "Desenvolvedor backend sênior com 6 anos de experiência em sistemas cloud",
                            cpf        : "123.456.789-00",
                            birthday   : new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-15"),
                            country    : 'Brasil',
                            state      : "Rio de Janeiro",
                            postalCode : "98765-432",
                            skills     : ["Java", "Spring", "Groovy", "AWS", "Kubernetes"]
                    ])
            ],

            output: "UPDATE users SET\n" +
                    "email = 'john.doe.novo@example.com',\n" +
                    "password = 'novaSenhaSuperSegura!456'\n" +
                    "WHERE id = 1;\n" +
                    "UPDATE candidates SET\n" +
                    "name = 'John Michael Doe',\n" +
                    "description = 'Desenvolvedor backend sênior com 6 anos de experiência em sistemas cloud',\n" +
                    "postal_code_id = (SELECT id FROM postal_codes WHERE postal_code = '98765-432')\n" +
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
                    ");DELETE FROM candidate_skill WHERE candidate_id = 1;\n" +
                    "INSERT INTO candidate_skill (candidate_id, skill_id)\n" +
                    "VALUES\n" +
                    "(1, (SELECT id FROM skills WHERE name = 'Java')),\n" +
                    "(1, (SELECT id FROM skills WHERE name = 'Spring')),\n" +
                    "(1, (SELECT id FROM skills WHERE name = 'Groovy')),\n" +
                    "(1, (SELECT id FROM skills WHERE name = 'AWS')),\n" +
                    "(1, (SELECT id FROM skills WHERE name = 'Kubernetes'));\n" +
                    "DELETE FROM skills\n" +
                    "WHERE NOT EXISTS (\n" +
                    "    SELECT 1 FROM candidate_skill \n" +
                    "    WHERE candidate_skill.skill_id = skills.id\n" +
                    ") AND NOT EXISTS (\n" +
                    "    SELECT 1 FROM employment_skill \n" +
                    "    WHERE employment_skill.skill_id = skills.id\n" +
                    ");"
    ]

    static def deleteById = [
            input : 1,
            output: "DELETE FROM candidate_skill WHERE candidate_id = 1;\n" +
                    "DELETE FROM matches WHERE candidate_id = 1;\n" +
                    "DELETE FROM candidates WHERE user_id = 1;\n" +
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
                    ");DELETE FROM skills\n" +
                    "WHERE NOT EXISTS (\n" +
                    "    SELECT 1 FROM candidate_skill \n" +
                    "    WHERE candidate_skill.skill_id = skills.id\n" +
                    ") AND NOT EXISTS (\n" +
                    "    SELECT 1 FROM employment_skill \n" +
                    "    WHERE employment_skill.skill_id = skills.id\n" +
                    ");"
    ]
}
