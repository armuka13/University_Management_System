package Tests.Integration_Tests;

import edu.university.main.controller.CourseController;
import edu.university.main.model.Course;
import edu.university.main.repository.CourseRepository;
import edu.university.main.repository.StudentRepository;
import edu.university.main.service.EnrollmentService;
import edu.university.main.view.CourseView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseControllerIntegrationTest {

    @Test
    void testCreateCourseSuccessfully() {
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService =
                new EnrollmentService(new StudentRepository(), courseRepository);
        SpyCourseView view = new SpyCourseView();
        CourseController controller = new CourseController(enrollmentService, view);

        controller.createCourse("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");

        assertTrue(courseRepository.exists("SWE303"));
        view.verifyMessage("Course created: Software Testing");
    }

    @Test
    void testCheckCourseAvailability() {
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService =
                new EnrollmentService(new StudentRepository(), courseRepository);
        SpyCourseView view = new SpyCourseView();
        CourseController controller = new CourseController(enrollmentService, view);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        course.setCurrentEnrollment(10);
        courseRepository.save(course);

        controller.checkCourseAvailability("SWE303");

        view.verifyAvailableSeats(20);
    }

    @Test
    void testShowCourseFillRate() {
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService =
                new EnrollmentService(new StudentRepository(), courseRepository);
        SpyCourseView view = new SpyCourseView();
        CourseController controller = new CourseController(enrollmentService, view);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        course.setCurrentEnrollment(15);
        courseRepository.save(course);

        controller.showCourseFillRate("SWE303");

        view.verifyFillRate(50.0);
    }

    @Test
    void testShowCoursePopularityHighDemand() {
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService =
                new EnrollmentService(new StudentRepository(), courseRepository);
        SpyCourseView view = new SpyCourseView();
        CourseController controller = new CourseController(enrollmentService, view);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        course.setCurrentEnrollment(26);
        courseRepository.save(course);

        controller.showCoursePopularity("SWE303");

        view.verifyPopularity("MODERATE_DEMAND");
    }

    // ===== SPY VIEW =====
    private static class SpyCourseView extends CourseView {
        private String message;
        private Integer availableSeats;
        private Double fillRate;
        private String popularity;

        @Override
        public void displayMessage(String message) {
            this.message = message;
        }

        @Override
        public void displayAvailableSeats(int seats) {
            this.availableSeats = seats;
        }

        @Override
        public void displayFillRate(double rate) {
            this.fillRate = rate;
        }

        @Override
        public void displayPopularity(String popularity) {
            this.popularity = popularity;
        }

        void verifyMessage(String expected) {
            assertEquals(expected, message);
        }

        void verifyAvailableSeats(int expected) {
            assertEquals(expected, availableSeats);
        }

        void verifyFillRate(double expected) {
            assertEquals(expected, fillRate, 0.01);
        }

        void verifyPopularity(String expected) {
            assertEquals(expected, popularity);
        }
    }
}

