package pl.generatoropinii.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A single entry from the SEN (special educational needs) database - step 3 of the app.
 * The user checks a checkbox (see the report's selected-measures list), and the
 * recommendation text is pulled from here. The category lets the list be grouped
 * in the UI (e.g. "DYSLEXIA", "METHODS", "ACTIVITIES" - matching the sections in
 * the original spreadsheet).
 */
@Entity
@Table(name = "sen_measure")
@Getter
@Setter
@NoArgsConstructor
public class SenMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String name;

    @Column(length = 2000)
    private String recommendationForSchool;

    @Column(length = 2000)
    private String recommendationForParent;

    /** Display order within a category. */
    private Integer displayOrder;
}
