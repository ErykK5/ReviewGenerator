package pl.generatoropinii.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.generatoropinii.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
