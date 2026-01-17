package edu.university.main.controller;



import edu.university.main.model.Course;
import edu.university.main.service.EnrollmentService;
import edu.university.main.view.CourseView;
import java.util.Optional;

public class CourseController {
    private EnrollmentService enrollmentService;
    private CourseView view;

    public CourseController(EnrollmentService enrollmentService, CourseView view) {
        this.enrollmentService = enrollmentService;
        this.view = view;
    }

    public void createCourse(String id, String name, String instructor, int credits, int capacity, String dept) {
        Course course = new Course(id, name, instructor, credits, capacity, dept);
        enrollmentService.addCourse(course);
        view.displayMessage("Course created: " + name);
    }

    public void checkCourseAvailability(String courseId) {
        Optional<Course> courseOpt = enrollmentService.getCourseRepository().findById(courseId);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            int available = course.calculateAvailableSeats();
            view.displayAvailableSeats(available);
        } else {
            view.displayMessage("Course not found");
        }
    }

    public void showCourseFillRate(String courseId) {
        Optional<Course> courseOpt = enrollmentService.getCourseRepository().findById(courseId);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            double fillRate = course.calculateFillRate();
            view.displayFillRate(fillRate);
        } else {
            view.displayMessage("Course not found");
        }
    }

    public void showCoursePopularity(String courseId) {
        Optional<Course> courseOpt = enrollmentService.getCourseRepository().findById(courseId);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            String popularity = course.determineCoursePopularity();
            view.displayPopularity(popularity);
        } else {
            view.displayMessage("Course not found");
        }
    }
}
