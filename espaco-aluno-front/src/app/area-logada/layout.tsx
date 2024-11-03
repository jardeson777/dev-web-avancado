import React from 'react'
import Sidebar from '../../components/sidebar'
import { cookies } from 'next/headers'

type LayoutProps = {
  children: React.ReactNode
}

export default async function Layout({ children }: LayoutProps) {
  const profile = (await cookies()).get('profile')?.value as 'coordinator' | 'teacher' | 'student' | undefined

  if (!profile) {
    return null
  }

  return (
    <div className="flex h-screen bg-gray-100">
      <Sidebar userType={profile} />
      <main className="flex-1 overflow-y-auto p-8">
        {children}
      </main>
    </div>
  )
}