package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Enrollment;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepository {
    private final ArrayList<Enrollment> enrollments = new ArrayList<>();

    public void save(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments);
    }

    public Enrollment findById(int id) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == id) {
                return enrollment;
            }
        }
        return null;
    }

    public List<Enrollment> findByStudentId(int studentId) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId) {
                result.add(enrollment);
            }
        }
        return result;
    }

    public boolean isEmpty() {
        return enrollments.isEmpty();
    }
}
