package Tests.Unit_Testing.ViewTest;

import edu.university.main.view.CourseView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class CourseViewTest {
    private CourseView courseView;

    @BeforeEach
    void setUp() {
        courseView = new CourseView();
    }

    @Test
    @DisplayName("Should initialize all text field components")
    void testTextFieldInitialization() {
        assertNotNull(courseView.getCourseIdField(), "Course ID field should not be null");
        assertNotNull(courseView.getCourseNameField(), "Course name field should not be null");
        assertNotNull(courseView.getInstructorField(), "Instructor field should not be null");
        assertNotNull(courseView.getCreditsField(), "Credits field should not be null");
        assertNotNull(courseView.getCapacityField(), "Capacity field should not be null");
        assertNotNull(courseView.getDepartmentField(), "Department field should not be null");
        assertNotNull(courseView.getSearchCourseIdField(), "Search course ID field should not be null");
    }

    @Test
    @DisplayName("Should initialize all button components")
    void testButtonInitialization() {
        assertNotNull(courseView.getCreateButton(), "Create button should not be null");
        assertNotNull(courseView.getSearchButton(), "Search button should not be null");

        assertEquals("Create Course", courseView.getCreateButton().getText());
        assertEquals("Search", courseView.getSearchButton().getText());
    }

    @Test
    @DisplayName("Should display available seats with correct formatting")
    void testDisplayAvailableSeats() {
        assertDoesNotThrow(() -> courseView.displayAvailableSeats(15));
        assertDoesNotThrow(() -> courseView.displayAvailableSeats(5));
        assertDoesNotThrow(() -> courseView.displayAvailableSeats(0));
    }

    @Test
    @DisplayName("Should display available seats with correct color coding")
    void testDisplayAvailableSeatsColorCoding() throws NoSuchFieldException, IllegalAccessException {
        Field seatsLabelField = CourseView.class.getDeclaredField("seatsLabel");
        seatsLabelField.setAccessible(true);
        JLabel seatsLabel = (JLabel) seatsLabelField.get(courseView);

        // Many seats (> 10) should be green
        courseView.displayAvailableSeats(15);
        assertEquals(new Color(0, 128, 0), seatsLabel.getForeground());

        // Few seats (1-10) should be orange
        courseView.displayAvailableSeats(5);
        assertEquals(Color.ORANGE, seatsLabel.getForeground());

        // No seats (0) should be red
        courseView.displayAvailableSeats(0);
        assertEquals(Color.RED, seatsLabel.getForeground());
    }

    @Test
    @DisplayName("Should display fill rate with correct formatting")
    void testDisplayFillRate() {
        assertDoesNotThrow(() -> courseView.displayFillRate(95.5));
        assertDoesNotThrow(() -> courseView.displayFillRate(75.0));
        assertDoesNotThrow(() -> courseView.displayFillRate(50.0));
        assertDoesNotThrow(() -> courseView.displayFillRate(0.0));
    }

    @Test
    @DisplayName("Should display fill rate with correct color coding")
    void testDisplayFillRateColorCoding() throws NoSuchFieldException, IllegalAccessException {
        Field fillRateLabelField = CourseView.class.getDeclaredField("fillRateLabel");
        fillRateLabelField.setAccessible(true);
        JLabel fillRateLabel = (JLabel) fillRateLabelField.get(courseView);

        // High fill rate (>= 90) should be red
        courseView.displayFillRate(95.0);
        assertEquals(Color.RED, fillRateLabel.getForeground());

        // Medium fill rate (70-89) should be orange
        courseView.displayFillRate(75.0);
        assertEquals(Color.ORANGE, fillRateLabel.getForeground());

        // Low fill rate (< 70) should be green
        courseView.displayFillRate(50.0);
        assertEquals(new Color(0, 128, 0), fillRateLabel.getForeground());
    }

    @Test
    @DisplayName("Should display popularity correctly")
    void testDisplayPopularity() {
        assertDoesNotThrow(() -> courseView.displayPopularity("HIGH_DEMAND"));
        assertDoesNotThrow(() -> courseView.displayPopularity("MODERATE"));
        assertDoesNotThrow(() -> courseView.displayPopularity("LOW_INTEREST"));
    }

    @Test
    @DisplayName("Should replace underscores in popularity text")
    void testPopularityUnderscoreReplacement() throws NoSuchFieldException, IllegalAccessException {
        Field popularityLabelField = CourseView.class.getDeclaredField("popularityLabel");
        popularityLabelField.setAccessible(true);
        JLabel popularityLabel = (JLabel) popularityLabelField.get(courseView);

        courseView.displayPopularity("HIGH_DEMAND");
        assertTrue(popularityLabel.getText().contains("HIGH DEMAND"));
    }

    @Test
    @DisplayName("Should display messages in activity log")
    void testDisplayMessage() {
        String testMessage = "Test course message";
        assertDoesNotThrow(() -> courseView.displayMessage(testMessage));
    }

    @Test
    @DisplayName("Should clear messages from activity log")
    void testClearMessages() throws NoSuchFieldException, IllegalAccessException {
        Field messageAreaField = CourseView.class.getDeclaredField("messageArea");
        messageAreaField.setAccessible(true);
        JTextArea messageArea = (JTextArea) messageAreaField.get(courseView);

        courseView.displayMessage("Test message 1");
        courseView.displayMessage("Test message 2");
        assertTrue(messageArea.getText().length() > 0);

        courseView.clearMessages();
        assertEquals("", messageArea.getText());
    }

    @Test
    @DisplayName("Should have correct layout manager")
    void testLayoutManager() {
        assertTrue(courseView.getLayout() instanceof BorderLayout);
    }

    @Test
    @DisplayName("All text fields should be initially empty")
    void testInitialTextFieldValues() {
        assertEquals("", courseView.getCourseIdField().getText());
        assertEquals("", courseView.getCourseNameField().getText());
        assertEquals("", courseView.getInstructorField().getText());
        assertEquals("", courseView.getCreditsField().getText());
        assertEquals("", courseView.getCapacityField().getText());
        assertEquals("", courseView.getDepartmentField().getText());
        assertEquals("", courseView.getSearchCourseIdField().getText());
    }

    @Test
    @DisplayName("Buttons should have correct background colors")
    void testButtonColors() {
        assertEquals(new Color(70, 130, 180), courseView.getCreateButton().getBackground());
        assertEquals(Color.WHITE, courseView.getCreateButton().getForeground());

        assertEquals(new Color(60, 179, 113), courseView.getSearchButton().getBackground());
        assertEquals(Color.WHITE, courseView.getSearchButton().getForeground());
    }

    @Test
    @DisplayName("Should handle negative seat values")
    void testNegativeSeatValues() {
        assertDoesNotThrow(() -> courseView.displayAvailableSeats(-5));
    }

    @Test
    @DisplayName("Should handle fill rate over 100%")
    void testFillRateOver100() {
        assertDoesNotThrow(() -> courseView.displayFillRate(150.0));
    }

    @Test
    @DisplayName("Should handle negative fill rate")
    void testNegativeFillRate() {
        assertDoesNotThrow(() -> courseView.displayFillRate(-10.0));
    }

    @Test
    @DisplayName("Should handle empty popularity string")
    void testEmptyPopularity() {
        assertDoesNotThrow(() -> courseView.displayPopularity(""));
    }

    @Test
    @DisplayName("Should handle very large seat numbers")
    void testLargeSeatNumbers() {
        assertDoesNotThrow(() -> courseView.displayAvailableSeats(Integer.MAX_VALUE));
    }
}