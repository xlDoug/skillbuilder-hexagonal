-- Inserir alguns usuários iniciais
INSERT INTO usuarios (id, nome, email, tipo, data_cadastro)
VALUES
    ('a0000001-0000-0000-0000-000000000001', 'Admin Sistema', 'admin@nttdata.com', 'ADMIN', CURRENT_DATE()),
    ('a0000001-0000-0000-0000-000000000002', 'Professor João', 'joao@nttdata.com', 'PROFESSOR', CURRENT_DATE()),
    ('a0000001-0000-0000-0000-000000000003', 'Professor Maria', 'maria@nttdata.com', 'PROFESSOR', CURRENT_DATE()),
    ('a0000001-0000-0000-0000-000000000004', 'Aluno Pedro', 'pedro@nttdata.com', 'ALUNO', CURRENT_DATE()),
    ('a0000001-0000-0000-0000-000000000005', 'Aluna Ana', 'ana@nttdata.com', 'ALUNO', CURRENT_DATE()),
    ('a0000001-0000-0000-0000-000000000006', 'Aluno Carlos', 'carlos@nttdata.com', 'ALUNO', CURRENT_DATE());

-- Inserir credenciais (senha: 123456)
INSERT INTO credenciais_usuarios (usuario_id, email, hash_senha, ativo)
VALUES
    ('a0000001-0000-0000-0000-000000000001', 'admin@nttdata.com', '$2a$10$C7S8jRX0t/CzpOFucT6Q5eCcOJnw4.RR8jPVzYA78NIYpA1uzjf.e', true),
    ('a0000001-0000-0000-0000-000000000002', 'joao@nttdata.com', '$2a$10$C7S8jRX0t/CzpOFucT6Q5eCcOJnw4.RR8jPVzYA78NIYpA1uzjf.e', true),
    ('a0000001-0000-0000-0000-000000000003', 'maria@nttdata.com', '$2a$10$C7S8jRX0t/CzpOFucT6Q5eCcOJnw4.RR8jPVzYA78NIYpA1uzjf.e', true),
    ('a0000001-0000-0000-0000-000000000004', 'pedro@nttdata.com', '$2a$10$C7S8jRX0t/CzpOFucT6Q5eCcOJnw4.RR8jPVzYA78NIYpA1uzjf.e', true),
    ('a0000001-0000-0000-0000-000000000005', 'ana@nttdata.com', '$2a$10$C7S8jRX0t/CzpOFucT6Q5eCcOJnw4.RR8jPVzYA78NIYpA1uzjf.e', true),
    ('a0000001-0000-0000-0000-000000000006', 'carlos@nttdata.com', '$2a$10$C7S8jRX0t/CzpOFucT6Q5eCcOJnw4.RR8jPVzYA78NIYpA1uzjf.e', true);

-- Inserir informações dos professores
INSERT INTO informacoes_professores (usuario_id, descricao, meta_pontos, data_criacao)
VALUES
    ('a0000001-0000-0000-0000-000000000002', 'Professor especialista em desenvolvimento de software', 1000, CURRENT_DATE()),
    ('a0000001-0000-0000-0000-000000000003', 'Professora especialista em arquitetura de software', 1200, CURRENT_DATE());

-- Inserir cursos
INSERT INTO cursos (id, nome, descricao, professor_id, meta_pontos, data_criacao)
VALUES
    ('b0000001-0000-0000-0000-000000000001', 'Fundamentos de Java', 'Curso básico de programação Java', 'a0000001-0000-0000-0000-000000000002', 500, CURRENT_DATE()),
    ('b0000001-0000-0000-0000-000000000002', 'Arquitetura Hexagonal', 'Implementando aplicações com arquitetura hexagonal', 'a0000001-0000-0000-0000-000000000003', 700, CURRENT_DATE()),
    ('b0000001-0000-0000-0000-000000000003', 'Clean Code', 'Princípios de código limpo', 'a0000001-0000-0000-0000-000000000002', 600, CURRENT_DATE());

