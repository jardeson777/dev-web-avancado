'use client'

import { useState } from 'react'
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Progress } from "@/components/ui/progress"
import {
  Table,
  TableBody,
  TableCell,
  TableRow,
} from "@/components/ui/table"
import {
  PieChart,
  Pie,
  Cell,
  ResponsiveContainer,
  Tooltip,
  Legend
} from 'recharts'

// Mock data
const mockSchoolData = {
  totalStudents: 1200,
  totalTeachers: 80,
  totalDisciplines: 50,
  averageAttendance: 92,
  averageGrade: 7.8,
}

const mockStatusDistribution = [
  { name: 'Cursando', value: 800 },
  { name: 'Aprovados', value: 300 },
  { name: 'Reprovados', value: 70 },
  { name: 'Reprovados por Falta', value: 30 },
]

const mockGradeDistribution = [
  { name: 'Excelente (9-10)', value: 250 },
  { name: 'Bom (7-8.9)', value: 550 },
  { name: 'Regular (5-6.9)', value: 300 },
  { name: 'Insuficiente (<5)', value: 100 },
]

const STATUS_COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042']
const GRADE_COLORS = ['#4CAF50', '#8BC34A', '#FFC107', '#FF5722']

const mockSchoolInfo = {
  semesterStart: '2024-02-01',
  semesterEnd: '2024-06-30',
  enrollmentStart: '2024-01-15',
  enrollmentEnd: '2024-01-31',
}

export default function CoordinatorDashboardPage() {
  const [schoolData] = useState(mockSchoolData)
  const [statusDistribution] = useState(mockStatusDistribution)
  const [gradeDistribution] = useState(mockGradeDistribution)
  const [schoolInfo] = useState(mockSchoolInfo)

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('pt-BR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    })
  }

  return (
    <div className="container mx-auto py-10">
      <h1 className="text-3xl font-bold mb-6">Dashboard do Coordenador</h1>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-5 mb-8">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total de Alunos</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{schoolData.totalStudents}</div>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total de Professores</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{schoolData.totalTeachers}</div>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total de Disciplinas</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{schoolData.totalDisciplines}</div>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Média de Presença</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{schoolData.averageAttendance}%</div>
            <Progress value={schoolData.averageAttendance} className="mt-2" />
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Média Geral</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{schoolData.averageGrade.toFixed(1)}</div>
          </CardContent>
        </Card>
      </div>

      <div className="grid gap-4 md:grid-cols-2 mb-8">
        <Card>
          <CardHeader>
            <CardTitle>Distribuição de Status dos Alunos</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="h-[300px]">
              <ResponsiveContainer width="100%" height="100%">
                <PieChart>
                  <Pie
                    data={statusDistribution}
                    cx="50%"
                    cy="50%"
                    labelLine={false}
                    outerRadius={80}
                    fill="#8884d8"
                    dataKey="value"
                  >
                    {statusDistribution.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={STATUS_COLORS[index % STATUS_COLORS.length]} />
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
            <CardTitle>Distribuição de Médias dos Alunos</CardTitle>
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
                      <Cell key={`cell-${index}`} fill={GRADE_COLORS[index % GRADE_COLORS.length]} />
                    ))}
                  </Pie>
                  <Tooltip />
                  <Legend />
                </PieChart>
              </ResponsiveContainer>
            </div>
          </CardContent>
        </Card>
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Informações do Período Letivo</CardTitle>
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
  )
}
