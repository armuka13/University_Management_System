package edu.university.main.ViewTest;

import edu.university.main.view.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MainFrameTest {
    private MainFrame mainFrame;

    @BeforeEach
    void setUp() {
        mainFrame = new MainFrame();
    }

    @Test
    @DisplayName("Should initialize with correct title")
    void testFrameTitle() {
        assertEquals("University Management System - Advanced Edition", mainFrame.getTitle());
    }

    @Test
    @DisplayName("Should have correct frame size")
    void testFrameSize() {
        assertEquals(1000, mainFrame.getWidth());
        assertEquals(700, mainFrame.getHeight());
    }

    @Test
    @DisplayName("Should have correct default close operation")
    void testCloseOperation() {
        assertEquals(JFrame.EXIT_ON_CLOSE, mainFrame.getDefaultCloseOperation());
    }

    @Test
    @DisplayName("Should initialize all view components")
    void testViewInitialization() {
        assertNotNull(mainFrame.getStudentView(), "Student view should not be null");
        assertNotNull(mainFrame.getCourseView(), "Course view should not be null");
        assertNotNull(mainFrame.getGradeView(), "Grade view should not be null");
        assertNotNull(mainFrame.getFinancialView(), "Financial view should not be null");
    }

    @Test
    @DisplayName("Should initialize status bar")
    void testStatusBarInitialization() {
        assertNotNull(mainFrame.getStatusBar(), "Status bar should not be null");
        assertEquals(" Ready", mainFrame.getStatusBar().getText());
    }

    @Test
    @DisplayName("Should update status bar text correctly")
    void testUpdateStatus() {
        String testMessage = "Processing...";
        mainFrame.updateStatus(testMessage);
        assertEquals(" " + testMessage, mainFrame.getStatusBar().getText());
    }

    @Test
    @DisplayName("Should have menu bar")
    void testMenuBarExists() {
        assertNotNull(mainFrame.getJMenuBar(), "Menu bar should not be null");
    }

    @Test
    @DisplayName("Should have File menu")
    void testFileMenuExists() {
        JMenuBar menuBar = mainFrame.getJMenuBar();
        boolean hasFileMenu = false;

        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            if ("File".equals(menuBar.getMenu(i).getText())) {
                hasFileMenu = true;
                break;
            }
        }

        assertTrue(hasFileMenu, "File menu should exist");
    }

    @Test
    @DisplayName("Should have Help menu")
    void testHelpMenuExists() {
        JMenuBar menuBar = mainFrame.getJMenuBar();
        boolean hasHelpMenu = false;

        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            if ("Help".equals(menuBar.getMenu(i).getText())) {
                hasHelpMenu = true;
                break;
            }
        }

        assertTrue(hasHelpMenu, "Help menu should exist");
    }

    @Test
    @DisplayName("Should have tabbed pane with 4 tabs")
    void testTabbedPaneTabCount() {
        Container contentPane = mainFrame.getContentPane();
        Component[] components = contentPane.getComponents();

        JTabbedPane tabbedPane = null;
        for (Component comp : components) {
            if (comp instanceof JTabbedPane) {
                tabbedPane = (JTabbedPane) comp;
                break;
            }
        }

        assertNotNull(tabbedPane, "Tabbed pane should exist");
        assertEquals(4, tabbedPane.getTabCount(), "Should have 4 tabs");
    }

    @Test
    @DisplayName("Should have Students tab")
    void testStudentsTab() {
        Container contentPane = mainFrame.getContentPane();
        JTabbedPane tabbedPane = findTabbedPane(contentPane);

        assertNotNull(tabbedPane);
        assertEquals("Students", tabbedPane.getTitleAt(0));
    }

    @Test
    @DisplayName("Should have Courses tab")
    void testCoursesTab() {
        Container contentPane = mainFrame.getContentPane();
        JTabbedPane tabbedPane = findTabbedPane(contentPane);

        assertNotNull(tabbedPane);
        assertEquals("Courses", tabbedPane.getTitleAt(1));
    }

    @Test
    @DisplayName("Should have Grades tab")
    void testGradesTab() {
        Container contentPane = mainFrame.getContentPane();
        JTabbedPane tabbedPane = findTabbedPane(contentPane);

        assertNotNull(tabbedPane);
        assertEquals("Grades", tabbedPane.getTitleAt(2));
    }

    @Test
    @DisplayName("Should have Financial tab")
    void testFinancialTab() {
        Container contentPane = mainFrame.getContentPane();
        JTabbedPane tabbedPane = findTabbedPane(contentPane);

        assertNotNull(tabbedPane);
        assertEquals("Financial", tabbedPane.getTitleAt(3));
    }

    @Test
    @DisplayName("Student view should be on first tab")
    void testStudentViewOnFirstTab() {
        Container contentPane = mainFrame.getContentPane();
        JTabbedPane tabbedPane = findTabbedPane(contentPane);

        assertNotNull(tabbedPane);
        assertTrue(tabbedPane.getComponentAt(0) instanceof StudentView);
    }

    @Test
    @DisplayName("Course view should be on second tab")
    void testCourseViewOnSecondTab() {
        Container contentPane = mainFrame.getContentPane();
        JTabbedPane tabbedPane = findTabbedPane(contentPane);

        assertNotNull(tabbedPane);
        assertTrue(tabbedPane.getComponentAt(1) instanceof CourseView);
    }

    @Test
    @DisplayName("Grade view should be on third tab")
    void testGradeViewOnThirdTab() {
        Container contentPane = mainFrame.getContentPane();
        JTabbedPane tabbedPane = findTabbedPane(contentPane);

        assertNotNull(tabbedPane);
        assertTrue(tabbedPane.getComponentAt(2) instanceof GradeView);
    }

    @Test
    @DisplayName("Financial view should be on fourth tab")
    void testFinancialViewOnFourthTab() {
        Container contentPane = mainFrame.getContentPane();
        JTabbedPane tabbedPane = findTabbedPane(contentPane);

        assertNotNull(tabbedPane);
        assertTrue(tabbedPane.getComponentAt(3) instanceof FinancialView);
    }

    @Test
    @DisplayName("Should use BorderLayout for content pane")
    void testContentPaneLayout() {
        assertTrue(mainFrame.getContentPane().getLayout() instanceof BorderLayout);
    }

    @Test
    @DisplayName("Status bar should have etched border")
    void testStatusBarBorder() {
        assertNotNull(mainFrame.getStatusBar().getBorder());
    }

    @Test
    @DisplayName("Should handle multiple status updates")
    void testMultipleStatusUpdates() {
        mainFrame.updateStatus("Message 1");
        assertEquals(" Message 1", mainFrame.getStatusBar().getText());

        mainFrame.updateStatus("Message 2");
        assertEquals(" Message 2", mainFrame.getStatusBar().getText());

        mainFrame.updateStatus("Message 3");
        assertEquals(" Message 3", mainFrame.getStatusBar().getText());
    }

    @Test
    @DisplayName("Should handle empty status message")
    void testEmptyStatusMessage() {
        mainFrame.updateStatus("");
        assertEquals(" ", mainFrame.getStatusBar().getText());
    }

    @Test
    @DisplayName("Should handle long status message")
    void testLongStatusMessage() {
        String longMessage = "This is a very long status message that should still be handled correctly";
        mainFrame.updateStatus(longMessage);
        assertEquals(" " + longMessage, mainFrame.getStatusBar().getText());
    }

    @Test
    @DisplayName("All views should be unique instances")
    void testViewsAreUniqueInstances() {
        assertNotSame(mainFrame.getStudentView(), mainFrame.getCourseView());
        assertNotSame(mainFrame.getStudentView(), mainFrame.getGradeView());
        assertNotSame(mainFrame.getStudentView(), mainFrame.getFinancialView());
        assertNotSame(mainFrame.getCourseView(), mainFrame.getGradeView());
        assertNotSame(mainFrame.getCourseView(), mainFrame.getFinancialView());
        assertNotSame(mainFrame.getGradeView(), mainFrame.getFinancialView());
    }

    // Helper method to find JTabbedPane
    private JTabbedPane findTabbedPane(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JTabbedPane) {
                return (JTabbedPane) comp;
            }
        }
        return null;
    }
}