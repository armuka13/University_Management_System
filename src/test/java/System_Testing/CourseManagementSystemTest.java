package System_Testing;

import edu.university.main.model.Course;
import edu.university.main.repository.CourseRepository;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FR-CM: Course Management System Tests")
public class CourseManagementSystemTest {

    private Course course=new Course("CS101", "Introduction to Programming", "Dr. Smith", 3, 30, "Computer Science");

    private CourseRepository repository= new CourseRepository();


    @Test
    @DisplayName("FR-CM-001: Course Creation - Valid")
    void testCourseCreation_Valid() {
        boolean saved = repository.save(course);
        assertTrue(saved, "Course should be saved");
        assertEquals(1, repository.count());
    }

    @Test
    @DisplayName("FR-CM-002: Fill Rate - 50% Enrollment")
    void testFillRate_HalfFull() {
        course.setCurrentEnrollment(15);
        double fillRate = course.calculateFillRate();
        assertEquals(50.0, fillRate, 0.01, "15/30 = 50%");
    }

    @Test
    @DisplayName("FR-CM-002: Fill Rate - Fully Enrolled")
    void testFillRate_Full() {
        course.setCurrentEnrollment(30);
        double fillRate = course.calculateFillRate();
        assertEquals(100.0, fillRate, 0.01, "30/30 = 100%");
    }

    @Test
    @DisplayName("FR-CM-002: Fill Rate - Empty Course")
    void testFillRate_Empty() {
        double fillRate = course.calculateFillRate();
        assertEquals(0.0, fillRate, 0.01, "0/30 = 0%");
    }

    @Test
    @DisplayName("FR-CM-003: Available Seats - Normal Case")
    void testAvailableSeats_NormalCase() {
        course.setCurrentEnrollment(25);
        int available = course.calculateAvailableSeats();
        assertEquals(5, available, "30 - 25 = 5 seats");
    }

    @Test
    @DisplayName("FR-CM-003: Available Seats - Full Course")
    void testAvailableSeats_FullCourse() {
        course.setCurrentEnrollment(30);
        int available = course.calculateAvailableSeats();
        assertEquals(0, available, "Full course has 0 seats");
    }

    @Test
    @DisplayName("FR-CM-004: Waitlist Size - Over Capacity")
    void testWaitlistSize_OverCapacity() {
        int waitlist = course.calculateWaitlistSize(45);
        assertEquals(15, waitlist, "45 - 30 = 15 on waitlist");
    }

    @Test
    @DisplayName("FR-CM-004: Waitlist Size - Under Capacity")
    void testWaitlistSize_UnderCapacity() {
        int waitlist = course.calculateWaitlistSize(20);
        assertEquals(0, waitlist, "Demand < capacity means no waitlist");
    }

    @Test
    @DisplayName("FR-CM-005: Can Accept Enrollment - Yes")
    void testCanAcceptEnrollment_Yes() {
        course.setCurrentEnrollment(25);
        boolean canAccept = course.canAcceptEnrollment(3);
        assertTrue(canAccept, "25 + 3 = 28 <= 30");
    }

    @Test
    @DisplayName("FR-CM-005: Can Accept Enrollment - No")
    void testCanAcceptEnrollment_No() {
        course.setCurrentEnrollment(28);
        boolean canAccept = course.canAcceptEnrollment(5);
        assertFalse(canAccept, "28 + 5 = 33 > 30");
    }

    @Test
    @DisplayName("FR-CM-006: Class Size - Small")
    void testClassSize_Small() {
        Course smallCourse = new Course("CS100", "Seminar", "Dr. Jones", 1, 15, "CS");
        String size = smallCourse.determineClassSize();
        assertEquals("SMALL", size, "15 students is SMALL");
    }

    @Test
    @DisplayName("FR-CM-006: Class Size - Medium")
    void testClassSize_Medium() {
        Course mediumCourse = new Course("CS200", "Data Structures", "Dr. Lee", 3, 35, "CS");
        String size = mediumCourse.determineClassSize();
        assertEquals("MEDIUM", size, "35 students is MEDIUM");
    }

    @Test
    @DisplayName("FR-CM-006: Class Size - Large")
    void testClassSize_Large() {
        Course largeCourse = new Course("CS300", "Algorithms", "Dr. Wang", 3, 75, "CS");
        String size = largeCourse.determineClassSize();
        assertEquals("LARGE", size, "75 students is LARGE");
    }

    @Test
    @DisplayName("FR-CM-006: Class Size - Auditorium")
    void testClassSize_Auditorium() {
        Course audCourse = new Course("CS400", "Intro Lecture", "Dr. Brown", 3, 150, "CS");
        String size = audCourse.determineClassSize();
        assertEquals("AUDITORIUM", size, "150 students is AUDITORIUM");
    }

    @Test
    @DisplayName("FR-CM-007: Course Popularity - High Demand")
    void testCoursePopularity_HighDemand() {
        course.setCurrentEnrollment(28);
        String popularity = course.determineCoursePopularity();
        assertEquals("HIGH_DEMAND", popularity, "93% fill rate is HIGH_DEMAND");
    }

    @Test
    @DisplayName("FR-CM-007: Course Popularity - Moderate Demand")
    void testCoursePopularity_ModerateDemand() {
        course.setCurrentEnrollment(24);
        String popularity = course.determineCoursePopularity();
        assertEquals("MODERATE_DEMAND", popularity, "80% fill rate is MODERATE_DEMAND");
    }

    @Test
    @DisplayName("FR-CM-007: Course Popularity - Low Demand")
    void testCoursePopularity_LowDemand() {
        course.setCurrentEnrollment(15);
        String popularity = course.determineCoursePopularity();
        assertEquals("LOW_DEMAND", popularity, "50% fill rate is LOW_DEMAND");
    }

    @Test
    @DisplayName("FR-CM-008: Instructor Workload")
    void testInstructorWorkload() {
        double workload = course.calculateInstructorWorkload(4, 120);
        assertEquals(100.0, workload, 0.01, "(4*10) + (120*0.5) = 100");
    }

    @Test
    @DisplayName("FR-CM-009: Course Cost - In-Person")
    void testCourseCost_InPerson() {
        double cost = course.calculateCourseCost(3, "IN_PERSON", false);
        assertEquals(1200.0, cost, 0.01, "3 credits * $400");
    }

    @Test
    @DisplayName("FR-CM-009: Course Cost - Online")
    void testCourseCost_Online() {
        double cost = course.calculateCourseCost(3, "ONLINE", false);
        assertEquals(1080.0, cost, 0.01, "10% discount for online");
    }

    @Test
    @DisplayName("FR-CM-009: Course Cost - With Lab")
    void testCourseCost_WithLab() {
        double cost = course.calculateCourseCost(4, "IN_PERSON", true);
        assertEquals(1750.0, cost, 0.01, "(4*400) + 150 lab fee");
    }

    @Test
    @DisplayName("FR-CM-010: Optimal Capacity")
    void testOptimalCapacity() {
        int optimal = course.calculateOptimalCapacity(1.5, 50);
        assertEquals(75, optimal, "ceil(50 * 1.5) = 75");
    }

    @Test
    @DisplayName("FR-CM-010: Optimal Capacity - Capped at 200")
    void testOptimalCapacity_Capped() {
        int optimal = course.calculateOptimalCapacity(2.5, 100);
        assertEquals(200, optimal, "Should cap at 200");
    }
}