package Tests.Integration_Testing;

import edu.university.main.controller.FinancialController;
import edu.university.main.service.FinancialService;
import edu.university.main.view.FinancialView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FinancialControllerIntegrationTest {

    @Test
    void testCalculateScholarshipHighGPA() {
        FinancialService service = new FinancialService();
        SpyFinancialView view = new SpyFinancialView();
        FinancialController controller = new FinancialController(service, view);

        controller.calculateScholarship(3.8, 15, 5000.0);

        view.verifyScholarship(view.lastScholarship > 0);
    }

    @Test
    void testCalculateLoanEligibility() {
        FinancialService service = new FinancialService();
        SpyFinancialView view = new SpyFinancialView();
        FinancialController controller = new FinancialController(service, view);

        controller.calculateLoan(30000, 15, true);

        view.verifyLoan(view.lastLoan > 0);
    }

    @Test
    void testCalculateMonthlyPayment() {
        FinancialService service = new FinancialService();
        SpyFinancialView view = new SpyFinancialView();
        FinancialController controller = new FinancialController(service, view);

        controller.calculatePayment(12000, 24, 0.05);

        view.verifyMonthlyPayment(view.lastPayment > 0);
    }

    @Test
    void testApplyLateFee() {
        FinancialService service = new FinancialService();
        SpyFinancialView view = new SpyFinancialView();
        FinancialController controller = new FinancialController(service, view);

        controller.applyLate(1000, 10);

        view.verifyTotalWithFee(view.lastTotal > 1000);
    }

    // ===== SPY VIEW =====
    private static class SpyFinancialView extends FinancialView {
        double lastScholarship;
        double lastLoan;
        double lastPayment;
        double lastTotal;

        @Override
        public void displayScholarshipAmount(double amount) {
            lastScholarship = amount;
        }

        @Override
        public void displayLoanEligibility(double amount) {
            lastLoan = amount;
        }

        @Override
        public void displayMonthlyPayment(double payment) {
            lastPayment = payment;
        }

        @Override
        public void displayTotalWithFee(double total) {
            lastTotal = total;
        }

        void verifyScholarship(boolean condition) {
            assertTrue(condition);
        }

        void verifyLoan(boolean condition) {
            assertTrue(condition);
        }

        void verifyMonthlyPayment(boolean condition) {
            assertTrue(condition);
        }

        void verifyTotalWithFee(boolean condition) {
            assertTrue(condition);
        }
    }
}
