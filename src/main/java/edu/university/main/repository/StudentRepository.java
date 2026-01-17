package edu.university.main.repository;



import edu.university.main.model.Student;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class StudentRepository {
    private Map<String, Student> students;
    private int totalStudentsCreated;

    public StudentRepository() {
        this.students = new HashMap<>();
        this.totalStudentsCreated = 0;
    }

    public boolean save(Student student) {
        if (student == null || student.getStudentId() == null || student.getStudentId().isEmpty()) {
            return false;
        }

        boolean isNew = !students.containsKey(student.getStudentId());
        students.put(student.getStudentId(), student);

        if (isNew) {
            totalStudentsCreated++;
        }

        return true;
    }

    public Optional<Student> findById(String studentId) {
        if (studentId == null || studentId.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(students.get(studentId));
    }

    public Collection<Student> findAll() {
        return new ArrayList<>(students.values());
    }

    public List<Student> findByMajor(String major) {
        if (major == null || major.isEmpty()) {
            return new ArrayList<>();
        }

        return students.values().stream()
                .filter(s -> major.equals(s.getMajor()))
                .collect(Collectors.toList());
    }

    public List<Student> findByGpaRange(double minGpa, double maxGpa) {
        if (minGpa < 0.0 || maxGpa > 4.0 || minGpa > maxGpa) {
            return new ArrayList<>();
        }

        return students.values().stream()
                .filter(s -> s.getGpa() >= minGpa && s.getGpa() <= maxGpa)
                .collect(Collectors.toList());
    }

    public List<Student> findByAcademicLevel(String level) {
        if (level == null || level.isEmpty()) {
            return new ArrayList<>();
        }

        return students.values().stream()
                .filter(s -> level.equals(s.getAcademicLevel()))
                .collect(Collectors.toList());
    }

    public boolean delete(String studentId) {
        if (studentId == null || studentId.isEmpty()) {
            return false;
        }
        return students.remove(studentId) != null;
    }

    public boolean exists(String studentId) {
        if (studentId == null || studentId.isEmpty()) {
            return false;
        }
        return students.containsKey(studentId);
    }

    public int count() {
        return students.size();
    }

    public int getTotalStudentsCreated() {
        return totalStudentsCreated;
    }

    public double calculateAverageGpa() {
        if (students.isEmpty()) {
            return 0.0;
        }

        double sum = students.values().stream()
                .mapToDouble(Student::getGpa)
                .sum();

        double average = sum / students.size();
        return Math.round(average * 100.0) / 100.0;
    }

    public void clear() {
        students.clear();
    }
    public boolean update(Student student) {
        if (student == null || student.getStudentId() == null || student.getStudentId().isEmpty()) {
            return false;
        }

        if (!students.containsKey(student.getStudentId())) {
            return false; // Cannot update non-existent student
        }

        students.put(student.getStudentId(), student);
        return true;
    }

    public boolean deleteById(String studentId) {
        return delete(studentId);
    }

    public List<Student> searchByName(String namePattern) {
        if (namePattern == null || namePattern.isEmpty()) {
            return new ArrayList<>();
        }

        return students.values().stream()
                .filter(s -> s.getName().toLowerCase().contains(namePattern.toLowerCase()))
                .collect(Collectors.toList());
    }
}
