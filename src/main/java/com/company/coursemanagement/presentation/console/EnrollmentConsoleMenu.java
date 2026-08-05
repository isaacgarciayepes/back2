package com.company.coursemanagement.presentation.console;

import com.company.coursemanagement.application.dto.EnrollmentDTO;
import com.company.coursemanagement.application.service.EnrollmentService;
import com.company.coursemanagement.domain.exception.BusinessException;

import java.util.List;
import java.util.Scanner;

public class EnrollmentConsoleMenu {
    private final EnrollmentService enrollmentService;
    private final Scanner scanner;

    public EnrollmentConsoleMenu(EnrollmentService enrollmentService, Scanner scanner) {
        this.enrollmentService = enrollmentService;
        this.scanner = scanner;
    }

    public void display() {
        int option = -1;
        while (option != 0) {
            System.out.println("\n--- ENROLLMENT MENU ---");
            System.out.println("1. Create Enrollment");
            System.out.println("2. Find By Id");
            System.out.println("3. List All");
            System.out.println("4. Cancel Enrollment");
            System.out.println("5. Delete Enrollment");
            System.out.println("0. Back");
            System.out.print("Select option: ");

            try {
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1 -> createEnrollment();
                    case 2 -> findEnrollmentById();
                    case 3 -> listEnrollments();
                    case 4 -> cancelEnrollment();
                    case 5 -> deleteEnrollment();
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

    private void createEnrollment() {
        System.out.print("Student ID: ");
        Long studentId = Long.parseLong(scanner.nextLine());
        System.out.print("Course ID: ");
        Long courseId = Long.parseLong(scanner.nextLine());

        EnrollmentDTO created = enrollmentService.create(studentId, courseId);
        System.out.println("Enrollment created successfully with ID: " + created.getId());
    }

    private void findEnrollmentById() {
        System.out.print("Enter Enrollment ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        EnrollmentDTO dto = enrollmentService.findById(id);
        printEnrollment(dto);
    }

    private void listEnrollments() {
        List<EnrollmentDTO> enrollments = enrollmentService.findAll();
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments registered.");
        } else {
            enrollments.forEach(this::printEnrollment);
        }
    }

    private void cancelEnrollment() {
        System.out.print("Enter Enrollment ID to cancel: ");
        Long id = Long.parseLong(scanner.nextLine());
        EnrollmentDTO cancelled = enrollmentService.cancel(id);
        System.out.println("Enrollment status changed to: " + cancelled.getStatus());
    }

    private void deleteEnrollment() {
        System.out.print("Enter Enrollment ID to delete: ");
        Long id = Long.parseLong(scanner.nextLine());
        enrollmentService.delete(id);
        System.out.println("Enrollment deleted successfully.");
    }

    private void printEnrollment(EnrollmentDTO dto) {
        System.out.printf("[%d] Student ID: %d | Course ID: %d | Date: %s | Status: %s%n",
                dto.getId(), dto.getStudentId(), dto.getCourseId(), dto.getEnrollmentDate(), dto.getStatus());
    }
}