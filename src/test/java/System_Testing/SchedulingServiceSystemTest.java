package System_Testing;


import edu.university.main.service.SchedulingService;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FR-SS: Scheduling Service System Tests")
public class SchedulingServiceSystemTest {

    private SchedulingService schedulingService;

    @BeforeEach
    void setUp() {
        schedulingService = new SchedulingService();
    }

    @Test
    @DisplayName("FR-SS-001: Time Conflict - Has Conflict")
    void testTimeConflict_HasConflict() {
        boolean conflict = schedulingService.hasTimeConflict("09:00", "10:30", "10:00", "11:30");
        assertTrue(conflict, "9:00-10:30 conflicts with 10:00-11:30");
    }

    @Test
    @DisplayName("FR-SS-001: Time Conflict - No Conflict")
    void testTimeConflict_NoConflict() {
        boolean conflict = schedulingService.hasTimeConflict("09:00", "10:30", "11:00", "12:30");
        assertFalse(conflict, "9:00-10:30 does not conflict with 11:00-12:30");
    }

    @Test
    @DisplayName("FR-SS-001: Time Conflict - Back to Back")
    void testTimeConflict_BackToBack() {
        boolean conflict = schedulingService.hasTimeConflict("09:00", "10:00", "10:00", "11:00");
        assertFalse(conflict, "Back-to-back classes don't conflict");
    }

    @Test
    @DisplayName("FR-SS-002: Room Utilization")
    void testRoomUtilization() {
        int utilization = schedulingService.calculateRoomUtilization(30, 40);
        assertEquals(75, utilization, "30/40 = 75%");
    }

    @Test
    @DisplayName("FR-SS-003: Optimal Sections")
    void testOptimalSections() {
        int sections = schedulingService.calculateOptimalSections(125, 30);
        assertEquals(5, sections, "ceil(125/30) = 5 sections");
    }

    @Test
    @DisplayName("FR-SS-003: Optimal Sections - Exact Division")
    void testOptimalSections_ExactDivision() {
        int sections = schedulingService.calculateOptimalSections(120, 30);
        assertEquals(4, sections, "120/30 = 4 sections");
    }

    @Test
    @DisplayName("FR-SS-004: Schedule Feasibility - Feasible")
    void testScheduleFeasibility_Feasible() {
        boolean feasible = schedulingService.canScheduleCourse(5, 3, 10, 4);
        assertTrue(feasible, "Enough rooms and instructors");
    }

    @Test
    @DisplayName("FR-SS-004: Schedule Feasibility - Not Enough Rooms")
    void testScheduleFeasibility_NotEnoughRooms() {
        boolean feasible = schedulingService.canScheduleCourse(2, 3, 10, 4);
        assertFalse(feasible, "Not enough rooms");
    }

    @Test
    @DisplayName("FR-SS-004: Schedule Feasibility - Not Enough Instructors")
    void testScheduleFeasibility_NotEnoughInstructors() {
        boolean feasible = schedulingService.canScheduleCourse(5, 3, 3, 4);
        assertFalse(feasible, "Not enough instructors");
    }

    @Test
    @DisplayName("FR-SS-005: Schedule Efficiency")
    void testScheduleEfficiency() {
        double efficiency = schedulingService.calculateScheduleEfficiency(45, 50, 60);
        double expected = (90.0 * 0.7) + (75.0 * 0.3);
        assertEquals(expected, efficiency, 0.01, "Weighted efficiency score");
    }
}





