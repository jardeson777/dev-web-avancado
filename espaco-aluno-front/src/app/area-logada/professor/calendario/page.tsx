'use client'

import { useState, useEffect } from 'react'
import { useForm, Controller } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import * as z from 'zod'
import { format, isWithinInterval, parseISO } from 'date-fns'
import { ptBR } from 'date-fns/locale'
import { Calendar } from "@/components/ui/calendar"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Textarea } from "@/components/ui/textarea"
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"

// Mock data
const mockTeacher = {
  name: 'João Silva',
  discipline: 'Matemática',
}

const mockAcademicPeriod = {
  start: '2024-02-01',
  end: '2024-06-30',
}

const mockClasses = [
  { id: 1, title: 'Introdução à Álgebra', description: 'Conceitos básicos de álgebra', date: '2024-02-15', weight: 1 },
  { id: 2, title: 'Geometria Plana', description: 'Estudo de formas geométricas', date: '2024-03-01', weight: 1.5 },
]

const classFormSchema = z.object({
  title: z.string().min(3, 'O título deve ter pelo menos 3 caracteres'),
  description: z.string().min(10, 'A descrição deve ter pelo menos 10 caracteres'),
  date: z.date({
    required_error: "A data da aula é obrigatória",
  }),
  weight: z.number().min(0.5, 'O peso deve ser no mínimo 0.5').max(3, 'O peso deve ser no máximo 3'),
})

type ClassFormValues = z.infer<typeof classFormSchema>

