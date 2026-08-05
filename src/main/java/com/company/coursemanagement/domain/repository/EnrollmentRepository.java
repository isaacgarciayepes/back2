package com.company.coursemanagement.domain.repository;

import com.company.coursemanagement.domain.model.Enrollment;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository {
    Enrollment save(Enrollment enrollment);
    Optional<Enrollment> findById(Long id);
    List<Enrollment> findAll();
    Enrollment update(Enrollment enrollment);
    boolean deleteById(Long id);
    long countActiveByCourseId(Long courseId);
    boolean existsByStudentIdAndCourseIdAndStatus(Long studentId, Long courseId, com.company.coursemanagement.domain.model.EnrollmentStatus status);
}