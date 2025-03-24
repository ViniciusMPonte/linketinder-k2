package db

import entities.Employment
import entities.NewCandidate
import entities.NewEnterprise

class Queries {

    static String insertUsersTable(entity) {
        String query = "BEGIN;\n" // Inicia a transação

        // Insere usuário
        query += "INSERT INTO users (email, password)\n" +
                "VALUES ('" + entity.getEmail() + "', '" + entity.getPassword() + "');\n"

        query += "COMMIT;" // Finaliza a transação
        return query
    }

    static String insertPostalCodesTable(entity) {
        String query = "BEGIN;\n" // Inicia a transação

        // Insere postal_code
        query += "INSERT INTO postal_codes (postal_code, state_id)\n" +
                "VALUES ('" + entity.getPostalCode() + "', (SELECT id FROM states WHERE name = '" + entity.getState() + "'));\n"

        query += "COMMIT;" // Finaliza a transação
        return query
    }

    static String insertCandidatesTable(NewCandidate candidate) {
        String query = "BEGIN;\n" // Inicia a transação

        // Insere candidato
        query += "INSERT INTO candidates (user_id, name, description, birthday, cpf, postal_code_id)\n" +
                "VALUES (\n" +
                "    (SELECT id FROM users WHERE email = '" + candidate.getEmail() + "'),\n" +
                "    '" + candidate.getName() + "',\n" +
                "    '" + candidate.getDescription() + "',\n" +
                "    '" + candidate.getBirthday().format("yyyy-MM-dd") + "',\n" + // Formato esperado: 'YYYY-MM-DD'
                "    '" + candidate.getCpf() + "',\n" +
                "    (SELECT id FROM postal_codes WHERE postal_code = '" + candidate.getPostalCode() + "')\n" +
                ");\n"

        query += "COMMIT;" // Finaliza a transação
        return query
    }

    static String insertEnterprisesTable(NewEnterprise enterprise) {
        String query = "BEGIN;\n" // Inicia a transação

        // Insere candidato
        query += "INSERT INTO enterprises (user_id, name, description, cnpj, postal_code_id)\n" +
                "VALUES (\n" +
                "    (SELECT id FROM users WHERE email = '" + enterprise.getEmail() + "'),\n" +
                "    '" + enterprise.getName() + "',\n" +
                "    '" + enterprise.getDescription() + "',\n" +
                "    '" + enterprise.getCnpj() + "',\n" +
                "    (SELECT id FROM postal_codes WHERE postal_code = '" + enterprise.getPostalCode() + "')\n" +
                ");\n"

        query += "COMMIT;" // Finaliza a transação
        return query
    }

    static String insertEmploymentsTable(Employment employment) {
        String query = "BEGIN;\n" // Inicia a transação

        // Insere vaga
        query += "INSERT INTO employments (name, description, postal_code_id, enterprise_id)\n" +
                "VALUES (\n" +
                "    '" + employment.getName() + "',\n" +
                "    '" + employment.getDescription() + "',\n" +
                "    (SELECT id FROM postal_codes WHERE postal_code = '" + employment.getPostalCode() + "'),\n" +
                "    " + employment.getEnterpriseId() + "\n" +
                ");\n"

        query += "COMMIT;" // Finaliza a transação
        return query
    }

    static String insertCandidateSkillTable(NewCandidate candidate) {
        String query = "BEGIN;\n" // Inicia a transação

        // Insere skills
        query += "INSERT INTO candidate_skill (candidate_id, skill_id)\nVALUES\n"
        candidate.getSkills().forEach(skill -> {
            query += "((SELECT id FROM users WHERE email = '" + candidate.getEmail() + "'), " +
                    "(SELECT id FROM skills WHERE name = '" + skill + "')),\n"
        });
        query = query.replaceFirst(/,\n$/, ";\n") // Substitui última vírgula por ponto-e-vírgula

        query += "COMMIT;" // Finaliza a transação
        return query
    }

