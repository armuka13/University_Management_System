package edu.university.main.repository.Stub;

import edu.university.main.repository.CourseRepository;
import edu.university.main.model.Course;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class CourseRepositoryStub extends CourseRepository {

    @Override
    public boolean save(Course course) {
        return true;
    }

    @Override
    public Optional<Course> findById(String courseId) {
        if ("SWE303".equals(courseId)) {
            Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
            return Optional.of(course);
        }
        return Optional.empty();
    }

    @Override
    public List<Course> findByDepartment(String department) {
        List<Course> courses = new ArrayList<>();
        if ("SWE".equals(department)) {
            Course course1 = new Course("SWE303", "Intro to CS", "Mr. Gjerazi", 3, 30, "SWE");
            Course course2 = new Course("CS201", "Data Structures", "Dr. Skuka", 3, 25, "SW");
            courses.add(course1);
            courses.add(course2);
        }
        return courses;
    }
}