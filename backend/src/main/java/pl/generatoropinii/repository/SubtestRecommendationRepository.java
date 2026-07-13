package pl.generatoropinii.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.generatoropinii.model.SubtestRecommendation;

import java.util.Optional;

public interface SubtestRecommendationRepository extends JpaRepository<SubtestRecommendation, Long> {
    Optional<SubtestRecommendation> findBySubtest(String subtest);
}
