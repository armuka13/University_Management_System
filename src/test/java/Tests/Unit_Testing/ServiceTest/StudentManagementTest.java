package Tests.Unit_Testing.ServiceTest;

import edu.university.main.model.Student;
import edu.university.main.repository.StudentRepository;
import edu.university.main.service.StudentManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentManagementTest {

    private StudentManagementService service;
    private FakeStudentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new FakeStudentRepository();
        service = new StudentManagementService(repository);
    }

    @Test
    void testCreateStudentSuccess() {
        boolean result = service.createStudent("S001", "John Doe", "john@test.com", 20, "CS");

        assertTrue(result);
        assertTrue(repository.exists("S001"));
    }

    @Test
    void testCreateStudentNullId() {
        boolean result = service.createStudent(null, "John Doe", "john@test.com", 20, "CS");

        assertFalse(result);
    }

    @Test
    void testCreateStudentEmptyId() {
        boolean result = service.createStudent("", "John Doe", "john@test.com", 20, "CS");

        assertFalse(result);
    }

    @Test
    void testCreateStudentNullName() {
        boolean result = service.createStudent("S001", null, "john@test.com", 20, "CS");

        assertFalse(result);
    }

    @Test
    void testCreateStudentAlreadyExists() {
        repository.save(new Student("S001", "Jane", "jane@test.com", 21, "Math"));

        boolean result = service.createStudent("S001", "John Doe", "john@test.com", 20, "CS");

        assertFalse(result);
    }

    @Test
    void testGetStudentExists() {
        Student student = new Student("S001", "John", "john@test.com", 20, "CS");
        repository.save(student);

        Optional<Student> result = service.getStudent("S001");

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
    }

    @Test
    void testGetStudentNotExists() {
        Optional<Student> result = service.getStudent("S999");

        assertFalse(result.isPresent());
    }

    @Test
    void testGetAllStudents() {
        repository.save(new Student("S001", "John", "john@test.com", 20, "CS"));
        repository.save(new Student("S002", "Jane", "jane@test.com", 21, "Math"));

        List<Student> students = service.getAllStudents();

        assertEquals(2, students.size());
    }

    @Test
    void testGetStudentsByMajor() {
        repository.save(new Student("S001", "John", "john@test.com", 20, "CS"));
        repository.save(new Student("S002", "Jane", "jane@test.com", 21, "CS"));
        repository.save(new Student("S003", "Bob", "bob@test.com", 22, "Math"));

        List<Student> csStudents = service.getStudentsByMajor("CS");

        assertEquals(2, csStudents.size());
    }

    @Test
    void testSearchStudentsByName() {
        repository.save(new Student("S001", "John Doe", "john@test.com", 20, "CS"));
        repository.save(new Student("S002", "Jane Doe", "jane@test.com", 21, "Math"));
        repository.save(new Student("S003", "Bob Smith", "bob@test.com", 22, "CS"));

        List<Student> results = service.searchStudentsByName("Doe");

        assertEquals(2, results.size());
    }

    @Test
    void testUpdateStudentSuccess() {
        Student student = new Student("S001", "John", "john@test.com", 20, "CS");
        repository.save(student);
        student.setName("John Updated");

        boolean result = service.updateStudent(student);

        assertTrue(result);
        assertEquals("John Updated", repository.findById("S001").get().getName());
    }

    @Test
    void testUpdateStudentNull() {
        boolean result = service.updateStudent(null);

        assertFalse(result);
    }

    @Test
    void testUpdateStudentNotExists() {
        Student student = new Student("S999", "John", "john@test.com", 20, "CS");

        boolean result = service.updateStudent(student);

        assertFalse(result);
    }

    @Test
    void testUpdateStudentEmailSuccess() {
        repository.save(new Student("S001", "John", "john@test.com", 20, "CS"));

        boolean result = service.updateStudentEmail("S001", "newemail@test.com");

        assertTrue(result);
        assertEquals("newemail@test.com", repository.findById("S001").get().getEmail());
    }

    @Test
    void testUpdateStudentEmailNotExists() {
        boolean result = service.updateStudentEmail("S999", "newemail@test.com");

        assertFalse(result);
    }

    @Test
    void testUpdateStudentGPASuccess() {
        repository.save(new Student("S001", "John", "john@test.com", 20, "CS"));

        boolean result = service.updateStudentGPA("S001", 3.75);

        assertTrue(result);
        assertEquals(3.75, repository.findById("S001").get().getGpa());
    }

    @Test
    void testUpdateStudentGPAInvalidLow() {
        repository.save(new Student("S001", "John", "john@test.com", 20, "CS"));

        boolean result = service.updateStudentGPA("S001", -0.5);

        assertFalse(result);
    }

    @Test
    void testUpdateStudentGPAInvalidHigh() {
        repository.save(new Student("S001", "John", "john@test.com", 20, "CS"));

        boolean result = service.updateStudentGPA("S001", 5.0);

        assertFalse(result);
    }

    @Test
    void testUpdateStudentGPANotExists() {
        boolean result = service.updateStudentGPA("S999", 3.5);

        assertFalse(result);
    }

    @Test
    void testDeleteStudentSuccess() {
        repository.save(new Student("S001", "John", "john@test.com", 20, "CS"));

        boolean result = service.deleteStudent("S001");

        assertTrue(result);
        assertFalse(repository.exists("S001"));
    }

    @Test
    void testDeleteStudentNotExists() {
        boolean result = service.deleteStudent("S999");

        assertFalse(result);
    }

    @Test
    void testDeleteStudentsByMajor() {
        repository.save(new Student("S001", "John", "john@test.com", 20, "CS"));
        repository.save(new Student("S002", "Jane", "jane@test.com", 21, "CS"));
        repository.save(new Student("S003", "Bob", "bob@test.com", 22, "Math"));

        int deleted = service.deleteStudentsByMajor("CS");

        assertEquals(2, deleted);
        assertEquals(1, repository.count());
    }

    @Test
    void testDeleteStudentsByMajorNoneFound() {
        repository.save(new Student("S001", "John", "john@test.com", 20, "CS"));

        int deleted = service.deleteStudentsByMajor("Math");

        assertEquals(0, deleted);
    }

    @Test
    void testGetTotalStudents() {
        repository.save(new Student("S001", "John", "john@test.com", 20, "CS"));
        repository.save(new Student("S002", "Jane", "jane@test.com", 21, "Math"));

        int total = service.getTotalStudents();

        assertEquals(2, total);
    }

    @Test
    void testGetAverageGPA() {
        Student s1 = new Student("S001", "John", "john@test.com", 20, "CS");
        s1.setGpa(3.5);
        Student s2 = new Student("S002", "Jane", "jane@test.com", 21, "Math");
        s2.setGpa(3.7);
        repository.save(s1);
        repository.save(s2);

        double avg = service.getAverageGPA();

        assertEquals(3.6, avg);
    }

    @Test
    void testHasStudentsTrue() {
        repository.save(new Student("S001", "John", "john@test.com", 20, "CS"));

        boolean hasStudents = service.hasStudents();

        assertTrue(hasStudents);
    }

    @Test
    void testHasStudentsFalse() {
        boolean hasStudents = service.hasStudents();

        assertFalse(hasStudents);
    }

    // Test Double
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
            if (!students.containsKey(student.getStudentId())) {
                return false;
            }
            students.put(student.getStudentId(), student);
            return true;
        }

        @Override
        public boolean delete(String id) {
            if (!students.containsKey(id)) {
                return false;
            }
            students.remove(id);
            return true;
        }

        @Override
        public List<Student> findByMajor(String major) {
            List<Student> result = new ArrayList<>();
            for (Student s : students.values()) {
                if (s.getMajor().equals(major)) {
                    result.add(s);
                }
            }
            return result;
        }

        @Override
        public List<Student> searchByName(String namePattern) {
            List<Student> result = new ArrayList<>();
            for (Student s : students.values()) {
                if (s.getName().contains(namePattern)) {
                    result.add(s);
                }
            }
            return result;
        }

        @Override
        public int count() {
            return students.size();
        }

        @Override
        public double calculateAverageGpa() {
            if (students.isEmpty()) return 0.0;
            double sum = 0.0;
            for (Student s : students.values()) {
                sum += s.getGpa();
            }
            return sum / students.size();
        }
    }
}