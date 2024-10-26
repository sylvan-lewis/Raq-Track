package personal_expense_manager._Category;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

//This class contains various methods to calculate the application reports.
public class ReportService {

//Declare a reference of singleton repository.
	private Repository repo = Repository.getRepository();

//The method calculates month-wise total and returns results in Map.	
//Preparing data in proper order.
	public Map<String, Float> calculateMonthlyTotal() {
		Map<String, Float> m = new TreeMap<>();
		for (Expense exp : repo.expList) {
			Date expDate = exp.getDate();
			String yearMonth = DateUtil.getYearAndMonth(expDate);
			if (m.containsKey(yearMonth)) {
				Float total = m.get(yearMonth);
				total = total + exp.getAmount();
				m.put(yearMonth, total);
			} else {
				m.put(yearMonth, exp.getAmount());

			}
		}
		return m;
	}

//The method calculates month-wise total and returns results in Map.	
//Preparing data in proper order.		
	public Map<Integer, Float> calculateYearlyTotal() {
		Map<Integer, Float> m = new TreeMap<>();
		for (Expense exp : repo.expList) {
			Date expDate = exp.getDate();
			Integer year = DateUtil.getYear(expDate);
			if (m.containsKey(year)) {
				Float total = m.get(year);
				total = total + exp.getAmount();
				m.put(year, total);
			} else {
				m.put(year, exp.getAmount());
			}
		}
		return m;

	}

//The method calculates category-wise total and returns results in Map.	
//Preparing data in proper order.
	public Map<String, Float> calculateCategoriedTotal() {
		Map<String, Float> m = new TreeMap<>();

		for (Expense exp : repo.expList) {
			Long categoryId = exp.getCategoryId();
			String catName = this.getCategoryName(categoryId);

			if (m.containsKey(catName)) {
				Float total = m.get(catName);
				total = total + exp.getAmount();
				m.put(catName, total);
			} else {

				m.put(catName, exp.getAmount());
			}
		}
		return m;
	}

//The method returns category name for given categoryId
// Will return null when wrong category Id is supplied
	public String getCategoryName(Long categoryId) {
		for (Category c : repo.catList) {
			if (c.getCategoryId() != null && c.getCategoryId().equals(categoryId)) {
				return c.getName();
			}
		}
		return null;
	}

	public static void main(String[] args) {

	}

}
