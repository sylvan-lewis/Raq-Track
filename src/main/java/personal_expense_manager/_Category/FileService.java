package personal_expense_manager._Category;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileService {

	// Generic method to write data to a file
	public <T> void writeToFile(String fileName, List<T> list) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			for (T item : list) {
				if (item instanceof Category) {
					// Write only the name for Category objects
					writer.write(((Category) item).getName() + "\n");
				} else if (item instanceof Expense) {
					Expense exp = (Expense) item;
					// Write four fields for Expense objects
					writer.write(exp.getCategoryId() + "," + exp.getAmount() + "," + exp.getRemark() + ","
							+ DateUtil.dateToString(exp.getDate()) + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Generic method to read data from a file and populate a list
	public <T> List<T> readFromFile(String fileName, Class<T> cls) {
		List<T> list = new ArrayList<>();
		try (Scanner scanner = new Scanner(new FileInputStream(fileName))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(",");

				if (cls == Category.class) {
					// Expecting only the name for Category
					if (parts.length == 1) {
						Category category = new Category(parts[0].trim());
						list.add(cls.cast(category));
					} else {
						System.out.println("Invalid category format in file: " + line);
					}
				} else if (cls == Expense.class) {
					// Expecting 4 parts for Expense
					if (parts.length == 4) {
						Expense exp = new Expense();
						exp.setCategoryId(Long.decode(parts[0].trim()));
						exp.setAmount(Float.parseFloat(parts[1].trim()));
						exp.setRemark(parts[2].trim());
						exp.setDate(DateUtil.stringToDate(parts[3].trim()));
						list.add(cls.cast(exp));
					} else {
						System.out.println("Invalid expense format in file: " + line);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
