package pl.generatoropinii.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subtest_result")
@Getter
@Setter
@NoArgsConstructor
public class SubtestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "report_id")
    @JsonIgnore
    private Report report;

    /** Must match the `subtest` name in SubtestRecommendation as well as subtestA/subtestB in SubtestPair. */
    private String subtest;

    private Integer score;
}
