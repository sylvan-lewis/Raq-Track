package personal_expense_manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import personal_expense_manager._Category.Category;
import personal_expense_manager._Category.FileService;

class FileServiceTest {

	private FileService fileService;
	private final String testFilename = "testData.txt";

	@BeforeEach
	void setUp() {
		fileService = new FileService();
	}

	@AfterEach
	void tearDown() {
		// Clean up by deleting the test file
		File file = new File(testFilename);
		if (file.exists()) {
			file.delete();
		}
	}

	@Test
	void testWriteAndReadCategoryToFile() {
		// Prepare sample Category data
		List<Category> categories = new ArrayList<>();
		categories.add(new Category(1L, "Food"));
		categories.add(new Category(2L, "Utilities"));

		// Write to file
		fileService.writeToFile(testFilename, categories);

		// Confirm file exists
		File file = new File(testFilename);
		assertTrue(file.exists(), "File should exist after writing.");

		// Debugging output to check file creation
		System.out.println("File created at: " + file.getAbsolutePath());

		// Read from file
		List<Category> result = fileService.readFromFile(testFilename, Category.class);

		// Debugging output to see what was read
		System.out.println("Read categories: " + result);

		// Verify the result
		assertEquals(2, result.size(), "There should be 2 categories in the file.");
		assertEquals("Food", result.get(0).getName(), "The first category name should be 'Food'.");
		assertEquals("Utilities", result.get(1).getName(), "The second category name should be 'Utilities'.");
	}
}
