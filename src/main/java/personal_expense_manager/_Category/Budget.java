package personal_expense_manager._Category;


import java.util.Calendar;
import java.util.Map;

public class Budget {
    private Float monthlyBudget;
    private Float yearlyBudget;
    private ReportService reportService = new ReportService();

    public Budget() {}

    // Method to set the monthly budget
    public void setMonthlyBudget(Float amount) {
        this.monthlyBudget = amount;
        System.out.println("Monthly budget set to " + amount);
    }

    // Method to set the yearly budget
    public void setYearlyBudget(Float amount) {
        this.yearlyBudget = amount;
        System.out.println("Yearly budget set to " + amount);
    }

    // Method to print the monthly budget report
    public void printMonthlyBudgetReport() {
        if (monthlyBudget == null || monthlyBudget == 0) {
            System.out.println("Monthly budget is not set.");
            return;
        }
        float totalMonthlyExpense = reportService.calculateMonthlyTotal().values().stream().reduce(0f, Float::sum);
        float remainingMonthlyBudget = monthlyBudget - totalMonthlyExpense;

        System.out.println("Monthly Budget: " + monthlyBudget);
        System.out.println("Total Monthly Expense: " + totalMonthlyExpense);
        System.out.println("Remaining Monthly Budget: " + remainingMonthlyBudget);

        if (remainingMonthlyBudget < 0) {
            System.out.println("Warning: You have exceeded your monthly budget by " + Math.abs(remainingMonthlyBudget));
        }
    }

    // Method to print the yearly budget report
    public void printYearlyBudgetReport() {
        if (yearlyBudget == null || yearlyBudget == 0) {
            System.out.println("Yearly budget is not set.");
            return;
        }

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Only include expenses from the current year
        float totalYearlyExpense = reportService.calculateYearlyTotal().entrySet().stream()
                .filter(entry -> entry.getKey() == currentYear) // Filter expenses by the current year
                .map(Map.Entry::getValue)
                .reduce(0f, Float::sum);

        float remainingYearlyBudget = yearlyBudget - totalYearlyExpense;

        System.out.println("Yearly Budget: " + yearlyBudget);
        System.out.println("Total Yearly Expense: " + totalYearlyExpense);
        System.out.println("Remaining Yearly Budget: " + remainingYearlyBudget);

        if (remainingYearlyBudget < 0) {
            System.out.println("Warning: You have exceeded your yearly budget by " + Math.abs(remainingYearlyBudget));
        }
    }

	public String getMonthlyBudget() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getYearlyBudget() {
		// TODO Auto-generated method stub
		return null;
	}
}
