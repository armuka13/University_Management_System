package edu.university.main.ModelTest;

import edu.university.main.model.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department("D001", "Computer Science", "Dr. Smith", 500000.0);
        department.setNumberOfFaculty(20);
        department.setNumberOfStudents(400);
    }

    @Test
    void testCalculateBudgetPerStudent() {
        double perStudent = department.calculateBudgetPerStudent();

        assertEquals(1250.0, perStudent);
    }

    @Test
    void testCalculateBudgetPerStudentZeroStudents() {
        department.setNumberOfStudents(0);

        double perStudent = department.calculateBudgetPerStudent();

        assertEquals(-1.0, perStudent);
    }

    @Test
    void testCalculateBudgetPerStudentNegativeBudget() {
        department.setBudget(-1000.0);

        double perStudent = department.calculateBudgetPerStudent();

        assertEquals(-1.0, perStudent);
    }

    @Test
    void testCalculateBudgetPerFaculty() {
        double perFaculty = department.calculateBudgetPerFaculty();

        assertEquals(25000.0, perFaculty);
    }

    @Test
    void testCalculateBudgetPerFacultyZeroFaculty() {
        department.setNumberOfFaculty(0);

        double perFaculty = department.calculateBudgetPerFaculty();

        assertEquals(-1.0, perFaculty);
    }

    @Test
    void testCalculateBudgetPerFacultyNegativeBudget() {
        department.setBudget(-5000.0);

        double perFaculty = department.calculateBudgetPerFaculty();

        assertEquals(-1.0, perFaculty);
    }

    @Test
    void testCalculateFacultyToStudentRatio() {
        double ratio = department.calculateFacultyToStudentRatio();

        assertEquals(20.0, ratio);
    }

    @Test
    void testCalculateFacultyToStudentRatioLowRatio() {
        department.setNumberOfStudents(200);

        double ratio = department.calculateFacultyToStudentRatio();

        assertEquals(10.0, ratio);
    }

    @Test
    void testCalculateFacultyToStudentRatioZeroFaculty() {
        department.setNumberOfFaculty(0);

        double ratio = department.calculateFacultyToStudentRatio();

        assertEquals(-1.0, ratio);
    }

    @Test
    void testCalculateFacultyToStudentRatioNegativeStudents() {
        department.setNumberOfStudents(-10);

        double ratio = department.calculateFacultyToStudentRatio();

        assertEquals(-1.0, ratio);
    }

    @Test
    void testCalculateAverageClassSize() {
        double avgSize = department.calculateAverageClassSize(40);

        assertEquals(10.0, avgSize);
    }

    @Test
    void testCalculateAverageClassSizeLargeCourses() {
        double avgSize = department.calculateAverageClassSize(20);

        assertEquals(20.0, avgSize);
    }

    @Test
    void testCalculateAverageClassSizeZeroCourses() {
        double avgSize = department.calculateAverageClassSize(0);

        assertEquals(-1.0, avgSize);
    }

    @Test
    void testCalculateAverageClassSizeNegativeStudents() {
        department.setNumberOfStudents(-50);

        double avgSize = department.calculateAverageClassSize(10);

        assertEquals(-1.0, avgSize);
    }

    @Test
    void testDetermineDepartmentSizeSmall() {
        department.setNumberOfFaculty(8);
        department.setNumberOfStudents(80);

        String size = department.determineDepartmentSize();

        assertEquals("SMALL", size);
    }

    @Test
    void testDetermineDepartmentSizeMedium() {
        department.setNumberOfFaculty(25);
        department.setNumberOfStudents(350);

        String size = department.determineDepartmentSize();

        assertEquals("MEDIUM", size);
    }

    @Test
    void testDetermineDepartmentSizeLarge() {
        department.setNumberOfFaculty(50);
        department.setNumberOfStudents(800);

        String size = department.determineDepartmentSize();

        assertEquals("LARGE", size);
    }

    @Test
    void testDetermineDepartmentSizeVeryLarge() {
        department.setNumberOfFaculty(70);
        department.setNumberOfStudents(1200);

        String size = department.determineDepartmentSize();

        assertEquals("VERY_LARGE", size);
    }

    @Test
    void testDetermineDepartmentSizeInvalid() {
        department.setNumberOfFaculty(-5);

        String size = department.determineDepartmentSize();

        assertEquals("INVALID", size);
    }

    @Test
    void testEvaluateDepartmentPerformanceExcellent() {
        String performance = department.evaluateDepartmentPerformance(90.0, 88.0, 85.0);

        assertEquals("EXCELLENT", performance);
    }

    @Test
    void testEvaluateDepartmentPerformanceGood() {
        String performance = department.evaluateDepartmentPerformance(75.0, 72.0, 70.0);

        assertEquals("GOOD", performance);
    }

    @Test
    void testEvaluateDepartmentPerformanceSatisfactory() {
        String performance = department.evaluateDepartmentPerformance(60.0, 58.0, 55.0);

        assertEquals("SATISFACTORY", performance);
    }

    @Test
    void testEvaluateDepartmentPerformanceNeedsImprovement() {
        String performance = department.evaluateDepartmentPerformance(45.0, 50.0, 48.0);

        assertEquals("NEEDS_IMPROVEMENT", performance);
    }

    @Test
    void testEvaluateDepartmentPerformanceInvalid() {
        String performance = department.evaluateDepartmentPerformance(-10.0, 88.0, 85.0);

        assertEquals("INVALID", performance);
    }

    @Test
    void testEvaluateDepartmentPerformanceOverHundred() {
        String performance = department.evaluateDepartmentPerformance(105.0, 88.0, 85.0);

        assertEquals("INVALID", performance);
    }

    @Test
    void testAllocateBudgetNormal() {
        double allocated = department.allocateBudget(1000000.0, 10, false);

        assertEquals(100000.0, allocated);
    }

    @Test
    void testAllocateBudgetPriorityLargeEnrollment() {
        department.setNumberOfStudents(600);

        double allocated = department.allocateBudget(1000000.0, 10, true);

        assertEquals(125000.0, allocated);
    }

    @Test
    void testAllocateBudgetPrioritySmallEnrollment() {
        department.setNumberOfStudents(300);

        double allocated = department.allocateBudget(1000000.0, 10, true);

        assertEquals(115000.0, allocated);
    }

    @Test
    void testAllocateBudgetNegativeBudget() {
        double allocated = department.allocateBudget(-50000.0, 10, false);

        assertEquals(-1.0, allocated);
    }

    @Test
    void testAllocateBudgetZeroDepartments() {
        double allocated = department.allocateBudget(1000000.0, 0, false);

        assertEquals(-1.0, allocated);
    }

    @Test
    void testCalculateResearchFundingPerFaculty() {
        department.setResearchFunding(200000.0);

        double perFaculty = department.calculateResearchFundingPerFaculty();

        assertEquals(10000.0, perFaculty);
    }

    @Test
    void testCalculateResearchFundingPerFacultyZeroFaculty() {
        department.setNumberOfFaculty(0);
        department.setResearchFunding(200000.0);

        double perFaculty = department.calculateResearchFundingPerFaculty();

        assertEquals(-1.0, perFaculty);
    }

    @Test
    void testCalculateResearchFundingPerFacultyNegativeFunding() {
        department.setResearchFunding(-5000.0);

        double perFaculty = department.calculateResearchFundingPerFaculty();

        assertEquals(-1.0, perFaculty);
    }

    @Test
    void testCalculateRequiredFaculty() {
        int required = department.calculateRequiredFaculty(600, 30);

        assertEquals(20, required);
    }

    @Test
    void testCalculateRequiredFacultyRoundsUp() {
        int required = department.calculateRequiredFaculty(610, 30);

        assertEquals(21, required);
    }

    @Test
    void testCalculateRequiredFacultyInvalidStudents() {
        int required = department.calculateRequiredFaculty(-50, 30);

        assertEquals(-1, required);
    }

    @Test
    void testCalculateRequiredFacultyZeroRatio() {
        int required = department.calculateRequiredFaculty(600, 0);

        assertEquals(-1, required);
    }

    @Test
    void testNeedsMoreFacultyTrue() {
        boolean needs = department.needsMoreFaculty(15, 600, 25);

        assertTrue(needs);
    }

    @Test
    void testNeedsMoreFacultyFalse() {
        boolean needs = department.needsMoreFaculty(25, 600, 25);

        assertFalse(needs);
    }

    @Test
    void testNeedsMoreFacultyExactRatio() {
        boolean needs = department.needsMoreFaculty(20, 600, 30);

        assertFalse(needs);
    }

    @Test
    void testNeedsMoreFacultyInvalidInput() {
        boolean needs = department.needsMoreFaculty(0, 600, 25);

        assertFalse(needs);
    }

    @Test
    void testGettersAndSetters() {
        department.setDepartmentName("Mathematics");
        department.setHeadOfDepartment("Dr. Johnson");
        department.setNumberOfFaculty(25);
        department.setNumberOfStudents(500);
        department.setBudget(750000.0);
        department.setNumberOfPrograms(5);
        department.setResearchFunding(150000.0);

        assertEquals("D001", department.getDepartmentId());
        assertEquals("Mathematics", department.getDepartmentName());
        assertEquals("Dr. Johnson", department.getHeadOfDepartment());
        assertEquals(25, department.getNumberOfFaculty());
        assertEquals(500, department.getNumberOfStudents());
        assertEquals(750000.0, department.getBudget());
        assertEquals(5, department.getNumberOfPrograms());
        assertEquals(150000.0, department.getResearchFunding());
        assertNotNull(department.getCourseIds());
    }
}