export default function TeacherCalendarPage() {
  const [classes, setClasses] = useState(mockClasses)
  const [selectedDate, setSelectedDate] = useState<Date | undefined>(new Date())
  const [isDialogOpen, setIsDialogOpen] = useState(false)
  const [selectedClass, setSelectedClass] = useState<(typeof mockClasses)[0] | null>(null)
  const [isDetailsDialogOpen, setIsDetailsDialogOpen] = useState(false)

  const { control, register, handleSubmit, formState: { errors }, setValue, reset } = useForm<ClassFormValues>({
    resolver: zodResolver(classFormSchema),
    defaultValues: {
      title: '',
      description: '',
      weight: 1,
      date: selectedDate,
    },
  })

  useEffect(() => {
    if (selectedDate) {
      setValue('date', selectedDate)
    }
  }, [selectedDate, setValue])

  const onSubmit = (data: ClassFormValues) => {
    if (!isWithinAcademicPeriod(data.date)) {
      alert('A data da aula deve estar dentro do período letivo')
      return
    }

    const newClass = {
      id: classes.length + 1,
      ...data,
      date: format(data.date, 'yyyy-MM-dd'),
    }

    setClasses([...classes, newClass])
    setIsDialogOpen(false)
    reset()
  }

  const deleteClass = (id: number) => {
    setClasses(classes.filter(c => c.id !== id))
    setSelectedClass(null)
    setIsDetailsDialogOpen(false)
  }

  const isWithinAcademicPeriod = (date: Date) => {
    return isWithinInterval(date, {
      start: parseISO(mockAcademicPeriod.start),
      end: parseISO(mockAcademicPeriod.end),
    })
  }

  const classesForDate = (date: Date) => {
    return classes.filter(c => c.date === format(date, 'yyyy-MM-dd'))
  }

  return (
    <div className="container mx-auto py-10">
      <h1 className="text-3xl font-bold mb-6">Calendário de Aulas - {mockTeacher.discipline}</h1>
      <div className="flex space-x-6">
        <div className="w-fit">
          <Calendar
            mode="single"
            selected={selectedDate}
            onSelect={setSelectedDate}
            className="rounded-md border"
            locale={ptBR}
            modifiers={{
              hasClass: (date) => classesForDate(date).length > 0,
            }}
            modifiersStyles={{
              hasClass: { backgroundColor: 'rgba(0, 120, 255, 0.1)' },
            }}
          />
        </div>
        <div className="w-1/2">
          <h2 className="text-2xl font-semibold mb-4">
            Aulas para {selectedDate && format(selectedDate, 'dd/MM/yyyy')}
          </h2>
          {selectedDate && classesForDate(selectedDate).map(classItem => (
            <div key={classItem.id} className="mb-4 p-4 border rounded-md">
              <h3 className="text-xl font-semibold">{classItem.title}</h3>
              <p className="text-gray-600">{classItem.description}</p>
              <p className="text-sm text-gray-500">Peso: {classItem.weight}</p>
              <div className="mt-2">
                <Button
                  variant="outline"
                  className="mr-2"
                  onClick={() => {
                    setSelectedClass(classItem)
                    setIsDetailsDialogOpen(true)
                  }}
                >
                  Ver Detalhes
                </Button>
                <Button variant="destructive" onClick={() => deleteClass(classItem.id)}>
                  Excluir
                </Button>
              </div>
            </div>
          ))}
          {(!selectedDate || classesForDate(selectedDate).length === 0) ? (
            <p>Nenhuma aula cadastrada para esta data.</p>
          ) : null}
          {(!selectedDate || classesForDate(selectedDate).length === 0) && (
            <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
              <DialogTrigger asChild>
                <Button className="mt-4">Cadastrar Nova Aula</Button>
              </DialogTrigger>
              <DialogContent>
                <DialogHeader>
                  <DialogTitle>Cadastrar Nova Aula</DialogTitle>
                  <DialogDescription>
                    Preencha os detalhes da nova aula abaixo.
                  </DialogDescription>
                </DialogHeader>
                <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                  <div>
                    <Label htmlFor="title">Título</Label>
                    <Input id="title" {...register('title')} />
                    {errors.title && (
                      <p className="text-sm text-red-500">{errors.title.message}</p>
                    )}
                  </div>
                  <div>
                    <Label htmlFor="description">Descrição</Label>
                    <Textarea id="description" {...register('description')} />
                    {errors.description && (
                      <p className="text-sm text-red-500">{errors.description.message}</p>
                    )}
                  </div>
                  <div>
                    <Label htmlFor="date">Data</Label>
                    <Controller
                      name="date"
                      control={control}
                      render={({ field }) => (
                        <Input
                          id="date"
                          type="date"
                          {...field}
                          value={field.value ? format(field.value, 'yyyy-MM-dd') : ''}
                          onChange={(e) => field.onChange(e.target.valueAsDate)}
                          min={mockAcademicPeriod.start}
                          max={mockAcademicPeriod.end}
                        />
                      )}
                    />
                    {errors.date && (
                      <p className="text-sm text-red-500">{errors.date.message}</p>
                    )}
                  </div>
                  <div>
                    <Label htmlFor="weight">Peso</Label>
                    <Input
                      id="weight"
                      type="number"
                      step="0.1"
                      {...register('weight', { valueAsNumber: true })}
                    />
                    {errors.weight && (
                      <p className="text-sm text-red-500">{errors.weight.message}</p>
                    )}
                  </div>
                  <Button type="submit" className="w-full">Cadastrar Aula</Button>
                </form>
              </DialogContent>
            </Dialog>
          )}

        </div>
      </div>
      <Dialog open={isDetailsDialogOpen} onOpenChange={setIsDetailsDialogOpen}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Detalhes da Aula</DialogTitle>
          </DialogHeader>
          {selectedClass && (
            <div className="mt-4">
              <h3 className="text-lg font-semibold">{selectedClass.title}</h3>
              <p className="text-sm text-gray-600 mt-2">{selectedClass.description}</p>
              <p className="text-sm text-gray-500 mt-1">Data: {format(parseISO(selectedClass.date), 'dd/MM/yyyy')}</p>
              <p className="text-sm text-gray-500">Peso: {selectedClass.weight}</p>
              <div className="mt-4 flex justify-end space-x-2">
                <Button variant="outline" onClick={() => setIsDetailsDialogOpen(false)}>Fechar</Button>
                <Button variant="destructive" onClick={() => deleteClass(selectedClass.id)}>Excluir</Button>
              </div>
            </div>
          )}
        </DialogContent>
      </Dialog>
    </div>
  )
}
