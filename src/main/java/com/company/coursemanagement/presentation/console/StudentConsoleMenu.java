package com.company.coursemanagement.presentation.console;

import com.company.coursemanagement.application.dto.StudentDTO;
import com.company.coursemanagement.application.service.StudentService;
import com.company.coursemanagement.domain.exception.BusinessException;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class StudentConsoleMenu {
    private final StudentService studentService;
    private final Scanner scanner;

    public StudentConsoleMenu(StudentService studentService, Scanner scanner) {
        this.studentService = studentService;
        this.scanner = scanner;
    }

    public void display() {
        int option = -1;
        while (option != 0) {
            System.out.println("\n--- STUDENT MENU ---");
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
                    case 1 -> createStudent();
                    case 2 -> findStudentById();
                    case 3 -> listStudents();
                    case 4 -> updateStudent();
                    case 5 -> deleteStudent();
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

    private void createStudent() {
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Birth Date (YYYY-MM-DD): ");
        LocalDate birthDate = LocalDate.parse(scanner.nextLine());

        StudentDTO created = studentService.create(new StudentDTO(null, firstName, lastName, email, birthDate));
        System.out.println("Student created successfully with ID: " + created.getId());
    }

    private void findStudentById() {
        System.out.print("Enter Student ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        StudentDTO dto = studentService.findById(id);
        printStudent(dto);
    }

    private void listStudents() {
        List<StudentDTO> students = studentService.findAll();
        if (students.isEmpty()) {
            System.out.println("No students registered.");
        } else {
            students.forEach(this::printStudent);
        }
    }

    private void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.print("New First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("New Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("New Email: ");
        String email = scanner.nextLine();
        System.out.print("New Birth Date (YYYY-MM-DD): ");
        LocalDate birthDate = LocalDate.parse(scanner.nextLine());

        StudentDTO updated = studentService.update(id, new StudentDTO(id, firstName, lastName, email, birthDate));
        System.out.println("Student updated successfully.");
        printStudent(updated);
    }

    private void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        Long id = Long.parseLong(scanner.nextLine());
        studentService.delete(id);
        System.out.println("Student deleted successfully.");
    }

    private void printStudent(StudentDTO dto) {
        System.out.printf("[%d] %s %s - Email: %s | DOB: %s%n",
                dto.getId(), dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getBirthDate());
    }
}