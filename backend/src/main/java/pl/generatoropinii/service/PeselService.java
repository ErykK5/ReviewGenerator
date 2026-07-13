package pl.generatoropinii.service;

import org.springframework.stereotype.Service;
import pl.generatoropinii.model.EducationStage;

import java.time.LocalDate;
import java.time.Period;

/**
 * Parses a PESEL (Polish national ID number) into a date of birth, and computes
 * the student's age and education stage.
 *
 * The month code also encodes the century of birth:
 *   01-12 -> 1900-1999
 *   21-32 -> 2000-2099
 *   41-52 -> 2100-2199
 *   61-72 -> 2200-2299
 *   81-92 -> 1800-1899
 */
@Service
public class PeselService {

    public LocalDate computeDateOfBirth(String pesel) {
        if (pesel == null || pesel.length() < 6 || !pesel.substring(0, 6).chars().allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("PESEL must start with 6 digits (YYMMDD)");
        }
        int year = Integer.parseInt(pesel.substring(0, 2));
        int monthCode = Integer.parseInt(pesel.substring(2, 4));
        int day = Integer.parseInt(pesel.substring(4, 6));

        int century;
        int month;
        if (monthCode >= 1 && monthCode <= 12) {
            century = 1900; month = monthCode;
        } else if (monthCode >= 21 && monthCode <= 32) {
            century = 2000; month = monthCode - 20;
        } else if (monthCode >= 41 && monthCode <= 52) {
            century = 2100; month = monthCode - 40;
        } else if (monthCode >= 61 && monthCode <= 72) {
            century = 2200; month = monthCode - 60;
        } else if (monthCode >= 81 && monthCode <= 92) {
            century = 1800; month = monthCode - 80;
        } else {
            throw new IllegalArgumentException("Invalid month code in PESEL: " + monthCode);
        }

        return LocalDate.of(century + year, month, day);
    }

    public int computeAge(LocalDate dateOfBirth, LocalDate onDate) {
        return Period.between(dateOfBirth, onDate).getYears();
    }

    /** Very simplified age-based stage matching - adjust to fit the real school year. */
    public EducationStage computeEducationStage(int age) {
        if (age <= 6) return EducationStage.PRESCHOOL;
        if (age <= 9) return EducationStage.GRADES_1_3;
        if (age <= 15) return EducationStage.GRADES_4_8;
        return EducationStage.SECONDARY_SCHOOL;
    }
}
