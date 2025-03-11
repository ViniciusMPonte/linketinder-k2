# Linketinder
README divido em duas partes, FRONTEND e BACKEND. Atualmente não há conexão entre ambas.

# FRONTEND: Linketinder

O Linketinder Frontend é uma aplicação web desenvolvida em TypeScript desenvolvida por **Vinícius Menezes Pontes** que permite a criação de perfis para candidatos e empresas, login, publicação de vagas e visualização de um gráfico de competências dos candidatos. O sistema inclui uma navbar dinâmica (baseada no tipo de usuário e estado de login), redirecionamentos para evitar acessos não autorizados e utiliza o `localStorage` como banco de dados local.

---

## **Funcionalidades Principais**
- **Navbar Dinâmica**: Exibe opções específicas para candidatos, empresas ou usuários não logados.
- **Gráfico de Competências**: Visualização gráfica das habilidades por candidatos.
- **Banco de Dados Local**: Dados persistem no `localStorage` do navegador.
- **Redirecionamentos Inteligentes**: Impede acesso a páginas restritas sem autenticação.

---

## **Estrutura do Projeto**

- `main.ts`: Ponto de entrada.

### 📂 **components** - Componentes de Interface
- `Card.ts`: Componente reutilizável para exibir informações em cards (ex: candidatos, vagas).
- `Chart.ts`: Componente que renderiza o gráfico de competências por candidatos.
- `Nav.ts`: Navbar dinâmica que altera conforme o tipo de usuário (candidato/empresa) e estado de login.
- `Profile.ts`: Página de perfil para visualização de dados de candidatos ou empresas.

### 📂 **data** - Gerenciamento de Dados
- `CandidatesData.ts`: Mock de exemplos de candidatos.
- `EmploymentsData.ts`: Mock de exemplos de empresas.
- `EnterprisesData.ts`: Mock de exemplos de vagas.

### 📂 **entities** - Modelos das entidades
- `Candidate.ts`: Define a estrutura de um candidato (ex: nome, habilidades, etc.).
- `Employment.ts`: Define a estrutura de uma vaga (título, descrição, etc.).
- `Enterprise.ts`: Define a estrutura de uma empresa (nome, CNPJ, etc.).

### 📂 **services** - Serviços
- `DatabaseManager.ts`: Serviço principal para interação com o `localStorage` (banco de dados local).
- `NavigationManager.ts`: Gerencia rotas, redirecionamentos e valida acesso a páginas restritas.
- `LoginManager.ts`: Gerencia o login.
- `ValidationForms`: Valida todos os formulários.

---
## Módulo de Validação de Formulários

O módulo **ValidationForms** é uma classe utilitária, escrita em TypeScript, que centraliza a validação dos dados inseridos em formulários. Ele garante que as informações fornecidas pelos usuários estejam no formato esperado, contribuindo para a integridade dos dados.

### Principais Funcionalidades

- **Validação de Nome**  
  Verifica se o nome possui pelo menos duas palavras, com a primeira letra maiúscula e permitindo caracteres acentuados.  
  *Método*: `name(nome: string): boolean`

- **Validação de Email**  
  Confirma que o email segue um padrão básico de formatação.  
  *Método*: `email(email: string): boolean`

- **Validação de Senha**  
  Garante que a senha contenha 6 ou mais caracteres sem espaços.  
  *Método*: `password(password: string): boolean`

- **Validação de Descrição**  
  Assegura que a descrição contenha somente caracteres permitidos e seja composta por, no mínimo, duas palavras.  
  *Método*: `description(description: string): boolean`

- **Validação de Habilidades (Skills)**
   - Utiliza uma lista pré-definida (`validSkills`) para validar as habilidades informadas.
   - Separa as habilidades recebidas em uma string (usando vírgulas) e valida cada uma através de uma expressão regular gerada dinamicamente.  
     *Método*: `skills(skills: string): boolean`

- **Validação de Idade**  
  Aceita idades entre 18 e 99 anos.  
  *Método*: `age(age: string): boolean`

- **Validação de CPF e CNPJ**  
  Verifica os formatos específicos de CPF (`xxx.xxx.xxx-xx`) e CNPJ (`xx.xxx.xxx/xxxx-xx`).  
  *Métodos*: `cpf(cpf: string): boolean` e `cnpj(cnpj: string): boolean`

- **Validação de País, Estado e CEP**  
  Confirma que os nomes de país e estado iniciem com letra maiúscula e que o CEP siga o padrão `xxxxx-xxx`.  
  *Métodos*: `country(country: string): boolean`, `state(state: string): boolean` e `cep(cep: string): boolean`

### Métodos Auxiliares

- **Geração Dinâmica de Regex**  
  O método `createRegexFromKeywords` recebe uma lista de palavras-chave (como as habilidades válidas) e retorna uma expressão regular customizada, que é utilizada para validar os campos de *skills*.

