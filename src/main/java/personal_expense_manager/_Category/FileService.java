package personal_expense_manager._Category;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileService {

    // Generic method to write data to a file
    public <T> void writeToFile(String fileName, List<T> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            list.stream().forEach(item -> {
                try {
                    if (item instanceof Category) {
                        writer.write(((Category) item).getName() + "\n");
                    } else if (item instanceof Expense) {
                        Expense exp = (Expense) item;
                        writer.write(exp.getCategoryId() + "," + exp.getAmount() + "," + exp.getRemark() + ","
                                + DateUtil.dateToString(exp.getDate()) + "\n");
                    } else if (item instanceof Budget) {
                        writer.write(item.toString() + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Generic method to read data from a file and populate a list
    public <T> List<T> readFromFile(String fileName, Class<T> cls) {
        List<T> list = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            try {
                file.createNewFile(); // Create the file if it does not exist
                System.out.println("Created new file: " + fileName);
            } catch (IOException e) {
                System.out.println("Failed to create file: " + fileName);
                e.printStackTrace();
            }
            return list;
        }

        // Using Files.lines to stream through each line in the file
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            lines.forEach(line -> {
                String[] parts = line.split(",");

                if (cls == Category.class && parts.length == 1) {
                    Category category = new Category(parts[0].trim());
                    list.add(cls.cast(category));
                } else if (cls == Expense.class && parts.length == 4) {
                    Expense exp = new Expense();
                    exp.setCategoryId(Long.decode(parts[0].trim()));
                    exp.setAmount(Float.parseFloat(parts[1].trim()));
                    exp.setRemark(parts[2].trim());
                    exp.setDate(DateUtil.stringToDate(parts[3].trim()));
                    list.add(cls.cast(exp));
                } else if (cls == Budget.class && parts.length == 2) {
                    Budget budget = new Budget();
                    budget.setMonthlyBudget(Float.parseFloat(parts[0].trim()));
                    budget.setYearlyBudget(Float.parseFloat(parts[1].trim()));
                    list.add(cls.cast(budget));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
