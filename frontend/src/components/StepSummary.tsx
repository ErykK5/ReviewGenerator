import { useEffect, useRef, useState } from 'react'
import { api } from '../api/client'
import type { Report } from '../types'

export default function StepSummary({
  reportId,
  initialReport,
  onBack,
  onReportChange,
}: {
  reportId: number
  initialReport?: Report
  onBack: () => void
  onReportChange: (report: Report) => void
}) {
  const alreadyGenerated =
    initialReport?.textForSchool != null || initialReport?.textForParent != null
  const [report, setReport] = useState<Report | null>(alreadyGenerated ? initialReport! : null)
  const [saved, setSaved] = useState(false)
  const generatingRef = useRef(false)

  useEffect(() => {
    if (report || generatingRef.current) return
    generatingRef.current = true
    api.generateReport(reportId).then((r) => {
      setReport(r)
      onReportChange(r)
    })
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [reportId])

  async function saveEdits() {
    if (!report) return
    const updated = await api.saveEditedText(reportId, {
      textForSchool: report.textForSchool,
      textForParent: report.textForParent,
    })
    setReport(updated)
    onReportChange(updated)
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

      <div className="step-actions">
        <button type="button" className="btn-secondary" onClick={onBack}>
          ← Back
        </button>
        <button onClick={saveEdits}>Save edits</button>
      </div>
      {saved && (
        <p className="success-text" style={{ marginTop: 12 }}>
          Saved. Export to .docx - to be wired up (see DocxExportService on the backend).
        </p>
      )}
    </div>
  )
}
