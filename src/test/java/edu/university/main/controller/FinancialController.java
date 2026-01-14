package edu.university.main.controller;



import edu.university.main.service.FinancialService;
import edu.university.main.view.FinancialView;

public class FinancialController {
    private FinancialService financialService;
    private FinancialView view;

    public FinancialController(FinancialService financialService, FinancialView view) {
        this.financialService = financialService;
        this.view = view;
    }

    public void calculateScholarship(double gpa, int credits, double baseTuition) {
        double amount = financialService.calculateScholarshipAmount(gpa, credits, baseTuition);
        view.displayScholarshipAmount(amount);
    }

    public void calculateLoan(double income, int credits, boolean dependent) {
        double eligibility = financialService.calculateLoanEligibility(income, credits, dependent);
        view.displayLoanEligibility(eligibility);
    }

    public void calculatePayment(double amount, int months, double rate) {
        double monthly = financialService.calculatePaymentPlan(amount, months, rate);
        view.displayMonthlyPayment(monthly);
    }

    public void applyLate(double base, int days) {
        double total = financialService.applyLateFee(base, days);
        view.displayTotalWithFee(total);
    }
}
