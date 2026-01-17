package edu.university.main.repository.Stub;

import edu.university.main.model.Grade;
import edu.university.main.repository.GradeRepository;

public class GradeRepositoryStub extends GradeRepository {

    @Override
    public boolean save(Grade grade) {
        return true;
    }

    @Override
    public Grade findByStudentAndCourse(String studentId, String courseId) {
        if ("S001".equals(studentId) && "SWE303".equals(courseId)) {
            return new Grade("S001", "SWE303", 85.5, 3, 2025);
        }
        return null;
    }
}