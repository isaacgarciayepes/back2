package com.company.coursemanagement.presentation.console;

import com.company.coursemanagement.application.dto.CourseDTO;
import com.company.coursemanagement.application.service.CourseService;
import com.company.coursemanagement.domain.exception.BusinessException;

import java.util.List;
import java.util.Scanner;

public class CourseConsoleMenu {
    private final CourseService courseService;
    private final Scanner scanner;

    public CourseConsoleMenu(CourseService courseService, Scanner scanner) {
        this.courseService = courseService;
        this.scanner = scanner;
    }

    public void display() {
        int option = -1;
        while (option != 0) {
            System.out.println("\n--- COURSE MENU ---");
            System.out.println("1. Create");
            System.out.println("2. Find By Id");
            System.out.println("3. List All");
            System.out.println("4. Update");
            System.out.println("5. Delete");
            System.out.println("0. Back");
            System.out.print("Select option: ");

            try {
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1 -> createCourse();
                    case 2 -> findCourseById();
                    case 3 -> listCourses();
                    case 4 -> updateCourse();
                    case 5 -> deleteCourse();
                    case 0 -> System.out.println("Returning to main menu...");
                    default -> System.out.println("Invalid option.");
                }
            } catch (BusinessException e) {
                System.out.println("Business Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void createCourse() {
        System.out.print("Code: ");
        String code = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Max Capacity: ");
        Integer maxCapacity = Integer.parseInt(scanner.nextLine());

        CourseDTO created = courseService.create(new CourseDTO(null, code, name, description, maxCapacity));
        System.out.println("Course created successfully with ID: " + created.getId());
    }

    private void findCourseById() {
        System.out.print("Enter Course ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        CourseDTO dto = courseService.findById(id);
        printCourse(dto);
    }

    private void listCourses() {
        List<CourseDTO> courses = courseService.findAll();
        if (courses.isEmpty()) {
            System.out.println("No courses registered.");
        } else {
            courses.forEach(this::printCourse);
        }
    }

    private void updateCourse() {
        System.out.print("Enter Course ID to update: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.print("New Code: ");
        String code = scanner.nextLine();
        System.out.print("New Name: ");
        String name = scanner.nextLine();
        System.out.print("New Description: ");
        String description = scanner.nextLine();
        System.out.print("New Max Capacity: ");
        Integer maxCapacity = Integer.parseInt(scanner.nextLine());

        CourseDTO updated = courseService.update(id, new CourseDTO(id, code, name, description, maxCapacity));
        System.out.println("Course updated successfully.");
        printCourse(updated);
    }

    private void deleteCourse() {
        System.out.print("Enter Course ID to delete: ");
        Long id = Long.parseLong(scanner.nextLine());
        courseService.delete(id);
        System.out.println("Course deleted successfully.");
    }

    private void printCourse(CourseDTO dto) {
        System.out.printf("[%d] Code: %s | Name: %s | Capacity: %d | Desc: %s%n",
                dto.getId(), dto.getCode(), dto.getName(), dto.getMaxCapacity(), dto.getDescription());
    }
}