-- Inserir atividades
INSERT INTO atividades (id, curso_id, tipo_atividade, descricao, pontuacao, data_disponibilidade)
VALUES
    ('c0000001-0000-0000-0000-000000000001', 'b0000001-0000-0000-0000-000000000001', 'VIDEO', 'Introdução ao Java', 10, CURRENT_DATE()),
    ('c0000001-0000-0000-0000-000000000002', 'b0000001-0000-0000-0000-000000000001', 'QUIZ', 'Quiz sobre fundamentos Java', 20, CURRENT_DATE()),
    ('c0000001-0000-0000-0000-000000000003', 'b0000001-0000-0000-0000-000000000001', 'TAREFA', 'Implementar Hello World', 30, CURRENT_DATE()),
    ('c0000001-0000-0000-0000-000000000004', 'b0000001-0000-0000-0000-000000000002', 'VIDEO', 'Introdução à Arquitetura Hexagonal', 15, CURRENT_DATE()),
    ('c0000001-0000-0000-0000-000000000005', 'b0000001-0000-0000-0000-000000000002', 'QUIZ', 'Quiz sobre ports e adapters', 25, CURRENT_DATE()),
    ('c0000001-0000-0000-0000-000000000006', 'b0000001-0000-0000-0000-000000000002', 'TAREFA', 'Implementar aplicação com arquitetura hexagonal', 40, CURRENT_DATE()),
    ('c0000001-0000-0000-0000-000000000007', 'b0000001-0000-0000-0000-000000000003', 'VIDEO', 'Introdução ao Clean Code', 10, CURRENT_DATE()),
    ('c0000001-0000-0000-0000-000000000008', 'b0000001-0000-0000-0000-000000000003', 'QUIZ', 'Quiz sobre princípios SOLID', 20, CURRENT_DATE()),
    ('c0000001-0000-0000-0000-000000000009', 'b0000001-0000-0000-0000-000000000003', 'TAREFA', 'Refatorar código aplicando princípios de Clean Code', 35, CURRENT_DATE());

-- Inserir inscrições de alunos em cursos
INSERT INTO progressos (id, usuario_id, curso_id, status, data_inicio, pontos_acumulados)
VALUES
    ('d0000001-0000-0000-0000-000000000001', 'a0000001-0000-0000-0000-000000000004', 'b0000001-0000-0000-0000-000000000001', 'EM_ANDAMENTO', CURRENT_DATE(), 0),
    ('d0000001-0000-0000-0000-000000000002', 'a0000001-0000-0000-0000-000000000004', 'b0000001-0000-0000-0000-000000000002', 'EM_ANDAMENTO', CURRENT_DATE(), 0),
    ('d0000001-0000-0000-0000-000000000003', 'a0000001-0000-0000-0000-000000000005', 'b0000001-0000-0000-0000-000000000001', 'EM_ANDAMENTO', CURRENT_DATE(), 0),
    ('d0000001-0000-0000-0000-000000000004', 'a0000001-0000-0000-0000-000000000005', 'b0000001-0000-0000-0000-000000000003', 'EM_ANDAMENTO', CURRENT_DATE(), 0),
    ('d0000001-0000-0000-0000-000000000005', 'a0000001-0000-0000-0000-000000000006', 'b0000001-0000-0000-0000-000000000002', 'EM_ANDAMENTO', CURRENT_DATE(), 0),
    ('d0000001-0000-0000-0000-000000000006', 'a0000001-0000-0000-0000-000000000006', 'b0000001-0000-0000-0000-000000000003', 'EM_ANDAMENTO', CURRENT_DATE(), 0);

-- Inserir algumas recompensas disponíveis
INSERT INTO recompensas (id, nome, descricao, custo_pontos, quantidade_disponivel, data_criacao)
VALUES
    ('e0000001-0000-0000-0000-000000000001', 'Certificado Premium', 'Certificado especial de conclusão', 500, 100, CURRENT_DATE()),
    ('e0000001-0000-0000-0000-000000000002', 'Mentoria Individual', '1 hora de mentoria com especialista', 1000, 50, CURRENT_DATE()),
    ('e0000001-0000-0000-0000-000000000003', 'Acesso a Curso Avançado', 'Acesso a um curso especial avançado', 800, 30, CURRENT_DATE());