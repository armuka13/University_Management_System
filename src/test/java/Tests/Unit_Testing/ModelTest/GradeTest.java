package Tests.Unit_Testing.ModelTest;

import edu.university.main.model.Grade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeTest {

    private Grade grade;

    @BeforeEach
    void setUp() {
        grade = new Grade("S001", "CS101", 85.0, 1, 2024);
    }

    @Test
    void testCalculateLetterGradeA() {
        String letter = grade.calculateLetterGrade(95.0);

        assertEquals("A", letter);
    }

    @Test
    void testCalculateLetterGradeAMinus() {
        String letter = grade.calculateLetterGrade(91.0);

        assertEquals("A-", letter);
    }

    @Test
    void testCalculateLetterGradeBPlus() {
        String letter = grade.calculateLetterGrade(88.0);

        assertEquals("B+", letter);
    }

    @Test
    void testCalculateLetterGradeB() {
        String letter = grade.calculateLetterGrade(85.0);

        assertEquals("B", letter);
    }

    @Test
    void testCalculateLetterGradeBMinus() {
        String letter = grade.calculateLetterGrade(81.0);

        assertEquals("B-", letter);
    }

    @Test
    void testCalculateLetterGradeCPlus() {
        String letter = grade.calculateLetterGrade(78.0);

        assertEquals("C+", letter);
    }

    @Test
    void testCalculateLetterGradeC() {
        String letter = grade.calculateLetterGrade(75.0);

        assertEquals("C", letter);
    }

    @Test
    void testCalculateLetterGradeCMinus() {
        String letter = grade.calculateLetterGrade(71.0);

        assertEquals("C-", letter);
    }

    @Test
    void testCalculateLetterGradeDPlus() {
        String letter = grade.calculateLetterGrade(68.0);

        assertEquals("D+", letter);
    }

    @Test
    void testCalculateLetterGradeD() {
        String letter = grade.calculateLetterGrade(65.0);

        assertEquals("D", letter);
    }

    @Test
    void testCalculateLetterGradeDMinus() {
        String letter = grade.calculateLetterGrade(61.0);

        assertEquals("D-", letter);
    }

    @Test
    void testCalculateLetterGradeF() {
        String letter = grade.calculateLetterGrade(55.0);

        assertEquals("F", letter);
    }

    @Test
    void testCalculateLetterGradeInvalid() {
        String letter = grade.calculateLetterGrade(-10.0);

        assertEquals("INVALID", letter);
    }

    @Test
    void testCalculateLetterGradeOverHundred() {
        String letter = grade.calculateLetterGrade(105.0);

        assertEquals("INVALID", letter);
    }

    @Test
    void testConvertLetterToGPAA() {
        double gpa = grade.convertLetterToGPA("A");

        assertEquals(4.0, gpa);
    }

    @Test
    void testConvertLetterToGPAAMinus() {
        double gpa = grade.convertLetterToGPA("A-");

        assertEquals(3.7, gpa);
    }

    @Test
    void testConvertLetterToGPABPlus() {
        double gpa = grade.convertLetterToGPA("B+");

        assertEquals(3.3, gpa);
    }

    @Test
    void testConvertLetterToGPAB() {
        double gpa = grade.convertLetterToGPA("B");

        assertEquals(3.0, gpa);
    }

    @Test
    void testConvertLetterToGPAF() {
        double gpa = grade.convertLetterToGPA("F");

        assertEquals(0.0, gpa);
    }

    @Test
    void testConvertLetterToGPAInvalid() {
        double gpa = grade.convertLetterToGPA("Z");

        assertEquals(-1.0, gpa);
    }

    @Test
    void testConvertLetterToGPANull() {
        double gpa = grade.convertLetterToGPA(null);

        assertEquals(-1.0, gpa);
    }

    @Test
    void testConvertLetterToGPAEmpty() {
        double gpa = grade.convertLetterToGPA("");

        assertEquals(-1.0, gpa);
    }

    @Test
    void testConvertLetterToGPACaseInsensitive() {
        double gpa = grade.convertLetterToGPA("b+");

        assertEquals(3.3, gpa);
    }

    @Test
    void testConvertPercentageToGPA() {
        double gpa = grade.convertPercentageToGPA(95.0);

        assertEquals(4.0, gpa);
    }

    @Test
    void testConvertPercentageToGPABRange() {
        double gpa = grade.convertPercentageToGPA(85.0);

        assertEquals(3.0, gpa);
    }

    @Test
    void testConvertPercentageToGPAInvalid() {
        double gpa = grade.convertPercentageToGPA(-5.0);

        assertEquals(-1.0, gpa);
    }

    @Test
    void testIsPassingUndergraduate() {
        boolean passing = grade.isPassing(65.0, false);

        assertTrue(passing);
    }

    @Test
    void testIsPassingUndergraduateFailing() {
        boolean passing = grade.isPassing(55.0, false);

        assertFalse(passing);
    }

    @Test
    void testIsPassingGraduate() {
        boolean passing = grade.isPassing(75.0, true);

        assertTrue(passing);
    }

    @Test
    void testIsPassingGraduateFailing() {
        boolean passing = grade.isPassing(65.0, true);

        assertFalse(passing);
    }

    @Test
    void testIsPassingInvalidScore() {
        boolean passing = grade.isPassing(-10.0, false);

        assertFalse(passing);
    }

    @Test
    void testIsPassingWithHonorsRegularCourse() {
        boolean passing = grade.isPassingWithHonors(95.0, false);

        assertTrue(passing);
    }

    @Test
    void testIsPassingWithHonorsRegularCourseNotHonors() {
        boolean passing = grade.isPassingWithHonors(90.0, false);

        assertFalse(passing);
    }

    @Test
    void testIsPassingWithHonorsHonorsCourse() {
        boolean passing = grade.isPassingWithHonors(88.0, true);

        assertTrue(passing);
    }

    @Test
    void testIsPassingWithHonorsHonorsCourseNotHonors() {
        boolean passing = grade.isPassingWithHonors(85.0, true);

        assertFalse(passing);
    }

    @Test
    void testCalculateWeightedScorePercentageWeights() {
        double weighted = grade.calculateWeightedScore(80.0, 90.0, 85.0, 30.0, 40.0, 30.0);

        assertEquals(85.5, weighted);
    }

    @Test
    void testCalculateWeightedScoreDecimalWeights() {
        double weighted = grade.calculateWeightedScore(80.0, 90.0, 85.0, 0.3, 0.4, 0.3);

        assertEquals(85.5, weighted);
    }

    @Test
    void testCalculateWeightedScoreInvalidWeightSum() {
        double weighted = grade.calculateWeightedScore(80.0, 90.0, 85.0, 30.0, 40.0, 20.0);

        assertEquals(-1.0, weighted);
    }

    @Test
    void testCalculateWeightedScoreInvalidScore() {
        double weighted = grade.calculateWeightedScore(-10.0, 90.0, 85.0, 30.0, 40.0, 30.0);

        assertEquals(-1.0, weighted);
    }

    @Test
    void testCalculateWeightedScoreNegativeWeight() {
        double weighted = grade.calculateWeightedScore(80.0, 90.0, 85.0, -10.0, 40.0, 70.0);

        assertEquals(-1.0, weighted);
    }

    @Test
    void testCalculateComprehensiveScore() {
        double score = grade.calculateComprehensiveScore(80.0, 90.0, 85.0, 95.0, 100.0);

        assertEquals(86.0, score);
    }

    @Test
    void testCalculateComprehensiveScoreAllPerfect() {
        double score = grade.calculateComprehensiveScore(100.0, 100.0, 100.0, 100.0, 100.0);

        assertEquals(100.0, score);
    }

    @Test
    void testCalculateComprehensiveScoreInvalidMidterm() {
        double score = grade.calculateComprehensiveScore(-10.0, 90.0, 85.0, 95.0, 100.0);

        assertEquals(-1.0, score);
    }

    @Test
    void testCalculateComprehensiveScoreInvalidAttendance() {
        double score = grade.calculateComprehensiveScore(80.0, 90.0, 85.0, 95.0, 110.0);

        assertEquals(-1.0, score);
    }

    @Test
    void testApplyExtraCreditBonus() {
        double finalScore = grade.applyExtraCreditBonus(85.0, 5.0, 10.0);

        assertEquals(90.0, finalScore);
    }

    @Test
    void testApplyExtraCreditBonusExceedsMax() {
        double finalScore = grade.applyExtraCreditBonus(85.0, 15.0, 10.0);

        assertEquals(95.0, finalScore);
    }

    @Test
    void testApplyExtraCreditBonusCappedAtHundred() {
        double finalScore = grade.applyExtraCreditBonus(95.0, 10.0, 10.0);

        assertEquals(100.0, finalScore);
    }

    @Test
    void testApplyExtraCreditBonusZeroBonus() {
        double finalScore = grade.applyExtraCreditBonus(85.0, 0.0, 10.0);

        assertEquals(85.0, finalScore);
    }

    @Test
    void testApplyExtraCreditBonusInvalidBase() {
        double finalScore = grade.applyExtraCreditBonus(-10.0, 5.0, 10.0);

        assertEquals(-1.0, finalScore);
    }

    @Test
    void testApplyExtraCreditBonusNegativeExtra() {
        double finalScore = grade.applyExtraCreditBonus(85.0, -5.0, 10.0);

        assertEquals(-1.0, finalScore);
    }

    @Test
    void testGettersAndSetters() {
        grade.setNumericGrade(92.0);
        grade.setMidtermScore(88.0);
        grade.setFinalScore(95.0);
        grade.setAssignmentScore(90.0);
        grade.setParticipationScore(85.0);

        assertEquals("S001", grade.getStudentId());
        assertEquals("CS101", grade.getCourseId());
        assertEquals(92.0, grade.getNumericGrade());
        assertEquals("A-", grade.getLetterGrade());
        assertEquals(1, grade.getSemester());
        assertEquals(2024, grade.getYear());
        assertEquals(88.0, grade.getMidtermScore());
        assertEquals(95.0, grade.getFinalScore());
        assertEquals(90.0, grade.getAssignmentScore());
        assertEquals(85.0, grade.getParticipationScore());
    }
}