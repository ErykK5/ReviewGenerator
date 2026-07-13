package pl.generatoropinii.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.generatoropinii.model.*;
import pl.generatoropinii.repository.SubtestPairRepository;
import pl.generatoropinii.repository.SubtestRecommendationRepository;
import pl.generatoropinii.repository.WiscIndexThresholdRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Reproduces the logic of the three "engines" from the original spreadsheet:
 *  - RESULTS      -> indexInterpretation()
 *  - AREAS        -> compareSubtestPair()
 *  - WISC RECOMMENDATIONS -> recommendationForSubtest() (with the FIXED subtest matching)
 *
 * Thresholds and texts are NOT hardcoded in the code - they come from
 * configuration tables (WiscIndexThreshold, SubtestPair, SubtestRecommendation),
 * so they can be edited without changing the application.
 */
@Service
@RequiredArgsConstructor
public class WiscCalculationService {

    private final WiscIndexThresholdRepository wiscIndexThresholdRepository;
    private final SubtestPairRepository subtestPairRepository;
    private final SubtestRecommendationRepository subtestRecommendationRepository;

    /** Equivalent of the "Interpretation" column on the RESULTS tab. */
    public String indexInterpretation(WiscIndex wiscIndex, int score) {
        List<WiscIndexThreshold> thresholds = wiscIndexThresholdRepository.findByWiscIndexOrderByLowerThresholdDesc(wiscIndex);
        return thresholds.stream()
                .filter(t -> score >= t.getLowerThreshold())
                .findFirst()
                .map(WiscIndexThreshold::getInterpretationText)
                .orElse("No threshold defined for score " + score + " (" + wiscIndex + ")");
    }

    /** Equivalent of the "Description / A>B / B>A" columns on the AREAS tab. */
    public String compareSubtestPair(SubtestPair pair, int scoreA, int scoreB) {
        double difference = Math.abs(scoreA - scoreB);
        if (difference <= pair.getSignificanceThreshold()) {
            return "";
        }
        return scoreA > scoreB ? pair.getTextWhenAGreater() : pair.getTextWhenBGreater();
    }

    /**
     * Equivalent of the "RECOMMENDATIONS" column on the WISC RECOMMENDATIONS tab.
     *
     * BUG FIX: in the spreadsheet, the formula for "Block Design" checked the
     * result of a different subtest ("Similarities"). Here the match is made
     * against the subtest name passed explicitly as an argument, so it's not
     * possible to accidentally use someone else's result.
     */
    public Optional<String> recommendationForSubtest(String subtest, int score) {
        return subtestRecommendationRepository.findBySubtest(subtest)
                .filter(sr -> score < sr.getThresholdBelow())
                .map(SubtestRecommendation::getRecommendationText);
    }

    /** Convenience helper for the service that generates the full report (step 4). */
    public String computeAllInterpretations(Map<WiscIndex, Integer> indexResults) {
        StringBuilder sb = new StringBuilder();
        for (var entry : indexResults.entrySet()) {
            sb.append(indexInterpretation(entry.getKey(), entry.getValue())).append(" ");
        }
        return sb.toString().trim();
    }
}
