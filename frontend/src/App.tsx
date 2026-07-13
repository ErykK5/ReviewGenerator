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
    <div className="app-shell">
      <header className="app-header">
        <h1>Psychological-pedagogical report generator</h1>
        <p>Walk through the steps below to build a complete report</p>
      </header>

      <ol className="stepper">
        {(['Student data', 'Test results', 'SEN database', 'Summary'] as const).map((label, i) => {
          const n = i + 1
          const state = n === step ? 'active' : n < step ? 'done' : ''
          return (
            <li key={label} className={state}>
              <span className="step-badge">{n < step ? '✓' : n}</span>
              {label}
            </li>
          )
        })}
      </ol>

      <div className="card">
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
    </div>
  )
}
