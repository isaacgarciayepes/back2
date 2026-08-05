package com.company.coursemanagement.application.service;

import com.company.coursemanagement.application.dto.StudentDTO;
import com.company.coursemanagement.domain.exception.StudentNotFoundException;
import com.company.coursemanagement.domain.model.Student;
import com.company.coursemanagement.domain.repository.StudentRepository;

import java.util.List;

public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentDTO create(StudentDTO dto) {
        Student student = mapToEntity(dto);
        Student saved = studentRepository.save(student);
        return mapToDTO(saved);
    }

    public StudentDTO findById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        return mapToDTO(student);
    }

    public List<StudentDTO> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public StudentDTO update(Long id, StudentDTO dto) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        Student student = mapToEntity(dto);
        student.setId(id);
        Student updated = studentRepository.update(student);
        return mapToDTO(updated);
    }

    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }

    private Student mapToEntity(StudentDTO dto) {
        return new Student(dto.getId(), dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getBirthDate());
    }

    private StudentDTO mapToDTO(Student student) {
        return new StudentDTO(student.getId(), student.getFirstName(), student.getLastName(), student.getEmail(), student.getBirthDate());
    }
}