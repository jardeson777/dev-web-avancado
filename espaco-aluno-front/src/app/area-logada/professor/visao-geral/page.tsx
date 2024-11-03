'use client'

import { useState, useEffect } from 'react'
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
import {
  PieChart,
  Pie,
  Cell,
  ResponsiveContainer,
  Tooltip,
  Legend
} from 'recharts'

// Mock data
const mockClassData = {
  totalClasses: 20,
  classesGiven: 12,
  averageAttendance: 85,
  classAverage: 7.5,
  studentsCount: 50,
  passingStudents: 35,
}

const mockPerformanceDistribution = [
  { name: 'Excelente (9-10)', value: 10 },
  { name: 'Bom (7-8.9)', value: 25 },
  { name: 'Regular (5-6.9)', value: 10 },
  { name: 'Insuficiente (<5)', value: 5 },
]

const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042']

export default function TeacherDashboardPage() {
  const [classData, setClassData] = useState(mockClassData)
  const [performanceDistribution, setPerformanceDistribution] = useState(mockPerformanceDistribution)

  // In a real application, you would fetch this data from your backend
  useEffect(() => {
    // Fetch class data
    // Fetch performance distribution data
  }, [])

  return (
    <div className="container mx-auto py-10">
      <h1 className="text-3xl font-bold mb-6">Dashboard do Professor</h1>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4 mb-8">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total de Aulas</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{classData.totalClasses}</div>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Aulas Ministradas</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{classData.classesGiven}</div>
            <p className="text-xs text-muted-foreground">
              {((classData.classesGiven / classData.totalClasses) * 100).toFixed(0)}% concluído
            </p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Média de Presença</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{classData.averageAttendance}%</div>
            <Progress value={classData.averageAttendance} className="mt-2" />
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Média da Turma</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{classData.classAverage.toFixed(1)}</div>
            <Progress value={classData.classAverage * 10} className="mt-2" />
          </CardContent>
        </Card>
      </div>

      <div className="grid gap-4 md:grid-cols-2 mb-8">
        <Card>
          <CardHeader>
            <CardTitle>Distribuição de Desempenho</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="h-[300px]">
              <ResponsiveContainer width="100%" height="100%">
                <PieChart>
                  <Pie
                    data={performanceDistribution}
                    cx="50%"
                    cy="50%"
                    labelLine={false}
                    outerRadius={80}
                    fill="#8884d8"
                    dataKey="value"
                  >
                    {performanceDistribution.map((entry, index) => (
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
            <CardTitle>Resumo da Turma</CardTitle>
          </CardHeader>
          <CardContent>
            <Table>
              <TableBody>
                <TableRow>
                  <TableCell className="font-medium">Total de Alunos</TableCell>
                  <TableCell>{classData.studentsCount}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell className="font-medium">Alunos Aprovados</TableCell>
                  <TableCell>{classData.passingStudents}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell className="font-medium">Taxa de Aprovação</TableCell>
                  <TableCell>{((classData.passingStudents / classData.studentsCount) * 100).toFixed(1)}%</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell className="font-medium">Aulas Restantes</TableCell>
                  <TableCell>{classData.totalClasses - classData.classesGiven}</TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Distribuição de Notas</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Faixa de Notas</TableHead>
                <TableHead>Número de Alunos</TableHead>
                <TableHead>Porcentagem</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {performanceDistribution.map((range) => (
                <TableRow key={range.name}>
                  <TableCell>{range.name}</TableCell>
                  <TableCell>{range.value}</TableCell>
                  <TableCell>{((range.value / classData.studentsCount) * 100).toFixed(1)}%</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </div>
  )
}
