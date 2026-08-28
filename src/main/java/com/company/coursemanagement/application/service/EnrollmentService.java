package com.company.coursemanagement.application.service;

import com.company.coursemanagement.domain.exception.EnrollmentNotFoundException;
import com.company.coursemanagement.domain.model.Enrollment;
import com.company.coursemanagement.domain.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public Optional<Enrollment> findById(Long id) {
        return enrollmentRepository.findById(id);
    }

    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    public Enrollment update(Enrollment enrollment) {

        if (enrollment.getId() == null || enrollmentRepository.findById(enrollment.getId()).isEmpty()) {
            throw new EnrollmentNotFoundException(enrollment.getId());
        }

        return enrollmentRepository.update(enrollment);
    }

    public boolean deleteById(Long id) {
        if (enrollmentRepository.findById(id).isEmpty()) {
            throw new EnrollmentNotFoundException(id);
        }

        return enrollmentRepository.deleteById(id);
    }

    public Enrollment findEnrollmentOrThrow(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
    }

    public Enrollment create(Enrollment enrollment) {
        return this.save(enrollment);
    }
}