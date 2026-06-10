package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;

import java.time.LocalDate;
import java.util.List;

public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentRepository studentRepository,
                             CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public Enrollment enrollStudent(int studentId, int courseId) throws EntityNotFoundException {
        if (studentRepository.findById(studentId) == null) {
            throw new EntityNotFoundException("Student not found with ID: " + studentId);
        }

        Course course = courseRepository.findById(courseId);
        if (course == null) {
            throw new EntityNotFoundException("Course not found with ID: " + courseId);
        }
        if (!course.isActive()) {
            throw new EntityNotFoundException("Cannot enroll in an inactive course (ID: " + courseId + ").");
        }

        Enrollment enrollment = new Enrollment(
                IdGenerator.getNextEnrollmentId(),
                studentId,
                courseId,
                LocalDate.now()
        );
        enrollmentRepository.save(enrollment);
        return enrollment;
    }

    public List<Enrollment> getEnrollmentsForStudent(int studentId) throws EntityNotFoundException {
        if (studentRepository.findById(studentId) == null) {
            throw new EntityNotFoundException("Student not found with ID: " + studentId);
        }
        return enrollmentRepository.findByStudentId(studentId);
    }

    public void markEnrollmentCompleted(int enrollmentId) throws EntityNotFoundException {
        Enrollment enrollment = findEnrollmentById(enrollmentId);
        enrollment.setStatus(EnrollmentStatus.COMPLETED);
    }

    public void markEnrollmentCancelled(int enrollmentId) throws EntityNotFoundException {
        Enrollment enrollment = findEnrollmentById(enrollmentId);
        enrollment.setStatus(EnrollmentStatus.CANCELLED);
    }

    public List<Enrollment> listAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment findEnrollmentById(int id) throws EntityNotFoundException {
        Enrollment enrollment = enrollmentRepository.findById(id);
        if (enrollment == null) {
            throw new EntityNotFoundException("Enrollment not found with ID: " + id);
        }
        return enrollment;
    }

    public boolean hasEnrollments() {
        return !enrollmentRepository.isEmpty();
    }
}
