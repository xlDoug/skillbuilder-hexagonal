# NTTSkillBoost 🚀📚

## Visão Geral

NTTSkillBoost é uma plataforma inovadora de aprendizado gamificado desenvolvida pela NTT DATA, projetada para transformar a experiência de treinamento interno através de um sistema de pontuação e recompensas.

## Arquitetura

O projeto utiliza a **Arquitetura Hexagonal** (Ports and Adapters), que oferece:
- Desacoplamento entre regras de negócio e infraestrutura
- Maior flexibilidade e testabilidade
- Independência de tecnologias externas

### Microserviços Planejados

1. **Serviço de Autenticação e Autorização**
    - Gerencia login, cadastro e controle de acesso
    - Suporta diferentes tipos de usuário (Aluno, Professor, Admin)

2. **Serviço de Gerenciamento de Cursos**
    - CRUD de cursos
    - Atribuição de cursos a professores
    - Listagem de cursos

3. **Serviço de Gerenciamento de Atividades**
    - Criação de atividades (vídeos, quizzes, tarefas)
    - Atribuição de pontuações
    - Acompanhamento de progresso

4. **Serviço de Progresso**
    - Rastreamento do progresso do aluno
    - Atualização de status de conclusão
    - Cálculo de pontuações

5. **Serviço de Gamificação**
    - Gerenciamento de pontos
    - Geração de rankings
    - Acompanhamento de metas

6. **Serviço de Loja de Recompensas** (Futuro)
    - Gestão de recompensas
    - Resgate de prêmios
    - Controle de disponibilidade

## Tecnologias Utilizadas

- **Backend**: Java 17 com Spring Boot 3
- **Banco de Dados**:
    - PostgreSQL
    - MongoDB (para progresso)
    - Redis (para rankings em tempo real)
- **Autenticação**: JWT com Spring Security
- **Documentação**: Springdoc OpenAPI (Swagger)
- **Infraestrutura**: AWS (Fargate, RDS, S3)
- **Mensageria**: Amazon SQS/SNS
- **Orquestração**: Docker, GitHub Actions

## Funcionalidades Principais

### Para Alunos
- Cadastro e Login
- Visualização de cursos disponíveis
- Inscrição em cursos
- Sistema de pontuação baseado em:
    - Conclusão de aulas
    - Participação em quizzes
    - Desempenho destacado

### Para Professores
- Criação e gerenciamento de cursos
- Acompanhamento do progresso dos alunos
- Sistema de metas com pontuação

### Para Administradores
- Aprovação de professores
- Gerenciamento de recompensas
- Geração de relatórios e estatísticas

## Conceitos de Domínio

### Modelos Principais
- **Usuário**: Representa alunos, professores e admins
- **Curso**: Contém informações sobre cursos e metas
- **Atividade**: Diferentes tipos de tarefas de aprendizado
- **Progresso**: Rastreia o andamento do aluno
- **Pontuação**: Registra pontos obtidos
- **Ranking**: Mantém posição global do usuário
- **Recompensa**: Prêmios resgatáveis por pontos

### Regras de Negócio
- Pontuação dinâmica por tipo de atividade
- Bônus por conclusão de curso
- Restrições de resgate baseadas em pontos
- Validações de permissão por tipo de usuário

## Configuração e Instalação

### Pré-requisitos
- Java 17
- Docker
- Maven
- AWS CLI (para deploy)

### Passos de Instalação
1. Clonar o repositório
2. Configurar variáveis de ambiente
3. Executar `mvn clean install`
4. Iniciar com `java -jar target/skillbuilder.jar`

## Executando Testes
- Testes unitários: `mvn test`
- Cobertura de código: `mvn jacoco:report`

## Contribuição
1. Faça um fork do projeto
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Abra um Pull Request

## Próximos Passos
- Implementação completa de microserviços
- Integração com sistemas de autenticação corporativos
- Desenvolvimento do frontend
- Expansão do sistema de recompensas

## Licença
[Inserir detalhes da licença]

## Contato
NTT DATA Brasil
E-mail: [contato]
