package edu.university.main.service;

import edu.university.main.model.Grade;
import edu.university.main.repository.GradeRepository;
import java.util.List;

public class GradeManagementService {
    private GradeRepository gradeRepository;

    public GradeManagementService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    // CREATE
    public boolean createGrade(String studentId, String courseId, double numericGrade, int semester, int year) {
        if (studentId == null || courseId == null || numericGrade < 0 || numericGrade > 100) {
            return false;
        }

        Grade existingGrade = gradeRepository.findByStudentAndCourse(studentId, courseId);
        if (existingGrade != null) {
            return false; // Grade already exists
        }

        Grade grade = new Grade(studentId, courseId, numericGrade, semester, year);
        return gradeRepository.save(grade);
    }

    // READ
    public Grade getGrade(String studentId, String courseId) {
        return gradeRepository.findByStudentAndCourse(studentId, courseId);
    }

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public List<Grade> getGradesByStudent(String studentId) {
        return gradeRepository.findByStudent(studentId);
    }

    public List<Grade> getGradesByCourse(String courseId) {
        return gradeRepository.findByCourse(courseId);
    }

    public List<Grade> getGradesByLetterGrade(String letterGrade) {
        return gradeRepository.findByLetterGrade(letterGrade);
    }

    public List<Grade> getGradesBySemester(int semester, int year) {
        return gradeRepository.findBySemester(semester, year);
    }

    // UPDATE
    public boolean updateGrade(Grade grade) {
        if (grade == null) {
            return false;
        }

        Grade existing = gradeRepository.findByStudentAndCourse(grade.getStudentId(), grade.getCourseId());
        if (existing == null) {
            return false;
        }

        return gradeRepository.update(grade);
    }

    public boolean updateGradeScore(String studentId, String courseId, double newScore) {
        if (newScore < 0 || newScore > 100) {
            return false;
        }

        Grade grade = gradeRepository.findByStudentAndCourse(studentId, courseId);
        if (grade == null) {
            return false;
        }

        grade.setNumericGrade(newScore);
        return gradeRepository.update(grade);
    }

    // DELETE
    public boolean deleteGrade(String studentId, String courseId) {
        return gradeRepository.delete(studentId, courseId);
    }

    public int deleteGradesByStudent(String studentId) {
        List<Grade> grades = gradeRepository.findByStudent(studentId);
        int deletedCount = 0;

        for (Grade grade : grades) {
            if (gradeRepository.delete(grade.getStudentId(), grade.getCourseId())) {
                deletedCount++;
            }
        }

        return deletedCount;
    }

    public int deleteGradesByCourse(String courseId) {
        List<Grade> grades = gradeRepository.findByCourse(courseId);
        int deletedCount = 0;

        for (Grade grade : grades) {
            if (gradeRepository.delete(grade.getStudentId(), grade.getCourseId())) {
                deletedCount++;
            }
        }

        return deletedCount;
    }

    // STATISTICS
    public int getTotalGrades() {
        return gradeRepository.count();
    }

    public double getAverageGrade() {
        return gradeRepository.calculateAverageGrade();
    }

    public int countGradesByLetter(String letterGrade) {
        return gradeRepository.countByLetterGrade(letterGrade);
    }

    public boolean hasGrades() {
        return gradeRepository.count() > 0;
    }
}