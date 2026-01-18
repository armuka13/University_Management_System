package Tests.Unit_Testing.ModelTest;

import edu.university.main.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course("CS101", "Introduction to Programming", "Dr. Smith", 3, 30, "Computer Science");
        course.setCurrentEnrollment(20);
    }

    @Test
    void testCalculateFillRate() {
        double fillRate = course.calculateFillRate();

        assertEquals(66.67, fillRate);
    }

    @Test
    void testCalculateFillRateFullClass() {
        course.setCurrentEnrollment(30);

        double fillRate = course.calculateFillRate();

        assertEquals(100.0, fillRate);
    }

    @Test
    void testCalculateFillRateEmptyClass() {
        course.setCurrentEnrollment(0);

        double fillRate = course.calculateFillRate();

        assertEquals(0.0, fillRate);
    }

    @Test
    void testCalculateFillRateInvalidCapacity() {
        course.setMaxCapacity(0);

        double fillRate = course.calculateFillRate();

        assertEquals(-1.0, fillRate);
    }

    @Test
    void testCalculateAvailableSeats() {
        int available = course.calculateAvailableSeats();

        assertEquals(10, available);
    }

    @Test
    void testCalculateAvailableSeatsFullCourse() {
        course.setCurrentEnrollment(30);

        int available = course.calculateAvailableSeats();

        assertEquals(0, available);
    }

    @Test
    void testCalculateAvailableSeatsOverEnrolled() {
        course.setCurrentEnrollment(35);

        int available = course.calculateAvailableSeats();

        assertEquals(0, available);
    }

    @Test
    void testCalculateAvailableSeatsInvalid() {
        course.setMaxCapacity(-5);

        int available = course.calculateAvailableSeats();

        assertEquals(-1, available);
    }

    @Test
    void testCalculateWaitlistSize() {
        int waitlist = course.calculateWaitlistSize(45);

        assertEquals(15, waitlist);
    }

    @Test
    void testCalculateWaitlistSizeNoDemand() {
        int waitlist = course.calculateWaitlistSize(20);

        assertEquals(0, waitlist);
    }

    @Test
    void testCalculateWaitlistSizeInvalidDemand() {
        int waitlist = course.calculateWaitlistSize(-10);

        assertEquals(-1, waitlist);
    }

    @Test
    void testCanAcceptEnrollmentTrue() {
        boolean canAccept = course.canAcceptEnrollment(5);

        assertTrue(canAccept);
    }

    @Test
    void testCanAcceptEnrollmentExactFit() {
        boolean canAccept = course.canAcceptEnrollment(10);

        assertTrue(canAccept);
    }

    @Test
    void testCanAcceptEnrollmentExceedsCapacity() {
        boolean canAccept = course.canAcceptEnrollment(15);

        assertFalse(canAccept);
    }

    @Test
    void testCanAcceptEnrollmentAlreadyFull() {
        course.setCurrentEnrollment(30);

        boolean canAccept = course.canAcceptEnrollment(1);

        assertFalse(canAccept);
    }

    @Test
    void testCanAcceptEnrollmentInvalidSeats() {
        boolean canAccept = course.canAcceptEnrollment(0);

        assertFalse(canAccept);
    }

    @Test
    void testHasWaitlistTrue() {
        course.setCurrentEnrollment(30);

        boolean hasWaitlist = course.hasWaitlist();

        assertTrue(hasWaitlist);
    }

    @Test
    void testHasWaitlistFalse() {
        boolean hasWaitlist = course.hasWaitlist();

        assertFalse(hasWaitlist);
    }

    @Test
    void testDetermineClassSizeSmall() {
        course.setMaxCapacity(15);

        String size = course.determineClassSize();

        assertEquals("SMALL", size);
    }

    @Test
    void testDetermineClassSizeMedium() {
        course.setMaxCapacity(35);

        String size = course.determineClassSize();

        assertEquals("MEDIUM", size);
    }

    @Test
    void testDetermineClassSizeLarge() {
        course.setMaxCapacity(75);

        String size = course.determineClassSize();

        assertEquals("LARGE", size);
    }

    @Test
    void testDetermineClassSizeAuditorium() {
        course.setMaxCapacity(150);

        String size = course.determineClassSize();

        assertEquals("AUDITORIUM", size);
    }

    @Test
    void testDetermineClassSizeInvalid() {
        course.setMaxCapacity(-5);

        String size = course.determineClassSize();

        assertEquals("INVALID", size);
    }

    @Test
    void testDetermineCoursePopularityHighDemand() {
        course.setCurrentEnrollment(28);

        String popularity = course.determineCoursePopularity();

        assertEquals("HIGH_DEMAND", popularity);
    }

    @Test
    void testDetermineCoursePopularityModerateDemand() {
        course.setCurrentEnrollment(22);

        String popularity = course.determineCoursePopularity();

        assertEquals("MODERATE_DEMAND", popularity);
    }

    @Test
    void testDetermineCoursePopularityLowDemand() {
        course.setCurrentEnrollment(15);

        String popularity = course.determineCoursePopularity();

        assertEquals("LOW_DEMAND", popularity);
    }

    @Test
    void testDetermineCoursePopularityVeryLowDemand() {
        course.setCurrentEnrollment(8);

        String popularity = course.determineCoursePopularity();

        assertEquals("VERY_LOW_DEMAND", popularity);
    }

    @Test
    void testDetermineCoursePopularityInvalid() {
        course.setMaxCapacity(0);

        String popularity = course.determineCoursePopularity();

        assertEquals("INVALID", popularity);
    }

    @Test
    void testCalculateInstructorWorkload() {
        double workload = course.calculateInstructorWorkload(4, 120);

        assertEquals(100.0, workload);
    }

    @Test
    void testCalculateInstructorWorkloadLightLoad() {
        double workload = course.calculateInstructorWorkload(2, 40);

        assertEquals(40.0, workload);
    }

    @Test
    void testCalculateInstructorWorkloadInvalidCourses() {
        double workload = course.calculateInstructorWorkload(0, 120);

        assertEquals(-1.0, workload);
    }

    @Test
    void testCalculateInstructorWorkloadNegativeStudents() {
        double workload = course.calculateInstructorWorkload(3, -10);

        assertEquals(-1.0, workload);
    }

    @Test
    void testCalculateCourseCostInPerson() {
        double cost = course.calculateCourseCost(3, "IN_PERSON", false);

        assertEquals(1200.0, cost);
    }

    @Test
    void testCalculateCourseCostOnline() {
        double cost = course.calculateCourseCost(3, "ONLINE", false);

        assertEquals(1080.0, cost);
    }

    @Test
    void testCalculateCourseCostHybrid() {
        double cost = course.calculateCourseCost(3, "HYBRID", false);

        assertEquals(1140.0, cost);
    }

    @Test
    void testCalculateCourseCostWithLab() {
        double cost = course.calculateCourseCost(4, "IN_PERSON", true);

        assertEquals(1750.0, cost);
    }

    @Test
    void testCalculateCourseCostOnlineWithLab() {
        double cost = course.calculateCourseCost(3, "ONLINE", true);

        assertEquals(1230.0, cost);
    }

    @Test
    void testCalculateCourseCostInvalidCredits() {
        double cost = course.calculateCourseCost(-1, "IN_PERSON", false);

        assertEquals(-1.0, cost);
    }

    @Test
    void testCalculateCourseCostTooManyCredits() {
        double cost = course.calculateCourseCost(7, "IN_PERSON", false);

        assertEquals(-1.0, cost);
    }

    @Test
    void testCalculateOptimalCapacity() {
        int optimal = course.calculateOptimalCapacity(1.5, 40);

        assertEquals(60, optimal);
    }

    @Test
    void testCalculateOptimalCapacityLowDemand() {
        int optimal = course.calculateOptimalCapacity(0.8, 50);

        assertEquals(40, optimal);
    }

    @Test
    void testCalculateOptimalCapacityHighDemand() {
        int optimal = course.calculateOptimalCapacity(2.5, 100);

        assertEquals(200, optimal);
    }

    @Test
    void testCalculateOptimalCapacityExceedsMax() {
        int optimal = course.calculateOptimalCapacity(3.0, 100);

        assertEquals(200, optimal);
    }

    @Test
    void testCalculateOptimalCapacityInvalidDemand() {
        int optimal = course.calculateOptimalCapacity(-0.5, 40);

        assertEquals(-1, optimal);
    }

    @Test
    void testCalculateOptimalCapacityDemandTooHigh() {
        int optimal = course.calculateOptimalCapacity(3.5, 40);

        assertEquals(-1, optimal);
    }

    @Test
    void testCalculateOptimalCapacityZeroBase() {
        int optimal = course.calculateOptimalCapacity(1.5, 0);

        assertEquals(-1, optimal);
    }

    @Test
    void testGettersAndSetters() {
        course.setCourseName("Advanced Programming");
        course.setInstructor("Dr. Johnson");
        course.setCredits(4);
        course.setMaxCapacity(40);
        course.setCurrentEnrollment(25);
        course.setDepartment("Engineering");
        course.setCourseLevel("GRADUATE");
        course.setSemester("Fall");
        course.setYear(2024);
        course.setDeliveryMode("HYBRID");

        assertEquals("CS101", course.getCourseId());
        assertEquals("Advanced Programming", course.getCourseName());
        assertEquals("Dr. Johnson", course.getInstructor());
        assertEquals(4, course.getCredits());
        assertEquals(40, course.getMaxCapacity());
        assertEquals(25, course.getCurrentEnrollment());
        assertEquals("Engineering", course.getDepartment());
        assertEquals("GRADUATE", course.getCourseLevel());
        assertEquals("Fall", course.getSemester());
        assertEquals(2024, course.getYear());
        assertEquals("HYBRID", course.getDeliveryMode());
        assertNotNull(course.getEnrolledStudentIds());
        assertNotNull(course.getPrerequisites());
    }
}