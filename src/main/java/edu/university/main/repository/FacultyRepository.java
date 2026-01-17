package edu.university.main.repository;



import edu.university.main.model.Faculty;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class FacultyRepository {
    private Map<String, Faculty> facultyMembers;
    private int totalFacultyCreated;

    public FacultyRepository() {
        this.facultyMembers = new HashMap<>();
        this.totalFacultyCreated = 0;
    }

    public boolean save(Faculty faculty) {
        if (faculty == null || faculty.getFacultyId() == null || faculty.getFacultyId().isEmpty()) {
            return false;
        }

        boolean isNew = !facultyMembers.containsKey(faculty.getFacultyId());
        facultyMembers.put(faculty.getFacultyId(), faculty);

        if (isNew) {
            totalFacultyCreated++;
        }

        return true;
    }

    public Optional<Faculty> findById(String facultyId) {
        if (facultyId == null || facultyId.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(facultyMembers.get(facultyId));
    }

    public Collection<Faculty> findAll() {
        return new ArrayList<>(facultyMembers.values());
    }

    public List<Faculty> findByDepartment(String department) {
        if (department == null || department.isEmpty()) {
            return new ArrayList<>();
        }

        return facultyMembers.values().stream()
                .filter(f -> department.equals(f.getDepartment()))
                .collect(Collectors.toList());
    }

    public List<Faculty> findByRank(String rank) {
        if (rank == null || rank.isEmpty()) {
            return new ArrayList<>();
        }

        return facultyMembers.values().stream()
                .filter(f -> rank.equals(f.getRank()))
                .collect(Collectors.toList());
    }

    public List<Faculty> findBySalaryRange(double minSalary, double maxSalary) {
        if (minSalary < 0 || maxSalary < minSalary) {
            return new ArrayList<>();
        }

        return facultyMembers.values().stream()
                .filter(f -> f.getSalary() >= minSalary && f.getSalary() <= maxSalary)
                .collect(Collectors.toList());
    }

    public boolean delete(String facultyId) {
        if (facultyId == null || facultyId.isEmpty()) {
            return false;
        }
        return facultyMembers.remove(facultyId) != null;
    }

    public boolean exists(String facultyId) {
        if (facultyId == null || facultyId.isEmpty()) {
            return false;
        }
        return facultyMembers.containsKey(facultyId);
    }

    public int count() {
        return facultyMembers.size();
    }

    public int getTotalFacultyCreated() {
        return totalFacultyCreated;
    }

    public double calculateAverageSalary() {
        if (facultyMembers.isEmpty()) {
            return 0.0;
        }

        double sum = facultyMembers.values().stream()
                .mapToDouble(Faculty::getSalary)
                .sum();

        double average = sum / facultyMembers.size();
        return Math.round(average * 100.0) / 100.0;
    }

    public int countByDepartment(String department) {
        if (department == null || department.isEmpty()) {
            return 0;
        }

        return (int) facultyMembers.values().stream()
                .filter(f -> department.equals(f.getDepartment()))
                .count();
    }

    public void clear() {
        facultyMembers.clear();
    }

    public boolean update(Faculty faculty) {
        if (faculty == null || faculty.getFacultyId() == null || faculty.getFacultyId().isEmpty()) {
            return false;
        }

        if (!facultyMembers.containsKey(faculty.getFacultyId())) {
            return false;
        }

        facultyMembers.put(faculty.getFacultyId(), faculty);
        return true;
    }

    public boolean deleteById(String facultyId) {
        return delete(facultyId);
    }

    public List<Faculty> searchByName(String namePattern) {
        if (namePattern == null || namePattern.isEmpty()) {
            return new ArrayList<>();
        }

        return facultyMembers.values().stream()
                .filter(f -> f.getName().toLowerCase().contains(namePattern.toLowerCase()))
                .collect(Collectors.toList());
    }
}
