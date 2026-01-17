package edu.university.main.repository;



import edu.university.main.model.Grade;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GradeRepository {
    private Map<String, Grade> grades;
    private int totalGradesRecorded;

    public GradeRepository() {
        this.grades = new HashMap<>();
        this.totalGradesRecorded = 0;
    }

    private String generateKey(String studentId, String courseId) {
        return studentId + "_" + courseId;
    }

    public boolean save(Grade grade) {
        if (grade == null || grade.getStudentId() == null || grade.getCourseId() == null) {
            return false;
        }

        String key = generateKey(grade.getStudentId(), grade.getCourseId());
        boolean isNew = !grades.containsKey(key);
        grades.put(key, grade);

        if (isNew) {
            totalGradesRecorded++;
        }

        return true;
    }

    public Grade findByStudentAndCourse(String studentId, String courseId) {
        if (studentId == null || courseId == null) {
            return null;
        }

        String key = generateKey(studentId, courseId);
        return grades.get(key);
    }

    public List<Grade> findByStudent(String studentId) {
        if (studentId == null || studentId.isEmpty()) {
            return new ArrayList<>();
        }

        return grades.values().stream()
                .filter(g -> studentId.equals(g.getStudentId()))
                .collect(Collectors.toList());
    }

    public List<Grade> findByCourse(String courseId) {
        if (courseId == null || courseId.isEmpty()) {
            return new ArrayList<>();
        }

        return grades.values().stream()
                .filter(g -> courseId.equals(g.getCourseId()))
                .collect(Collectors.toList());
    }

    public List<Grade> findByLetterGrade(String letterGrade) {
        if (letterGrade == null || letterGrade.isEmpty()) {
            return new ArrayList<>();
        }

        return grades.values().stream()
                .filter(g -> letterGrade.equals(g.getLetterGrade()))
                .collect(Collectors.toList());
    }

    public List<Grade> findBySemester(int semester, int year) {
        if (semester < 1 || semester > 3 || year < 2000 || year > 2100) {
            return new ArrayList<>();
        }

        return grades.values().stream()
                .filter(g -> g.getSemester() == semester && g.getYear() == year)
                .collect(Collectors.toList());
    }

    public List<Grade> findAll() {
        return new ArrayList<>(grades.values());
    }

    public boolean delete(String studentId, String courseId) {
        if (studentId == null || courseId == null) {
            return false;
        }

        String key = generateKey(studentId, courseId);
        return grades.remove(key) != null;
    }

    public int count() {
        return grades.size();
    }

    public int getTotalGradesRecorded() {
        return totalGradesRecorded;
    }

    public double calculateAverageGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }

        double sum = grades.values().stream()
                .mapToDouble(Grade::getNumericGrade)
                .sum();

        double average = sum / grades.size();
        return Math.round(average * 100.0) / 100.0;
    }

    public int countByLetterGrade(String letterGrade) {
        if (letterGrade == null || letterGrade.isEmpty()) {
            return 0;
        }

        return (int) grades.values().stream()
                .filter(g -> letterGrade.equals(g.getLetterGrade()))
                .count();
    }

    public void clear() {
        grades.clear();
    }
    public boolean update(Grade grade) {
        if (grade == null || grade.getStudentId() == null || grade.getCourseId() == null) {
            return false;
        }

        String key = generateKey(grade.getStudentId(), grade.getCourseId());

        if (!grades.containsKey(key)) {
            return false;
        }

        grades.put(key, grade);
        return true;
    }

    public boolean deleteById(String studentId, String courseId) {
        return delete(studentId, courseId);
    }

    public List<Grade> findByScoreRange(double minScore, double maxScore) {
        if (minScore < 0 || maxScore > 100 || minScore > maxScore) {
            return new ArrayList<>();
        }

        return grades.values().stream()
                .filter(g -> g.getNumericGrade() >= minScore && g.getNumericGrade() <= maxScore)
                .collect(Collectors.toList());
    }
}
