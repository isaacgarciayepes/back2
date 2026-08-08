package com.company.coursemanagement.application.service;

import com.company.coursemanagement.domain.exception.BusinessException;
import com.company.coursemanagement.domain.exception.CourseNotFoundException;
import com.company.coursemanagement.domain.exception.EnrollmentNotFoundException;
import com.company.coursemanagement.domain.exception.StudentNotFoundException;
import com.company.coursemanagement.domain.model.Enrollment;
import com.company.coursemanagement.domain.model.EnrollmentStatus;
import com.company.coursemanagement.domain.repository.CourseRepository;
import com.company.coursemanagement.domain.repository.EnrollmentRepository;
import com.company.coursemanagement.domain.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

public class EnrollmentService implements EnrollmentRepository {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentRepository studentRepository,
                             CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Enrollment save(Enrollment enrollment) {

        Long studentId = enrollment.getStudentId();
        Long courseId = enrollment.getCourseId();


        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(studentId);
        }


        if (!courseRepository.existsById(courseId)) {
            throw new CourseNotFoundException(courseId);
        }


        if (enrollmentRepository.existsByStudentIdAndCourseIdAndStatus(studentId, courseId, EnrollmentStatus.ACTIVE)) {
            throw new BusinessException("El estudiante ya tiene una inscripción activa en este curso.");
        }


        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Optional<Enrollment> findById(Long id) {
        return enrollmentRepository.findById(id);
    }

    @Override
    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    @Override
    public Enrollment update(Enrollment enrollment) {

        enrollmentRepository.findById(enrollment.getId())
                .orElseThrow(() -> new EnrollmentNotFoundException(enrollment.getId()));

        return enrollmentRepository.update(enrollment);
    }

    @Override
    public boolean deleteById(Long id) {

        enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));

        return enrollmentRepository.deleteById(id);
    }

    @Override
    public long countActiveByCourseId(Long courseId) {
        return enrollmentRepository.countActiveByCourseId(courseId);
    }

    @Override
    public boolean existsByStudentIdAndCourseIdAndStatus(Long studentId, Long courseId, EnrollmentStatus status) {
        return enrollmentRepository.existsByStudentIdAndCourseIdAndStatus(studentId, courseId, status);
    }


    public Enrollment findEnrollmentOrThrow(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
    }
}