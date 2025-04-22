package mock

import model.entities.Employment

class EmploymentMocks {

    static def save = [
            input : new Employment([
                    enterpriseId: 1,
                    name        : "Desenvolvedor Backend",
                    description : "Responsável pelo desenvolvimento e manutenção de APIs RESTful usando Java e Spring Boot.",
                    country     : "Brasil",
                    state       : "São Paulo",
                    postalCode  : "01000-000",
                    skills      : ["Java", "Git"]
            ]),
            output: "INSERT INTO employments (name, description, postal_code_id, enterprise_id)\n" +
                    "VALUES (\n" +
                    "    'Desenvolvedor Backend',\n" +
                    "    'Responsável pelo desenvolvimento e manutenção de APIs RESTful usando Java e Spring Boot.',\n" +
                    "    (SELECT id FROM postal_codes WHERE postal_code = '01000-000'),\n" +
                    "    1\n" +
                    ");\n" +
                    "INSERT INTO employment_skill (employment_id, skill_id)\n" +
                    "VALUES\n" +
                    "((SELECT id \n" +
                    "FROM employments \n" +
                    "WHERE \n" +
                    "    name = 'Desenvolvedor Backend' AND\n" +
                    "    enterprise_id = 1 ), (SELECT id FROM skills WHERE name = 'Java')),\n" +
                    "((SELECT id \n" +
                    "FROM employments \n" +
                    "WHERE \n" +
                    "    name = 'Desenvolvedor Backend' AND\n" +
                    "    enterprise_id = 1 ), (SELECT id FROM skills WHERE name = 'Git'));"
    ]

    static def getById = [
            input : new Employment([
                    id          : 1,
                    enterpriseId: 1,
                    name        : "Desenvolvedor Backend",
                    description : "Responsável pelo desenvolvimento e manutenção de APIs RESTful usando Java e Spring Boot.",
                    country     : "Brasil",
                    state       : "São Paulo",
                    postalCode  : "01000-000",
                    skills      : ["Java", "Git"]
            ]),
            output: "SELECT\n" +
                    "    e.id,\n" +
                    "    e.name,\n" +
                    "    e.description,\n" +
                    "    pc.postal_code AS postalCode,\n" +
                    "    s.name AS state,\n" +
                    "    c.name AS country,\n" +
                    "    e.enterprise_id AS enterpriseId,    ARRAY_AGG(sk.name) FILTER (WHERE sk.name IS NOT NULL) AS skills\n" +
                    "FROM\n" +
                    "    employments e\n" +
                    "        JOIN\n" +
                    "    postal_codes pc ON e.postal_code_id = pc.id\n" +
                    "        JOIN\n" +
                    "    states s ON pc.state_id = s.id\n" +
                    "        JOIN\n" +
                    "    countries c ON s.country_id = c.id\n" +
                    "        LEFT JOIN\n" +
                    "    employment_skill es ON e.id = es.employment_id\n" +
                    "        LEFT JOIN\n" +
                    "    skills sk ON es.skill_id = sk.id\n" +
                    "WHERE\n" +
                    "    e.id = 1\n" +
                    "GROUP BY\n" +
                    "    e.id,\n" +
                    "    e.name,\n" +
                    "    e.description,\n" +
                    "    pc.postal_code,\n" +
                    "    s.name,\n" +
                    "    c.name,\n" +
                    "    e.enterprise_id;"
    ]

    static def update = [
            input : [
                    original: new Employment([
                            id          : 1,
                            enterpriseId: 1,
                            name        : "Desenvolvedor Backend",
                            description : "Responsável pelo desenvolvimento e manutenção de APIs RESTful usando Java e Spring Boot.",
                            country     : "Brasil",
                            state       : "São Paulo",
                            postalCode  : "01000-000",
                            skills      : ["Java", "Git"]
                    ]),
                    updated : new Employment([
                            enterpriseId: 1,
                            name        : "Engenheiro de Software",
                            description : "Atua na criação e otimização de microsserviços em Node.js, garantindo escalabilidade e segurança.",
                            country     : "Brasil",
                            state       : "Minas Gerais",
                            postalCode  : "30130-000",
                            skills      : ["Git", "GitHub"]
                    ])

            ],

            output: "UPDATE employments SET\n" +
                    "name = 'Engenheiro de Software',\n" +
                    "description = 'Atua na criação e otimização de microsserviços em Node.js, garantindo escalabilidade e segurança.',\n" +
                    "postal_code_id = (SELECT pc.id FROM postal_codes pc JOIN states s ON pc.state_id = s.id WHERE pc.postal_code = '30130-000' AND s.name = 'Minas Gerais')\n" +
                    "WHERE id = 1;\n" +
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
                    ");DELETE FROM employment_skill WHERE employment_id = 1;\n" +
                    "INSERT INTO employment_skill (employment_id, skill_id)\n" +
                    "VALUES\n" +
                    "(1, (SELECT id FROM skills WHERE name = 'Git')),\n" +
                    "(1, (SELECT id FROM skills WHERE name = 'GitHub'));\n" +
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
                    ");"
    ]
}
