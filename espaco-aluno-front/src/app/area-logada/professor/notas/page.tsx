'use client'

import { useState, useEffect } from 'react'
import { format } from 'date-fns'
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import {
  Pagination,
  PaginationContent,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination"

// Mock data for students
const mockStudents = Array.from({ length: 50 }, (_, i) => ({
  id: i + 1,
  name: `Aluno ${i + 1}`,
}))

// Mock data for classes/activities
const mockClasses = [
  { id: 1, title: 'Introdução à Álgebra', date: '2024-02-15', weight: 1 },
  { id: 2, title: 'Geometria Plana', date: '2024-03-01', weight: 1.5 },
  { id: 3, title: 'Trigonometria Básica', date: '2024-03-15', weight: 2 },
]

type Grade = {
  studentId: number
  classId: number
  grade: number
}

const ITEMS_PER_PAGE = 10

export default function GradeRegisterPage() {
  const [selectedClass, setSelectedClass] = useState<typeof mockClasses[0] | null>(null)
  const [grades, setGrades] = useState<Grade[]>([])
  const [currentPage, setCurrentPage] = useState(1)
  const [searchTerm, setSearchTerm] = useState('')

  const filteredStudents = mockStudents.filter(student =>
    student.name.toLowerCase().includes(searchTerm.toLowerCase())
  )

  const totalPages = Math.ceil(filteredStudents.length / ITEMS_PER_PAGE)
  const paginatedStudents = filteredStudents.slice(
    (currentPage - 1) * ITEMS_PER_PAGE,
    currentPage * ITEMS_PER_PAGE
  )

  useEffect(() => {
    setCurrentPage(1)
  }, [searchTerm])

  const handleGradeChange = (studentId: number, grade: number) => {
    if (!selectedClass) return

    setGrades(prevGrades => {
      const existingGradeIndex = prevGrades.findIndex(g => g.studentId === studentId && g.classId === selectedClass.id)
      if (existingGradeIndex !== -1) {
        return prevGrades.map((g, index) =>
          index === existingGradeIndex ? { ...g, grade } : g
        )
      } else {
        return [...prevGrades, { studentId, classId: selectedClass.id, grade }]
      }
    })
  }

  const getGradeForStudent = (studentId: number) => {
    if (!selectedClass) return ''
    const grade = grades.find(g => g.studentId === studentId && g.classId === selectedClass.id)
    return grade ? grade.grade.toString() : ''
  }

  const saveGrades = () => {
    if (!selectedClass) return
    // Here you would typically send the grades data to your backend
    console.log('Saving grades for', selectedClass.title, grades)
    alert('Notas registradas com sucesso!')
  }

  return (
    <div className="container mx-auto py-10">
      <h1 className="text-3xl font-bold mb-6">Registro de Notas</h1>
      <div className="mb-6">
        <Label htmlFor="class-select" className="mb-2 block">Selecione a Aula/Atividade</Label>
        <Select onValueChange={(value) => setSelectedClass(mockClasses.find(c => c.id.toString() === value) || null)}>
          <SelectTrigger id="class-select" className="w-[300px]">
            <SelectValue placeholder="Selecione uma aula" />
          </SelectTrigger>
          <SelectContent>
            {mockClasses.map((classItem) => (
              <SelectItem key={classItem.id} value={classItem.id.toString()}>
                {classItem.title} - {format(new Date(classItem.date), 'dd/MM/yyyy')} (Peso: {classItem.weight})
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      </div>
      {selectedClass && (
        <div className="mb-6">
          <h2 className="text-2xl font-semibold mb-2">{selectedClass.title}</h2>
          <p className="text-gray-600">Data: {format(new Date(selectedClass.date), 'dd/MM/yyyy')} - Peso: {selectedClass.weight}</p>
        </div>
      )}
      <div className="mb-4">
        <Input
          type="text"
          placeholder="Buscar aluno por nome"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="max-w-sm"
        />
      </div>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Nome do Aluno</TableHead>
            <TableHead>Nota</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {paginatedStudents.map((student) => (
            <TableRow key={student.id}>
              <TableCell>{student.name}</TableCell>
              <TableCell>
                <Input
                  type="number"
                  min="0"
                  max="10"
                  step="0.1"
                  value={getGradeForStudent(student.id)}
                  onChange={(e) => handleGradeChange(student.id, parseFloat(e.target.value))}
                  className="w-20"
                  disabled={!selectedClass}
                />
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
      <Pagination className="mt-4">
        <PaginationContent>
          <PaginationItem>
            <PaginationPrevious
              onClick={() => setCurrentPage(prev => Math.max(prev - 1, 1))}
            />
          </PaginationItem>
          {Array.from({ length: totalPages }, (_, i) => i + 1).map((page) => (
            <PaginationItem key={page}>
              <PaginationLink
                onClick={() => setCurrentPage(page)}
                isActive={currentPage === page}
              >
                {page}
              </PaginationLink>
            </PaginationItem>
          ))}
          <PaginationItem>
            <PaginationNext
              onClick={() => setCurrentPage(prev => Math.min(prev + 1, totalPages))}
            />
          </PaginationItem>
        </PaginationContent>
      </Pagination>
      <Button onClick={saveGrades} className="mt-4" disabled={!selectedClass}>Salvar Notas</Button>
    </div>
  )
}
