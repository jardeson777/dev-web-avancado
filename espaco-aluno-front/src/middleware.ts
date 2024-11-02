import { NextResponse } from 'next/server'
import type { NextRequest } from 'next/server'

export function middleware(request: NextRequest) {
  const profile = request.cookies.get('profile')?.value
  const isLoginPage = request.nextUrl.pathname === '/login'

  // Verifica se o usuário está tentando acessar a página de login
  if (isLoginPage) {
    // Se o usuário já estiver logado, redireciona para a área correta
    if (profile) {
      switch (profile) {
        case 'coordinator':
          return NextResponse.redirect(new URL('/area-logada/coordenador', request.url))
        case 'teacher':
          return NextResponse.redirect(new URL('/area-logada/professor', request.url))
        case 'student':
          return NextResponse.redirect(new URL('/area-logada/aluno', request.url))
        default:
          // Se o perfil for inválido, redireciona para o login
          return NextResponse.redirect(new URL('/login', request.url))
      }
    }

    return NextResponse.next()
  }

  // Verifica se o usuário está tentando acessar uma área protegida
  if (request.nextUrl.pathname.startsWith('/area-logada')) {
    if (!profile) {
      // Se não houver perfil, redireciona para o login
      return NextResponse.redirect(new URL('/login', request.url))
    }

    // Redireciona para a área correta com base no perfil
    switch (profile) {
      case 'coordinator':
        if (!request.nextUrl.pathname.startsWith('/area-logada/coordenador')) {
          return NextResponse.redirect(new URL('/area-logada/coordenador', request.url))
        }
        break
      case 'teacher':
        if (!request.nextUrl.pathname.startsWith('/area-logada/professor')) {
          return NextResponse.redirect(new URL('/area-logada/professor', request.url))
        }
        break
      case 'student':
        if (!request.nextUrl.pathname.startsWith('/area-logada/aluno')) {
          return NextResponse.redirect(new URL('/area-logada/aluno', request.url))
        }
        break
      default:
        // Se o perfil for inválido, redireciona para o login
        return NextResponse.redirect(new URL('/login', request.url))
    }
  }

  return NextResponse.next()
}

export const config = {
  matcher: '/:path*',
}
