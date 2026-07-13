package pl.generatoropinii.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.generatoropinii.model.WiscIndex;
import pl.generatoropinii.model.WiscIndexThreshold;

import java.util.List;

public interface WiscIndexThresholdRepository extends JpaRepository<WiscIndexThreshold, Long> {
    List<WiscIndexThreshold> findByWiscIndexOrderByLowerThresholdDesc(WiscIndex wiscIndex);
}
