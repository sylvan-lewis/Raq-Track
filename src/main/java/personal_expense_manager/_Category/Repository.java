package personal_expense_manager._Category;

import java.util.ArrayList;
import java.util.List;

//This class is used a Database/Repository and its a singleton
public class Repository {

//The list holds all expenses added by user.
	public List<Expense> expList = new ArrayList<>();

//The list holds all expense-categories added by user.
	public List<Category> catList = new ArrayList<>();

// The list holds all budgets set by the user.
    public List<Budget> budList = new ArrayList<>();
    
//A singleton reference of repository.
	private static Repository repo;

//Private constructor to restrict object creation from outside.
	private Repository() {

	}
	
    // Reference to FileService for file operations.
    private FileService fileService = new FileService();
	
//Provides a singleton object

	public static Repository getRepository() {
		if (repo == null) {
			repo = new Repository();
		}
		System.out.println("Repository instance accessed.");
		return repo;
	}
	
	// This method restores data from text files using FileService when the application starts
	public void persistData(FileService fileService) {
	    fileService.writeToFile("categories.txt", catList);
	    fileService.writeToFile("expenses.txt", expList);
	    fileService.writeToFile("budgets.txt", budList);
	}
	
	//This method saves the repository data to text files using FileService
		public void restoreData(FileService fileService) {
		    catList = fileService.readFromFile("categories.txt", Category.class);
		    expList = fileService.readFromFile("expenses.txt", Expense.class);
		    budList = fileService.readFromFile("budgets.txt", Budget.class);
		}
}
