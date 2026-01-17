package Tests.Integration_Tests;

import edu.university.main.model.Student;
import edu.university.main.model.Course;
import edu.university.main.model.Grade;
import edu.university.main.repository.StudentRepository;
import edu.university.main.repository.CourseRepository;
import edu.university.main.repository.GradeRepository;
import edu.university.main.service.EnrollmentService;
import edu.university.main.service.GradeCalculationService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EnrollmentGradesIntegrationTest {

    @Test
    void testStudentEnrollsCompletesAndGradeCalculated() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        GradeRepository gradeRepository = new GradeRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        GradeCalculationService gradeCalculationService = new GradeCalculationService();

        // Step 1: Create and enroll student
        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(3.0);
        student.setCreditHours(0);
        enrollmentService.createStudent(student);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        courseRepository.save(course);

        boolean enrolled = enrollmentService.enrollStudent("S001", "SWE303");
        assertTrue(enrolled);

        // Step 2: Simulate course completion with grades
        List<Double> studentScores = new ArrayList<>();
        studentScores.add(85.0);
        studentScores.add(90.0);
        studentScores.add(92.0);
        studentScores.add(88.0);

        // Step 3: Calculate course average
        double courseAverage = gradeCalculationService.calculateCourseAverage(studentScores);
        assertTrue(courseAverage > 0);
        assertEquals(88.75, courseAverage, 0.1);

        // Step 4: Assign final grade and verify
        Grade grade = new Grade("S001", "SWE303", courseAverage, 1, 2025);
        gradeRepository.save(grade);

        Grade retrievedGrade = gradeRepository.findByStudentAndCourse("S001", "SWE303");
        assertNotNull(retrievedGrade);
        assertEquals(88.75, retrievedGrade.getNumericGrade(), 0.1);
    }

    @Test
    void testMultipleCoursesWithWeightedGrades() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        GradeRepository gradeRepository = new GradeRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        GradeCalculationService gradeCalculationService = new GradeCalculationService();

        // Create student
        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(0.0);
        enrollmentService.createStudent(student);

        // Create and enroll in multiple courses
        Course course1 = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        Course course2 = new Course("CS201", "Data Structures", "Dr. Skuka", 3, 30, "SWE");

        courseRepository.save(course1);
        courseRepository.save(course2);

        enrollmentService.enrollStudent("S001", "SWE303");
        enrollmentService.enrollStudent("S001", "CS201");

        // Calculate weighted average for SWE303
        List<Double> swe303Scores = new ArrayList<>();
        swe303Scores.add(90.0);
        swe303Scores.add(85.0);
        swe303Scores.add(92.0);

        List<Double> swe303Weights = new ArrayList<>();
        swe303Weights.add(0.3);
        swe303Weights.add(0.3);
        swe303Weights.add(0.4);

        double swe303Average = gradeCalculationService.calculateWeightedAverage(swe303Scores, swe303Weights);
        assertEquals(89.3, swe303Average);

        // Calculate weighted average for CS201
        List<Double> cs201Scores = new ArrayList<>();
        cs201Scores.add(88.0);
        cs201Scores.add(92.0);

        List<Double> cs201Weights = new ArrayList<>();
        cs201Weights.add(0.5); 
        cs201Weights.add(0.5);

        double cs201Average = gradeCalculationService.calculateWeightedAverage(cs201Scores, cs201Weights);
        assertEquals(90.0, cs201Average, 0.1);

        // Save grades
        gradeRepository.save(new Grade("S001", "SWE303", swe303Average, 1, 2025));
        gradeRepository.save(new Grade("S001", "CS201", cs201Average, 1, 2025));

        // Verify grades exist
        assertEquals(2, gradeRepository.findByStudent("S001").size());
    }

    @Test
    void testBonusPointsAppliedToFinalGrade() {
        GradeCalculationService gradeCalculationService = new GradeCalculationService();

        double baseScore = 85.0;

        // Student with both attendance and participation
        double scoreWithBoth = gradeCalculationService.applyBonusPoints(baseScore, true, true);
        assertEquals(90.0, scoreWithBoth, 0.1);

        // Student with only attendance
        double scoreWithAttendance = gradeCalculationService.applyBonusPoints(baseScore, true, false);
        assertEquals(87.0, scoreWithAttendance, 0.1);

        // Student with only participation
        double scoreWithParticipation = gradeCalculationService.applyBonusPoints(baseScore, false, true);
        assertEquals(87.0, scoreWithParticipation, 0.1);

        // Student with neither
        double scoreWithNeither = gradeCalculationService.applyBonusPoints(baseScore, false, false);
        assertEquals(85.0, scoreWithNeither, 0.1);
    }

    @Test
    void testCurvedGradeCalculation() {
        GradeCalculationService gradeCalculationService = new GradeCalculationService();

        double rawScore = 75.0;
        double classAverage = 70.0;
        double targetAverage = 75.0;

        double curvedScore = gradeCalculationService.calculateCurvedGrade(rawScore, classAverage, targetAverage);
        assertEquals(80.0, curvedScore, 0.1);
    }

    @Test
    void testHonorsEligibilityBasedOnGrades() {
        StudentRepository studentRepository = new StudentRepository();
        GradeCalculationService gradeCalculationService = new GradeCalculationService();

        // High GPA student
        Student excellentStudent = new Student("S001", "Albi", "albi@edu.com", 22, "SWE");
        excellentStudent.setGpa(3.9);
        studentRepository.save(excellentStudent);

        boolean eligibleForHonors = gradeCalculationService.isEligibleForHonors(3.9, 120, 120);
        assertTrue(eligibleForHonors);

        String honorsLevel = gradeCalculationService.determineHonorsLevel(3.9, 120);
        assertEquals("SUMMA_CUM_LAUDE", honorsLevel);

        // Good GPA student
        Student goodStudent = new Student("S002", "Tomi", "tomi@edu.com", 22, "SWE");
        goodStudent.setGpa(3.7);
        studentRepository.save(goodStudent);

        boolean eligibleMagna = gradeCalculationService.isEligibleForHonors(3.7, 120, 120);
        assertTrue(eligibleMagna);

        String magnaCumLaude = gradeCalculationService.determineHonorsLevel(3.7, 120);
        assertEquals("MAGNA_CUM_LAUDE", magnaCumLaude);
    }

    @Test
    void testDropLowestScoresCalculation() {
        GradeCalculationService gradeCalculationService = new GradeCalculationService();

        List<Double> scores = new ArrayList<>();
        scores.add(60.0);
        scores.add(75.0);
        scores.add(85.0);
        scores.add(90.0);
        scores.add(95.0);

        // Drop lowest 2 scores (60, 75)
        double averageAfterDrop = gradeCalculationService.calculateDroppedLowestScores(scores, 2);
        assertEquals(90.0, averageAfterDrop, 0.1); // (85 + 90 + 95) / 3
    }

    @Test
    void testStandardDeviationCalculation() {
        GradeCalculationService gradeCalculationService = new GradeCalculationService();

        List<Double> scores = new ArrayList<>();
        scores.add(80.0);
        scores.add(85.0);
        scores.add(90.0);
        scores.add(85.0);
        scores.add(90.0);

        double stdDev = gradeCalculationService.calculateStandardDeviation(scores);
        assertTrue(stdDev > 0);
    }

    @Test
    void testClassRankCalculation() {
        GradeCalculationService gradeCalculationService = new GradeCalculationService();

        // Top 5% student
        int rankTop = gradeCalculationService.calculateClassRank(3.95, 100);
        assertTrue(rankTop <= 5);

        // Top 10% student
        int rankGood = gradeCalculationService.calculateClassRank(3.75, 100);
        assertTrue(rankGood <= 10);

        // Average student
        int rankAverage = gradeCalculationService.calculateClassRank(3.0, 100);
        assertTrue(rankAverage <= 50);
    }
}