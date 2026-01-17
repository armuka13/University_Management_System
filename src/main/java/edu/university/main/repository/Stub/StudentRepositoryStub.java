package edu.university.main.repository.Stub;

import edu.university.main.model.Student;
import edu.university.main.repository.StudentRepository;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class StudentRepositoryStub extends StudentRepository {

    @Override
    public boolean save(Student student) {
        return true;
    }

    @Override
    public Optional<Student> findById(String studentId) {
        if ("S001".equals(studentId)) {
            Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
            student.setGpa(3.5);
            student.setCreditHours(12);
            return Optional.of(student);
        }
        return Optional.empty();
    }

    @Override
    public List<Student> findByGpaRange(double minGpa, double maxGpa) {
        List<Student> students = new ArrayList<>();
        if (minGpa <= 3.5 && maxGpa >= 3.5) {
            Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
            student.setGpa(3.5);
            students.add(student);
        }
        return students;
    }

    @Override
    public boolean update(Student student) {
        return true;
    }

    @Override
    public boolean delete(String studentId) {
        return true;
    }
}