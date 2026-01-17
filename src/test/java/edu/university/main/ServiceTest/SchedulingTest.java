package edu.university.main.ServiceTest;

import edu.university.main.service.SchedulingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchedulingTest {

    private SchedulingService service;

    @BeforeEach
    void setUp() {
        service = new SchedulingService();
    }

    @Test
    void testHasTimeConflictTrue() {
        boolean conflict = service.hasTimeConflict("10:00", "11:00", "10:30", "11:30");

        assertTrue(conflict);
    }

    @Test
    void testHasTimeConflictFalse() {
        boolean conflict = service.hasTimeConflict("10:00", "11:00", "11:30", "12:30");

        assertFalse(conflict);
    }

    @Test
    void testHasTimeConflictExactOverlap() {
        boolean conflict = service.hasTimeConflict("10:00", "11:00", "10:00", "11:00");

        assertTrue(conflict);
    }

    @Test
    void testHasTimeConflictBoundaryTouch() {
        boolean conflict = service.hasTimeConflict("10:00", "11:00", "11:00", "12:00");

        assertFalse(conflict);
    }

    @Test
    void testHasTimeConflictCompletelyContained() {
        boolean conflict = service.hasTimeConflict("10:00", "12:00", "10:30", "11:30");

        assertTrue(conflict);
    }

    @Test
    void testHasTimeConflictNullTime() {
        boolean conflict = service.hasTimeConflict(null, "11:00", "10:00", "11:00");

        assertFalse(conflict);
    }

    @Test
    void testHasTimeConflictInvalidFormat() {
        boolean conflict = service.hasTimeConflict("invalid", "11:00", "10:00", "11:00");

        assertFalse(conflict);
    }

    @Test
    void testCalculateRoomUtilization() {
        int utilization = service.calculateRoomUtilization(30, 40);

        assertEquals(75, utilization);
    }

    @Test
    void testCalculateRoomUtilizationFullUse() {
        int utilization = service.calculateRoomUtilization(40, 40);

        assertEquals(100, utilization);
    }

    @Test
    void testCalculateRoomUtilizationNoUse() {
        int utilization = service.calculateRoomUtilization(0, 40);

        assertEquals(0, utilization);
    }

    @Test
    void testCalculateRoomUtilizationNegativeHours() {
        int utilization = service.calculateRoomUtilization(-5, 40);

        assertEquals(-1, utilization);
    }

    @Test
    void testCalculateRoomUtilizationZeroTotal() {
        int utilization = service.calculateRoomUtilization(10, 0);

        assertEquals(-1, utilization);
    }

    @Test
    void testCalculateRoomUtilizationExceedsTotal() {
        int utilization = service.calculateRoomUtilization(50, 40);

        assertEquals(-1, utilization);
    }

    @Test
    void testCalculateOptimalSections() {
        int sections = service.calculateOptimalSections(100, 30);

        assertEquals(4, sections);
    }

    @Test
    void testCalculateOptimalSectionsExactDivision() {
        int sections = service.calculateOptimalSections(90, 30);

        assertEquals(3, sections);
    }

    @Test
    void testCalculateOptimalSectionsOneStudent() {
        int sections = service.calculateOptimalSections(1, 30);

        assertEquals(1, sections);
    }

    @Test
    void testCalculateOptimalSectionsNegativeStudents() {
        int sections = service.calculateOptimalSections(-10, 30);

        assertEquals(-1, sections);
    }

    @Test
    void testCalculateOptimalSectionsZeroCapacity() {
        int sections = service.calculateOptimalSections(100, 0);

        assertEquals(-1, sections);
    }

    @Test
    void testCanScheduleCourseTrue() {
        boolean canSchedule = service.canScheduleCourse(5, 3, 10, 5);

        assertTrue(canSchedule);
    }

    @Test
    void testCanScheduleCourseExactMatch() {
        boolean canSchedule = service.canScheduleCourse(3, 3, 5, 5);

        assertTrue(canSchedule);
    }

    @Test
    void testCanScheduleCourseInsufficientRooms() {
        boolean canSchedule = service.canScheduleCourse(2, 3, 10, 5);

        assertFalse(canSchedule);
    }

    @Test
    void testCanScheduleCourseInsufficientInstructors() {
        boolean canSchedule = service.canScheduleCourse(5, 3, 3, 5);

        assertFalse(canSchedule);
    }

    @Test
    void testCanScheduleCourseNegativeValues() {
        boolean canSchedule = service.canScheduleCourse(-1, 3, 10, 5);

        assertFalse(canSchedule);
    }

    @Test
    void testCalculateScheduleEfficiency() {
        double efficiency = service.calculateScheduleEfficiency(14, 20, 20);

        assertEquals(70.0, efficiency);
    }

    @Test
    void testCalculateScheduleEfficiencyHighFulfillmentLowUtilization() {
        double efficiency = service.calculateScheduleEfficiency(18, 20, 40);

        assertEquals(76.5, efficiency);
    }

    @Test
    void testCalculateScheduleEfficiencyPerfect() {
        double efficiency = service.calculateScheduleEfficiency(20, 20, 20);

        assertEquals(100.0, efficiency);
    }

    @Test
    void testCalculateScheduleEfficiencyNegativeScheduled() {
        double efficiency = service.calculateScheduleEfficiency(-5, 20, 20);

        assertEquals(-1.0, efficiency);
    }

    @Test
    void testCalculateScheduleEfficiencyZeroRequested() {
        double efficiency = service.calculateScheduleEfficiency(10, 0, 20);

        assertEquals(-1.0, efficiency);
    }

    @Test
    void testCalculateScheduleEfficiencyZeroSlots() {
        double efficiency = service.calculateScheduleEfficiency(10, 20, 0);

        assertEquals(-1.0, efficiency);
    }
}