package pl.generatoropinii.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Student data entered in step 1.
 * The PESEL (Polish national ID number) is not treated as the source of truth for
 * age in the database - the date of birth and education stage are computed once
 * at save time (see PeselService) and stored as plain fields, so the PESEL-parsing
 * logic doesn't need to be scattered throughout the app.
 */
@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    /**
     * The PESEL is deliberately kept here, since the app is intended for
     * individual, local use (see the spec: option 2 - a single jar on the
     * user's own computer). In a multi-user/hosted version this field should
     * be encrypted or not stored at all.
     */
    private String pesel;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private EducationStage educationStage;

    private String schoolClass;
    private String school;
    private String psychologist;
    private LocalDate assessmentDate;
}
