package edu.university.main.service;

import edu.university.main.model.Course;
import edu.university.main.repository.CourseRepository;
import java.util.List;
import java.util.Optional;

public class CourseManagementService {
    private CourseRepository courseRepository;

    public CourseManagementService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // CREATE
    public boolean createCourse(String id, String name, String instructor, int credits, int capacity, String department) {
        if (id == null || id.isEmpty() || name == null || name.isEmpty()) {
            return false;
        }

        if (courseRepository.exists(id)) {
            return false;
        }

        Course course = new Course(id, name, instructor, credits, capacity, department);
        return courseRepository.save(course);
    }

    // READ
    public Optional<Course> getCourse(String courseId) {
        return courseRepository.findById(courseId);
    }

    public List<Course> getAllCourses() {
        return (List<Course>) courseRepository.findAll();
    }

    public List<Course> getCoursesByDepartment(String department) {
        return courseRepository.findByDepartment(department);
    }

    public List<Course> getCoursesByInstructor(String instructor) {
        return courseRepository.findByInstructor(instructor);
    }

    public List<Course> searchCoursesByName(String namePattern) {
        return courseRepository.searchByName(namePattern);
    }

    public List<Course> getAvailableCourses() {
        return courseRepository.findAvailableCourses();
    }

    public List<Course> getFullCourses() {
        return courseRepository.findFullCourses();
    }

    // UPDATE
    public boolean updateCourse(Course course) {
        if (course == null || !courseRepository.exists(course.getCourseId())) {
            return false;
        }
        return courseRepository.update(course);
    }

    public boolean updateCourseCapacity(String courseId, int newCapacity) {
        if (newCapacity < 0) {
            return false;
        }

        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (!courseOpt.isPresent()) {
            return false;
        }

        Course course = courseOpt.get();
        if (newCapacity < course.getCurrentEnrollment()) {
            return false; // Cannot reduce below current enrollment
        }

        course.setMaxCapacity(newCapacity);
        return courseRepository.update(course);
    }

    public boolean updateCourseInstructor(String courseId, String newInstructor) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (!courseOpt.isPresent()) {
            return false;
        }

        Course course = courseOpt.get();
        course.setInstructor(newInstructor);
        return courseRepository.update(course);
    }

    // DELETE
    public boolean deleteCourse(String courseId) {
        return courseRepository.delete(courseId);
    }

    public int deleteCoursesByDepartment(String department) {
        List<Course> courses = courseRepository.findByDepartment(department);
        int deletedCount = 0;

        for (Course course : courses) {
            if (courseRepository.delete(course.getCourseId())) {
                deletedCount++;
            }
        }

        return deletedCount;
    }

    // STATISTICS
    public int getTotalCourses() {
        return courseRepository.count();
    }

    public int getTotalEnrollment() {
        return courseRepository.calculateTotalEnrollment();
    }

    public double getAverageFillRate() {
        return courseRepository.calculateAverageFillRate();
    }

    public boolean hasCourses() {
        return courseRepository.count() > 0;
    }
}
