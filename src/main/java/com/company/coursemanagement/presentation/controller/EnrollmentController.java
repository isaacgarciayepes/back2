package com.company.coursemanagement.presentation.controller;

import com.company.coursemanagement.application.service.EnrollmentService;
import com.company.coursemanagement.domain.exception.BusinessException;
import com.company.coursemanagement.domain.model.Enrollment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        try {
            var enrollments = enrollmentService.findAll();
            return ResponseEntity.ok(enrollments);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", "Unexpected error: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            Enrollment enrollment = enrollmentService.findEnrollmentOrThrow(id);
            return ResponseEntity.ok(enrollment);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", "Unexpected error: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Enrollment enrollment) {
        try {
            Enrollment createdEnrollment = enrollmentService.create(enrollment);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEnrollment);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", "Unexpected error: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Enrollment enrollment) {
        try {
            enrollment.setId(id);
            Enrollment updatedEnrollment = enrollmentService.update(enrollment);
            return ResponseEntity.ok(updatedEnrollment);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", "Unexpected error: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        try {
            enrollmentService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", "Unexpected error: " + e.getMessage()));
        }
    }
}