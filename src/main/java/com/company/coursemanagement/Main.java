package com.company.coursemanagement;

import com.company.coursemanagement.application.service.CourseService;
import com.company.coursemanagement.application.service.EnrollmentService;
import com.company.coursemanagement.application.service.StudentService;
import com.company.coursemanagement.domain.repository.CourseRepository;
import com.company.coursemanagement.domain.repository.EnrollmentRepository;
import com.company.coursemanagement.domain.repository.StudentRepository;
import com.company.coursemanagement.infrastructure.persistence.InMemoryCourseRepository;
import com.company.coursemanagement.infrastructure.persistence.InMemoryEnrollmentRepository;
import com.company.coursemanagement.infrastructure.persistence.InMemoryStudentRepository;
import com.company.coursemanagement.presentation.console.CourseConsoleMenu;
import com.company.coursemanagement.presentation.console.EnrollmentConsoleMenu;
import com.company.coursemanagement.presentation.console.MainConsoleMenu;
import com.company.coursemanagement.presentation.console.StudentConsoleMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Instanciar Repositorios (Infraestructura)
        StudentRepository studentRepository = new InMemoryStudentRepository();
        CourseRepository courseRepository = new InMemoryCourseRepository();
        EnrollmentRepository enrollmentRepository = new InMemoryEnrollmentRepository();

        // 2. Instanciar Servicios (Aplicación)
        StudentService studentService = new StudentService(studentRepository);
        CourseService courseService = new CourseService(courseRepository);
        EnrollmentService enrollmentService = new EnrollmentService(enrollmentRepository, studentRepository, courseRepository);

        // 3. Instanciar Menús (Presentación)
        StudentConsoleMenu studentConsoleMenu = new StudentConsoleMenu(studentService, scanner);
        CourseConsoleMenu courseConsoleMenu = new CourseConsoleMenu(courseService, scanner);
        EnrollmentConsoleMenu enrollmentConsoleMenu = new EnrollmentConsoleMenu(enrollmentService, scanner);

        MainConsoleMenu mainConsoleMenu = new MainConsoleMenu(
                studentConsoleMenu,
                courseConsoleMenu,
                enrollmentConsoleMenu,
                scanner
        );

        // 4. Iniciar Aplicación
        mainConsoleMenu.start();
    }
}