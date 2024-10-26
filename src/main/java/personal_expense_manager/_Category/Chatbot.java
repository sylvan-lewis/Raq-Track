package personal_expense_manager._Category;

import java.io.IOException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

//This class contains most of the operations related to my application.
//This class prepares a menu and various methods are present to handle the user action.
//The class makes use of the 'Repository.java' to store the data. 
//Also its using 'ReportService.java' to generate different required reports.

public class Chatbot {

//Declare a reference of repository by calling a static method which returns a singleton repository object.
	
    private Repository repo = Repository.getRepository();
   
//Declare a reference of reportService by calling a different method to calculate reports
    
    private ReportService reportService = new ReportService();
   
 //Declare a reference of fileService by calling a different method to buffer written user input to .txt's   
 
    private FileService fileService = new FileService(); // Use the new FileService class

 // Declare a Scanner object to take input standard input from keyboard.   
    
    private Scanner s = new Scanner(System.in);
    
 // This variable store the menu-choice   
   
    private int choice;

 // Call this constructor to create our Chatbot object with default details.   
  
    public Chatbot() {
        restoreRepository();  // Restore data from text files when the chatbot starts
    }

//This method prepares the application menu using a switch-case and infinite loop, inquiring for user choice
   
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
               //Calling Default if a user inputs wrong digit
                     
                    	System.out.println("Invalid option. Please enter a valid option (0-7).");
                        break;
                }
         //This catch with prompt the print statements contents whenever a user inputs Invalid prompts
        
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                s.next();
            }
        }
    }
// This method prints a menu, which will show up in the console each input
   
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
// This method is called to hold a output screen after processing the requested task.
// and wait for any char input to continue to the menu
  
    public void pressAnyKeyToContinue() {
        System.out.println("Press 'Enter' to continue...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This method to taking expense category name as input to add caategory in the system
   
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

    // Call this method to print existing category list.
   
    public void onCategoryList() {
        System.out.println("Category List");
        for (int i = 0; i < repo.catList.size(); i++) {
            Category c = repo.catList.get(i);
            System.out.println((i + 1) + ". " + c.getName() + ", " + c.getCategoryId());
        }
    }

    // Call this method to enter expense detail. The entered details will be added in repository.
  
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

    // Call this method prints all entered expenses.
   
    private void onExpenseList() {
        System.out.println("Expense Listing...");
        for (Expense exp : repo.expList) {
            String catName = reportService.getCategoryName(exp.getCategoryId());
            String dateString = DateUtil.dateToString(exp.getDate());
            System.out.println(catName + ", " + exp.getAmount() + ", " + exp.getRemark() + ", " + dateString);
        }
    }
// This method is called from menu to prepare monthly-wise-expense-total.
// Using ReportService.java to calculate report. 
// The returned result is printed by this method.
// Means this method invokes a call to generate report then result is printed by this method
   
    private void onMonthlyExpenseList() {
        System.out.println("Monthly Expense Total...");
        Map<String, Float> resultMap = reportService.calculateMonthlyTotal();
        for (String yearMonth : resultMap.keySet()) {
            System.out.println(yearMonth + " : " + resultMap.get(yearMonth));
        }
    }
//This method is called from menu to prepare yearly-wise-expense-total.
// Using ReportService.java to calculate report. 
// The returned result is printed by this method.
// Means this method invokes a call to generate report then result is printed by this method     
  
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
  //This method is called from menu to prepare category-wise-expense-total.
 // Using ReportService.java to calculate report. 
 // The returned result is printed by this method.
 // Means this method invokes a call to generate report then result is printed by this method     
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

// This method stops the JVM
// Closing our Application
    private void onExit() {
        persistRepository();
        System.exit(0);
    }

//This method saves the repository data to text files using FileService
    private void persistRepository() {
        fileService.writeToFile("categories.txt", repo.catList);
        fileService.writeToFile("expenses.txt", repo.expList);
    }

// This method restores data from text files using FileService when the application starts
    private void restoreRepository() {
        repo.catList = fileService.readFromFile("categories.txt", Category.class);
        repo.expList = fileService.readFromFile("expenses.txt", Expense.class);
    }
}
