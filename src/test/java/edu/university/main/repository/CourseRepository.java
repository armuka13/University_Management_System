package edu.university.main.repository;



import edu.university.main.model.Course;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CourseRepository {
    private Map<String, Course> courses;
    private int totalCoursesCreated;

    public CourseRepository() {
        this.courses = new HashMap<>();
        this.totalCoursesCreated = 0;
    }

    public boolean save(Course course) {
        if (course == null || course.getCourseId() == null || course.getCourseId().isEmpty()) {
            return false;
        }

        boolean isNew = !courses.containsKey(course.getCourseId());
        courses.put(course.getCourseId(), course);

        if (isNew) {
            totalCoursesCreated++;
        }

        return true;
    }

    public Optional<Course> findById(String courseId) {
        if (courseId == null || courseId.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(courses.get(courseId));
    }

    public Collection<Course> findAll() {
        return new ArrayList<>(courses.values());
    }

    public List<Course> findByDepartment(String department) {
        if (department == null || department.isEmpty()) {
            return new ArrayList<>();
        }

        return courses.values().stream()
                .filter(c -> department.equals(c.getDepartment()))
                .collect(Collectors.toList());
    }

    public List<Course> findByInstructor(String instructor) {
        if (instructor == null || instructor.isEmpty()) {
            return new ArrayList<>();
        }

        return courses.values().stream()
                .filter(c -> instructor.equals(c.getInstructor()))
                .collect(Collectors.toList());
    }

    public List<Course> findByCredits(int credits) {
        if (credits < 0) {
            return new ArrayList<>();
        }

        return courses.values().stream()
                .filter(c -> c.getCredits() == credits)
                .collect(Collectors.toList());
    }

    public List<Course> findAvailableCourses() {
        return courses.values().stream()
                .filter(c -> c.getCurrentEnrollment() < c.getMaxCapacity())
                .collect(Collectors.toList());
    }

    public List<Course> findFullCourses() {
        return courses.values().stream()
                .filter(c -> c.getCurrentEnrollment() >= c.getMaxCapacity())
                .collect(Collectors.toList());
    }

    public boolean delete(String courseId) {
        if (courseId == null || courseId.isEmpty()) {
            return false;
        }
        return courses.remove(courseId) != null;
    }

    public boolean exists(String courseId) {
        if (courseId == null || courseId.isEmpty()) {
            return false;
        }
        return courses.containsKey(courseId);
    }

    public int count() {
        return courses.size();
    }

    public int getTotalCoursesCreated() {
        return totalCoursesCreated;
    }

    public int calculateTotalEnrollment() {
        return courses.values().stream()
                .mapToInt(Course::getCurrentEnrollment)
                .sum();
    }

    public double calculateAverageFillRate() {
        if (courses.isEmpty()) {
            return 0.0;
        }

        double sum = courses.values().stream()
                .mapToDouble(Course::calculateFillRate)
                .filter(rate -> rate >= 0)
                .sum();

        double average = sum / courses.size();
        return Math.round(average * 100.0) / 100.0;
    }

    public void clear() {
        courses.clear();
    }
    public boolean update(Course course) {
        if (course == null || course.getCourseId() == null || course.getCourseId().isEmpty()) {
            return false;
        }

        if (!courses.containsKey(course.getCourseId())) {
            return false;
        }

        courses.put(course.getCourseId(), course);
        return true;
    }

    public boolean deleteById(String courseId) {
        return delete(courseId);
    }

    public List<Course> searchByName(String namePattern) {
        if (namePattern == null || namePattern.isEmpty()) {
            return new ArrayList<>();
        }

        return courses.values().stream()
                .filter(c -> c.getCourseName().toLowerCase().contains(namePattern.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Course findCourseById(String id) {
        return findById(id).orElse(null);
    }
    public int getCourseCount() {
        return count();
    }
    public void addCourse(Course c) {
        save(c);
    }
}
