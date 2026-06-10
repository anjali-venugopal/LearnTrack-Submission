package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private final ArrayList<Student> students = new ArrayList<>();

    public void save(Student student) {
        students.add(student);
    }

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public Student findById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public void remove(Student student) {
        students.remove(student);
    }

    public boolean isEmpty() {
        return students.isEmpty();
    }
}
