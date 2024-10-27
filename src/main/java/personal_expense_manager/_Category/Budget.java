package personal_expense_manager._Category;

import java.util.Map;

public class Budget {
    private Float monthlyBudget;
    private Float yearlyBudget;
    private ReportService reportService = new ReportService();

    public Budget() {}

    // Method to set the monthly budget with validation
    public void setMonthlyBudget(Float amount) {
        // Check if yearlyBudget is set and ensure monthlyBudget does not exceed yearlyBudget/12
        if (yearlyBudget != null && amount > yearlyBudget / 12) {
            System.out.println("Error: Monthly budget cannot be set higher than yearly budget divided by 12.");
            return;
        }
        this.monthlyBudget = amount;        
        Repository.getRepository().persistData();
    }

    // Method to set the yearly budget with validation
    public void setYearlyBudget(Float amount) {
        // Check if monthlyBudget is set and ensure yearlyBudget is not set lower than monthlyBudget * 12
        if (monthlyBudget != null && amount < monthlyBudget * 12) {
            System.out.println("Error: Yearly budget cannot be set lower than 12 times the monthly budget.");
            return;
        }
        this.yearlyBudget = amount;
        Repository.getRepository().persistData();
    }

    // Method to print the monthly budget report
    public void printMonthlyBudgetReport() {
        if (monthlyBudget == null || monthlyBudget == 0) {
            System.out.println("Monthly budget is not set.");
            return;
        }

        // Retrieve the monthly expense totals from ReportService
        Map<String, Float> monthlyExpenses = reportService.calculateMonthlyTotal();

        System.out.println("Monthly Budget Report:");
        for (Map.Entry<String, Float> entry : monthlyExpenses.entrySet()) {
            String yearMonth = entry.getKey();
            Float totalMonthlyExpense = entry.getValue();
            
            // Calculate the remaining budget
            float remainingMonthlyBudget = monthlyBudget - totalMonthlyExpense;

            System.out.println("For " + yearMonth + ": ");
            System.out.println("Total Monthly Expense: " + totalMonthlyExpense);
            System.out.println("Monthly Budget: " + monthlyBudget);
            System.out.println("Remaining Budget: " + remainingMonthlyBudget);

            // Check if the budget was exceeded
            if (remainingMonthlyBudget < 0) {
                System.out.println("Warning: You have exceeded your monthly budget by " + Math.abs(remainingMonthlyBudget));
            } else {
                System.out.println("Good job! You are within the monthly budget.");
            }
            System.out.println("------------------------------");
        }
    }


    // Method to print the yearly budget report for a specified year
    public void printYearlyBudgetReport() {
        if (yearlyBudget == null || yearlyBudget == 0) {
            System.out.println("Yearly budget is not set.");
            return;
        }

        // Retrieve the yearly expense totals from ReportService
        Map<Integer, Float> yearlyExpenses = reportService.calculateYearlyTotal();

        System.out.println("Yearly Budget Report:");
        for (Map.Entry<Integer, Float> entry : yearlyExpenses.entrySet()) {
            int year = entry.getKey();
            float totalYearlyExpense = entry.getValue();

            // Calculate the remaining budget
            float remainingYearlyBudget = yearlyBudget - totalYearlyExpense;

            System.out.println("For Year " + year + ":");
            System.out.println("Total Yearly Expense: " + totalYearlyExpense);
            System.out.println("Yearly Budget: " + yearlyBudget);
            System.out.println("Remaining Budget: " + remainingYearlyBudget);

            // Check if the budget was exceeded
            if (remainingYearlyBudget < 0) {
                System.out.println("Warning: You have exceeded your yearly budget by " + Math.abs(remainingYearlyBudget));
            } else {
                System.out.println("Good job! You are within the yearly budget.");
            }
            System.out.println("------------------------------");
        }
    }


    public Float getMonthlyBudget() {
        return monthlyBudget;
    }

    public Float getYearlyBudget() {
        return yearlyBudget;
    }
    
    @Override
    public String toString() {
        return (monthlyBudget != null ? monthlyBudget : "") + "," + (yearlyBudget != null ? yearlyBudget : "");
    }
}
