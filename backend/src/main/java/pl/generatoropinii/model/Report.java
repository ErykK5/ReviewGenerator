package pl.generatoropinii.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A single generated report for a student. A student can have multiple reports
 * over time (different assessments), which is why this is a separate entity
 * rather than fields on Student.
 */
@Entity
@Table(name = "report")
@Getter
@Setter
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Step 2a/2b/2c - results entered by the user
    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IndexResult> indexResults = new ArrayList<>();

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubtestResult> subtestResults = new ArrayList<>();

    // Step 3 - selected entries from the SEN database
    @ManyToMany
    @JoinTable(
        name = "report_sen_measure",
        joinColumns = @JoinColumn(name = "report_id"),
        inverseJoinColumns = @JoinColumn(name = "sen_measure_id"))
    private List<SenMeasure> selectedSenMeasures = new ArrayList<>();

    // Step 4 - generated final text, editable by the user
    @Column(length = 10000)
    private String textForSchool;

    @Column(length = 10000)
    private String textForParent;
}
