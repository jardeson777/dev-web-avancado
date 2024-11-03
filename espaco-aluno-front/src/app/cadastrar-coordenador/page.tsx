'use client'

import { useState } from 'react'
import { useRouter } from 'next/navigation'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import * as z from 'zod'
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { EyeIcon, EyeOffIcon, ArrowLeftIcon } from 'lucide-react'

const personalInfoSchema = z.object({
  name: z.string().min(3, 'O nome deve ter pelo menos 3 caracteres'),
  email: z.string().email('Email inválido'),
  password: z.string().min(8, 'A senha deve ter pelo menos 8 caracteres'),
  confirmPassword: z.string(),
  schoolName: z.string().optional(),
})
  .refine((data) => data.password === data.confirmPassword, {
    message: "As senhas não coincidem",
    path: ["confirmPassword"],
  })

type FormData = z.infer<typeof personalInfoSchema>

const steps = ['Informações Pessoais', 'Informações da Escola', 'Conclusão']

export default function CadastroCoordenador() {
  const [currentStep, setCurrentStep] = useState(0)
  const [showPassword, setShowPassword] = useState(false)
  const [showConfirmPassword, setShowConfirmPassword] = useState(false)
  const router = useRouter()

  const { register, handleSubmit, formState: { errors, isValid }, getValues, setError } = useForm<FormData>({
    resolver: zodResolver(personalInfoSchema),
    defaultValues: {
      name: '',
      email: '',
      password: '',
      confirmPassword: '',
      schoolName: '',
    },
  })

  const onSubmit = async (data: FormData) => {
    if (currentStep < steps.length - 1) {
      if (currentStep === 1 && !data.schoolName) {
        setError('schoolName', {
          type: 'manual',
          message: 'Por favor, preencha o nome da escola'
        })
        return
      }

      if (isValid) {
        setCurrentStep((prev) => prev + 1)
      }
    } else {
      console.log(data)
      await new Promise(resolve => setTimeout(resolve, 2000))
      alert('Cadastro realizado com sucesso!')
    }
  }

  const handlePrevious = () => {
    setCurrentStep((prev) => prev - 1)
  }

  const togglePasswordVisibility = (field: 'password' | 'confirmPassword') => {
    if (field === 'password') {
      setShowPassword(!showPassword)
    } else {
      setShowConfirmPassword(!showConfirmPassword)
    }
  }

  const handleBackToLogin = () => {
    router.push('/login')
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 p-4">
      <Card className="w-full max-w-lg">
        <CardHeader>
          <Button
            variant="ghost"
            className="absolute top-2 left-2 p-2"
            onClick={handleBackToLogin}
          >
            <ArrowLeftIcon className="h-4 w-4 mr-2" />
            Voltar para Login
          </Button>
          <CardTitle className="text-2xl text-center mt-8">Cadastro de Coordenador</CardTitle>
          <CardDescription className="text-center">
            {steps[currentStep]}
          </CardDescription>
        </CardHeader>
        <CardContent>
          <div className="mb-8">
            <div className="flex justify-around">
              {steps.map((step, index) => (
                <div key={step} className="flex flex-col items-center">
                  <div className={`w-10 h-10 rounded-full flex items-center justify-center ${index <= currentStep ? 'bg-primary text-primary-foreground' : 'bg-muted text-muted-foreground'
                    }`}>
                    {index + 1}
                  </div>
                </div>
              ))}
            </div>
            <div className="mt-2 h-2 bg-muted rounded-full">
              <div
                className="h-full bg-primary rounded-full transition-all duration-300 ease-in-out"
                style={{ width: `${((currentStep + 1) / steps.length) * 100}%` }}
              ></div>
            </div>
          </div>
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            {currentStep === 0 && (
              <>
                <div className="space-y-2">
                  <Label htmlFor="name">Nome Completo</Label>
                  <Input id="name" {...register('name')} />
                  {errors.name && <p className="text-sm text-red-500">{errors.name.message}</p>}
                </div>
                <div className="space-y-2">
                  <Label htmlFor="email">Email</Label>
                  <Input id="email" type="email" {...register('email')} />
                  {errors.email && <p className="text-sm text-red-500">{errors.email.message}</p>}
                </div>
                <div className="space-y-2">
                  <Label htmlFor="password">Senha</Label>
                  <div className="relative">
                    <Input
                      id="password"
                      type={showPassword ? "text" : "password"}
                      {...register('password')}
                    />
                    <button
                      type="button"
                      onClick={() => togglePasswordVisibility('password')}
                      className="absolute inset-y-0 right-0 pr-3 flex items-center text-gray-400 hover:text-gray-600"
                    >
                      {showPassword ? (
                        <EyeOffIcon className="h-5 w-5" />
                      ) : (
                        <EyeIcon className="h-5 w-5" />
                      )}
                    </button>
                  </div>
                  {errors.password && <p className="text-sm text-red-500">{errors.password.message}</p>}
                </div>
                <div className="space-y-2">
                  <Label htmlFor="confirmPassword">Confirmar Senha</Label>
                  <div className="relative">
                    <Input
                      id="confirmPassword"
                      type={showConfirmPassword ? "text" : "password"}
                      {...register('confirmPassword')}
                    />
                    <button
                      type="button"
                      onClick={() => togglePasswordVisibility('confirmPassword')}
                      className="absolute inset-y-0 right-0 pr-3 flex items-center text-gray-400 hover:text-gray-600"
                    >
                      {showConfirmPassword ? (
                        <EyeOffIcon className="h-5 w-5" />
                      ) : (
                        <EyeIcon className="h-5 w-5" />
                      )}
                    </button>
                  </div>
                  {errors.confirmPassword && <p className="text-sm text-red-500">{errors.confirmPassword.message}</p>}
                </div>
              </>
            )}
            {currentStep === 1 && (
              <div className="space-y-2">
                <Label htmlFor="schoolName">Nome da Escola</Label>
                <Input id="schoolName" {...register('schoolName')} />
                {errors.schoolName && <p className="text-sm text-red-500">{errors.schoolName.message}</p>}
              </div>
            )}
            {currentStep === 2 && (
              <div className="text-center">
                <p>Por favor, revise suas informações antes de concluir o cadastro.</p>
                <ul className="mt-4 text-left">
                  <li><strong>Nome:</strong> {getValues('name')}</li>
                  <li><strong>Email:</strong> {getValues('email')}</li>
                  <li><strong>Escola:</strong> {getValues('schoolName')}</li>
                </ul>
              </div>
            )}
          </form>
        </CardContent>
        <CardFooter className="flex justify-between">
          {currentStep > 0 && (
            <Button type="button" variant="outline" onClick={handlePrevious}>
              Voltar
            </Button>
          )}
          <Button type="submit" onClick={handleSubmit(onSubmit)}>
            {currentStep === steps.length - 1 ? 'Concluir Cadastro' : 'Próximo'}
          </Button>
        </CardFooter>
      </Card>
    </div>
  )
}