    static String insertEmploymentSkillTable(Employment employment) {
        String query = "BEGIN;\n" // Inicia a transação

        // Insere skills
        query += "INSERT INTO employment_skill (employment_id, skill_id)\nVALUES\n"
        employment.getSkills().forEach(skill -> {
            query += "((SELECT id \n" +
                    "FROM employments \n" +
                    "WHERE \n" +
                    "    name = '" + employment.getName() + "' AND\n" +
                    "    enterprise_id = " + employment.getEnterpriseId() + " ), " +
                    "(SELECT id FROM skills WHERE name = '" + skill + "')),\n"
        });
        query = query.replaceFirst(/,\n$/, ";\n") // Substitui última vírgula por ponto-e-vírgula

        query += "COMMIT;" // Finaliza a transação
        return query
    }

    static String selectCandidateById(int id) {
        return "SELECT \n" +
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
                "    c.user_id = " + id + "\n" +
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
    }

    static String selectEnterpriseById(int id) {
        return "SELECT\n" +
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
                "    e.user_id = " + id + "\n" +
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
    }

    static String selectEmploymentById(int id) {
        return "SELECT\n" +
                "    e.id,\n" +
                "    e.name,\n" +
                "    e.description,\n" +
                "    pc.postal_code AS postalCode,\n" +
                "    s.name AS state,\n" +
                "    c.name AS country,\n" +
                "    e.enterprise_id AS enterpriseId," +
                "    ARRAY_AGG(sk.name) FILTER (WHERE sk.name IS NOT NULL) AS skills\n" +
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
                "    e.id = " + id + "\n" +
                "GROUP BY\n" +
                "    e.id,\n" +
                "    e.name,\n" +
                "    e.description,\n" +
                "    pc.postal_code,\n" +
                "    s.name,\n" +
                "    c.name,\n" +
                "    e.enterprise_id;"
    }

    static String updateUsersTable(original, updated) {

        boolean hasUpdates = false

        // Atualiza usuário (email/password)
        String query = "BEGIN;\n"
        if (!original.getEmail().equals(updated.getEmail()) || !original.getPassword().equals(updated.getPassword())) {
            query += "UPDATE users SET\n"
            if (!original.getEmail().equals(updated.getEmail())) {
                query += "email = '" + updated.getEmail() + "',\n"
            }
            if (!original.getPassword().equals(updated.getPassword())) {
                query += "password = '" + updated.getPassword() + "',\n"
            }
            query = query.replaceFirst(/,\n$/, "\n")
            query += "WHERE id = " + original.getId() + ";\n"
            hasUpdates = true
        }

        if (hasUpdates) {
            query += "COMMIT;"
        } else {
            query = "" // Nenhuma alteração necessária
        }

        return query
    }

    static String updatePostalCodesTable(original, updated) {
        boolean hasUpdates = false

        // Atualiza postal_code se necessário (versão simplificada)
        String query = "BEGIN;\n"
        if (!original.getPostalCode().equals(updated.getPostalCode()) || !original.getState().equals(updated.getState())) {
            query += "INSERT INTO postal_codes (postal_code, state_id)\n" +
                    "VALUES ('" + updated.getPostalCode() + "', " +
                    "(SELECT id FROM states WHERE name = '" + updated.getState() + "'));\n"
            hasUpdates = true
        }

        if (hasUpdates) {
            query += "COMMIT;"
        } else {
            query = "" // Nenhuma alteração necessária
        }

        return query
    }

