
package edu.university.main.service;

import edu.university.main.model.Student;
import edu.university.main.model.Course;
import edu.university.main.repository.StudentRepository;
import edu.university.main.repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnrollmentService {
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    public EnrollmentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public boolean enrollStudent(String studentId, String courseId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Course> courseOpt = courseRepository.findById(courseId);

        if (!studentOpt.isPresent() || !courseOpt.isPresent()) {
            return false;
        }

        Student student = studentOpt.get();
        Course course = courseOpt.get();

        if (course.getCurrentEnrollment() >= course.getMaxCapacity()) {
            return false;
        }

        if (!student.canEnroll(student.getCreditHours(), course.getCredits(), student.getGpa())) {
            return false;
        }

        course.setCurrentEnrollment(course.getCurrentEnrollment() + 1);
        student.setCreditHours(student.getCreditHours() + course.getCredits());
        student.getEnrolledCourseIds().add(courseId);
        course.getEnrolledStudentIds().add(studentId);

        studentRepository.save(student);
        courseRepository.save(course);

        return true;
    }

    public boolean dropCourse(String studentId, String courseId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Course> courseOpt = courseRepository.findById(courseId);

        if (!studentOpt.isPresent() || !courseOpt.isPresent()) {
            return false;
        }

        Student student = studentOpt.get();
        Course course = courseOpt.get();

        if (!student.getEnrolledCourseIds().contains(courseId)) {
            return false;
        }

        course.setCurrentEnrollment(course.getCurrentEnrollment() - 1);
        student.setCreditHours(student.getCreditHours() - course.getCredits());
        student.getEnrolledCourseIds().remove(courseId);
        course.getEnrolledStudentIds().remove(studentId);

        studentRepository.save(student);
        courseRepository.save(course);

        return true;
    }

    public double calculateEnrollmentRate(int enrolled, int totalCapacity) {
        if (totalCapacity <= 0 || enrolled < 0) {
            return -1.0;
        }

        if (enrolled > totalCapacity) {
            return -1.0;
        }

        double rate = ((double) enrolled / totalCapacity) * 100.0;
        return Math.round(rate * 100.0) / 100.0;
    }

    public int predictEnrollment(int lastYearEnrollment, double growthRate) {
        if (lastYearEnrollment < 0 || growthRate < -100.0 || growthRate > 200.0) {
            return -1;
        }

        double predicted = lastYearEnrollment * (1 + (growthRate / 100.0));
        return (int) Math.round(predicted);
    }

    public int calculateWaitlistPosition(int currentEnrollment, int maxCapacity, int totalRequests) {
        if (currentEnrollment < 0 || maxCapacity <= 0 || totalRequests < 0) {
            return -1;
        }

        if (currentEnrollment < maxCapacity) {
            return 0;
        }

        int waitlistSize = totalRequests - maxCapacity;
        return Math.max(waitlistSize, 0);
    }

    public boolean checkPrerequisites(double gpa, int completedCredits, boolean hasPrereqCourse) {
        if (gpa < 0.0 || gpa > 4.0 || completedCredits < 0) {
            return false;
        }

        if (!hasPrereqCourse) {
            return false;
        }

        if (gpa < 2.0 || completedCredits < 30) {
            return false;
        }

        return true;
    }

    public double calculateRetentionRate(int returningStudents, int totalStudents) {
        if (totalStudents <= 0 || returningStudents < 0) {
            return -1.0;
        }

        if (returningStudents > totalStudents) {
            return -1.0;
        }

        double rate = ((double) returningStudents / totalStudents) * 100.0;
        return Math.round(rate * 100.0) / 100.0;
    }

    public void addStudent(Student student) {
        if (student != null) {
            studentRepository.save(student);
        }
    }

    public void addCourse(Course course) {
        if (course != null) {
            courseRepository.save(course);
        }
    }

    public StudentRepository getStudentRepository() { return studentRepository; }

    // CRUD for Students
    public boolean createStudent(Student student) {
        if (student == null) {
            return false;
        }
        return studentRepository.save(student);
    }

    public Optional<Student> getStudentById(String studentId) {
        return studentRepository.findById(studentId);
    }

    public boolean updateStudent(Student student) {
        if (student == null) {
            return false;
        }
        return studentRepository.update(student);
    }

    public boolean deleteStudent(String studentId) {
        // First, unenroll from all courses
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            List<String> enrolledCourses = new ArrayList<>(student.getEnrolledCourseIds());

            for (String courseId : enrolledCourses) {
                dropCourse(studentId, courseId);
            }
        }

        return studentRepository.delete(studentId);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(studentRepository.findAll());
    }

    public List<Student> searchStudents(String namePattern) {
        return studentRepository.searchByName(namePattern);
    }

    // CRUD for Courses
    public boolean createCourse(Course course) {
        if (course == null) {
            return false;
        }
        return courseRepository.save(course);
    }

    public Optional<Course> getCourseById(String courseId) {
        return courseRepository.findById(courseId);
    }

    public boolean updateCourse(Course course) {
        if (course == null) {
            return false;
        }
        return courseRepository.update(course);
    }

    public boolean deleteCourse(String courseId) {
        // First, drop all students from course
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            List<String> enrolledStudents = new ArrayList<>(course.getEnrolledStudentIds());

            for (String studentId : enrolledStudents) {
                dropCourse(studentId, courseId);
            }
        }

        return courseRepository.delete(courseId);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courseRepository.findAll());
    }

    public List<Course> searchCourses(String namePattern) {
        return courseRepository.searchByName(namePattern);
    }

    public CourseRepository getCourseRepository() { return courseRepository; }
}

