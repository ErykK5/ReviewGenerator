package pl.generatoropinii.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.generatoropinii.model.SubtestPair;

public interface SubtestPairRepository extends JpaRepository<SubtestPair, Long> {
}
