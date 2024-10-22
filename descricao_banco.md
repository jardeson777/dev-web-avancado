# Descrição do Banco de Dados

Com base no diagrama fornecido, segue a descrição das tabelas, atributos e relacionamentos do banco de dados.

---

## **Tabela: `school` (Escola)**

Armazena as informações sobre a escola, incluindo o período letivo e prazos de inscrição.

### **Atributos:**

- **`id`**: `String` (PK) – Identificador único da escola.
- **`start_class`**: `Date` – Data de início das aulas.
- **`end_class`**: `Date` – Data de término das aulas.
- **`start_registration`**: `Date` – Data de início das inscrições.
- **`end_registration`**: `Date` – Data final para inscrições.

---

## **Tabela: `student` (Estudante)**

Contém os dados dos estudantes da escola.

### **Atributos:**

- **`id`**: `String` (PK) – Identificador único do estudante.
- **`name`**: `String` – Nome do estudante.
- **`email`**: `String` – E-mail do estudante.
- **`password`**: `String` – Senha de acesso do estudante.
- **`type`**: `StudentType` – Tipo do estudante (`ENGAGED` ou `OBSERVER`).
- **`school_id`**: `String` (FK) – Identificador da escola à qual o estudante pertence.

---

## **Tabela: `teacher` (Professor)**

Armazena as informações dos professores da escola.

### **Atributos:**

- **`id`**: `String` (PK) – Identificador único do professor.
- **`name`**: `String` – Nome do professor.
- **`email`**: `String` – E-mail do professor.
- **`password`**: `String` – Senha de acesso do professor.
- **`school_id`**: `String` (FK) – Identificador da escola à qual o professor pertence.

---

## **Tabela: `coordinator` (Coordenador)**

Contém os dados dos coordenadores da escola.

### **Atributos:**

- **`id`**: `String` (PK) – Identificador único do coordenador.
- **`name`**: `String` – Nome do coordenador.
- **`email`**: `String` – E-mail do coordenador.
- **`password`**: `String` – Senha de acesso do coordenador.
- **`school_id`**: `String` (FK) – Identificador da escola à qual o coordenador pertence.

---

## **Tabela: `discipline` (Disciplina)**

Armazena as informações das disciplinas oferecidas pela escola.

### **Atributos:**

- **`id`**: `String` (PK) – Identificador único da disciplina.
- **`name`**: `String` – Nome da disciplina.
- **`teacher_id`**: `String` (FK) – Identificador do professor responsável pela disciplina.

---

## **Tabela: `classroom` (Sala de Aula)**

Representa uma sala de aula dentro de uma disciplina, incluindo data e peso.

### **Atributos:**

- **`id`**: `String` (PK) – Identificador único da sala de aula.
- **`discipline_id`**: `String` (FK) – Identificador da disciplina à qual a sala pertence.
- **`title`**: `String` – Título da aula.
- **`description`**: `String` – Descrição da aula.
- **`date_time`**: `DateTime` – Data e horário da aula.
- **`weight`**: `int` – Peso da aula para a avaliação.

---

## **Tabela: `student_discipline` (Inscrição do Estudante em Disciplina)**

Armazena as inscrições dos estudantes em disciplinas.

### **Atributos:**

- **`id`**: `String` (PK) – Identificador único da inscrição.
- **`student_id`**: `String` (FK) – Identificador do estudante inscrito.
- **`discipline_id`**: `String` (FK) – Identificador da disciplina em que o estudante está inscrito.

---

## **Tabela: `student_classroom` (Participação do Estudante em Aula)**

Armazena a participação dos estudantes nas aulas específicas de uma disciplina.

### **Atributos:**

- **`id`**: `String` (PK) – Identificador único do registro de participação.
- **`student_id`**: `String` (FK) – Identificador do estudante.
- **`classroom_id`**: `String` (FK) – Identificador da sala de aula.
- **`score`**: `float` – Nota do estudante na aula.
- **`presence`**: `boolean` – Indica se o estudante esteve presente na aula.

---

## **Enumeração: `StudentType` (Tipo de Estudante)**

Define os tipos de estudante.

- **`ENGAGED`**: Estudante participativo.
- **`OBSERVER`**: Estudante observador (apenas acompanha).

---

## **Relacionamentos**

1. **school - student/teacher/coordinator:**

   - Uma escola pode ter muitos estudantes, professores e coordenadores.
   - Cada estudante, professor e coordenador pertence a apenas uma escola.

2. **discipline - teacher:**

   - Cada disciplina é ministrada por um professor.

3. **discipline - classroom:**

   - Uma disciplina pode ter várias salas de aula.

4. **student - student_discipline:**

   - Um estudante pode se inscrever em várias disciplinas.
   - Cada inscrição conecta um estudante a uma disciplina.

5. **classroom - student_classroom:**
   - Um estudante pode frequentar várias salas de aula.
   - Cada sala de aula tem registros de presença e notas dos estudantes.

---

## **Observações**

- **Inscrição de Estudantes:**  
  O aluno pode escolher se inscrever como **participativo** ou **observador** em cada disciplina.

- **Registro de Presença:**  
  O professor é responsável por registrar a presença do aluno nas aulas.

- **Progresso do Estudante:**  
  O aluno pode consultar seu progresso em uma disciplina com base na nota acumulada e na quantidade de faltas.
