package com.company.coursemanagement.application.dto;

import com.company.coursemanagement.domain.model.Student;

import java.time.LocalDate;

public record StudentDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        LocalDate birthDate,
        String phoneNumber
) {

    public static StudentDTO from(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getBirthDate(),
                student.getPhoneNumber()
        );
    }

    public Student toEntity() {
        return new Student(id, firstName, lastName, email, birthDate, phoneNumber);
    }
}