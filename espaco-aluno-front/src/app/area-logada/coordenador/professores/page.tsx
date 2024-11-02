'use client'

import { useState } from 'react'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import * as z from 'zod'
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"
import { PlusCircle, Eye, EyeOff } from 'lucide-react'

// Mock data for teachers
const mockTeachers = [
  { id: 1, name: 'Ana Rodrigues', email: 'ana@example.com' },
  { id: 2, name: 'Carlos Ferreira', email: 'carlos@example.com' },
  { id: 3, name: 'Beatriz Lima', email: 'beatriz@example.com' },
]

const teacherFormSchema = z.object({
  name: z.string().min(3, 'O nome deve ter pelo menos 3 caracteres'),
  email: z.string().email('Email inválido'),
  password: z.string().min(6, 'A senha deve ter pelo menos 6 caracteres'),
  confirmPassword: z.string(),
}).refine((data) => data.password === data.confirmPassword, {
  message: "As senhas não coincidem",
  path: ["confirmPassword"],
})

type TeacherFormValues = z.infer<typeof teacherFormSchema>

export default function TeachersPage() {
  const [teachers, setTeachers] = useState(mockTeachers)
  const [isDialogOpen, setIsDialogOpen] = useState(false)
  const [showPassword, setShowPassword] = useState(false)
  const [showConfirmPassword, setShowConfirmPassword] = useState(false)

  const form = useForm<TeacherFormValues>({
    resolver: zodResolver(teacherFormSchema),
    defaultValues: {
      name: '',
      email: '',
      password: '',
      confirmPassword: '',
    },
  })

  const onSubmit = async (data: TeacherFormValues) => {
    try {
      // Here you would typically send this data to your backend
      console.log(data)

      // For now, we'll just add it to our local state
      setTeachers([...teachers, { id: teachers.length + 1, name: data.name, email: data.email }])

      setIsDialogOpen(false)
      form.reset()
      alert('Professor cadastrado com sucesso!')
    } catch (error) {
      console.error('Erro ao cadastrar professor:', error)
      alert('Erro ao cadastrar professor')
    }
  }

  const togglePasswordVisibility = (field: 'password' | 'confirmPassword') => {
    if (field === 'password') {
      setShowPassword(!showPassword)
    } else {
      setShowConfirmPassword(!showConfirmPassword)
    }
  }

  return (
    <div className="container mx-auto py-10">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold">Professores</h1>
        <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
          <DialogTrigger asChild>
            <Button>
              <PlusCircle className="mr-2 h-4 w-4" />
              Cadastrar Professor
            </Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Cadastrar Novo Professor</DialogTitle>
              <DialogDescription>
                Preencha os dados do novo professor abaixo.
              </DialogDescription>
            </DialogHeader>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
              <div>
                <Label htmlFor="name">Nome</Label>
                <Input id="name" {...form.register('name')} />
                {form.formState.errors.name && (
                  <p className="text-sm text-red-500">{form.formState.errors.name.message}</p>
                )}
              </div>
              <div>
                <Label htmlFor="email">Email</Label>
                <Input id="email" type="email" {...form.register('email')} />
                {form.formState.errors.email && (
                  <p className="text-sm text-red-500">{form.formState.errors.email.message}</p>
                )}
              </div>
              <div>
                <Label htmlFor="password">Senha</Label>
                <div className="relative">
                  <Input
                    id="password"
                    type={showPassword ? "text" : "password"}
                    {...form.register('password')}
                  />
                  <button
                    type="button"
                    onClick={() => togglePasswordVisibility('password')}
                    className="absolute inset-y-0 right-0 pr-3 flex items-center text-gray-400 hover:text-gray-600"
                  >
                    {showPassword ? (
                      <EyeOff className="h-5 w-5" />
                    ) : (
                      <Eye className="h-5 w-5" />
                    )}
                  </button>
                </div>
                {form.formState.errors.password && (
                  <p className="text-sm text-red-500">{form.formState.errors.password.message}</p>
                )}
              </div>
              <div>
                <Label htmlFor="confirmPassword">Confirmar Senha</Label>
                <div className="relative">
                  <Input
                    id="confirmPassword"
                    type={showConfirmPassword ? "text" : "password"}
                    {...form.register('confirmPassword')}
                  />
                  <button
                    type="button"
                    onClick={() => togglePasswordVisibility('confirmPassword')}
                    className="absolute inset-y-0 right-0 pr-3 flex items-center text-gray-400 hover:text-gray-600"
                  >
                    {showConfirmPassword ? (
                      <EyeOff className="h-5 w-5" />
                    ) : (
                      <Eye className="h-5 w-5" />
                    )}
                  </button>
                </div>
                {form.formState.errors.confirmPassword && (
                  <p className="text-sm text-red-500">{form.formState.errors.confirmPassword.message}</p>
                )}
              </div>
              <Button type="submit" className="w-full">Cadastrar</Button>
            </form>
          </DialogContent>
        </Dialog>
      </div>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Nome</TableHead>
            <TableHead>Email</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {teachers.map((teacher) => (
            <TableRow key={teacher.id}>
              <TableCell>{teacher.name}</TableCell>
              <TableCell>{teacher.email}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  )
}