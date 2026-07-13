package pl.generatoropinii.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A recommendation tied to a specific subtest (the WISC RECOMMENDATIONS tab).
 *
 * BUG FIX FROM THE ORIGINAL SPREADSHEET:
 * in the original file, the formula for the "Block Design" row incorrectly checked
 * the result of the "Similarities" subtest (=IF(B2<8,...) instead of =IF(B4<8,...)).
 * Because every row of this table has an explicit `subtest` field that the service
 * uses as the key to match against the entered score, this kind of mistake is no
 * longer structurally possible - there's no way to accidentally wire a threshold to
 * the wrong subtest the way the Excel formula did.
 */
@Entity
@Table(name = "subtest_recommendation")
@Getter
@Setter
@NoArgsConstructor
public class SubtestRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Subtest name - must exactly match the name used in the step 2 form. */
    private String subtest;

    /** A score below this threshold triggers the recommendation (always 8 in the original spreadsheet). */
    private Integer thresholdBelow;

    @Column(length = 2000)
    private String recommendationText;
}
