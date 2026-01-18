package System_Testing;

import edu.university.main.UniversityManagementApp;
import edu.university.main.repository.CourseRepository;
import edu.university.main.repository.GradeRepository;
import edu.university.main.repository.StudentRepository;
import edu.university.main.service.EnrollmentService;
import edu.university.main.service.FinancialService;
import edu.university.main.service.GradeCalculationService;
import edu.university.main.service.GradeManagementService;
import org.junit.jupiter.api.*;

public abstract class BaseSystemTest {

    protected UniversityManagementApp app;
    protected StudentRepository studentRepo;
    protected CourseRepository courseRepo;
    protected GradeRepository gradeRepo;
    protected EnrollmentService enrollmentService;
    protected FinancialService financialService;
    protected GradeCalculationService gradeCalculationService;
    protected GradeManagementService gradeManagementService;

    @BeforeEach
    void setUp() {
        studentRepo = new StudentRepository();
        courseRepo = new CourseRepository();
        gradeRepo=new GradeRepository();
        enrollmentService = new EnrollmentService(studentRepo, courseRepo);
        financialService = new FinancialService();
        gradeCalculationService = new GradeCalculationService();
        gradeManagementService=new GradeManagementService(gradeRepo);
    }

    @AfterEach
    void tearDown() {
        // Clean up data after each test
        if (studentRepo != null) studentRepo.clear();
        if (courseRepo != null) courseRepo.clear();
    }
}
