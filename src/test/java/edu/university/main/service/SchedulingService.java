package edu.university.main.service;


public class SchedulingService {

    public boolean hasTimeConflict(String startTime1, String endTime1, String startTime2, String endTime2) {
        if (startTime1 == null || endTime1 == null || startTime2 == null || endTime2 == null) {
            return false;
        }

        int start1 = parseTime(startTime1);
        int end1 = parseTime(endTime1);
        int start2 = parseTime(startTime2);
        int end2 = parseTime(endTime2);

        if (start1 < 0 || end1 < 0 || start2 < 0 || end2 < 0) {
            return false;
        }

        return (start1 < end2 && start2 < end1);
    }

    private int parseTime(String time) {
        try {
            String[] parts = time.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            return hour * 60 + minute;
        } catch (Exception e) {
            return -1;
        }
    }

    public int calculateRoomUtilization(int hoursUsed, int totalAvailableHours) {
        if (hoursUsed < 0 || totalAvailableHours <= 0 || hoursUsed > totalAvailableHours) {
            return -1;
        }

        double utilization = ((double) hoursUsed / totalAvailableHours) * 100.0;
        return (int) Math.round(utilization);
    }

    public int calculateOptimalSections(int totalStudents, int maxCapacity) {
        if (totalStudents < 0 || maxCapacity <= 0) {
            return -1;
        }

        return (int) Math.ceil((double) totalStudents / maxCapacity);
    }

    public boolean canScheduleCourse(int availableRooms, int requiredRooms, int availableInstructors, int requiredInstructors) {
        if (availableRooms < 0 || requiredRooms < 0 || availableInstructors < 0 || requiredInstructors < 0) {
            return false;
        }

        return (availableRooms >= requiredRooms && availableInstructors >= requiredInstructors);
    }

    public double calculateScheduleEfficiency(int scheduledCourses, int requestedCourses, int availableSlots) {
        if (scheduledCourses < 0 || requestedCourses <= 0 || availableSlots <= 0) {
            return -1.0;
        }

        double requestFulfillment = ((double) scheduledCourses / requestedCourses) * 100.0;
        double slotUtilization = ((double) scheduledCourses / availableSlots) * 100.0;

        double efficiency = (requestFulfillment * 0.7) + (slotUtilization * 0.3);
        return Math.round(efficiency * 100.0) / 100.0;
    }
}
