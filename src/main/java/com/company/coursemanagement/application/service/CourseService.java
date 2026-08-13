package com.company.coursemanagement.application.service;

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
        return courseRepository.save(course);
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course update(Course course) {
        if (!courseRepository.existsById(course.getId())) {
            throw new CourseNotFoundException(course.getId());
        }
        return courseRepository.update(course);
    }

    @Override
    public boolean deleteById(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        return courseRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return courseRepository.existsById(id);
    }

    public Course findCourseOrThrow(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    public Course create(Course course) {
        return this.save(course);
    }
}