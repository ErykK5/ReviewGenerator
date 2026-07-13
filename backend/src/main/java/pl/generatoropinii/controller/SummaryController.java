package pl.generatoropinii.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.generatoropinii.model.Report;
import pl.generatoropinii.repository.ReportRepository;
import pl.generatoropinii.service.ReportGenerationService;

/** Step 4: generate the summary text based on steps 2-3. */
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class SummaryController {

    private final ReportRepository reportRepository;
    private final ReportGenerationService reportGenerationService;

    @PostMapping("/{reportId}/generate")
    public Report generate(@PathVariable Long reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow();
        Report generated = reportGenerationService.generateReportText(report);
        return reportRepository.save(generated);
    }

    /** Preview/edit before export - the frontend sends the edited text back here. */
    @PutMapping("/{reportId}/text")
    public Report saveEdited(@PathVariable Long reportId, @RequestBody Report edits) {
        Report report = reportRepository.findById(reportId).orElseThrow();
        report.setTextForSchool(edits.getTextForSchool());
        report.setTextForParent(edits.getTextForParent());
        return reportRepository.save(report);
    }

    // Export to .docx (Apache POI) - see DocxExportService, endpoint to be wired up in a future step.
}
