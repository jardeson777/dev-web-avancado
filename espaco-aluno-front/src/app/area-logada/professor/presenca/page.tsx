'use client'

import { useState, useEffect } from 'react'
import { format } from 'date-fns'
import { ptBR } from 'date-fns/locale'
import { Calendar } from "@/components/ui/calendar"
import { Button } from "@/components/ui/button"
import { Label } from "@/components/ui/label"
import { Switch } from "@/components/ui/switch"
import { Input } from "@/components/ui/input"
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

type Attendance = {
  date: string
  students: { id: number; present: boolean }[]
}

const ITEMS_PER_PAGE = 10

export default function AttendanceRegisterPage() {
  const [selectedDate, setSelectedDate] = useState<Date | undefined>(new Date())
  const [attendanceRecords, setAttendanceRecords] = useState<Attendance[]>([])
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

  const handleAttendanceChange = (studentId: number, isPresent: boolean) => {
    if (!selectedDate) return

    const dateString = format(selectedDate, 'yyyy-MM-dd')
    setAttendanceRecords(prevRecords => {
      const existingRecord = prevRecords.find(record => record.date === dateString)
      if (existingRecord) {
        return prevRecords.map(record =>
          record.date === dateString
            ? {
              ...record,
              students: record.students.map(student =>
                student.id === studentId ? { ...student, present: isPresent } : student
              )
            }
            : record
        )
      } else {
        return [
          ...prevRecords,
          {
            date: dateString,
            students: mockStudents.map(student => ({
              id: student.id,
              present: student.id === studentId ? isPresent : false
            }))
          }
        ]
      }
    })
  }

  const getAttendanceForDate = (date: Date) => {
    const dateString = format(date, 'yyyy-MM-dd')
    return attendanceRecords.find(record => record.date === dateString)?.students ||
      mockStudents.map(student => ({ id: student.id, present: false }))
  }

  const saveAttendance = () => {
    if (!selectedDate) return
    // Here you would typically send the attendance data to your backend
    console.log('Saving attendance for', format(selectedDate, 'dd/MM/yyyy'), attendanceRecords)
    alert('Presença registrada com sucesso!')
  }

  return (
    <div className="container mx-auto py-10">
      <h1 className="text-3xl font-bold mb-6">Registro de Presença</h1>
      <div className="flex flex-col lg:flex-row lg:space-x-6">
        <div className="w-fit mb-6 lg:mb-0">
          <Calendar
            mode="single"
            selected={selectedDate}
            onSelect={setSelectedDate}
            className="rounded-md border"
            locale={ptBR}
          />
        </div>
        <div className="w-full lg:w-2/3">
          <h2 className="text-2xl font-semibold mb-4">
            Presença para {selectedDate && format(selectedDate, 'dd/MM/yyyy')}
          </h2>
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
                <TableHead>Presente</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {selectedDate && paginatedStudents.map((student) => {
                const attendance = getAttendanceForDate(selectedDate).find(a => a.id === student.id) || { id: student.id, present: false }
                return (
                  <TableRow key={student.id}>
                    <TableCell>{student.name}</TableCell>
                    <TableCell>
                      <div className="flex items-center space-x-2">
                        <Switch
                          id={`attendance-${student.id}`}
                          checked={attendance.present}
                          onCheckedChange={(isPresent) => handleAttendanceChange(student.id, isPresent)}
                        />
                        <Label htmlFor={`attendance-${student.id}`} className="w-16">
                          {attendance.present ? 'Presente' : 'Ausente'}
                        </Label>
                      </div>
                    </TableCell>
                  </TableRow>
                )
              })}
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
                <PaginationItem key={page} className='cursor-pointer'>
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
          <Button onClick={saveAttendance} className="mt-4">Salvar Presença</Button>
        </div>
      </div>
    </div>
  )
}
