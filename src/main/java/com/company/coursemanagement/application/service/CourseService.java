package com.company.coursemanagement.application.service;

import com.company.coursemanagement.application.dto.CourseDTO;
import com.company.coursemanagement.domain.exception.CourseNotFoundException;
import com.company.coursemanagement.domain.model.Course;
import com.company.coursemanagement.domain.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

public class CourseService implements CourseRepository {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course save(Course course) {
        return null;
    }

    @Override
    public Optional<Course> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Course> findAll() {
        return null;
    }

    @Override
    public Course update(Course course) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }
}