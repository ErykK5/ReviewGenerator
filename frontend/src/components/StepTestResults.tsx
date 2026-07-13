import { useEffect, useState } from 'react'
import { api } from '../api/client'
import type { Report, WiscIndex } from '../types'

const INDEXES: WiscIndex[] = ['FSIQ', 'VCI', 'VSI', 'FRI', 'WMI', 'PSI']

const SUBTESTS = [
  'Similarities', 'Vocabulary', 'Block Design', 'Visual Puzzles', 'Matrix Reasoning', 'Figure Weights',
  'Digit Span', 'Picture Span', 'Coding', 'Symbol Search',
]

export default function StepTestResults({
  studentId,
  onReady,
}: {
  studentId: number
  onReady: (report: Report) => void
}) {
  const [reportId, setReportId] = useState<number | null>(null)
  const [indexScores, setIndexScores] = useState<Record<string, number>>({})
  const [subtestScores, setSubtestScores] = useState<Record<string, number>>({})
  const [saving, setSaving] = useState(false)

  useEffect(() => {
    api.createReport(studentId).then((r) => setReportId(r.id!))
  }, [studentId])

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
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: 8, maxWidth: 480 }}>
        {INDEXES.map((w) => (
          <label key={w}>
            {w}
            <input
              type="number"
              onChange={(e) => setIndexScores({ ...indexScores, [w]: Number(e.target.value) })}
            />
          </label>
        ))}
      </div>

      <h2>Subtest scores</h2>
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(2, 1fr)', gap: 8, maxWidth: 480 }}>
        {SUBTESTS.map((p) => (
          <label key={p}>
            {p}
            <input
              type="number"
              onChange={(e) => setSubtestScores({ ...subtestScores, [p]: Number(e.target.value) })}
            />
          </label>
        ))}
      </div>

      <button onClick={saveAndContinue} disabled={!reportId || saving} style={{ marginTop: 16 }}>
        Next: SEN database
      </button>
    </div>
  )
}