    static String updateCandidatesTable(original, updated) {
        boolean hasUpdates = false

        // Atualiza candidato
        String query = "BEGIN;\n"
        if(
                !original.getName().equals(updated.getName()) ||
                !original.getDescription().equals(updated.getDescription()) ||
                !original.getBirthday().equals(updated.getBirthday()) ||
                !original.getCpf().equals(updated.getCpf()) ||
                !original.getPostalCode().equals(updated.getPostalCode())
        ){
            query += "UPDATE candidates SET\n"
        }

        List<String> updates = new ArrayList<>()

        if (!original.getName().equals(updated.getName())) {
            updates.add("name = '" + updated.getName() + "'")
        }
        if (!original.getDescription().equals(updated.getDescription())) {
            updates.add("description = '" + updated.getDescription() + "'")
        }
        if (!original.getBirthday().equals(updated.getBirthday())) {
            updates.add("birthday = '" + updated.getBirthday().format("yyyy-MM-dd") + "'")
        }
        if (!original.getCpf().equals(updated.getCpf())) {
            updates.add("cpf = '" + updated.getCpf() + "'")
        }
        if (!original.getPostalCode().equals(updated.getPostalCode())) {
            updates.add("postal_code_id = (SELECT id FROM postal_codes WHERE postal_code = '" + updated.getPostalCode() + "')")
        }

        if (!updates.isEmpty()) {
            query += String.join(",\n", updates) + "\n"
            query += "WHERE user_id = " + original.getId() + ";\n"
            hasUpdates = true
        }

        if (hasUpdates) {
            query += "COMMIT;"
        } else {
            query = "" // Nenhuma alteração necessária
        }

        return query
    }

    static String updateEnterprisesTable(original, updated) {
        boolean hasUpdates = false

        // Atualiza candidato
        String query = "BEGIN;\n"
        if(
                !original.getName().equals(updated.getName()) ||
                        !original.getDescription().equals(updated.getDescription()) ||
                        !original.getCnpj().equals(updated.getCnpj()) ||
                        !original.getPostalCode().equals(updated.getPostalCode())
        ){
            query += "UPDATE enterprises SET\n"
        }

        List<String> updates = new ArrayList<>()

        if (!original.getName().equals(updated.getName())) {
            updates.add("name = '" + updated.getName() + "'")
        }
        if (!original.getDescription().equals(updated.getDescription())) {
            updates.add("description = '" + updated.getDescription() + "'")
        }
        if (!original.getCnpj().equals(updated.getCnpj())) {
            updates.add("cnpj = '" + updated.getCnpj() + "'")
        }
        if (!original.getPostalCode().equals(updated.getPostalCode())) {
            updates.add("postal_code_id = (SELECT id FROM postal_codes WHERE postal_code = '" + updated.getPostalCode() + "')")
        }

        if (!updates.isEmpty()) {
            query += String.join(",\n", updates) + "\n"
            query += "WHERE user_id = " + original.getId() + ";\n"
            hasUpdates = true
        }

        if (hasUpdates) {
            query += "COMMIT;"
        } else {
            query = "" // Nenhuma alteração necessária
        }

        return query
    }

    static String updateEmploymentsTable(Employment original, Employment updated) {
        boolean hasUpdates = false

        // Atualiza vaga
        String query = "BEGIN;\n"
        if(
                !original.getName().equals(updated.getName()) ||
                !original.getDescription().equals(updated.getDescription()) ||
                !original.getPostalCode().equals(updated.getPostalCode()) ||
                !original.getCountry().equals(updated.getCountry()) ||
                !original.getState().equals(updated.getState()) ||
                !original.getEnterpriseId().equals(updated.getEnterpriseId())
        ){
            query += "UPDATE employments SET\n"
        }

        List<String> updates = new ArrayList<>()

        if (!original.getName().equals(updated.getName())) {
            updates.add("name = '" + updated.getName() + "'");
        }

        if (!original.getDescription().equals(updated.getDescription())) {
            updates.add("description = '" + updated.getDescription() + "'");
        }

        if (!original.getPostalCode().equals(updated.getPostalCode()) || !original.getState().equals(updated.getState())) {
            updates.add("postal_code_id = (SELECT pc.id FROM postal_codes pc " +
                    "JOIN states s ON pc.state_id = s.id " +
                    "WHERE pc.postal_code = '" + updated.getPostalCode() + "' " +
                    "AND s.name = '" + updated.getState() + "')");
        }

        if (!original.getEnterpriseId().equals(updated.getEnterpriseId())) {
            updates.add("enterprise_id = " + updated.getEnterpriseId());
        }

        if (!updates.isEmpty()) {
            query += String.join(",\n", updates) + "\n"
            query += "WHERE id = " + original.getId() + ";\n"
            hasUpdates = true
        }

        if (hasUpdates) {
            query += "COMMIT;"
        } else {
            query = "" // Nenhuma alteração necessária
        }

        return query
    }

