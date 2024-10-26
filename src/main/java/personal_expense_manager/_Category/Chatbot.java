package personal_expense_manager._Category;

import java.io.IOException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Chatbot {

    private Repository repo = Repository.getRepository();
    private ReportService reportService = new ReportService();
    private FileService fileService = new FileService(); // Use the new FileService class

    private Scanner s = new Scanner(System.in);
    private int choice;

    public Chatbot() {
        restoreRepository();  // Restore data from text files when the chatbot starts
    }

    public void showMenu() {
        while (true) {
            try {
                printMenu();
                switch (choice) {
                    case 1:
                        onAddCategory();
                        pressAnyKeyToContinue();
                        break;
                    case 2:
                        onCategoryList();
                        pressAnyKeyToContinue();
                        break;
                    case 3:
                        onExpenseEntry();
                        pressAnyKeyToContinue();
                        break;
                    case 4:
                        onExpenseList();
                        pressAnyKeyToContinue();
                        break;
                    case 5:
                        onMonthlyExpenseList();
                        pressAnyKeyToContinue();
                        break;
                    case 6:
                        onYearlyExpenseList();
                        pressAnyKeyToContinue();
                        break;
                    case 7:
                        onCategorizedExpenseList();
                        pressAnyKeyToContinue();
                        break;
                    case 0:
                        onExit();
                        break;
                    default:
                        System.out.println("Invalid option. Please enter a valid option (0-7).");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                s.next();  // Clear invalid input
            }
        }
    }

    public void printMenu() {
        System.out.println("----- Welcome to YourExpense! -----");
        System.out.println("1. Add Category");
        System.out.println("2. Category List");
        System.out.println("3. Expense Entry");
        System.out.println("4. Expense List");
        System.out.println("5. Monthly Expense List");
        System.out.println("6. Yearly Expense List");
        System.out.println("7. Categorized Expense List");
        System.out.println("0. Exit");
        System.out.println("----------------------------------");
        System.out.print("Enter your choice: ");
        choice = s.nextInt();
    }

    public void pressAnyKeyToContinue() {
        System.out.println("Press 'Enter' to continue...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to add a category
    public void onAddCategory() {
        s.nextLine();  // Consume newline
        System.out.print("Enter Category Name: ");
        String catName = s.nextLine();
        Category cat = new Category(catName);
        repo.catList.add(cat);
        System.out.println("Success: Category Added.");

        // Write the new category to the text file using FileService
        fileService.writeToFile("categories.txt", repo.catList);
    }

    // Method to list all categories
    public void onCategoryList() {
        System.out.println("Category List");
        for (int i = 0; i < repo.catList.size(); i++) {
            Category c = repo.catList.get(i);
            System.out.println((i + 1) + ". " + c.getName() + ", " + c.getCategoryId());
        }
    }

    // Method to add an expense entry
    public void onExpenseEntry() {
        System.out.println("Enter Details for Expense Entry...");
        onCategoryList();  // Display the categories to choose from
        System.out.print("Choose category (by number): ");
        int catChoice = s.nextInt();
        if (catChoice < 1 || catChoice > repo.catList.size()) {
            System.out.println("Invalid category choice.");
            return;
        }
        Category selectedCat = repo.catList.get(catChoice - 1);

        System.out.print("Enter Amount: ");
        float amount = s.nextFloat();

        System.out.print("Enter Remark: ");
        s.nextLine();  // Consume newline
        String remark = s.nextLine();

        System.out.print("Enter Date (DD/MM/YYYY): ");
        String dateAsString = s.nextLine();
        Date date = DateUtil.stringToDate(dateAsString);

        Expense exp = new Expense(selectedCat.getCategoryId(), amount, date, remark);
        repo.expList.add(exp);
        System.out.println("Success: Expense Added.");

        // Write the new expense to the text file using FileService
        fileService.writeToFile("expenses.txt", repo.expList);
    }

    // Method to list all expenses
    private void onExpenseList() {
        System.out.println("Expense Listing...");
        for (Expense exp : repo.expList) {
            String catName = reportService.getCategoryName(exp.getCategoryId());
            String dateString = DateUtil.dateToString(exp.getDate());
            System.out.println(catName + ", " + exp.getAmount() + ", " + exp.getRemark() + ", " + dateString);
        }
    }

    private void onMonthlyExpenseList() {
        System.out.println("Monthly Expense Total...");
        Map<String, Float> resultMap = reportService.calculateMonthlyTotal();
        for (String yearMonth : resultMap.keySet()) {
            System.out.println(yearMonth + " : " + resultMap.get(yearMonth));
        }
    }

    private void onYearlyExpenseList() {
        System.out.println("Yearly Expense Total...");
        Map<Integer, Float> resultMap = reportService.calculateYearlyTotal();
        float total = 0.0F;
        for (Integer year : resultMap.keySet()) {
            float yearlyTotal = resultMap.get(year);
            total += yearlyTotal;
            System.out.println(year + " : " + yearlyTotal);
        }
        System.out.println("Total Expenses: " + total);
    }

    private void onCategorizedExpenseList() {
        System.out.println("Category-wise Expense Listing...");
        Map<String, Float> resultMap = reportService.calculateCategoriedTotal();
        float netTotal = 0.0F;
        for (String categoryName : resultMap.keySet()) {
            float catWiseTotal = resultMap.get(categoryName);
            netTotal += catWiseTotal;
            System.out.println(categoryName + " : " + catWiseTotal);
        }
        System.out.println("Net Total: " + netTotal);
    }

    // Exit the program and save the data
    private void onExit() {
        persistRepository();
        System.exit(0);
    }

    // Save the repository data to text files using FileService
    private void persistRepository() {
        fileService.writeToFile("categories.txt", repo.catList);
        fileService.writeToFile("expenses.txt", repo.expList);
    }

    // Restore data from text files using FileService when the application starts
    private void restoreRepository() {
        repo.catList = fileService.readFromFile("categories.txt", Category.class);
        repo.expList = fileService.readFromFile("expenses.txt", Expense.class);
    }
}
