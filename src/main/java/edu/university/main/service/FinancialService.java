package edu.university.main.service;



public class FinancialService {

    public double calculateScholarshipAmount(double gpa, int creditHours, double baseTuition) {
        if (gpa < 0.0 || gpa > 4.0 || creditHours < 0 || baseTuition < 0) {
            return -1.0;
        }

        double scholarshipPercentage = 0.0;

        if (gpa >= 3.9 && creditHours >= 15) {
            scholarshipPercentage = 1.0;
        } else if (gpa >= 3.7 && creditHours >= 15) {
            scholarshipPercentage = 0.75;
        } else if (gpa >= 3.5 && creditHours >= 12) {
            scholarshipPercentage = 0.50;
        } else if (gpa >= 3.0 && creditHours >= 12) {
            scholarshipPercentage = 0.25;
        }

        double scholarshipAmount = baseTuition * scholarshipPercentage;
        return Math.round(scholarshipAmount * 100.0) / 100.0;
    }

    public double calculateLoanEligibility(double familyIncome, int creditHours, boolean isDependent) {
        if (familyIncome < 0 || creditHours < 0) {
            return -1.0;
        }

        double maxLoan = 0.0;

        if (isDependent) {
            if (familyIncome < 30000 && creditHours >= 12) {
                maxLoan = 5500.0;
            } else if (familyIncome < 60000 && creditHours >= 12) {
                maxLoan = 4500.0;
            } else if (familyIncome < 100000 && creditHours >= 6) {
                maxLoan = 3500.0;
            }
        } else {
            if (familyIncome < 50000 && creditHours >= 12) {
                maxLoan = 12500.0;
            } else if (familyIncome < 100000 && creditHours >= 12) {
                maxLoan = 9500.0;
            } else if (creditHours >= 6) {
                maxLoan = 6500.0;
            }
        }

        return maxLoan;
    }

    public double calculatePaymentPlan(double totalAmount, int months, double interestRate) {
        if (totalAmount < 0 || months <= 0 || interestRate < 0 || interestRate > 100) {
            return -1.0;
        }

        if (interestRate == 0.0) {
            return Math.round((totalAmount / months) * 100.0) / 100.0;
        }

        double monthlyRate = (interestRate / 100.0) / 12.0;
        double monthlyPayment = totalAmount * (monthlyRate * Math.pow(1 + monthlyRate, months)) /
                (Math.pow(1 + monthlyRate, months) - 1);

        return Math.round(monthlyPayment * 100.0) / 100.0;
    }

    public double applyLateFee(double baseAmount, int daysLate) {
        if (baseAmount < 0 || daysLate < 0) {
            return -1.0;
        }

        double lateFee = 0.0;

        if (daysLate > 30) {
            lateFee = baseAmount * 0.10;
        } else if (daysLate > 15) {
            lateFee = baseAmount * 0.05;
        } else if (daysLate > 7) {
            lateFee = baseAmount * 0.02;
        } else if (daysLate > 0) {
            lateFee = 25.0;
        }

        double totalAmount = baseAmount + lateFee;
        return Math.round(totalAmount * 100.0) / 100.0;
    }

    public boolean qualifiesForWaiver(double gpa, double familyIncome, int creditHours) {
        if (gpa < 0.0 || gpa > 4.0 || familyIncome < 0 || creditHours < 0) {
            return false;
        }

        if (gpa >= 3.8 && familyIncome < 40000 && creditHours >= 12) {
            return true;
        }

        if (gpa >= 3.5 && familyIncome < 25000 && creditHours >= 15) {
            return true;
        }

        return false;
    }

    public double calculateWorkStudyAmount(int hoursPerWeek, double hourlyRate, int weeks) {
        if (hoursPerWeek < 0 || hoursPerWeek > 20 || hourlyRate < 0 || weeks < 0 || weeks > 52) {
            return -1.0;
        }

        double totalAmount = hoursPerWeek * hourlyRate * weeks;
        return Math.round(totalAmount * 100.0) / 100.0;
    }

    public double calculateFinancialAidPackage(double tuition, double scholarships, double grants, double loans, double workStudy) {
        if (tuition < 0 || scholarships < 0 || grants < 0 || loans < 0 || workStudy < 0) {
            return -1.0;
        }

        double totalAid = scholarships + grants + loans + workStudy;
        double outOfPocket = tuition - totalAid;

        if (outOfPocket < 0) {
            outOfPocket = 0.0;
        }

        return Math.round(outOfPocket * 100.0) / 100.0;
    }

    public double calculateRefundAmount(double tuition, int weeksCompleted, int totalWeeks) {
        if (tuition < 0 || weeksCompleted < 0 || totalWeeks <= 0 || weeksCompleted > totalWeeks) {
            return -1.0;
        }

        double percentComplete = ((double) weeksCompleted / totalWeeks) * 100.0;
        double refundPercent = 0.0;

        if (percentComplete <= 10.0) {
            refundPercent = 90.0;
        } else if (percentComplete <= 25.0) {
            refundPercent = 75.0;
        } else if (percentComplete <= 50.0) {
            refundPercent = 50.0;
        } else if (percentComplete <= 75.0) {
            refundPercent = 25.0;
        }

        double refundAmount = tuition * (refundPercent / 100.0);
        return Math.round(refundAmount * 100.0) / 100.0;
    }
}
