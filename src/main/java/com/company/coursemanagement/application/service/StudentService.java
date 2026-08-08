package com.company.coursemanagement.application.service;

import com.company.coursemanagement.domain.exception.StudentNotFoundException;
import com.company.coursemanagement.domain.model.Student;
import com.company.coursemanagement.domain.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

public class StudentService implements StudentRepository {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public Student save(Student student) {

        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> findById(Long id) {

        return studentRepository.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student update(Student student) {

        if (!studentRepository.existsById(student.getId())) {
            throw new StudentNotFoundException(student.getId());
        }
        return studentRepository.update(student);
    }

    @Override
    public boolean deleteById(Long id) {

        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        return studentRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return studentRepository.existsById(id);
    }


    public Student findStudentOrThrow(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }


    public Student create(Student student) {
        return this.save(student);
    }
}