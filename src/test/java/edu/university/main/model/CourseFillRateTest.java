package edu.university.main.model;

import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseFillRateTest {

    Course course = new Course("C001", "Test Course", "Pr.Adam", 3, 100, "CS");


    @Test
    public void testInvalidCapacity() {
        course.setCurrentEnrollment(10);
        course.setMaxCapacity(0);
        assertEquals(-1.0, course.calculateFillRate(), 0.01);
    }

    @Test
    public void testMinCapacityEmpty() {
        course.setCurrentEnrollment(0);
        course.setMaxCapacity(1);
        assertEquals(0.0, course.calculateFillRate(), 0.01);
    }

    @Test
    public void testMinCapacityFull() {
        course.setCurrentEnrollment(1);
        course.setMaxCapacity(1);
        assertEquals(100.0, course.calculateFillRate(), 0.01);
    }

    @Test
    public void testHalfFull() {
        course.setCurrentEnrollment(5);
        course.setMaxCapacity(10);
        assertEquals(50.0, course.calculateFillRate(), 0.01);
    }

    @Test
    public void testJustBelowFull() {
        course.setCurrentEnrollment(9);
        course.setMaxCapacity(10);
        assertEquals(90.0, course.calculateFillRate(), 0.01);
    }

    @Test
    public void testFull() {
        course.setCurrentEnrollment(10);
        course.setMaxCapacity(10);
        assertEquals(100.0, course.calculateFillRate(), 0.01);
    }

}