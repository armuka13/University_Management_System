package Tests.Integration_Testing;
import edu.university.main.model.Course;
import edu.university.main.repository.CourseRepository;
import edu.university.main.service.CourseManagementService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class CourseManagementServiceIntegrationTest {

    @Test
    void testCreateCourseValidation() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        boolean created = courseManagementService.createCourse("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");

        assertTrue(created);
        assertTrue(courseRepository.exists("SWE303"));
    }

    @Test
    void testCreateCourseDuplicateFails() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        courseManagementService.createCourse("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        boolean created = courseManagementService.createCourse("SWE303", "Duplicate", "Dr. Skuka", 3, 30, "SWE");

        assertFalse(created);
    }

    @Test
    void testCreateCourseInvalidInput() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        boolean created = courseManagementService.createCourse("", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");

        assertFalse(created);
    }

    @Test
    void testUpdateCourseCapacity() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        courseManagementService.createCourse("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        boolean updated = courseManagementService.updateCourseCapacity("SWE303", 50);

        assertTrue(updated);
        assertEquals(50, courseManagementService.getCourse("SWE303").get().getMaxCapacity());
    }

    @Test
    void testUpdateCourseCapacityBelowEnrollmentFails() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        courseManagementService.createCourse("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        Course course = courseRepository.findById("SWE303").get();
        course.setCurrentEnrollment(40);
        courseRepository.update(course);

        boolean updated = courseManagementService.updateCourseCapacity("SWE303", 30);

        assertFalse(updated);
    }

    @Test
    void testUpdateCourseInstructor() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        courseManagementService.createCourse("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        boolean updated = courseManagementService.updateCourseInstructor("SWE303", "Dr. Skuka");

        assertTrue(updated);
        assertEquals("Dr. Skuka", courseManagementService.getCourse("SWE303").get().getInstructor());
    }

    @Test
    void testGetCoursesByDepartment() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        courseManagementService.createCourse("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        courseManagementService.createCourse("CS102", "Data Structures", "Dr. Skuka", 3, 25, "SWE");
        courseManagementService.createCourse("MATH101", "Calculus", "Dr. Bame", 3, 35, "MATH");

        List<Course> csCourses = courseManagementService.getCoursesByDepartment("SWE");

        assertEquals(2, csCourses.size());
        assertTrue(csCourses.stream().allMatch(c -> "SWE".equals(c.getDepartment())));
    }

    @Test
    void testDeleteCourse() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        courseManagementService.createCourse("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        boolean deleted = courseManagementService.deleteCourse("SWE303");

        assertTrue(deleted);
        assertFalse(courseRepository.exists("SWE303"));
    }

    @Test
    void testDeleteCoursesByDepartment() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        courseManagementService.createCourse("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        courseManagementService.createCourse("CS102", "Data Structures", "Dr. Skuka", 3, 25, "SWE");
        courseManagementService.createCourse("MATH101", "Calculus", "Dr. Bame", 3, 35, "MATH");

        int deleted = courseManagementService.deleteCoursesByDepartment("SWE");

        assertEquals(2, deleted);
        assertEquals(1, courseManagementService.getTotalCourses());
    }

    @Test
    void testGetCourseStatistics() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        courseManagementService.createCourse("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        courseManagementService.createCourse("CS102", "Data Structures", "Dr. Skuka", 3, 25, "SWE");

        assertEquals(2, courseManagementService.getTotalCourses());
        assertEquals(0, courseManagementService.getTotalEnrollment());
    }


}
