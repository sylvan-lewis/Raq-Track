package personal_expense_manager._Category;

import java.io.Serializable;

//This is a domain class representing a category
// It also implements the Serializable interface, making instances serializable
//
public class Category implements Serializable{
	private static final long serialVersionUID = 1L;
	
//It refers to a unique category Id. Simply generated using current time
//In a real time application it should be generated using some professional strategy or algo
		private String categoryId; //= System.currentTimeMillis();
		
//Name of Expense category		
		private String name;
		
		public Category(String name) {
			this.name = name;
		}
		public Category(String categoryId, String name) {
			this.categoryId = categoryId;
			this.name = name;
		}
		
		public Category() {
			
		}
		
		public String getCategoryId() {
			return categoryId;
		}
		
		public void setCategoryId(String categoryId) {
			this.categoryId = categoryId;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name= name;
		}
		
		public static void main(String[] args) {
		
	}
}

