'use client'

import { useState } from 'react'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import { Badge } from "@/components/ui/badge"
import { Search } from 'lucide-react'
import {
  Pagination,
  PaginationContent,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination"
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogFooter,
} from "@/components/ui/dialog"

// Mock data for available subjects
const mockSubjects = [
  { id: 1, name: 'Matemática Básica', professor: 'Dr. Silva', enrolled: 25 },
  { id: 2, name: 'Física I', professor: 'Dra. Santos', enrolled: 20 },
  { id: 3, name: 'Química Geral', professor: 'Dr. Oliveira', enrolled: 30 },
  { id: 4, name: 'Biologia Celular', professor: 'Dra. Costa', enrolled: 38 },
  { id: 5, name: 'História do Brasil', professor: 'Dr. Ferreira', enrolled: 45 },
  { id: 6, name: 'Introdução à Engenharia', professor: 'Dr. Martins', enrolled: 50 },
  { id: 7, name: 'Psicologia Social', professor: 'Dra. Almeida', enrolled: 35 },
  { id: 8, name: 'Microeconomia', professor: 'Dr. Rodrigues', enrolled: 40 },
  { id: 9, name: 'Direito Constitucional', professor: 'Dra. Pereira', enrolled: 55 },
  { id: 10, name: 'História da Arte', professor: 'Dr. Sousa', enrolled: 28 },
]

const ITEMS_PER_PAGE = 5
const ENROLLMENT_DEADLINE = '2024-03-15T23:59:59'

export default function SubjectEnrollmentPage() {
  const [subjects, setSubjects] = useState(mockSubjects)
  const [searchTerm, setSearchTerm] = useState('')
  const [currentPage, setCurrentPage] = useState(1)
  const [selectedSubject, setSelectedSubject] = useState<typeof mockSubjects[0] | null>(null)
  const [isDialogOpen, setIsDialogOpen] = useState(false)
  const [enrolledSubjects, setEnrolledSubjects] = useState<number[]>([])

  const filteredSubjects = subjects.filter(subject =>
    subject.name.toLowerCase().includes(searchTerm.toLowerCase())
  )

  const totalPages = Math.ceil(filteredSubjects.length / ITEMS_PER_PAGE)
  const paginatedSubjects = filteredSubjects.slice(
    (currentPage - 1) * ITEMS_PER_PAGE,
    currentPage * ITEMS_PER_PAGE
  )

  const handleEnrollment = (subject: typeof mockSubjects[0]) => {
    setSelectedSubject(subject)
    setIsDialogOpen(true)
  }

  const confirmEnrollment = () => {
    if (selectedSubject) {
      if (enrolledSubjects.includes(selectedSubject.id)) {
        setEnrolledSubjects(enrolledSubjects.filter(id => id !== selectedSubject.id))
        setSubjects(subjects.map(s =>
          s.id === selectedSubject.id ? { ...s, enrolled: s.enrolled - 1 } : s
        ))
      } else {
        setEnrolledSubjects([...enrolledSubjects, selectedSubject.id])
        setSubjects(subjects.map(s =>
          s.id === selectedSubject.id ? { ...s, enrolled: s.enrolled + 1 } : s
        ))
      }
    }
    setIsDialogOpen(false)
  }

  return (
    <div className="container mx-auto py-10">
      <h1 className="text-3xl font-bold mb-6">Inscrição em Matérias</h1>

      <Card className="mb-6">
        <CardHeader>
          <CardTitle>Período de Inscrição</CardTitle>
          <CardDescription>As inscrições estão abertas até {new Date(ENROLLMENT_DEADLINE).toLocaleString()}</CardDescription>
        </CardHeader>
      </Card>

      <Card className="mb-6">
        <CardHeader>
          <CardTitle>Buscar Matérias</CardTitle>
          <CardDescription>Digite o nome da matéria para filtrar</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="flex items-center space-x-2">
            <Search className="w-5 h-5 text-gray-500" />
            <Input
              type="text"
              placeholder="Buscar matérias..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="flex-grow"
            />
          </div>
        </CardContent>
      </Card>

      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Nome da Matéria</TableHead>
            <TableHead>Professor</TableHead>
            <TableHead>Total de Inscritos</TableHead>
            <TableHead>Ação</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {paginatedSubjects.map((subject) => (
            <TableRow key={subject.id}>
              <TableCell>{subject.name}</TableCell>
              <TableCell>{subject.professor}</TableCell>
              <TableCell>
                <Badge variant="secondary">
                  {subject.enrolled}
                </Badge>
              </TableCell>
              <TableCell>
                <Button
                  onClick={() => handleEnrollment(subject)}
                  variant={enrolledSubjects.includes(subject.id) ? "destructive" : "default"}
                >
                  {enrolledSubjects.includes(subject.id) ? 'Desinscrever' : 'Inscrever-se'}
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      {filteredSubjects.length === 0 && (
        <p className="text-center mt-4 text-gray-500">Nenhuma matéria encontrada.</p>
      )}

      {filteredSubjects.length > ITEMS_PER_PAGE && (
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
      )}

      <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Confirmação de {enrolledSubjects.includes(selectedSubject?.id ?? -1) ? 'Desinscrição' : 'Inscrição'}</DialogTitle>
            <DialogDescription>
              {enrolledSubjects.includes(selectedSubject?.id ?? -1)
                ? `Tem certeza que deseja se desinscrever da matéria "${selectedSubject?.name}"?`
                : `Tem certeza que deseja se inscrever na matéria "${selectedSubject?.name}"?`}
            </DialogDescription>
          </DialogHeader>
          <DialogFooter>
            <Button variant="outline" onClick={() => setIsDialogOpen(false)}>Cancelar</Button>
            <Button onClick={confirmEnrollment}>Confirmar</Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </div>
  )
}