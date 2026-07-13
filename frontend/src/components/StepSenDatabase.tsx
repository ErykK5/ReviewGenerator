import { useEffect, useMemo, useState } from 'react'
import { api } from '../api/client'
import type { SenMeasure, Report } from '../types'

export default function StepSenDatabase({
  reportId,
  onReady,
}: {
  reportId: number
  onReady: (report: Report) => void
}) {
  const [measures, setMeasures] = useState<SenMeasure[]>([])
  const [selected, setSelected] = useState<Set<number>>(new Set())

  useEffect(() => {
    api.getSenMeasures().then(setMeasures)
  }, [])

  const grouped = useMemo(() => {
    const groups = new Map<string, SenMeasure[]>()
    for (const m of measures) {
      if (!groups.has(m.category)) groups.set(m.category, [])
      groups.get(m.category)!.push(m)
    }
    return groups
  }, [measures])

  function toggle(id: number) {
    const updated = new Set(selected)
    updated.has(id) ? updated.delete(id) : updated.add(id)
    setSelected(updated)
  }

  async function saveAndContinue() {
    const report = await api.saveSenSelections(reportId, Array.from(selected))
    onReady(report)
  }

  return (
    <div>
      <h2>SEN database - check the recommendations to include</h2>
      {Array.from(grouped.entries()).map(([category, items]) => (
        <fieldset key={category} style={{ marginBottom: 12 }}>
          <legend>{category}</legend>
          {items.map((m) => (
            <label key={m.id} style={{ display: 'block' }}>
              <input type="checkbox" checked={selected.has(m.id)} onChange={() => toggle(m.id)} />
              {m.name}
            </label>
          ))}
        </fieldset>
      ))}

      <button onClick={saveAndContinue}>Next: summary</button>
    </div>
  )
}
