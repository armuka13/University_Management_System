package Tests.ECT;

import edu.university.main.model.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseECTCourseDemandTest {

    Course course = new Course("C001", "Test Course", "Pr.Adam", 3, 100, "CS");

    @Test
    public void testInvalidCapacity_Zero() {
        course.setMaxCapacity(0);
        course.setCurrentEnrollment(0);
        assertEquals("INVALID", course.determineCoursePopularity());
    }

    @Test
    public void testInvalidCapacity_Negative() {
        course.setMaxCapacity(-10);
        course.setCurrentEnrollment(0);
        assertEquals("INVALID", course.determineCoursePopularity());
    }

    @Test
    public void testHighDemand() {
        course.setMaxCapacity(50);
        course.setCurrentEnrollment(48); // 96%
        assertEquals("HIGH_DEMAND", course.determineCoursePopularity());
    }

    @Test
    public void testModerateDemand() {
        course.setMaxCapacity(100);
        course.setCurrentEnrollment(75); // 75%
        assertEquals("MODERATE_DEMAND", course.determineCoursePopularity());
    }

    @Test
    public void testLowDemand() {
        course.setMaxCapacity(30);
        course.setCurrentEnrollment(15); // 50%
        assertEquals("LOW_DEMAND", course.determineCoursePopularity());
    }

    @Test
    public void testVeryLowDemand() {
        course.setMaxCapacity(20);
        course.setCurrentEnrollment(6); // 30%
        assertEquals("VERY_LOW_DEMAND", course.determineCoursePopularity());
    }

}