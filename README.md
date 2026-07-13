# Psychological-pedagogical report generator

An app for individual use: student data -> WISC test results ->
SEN database (checklists) -> summary and export. Runs locally, on
your own computer, with no hosting required (see the spec: option 2 -
a single `.jar` file to run).

## Repo structure

```
generator-opinii/
├── backend/    Java 17 + Spring Boot + SQLite + Apache POI
└── frontend/   React + TypeScript + Vite
```

## Requirements

- JDK 17+
- Maven (or `./mvnw` if you add a wrapper)
- Node.js 18+ (only needed to build the frontend, not required afterwards)

## Running in development mode (two processes)

```bash
# terminal 1 - backend
cd backend
mvn spring-boot:run

# terminal 2 - frontend (with hot reload, proxying /api to the backend)
cd frontend
npm install
npm run dev
```

Frontend available at `http://localhost:5173`, backend at `http://localhost:8080`.

## Building a single .jar file (the intended usage mode)

```bash
# 1. build the frontend - the output goes straight into backend/src/main/resources/static
cd frontend
npm install
npm run build

# 2. build the backend together with the embedded frontend
cd ../backend
mvn clean package

# 3. run it
java -jar target/generator-opinii.jar
```

Open `http://localhost:8080` in your browser - the whole application (frontend +
backend + SQLite database) runs from a single process, a single file.

Optionally: `jpackage` can wrap this `.jar` into an `.exe`/`.app` file, so it can
be launched with a double-click, without a terminal.

## Database

SQLite as a single file `generator-opinii.db`, created automatically next
to the jar on first run. Data never leaves your computer.

On first startup, `DataSeeder` populates the configuration tables
(index thresholds, subtest pairs, recommendations) with data migrated from
the original `WISC-KLIKADLO.xlsx` spreadsheet, with the formula bug for the
"Block Design" subtest fixed (see the comment in `DataSeeder.java` and
`SubtestRecommendation.java`).

## What's done, and what's still to be added

Done:
- the full data model (entities + repositories)
- a calculation engine reproducing the logic of the RESULTS / AREAS / WISC RECOMMENDATIONS formulas
- a PESEL parser that handles all centuries
- a REST API for all 4 steps
- a frontend skeleton with navigation between steps

Still to be added:
- a full import of the SEN database (~80 entries) - `DataSeeder` currently only has a representative fragment
- `DocxExportService` (Apache POI) - export of the summary to .docx; the endpoint in `SummaryController` is already prepared for it
- frontend styling (currently plain functionality with no CSS)
- form validation (e.g. a PESEL checksum)
