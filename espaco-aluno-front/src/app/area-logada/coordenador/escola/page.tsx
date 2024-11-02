'use client'

import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import * as z from 'zod'
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { DateRangePicker } from "@/components/ui/date-range-picker"

const schoolFormSchema = z.object({
  schoolName: z.string().min(3, 'O nome da escola deve ter pelo menos 3 caracteres'),
  academicPeriod: z.object({
    from: z.date().optional(),
    to: z.date().optional(),
  }, {
    required_error: "Por favor selecione o período letivo",
  }),
  registrationDeadline: z.object({
    from: z.date().optional(),
    to: z.date().optional(),
  }, {
    required_error: "Por favor selecione o prazo de inscrição",
  }),
})

type SchoolFormValues = z.infer<typeof schoolFormSchema>

export default function SchoolPage() {
  const form = useForm<SchoolFormValues>({
    resolver: zodResolver(schoolFormSchema),
    defaultValues: {
      schoolName: '',
    },
  })

  const onSubmit = async (data: SchoolFormValues) => {
    try {
      console.log(data)
      // Aqui você implementaria a lógica para salvar os dados
      alert('Dados da escola salvos com sucesso!')
    } catch (error) {
      console.error('Erro ao salvar dados:', error)
      alert('Erro ao salvar os dados da escola')
    }
  }

  return (
    <div className="w-full h-full flex justify-center items-center">
      <Card className='w-1/2'>
        <CardHeader>
          <CardTitle>Configurações da Escola</CardTitle>
          <CardDescription>
            Configure as informações básicas da sua instituição de ensino
          </CardDescription>
        </CardHeader>
        <form onSubmit={form.handleSubmit(onSubmit)}>
          <CardContent className="space-y-6">
            <div className="space-y-2">
              <Label htmlFor="schoolName">Nome da Escola</Label>
              <Input
                id="schoolName"
                {...form.register('schoolName')}
                placeholder="Digite o nome da escola"
              />
              {form.formState.errors.schoolName && (
                <p className="text-sm text-red-500">
                  {form.formState.errors.schoolName.message}
                </p>
              )}
            </div>

            <div className="space-y-2">
              <Label>Período Letivo</Label>
              <DateRangePicker
                from={form.getValues('academicPeriod')?.from}
                to={form.getValues('academicPeriod')?.to}
                onSelect={(range) => {
                  if (!range) return
                  form.setValue('academicPeriod', range)
                }}
              />
              {form.formState.errors.academicPeriod && (
                <p className="text-sm text-red-500">
                  {form.formState.errors.academicPeriod.message}
                </p>
              )}
            </div>

            <div className="space-y-2">
              <Label>Prazo de Inscrição</Label>
              <DateRangePicker
                from={form.getValues('registrationDeadline')?.from}
                to={form.getValues('registrationDeadline')?.to}
                onSelect={(range) => {
                  if (!range) return
                  form.setValue('registrationDeadline', range)
                }}
              />
              {form.formState.errors.registrationDeadline && (
                <p className="text-sm text-red-500">
                  {form.formState.errors.registrationDeadline.message}
                </p>
              )}
            </div>
          </CardContent>
          <CardFooter>
            <Button type="submit" className="w-full">
              Salvar Configurações
            </Button>
          </CardFooter>
        </form>
      </Card>
    </div>
  )
}