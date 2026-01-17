package edu.university.main.ServiceTest;

import edu.university.main.model.Course;
import edu.university.main.repository.CourseRepository;
import edu.university.main.service.CourseManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CourseManagementTest {

    private CourseManagementService service;
    private FakeCourseRepository repository;

    @BeforeEach
    void setUp() {
        repository = new FakeCourseRepository();
        service = new CourseManagementService(repository);
    }

    @Test
    void testCreateCourseSuccess() {
        boolean result = service.createCourse("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS");

        assertTrue(result);
        assertTrue(repository.exists("CS101"));
    }

    @Test
    void testCreateCourseNullId() {
        boolean result = service.createCourse(null, "Intro to CS", "Dr. Smith", 3, 30, "CS");

        assertFalse(result);
    }

    @Test
    void testCreateCourseEmptyId() {
        boolean result = service.createCourse("", "Intro to CS", "Dr. Smith", 3, 30, "CS");

        assertFalse(result);
    }

    @Test
    void testCreateCourseAlreadyExists() {
        repository.save(new Course("CS101", "Intro", "Smith", 3, 30, "CS"));

        boolean result = service.createCourse("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS");

        assertFalse(result);
    }

    @Test
    void testGetCourseExists() {
        Course course = new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS");
        repository.save(course);

        Optional<Course> result = service.getCourse("CS101");

        assertTrue(result.isPresent());
        assertEquals("Intro to CS", result.get().getCourseName());
    }

    @Test
    void testGetCourseNotExists() {
        Optional<Course> result = service.getCourse("CS999");

        assertFalse(result.isPresent());
    }

    @Test
    void testGetAllCourses() {
        repository.save(new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS"));
        repository.save(new Course("CS201", "Data Structures", "Dr. Jones", 3, 25, "CS"));

        List<Course> courses = service.getAllCourses();

        assertEquals(2, courses.size());
    }

    @Test
    void testGetCoursesByDepartment() {
        repository.save(new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS"));
        repository.save(new Course("CS201", "Data Structures", "Dr. Jones", 3, 25, "CS"));
        repository.save(new Course("MATH101", "Calculus", "Dr. Brown", 4, 20, "Math"));

        List<Course> csCourses = service.getCoursesByDepartment("CS");

        assertEquals(2, csCourses.size());
    }

    @Test
    void testGetCoursesByInstructor() {
        repository.save(new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS"));
        repository.save(new Course("CS301", "Algorithms", "Dr. Smith", 3, 25, "CS"));
        repository.save(new Course("CS201", "Data Structures", "Dr. Jones", 3, 25, "CS"));

        List<Course> smithCourses = service.getCoursesByInstructor("Dr. Smith");

        assertEquals(2, smithCourses.size());
    }

    @Test
    void testSearchCoursesByName() {
        repository.save(new Course("CS101", "Intro to Programming", "Dr. Smith", 3, 30, "CS"));
        repository.save(new Course("CS201", "Advanced Programming", "Dr. Jones", 3, 25, "CS"));
        repository.save(new Course("MATH101", "Calculus", "Dr. Brown", 4, 20, "Math"));

        List<Course> results = service.searchCoursesByName("Programming");

        assertEquals(2, results.size());
    }

    @Test
    void testGetAvailableCourses() {
        Course c1 = new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS");
        c1.setCurrentEnrollment(20);
        Course c2 = new Course("CS201", "Data Structures", "Dr. Jones", 3, 25, "CS");
        c2.setCurrentEnrollment(25);
        repository.save(c1);
        repository.save(c2);

        List<Course> available = service.getAvailableCourses();

        assertEquals(1, available.size());
    }

    @Test
    void testGetFullCourses() {
        Course c1 = new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS");
        c1.setCurrentEnrollment(30);
        Course c2 = new Course("CS201", "Data Structures", "Dr. Jones", 3, 25, "CS");
        c2.setCurrentEnrollment(20);
        repository.save(c1);
        repository.save(c2);

        List<Course> full = service.getFullCourses();

        assertEquals(1, full.size());
    }

    @Test
    void testUpdateCourseSuccess() {
        Course course = new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS");
        repository.save(course);
        course.setCourseName("Introduction to Programming");

        boolean result = service.updateCourse(course);

        assertTrue(result);
        assertEquals("Introduction to Programming", repository.findById("CS101").get().getCourseName());
    }

    @Test
    void testUpdateCourseNull() {
        boolean result = service.updateCourse(null);

        assertFalse(result);
    }

    @Test
    void testUpdateCourseNotExists() {
        Course course = new Course("CS999", "Unknown", "Dr. X", 3, 30, "CS");

        boolean result = service.updateCourse(course);

        assertFalse(result);
    }

    @Test
    void testUpdateCourseCapacitySuccess() {
        Course course = new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS");
        course.setCurrentEnrollment(20);
        repository.save(course);

        boolean result = service.updateCourseCapacity("CS101", 40);

        assertTrue(result);
        assertEquals(40, repository.findById("CS101").get().getMaxCapacity());
    }

    @Test
    void testUpdateCourseCapacityBelowEnrollment() {
        Course course = new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS");
        course.setCurrentEnrollment(25);
        repository.save(course);

        boolean result = service.updateCourseCapacity("CS101", 20);

        assertFalse(result);
    }

    @Test
    void testUpdateCourseCapacityNegative() {
        repository.save(new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS"));

        boolean result = service.updateCourseCapacity("CS101", -5);

        assertFalse(result);
    }

    @Test
    void testUpdateCourseCapacityNotExists() {
        boolean result = service.updateCourseCapacity("CS999", 40);

        assertFalse(result);
    }

    @Test
    void testUpdateCourseInstructorSuccess() {
        repository.save(new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS"));

        boolean result = service.updateCourseInstructor("CS101", "Dr. Johnson");

        assertTrue(result);
        assertEquals("Dr. Johnson", repository.findById("CS101").get().getInstructor());
    }

    @Test
    void testUpdateCourseInstructorNotExists() {
        boolean result = service.updateCourseInstructor("CS999", "Dr. Johnson");

        assertFalse(result);
    }

    @Test
    void testDeleteCourseSuccess() {
        repository.save(new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS"));

        boolean result = service.deleteCourse("CS101");

        assertTrue(result);
        assertFalse(repository.exists("CS101"));
    }

    @Test
    void testDeleteCourseNotExists() {
        boolean result = service.deleteCourse("CS999");

        assertFalse(result);
    }

    @Test
    void testDeleteCoursesByDepartment() {
        repository.save(new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS"));
        repository.save(new Course("CS201", "Data Structures", "Dr. Jones", 3, 25, "CS"));
        repository.save(new Course("MATH101", "Calculus", "Dr. Brown", 4, 20, "Math"));

        int deleted = service.deleteCoursesByDepartment("CS");

        assertEquals(2, deleted);
        assertEquals(1, repository.count());
    }

    @Test
    void testGetTotalCourses() {
        repository.save(new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS"));
        repository.save(new Course("CS201", "Data Structures", "Dr. Jones", 3, 25, "CS"));

        int total = service.getTotalCourses();

        assertEquals(2, total);
    }

    @Test
    void testGetTotalEnrollment() {
        Course c1 = new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS");
        c1.setCurrentEnrollment(25);
        Course c2 = new Course("CS201", "Data Structures", "Dr. Jones", 3, 25, "CS");
        c2.setCurrentEnrollment(20);
        repository.save(c1);
        repository.save(c2);

        int total = service.getTotalEnrollment();

        assertEquals(45, total);
    }

    @Test
    void testGetAverageFillRate() {
        Course c1 = new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS");
        c1.setCurrentEnrollment(30);
        Course c2 = new Course("CS201", "Data Structures", "Dr. Jones", 3, 20, "CS");
        c2.setCurrentEnrollment(10);
        repository.save(c1);
        repository.save(c2);

        double avg = service.getAverageFillRate();

        assertEquals(75.0, avg);
    }

    @Test
    void testHasCoursesTrue() {
        repository.save(new Course("CS101", "Intro to CS", "Dr. Smith", 3, 30, "CS"));

        boolean hasCourses = service.hasCourses();

        assertTrue(hasCourses);
    }

    @Test
    void testHasCoursesFalse() {
        boolean hasCourses = service.hasCourses();

        assertFalse(hasCourses);
    }

    // Test Double
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
            if (!courses.containsKey(course.getCourseId())) {
                return false;
            }
            courses.put(course.getCourseId(), course);
            return true;
        }

        @Override
        public boolean delete(String id) {
            if (!courses.containsKey(id)) {
                return false;
            }
            courses.remove(id);
            return true;
        }

        @Override
        public List<Course> findByDepartment(String department) {
            List<Course> result = new ArrayList<>();
            for (Course c : courses.values()) {
                if (c.getDepartment().equals(department)) {
                    result.add(c);
                }
            }
            return result;
        }

        @Override
        public List<Course> findByInstructor(String instructor) {
            List<Course> result = new ArrayList<>();
            for (Course c : courses.values()) {
                if (c.getInstructor().equals(instructor)) {
                    result.add(c);
                }
            }
            return result;
        }

        @Override
        public List<Course> searchByName(String namePattern) {
            List<Course> result = new ArrayList<>();
            for (Course c : courses.values()) {
                if (c.getCourseName().contains(namePattern)) {
                    result.add(c);
                }
            }
            return result;
        }

        @Override
        public List<Course> findAvailableCourses() {
            List<Course> result = new ArrayList<>();
            for (Course c : courses.values()) {
                if (c.getCurrentEnrollment() < c.getMaxCapacity()) {
                    result.add(c);
                }
            }
            return result;
        }

        @Override
        public List<Course> findFullCourses() {
            List<Course> result = new ArrayList<>();
            for (Course c : courses.values()) {
                if (c.getCurrentEnrollment() >= c.getMaxCapacity()) {
                    result.add(c);
                }
            }
            return result;
        }

        @Override
        public int count() {
            return courses.size();
        }

        @Override
        public int calculateTotalEnrollment() {
            int total = 0;
            for (Course c : courses.values()) {
                total += c.getCurrentEnrollment();
            }
            return total;
        }

        @Override
        public double calculateAverageFillRate() {
            if (courses.isEmpty()) return 0.0;
            double totalRate = 0.0;
            for (Course c : courses.values()) {
                totalRate += ((double) c.getCurrentEnrollment() / c.getMaxCapacity()) * 100.0;
            }
            return totalRate / courses.size();
        }
    }
}