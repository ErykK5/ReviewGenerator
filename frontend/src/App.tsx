import { useState } from 'react'
import type { Student, Report } from './types'
import StepStudentData from './components/StepStudentData'
import StepTestResults from './components/StepTestResults'
import StepSenDatabase from './components/StepSenDatabase'
import StepSummary from './components/StepSummary'

type Step = 1 | 2 | 3 | 4

export default function App() {
  const [step, setStep] = useState<Step>(1)
  const [student, setStudent] = useState<Student | null>(null)
  const [report, setReport] = useState<Report | null>(null)

  return (
    <div style={{ maxWidth: 720, margin: '0 auto', padding: 24, fontFamily: 'sans-serif' }}>
      <h1>Psychological-pedagogical report generator</h1>

      <ol style={{ display: 'flex', gap: 16, listStyle: 'none', padding: 0 }}>
        {(['Student data', 'Test results', 'SEN database', 'Summary'] as const).map((label, i) => (
          <li key={label} style={{ fontWeight: step === i + 1 ? 'bold' : 'normal' }}>
            {i + 1}. {label}
          </li>
        ))}
      </ol>

      {step === 1 && (
        <StepStudentData
          onSaved={(s) => {
            setStudent(s)
            setStep(2)
          }}
        />
      )}

      {step === 2 && student?.id && (
        <StepTestResults
          studentId={student.id}
          onReady={(r) => {
            setReport(r)
            setStep(3)
          }}
        />
      )}

      {step === 3 && report?.id && (
        <StepSenDatabase
          reportId={report.id}
          onReady={(r) => {
            setReport(r)
            setStep(4)
          }}
        />
      )}

      {step === 4 && report?.id && <StepSummary reportId={report.id} />}
    </div>
  )
}
