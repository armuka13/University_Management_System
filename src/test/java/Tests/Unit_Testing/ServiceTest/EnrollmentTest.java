package Tests.Unit_Testing.ServiceTest;

import edu.university.main.model.Course;
import edu.university.main.model.Student;
import edu.university.main.repository.CourseRepository;
import edu.university.main.repository.StudentRepository;
import edu.university.main.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EnrollmentTest {

    private EnrollmentService service;
    private FakeStudentRepository studentRepository;
    private FakeCourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        studentRepository = new FakeStudentRepository();
        courseRepository = new FakeCourseRepository();
        service = new EnrollmentService(studentRepository, courseRepository);
    }

    @Test
    void testEnrollStudentSuccess() {
        Student student = new Student("S001", "John", "john@test.com", 20, "CS");
        student.setGpa(3.5);
        student.setCreditHours(12);
        studentRepository.save(student);

        Course course = new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS");
        courseRepository.save(course);

        boolean result = service.enrollStudent("S001", "CS101");

        assertTrue(result);
        assertEquals(1, course.getCurrentEnrollment());
        assertEquals(15, student.getCreditHours());
    }

    @Test
    void testEnrollStudentNotFound() {
        Course course = new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS");
        courseRepository.save(course);

        boolean result = service.enrollStudent("S999", "CS101");

        assertFalse(result);
    }

    @Test
    void testEnrollStudentCourseNotFound() {
        Student student = new Student("S001", "John", "john@test.com", 20, "CS");
        studentRepository.save(student);

        boolean result = service.enrollStudent("S001", "CS999");

        assertFalse(result);
    }

    @Test
    void testEnrollStudentCourseFull() {
        Student student = new Student("S001", "John", "john@test.com", 20, "CS");
        student.setGpa(3.5);
        studentRepository.save(student);

        Course course = new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS");
        course.setCurrentEnrollment(30);
        courseRepository.save(course);

        boolean result = service.enrollStudent("S001", "CS101");

        assertFalse(result);
    }

    @Test
    void testEnrollStudentCannotEnroll() {
        Student student = new Student("S001", "John", "john@test.com", 20, "CS");
        student.setGpa(1.5);
        student.setCreditHours(15);
        studentRepository.save(student);

        Course course = new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS");
        courseRepository.save(course);

        boolean result = service.enrollStudent("S001", "CS101");

        assertFalse(result);
    }

    @Test
    void testDropCourseSuccess() {
        Student student = new Student("S001", "John", "john@test.com", 20, "CS");
        student.setCreditHours(15);
        student.getEnrolledCourseIds().add("CS101");
        studentRepository.save(student);

        Course course = new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS");
        course.setCurrentEnrollment(20);
        course.getEnrolledStudentIds().add("S001");
        courseRepository.save(course);

        boolean result = service.dropCourse("S001", "CS101");

        assertTrue(result);
        assertEquals(19, course.getCurrentEnrollment());
        assertEquals(12, student.getCreditHours());
        assertFalse(student.getEnrolledCourseIds().contains("CS101"));
    }

    @Test
    void testDropCourseNotEnrolled() {
        Student student = new Student("S001", "John", "john@test.com", 20, "CS");
        studentRepository.save(student);

        Course course = new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS");
        courseRepository.save(course);

        boolean result = service.dropCourse("S001", "CS101");

        assertFalse(result);
    }

    @Test
    void testCalculateEnrollmentRate() {
        double rate = service.calculateEnrollmentRate(75, 100);

        assertEquals(75.0, rate);
    }

    @Test
    void testCalculateEnrollmentRateFull() {
        double rate = service.calculateEnrollmentRate(100, 100);

        assertEquals(100.0, rate);
    }

    @Test
    void testCalculateEnrollmentRateZeroCapacity() {
        double rate = service.calculateEnrollmentRate(50, 0);

        assertEquals(-1.0, rate);
    }

    @Test
    void testCalculateEnrollmentRateExceedsCapacity() {
        double rate = service.calculateEnrollmentRate(110, 100);

        assertEquals(-1.0, rate);
    }

    @Test
    void testPredictEnrollment() {
        int predicted = service.predictEnrollment(1000, 5.0);

        assertEquals(1050, predicted);
    }

    @Test
    void testPredictEnrollmentNegativeGrowth() {
        int predicted = service.predictEnrollment(1000, -10.0);

        assertEquals(900, predicted);
    }

    @Test
    void testPredictEnrollmentInvalidGrowth() {
        int predicted = service.predictEnrollment(1000, -150.0);

        assertEquals(-1, predicted);
    }

    @Test
    void testCalculateWaitlistPosition() {
        int position = service.calculateWaitlistPosition(30, 30, 45);

        assertEquals(15, position);
    }

    @Test
    void testCalculateWaitlistPositionNoWaitlist() {
        int position = service.calculateWaitlistPosition(20, 30, 25);

        assertEquals(0, position);
    }

    @Test
    void testCalculateWaitlistPositionInvalid() {
        int position = service.calculateWaitlistPosition(-5, 30, 45);

        assertEquals(-1, position);
    }

    @Test
    void testCheckPrerequisitesValid() {
        boolean valid = service.checkPrerequisites(3.0, 40, true);

        assertTrue(valid);
    }

    @Test
    void testCheckPrerequisitesLowGPA() {
        boolean valid = service.checkPrerequisites(1.8, 40, true);

        assertFalse(valid);
    }

    @Test
    void testCheckPrerequisitesInsufficientCredits() {
        boolean valid = service.checkPrerequisites(3.0, 20, true);

        assertFalse(valid);
    }

    @Test
    void testCheckPrerequisitesNoPrereq() {
        boolean valid = service.checkPrerequisites(3.0, 40, false);

        assertFalse(valid);
    }

    @Test
    void testCalculateRetentionRate() {
        double rate = service.calculateRetentionRate(850, 1000);

        assertEquals(85.0, rate);
    }

    @Test
    void testCalculateRetentionRatePerfect() {
        double rate = service.calculateRetentionRate(1000, 1000);

        assertEquals(100.0, rate);
    }

    @Test
    void testCalculateRetentionRateInvalid() {
        double rate = service.calculateRetentionRate(1100, 1000);

        assertEquals(-1.0, rate);
    }

    // Test Doubles
    private static class FakeStudentRepository extends StudentRepository {
        private Map<String, Student> students = new HashMap<>();

        @Override
        public boolean save(Student student) {
            students.put(student.getStudentId(), student);
            return true;
        }

        @Override
        public Optional<Student> findById(String id) {
            return Optional.ofNullable(students.get(id));
        }

        @Override
        public Collection<Student> findAll() {
            return new ArrayList<>(students.values());
        }

        @Override
        public boolean exists(String id) {
            return students.containsKey(id);
        }

        @Override
        public boolean update(Student student) {
            students.put(student.getStudentId(), student);
            return true;
        }

        @Override
        public boolean delete(String id) {
            return students.remove(id) != null;
        }

        @Override
        public List<Student> searchByName(String pattern) {
            List<Student> result = new ArrayList<>();
            for (Student s : students.values()) {
                if (s.getName().contains(pattern)) {
                    result.add(s);
                }
            }
            return result;
        }
    }

    private static class FakeCourseRepository extends CourseRepository {
        private Map<String, Course> courses = new HashMap<>();

        @Override
        public boolean save(Course course) {
            courses.put(course.getCourseId(), course);
            return true;
        }

        @Override
        public Optional<Course> findById(String id) {
            return Optional.ofNullable(courses.get(id));
        }

        @Override
        public Collection<Course> findAll() {
            return new ArrayList<>(courses.values());
        }

        @Override
        public boolean exists(String id) {
            return courses.containsKey(id);
        }

        @Override
        public boolean update(Course course) {
            courses.put(course.getCourseId(), course);
            return true;
        }

        @Override
        public boolean delete(String id) {
            return courses.remove(id) != null;
        }

        @Override
        public List<Course> searchByName(String pattern) {
            List<Course> result = new ArrayList<>();
            for (Course c : courses.values()) {
                if (c.getCourseName().contains(pattern)) {
                    result.add(c);
                }
            }
            return result;
        }
    }
}