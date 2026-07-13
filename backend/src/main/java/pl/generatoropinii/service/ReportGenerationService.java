package pl.generatoropinii.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.generatoropinii.model.*;
import pl.generatoropinii.repository.SubtestPairRepository;

import java.util.List;

/**
 * Step 4: assembles the final text from
 *   - index interpretations (step 2a)
 *   - differences between subtests (step 2b)
 *   - recommendations arising from low subtest scores (step 2c)
 *   - recommendations manually selected in the SEN database (step 3)
 */
@Service
@RequiredArgsConstructor
public class ReportGenerationService {

    private final WiscCalculationService wiscCalculationService;
    private final SubtestPairRepository subtestPairRepository;

    public Report generateReportText(Report report) {
        StringBuilder school = new StringBuilder();
        StringBuilder parent = new StringBuilder();

        // 2a - index interpretations (informational, go into both texts)
        for (IndexResult r : report.getIndexResults()) {
            String text = wiscCalculationService.indexInterpretation(r.getWiscIndex(), r.getScore());
            school.append(text).append("\n");
            parent.append(text).append("\n");
        }

        // 2b - differences between paired subtests
        List<SubtestPair> pairs = subtestPairRepository.findAll();
        for (SubtestPair pair : pairs) {
            var scoreA = findSubtestScore(report, pair.getSubtestA());
            var scoreB = findSubtestScore(report, pair.getSubtestB());
            if (scoreA != null && scoreB != null) {
                String description = wiscCalculationService.compareSubtestPair(pair, scoreA, scoreB);
                if (!description.isBlank()) {
                    school.append(description).append("\n");
                    parent.append(description).append("\n");
                }
            }
        }

        // 2c - recommendations from low subtest scores
        for (SubtestResult r : report.getSubtestResults()) {
            wiscCalculationService.recommendationForSubtest(r.getSubtest(), r.getScore())
                    .ifPresent(text -> {
                        school.append(text).append("\n");
                        parent.append(text).append("\n");
                    });
        }

        // 3 - manually selected entries from the SEN database
        for (SenMeasure m : report.getSelectedSenMeasures()) {
            if (m.getRecommendationForSchool() != null) {
                school.append(m.getRecommendationForSchool()).append("\n");
            }
            if (m.getRecommendationForParent() != null) {
                parent.append(m.getRecommendationForParent()).append("\n");
            }
        }

        report.setTextForSchool(school.toString().trim());
        report.setTextForParent(parent.toString().trim());
        return report;
    }

    private Integer findSubtestScore(Report report, String subtestName) {
        return report.getSubtestResults().stream()
                .filter(r -> r.getSubtest().equalsIgnoreCase(subtestName))
                .map(SubtestResult::getScore)
                .findFirst()
                .orElse(null);
    }
}
