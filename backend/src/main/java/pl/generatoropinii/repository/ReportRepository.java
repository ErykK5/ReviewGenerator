package pl.generatoropinii.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.generatoropinii.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
