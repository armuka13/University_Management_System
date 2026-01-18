package System_Testing1;


import edu.university.main.model.Department;
import edu.university.main.repository.DepartmentRepository;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FR-DM: Department Management System Tests")
public class DepartmentManagementSystemTest {

    private Department department;
    private DepartmentRepository repository;

    @BeforeEach
    void setUp() {
        department = new Department("CS", "Computer Science", "Dr. Anderson", 2000000.0);
        department.setNumberOfFaculty(40);
        department.setNumberOfStudents(600);
        repository = new DepartmentRepository();
    }

    @Test
    @DisplayName("FR-DM-001: Budget per Student")
    void testBudgetPerStudent() {
        double perStudent = department.calculateBudgetPerStudent();
        assertEquals(3333.33, perStudent, 0.01, "2000000/600");
    }

    @Test
    @DisplayName("FR-DM-002: Budget per Faculty")
    void testBudgetPerFaculty() {
        double perFaculty = department.calculateBudgetPerFaculty();
        assertEquals(50000.0, perFaculty, 0.01, "2000000/40");
    }

    @Test
    @DisplayName("FR-DM-003: Faculty-Student Ratio")
    void testFacultyStudentRatio() {
        double ratio = department.calculateFacultyToStudentRatio();
        assertEquals(15.0, ratio, 0.01, "600/40 = 15:1 ratio");
    }

    @Test
    @DisplayName("FR-DM-004: Average Class Size")
    void testAverageClassSize() {
        double avgSize = department.calculateAverageClassSize(40);
        assertEquals(15.0, avgSize, 0.01, "600/40 = 15 students per class");
    }

    @Test
    @DisplayName("FR-DM-005: Department Size - Small")
    void testDepartmentSize_Small() {
        Department small = new Department("PHIL", "Philosophy", "Dr. Lee", 500000.0);
        small.setNumberOfFaculty(8);
        small.setNumberOfStudents(80);
        assertEquals("SMALL", small.determineDepartmentSize());
    }

    @Test
    @DisplayName("FR-DM-005: Department Size - Medium")
    void testDepartmentSize_Medium() {
        Department medium = new Department("MATH", "Mathematics", "Dr. Chen", 1200000.0);
        medium.setNumberOfFaculty(25);
        medium.setNumberOfStudents(400);
        assertEquals("MEDIUM", medium.determineDepartmentSize());
    }

    @Test
    @DisplayName("FR-DM-005: Department Size - Large")
    void testDepartmentSize_Large() {
        assertEquals("LARGE", department.determineDepartmentSize());
    }

    @Test
    @DisplayName("FR-DM-006: Department Performance - Excellent")
    void testDepartmentPerformance_Excellent() {
        String performance = department.evaluateDepartmentPerformance(90.0, 88.0, 85.0);
        assertEquals("EXCELLENT", performance);
    }

    @Test
    @DisplayName("FR-DM-006: Department Performance - Good")
    void testDepartmentPerformance_Good() {
        String performance = department.evaluateDepartmentPerformance(75.0, 72.0, 78.0);
        assertEquals("GOOD", performance);
    }

    @Test
    @DisplayName("FR-DM-006: Department Performance - Satisfactory")
    void testDepartmentPerformance_Satisfactory() {
        String performance = department.evaluateDepartmentPerformance(60.0, 58.0, 62.0);
        assertEquals("SATISFACTORY", performance);
    }

    @Test
    @DisplayName("FR-DM-007: Budget Allocation - Priority with >500 students")
    void testBudgetAllocation_PriorityLarge() {
        double allocated = department.allocateBudget(10000000.0, 10, true);
        assertEquals(1250000.0, allocated, 0.01, "Base * 1.25 for priority large");
    }

    @Test
    @DisplayName("FR-DM-007: Budget Allocation - Priority Small")
    void testBudgetAllocation_PrioritySmall() {
        Department small = new Department("PHIL", "Philosophy", "Dr. Lee", 500000.0);
        small.setNumberOfStudents(400);
        double allocated = small.allocateBudget(10000000.0, 10, true);
        assertEquals(1150000.0, allocated, 0.01, "Base * 1.15 for priority small");
    }

    @Test
    @DisplayName("FR-DM-007: Budget Allocation - Not Priority")
    void testBudgetAllocation_NotPriority() {
        double allocated = department.allocateBudget(10000000.0, 10, false);
        assertEquals(1000000.0, allocated, 0.01, "Base allocation only");
    }

    @Test
    @DisplayName("FR-DM-008: Research Funding per Faculty")
    void testResearchFundingPerFaculty() {
        department.setResearchFunding(800000.0);
        double perFaculty = department.calculateResearchFundingPerFaculty();
        assertEquals(20000.0, perFaculty, 0.01, "800000/40");
    }

    @Test
    @DisplayName("FR-DM-009: Required Faculty")
    void testRequiredFaculty() {
        int required = department.calculateRequiredFaculty(800, 20);
        assertEquals(40, required, "ceil(800/20) = 40");
    }

    @Test
    @DisplayName("FR-DM-010: Faculty Shortage - Needs More")
    void testFacultyShortage_NeedsMore() {
        boolean needsMore = department.needsMoreFaculty(35, 700, 18);
        assertTrue(needsMore, "700/35 = 20 > 18, needs more faculty");
    }

    @Test
    @DisplayName("FR-DM-010: Faculty Shortage - Sufficient")
    void testFacultyShortage_Sufficient() {
        boolean needsMore = department.needsMoreFaculty(40, 600, 20);
        assertFalse(needsMore, "600/40 = 15 <= 20, sufficient");
    }
}