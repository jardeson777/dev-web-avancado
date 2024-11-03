'use client'

import { useState } from 'react'
import { Button } from "@/components/ui/button"
import { Progress } from "@/components/ui/progress"
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import { Badge } from "@/components/ui/badge"
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"

// Mock data for enrolled subjects
const mockEnrolledSubjects = [
  {
    id: 1,
    name: 'Matemática Avançada',
    professor: 'Dr. Silva',
    totalClasses: 20,
    attendedClasses: 15,
    maxAbsences: 6,
    classes: [
      { id: 1, date: '2024-03-01', topic: 'Introdução ao Cálculo', weight: 1, grade: 8.5 },
      { id: 2, date: '2024-03-08', topic: 'Limites e Continuidade', weight: 1, grade: 7.5 },
      { id: 3, date: '2024-03-15', topic: 'Derivadas', weight: 1.5, grade: 9.0 },
      { id: 4, date: '2024-03-22', topic: 'Aplicações de Derivadas', weight: 1.5, grade: 8.0 },
      { id: 5, date: '2024-03-29', topic: 'Integrais', weight: 2, grade: 7.0 },
      // ... outras aulas
    ],
    remainingClasses: [
      { id: 16, date: '2024-05-10', topic: 'Cálculo Diferencial', weight: 1.5 },
      { id: 17, date: '2024-05-17', topic: 'Cálculo Integral', weight: 1.5 },
      { id: 18, date: '2024-05-24', topic: 'Equações Diferenciais', weight: 2 },
      { id: 19, date: '2024-05-31', topic: 'Séries e Sequências', weight: 1.5 },
      { id: 20, date: '2024-06-07', topic: 'Revisão Final', weight: 1 },
    ],
  },
  {
    id: 2,
    name: 'Física Quântica',
    professor: 'Dra. Santos',
    totalClasses: 18,
    attendedClasses: 10,
    maxAbsences: 6,
    classes: [
      { id: 1, date: '2024-03-02', topic: 'Fundamentos da Mecânica Quântica', weight: 1, grade: 8.0 },
      { id: 2, date: '2024-03-09', topic: 'Dualidade Onda-Partícula', weight: 1.5, grade: 7.5 },
      { id: 3, date: '2024-03-16', topic: 'Equação de Schrödinger', weight: 2, grade: 9.0 },
      { id: 4, date: '2024-03-23', topic: 'Princípio da Incerteza', weight: 1.5, grade: 8.5 },
      // ... outras aulas
    ],
    remainingClasses: [
      { id: 13, date: '2024-05-11', topic: 'Tunelamento Quântico', weight: 1.5 },
      { id: 14, date: '2024-05-18', topic: 'Emaranhamento Quântico', weight: 2 },
      { id: 15, date: '2024-05-25', topic: 'Aplicações da Física Quântica', weight: 1.5 },
      { id: 16, date: '2024-06-01', topic: 'Interpretações da Mecânica Quântica', weight: 1 },
      { id: 17, date: '2024-06-08', topic: 'Revisão e Discussão Final', weight: 1 },
    ],
  },
]

