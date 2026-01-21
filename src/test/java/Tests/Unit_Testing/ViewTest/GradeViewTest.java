package Tests.Unit_Testing.ViewTest;

import edu.university.main.view.GradeView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class GradeViewTest {
    private GradeView gradeView;

    @BeforeEach
    void setUp() {
        gradeView = new GradeView();
    }

    @Test
    @DisplayName("Should initialize all text field components")
    void testTextFieldInitialization() {
        assertNotNull(gradeView.getScoreField(), "Score field should not be null");
        assertNotNull(gradeView.getGpaField(), "GPA field should not be null");
        assertNotNull(gradeView.getCreditsField(), "Credits field should not be null");
    }

    @Test
    @DisplayName("Should initialize all checkbox components")
    void testCheckboxInitialization() {
        assertNotNull(gradeView.getAttendanceBox(), "Attendance checkbox should not be null");
        assertNotNull(gradeView.getParticipationBox(), "Participation checkbox should not be null");

        assertFalse(gradeView.getAttendanceBox().isSelected(), "Attendance box should be initially unchecked");
        assertFalse(gradeView.getParticipationBox().isSelected(), "Participation box should be initially unchecked");
    }

    @Test
    @DisplayName("Should initialize all button components")
    void testButtonInitialization() {
        assertNotNull(gradeView.getCalculateButton(), "Calculate button should not be null");
        assertNotNull(gradeView.getCheckHonorsButton(), "Check honors button should not be null");

        assertEquals("Calculate Final Score", gradeView.getCalculateButton().getText());
        assertEquals("Check Honors", gradeView.getCheckHonorsButton().getText());
    }

    @Test
    @DisplayName("Should display average correctly")
    void testDisplayAverage() {
        assertDoesNotThrow(() -> gradeView.displayAverage(85.5));
        assertDoesNotThrow(() -> gradeView.displayAverage(0.0));
        assertDoesNotThrow(() -> gradeView.displayAverage(100.0));
    }

    @Test
    @DisplayName("Should display final score with correct formatting")
    void testDisplayFinalScore() {
        assertDoesNotThrow(() -> gradeView.displayFinalScore(92.5));
        assertDoesNotThrow(() -> gradeView.displayFinalScore(75.0));
        assertDoesNotThrow(() -> gradeView.displayFinalScore(65.0));
    }

    @Test
    @DisplayName("Should display final score with correct color coding")
    void testDisplayFinalScoreColorCoding() throws NoSuchFieldException, IllegalAccessException {
        Field scoreLabelField = GradeView.class.getDeclaredField("scoreLabel");
        scoreLabelField.setAccessible(true);
        JLabel scoreLabel = (JLabel) scoreLabelField.get(gradeView);

        // High score (>= 90) should be green
        gradeView.displayFinalScore(92.0);
        assertEquals(new Color(0, 128, 0), scoreLabel.getForeground());

        // Medium score (70-89) should be orange
        gradeView.displayFinalScore(75.0);
        assertEquals(Color.ORANGE, scoreLabel.getForeground());

        // Low score (< 70) should be red
        gradeView.displayFinalScore(65.0);
        assertEquals(Color.RED, scoreLabel.getForeground());
    }

    @Test
    @DisplayName("Should display curved grade")
    void testDisplayCurvedGrade() {
        assertDoesNotThrow(() -> gradeView.displayCurvedGrade(88.5));
        assertDoesNotThrow(() -> gradeView.displayCurvedGrade(0.0));
    }

    @Test
    @DisplayName("Should display honors eligibility correctly")
    void testDisplayHonorsEligibility() throws NoSuchFieldException, IllegalAccessException {
        Field eligibilityLabelField = GradeView.class.getDeclaredField("eligibilityLabel");
        eligibilityLabelField.setAccessible(true);
        JLabel eligibilityLabel = (JLabel) eligibilityLabelField.get(gradeView);

        // Eligible should be green and show "Yes"
        gradeView.displayHonorsEligibility(true);
        assertEquals("Honors Eligible: Yes", eligibilityLabel.getText());
        assertEquals(new Color(0, 128, 0), eligibilityLabel.getForeground());

        // Not eligible should be red and show "No"
        gradeView.displayHonorsEligibility(false);
        assertEquals("Honors Eligible: No", eligibilityLabel.getText());
        assertEquals(Color.RED, eligibilityLabel.getForeground());
    }

    @Test
    @DisplayName("Should display honors level correctly")
    void testDisplayHonorsLevel() {
        assertDoesNotThrow(() -> gradeView.displayHonorsLevel("SUMMA_CUM_LAUDE"));
        assertDoesNotThrow(() -> gradeView.displayHonorsLevel("MAGNA_CUM_LAUDE"));
        assertDoesNotThrow(() -> gradeView.displayHonorsLevel("CUM_LAUDE"));
    }

    @Test
    @DisplayName("Should replace underscores in honors level text")
    void testHonorsLevelUnderscoreReplacement() throws NoSuchFieldException, IllegalAccessException {
        Field honorsLabelField = GradeView.class.getDeclaredField("honorsLabel");
        honorsLabelField.setAccessible(true);
        JLabel honorsLabel = (JLabel) honorsLabelField.get(gradeView);

        gradeView.displayHonorsLevel("SUMMA_CUM_LAUDE");
        assertTrue(honorsLabel.getText().contains("With High Honors"));
    }

    @Test
    @DisplayName("Should display messages in activity log")
    void testDisplayMessage() {
        String testMessage = "Test grade message";
        assertDoesNotThrow(() -> gradeView.displayMessage(testMessage));
    }

    @Test
    @DisplayName("Should clear messages from activity log")
    void testClearMessages() throws NoSuchFieldException, IllegalAccessException {
        Field messageAreaField = GradeView.class.getDeclaredField("messageArea");
        messageAreaField.setAccessible(true);
        JTextArea messageArea = (JTextArea) messageAreaField.get(gradeView);

        gradeView.displayMessage("Test message 1");
        gradeView.displayMessage("Test message 2");
        assertTrue(messageArea.getText().length() > 0);

        gradeView.clearMessages();
        assertEquals("", messageArea.getText());
    }

    @Test
    @DisplayName("Should have correct layout manager")
    void testLayoutManager() {
        assertTrue(gradeView.getLayout() instanceof BorderLayout);
    }

    @Test
    @DisplayName("All text fields should be initially empty")
    void testInitialTextFieldValues() {
        assertEquals("", gradeView.getScoreField().getText());
        assertEquals("", gradeView.getGpaField().getText());
        assertEquals("", gradeView.getCreditsField().getText());
    }

    @Test
    @DisplayName("Buttons should have correct background colors")
    void testButtonColors() {
        assertEquals(new Color(70, 130, 180), gradeView.getCalculateButton().getBackground());
        assertEquals(Color.WHITE, gradeView.getCalculateButton().getForeground());

        assertEquals(new Color(60, 179, 113), gradeView.getCheckHonorsButton().getBackground());
        assertEquals(Color.WHITE, gradeView.getCheckHonorsButton().getForeground());
    }

    @Test
    @DisplayName("Should handle negative score values")
    void testNegativeScores() {
        assertDoesNotThrow(() -> gradeView.displayFinalScore(-10.0));
        assertDoesNotThrow(() -> gradeView.displayAverage(-5.0));
    }

    @Test
    @DisplayName("Should handle scores over 100")
    void testScoresOver100() {
        assertDoesNotThrow(() -> gradeView.displayFinalScore(150.0));
        assertDoesNotThrow(() -> gradeView.displayAverage(110.0));
    }

    @Test
    @DisplayName("Should handle empty honors level string")
    void testEmptyHonorsLevel() {
        assertDoesNotThrow(() -> gradeView.displayHonorsLevel(""));
    }

    @Test
    @DisplayName("Should handle extreme curved grade values")
    void testExtremeCurvedGrades() {
        assertDoesNotThrow(() -> gradeView.displayCurvedGrade(Double.MAX_VALUE));
        assertDoesNotThrow(() -> gradeView.displayCurvedGrade(Double.MIN_VALUE));
    }

    @Test
    @DisplayName("Checkboxes should be toggleable")
    void testCheckboxToggle() {
        JCheckBox attendanceBox = gradeView.getAttendanceBox();
        JCheckBox participationBox = gradeView.getParticipationBox();

        assertFalse(attendanceBox.isSelected());
        attendanceBox.setSelected(true);
        assertTrue(attendanceBox.isSelected());

        assertFalse(participationBox.isSelected());
        participationBox.setSelected(true);
        assertTrue(participationBox.isSelected());
    }
}