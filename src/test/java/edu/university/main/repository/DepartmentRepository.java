package edu.university.main.repository;



import edu.university.main.model.Department;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DepartmentRepository {
    private Map<String, Department> departments;
    private int totalDepartmentsCreated;

    public DepartmentRepository() {
        this.departments = new HashMap<>();
        this.totalDepartmentsCreated = 0;
    }

    public boolean save(Department department) {
        if (department == null || department.getDepartmentId() == null || department.getDepartmentId().isEmpty()) {
            return false;
        }

        boolean isNew = !departments.containsKey(department.getDepartmentId());
        departments.put(department.getDepartmentId(), department);

        if (isNew) {
            totalDepartmentsCreated++;
        }

        return true;
    }

    public Optional<Department> findById(String departmentId) {
        if (departmentId == null || departmentId.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(departments.get(departmentId));
    }

    public Collection<Department> findAll() {
        return new ArrayList<>(departments.values());
    }

    public List<Department> findBySize(String size) {
        if (size == null || size.isEmpty()) {
            return new ArrayList<>();
        }

        return departments.values().stream()
                .filter(d -> size.equals(d.determineDepartmentSize()))
                .collect(Collectors.toList());
    }

    public List<Department> findByBudgetRange(double minBudget, double maxBudget) {
        if (minBudget < 0 || maxBudget < minBudget) {
            return new ArrayList<>();
        }

        return departments.values().stream()
                .filter(d -> d.getBudget() >= minBudget && d.getBudget() <= maxBudget)
                .collect(Collectors.toList());
    }

    public boolean delete(String departmentId) {
        if (departmentId == null || departmentId.isEmpty()) {
            return false;
        }
        return departments.remove(departmentId) != null;
    }

    public boolean exists(String departmentId) {
        if (departmentId == null || departmentId.isEmpty()) {
            return false;
        }
        return departments.containsKey(departmentId);
    }

    public int count() {
        return departments.size();
    }

    public int getTotalDepartmentsCreated() {
        return totalDepartmentsCreated;
    }

    public double calculateTotalBudget() {
        return departments.values().stream()
                .mapToDouble(Department::getBudget)
                .sum();
    }

    public int calculateTotalStudents() {
        return departments.values().stream()
                .mapToInt(Department::getNumberOfStudents)
                .sum();
    }

    public int calculateTotalFaculty() {
        return departments.values().stream()
                .mapToInt(Department::getNumberOfFaculty)
                .sum();
    }

    public void clear() {
        departments.clear();
    }

    public boolean update(Department department) {
        if (department == null || department.getDepartmentId() == null || department.getDepartmentId().isEmpty()) {
            return false;
        }

        if (!departments.containsKey(department.getDepartmentId())) {
            return false;
        }

        departments.put(department.getDepartmentId(), department);
        return true;
    }

    public boolean deleteById(String departmentId) {
        return delete(departmentId);
    }

    public List<Department> searchByName(String namePattern) {
        if (namePattern == null || namePattern.isEmpty()) {
            return new ArrayList<>();
        }

        return departments.values().stream()
                .filter(d -> d.getDepartmentName().toLowerCase().contains(namePattern.toLowerCase()))
                .collect(Collectors.toList());
    }
}