export default function MySubjectsPage() {
  const [enrolledSubjects] = useState(mockEnrolledSubjects)
  const [selectedSubject, setSelectedSubject] = useState<typeof mockEnrolledSubjects[0] | null>(null)

  const calculateAverage = (classes: { weight: number; grade: number }[]) => {
    const totalWeight = classes.reduce((sum, class_) => sum + class_.weight, 0)
    const weightedSum = classes.reduce((sum, class_) => sum + class_.grade * class_.weight, 0)
    return (weightedSum / totalWeight).toFixed(2)
  }

  const calculateAttendancePercentage = (attended: number, total: number) => {
    return ((attended / total) * 100).toFixed(2)
  }

  const isFailedDueToAbsences = (subject: typeof mockEnrolledSubjects[0]) => {
    return subject.totalClasses - subject.attendedClasses > subject.maxAbsences
  }

  return (
    <div className="container mx-auto py-10">
      <h1 className="text-3xl font-bold mb-6">Minhas Disciplinas</h1>

      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Disciplina</TableHead>
            <TableHead>Professor</TableHead>
            <TableHead>Média</TableHead>
            <TableHead>Presença</TableHead>
            <TableHead>Status</TableHead>
            <TableHead>Ação</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {enrolledSubjects.map((subject) => (
            <TableRow key={subject.id}>
              <TableCell>{subject.name}</TableCell>
              <TableCell>{subject.professor}</TableCell>
              <TableCell>
                <Badge variant="secondary">
                  {calculateAverage(subject.classes)}
                </Badge>
              </TableCell>
              <TableCell>
                <Progress
                  value={parseFloat(calculateAttendancePercentage(subject.attendedClasses, subject.totalClasses))}
                  className="w-[100px]"
                />
              </TableCell>
              <TableCell>
                {isFailedDueToAbsences(subject) ? (
                  <Badge variant="destructive">Reprovado por falta</Badge>
                ) : (
                  <Badge variant="secondary">Cursando</Badge>
                )}
              </TableCell>
              <TableCell>
                <Dialog>
                  <DialogTrigger asChild>
                    <Button onClick={() => setSelectedSubject(subject)}>Ver Detalhes</Button>
                  </DialogTrigger>
                  <DialogContent className="max-w-[800px] max-h-[80vh] overflow-y-auto">
                    <DialogHeader>
                      <DialogTitle>{subject.name}</DialogTitle>
                      <DialogDescription>Professor: {subject.professor}</DialogDescription>
                    </DialogHeader>
                    <div className="space-y-4">
                      <div>
                        <h3 className="text-lg font-semibold mb-2">Presença</h3>
                        <Progress value={parseFloat(calculateAttendancePercentage(subject.attendedClasses, subject.totalClasses))} className="mb-2" />
                        <p>Aulas assistidas: {subject.attendedClasses} de {subject.totalClasses}</p>
                        <p>Faltas permitidas: {subject.maxAbsences}</p>
                        <p>Faltas atuais: {subject.totalClasses - subject.attendedClasses}</p>
                        <p>Faltas restantes: {Math.max(0, subject.maxAbsences - (subject.totalClasses - subject.attendedClasses))}</p>
                        {isFailedDueToAbsences(subject) && (
                          <p className="text-red-500 font-bold mt-2">Reprovado por falta</p>
                        )}
                      </div>

                      <div>
                        <h3 className="text-lg font-semibold mb-2">Aulas e Notas</h3>
                        <Table>
                          <TableHeader>
                            <TableRow>
                              <TableHead>Data</TableHead>
                              <TableHead>Tópico</TableHead>
                              <TableHead>Peso</TableHead>
                              <TableHead>Nota</TableHead>
                            </TableRow>
                          </TableHeader>
                          <TableBody>
                            {subject.classes.map((class_) => (
                              <TableRow key={class_.id}>
                                <TableCell>{class_.date}</TableCell>
                                <TableCell>{class_.topic}</TableCell>
                                <TableCell>{class_.weight}</TableCell>
                                <TableCell>{class_.grade?.toFixed(1) || '-'}</TableCell>
                              </TableRow>
                            ))}
                          </TableBody>
                        </Table>
                        <p className="mt-2 font-semibold">Média atual: {calculateAverage(subject.classes)}</p>
                      </div>

                      <div>
                        <h3 className="text-lg font-semibold mb-2">Próximas Aulas</h3>
                        <Table>
                          <TableHeader>
                            <TableRow>
                              <TableHead>Data</TableHead>
                              <TableHead>Tópico</TableHead>
                              <TableHead>Peso</TableHead>
                            </TableRow>
                          </TableHeader>
                          <TableBody>
                            {subject.remainingClasses.map((class_) => (
                              <TableRow key={class_.id}>
                                <TableCell>{class_.date}</TableCell>
                                <TableCell>{class_.topic}</TableCell>
                                <TableCell>{class_.weight}</TableCell>
                              </TableRow>
                            ))}
                          </TableBody>
                        </Table>
                      </div>
                    </div>
                  </DialogContent>
                </Dialog>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  )
}
