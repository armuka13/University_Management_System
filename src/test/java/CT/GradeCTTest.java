package CT;

import edu.university.main.model.Grade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeCTTest {

    Grade gradeHelper = new Grade("S1","C1",0,1,2026);

    @Test
    void statementCoverageTests() {

        assertEquals("INVALID", gradeHelper.calculateLetterGrade(-5));
        assertEquals("A", gradeHelper.calculateLetterGrade(95));
        assertEquals("A-", gradeHelper.calculateLetterGrade(91));
        assertEquals("B+", gradeHelper.calculateLetterGrade(88));
        assertEquals("B", gradeHelper.calculateLetterGrade(84));
        assertEquals("B-", gradeHelper.calculateLetterGrade(81));
        assertEquals("C+", gradeHelper.calculateLetterGrade(78));
        assertEquals("C", gradeHelper.calculateLetterGrade(74));
        assertEquals("C-", gradeHelper.calculateLetterGrade(71));
        assertEquals("D+", gradeHelper.calculateLetterGrade(68));
        assertEquals("D", gradeHelper.calculateLetterGrade(64));
        assertEquals("D-", gradeHelper.calculateLetterGrade(60));
        assertEquals("F", gradeHelper.calculateLetterGrade(50));
        assertEquals("INVALID", gradeHelper.calculateLetterGrade(105));
    }
    @Test
    void BranchCoverageTest() {
        // Branch 1: score < 0 -> TRUE
        assertEquals("INVALID", gradeHelper.calculateLetterGrade(-1));
        // Branch 1: FALSE
        assertEquals("A", gradeHelper.calculateLetterGrade(95));

        // Branch 2: score > 100 -> TRUE
        assertEquals("INVALID", gradeHelper.calculateLetterGrade(150));
        // Branch 2: FALSE (already covered by 95)

        // Branch 3: score >= 93 -> TRUE
        assertEquals("A", gradeHelper.calculateLetterGrade(95));
        // Branch 3: FALSE
        assertEquals("A-", gradeHelper.calculateLetterGrade(91));

        // Branch 4: score >= 90 -> TRUE
        assertEquals("A-", gradeHelper.calculateLetterGrade(91));
        // Branch 4: FALSE
        assertEquals("B+", gradeHelper.calculateLetterGrade(88));

        // Branch 5: score >= 87 ->TRUE
        assertEquals("B+", gradeHelper.calculateLetterGrade(88));
        // Branch 5: FALSE
        assertEquals("B", gradeHelper.calculateLetterGrade(84));

        // Branch 6: score >= 83 -> TRUE
        assertEquals("B", gradeHelper.calculateLetterGrade(84));
        // Branch 6: FALSE
        assertEquals("B-", gradeHelper.calculateLetterGrade(81));

        // Branch 7: score >= 80 ->TRUE
        assertEquals("B-", gradeHelper.calculateLetterGrade(81));
        // Branch 7: FALSE
        assertEquals("C+", gradeHelper.calculateLetterGrade(78));

        // Branch 8: score >= 77 -> TRUE
        assertEquals("C+", gradeHelper.calculateLetterGrade(78));
        // Branch 8: FALSE
        assertEquals("C", gradeHelper.calculateLetterGrade(74));

        // Branch 9: score >= 73 -> TRUE
        assertEquals("C", gradeHelper.calculateLetterGrade(74));
        // Branch 9: FALSE
        assertEquals("C-", gradeHelper.calculateLetterGrade(71));

        // Branch 10: score >= 70 -> TRUE
        assertEquals("C-", gradeHelper.calculateLetterGrade(71));
        // Branch 10: FALSE
        assertEquals("D+", gradeHelper.calculateLetterGrade(68));

        // Branch 11: score >= 67 -> TRUE
        assertEquals("D+", gradeHelper.calculateLetterGrade(68));
        // Branch 11: FALSE
        assertEquals("D", gradeHelper.calculateLetterGrade(64));

        // Branch 12: score >= 63 -> TRUE
        assertEquals("D", gradeHelper.calculateLetterGrade(64));
        // Branch 12: FALSE
        assertEquals("D-", gradeHelper.calculateLetterGrade(60));

        // Branch 13: else -> TRUE
        assertEquals("F", gradeHelper.calculateLetterGrade(50));
    }
    @Test
    void conditionCoverageTests() {
        // score < 0 -> true
        assertEquals("INVALID", gradeHelper.calculateLetterGrade(-10));

        // score > 100 -> true
        assertEquals("INVALID", gradeHelper.calculateLetterGrade(120));

        // both false
        assertEquals("A", gradeHelper.calculateLetterGrade(95));
    }

    @Test
    void MCDCCoverageTests() {
        // The compound condition: score < 0 || score > 100
        // Each condition independently affects outcome
        assertEquals("INVALID", gradeHelper.calculateLetterGrade(-1));   // score < 0 → true
        assertEquals("INVALID", gradeHelper.calculateLetterGrade(101));  // score > 100 → true
        assertEquals("A", gradeHelper.calculateLetterGrade(95));         // both false → false
    }
}