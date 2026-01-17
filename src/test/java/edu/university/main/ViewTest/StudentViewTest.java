package edu.university.main.ViewTest;

import edu.university.main.view.StudentView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class StudentViewTest {
    private StudentView studentView;

    @BeforeEach
    void setUp() {
        studentView = new StudentView();
    }

    @Test
    @DisplayName("Should initialize all text field components")
    void testTextFieldInitialization() {
        assertNotNull(studentView.getStudentIdField(), "Student ID field should not be null");
        assertNotNull(studentView.getNameField(), "Name field should not be null");
        assertNotNull(studentView.getEmailField(), "Email field should not be null");
        assertNotNull(studentView.getAgeField(), "Age field should not be null");
        assertNotNull(studentView.getMajorField(), "Major field should not be null");
        assertNotNull(studentView.getEnrollStudentIdField(), "Enroll student ID field should not be null");
        assertNotNull(studentView.getEnrollCourseIdField(), "Enroll course ID field should not be null");
    }

    @Test
    @DisplayName("Should initialize all button components")
    void testButtonInitialization() {
        assertNotNull(studentView.getCreateButton(), "Create button should not be null");
        assertNotNull(studentView.getEnrollButton(), "Enroll button should not be null");

        assertEquals("Create Student", studentView.getCreateButton().getText());
        assertEquals("Enroll", studentView.getEnrollButton().getText());
    }

    @Test
    @DisplayName("Should display GPA correctly with proper formatting")
    void testDisplayGPA() {
        studentView.displayGPA(3.75);
        assertDoesNotThrow(() -> studentView.displayGPA(3.75));

        // Test edge cases
        assertDoesNotThrow(() -> studentView.displayGPA(0.0));
        assertDoesNotThrow(() -> studentView.displayGPA(4.0));
    }

    @Test
    @DisplayName("Should display GPA with correct color coding")
    void testDisplayGPAColorCoding() throws NoSuchFieldException, IllegalAccessException {
        Field gpaLabelField = StudentView.class.getDeclaredField("gpaLabel");
        gpaLabelField.setAccessible(true);
        JLabel gpaLabel = (JLabel) gpaLabelField.get(studentView);

        // High GPA (>= 3.5) should be green
        studentView.displayGPA(3.8);
        assertEquals(new Color(0, 128, 0), gpaLabel.getForeground());

        // Medium GPA (>= 2.0) should be black
        studentView.displayGPA(2.5);
        assertEquals(Color.BLACK, gpaLabel.getForeground());

        // Low GPA (< 2.0) should be red
        studentView.displayGPA(1.5);
        assertEquals(Color.RED, gpaLabel.getForeground());
    }

    @Test
    @DisplayName("Should display academic standing correctly")
    void testDisplayAcademicStanding() {
        assertDoesNotThrow(() -> studentView.displayAcademicStanding("GOOD_STANDING"));
        assertDoesNotThrow(() -> studentView.displayAcademicStanding("PROBATION"));
        assertDoesNotThrow(() -> studentView.displayAcademicStanding("ACADEMIC_WARNING"));
    }

    @Test
    @DisplayName("Should display tuition with proper currency formatting")
    void testDisplayTuition() {
        assertDoesNotThrow(() -> studentView.displayTuition(15000.50));
        assertDoesNotThrow(() -> studentView.displayTuition(0.0));
        assertDoesNotThrow(() -> studentView.displayTuition(99999.99));
    }

    @Test
    @DisplayName("Should display graduation status with correct colors")
    void testDisplayGraduationStatus() throws NoSuchFieldException, IllegalAccessException {
        Field graduationLabelField = StudentView.class.getDeclaredField("graduationLabel");
        graduationLabelField.setAccessible(true);
        JLabel graduationLabel = (JLabel) graduationLabelField.get(studentView);

        // Eligible should be green
        studentView.displayGraduationStatus(true);
        assertEquals("Graduation: ELIGIBLE", graduationLabel.getText());
        assertEquals(new Color(0, 128, 0), graduationLabel.getForeground());

        // Not eligible should be red
        studentView.displayGraduationStatus(false);
        assertEquals("Graduation: NOT ELIGIBLE", graduationLabel.getText());
        assertEquals(Color.RED, graduationLabel.getForeground());
    }

    @Test
    @DisplayName("Should display messages in activity log")
    void testDisplayMessage() {
        String testMessage = "Test message";
        assertDoesNotThrow(() -> studentView.displayMessage(testMessage));
    }

    @Test
    @DisplayName("Should clear messages from activity log")
    void testClearMessages() throws NoSuchFieldException, IllegalAccessException {
        Field messageAreaField = StudentView.class.getDeclaredField("messageArea");
        messageAreaField.setAccessible(true);
        JTextArea messageArea = (JTextArea) messageAreaField.get(studentView);

        studentView.displayMessage("Test message 1");
        studentView.displayMessage("Test message 2");
        assertTrue(messageArea.getText().length() > 0);

        studentView.clearMessages();
        assertEquals("", messageArea.getText());
    }

    @Test
    @DisplayName("Should have correct layout manager")
    void testLayoutManager() {
        assertTrue(studentView.getLayout() instanceof BorderLayout);
    }

    @Test
    @DisplayName("Should have proper border with padding")
    void testBorder() {
        assertNotNull(studentView.getBorder());
    }

    @Test
    @DisplayName("All text fields should be initially empty")
    void testInitialTextFieldValues() {
        assertEquals("", studentView.getStudentIdField().getText());
        assertEquals("", studentView.getNameField().getText());
        assertEquals("", studentView.getEmailField().getText());
        assertEquals("", studentView.getAgeField().getText());
        assertEquals("", studentView.getMajorField().getText());
        assertEquals("", studentView.getEnrollStudentIdField().getText());
        assertEquals("", studentView.getEnrollCourseIdField().getText());
    }

    @Test
    @DisplayName("Buttons should have correct background colors")
    void testButtonColors() {
        assertEquals(new Color(70, 130, 180), studentView.getCreateButton().getBackground());
        assertEquals(Color.WHITE, studentView.getCreateButton().getForeground());

        assertEquals(new Color(60, 179, 113), studentView.getEnrollButton().getBackground());
        assertEquals(Color.WHITE, studentView.getEnrollButton().getForeground());
    }

    @Test
    @DisplayName("Should handle null or empty strings gracefully")
    void testNullSafetyForStrings() {
        assertDoesNotThrow(() -> studentView.displayAcademicStanding(""));
        assertDoesNotThrow(() -> studentView.displayMessage(""));
    }

    @Test
    @DisplayName("Should handle extreme GPA values")
    void testExtremeGPAValues() {
        assertDoesNotThrow(() -> studentView.displayGPA(-1.0));
        assertDoesNotThrow(() -> studentView.displayGPA(10.0));
    }

    @Test
    @DisplayName("Should handle extreme tuition values")
    void testExtremeTuitionValues() {
        assertDoesNotThrow(() -> studentView.displayTuition(-1000.0));
        assertDoesNotThrow(() -> studentView.displayTuition(Double.MAX_VALUE));
    }
}