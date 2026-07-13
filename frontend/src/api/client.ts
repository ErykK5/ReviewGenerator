import type { Student, Report, IndexResult, SubtestResult, SenMeasure } from '../types'

const BASE = '/api'

async function json<T>(res: Response): Promise<T> {
  if (!res.ok) throw new Error(`API error: ${res.status} ${res.statusText}`)
  return res.json() as Promise<T>
}

export const api = {
  // Step 1
  createStudent: (student: Student) =>
    fetch(`${BASE}/students`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(student),
    }).then((r) => json<Student>(r)),

  // Step 2
  createReport: (studentId: number) =>
    fetch(`${BASE}/reports/for-student/${studentId}`, { method: 'POST' }).then((r) => json<Report>(r)),

  addIndexResult: (reportId: number, result: IndexResult) =>
    fetch(`${BASE}/reports/${reportId}/indexes`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(result),
    }).then((r) => json<Report>(r)),

  addSubtestResult: (reportId: number, result: SubtestResult) =>
    fetch(`${BASE}/reports/${reportId}/subtests`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(result),
    }).then((r) => json<Report>(r)),

  // Step 3
  getSenMeasures: () => fetch(`${BASE}/sen`).then((r) => json<SenMeasure[]>(r)),

  saveSenSelections: (reportId: number, ids: number[]) =>
    fetch(`${BASE}/sen/reports/${reportId}/selections`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(ids),
    }).then((r) => json<Report>(r)),

  // Step 4
  generateReport: (reportId: number) =>
    fetch(`${BASE}/reports/${reportId}/generate`, { method: 'POST' }).then((r) => json<Report>(r)),

  saveEditedText: (reportId: number, text: Pick<Report, 'textForSchool' | 'textForParent'>) =>
    fetch(`${BASE}/reports/${reportId}/text`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(text),
    }).then((r) => json<Report>(r)),
}