    static String updateCandidateSkillTable(original, updated) {
        boolean hasUpdates = false

        // Atualiza skills se necessário
        String query = "BEGIN;\n"
        if (!original.getSkills().equals(updated.getSkills())) {
            query += "DELETE FROM candidate_skill WHERE candidate_id = " + original.getId() + ";\n"
            if (!updated.getSkills().isEmpty()) {
                query += "INSERT INTO candidate_skill (candidate_id, skill_id)\nVALUES\n"
                updated.getSkills().forEach(skill -> {
                    query += "(" + original.getId() + ", " +
                            "(SELECT id FROM skills WHERE name = '" + skill + "')),\n"
                })
                query = query.replaceFirst(/,\n$/, ";\n")
            }
            hasUpdates = true
        }

        if (hasUpdates) {
            query += "COMMIT;"
        } else {
            query = "" // Nenhuma alteração necessária
        }

        return query
    }

    static String updateEmploymentSkillTable(Employment original, Employment updated) {
        boolean hasUpdates = false

        // Atualiza skills se necessário
        String query = "BEGIN;\n"
        if (!original.getSkills().equals(updated.getSkills())) {
            query += "DELETE FROM employment_skill WHERE employment_id = " + original.getId() + ";\n"
            if (!updated.getSkills().isEmpty()) {
                query += "INSERT INTO employment_skill (employment_id, skill_id)\nVALUES\n"
                updated.getSkills().forEach(skill -> {
                    query += "(" + original.getId() + ", " +
                            "(SELECT id FROM skills WHERE name = '" + skill + "')),\n"
                })
                query = query.replaceFirst(/,\n$/, ";\n")
            }
            hasUpdates = true
        }

        if (hasUpdates) {
            query += "COMMIT;"
        } else {
            query = "" // Nenhuma alteração necessária
        }

        return query
    }

    static String deleteCandidateById(int id) {
        return "BEGIN;\n" +
                "\n" +
                "DELETE FROM candidate_skill WHERE candidate_id = " + id + ";\n" +
                "DELETE FROM matches WHERE candidate_id = " + id + ";\n" +
                "DELETE FROM candidates WHERE user_id = " + id + ";\n" +
                "DELETE FROM users WHERE id = " + id + ";\n" +
                "\n" +
                "COMMIT;"
    }

    static String deleteEmploymentById(int id) {
        return "BEGIN;\n" +
                "\n" +
                "DELETE FROM employment_skill WHERE employment_id = " + id + ";\n" +
                "DELETE FROM matches WHERE employment_id = " + id + ";\n" +
                "DELETE FROM employments WHERE id = " + id + ";\n" +
                "\n" +
                "COMMIT;"
    }

    static String deleteEnterpriseById(int id) {
        return "BEGIN;\n" +
                "\n" +
                "DELETE FROM enterprises WHERE user_id = " + id + ";\n" +
                "DELETE FROM users WHERE id = " + id + ";\n" +
                "\n" +
                "COMMIT;"
    }

    static String deleteUnusedPostalCodes() {
        return "BEGIN;\n" +
                "\n" +
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
                ");\n" +
                "\n" +
                "COMMIT;";
    }

    //UTILS
    static String selectIdByEmail(String email) {
        return "SELECT id FROM users WHERE email = '" + email + "';"
    }

    static String selectIdByEmailAndPassword(String email, String password) {
        return "SELECT id FROM users WHERE email = '" + email + "' AND password = '" + password + "';"
    }

    static String selectPostalCodeId(String postalCode, String state) {
        String query = "SELECT id \n" +
                "FROM postal_codes \n" +
                "WHERE postal_code = '" + postalCode + "' \n" +
                "AND state_id = (SELECT id FROM states WHERE name = '" + state + "');\n"
        return query
    }

    static String selectEmploymentId(int enterpriseId, String name) {
        String query = "SELECT id \n" +
                "FROM employments \n" +
                "WHERE name = '" + name + "' \n" +
                "AND enterprise_id = " + enterpriseId + ";\n"
        return query
    }
}


