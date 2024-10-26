package personal_expense_manager._Category;

import java.util.ArrayList;
import java.util.List;

//This class is used a Database/Repository and its a singleton
public class Repository {

//The list holds all expenses added by user.
	public List<Expense> expList = new ArrayList<>();

//The list holds all expense-categories added by user.
	public List<Category> catList = new ArrayList<>();

//A singleton reference of repository.
	private static Repository repository;

//Private constructor to restrict object creation from outside.
	private Repository() {

	}
//Provides a singleton object

	public static Repository getRepository() {
		if (repository == null) {
			repository = new Repository();
		}
		System.out.println("Repository instance accessed.");
		return repository;
	}
}
