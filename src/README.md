# Linketinder CLI

O projeto Linketinder é uma aplicação desenvolvida em Groovy por **Vinícius Menezes Pontes**, cujo objetivo é facilitar a conexão entre empresas e candidatos cujas competências sejam compatíveis. A plataforma permite que empresas definam as habilidades desejadas para suas vagas e que candidatos com essas qualificações tenham maior visibilidade, aumentando as chances de um alinhamento eficiente no processo seletivo.

Além das funcionalidades básicas, como listar, cadastrar e excluir usuários (empresas e candidatos), o sistema também oferece a opção de "Listar Matches". Nessa funcionalidade, cada empresa cadastrada terá uma lista de candidatos classificados por ordem de compatibilidade. Quanto maior o número de competências em comum, maior será a posição do candidato no ranking da empresa.

## Estrutura
### 📂 **data** - Dados estáticos
- `CandidatesData`: Armazena dados estáticos de candidatos (apenas para testes)
- `EnterprisesData`: Armazena dados estáticos de Empresas (apenas para testes)

### 📂 **entities** - Entidades
- `Candidate`: Classe resonsável por métodos e parametros da entidade Candidato
- `Enterprise`: Classe resonsável por métodos e parametros da entidade Empresa
- `User`: Interface base para usuários do sistema (candidatos e empresas)
- `SkillsList`: Define e valida as competências técnicas

### 📂 **managers** - Gerenciadores
- `UserManager`: Controla operações de CRUD para usuários (criação/exclusão)

### 📂 **services** - Lógica de negócios
- `MatchService`: Implementa algoritmo de compatibilidade entre candidatos e vagas

### 📂 **utils** - Utilitários
- `GenericUtils`: Oferece funções auxiliares para processamento genérico

### 📂 **view** - Interface do usuário
- `Cli`: Implementa a interface de linha de comando (CLI) interativa

### ⚙️ **Main_groovy** - Ponto de entrada
- Classe principal que inicia a aplicação

## Pré-requisitos
- Java JDK 8+ instalado
- Groovy 4.0+ instalado

## Como Executar o Projeto
### 1. Clone o repositório

Abra o terminal e execute o comando abaixo:

```bash
git clone [URL_DO_SEU_REPOSITORIO]
```

### 2. Acesse a pasta do projeto

Navegue até o diretório do projeto clonado:

```bash
cd linketinder
```

### 3. Execute a aplicação

Inicie a aplicação com o seguinte comando:

```bash
groovy Main.groovy
```

### 4. Primeiros passos após a execução

Após executar a aplicação, siga as orientações abaixo:

- **Navegação:** Utilize números para navegar pelos menus.
- **Sair:** Pressione `0` para voltar ou sair de qualquer menu.

