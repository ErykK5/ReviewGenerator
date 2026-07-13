import { useEffect, useState } from 'react'
import { api } from '../api/client'
import type { Report } from '../types'

export default function StepSummary({ reportId }: { reportId: number }) {
  const [report, setReport] = useState<Report | null>(null)
  const [saved, setSaved] = useState(false)

  useEffect(() => {
    api.generateReport(reportId).then(setReport)
  }, [reportId])

  async function saveEdits() {
    if (!report) return
    await api.saveEditedText(reportId, {
      textForSchool: report.textForSchool,
      textForParent: report.textForParent,
    })
    setSaved(true)
  }

  if (!report) return <p className="status-text">Generating report...</p>

  return (
    <div>
      <h2>Report preview (editable)</h2>

      <h3>Recommendations for the school</h3>
      <textarea
        rows={10}
        style={{ width: '100%' }}
        value={report.textForSchool ?? ''}
        onChange={(e) => setReport({ ...report, textForSchool: e.target.value })}
      />

      <h3 style={{ marginTop: 20 }}>Recommendations for the parent</h3>
      <textarea
        rows={10}
        style={{ width: '100%' }}
        value={report.textForParent ?? ''}
        onChange={(e) => setReport({ ...report, textForParent: e.target.value })}
      />

      <button onClick={saveEdits} style={{ marginTop: 16 }}>Save edits</button>
      {saved && (
        <p className="success-text" style={{ marginTop: 12 }}>
          Saved. Export to .docx - to be wired up (see DocxExportService on the backend).
        </p>
      )}
    </div>
  )
}
