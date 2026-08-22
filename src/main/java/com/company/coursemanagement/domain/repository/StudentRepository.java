package com.company.coursemanagement.domain.repository;

import com.company.coursemanagement.application.service.StudentService;
import com.company.coursemanagement.domain.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Student save(Student student);
    Optional<Student> findById(Long id);
    List<Student> findAll();
    Student update(Student student);
    boolean deleteById(Long id);
    boolean existsById(Long id);
}