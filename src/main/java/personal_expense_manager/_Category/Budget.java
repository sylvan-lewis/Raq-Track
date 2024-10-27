package personal_expense_manager._Category;


import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Budget {
    private Float monthlyBudget;
    private Float yearlyBudget;
    private ReportService reportService = new ReportService();

    public Budget() {}

    // Method to set the monthly budget
    public void setMonthlyBudget(Float amount) {
        this.monthlyBudget = amount;
    }

    // Method to set the yearly budget
    public void setYearlyBudget(Float amount) {
        this.yearlyBudget = amount;
    }

    public void printMonthlyBudgetReport(int month, int year) {
        if (monthlyBudget == null || monthlyBudget == 0) {
            System.out.println("Monthly budget is not set.");
            return;
        }

        // Calculate total expenses for the specified month and year
        float totalMonthlyExpense = reportService.calculateMonthlyTotal().entrySet().stream()
                .filter(entry -> {
                    Date date = DateUtil.stringToDate(entry.getKey());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    return cal.get(Calendar.MONTH) == month && cal.get(Calendar.YEAR) == year;
                })
                .map(Map.Entry::getValue)
                .reduce(0f, Float::sum);

        float remainingMonthlyBudget = monthlyBudget - totalMonthlyExpense;

        System.out.println("Monthly Budget for " + DateUtil.getMonthName(month + 1) + ": " + monthlyBudget);
        System.out.println("Total Expense for " + DateUtil.getMonthName(month + 1) + ": " + totalMonthlyExpense);
        System.out.println("Remaining Monthly Budget: " + remainingMonthlyBudget);

        if (remainingMonthlyBudget < 0) {
            System.out.println("Warning: You have exceeded your monthly budget by " + Math.abs(remainingMonthlyBudget));
        }
    }


    // Method to print the yearly budget report for a specified year
    public void printYearlyBudgetReport(int year) {
        if (yearlyBudget == null || yearlyBudget == 0) {
            System.out.println("Yearly budget is not set.");
            return;
        }

        // Filter and calculate total expenses for the specified year only
        float totalYearlyExpense = reportService.calculateYearlyTotal().entrySet().stream()
                .filter(entry -> entry.getKey() == year)  // Filter expenses by the specified year
                .map(Map.Entry::getValue)
                .reduce(0f, Float::sum);

        float remainingYearlyBudget = yearlyBudget - totalYearlyExpense;

        System.out.println("Yearly Budget for " + year + ": " + yearlyBudget);
        System.out.println("Total Expense for " + year + ": " + totalYearlyExpense);
        System.out.println("Remaining Yearly Budget: " + remainingYearlyBudget);

        if (remainingYearlyBudget < 0) {
            System.out.println("Uh-Oh! You have exceeded your yearly budget by " + Math.abs(remainingYearlyBudget));
        }
    }

	public String getMonthlyBudget() {
		return null;
	}

	public String getYearlyBudget() {
		return null;
	}
}
