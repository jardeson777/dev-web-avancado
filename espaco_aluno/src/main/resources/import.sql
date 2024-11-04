INSERT INTO school (id, name, start_class, end_class, start_registration, end_registration)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'Escola de Teste', '2024-01-10', '2024-12-20', '2023-12-01', '2024-01-09');

INSERT INTO coordinator (id, name, email, password, school_id)
VALUES ('d8f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'João Silva', 'joao.silva@example.com', 'senha123', '550e8400-e29b-41d4-a716-446655440000');

INSERT INTO teacher (id, name, email, password, is_active, school_id)
VALUES ('a2f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'Maria Oliveira', 'maria.oliveira@example.com', 'senha123', TRUE, '550e8400-e29b-41d4-a716-446655440000');

INSERT INTO student (id, name, email, password, is_active, school_id)
VALUES ('b3f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'Carlos Pereira', 'carlos.pereira@example.com', 'senha123', TRUE, '550e8400-e29b-41d4-a716-446655440000');

INSERT INTO discipline (id, name, teacher_id)
VALUES ('c4f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'Matemática', 'a2f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2');

INSERT INTO classroom (id, discipline_id, title, description, date_time, weight) VALUES
                                                                                     ('e1f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'c4f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'Sala 101', 'Aula de Matemática - Introdução', '2024-01-10 09:00:00', 4),
                                                                                     ('e2f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'c4f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'Sala 102', 'Aula de Matemática - Números Inteiros', '2024-01-10 10:00:00', 3),
                                                                                     ('e3f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'c4f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'Sala 103', 'Aula de Matemática - Frações', '2024-01-10 11:00:00', 3),
                                                                                     ('e4f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'c4f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'Sala 104', 'Aula de Matemática - Geometria', '2024-01-10 12:00:00', 0),
                                                                                     ('e5f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'c4f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'Sala 105', 'Aula de Matemática - Estatística', '2024-01-10 13:00:00', 0),
                                                                                     ('e6f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'c4f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'Sala 106', 'Aula de Matemática - Probabilidades', '2024-01-10 14:00:00', 0),
                                                                                     ('e7f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'c4f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'Sala 107', 'Aula de Matemática - Funções', '2024-01-10 15:00:00', 0),
                                                                                     ('e8f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'c4f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'Sala 108', 'Aula de Matemática - Sistemas Lineares', '2024-01-10 16:00:00', 0),
                                                                                     ('e9f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'c4f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'Sala 109', 'Aula de Matemática - Análise de Dados', '2024-01-10 17:00:00', 0),
                                                                                     ('f0f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'c4f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'Sala 110', 'Aula de Matemática - Revisão', '2024-01-10 18:00:00', 0);

-- Inserindo associação de aluno à disciplina
INSERT INTO student_discipline (id, student_id, discipline_id, student_type, status) VALUES
    ('f1f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'b3f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'c4f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'ENGAGED', 'ENROLLED');

-- Inserindo associação de aluno às salas de aula com peso
INSERT INTO student_classroom (id, student_id, classroom_id, score, presence) VALUES
                                                                                  ('g1f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'b3f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'e1f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 8.5, TRUE),
                                                                                  ('g2f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'b3f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'e2f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 7.0, TRUE),
                                                                                  ('g3f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'b3f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'e3f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 9.0, TRUE),
                                                                                  ('g4f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'b3f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'e4f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', NULL, FALSE), -- Sala sem peso
                                                                                  ('g5f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'b3f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'e5f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', NULL, TRUE),  -- Sala sem peso
                                                                                  ('g6f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'b3f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', 'e6f50f80-e4c2-4c1b-a5bb-9b1b0e69c6d2', NULL, FALSE); -- Sala sem peso