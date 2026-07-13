package pl.generatoropinii.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.generatoropinii.model.SenMeasure;

import java.util.List;

public interface SenMeasureRepository extends JpaRepository<SenMeasure, Long> {
    List<SenMeasure> findAllByOrderByCategoryAscDisplayOrderAsc();
}
