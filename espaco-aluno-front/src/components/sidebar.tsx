'use client'

import React from 'react'
import Link from 'next/link'
import { usePathname } from 'next/navigation'
import { School, Users, Presentation, Calendar, ClipboardCheck, GraduationCap, BookOpen, LayoutDashboard } from 'lucide-react'
import { cn } from '../lib/utils'
import Logo from '@/assets/logo-espaco-aluno.png'
import Image from 'next/image'

type SidebarProps = {
  userType: 'coordinator' | 'teacher' | 'student'
}

type MenuItem = {
  name: string
  icon: React.ReactNode
  href: string
}

const menuItems: Record<SidebarProps['userType'], MenuItem[]> = {
  coordinator: [
    { name: 'Escola', icon: <School className="w-5 h-5" />, href: '/area-logada/coordenador/escola' },
    { name: 'Alunos', icon: <Users className="w-5 h-5" />, href: '/area-logada/coordenador/alunos' },
    { name: 'Professores', icon: <Presentation className="w-5 h-5" />, href: '/area-logada/coordenador/professores' },
    { name: 'Visão geral', icon: <LayoutDashboard className="w-5 h-5" />, href: '/area-logada/coordenador/visao-geral' },

  ],
  teacher: [
    { name: 'Calendário', icon: <Calendar className="w-5 h-5" />, href: '/area-logada/professor/calendario' },
    { name: 'Presença', icon: <ClipboardCheck className="w-5 h-5" />, href: '/area-logada/professor/presenca' },
    { name: 'Notas', icon: <GraduationCap className="w-5 h-5" />, href: '/area-logada/professor/notas' },
    { name: 'Visão geral', icon: <LayoutDashboard className="w-5 h-5" />, href: '/area-logada/professor/visao-geral' },
  ],
  student: [
    { name: 'Inscrição', icon: <BookOpen className="w-5 h-5" />, href: '/area-logada/aluno/inscricao' },
    { name: 'Disciplinas', icon: <GraduationCap className="w-5 h-5" />, href: '/area-logada/aluno/disciplinas' },
    { name: 'Visão Geral', icon: <LayoutDashboard className="w-5 h-5" />, href: '/area-logada/aluno/visao-geral' },
  ],
}

export default function Sidebar({ userType }: SidebarProps) {
  const pathname = usePathname()

  return (
    <aside className="w-64 bg-gray-200 ">
      <div className="p-6">
        <div className="flex justify-center h-[90px]">
          <Image
            src={Logo}
            alt="Logo da Escola"
            width={150}
            style={{ objectFit: 'cover' }}
          />
        </div>
      </div>
      <nav className="mt-6">
        <ul>
          {menuItems[userType].map((item) => (
            <li key={item.name} className="mb-2">
              <Link href={item.href}
                className={cn(
                  "flex items-center px-6 py-3 text-gray-900 hover:bg-gray-800 hover:text-white transition-colors duration-200",
                  pathname === item.href ? "bg-gray-800 text-white border-l-4 border-blue-500" : ""
                )}
              >
                {item.icon}
                <span className="ml-3 text-sm">{item.name}</span>
              </Link>
            </li>
          ))}
        </ul>
      </nav>
    </aside>
  )
}