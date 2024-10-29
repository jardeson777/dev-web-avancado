# CRUD de Escola

## 1. Criar Escola

### Endpoint:

**POST** `/schools`

**STATUS 201**

#### DTO de Entrada:

```json
{
  "name": "Escola ABC",
  "start_class": "2024-01-01",
  "end_class": "2024-12-31",
  "start_registration": "2023-12-01",
  "end_registration": "2024-01-10"
}
```

#### DTO de Saída:

```json
{
  "id": "school-uuid"
}
```

## 2. Buscar Escola por id

### Endpoint:

**GET** `/school/:id`

**STATUS 200**

#### DTO de Saída:

```json
{
  "id": "school-uuid",
  "name": "Escola ABC",
  "start_class": "2024-01-01",
  "end_class": "2024-12-31",
  "start_registration": "2023-12-01",
  "end_registration": "2024-01-10"
}
```

**ou**

```json
{
  "error": "msg"
}
```

## 3. Editar Escola

### Endpoint:

**GET** `/school/:id`

**STATUS 200**

#### DTO de Entrada:

```json
{
  "id": "school-uuid",
  "name": "Escola ABC",
  "start_class": "2024-01-01",
  "end_class": "2024-12-31",
  "start_registration": "2023-12-01",
  "end_registration": "2024-01-10"
}
```

#### DTO de Saída:

```json
{
  "id": "school-uuid"
}
```

**ou**

```json
{
  "error": "msg"
}
```

## 4. Excluir Escola

### Endpoint:

**GET** `/school/:id`

**STATUS 200**

#### DTO de Saída:

```json
{
  "id": "school-uuid"
}
```

**ou**

```json
{
  "error": "msg"
}
```

# CRUD de Coordenador

## 1. Criar Coordenador

### Endpoint:

**POST** `/coordinators`

**STATUS 201**

#### DTO de Entrada:

```json
{
  "name": "Carlos Silva",
  "email": "carlos@email.com",
  "password": "123456",
  "school_id": "school-uuid"
}
```

## 2. Buscar Coordenador por id

### Endpoint:

**POST** `/coordinator/:id`

**STATUS 200**

#### DTO de Saída:

```json
{
  "name": "Carlos Silva",
  "email": "carlos@email.com",
  "password": "123456",
  "school_id": "school-uuid"
}
```

**ou**

```json
{
  "error": "msg"
}
```

# CRUD de Aluno e Professor

## 1. Criar Aluno

### Endpoint:

**POST** `/students`

**STATUS 201**

#### DTO de Entrada:

```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "123456",
  "type": "ENGAGED",
  "school_id": "school-uuid"
}
```

#### DTO de Saída:

```json
{
  "id": "student-uuid"
}
```

**ou**

```json
{
  "error": "msg"
}
```

## 2. Criar Aluno

### Endpoint:

**POST** `/teacher`

**STATUS 201**

#### DTO de Entrada:

```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "123456",
  "school_id": "school-uuid"
}
```

#### DTO de Saída:

```json
{
  "id": "teacher-uuid"
}
```

**ou**

```json
{
  "error": "msg"
}
```

# Criar Calendário e Aulas

## 1. Criar Calendário e Aulas

### Endpoint:

**POST** `/disciplines/{id}/calendar`

**STATUS 201**

#### DTO de Entrada:

```json
[
  {
    "title": "Aula 1",
    "description": "Introdução à disciplina",
    "date_time": "2024-01-15T08:00:00",
    "weight": 2
  },
  {
    "title": "Aula 2",
    "description": "Conceitos avançados",
    "date_time": "2024-01-22T08:00:00",
    "weight": 3
  }
]
```

#### DTO de Saída:

```json
{
  "id": "calendar-uuid",
  "discipline_id": "discipline-uuid",
  "created_at": "2023-12-01T10:00:00"
}
```

**ou**

```json
{
  "error": "msg"
}
```

# Criar Disciplina e Adicionar Professor à Disciplina

## 1. Criar Disciplina

### Endpoint:

**POST** `/disciplines`

**STATUS 201**

#### DTO de Entrada:

