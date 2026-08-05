package com.company.coursemanagement.application.service;

import com.company.coursemanagement.application.dto.EnrollmentDTO;
import com.company.coursemanagement.domain.exception.BusinessException;
import com.company.coursemanagement.domain.exception.CourseNotFoundException;
import com.company.coursemanagement.domain.exception.EnrollmentNotFoundException;
import com.company.coursemanagement.domain.exception.StudentNotFoundException;
import com.company.coursemanagement.domain.model.Course;
import com.company.coursemanagement.domain.model.Enrollment;
import com.company.coursemanagement.domain.model.EnrollmentStatus;
import com.company.coursemanagement.domain.repository.CourseRepository;
import com.company.coursemanagement.domain.repository.EnrollmentRepository;
import com.company.coursemanagement.domain.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;

public class EnrollmentService {
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

    public EnrollmentDTO create(Long studentId, Long courseId) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(studentId);
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        if (enrollmentRepository.existsByStudentIdAndCourseIdAndStatus(studentId, courseId, EnrollmentStatus.ACTIVE)) {
            throw new BusinessException("Student is already actively enrolled in this course.");
        }

        long activeCount = enrollmentRepository.countActiveByCourseId(courseId);
        if (activeCount >= course.getMaxCapacity()) {
            throw new BusinessException("Course max capacity reached (" + course.getMaxCapacity() + ").");
        }

        Enrollment enrollment = new Enrollment(null, studentId, courseId, LocalDate.now(), EnrollmentStatus.ACTIVE);
        Enrollment saved = enrollmentRepository.save(enrollment);
        return mapToDTO(saved);
    }

    public EnrollmentDTO findById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
        return mapToDTO(enrollment);
    }

    public List<EnrollmentDTO> findAll() {
        return enrollmentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public EnrollmentDTO cancel(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));

        if (enrollment.getStatus() == EnrollmentStatus.CANCELLED) {
            throw new BusinessException("Enrollment is already cancelled.");
        }

        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        Enrollment updated = enrollmentRepository.update(enrollment);
        return mapToDTO(updated);
    }

    public void delete(Long id) {
        if (enrollmentRepository.findById(id).isEmpty()) {
            throw new EnrollmentNotFoundException(id);
        }
        enrollmentRepository.deleteById(id);
    }

    private EnrollmentDTO mapToDTO(Enrollment enrollment) {
        return new EnrollmentDTO(
                enrollment.getId(),
                enrollment.getStudentId(),
                enrollment.getCourseId(),
                enrollment.getEnrollmentDate(),
                enrollment.getStatus()
        );
    }
}