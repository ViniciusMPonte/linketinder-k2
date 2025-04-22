package model.entities



class EntityFactory {

    Entity create(String type, params) {
        switch (type) {
            case 'Candidate':
                return new Candidate(params)
            case 'Enterprise':
                return new Enterprise(params)
            case 'Employment':
                return new Employment(params)
            default:
                throw new IllegalArgumentException("Tipo de produto desconhecido: $type")
        }
    }
}