```json
{
  "name": "Matemática",
  "school_id": "school-uuid",
  "teacher_id": "teacher-uuid",
  "prerequisite_id": "discipline-uuid" // opcional, pode ser null
}
```

#### DTO de Saída:

```json
{
  "id": "discipline-uuid",
  "name": "Matemática",
  "school_id": "school-uuid",
  "teacher_id": "teacher-uuid",
  "prerequisite_id": "discipline-uuid" // opcional, pode ser null
}
```

**ou**

```json
{
  "error": "msg"
}
```

# Registrar Presença de um Aluno

## 1. Registrar Presença

### Endpoint:

**POST** `/classrooms/{classroom_id}/attendance`

**STATUS 201**

#### DTO de Entrada:

```json
{
  "student_id": "student-uuid",
  "presence": true
}
```

#### DTO de Saída:

```json
{
  "id": "attendance-uuid",
  "classroom_id": "classroom-uuid",
  "student_id": "student-uuid",
  "presence": true,
  "recorded_at": "2024-01-15T10:00:00"
}
```

**ou**

```json
{
  "error": "msg"
}
```

# Lançar Notas para um Aluno

## 1. Lançar Nota

### Endpoint:

**POST** `/classrooms/{classroom_id}/grades`

**STATUS 201**

#### DTO de Entrada:

```json
{
  "student_id": "student-uuid",
  "score": 8.5
}
```

#### DTO de Saída:

```json
{
  "id": "grade-uuid",
  "classroom_id": "classroom-uuid",
  "student_id": "student-uuid",
  "score": 8.5,
  "recorded_at": "2024-01-15T10:00:00"
}
```

**ou**

```json
{
  "error": "msg"
}
```

# Inscrição do Aluno em uma Disciplina

## 1. Inscrever-se em Disciplina

### Endpoint:

**POST** `/disciplines/{discipline_id}/enroll`

**STATUS 201**

#### DTO de Entrada:

```json
{
  "student_id": "student-uuid",
  "type": "ENGAGED" // tbm pode ser observer
}
```

#### DTO de Saída:

```json
{
  "id": "enrollment-uuid",
  "discipline_id": "discipline-uuid",
  "student_id": "student-uuid",
  "type": "ENGAGED",
  "enrolled_at": "2024-01-10T09:00:00"
}
```

**ou**

```json
{
  "error": "msg"
}
```

# Consultar Disciplinas Inscritas

## 1. Consultar Disciplinas Inscritas

### Endpoint:

**GET** `/students/{student_id}/disciplines`

**STATUS 200**

#### DTO de Saída:

```json
[
  {
    "id": "discipline-uuid-1",
    "name": "Matemática",
    "teacher": {
      "id": "teacher-uuid",
      "name": "Maria Santos"
    },
    "prerequisite_id": null
  },
  {
    "id": "discipline-uuid-2",
    "name": "História",
    "teacher": {
      "id": "teacher-uuid-2",
      "name": "Carlos Silva"
    },
    "prerequisite_id": "discipline-uuid-1"
  }
]
```

**ou**

```json
{
  "error": "msg"
}
```

# Visualizar Faltas

## 1. Visualizar Faltas

### Endpoint:

**GET** `/students/{student_id}/absences`

**STATUS 200**

#### DTO de Saída:

```json
[
  {
    "discipline_id": "discipline-uuid-1",
    "discipline_name": "Matemática",
    "total_classes": 10,
    "attended_classes": 8,
    "absences": 2
  },
  {
    "discipline_id": "discipline-uuid-2",
    "discipline_name": "História",
    "total_classes": 12,
    "attended_classes": 10,
    "absences": 2
  }
]
```

**ou**

```json
{
  "error": "msg"
}
```

# Consultar Progresso

## 1. Consultar Progresso

### Endpoint:

**GET** `/students/{student_id}/progress?discipline_id={discipline_id}`

**STATUS 200**

#### DTO de Saída:

```json
{
  "discipline_id": "discipline-uuid",
  "discipline_name": "Matemática",
  "total_score": 75.0,
  "required_score": 100.0,
  "remaining_score": 25.0,
  "status": "Em progresso"
}
```

**ou**

```json
{
  "error": "msg"
}
```
