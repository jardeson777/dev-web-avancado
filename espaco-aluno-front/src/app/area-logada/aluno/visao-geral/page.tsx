'use client'

import { useState } from 'react'
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
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
  PieChart,
  Pie,
  Cell,
  ResponsiveContainer,
  Tooltip,
  Legend
} from 'recharts'

// Mock data
const mockStudentData = {
  name: 'João Silva',
  course: 'Engenharia de Software',
  overallGPA: 8.2,
  enrolledDisciplines: 5,
}

const mockDisciplines = [
  { id: 1, name: 'Matemática Avançada', grade: 8.5, absences: 2, totalClasses: 20, status: 'cursando' },
  { id: 2, name: 'Programação Orientada a Objetos', grade: 9.0, absences: 1, totalClasses: 18, status: 'aprovado' },
  { id: 3, name: 'Estruturas de Dados', grade: 5.5, absences: 3, totalClasses: 22, status: 'reprovado' },
  { id: 4, name: 'Banco de Dados', grade: 8.7, absences: 0, totalClasses: 16, status: 'cursando' },
  { id: 5, name: 'Engenharia de Software', grade: 0, absences: 12, totalClasses: 20, status: 'reprovado por falta' },
]

const gradeDistribution = [
  { name: 'Excelente (9-10)', value: 2 },
  { name: 'Bom (7-8.9)', value: 2 },
  { name: 'Regular (5-6.9)', value: 1 },
  { name: 'Insuficiente (<5)', value: 0 },
]

const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042']

const mockSchoolInfo = {
  semesterStart: '2024-02-01',
  semesterEnd: '2024-06-30',
  enrollmentStart: '2024-01-15',
  enrollmentEnd: '2024-01-31',
}

export default function StudentDashboardPage() {
  const [studentData] = useState(mockStudentData)
  const [disciplines] = useState(mockDisciplines)
  const [schoolInfo] = useState(mockSchoolInfo)

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('pt-BR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    })
  }

  const calculateSemesterProgress = () => {
    const start = new Date(schoolInfo.semesterStart).getTime()
    const end = new Date(schoolInfo.semesterEnd).getTime()
    const now = new Date().getTime()
    const progress = ((now - start) / (end - start)) * 100
    return Math.min(Math.max(progress, 0), 100) // Ensure progress is between 0 and 100
  }

  const semesterProgress = calculateSemesterProgress()

  const getStatusBadge = (status: string) => {
    switch (status) {
      case 'cursando':
        return <Badge variant="secondary">Cursando</Badge>
      case 'aprovado':
        return <Badge variant="default">Aprovado</Badge>
      case 'reprovado':
        return <Badge variant="destructive">Reprovado</Badge>
      case 'reprovado por falta':
        return <Badge variant="destructive">Reprovado por Falta</Badge>
      default:
        return <Badge variant="outline">Desconhecido</Badge>
    }
  }

  return (
    <div className="container mx-auto py-10">
      <h1 className="text-3xl font-bold mb-6">Dashboard do Aluno</h1>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3 mb-8">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Média Geral</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{studentData.overallGPA.toFixed(1)}</div>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Disciplinas Cursando</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{studentData.enrolledDisciplines}</div>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Progresso do Período</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{semesterProgress.toFixed(0)}%</div>
            <Progress value={semesterProgress} className="mt-2" />
          </CardContent>
        </Card>
      </div>

      <div className="grid gap-4 md:grid-cols-2 mb-8">
        <Card>
          <CardHeader>
            <CardTitle>Distribuição de Notas</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="h-[300px]">
              <ResponsiveContainer width="100%" height="100%">
                <PieChart>
                  <Pie
                    data={gradeDistribution}
                    cx="50%"
                    cy="50%"
                    labelLine={false}
                    outerRadius={80}
                    fill="#8884d8"
                    dataKey="value"
                  >
                    {gradeDistribution.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                    ))}
                  </Pie>
                  <Tooltip />
                  <Legend />
                </PieChart>
              </ResponsiveContainer>
            </div>
          </CardContent>
        </Card>
        <Card>
          <CardHeader>
            <CardTitle>Informações da Escola</CardTitle>
          </CardHeader>
          <CardContent>
            <Table>
              <TableBody>
                <TableRow>
                  <TableCell className="font-medium">Início do Período Letivo</TableCell>
                  <TableCell>{formatDate(schoolInfo.semesterStart)}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell className="font-medium">Fim do Período Letivo</TableCell>
                  <TableCell>{formatDate(schoolInfo.semesterEnd)}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell className="font-medium">Início das Inscrições</TableCell>
                  <TableCell>{formatDate(schoolInfo.enrollmentStart)}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell className="font-medium">Fim das Inscrições</TableCell>
                  <TableCell>{formatDate(schoolInfo.enrollmentEnd)}</TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Desempenho nas Disciplinas</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Disciplina</TableHead>
                <TableHead>Nota</TableHead>
                <TableHead>Faltas</TableHead>
                <TableHead>Status</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {disciplines.map((discipline) => (
                <TableRow key={discipline.id}>
                  <TableCell>{discipline.name}</TableCell>
                  <TableCell>{discipline.grade.toFixed(1)}</TableCell>
                  <TableCell>{discipline.absences} / {discipline.totalClasses}</TableCell>
                  <TableCell>
                    {getStatusBadge(discipline.status)}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </div>
  )
}
