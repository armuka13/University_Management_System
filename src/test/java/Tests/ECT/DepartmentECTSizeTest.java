package Tests.ECT;

import edu.university.main.model.Department;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentECTSizeTest {

    Department department=new Department("CS", "Computer Science", "Dr. Eni", 500000.0);

    @Test
    public void testInvalidFaculty() {
        department.setNumberOfFaculty(-1);
        department.setNumberOfStudents(50);

        assertEquals("INVALID", department.determineDepartmentSize());
    }

    @Test
    public void testInvalidStudents() {
        department.setNumberOfFaculty(10);
        department.setNumberOfStudents(-1);

        assertEquals("INVALID", department.determineDepartmentSize());
    }

    @Test
    public void testSmallDepartment() {
        department.setNumberOfFaculty(5);
        department.setNumberOfStudents(80);

        assertEquals("SMALL", department.determineDepartmentSize());
    }

    @Test
    public void testMediumDepartment() {
        department.setNumberOfFaculty(20);
        department.setNumberOfStudents(300);

        assertEquals("MEDIUM", department.determineDepartmentSize());
    }

    @Test
    public void testLargeDepartment() {
        department.setNumberOfFaculty(50);
        department.setNumberOfStudents(800);

        assertEquals("LARGE", department.determineDepartmentSize());
    }

    @Test
    public void testVeryLargeDepartment() {
        department.setNumberOfFaculty(70);
        department.setNumberOfStudents(1200);

        assertEquals("VERY_LARGE", department.determineDepartmentSize());
    }

}