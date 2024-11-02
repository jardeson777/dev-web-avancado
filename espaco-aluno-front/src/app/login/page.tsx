'use client'

import { useState } from 'react'
import Image from 'next/image'
import { Controller, useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import * as z from 'zod'
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { LoaderCircle } from 'lucide-react'
import WellcomeImage from '@/assets/image-wellcome-school.webp'
import Logo from '@/assets/logo-espaco-aluno.png'
import { RadioGroup, RadioGroupItem } from '../../components/ui/radio-group'

const loginSchema = z.object({
  email: z.string().email('Email inválido'),
  password: z.string().min(6, 'A senha deve ter pelo menos 6 caracteres'),
  userType: z.enum(['coordenador', 'professor', 'aluno'], {
    required_error: "Por favor, selecione um tipo de usuário",
  }),
})

type LoginFormValues = z.infer<typeof loginSchema>

export default function LoginPage() {
  const [isLoading, setIsLoading] = useState(false)

  const { register, handleSubmit, formState: { errors }, control } = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
  })

  const onSubmit = async (data: LoginFormValues) => {
    setIsLoading(true)
    // Aqui você implementaria a lógica de autenticação
    console.log(data)
    // Simula um delay de autenticação
    await new Promise(resolve => setTimeout(resolve, 2000))
    setIsLoading(false)
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="w-full max-w-4xl flex shadow-lg">
        <Card className="w-1/2 border-none rounded-r-none">
          <CardHeader className="space-y-1">
            <div className="flex justify-center h-[90px]">
              <Image
                src={Logo}
                alt="Logo da Escola"
                width={150}
                style={{ objectFit: 'cover' }}
              />
            </div>
            <CardTitle className="text-2xl text-center">Bem-vindo de volta!</CardTitle>
            <CardDescription className="text-center">
              Entre com suas credenciais para acessar o Espaço Aluno
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <form onSubmit={handleSubmit(onSubmit)}>
              <div className="space-y-2">
                <Label htmlFor="email">Email</Label>
                <Input
                  id="email"
                  type="email"
                  placeholder="seu@email.com"
                  {...register('email')}
                />
                {errors.email && (
                  <p className="text-sm text-red-500">{errors.email.message}</p>
                )}
              </div>
              <div className="space-y-2">
                <Label htmlFor="password">Senha</Label>
                <Input
                  id="password"
                  type="password"
                  {...register('password')}
                />
                {errors.password && (
                  <p className="text-sm text-red-500">{errors.password.message}</p>
                )}
              </div>
              <div className="space-y-2 mt-4">
                <Label>Tipo de Usuário</Label>
                <Controller
                  name="userType"
                  control={control}
                  render={({ field }) => (
                    <RadioGroup
                      onValueChange={field.onChange}
                      defaultValue={field.value}
                      className="flex flex-col space-y-1"
                    >
                      <div className="flex items-center space-x-2">
                        <RadioGroupItem value="coordenador" id="coordenador" />
                        <Label htmlFor="coordenador">Coordenador</Label>
                      </div>
                      <div className="flex items-center space-x-2">
                        <RadioGroupItem value="professor" id="professor" />
                        <Label htmlFor="professor">Professor</Label>
                      </div>
                      <div className="flex items-center space-x-2">
                        <RadioGroupItem value="aluno" id="aluno" />
                        <Label htmlFor="aluno">Aluno</Label>
                      </div>
                    </RadioGroup>
                  )}
                />
                {errors.userType && (
                  <p className="text-sm text-red-500">{errors.userType.message}</p>
                )}
              </div>
              <Button className="w-full mt-4" type="submit" disabled={isLoading}>
                {isLoading && (
                  <LoaderCircle className="mr-2 h-4 w-4 animate-spin" />
                )}
                Entrar
              </Button>
            </form>
          </CardContent>
          <CardFooter className="flex justify-center">
            <p className="text-sm text-gray-600">
              © 2024 Espaço Aluno. Todos os direitos reservados.
            </p>
          </CardFooter>
        </Card>
        <div className='w-1/2'>
          <div className='w-full h-[100%] relative rounded-xl rounded-l-none'>
            <Image
              src={WellcomeImage}
              alt="Imagem representativa da escola"
              className='w-full h-full rounded-xl rounded-l-none'
              style={{ objectFit: 'cover' }}
            />

            <div className='font-semibold text-white p-10 text-4xl gap-5 h-full absolute top-0 w-full flex flex-col justify-end bg-blue-900 bg-opacity-50 rounded-xl rounded-l-none'>
              <p>Bem vindo de volta!</p>
            </div>
          </div>
        </div>
      </div>
    </div >
  )
}