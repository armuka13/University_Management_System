package edu.university.main.service;

import edu.university.main.model.Student;
import edu.university.main.repository.StudentRepository;
import java.util.List;
import java.util.Optional;

public class StudentManagementService {
    private StudentRepository studentRepository;

    public StudentManagementService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // CREATE
    public boolean createStudent(String id, String name, String email, int age, String major) {
        if (id == null || id.isEmpty() || name == null || name.isEmpty()) {
            return false;
        }

        if (studentRepository.exists(id)) {
            return false; // Student already exists
        }

        Student student = new Student(id, name, email, age, major);
        return studentRepository.save(student);
    }

    // READ
    public Optional<Student> getStudent(String studentId) {
        return studentRepository.findById(studentId);
    }

    public List<Student> getAllStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    public List<Student> getStudentsByMajor(String major) {
        return studentRepository.findByMajor(major);
    }

    public List<Student> searchStudentsByName(String namePattern) {
        return studentRepository.searchByName(namePattern);
    }

    // UPDATE
    public boolean updateStudent(Student student) {
        if (student == null || !studentRepository.exists(student.getStudentId())) {
            return false;
        }
        return studentRepository.update(student);
    }

    public boolean updateStudentEmail(String studentId, String newEmail) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (!studentOpt.isPresent()) {
            return false;
        }

        Student student = studentOpt.get();
        student.setEmail(newEmail);
        return studentRepository.update(student);
    }

    public boolean updateStudentGPA(String studentId, double newGPA) {
        if (newGPA < 0.0 || newGPA > 4.0) {
            return false;
        }

        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (!studentOpt.isPresent()) {
            return false;
        }

        Student student = studentOpt.get();
        student.setGpa(newGPA);
        return studentRepository.update(student);
    }

    // DELETE
    public boolean deleteStudent(String studentId) {
        return studentRepository.delete(studentId);
    }

    public int deleteStudentsByMajor(String major) {
        List<Student> students = studentRepository.findByMajor(major);
        int deletedCount = 0;

        for (Student student : students) {
            if (studentRepository.delete(student.getStudentId())) {
                deletedCount++;
            }
        }

        return deletedCount;
    }

    // STATISTICS
    public int getTotalStudents() {
        return studentRepository.count();
    }

    public double getAverageGPA() {
        return studentRepository.calculateAverageGpa();
    }

    public boolean hasStudents() {
        return studentRepository.count() > 0;
    }
}