import { useEffect, useRef, useState } from 'react'
import { api } from '../api/client'
import type { Report, WiscIndex } from '../types'

const INDEXES: WiscIndex[] = ['FSIQ', 'VCI', 'VSI', 'FRI', 'WMI', 'PSI']

const SUBTESTS = [
  'Similarities', 'Vocabulary', 'Block Design', 'Visual Puzzles', 'Matrix Reasoning', 'Figure Weights',
  'Digit Span', 'Picture Span', 'Coding', 'Symbol Search',
]

export default function StepTestResults({
  studentId,
  initialReport,
  onBack,
  onReady,
}: {
  studentId: number
  initialReport?: Report
  onBack: () => void
  onReady: (report: Report) => void
}) {
  const [reportId, setReportId] = useState<number | null>(initialReport?.id ?? null)
  const [indexScores, setIndexScores] = useState<Record<string, number>>(() =>
    Object.fromEntries((initialReport?.indexResults ?? []).map((r) => [r.wiscIndex, r.score]))
  )
  const [subtestScores, setSubtestScores] = useState<Record<string, number>>(() =>
    Object.fromEntries((initialReport?.subtestResults ?? []).map((r) => [r.subtest, r.score]))
  )
  const [saving, setSaving] = useState(false)
  const creatingRef = useRef(false)

  useEffect(() => {
    if (reportId || creatingRef.current) return
    creatingRef.current = true
    api.createReport(studentId).then((r) => setReportId(r.id!))
  }, [studentId, reportId])

  async function saveAndContinue() {
    if (!reportId) return
    setSaving(true)
    let report: Report | null = null

    for (const [wiscIndex, score] of Object.entries(indexScores)) {
      report = await api.addIndexResult(reportId, { wiscIndex: wiscIndex as WiscIndex, score })
    }
    for (const [subtest, score] of Object.entries(subtestScores)) {
      report = await api.addSubtestResult(reportId, { subtest, score })
    }

    setSaving(false)
    if (report) onReady(report)
  }

  return (
    <div>
      <h2>Main index scores</h2>
      <div className="field-grid cols-3">
        {INDEXES.map((w) => (
          <label key={w}>
            {w}
            <input
              type="number"
              defaultValue={indexScores[w] ?? ''}
              onChange={(e) => setIndexScores({ ...indexScores, [w]: Number(e.target.value) })}
            />
          </label>
        ))}
      </div>

      <h2 style={{ marginTop: 24 }}>Subtest scores</h2>
      <div className="field-grid cols-2">
        {SUBTESTS.map((p) => (
          <label key={p}>
            {p}
            <input
              type="number"
              defaultValue={subtestScores[p] ?? ''}
              onChange={(e) => setSubtestScores({ ...subtestScores, [p]: Number(e.target.value) })}
            />
          </label>
        ))}
      </div>

      <div className="step-actions">
        <button type="button" className="btn-secondary" onClick={onBack}>
          ← Back
        </button>
        <button onClick={saveAndContinue} disabled={!reportId || saving}>
          Next: SEN database
        </button>
      </div>
    </div>
  )
}