- **Validação Genérica**  
  O método `validate` permite a validação de um campo específico a partir de uma chave e do valor informado, direcionando para o método de validação apropriado.

- **Mensagens de Erro Personalizadas**  
  Três métodos específicos fornecem mensagens de falha de validação para diferentes contextos:
   - `validationFailMessageCandidate`: para candidatos.
   - `validationFailMessageEnterprise`: para empresas.
   - `validationFailMessageEmployment`: para vagas/empregos.
---

## **Instalação e Execução**

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/ViniciusMPonte/linketinder-frontend-and-backend
   ```

2. **Instale as dependências**:
   ```bash
   npm install
   ```

3. **Compile o projeto**:
   ```bash
   npm run build
   ```
   *Será gerada a pasta `/frontend/dist` com os arquivos estáticos e o JavaScript compilado em `app.js`.*

4. **Inicie a aplicação**:
   ```bash
   npm start
      ```
   
---

# BACKEND: Linketinder CLI

O projeto Linketinder é uma aplicação desenvolvida em Groovy por **Vinícius Menezes Pontes**, cujo objetivo é facilitar a conexão entre empresas e candidatos cujas competências sejam compatíveis. A plataforma permite que empresas definam as habilidades desejadas para suas vagas e que candidatos com essas qualificações tenham maior visibilidade, aumentando as chances de um alinhamento eficiente no processo seletivo.

Além das funcionalidades básicas, como listar, cadastrar e excluir usuários (empresas e candidatos), o sistema também oferece a opção de "Listar Compartibilidade Empresa/Candidatos". Nessa funcionalidade, cada empresa cadastrada terá uma lista de candidatos classificados por ordem de compatibilidade. Quanto maior o número de competências em comum, maior será a posição do candidato no ranking da empresa.

No sistema, existe uma estrutura de testes unitários que garante a qualidade do código. O package tests contém o RunTests, ponto de entrada para a execução dos testes, e subpackages que avaliam as entidades (Candidate e Enterprise), o gerenciador de usuários (UserManager), além de oferecer mocks e utilitários, como o FakeScanner, para simular interações.

## Estrutura
### 📂 **data** - Dados estáticos
- `CandidatesData`: Armazena dados estáticos de candidatos (apenas para testes)
- `EnterprisesData`: Armazena dados estáticos de Empresas (apenas para testes)

### 📂 **entities** - Entidades
- `Candidate`: Classe responsável por métodos e parâmetros da entidade Candidato
- `Enterprise`: Classe responsável por métodos e parâmetros da entidade Empresa
- `User`: Interface base para usuários do sistema (candidatos e empresas)
- `SkillsList`: Define e valida as competências técnicas

### 📂 **managers** - Gerenciadores
- `UserManager`: Controla operações de CRUD para usuários (criação/exclusão)

### 📂 **services** - Lógica de negócios
- `CompatibilityService`: Implementa algoritmo de compatibilidade entre candidatos e vagas

### 📂 **utils** - Utilitários
- `GenericUtils`: Oferece funções auxiliares para processamento genérico

### 📂 **view** - Interface do usuário
- `Cli`: Implementa a interface de linha de comando (CLI) interativa

### ⚙️ **Main.groovy** - Ponto de entrada
- Classe principal que inicia a aplicação

## Estrutura - Testes Unitários

### 📂 **tests** - Testes unitários
- `RunTests`: Ponto de entrada para execução dos testes

### 📂 **tests.entities** - Testes do package entities
- `CandidateTest`: testes de todos os métodos da classe Candidate
- `EnterpriseTest`: testes de todos os métodos da classe Enterprise

### 📂 **tests.managers** - Testes do package managers
- `UserManagerTest`: testes de todos os métodos da classe UserManager

### 📂 **tests.mocks** - Mocks para os testes
- `UsersMock`: disponibiliza dados válidos e inválidos para testar as entidades Candidate e Enterprise

### 📂 **tests.utils** - Utilitários para testes
- `FakeScanner`: Simula entradas do usuário

### ⚙️ **./tests/RunTests** - Ponto de entrada
- Classe principal que inicia os testes unitários


## Pré-requisitos
- Java JDK 8+ instalado
- Groovy 4.0+ instalado

## Como Executar o Projeto
### 1. Clone o repositório

Abra o terminal e execute o comando abaixo:

```bash
git clone https://github.com/ViniciusMPonte/linketinder-frontend-and-backend
```

### 2. Acesse a pasta do projeto

Navegue até o diretório do projeto clonado:

```bash
cd backend
```

### 3. Execute a aplicação ou os testes

Inicie a aplicação com o seguinte comando:

```bash
groovy Main.groovy
```

Para os testes unitários, inicie com o seguinte comando:

```bash
groovy ./tests/RunTests.groovy
```

### 4. Primeiros passos após a execução

Após executar a aplicação, siga as orientações abaixo:

- **Navegação:** Utilize números para navegar pelos menus.
- **Sair:** Pressione `0` para voltar ou sair de qualquer menu.

