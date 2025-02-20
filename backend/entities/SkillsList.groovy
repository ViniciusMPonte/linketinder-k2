package entities

class SkillsList {
    private final def VALID_SKILLS = ["Java", "Groovy", "JavaScript", "Git", "GitHub", "SQL", "NoSQL", "Angular", "Spring", "Docker"]
    def skills = []

    def getVALID_SKILLS() {
        return VALID_SKILLS
    }

    def getSkills() {
        return skills
    }

    boolean setSkills(String skill) {
        if (VALID_SKILLS.contains(skill)) {
            skills << skill
            return true
        } else {
            return false
        }
    }

    boolean setSkills(List<String> skillsList) {
        boolean allValid = true
        skillsList.each { skill ->
            if (!setSkills(skill)) {
                allValid = false
            }
        }
        return allValid
    }

    boolean removeSkill(String skill) {
        return skills.remove(skill)
    }
}
