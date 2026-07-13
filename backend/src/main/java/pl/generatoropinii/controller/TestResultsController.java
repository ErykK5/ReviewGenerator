package pl.generatoropinii.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.generatoropinii.model.Report;
import pl.generatoropinii.model.Student;
import pl.generatoropinii.model.SubtestResult;
import pl.generatoropinii.model.IndexResult;
import pl.generatoropinii.repository.ReportRepository;
import pl.generatoropinii.repository.SubtestPairRepository;
import pl.generatoropinii.repository.StudentRepository;
import pl.generatoropinii.repository.WiscIndexThresholdRepository;

/**
 * Step 2: entering WISC test results.
 * Also returns configuration data (thresholds, subtest pairs) so the frontend
 * can, for example, show a live interpretation preview without re-implementing
 * the threshold logic on the client.
 */
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class TestResultsController {

    private final ReportRepository reportRepository;
    private final StudentRepository studentRepository;
    private final WiscIndexThresholdRepository wiscIndexThresholdRepository;
    private final SubtestPairRepository subtestPairRepository;

    @PostMapping("/for-student/{studentId}")
    public Report createReport(@PathVariable Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Report report = new Report();
        report.setStudent(student);
        return reportRepository.save(report);
    }

    @PostMapping("/{reportId}/indexes")
    public Report addIndexResult(@PathVariable Long reportId, @RequestBody IndexResult result) {
        Report report = reportRepository.findById(reportId).orElseThrow();
        result.setReport(report);
        report.getIndexResults().add(result);
        return reportRepository.save(report);
    }

    @PostMapping("/{reportId}/subtests")
    public Report addSubtestResult(@PathVariable Long reportId, @RequestBody SubtestResult result) {
        Report report = reportRepository.findById(reportId).orElseThrow();
        result.setReport(report);
        report.getSubtestResults().add(result);
        return reportRepository.save(report);
    }

    /** Threshold configuration - useful e.g. for hints in the UI. */
    @GetMapping("/config/index-thresholds")
    public Object indexThresholds() {
        return wiscIndexThresholdRepository.findAll();
    }

    @GetMapping("/config/subtest-pairs")
    public Object subtestPairs() {
        return subtestPairRepository.findAll();
    }
}
