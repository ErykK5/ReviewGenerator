package pl.generatoropinii.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "index_result")
@Getter
@Setter
@NoArgsConstructor
public class IndexResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "report_id")
    @JsonIgnore
    private Report report;

    @Enumerated(EnumType.STRING)
    private WiscIndex wiscIndex;

    private Integer score;
}
