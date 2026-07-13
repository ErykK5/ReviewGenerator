// Types correspond 1:1 to the Java entities in backend/src/main/java/pl/generatoropinii/model

export type EducationStage =
  | 'PRESCHOOL'
  | 'GRADES_1_3'
  | 'GRADES_4_8'
  | 'SECONDARY_SCHOOL'

export interface Student {
  id?: number
  firstName: string
  lastName: string
  pesel: string
  dateOfBirth?: string
  educationStage?: EducationStage
  schoolClass?: string
  school?: string
  psychologist?: string
  assessmentDate?: string
}

export type WiscIndex = 'FSIQ' | 'VCI' | 'VSI' | 'FRI' | 'WMI' | 'PSI'

export interface IndexResult {
  wiscIndex: WiscIndex
  score: number
}

export interface SubtestResult {
  subtest: string
  score: number
}

export interface SenMeasure {
  id: number
  category: string
  name: string
  recommendationForSchool: string
  recommendationForParent: string
}

export interface Report {
  id?: number
  student?: Student
  indexResults: IndexResult[]
  subtestResults: SubtestResult[]
  selectedSenMeasures: SenMeasure[]
  textForSchool?: string
  textForParent?: string
}
