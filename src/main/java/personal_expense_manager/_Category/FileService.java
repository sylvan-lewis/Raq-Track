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
				writer.write(item.toString() + "\n");
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

				// Check if the line has the expected number of parts
				if (cls == Category.class) {
					if (parts.length > 1) {
						Category category = new Category(parts[1].trim());
						list.add(cls.cast(category));
					} else {
						System.out.println("Invalid category format in file: " + line);
					}
				} else if (cls == Expense.class) {
					if (parts.length == 4) { // Expecting 4 parts for Expense
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
			// System.out.println("No existing data found for " + fileName);
		}
		return list;
	}
}