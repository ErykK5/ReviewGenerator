package pl.generatoropinii.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A single threshold band for a given index (the equivalent of one
 * "IF(Bx >= threshold, text, ...)" from the RESULTS tab in the original spreadsheet).
 * A single index can have several rows with different thresholds - when computing
 * the result we take the one with the highest threshold that the score satisfies.
 */
@Entity
@Table(name = "wisc_index_threshold")
@Getter
@Setter
@NoArgsConstructor
public class WiscIndexThreshold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WiscIndex wiscIndex;

    /** Lower bound of the band (inclusive), e.g. 130, 120, 110, 90, 80, 70, ... */
    private Integer lowerThreshold;

    @Column(length = 2000)
    private String interpretationText;
}
