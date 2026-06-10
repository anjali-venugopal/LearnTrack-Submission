package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.enums.CourseStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;

import java.util.List;

public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course addCourse(String courseName, String description, int durationInWeeks) {
        Course course = new Course(
                IdGenerator.getNextCourseId(),
                courseName,
                description,
                durationInWeeks
        );
        courseRepository.save(course);
        return course;
    }

    public List<Course> listCourses() {
        return courseRepository.findAll();
    }

    public Course findCourseById(int id) throws EntityNotFoundException {
        Course course = courseRepository.findById(id);
        if (course == null) {
            throw new EntityNotFoundException("Course not found with ID: " + id);
        }
        return course;
    }

    public void updateCourse(int id, String courseName, String description, int durationInWeeks)
            throws EntityNotFoundException {
        Course course = findCourseById(id);
        course.setCourseName(courseName);
        course.setDescription(description);
        course.setDurationInWeeks(durationInWeeks);
    }

    public void setCourseActiveStatus(int id, boolean active) throws EntityNotFoundException {
        Course course = findCourseById(id);
        course.setStatus(active ? CourseStatus.ACTIVE : CourseStatus.INACTIVE);
    }

    public boolean hasCourses() {
        return !courseRepository.isEmpty();
    }
}
