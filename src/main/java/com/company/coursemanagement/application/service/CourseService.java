package com.company.coursemanagement.application.service;

import com.company.coursemanagement.application.dto.CourseDTO;
import com.company.coursemanagement.domain.exception.CourseNotFoundException;
import com.company.coursemanagement.domain.model.Course;
import com.company.coursemanagement.domain.repository.CourseRepository;

import java.util.List;

public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public CourseDTO create(CourseDTO dto) {
        Course course = mapToEntity(dto);
        Course saved = courseRepository.save(course);
        return mapToDTO(saved);
    }

    public CourseDTO findById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        return mapToDTO(course);
    }

    public List<CourseDTO> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public CourseDTO update(Long id, CourseDTO dto) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        Course course = mapToEntity(dto);
        course.setId(id);
        Course updated = courseRepository.update(course);
        return mapToDTO(updated);
    }

    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }

    private Course mapToEntity(CourseDTO dto) {
        return new Course(dto.getId(), dto.getCode(), dto.getName(), dto.getDescription(), dto.getMaxCapacity());
    }

    private CourseDTO mapToDTO(Course course) {
        return new CourseDTO(course.getId(), course.getCode(), course.getName(), course.getDescription(), course.getMaxCapacity());
    }
}