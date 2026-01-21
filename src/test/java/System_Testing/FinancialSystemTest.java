package System_Testing;

import edu.university.main.model.Student;
import org.junit.jupiter.api.Order;
import edu.university.main.service.GradeManagementService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class FinancialSystemTest extends BaseSystemTest{
    @Test
    @DisplayName("ST-004: Financial Aid Calculation Workflow")
    void testST004_FinancialAidCalculation() {
        System.out.println("\n ST-004: Financial Aid Calculation Workflow ");


        System.out.println(" Step 1: Financial tab displayed");


        Student student = new Student("S005", "Eglis Braho", "Eglis@edu.edu", 19, "Engineering");
        student.setGpa(3.8);
        student.setCreditHours(15);
        studentRepo.addStudent(student);
        System.out.println(" Step 3: Student S005 created with GPA 3.8, 15 credits");


        double tuition = student.calculateTuitionFees(student.getCreditHours(), student.isInternational(), false);
        assertEquals(13250.0, tuition, 0.01, "Tuition should display $13250.00");
        System.out.println(" Step 5: Base tuition calculated: $" + String.format("%.2f", tuition));


        double scholarship = financialService.calculateScholarshipAmount(student.getGpa(), student.getCreditHours(), tuition);
        assertEquals(9937.5, scholarship, 0.01, "Scholarship should display $9937.5");
        System.out.println(" Step 9: Scholarship calculated: $" + String.format("%.2f", scholarship));


        int familyIncome = 45000;
        boolean isDependent = true;
        double loan = financialService.calculateLoanEligibility(familyIncome, student.getCreditHours(), isDependent);
        assertEquals(4500.00, loan, 0.01, "Loan should display $4,500.00");
        System.out.println(" Step 12: Loan eligibility calculated: $" + String.format("%.2f", loan));


        double outOfPocket = tuition - scholarship - loan;
        assertTrue(outOfPocket <= 0, "Student should have full coverage + extra");
        System.out.println(" Step 13: Out-of-pocket: $" + String.format("%.2f", Math.max(0, outOfPocket)));


        boolean feeWaiver = financialService.qualifiesForWaiver(student.getGpa(), familyIncome, student.getCreditHours());
        assertFalse(feeWaiver, "Student should not qualify with income $45k");
        System.out.println(" Step 14: Fee waiver checked (not qualified due to income)");


        double lowScholarship = financialService.calculateScholarshipAmount(2.5, student.getCreditHours(), tuition);
        assertEquals(0.00, lowScholarship, 0.01, "Scholarship should display $0.00");
        System.out.println(" Step 16: Scholarship with GPA 2.5: $" + String.format("%.2f", lowScholarship));

        System.out.println(" ST-004: PASSED - Financial aid calculation completed\n");
    }

}
