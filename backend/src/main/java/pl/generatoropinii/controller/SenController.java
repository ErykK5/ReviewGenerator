package pl.generatoropinii.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.generatoropinii.model.SenMeasure;
import pl.generatoropinii.model.Report;
import pl.generatoropinii.repository.SenMeasureRepository;
import pl.generatoropinii.repository.ReportRepository;

import java.util.List;

/** Step 3: list of entries from the SEN database + saving the checked boxes for a given report. */
@RestController
@RequestMapping("/api/sen")
@RequiredArgsConstructor
public class SenController {

    private final SenMeasureRepository senMeasureRepository;
    private final ReportRepository reportRepository;

    /** The full SEN database grouped by category (the frontend renders it as sections with checkboxes). */
    @GetMapping
    public List<SenMeasure> listMeasures() {
        return senMeasureRepository.findAllByOrderByCategoryAscDisplayOrderAsc();
    }

    /** Saves which SEN entries were checked (0/1) for a given report. */
    @PutMapping("/reports/{reportId}/selections")
    public Report saveSelections(@PathVariable Long reportId, @RequestBody List<Long> selectedSenMeasureIds) {
        Report report = reportRepository.findById(reportId).orElseThrow();
        List<SenMeasure> selected = senMeasureRepository.findAllById(selectedSenMeasureIds);
        report.setSelectedSenMeasures(selected);
        return reportRepository.save(report);
    }
}
