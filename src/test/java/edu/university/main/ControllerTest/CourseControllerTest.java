package edu.university.main.ControllerTest;

import edu.university.main.controller.CourseController;
import edu.university.main.model.Course;
import edu.university.main.repository.CourseRepository;
import edu.university.main.repository.StudentRepository;
import edu.university.main.service.EnrollmentService;
import edu.university.main.view.CourseView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CourseControllerTest {

    private CourseController controller;
    private StubEnrollmentService enrollmentService;
    private SpyCourseView view;
    private FakeCourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        enrollmentService = new StubEnrollmentService();
        view = new SpyCourseView();
        courseRepository = new FakeCourseRepository();
        enrollmentService.setCourseRepository(courseRepository);
        controller = new CourseController(enrollmentService, view);
    }

    @Test
    void testCreateCourse() {
        controller.createCourse("CS101", "Introduction to Programming", "Dr. Smith", 3, 30, "Computer Science");

        assertTrue(enrollmentService.isCourseAdded());
        assertEquals("Course created: Introduction to Programming", view.getLastMessage());
    }

    @Test
    void testCheckCourseAvailabilityWithSeats() {
        FakeCourse course = new FakeCourse("CS101", "Intro to Programming", "Dr. Smith", 3, 30, "CS");
        course.setAvailableSeatsToReturn(15);
        courseRepository.addCourse(course);

        controller.checkCourseAvailability("CS101");

        assertEquals(15, view.getAvailableSeatsDisplayed());
        assertNull(view.getLastMessage());
    }

    @Test
    void testCheckCourseAvailabilityFullCourse() {
        FakeCourse course = new FakeCourse("CS201", "Data Structures", "Dr. Jones", 3, 25, "CS");
        course.setAvailableSeatsToReturn(0);
        courseRepository.addCourse(course);

        controller.checkCourseAvailability("CS201");

        assertEquals(0, view.getAvailableSeatsDisplayed());
    }

    @Test
    void testCheckCourseAvailabilityCourseNotFound() {
        controller.checkCourseAvailability("CS999");

        assertEquals("Course not found", view.getLastMessage());
        assertEquals(-1, view.getAvailableSeatsDisplayed());
    }

    @Test
    void testShowCourseFillRateHighFill() {
        FakeCourse course = new FakeCourse("CS101", "Intro to Programming", "Dr. Smith", 3, 30, "CS");
        course.setFillRateToReturn(0.85);
        courseRepository.addCourse(course);

        controller.showCourseFillRate("CS101");

        assertEquals(0.85, view.getFillRateDisplayed());
    }

    @Test
    void testShowCourseFillRateLowFill() {
        FakeCourse course = new FakeCourse("MATH301", "Advanced Calculus", "Dr. Brown", 4, 20, "Math");
        course.setFillRateToReturn(0.25);
        courseRepository.addCourse(course);

        controller.showCourseFillRate("MATH301");

        assertEquals(0.25, view.getFillRateDisplayed());
    }

    @Test
    void testShowCourseFillRateCourseNotFound() {
        controller.showCourseFillRate("CS999");

        assertEquals("Course not found", view.getLastMessage());
        assertEquals(-1.0, view.getFillRateDisplayed());
    }

    @Test
    void testShowCoursePopularityHigh() {
        FakeCourse course = new FakeCourse("CS101", "Intro to Programming", "Dr. Smith", 3, 30, "CS");
        course.setPopularityToReturn("High");
        courseRepository.addCourse(course);

        controller.showCoursePopularity("CS101");

        assertEquals("High", view.getPopularityDisplayed());
    }

    @Test
    void testShowCoursePopularityMedium() {
        FakeCourse course = new FakeCourse("MATH201", "Linear Algebra", "Dr. White", 3, 25, "Math");
        course.setPopularityToReturn("Medium");
        courseRepository.addCourse(course);

        controller.showCoursePopularity("MATH201");

        assertEquals("Medium", view.getPopularityDisplayed());
    }

    @Test
    void testShowCoursePopularityLow() {
        FakeCourse course = new FakeCourse("PHYS401", "Quantum Mechanics", "Dr. Black", 4, 15, "Physics");
        course.setPopularityToReturn("Low");
        courseRepository.addCourse(course);

        controller.showCoursePopularity("PHYS401");

        assertEquals("Low", view.getPopularityDisplayed());
    }

    @Test
    void testShowCoursePopularityCourseNotFound() {
        controller.showCoursePopularity("CS999");

        assertEquals("Course not found", view.getLastMessage());
        assertNull(view.getPopularityDisplayed());
    }

    // Test Doubles
    private static class StubEnrollmentService extends EnrollmentService {
        private boolean courseAdded = false;
        private CourseRepository courseRepository;

        public StubEnrollmentService(StudentRepository studentRepository, CourseRepository courseRepository) {
            super(studentRepository, courseRepository);
        }

        public StubEnrollmentService() {
            super();
        }

        public void setCourseRepository(CourseRepository repo) {
            this.courseRepository = repo;
        }

        @Override
        public void addCourse(Course course) {
            this.courseAdded = true;
        }

        @Override
        public CourseRepository getCourseRepository() {
            return courseRepository;
        }

        public boolean isCourseAdded() { return courseAdded; }
    }

    private static class SpyCourseView extends CourseView {
        private String lastMessage;
        private int availableSeatsDisplayed = -1;
        private double fillRateDisplayed = -1.0;
        private String popularityDisplayed;

        @Override
        public void displayMessage(String message) {
            this.lastMessage = message;
        }

        @Override
        public void displayAvailableSeats(int seats) {
            this.availableSeatsDisplayed = seats;
        }

        @Override
        public void displayFillRate(double fillRate) {
            this.fillRateDisplayed = fillRate;
        }

        @Override
        public void displayPopularity(String popularity) {
            this.popularityDisplayed = popularity;
        }

        public String getLastMessage() { return lastMessage; }
        public int getAvailableSeatsDisplayed() { return availableSeatsDisplayed; }
        public double getFillRateDisplayed() { return fillRateDisplayed; }
        public String getPopularityDisplayed() { return popularityDisplayed; }
    }

    private static class FakeCourseRepository extends CourseRepository {
        private Course course;

        public void addCourse(Course course) {
            this.course = course;
        }

        @Override
        public Optional<Course> findById(String id) {
            if (course != null && course.getCourseId().equals(id)) {
                return Optional.of(course);
            }
            return Optional.empty();
        }
    }

    private static class FakeCourse extends Course {
        private int availableSeatsToReturn;
        private double fillRateToReturn;
        private String popularityToReturn;

        public FakeCourse(String id, String name, String instructor, int credits, int capacity, String dept) {
            super(id, name, instructor, credits, capacity, dept);
        }

        public void setAvailableSeatsToReturn(int seats) {
            this.availableSeatsToReturn = seats;
        }

        public void setFillRateToReturn(double fillRate) {
            this.fillRateToReturn = fillRate;
        }

        public void setPopularityToReturn(String popularity) {
            this.popularityToReturn = popularity;
        }

        @Override
        public int calculateAvailableSeats() {
            return availableSeatsToReturn;
        }

        @Override
        public double calculateFillRate() {
            return fillRateToReturn;
        }

        @Override
        public String determineCoursePopularity() {
            return popularityToReturn;
        }
    }
}