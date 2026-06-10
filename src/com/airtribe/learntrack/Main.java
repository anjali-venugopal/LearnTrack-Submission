package com.airtribe.learntrack;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.constants.MenuOptions;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;
import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public Main() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentRepository enrollmentRepository = new EnrollmentRepository();

        this.studentService = new StudentService(studentRepository);
        this.courseService = new CourseService(courseRepository);
        this.enrollmentService = new EnrollmentService(
                enrollmentRepository, studentRepository, courseRepository);
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    private void run() {
        boolean running = true;
        System.out.println("========================================");
        System.out.println("   Welcome to " + AppConstants.APP_NAME);
        System.out.println("   " + AppConstants.APP_TAGLINE);
        System.out.println("========================================");

        while (running) {
            printMainMenu();
            int choice = readMenuChoice(MenuOptions.EXIT, MenuOptions.MAIN_ENROLLMENT_MANAGEMENT);
            switch (choice) {
                case MenuOptions.MAIN_STUDENT_MANAGEMENT -> handleStudentMenu();
                case MenuOptions.MAIN_COURSE_MANAGEMENT -> handleCourseMenu();
                case MenuOptions.MAIN_ENROLLMENT_MANAGEMENT -> handleEnrollmentMenu();
                case MenuOptions.EXIT -> {
                    System.out.println(AppConstants.EXIT_MESSAGE);
                    running = false;
                }
                default -> System.out.println(AppConstants.INVALID_OPTION_MESSAGE);
            }
        }
        scanner.close();
    }

    private void printMainMenu() {
        System.out.println();
        System.out.println("--- Main Menu ---");
        System.out.println(MenuOptions.MAIN_STUDENT_MANAGEMENT + ". Student Management");
        System.out.println(MenuOptions.MAIN_COURSE_MANAGEMENT + ". Course Management");
        System.out.println(MenuOptions.MAIN_ENROLLMENT_MANAGEMENT + ". Enrollment Management");
        System.out.println(MenuOptions.EXIT + ". Exit");
        System.out.print("Enter your choice: ");
    }

    private void handleStudentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Student Management ---");
            System.out.println(MenuOptions.STUDENT_ADD + ". Add new student");
            System.out.println(MenuOptions.STUDENT_VIEW_ALL + ". View all students");
            System.out.println(MenuOptions.STUDENT_SEARCH_BY_ID + ". Search student by ID");
            System.out.println(MenuOptions.STUDENT_UPDATE + ". Update student");
            System.out.println(MenuOptions.STUDENT_DEACTIVATE + ". Deactivate student");
            System.out.println(MenuOptions.BACK + ". Back to main menu");
            System.out.print("Enter your choice: ");

            int choice = readMenuChoice(MenuOptions.BACK, MenuOptions.STUDENT_DEACTIVATE);
            switch (choice) {
                case MenuOptions.STUDENT_ADD -> addStudent();
                case MenuOptions.STUDENT_VIEW_ALL -> listStudents();
                case MenuOptions.STUDENT_SEARCH_BY_ID -> searchStudentById();
                case MenuOptions.STUDENT_UPDATE -> updateStudent();
                case MenuOptions.STUDENT_DEACTIVATE -> deactivateStudent();
                case MenuOptions.BACK -> back = true;
                default -> System.out.println(AppConstants.INVALID_OPTION_MESSAGE);
            }
        }
    }

    private void handleCourseMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Course Management ---");
            System.out.println(MenuOptions.COURSE_ADD + ". Add new course");
            System.out.println(MenuOptions.COURSE_VIEW_ALL + ". View all courses");
            System.out.println(MenuOptions.COURSE_ACTIVATE + ". Activate course");
            System.out.println(MenuOptions.COURSE_DEACTIVATE + ". Deactivate course");
            System.out.println(MenuOptions.BACK + ". Back to main menu");
            System.out.print("Enter your choice: ");

            int choice = readMenuChoice(MenuOptions.BACK, MenuOptions.COURSE_DEACTIVATE);
            switch (choice) {
                case MenuOptions.COURSE_ADD -> addCourse();
                case MenuOptions.COURSE_VIEW_ALL -> listCourses();
                case MenuOptions.COURSE_ACTIVATE -> setCourseStatus(true);
                case MenuOptions.COURSE_DEACTIVATE -> setCourseStatus(false);
                case MenuOptions.BACK -> back = true;
                default -> System.out.println(AppConstants.INVALID_OPTION_MESSAGE);
            }
        }
    }

    private void handleEnrollmentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Enrollment Management ---");
            System.out.println(MenuOptions.ENROLLMENT_ENROLL + ". Enroll student in course");
            System.out.println(MenuOptions.ENROLLMENT_VIEW_BY_STUDENT + ". View enrollments for a student");
            System.out.println(MenuOptions.ENROLLMENT_VIEW_ALL + ". View all enrollments");
            System.out.println(MenuOptions.ENROLLMENT_MARK_COMPLETED + ". Mark enrollment as completed");
            System.out.println(MenuOptions.ENROLLMENT_MARK_CANCELLED + ". Mark enrollment as cancelled");
            System.out.println(MenuOptions.BACK + ". Back to main menu");
            System.out.print("Enter your choice: ");

            int choice = readMenuChoice(MenuOptions.BACK, MenuOptions.ENROLLMENT_MARK_CANCELLED);
            switch (choice) {
                case MenuOptions.ENROLLMENT_ENROLL -> enrollStudent();
                case MenuOptions.ENROLLMENT_VIEW_BY_STUDENT -> viewEnrollmentsForStudent();
                case MenuOptions.ENROLLMENT_VIEW_ALL -> listAllEnrollments();
                case MenuOptions.ENROLLMENT_MARK_COMPLETED -> updateEnrollmentStatus(EnrollmentStatus.COMPLETED);
                case MenuOptions.ENROLLMENT_MARK_CANCELLED -> updateEnrollmentStatus(EnrollmentStatus.CANCELLED);
                case MenuOptions.BACK -> back = true;
                default -> System.out.println(AppConstants.INVALID_OPTION_MESSAGE);
            }
        }
    }

    private void addStudent() {
        try {
            System.out.print("First name: ");
            String firstName = scanner.nextLine();
            InputValidator.requireNonEmpty(firstName, "First name");

            System.out.print("Last name: ");
            String lastName = scanner.nextLine();
            InputValidator.requireNonEmpty(lastName, "Last name");

            System.out.print("Batch: ");
            String batch = scanner.nextLine();
            InputValidator.requireNonEmpty(batch, "Batch");

            System.out.print("Email (press Enter to skip): ");
            String email = scanner.nextLine().trim();

            Student student;
            if (email.isEmpty()) {
                student = studentService.addStudent(firstName, lastName, batch);
            } else {
                student = studentService.addStudent(firstName, lastName, email, batch);
            }
            System.out.println("Student added successfully: " + student);
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listStudents() {
        List<Student> students = studentService.listStudents();
        if (students.isEmpty()) {
            System.out.println(AppConstants.NO_STUDENTS_MESSAGE);
            return;
        }
        System.out.println("--- All Students ---");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void searchStudentById() {
        try {
            int id = readPositiveInt("Enter student ID: ");
            Student student = studentService.findStudentById(id);
            System.out.println("Student found: " + student);
            System.out.println("Display name: " + student.getDisplayName());
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateStudent() {
        try {
            int id = readPositiveInt("Enter student ID to update: ");
            studentService.findStudentById(id);

            System.out.print("New first name: ");
            String firstName = scanner.nextLine();
            InputValidator.requireNonEmpty(firstName, "First name");

            System.out.print("New last name: ");
            String lastName = scanner.nextLine();
            InputValidator.requireNonEmpty(lastName, "Last name");

            System.out.print("New email: ");
            String email = scanner.nextLine();

            System.out.print("New batch: ");
            String batch = scanner.nextLine();
            InputValidator.requireNonEmpty(batch, "Batch");

            studentService.updateStudent(id, firstName, lastName, email, batch);
            System.out.println("Student updated successfully.");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deactivateStudent() {
        try {
            int id = readPositiveInt("Enter student ID to deactivate: ");
            studentService.deactivateStudent(id);
            System.out.println("Student deactivated successfully.");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addCourse() {
        try {
            System.out.print("Course name: ");
            String name = scanner.nextLine();
            InputValidator.requireNonEmpty(name, "Course name");

            System.out.print("Description: ");
            String description = scanner.nextLine();

            int duration = readPositiveInt("Duration in weeks: ");
            Course course = courseService.addCourse(name, description, duration);
            System.out.println("Course added successfully: " + course);
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listCourses() {
        List<Course> courses = courseService.listCourses();
        if (courses.isEmpty()) {
            System.out.println(AppConstants.NO_COURSES_MESSAGE);
            return;
        }
        System.out.println("--- All Courses ---");
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private void setCourseStatus(boolean active) {
        try {
            String action = active ? "activate" : "deactivate";
            int id = readPositiveInt("Enter course ID to " + action + ": ");
            courseService.setCourseActiveStatus(id, active);
            System.out.println("Course " + action + "d successfully.");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void enrollStudent() {
        try {
            if (!studentService.hasStudents()) {
                System.out.println("No students available. Please add a student first.");
                return;
            }
            if (!courseService.hasCourses()) {
                System.out.println("No courses available. Please add a course first.");
                return;
            }

            int studentId = readPositiveInt("Enter student ID: ");
            int courseId = readPositiveInt("Enter course ID: ");
            Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
            System.out.println("Enrollment created: " + enrollment);
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewEnrollmentsForStudent() {
        try {
            int studentId = readPositiveInt("Enter student ID: ");
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsForStudent(studentId);
            if (enrollments.isEmpty()) {
                System.out.println("No enrollments found for student ID: " + studentId);
                return;
            }
            System.out.println("--- Enrollments for Student " + studentId + " ---");
            for (Enrollment enrollment : enrollments) {
                printEnrollmentDetails(enrollment);
            }
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.listAllEnrollments();
        if (enrollments.isEmpty()) {
            System.out.println(AppConstants.NO_ENROLLMENTS_MESSAGE);
            return;
        }
        System.out.println("--- All Enrollments ---");
        for (Enrollment enrollment : enrollments) {
            printEnrollmentDetails(enrollment);
        }
    }

    private void updateEnrollmentStatus(EnrollmentStatus status) {
        try {
            int enrollmentId = readPositiveInt("Enter enrollment ID: ");
            if (status == EnrollmentStatus.COMPLETED) {
                enrollmentService.markEnrollmentCompleted(enrollmentId);
                System.out.println("Enrollment marked as completed.");
            } else {
                enrollmentService.markEnrollmentCancelled(enrollmentId);
                System.out.println("Enrollment marked as cancelled.");
            }
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void printEnrollmentDetails(Enrollment enrollment) {
        System.out.println(enrollment);
        try {
            Student student = studentService.findStudentById(enrollment.getStudentId());
            Course course = courseService.findCourseById(enrollment.getCourseId());
            System.out.println("  Student: " + student.getDisplayName());
            System.out.println("  Course: " + course.getCourseName());
        } catch (EntityNotFoundException e) {
            System.out.println("  (Related student or course no longer available)");
        }
    }

    private int readMenuChoice(int min, int max) {
        try {
            int choice = InputValidator.parseNonNegativeInt(scanner.nextLine(), "menu choice");
            if (choice < min || choice > max) {
                System.out.println("Please enter a number between " + min + " and " + max + ".");
                return -1;
            }
            return choice;
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
            return -1;
        }
    }

    private int readPositiveInt(String prompt) throws InvalidInputException {
        System.out.print(prompt);
        return InputValidator.parsePositiveInt(scanner.nextLine(), "ID");
    }
}
