package pl.generatoropinii.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** A pair of subtests compared within a single scale (the AREAS tab). */
@Entity
@Table(name = "subtest_pair")
@Getter
@Setter
@NoArgsConstructor
public class SubtestPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WiscIndex scale;

    private String subtestA;
    private String subtestB;

    /** Statistical significance threshold for the difference, specific to each pair (e.g. 2.17 for VCI). */
    private Double significanceThreshold;

    @Column(length = 1000)
    private String textWhenAGreater;

    @Column(length = 1000)
    private String textWhenBGreater;
}
