import { useState } from 'react'
import { api } from '../api/client'
import type { Student } from '../types'

export default function StepStudentData({ onSaved }: { onSaved: (s: Student) => void }) {
  const [data, setData] = useState<Student>({ firstName: '', lastName: '', pesel: '' })
  const [error, setError] = useState<string | null>(null)

  async function save() {
    try {
      setError(null)
      const saved = await api.createStudent(data)
      onSaved(saved)
    } catch (e) {
      setError('Could not save the student data - check the PESEL.')
    }
  }

  return (
    <div style={{ display: 'grid', gap: 12, maxWidth: 400 }}>
      <label>
        First name
        <input value={data.firstName} onChange={(e) => setData({ ...data, firstName: e.target.value })} />
      </label>
      <label>
        Last name
        <input value={data.lastName} onChange={(e) => setData({ ...data, lastName: e.target.value })} />
      </label>
      <label>
        PESEL
        <input value={data.pesel} onChange={(e) => setData({ ...data, pesel: e.target.value })} maxLength={11} />
      </label>
      <label>
        School
        <input value={data.school ?? ''} onChange={(e) => setData({ ...data, school: e.target.value })} />
      </label>
      <label>
        Class
        <input value={data.schoolClass ?? ''} onChange={(e) => setData({ ...data, schoolClass: e.target.value })} />
      </label>
      <label>
        Psychologist
        <input value={data.psychologist ?? ''} onChange={(e) => setData({ ...data, psychologist: e.target.value })} />
      </label>

      {error && <p style={{ color: 'red' }}>{error}</p>}

      <button onClick={save} disabled={!data.firstName || !data.lastName || !data.pesel}>
        Next: test results
      </button>
    </div>
  )
}
