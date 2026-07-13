package pl.generatoropinii.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.generatoropinii.model.Student;
import pl.generatoropinii.repository.StudentRepository;
import pl.generatoropinii.service.PeselService;

import java.time.LocalDate;

/** Step 1: entering student data. */
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;
    private final PeselService peselService;

    @PostMapping
    public Student create(@RequestBody Student student) {
        if (student.getPesel() != null && !student.getPesel().isBlank()) {
            LocalDate dateOfBirth = peselService.computeDateOfBirth(student.getPesel());
            int age = peselService.computeAge(dateOfBirth, LocalDate.now());
            student.setDateOfBirth(dateOfBirth);
            student.setEducationStage(peselService.computeEducationStage(age));
        }
        return studentRepository.save(student);
    }

    @GetMapping("/{id}")
    public Student get(@PathVariable Long id) {
        return studentRepository.findById(id).orElseThrow();
    }
}
