package com.company.coursemanagement.presentation.console;

import java.util.Scanner;

public class MainConsoleMenu {
    private final StudentConsoleMenu studentConsoleMenu;
    private final CourseConsoleMenu courseConsoleMenu;
    private final EnrollmentConsoleMenu enrollmentConsoleMenu;
    private final Scanner scanner;

    public MainConsoleMenu(StudentConsoleMenu studentConsoleMenu,
                           CourseConsoleMenu courseConsoleMenu,
                           EnrollmentConsoleMenu enrollmentConsoleMenu,
                           Scanner scanner) {
        this.studentConsoleMenu = studentConsoleMenu;
        this.courseConsoleMenu = courseConsoleMenu;
        this.enrollmentConsoleMenu = enrollmentConsoleMenu;
        this.scanner = scanner;
    }

    public void start() {
        int option = -1;
        while (option != 0) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Students");
            System.out.println("2. Courses");
            System.out.println("3. Enrollments");
            System.out.println("0. Exit");
            System.out.print("Select option: ");

            try {
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1 -> studentConsoleMenu.display();
                    case 2 -> courseConsoleMenu.display();
                    case 3 -> enrollmentConsoleMenu.display();
                    case 0 -> System.out.println("Exiting system. Goodbye!");
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}