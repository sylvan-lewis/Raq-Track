package personal_expense_manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import personal_expense_manager._Category.Budget;
import personal_expense_manager._Category.Category;
import personal_expense_manager._Category.Expense;
import personal_expense_manager._Category.Repository;

class RepositoryTest {

    private Repository repository;

    @BeforeEach
    void setUp() {
        repository = Repository.getRepository(); // get singleton instance
        repository.catList.clear(); // Clear previous data for isolated tests
        repository.expList.clear();
        repository.budList.clear();
    }

    @Test
    void testSingletonInstance() {
        Repository instance1 = Repository.getRepository();
        Repository instance2 = Repository.getRepository();
        assertSame(instance1, instance2, "Singleton instances should be the same");
    }

    @Test
    void testAddCategory() {
        Category category = new Category("Groceries");
        repository.catList.add(category);
        repository.setLastAddedCategory(category.getName());
        assertEquals(1, repository.catList.size(), "Category list should contain one category");
        assertEquals("Groceries", repository.getLastAddedCategory(), "Last added category should be 'Groceries'");
    }

    @Test
    void testAddExpense() {
        Expense expense = new Expense(1L, 50.0f, null, "Groceries for the week");
        repository.expList.add(expense);
        assertEquals(1, repository.expList.size(), "Expense list should contain one expense");
        assertEquals("Groceries for the week", repository.expList.get(0).getRemark(), "Expense remark should match");
    }

    @Test
    void testAddBudget() {
        Budget budget = new Budget();
        budget.setMonthlyBudget(500.0f);
        budget.setYearlyBudget(6000.0f);
        repository.budList.add(budget);
        assertEquals(1, repository.budList.size(), "Budget list should contain one budget");
        assertEquals(500.0f, repository.budList.get(0).getMonthlyBudget(), "Monthly budget should match");
        assertEquals(6000.0f, repository.budList.get(0).getYearlyBudget(), "Yearly budget should match");
    }

    @Test
    public void testPersistData() {
        Repository repo = Repository.getRepository();

        // Add an expense to expList
        Expense testExpense = new Expense(1L, 100.0f, new Date(), "Test Expense");
        repo.expList.add(testExpense);

        // Persist data
        repo.persistData();
        System.out.println("Data persisted to file.");

        // Clear the list to simulate a fresh start
        repo.expList.clear();

        // Restore data
        repo.restoreData();
        System.out.println("Data restored from file. Expense list size: " + repo.expList.size());

        // Check that the expense was saved and loaded correctly
        assertEquals(1, repo.expList.size(), "One expense should be loaded from file");
        assertEquals(testExpense.getAmount(), repo.expList.get(0).getAmount(), "Expense amount should match");
        assertEquals(testExpense.getRemark(), repo.expList.get(0).getRemark(), "Expense remark should match");
    }


    @Test
    public void testRestoreData() {
        Repository repo = Repository.getRepository();

        // Add a test expense and persist it
        Expense testExpense = new Expense(1L, 100.0f, new Date(), "JUnit Test Expense");
        repo.expList.add(testExpense);
        repo.persistData();  // Ensure this step writes data correctly to expenses.txt
        System.out.println("Persisted expense count: " + repo.expList.size());

        // Clear expList to simulate a fresh session
        repo.expList.clear();

        // Restore data from file
        repo.restoreData();
        System.out.println("Restored expense count: " + repo.expList.size());

        // Verify restored data
        assertEquals(1, repo.expList.size(), "Restored data should have one expense");
        Expense restoredExpense = repo.expList.get(0);
        assertEquals(testExpense.getAmount(), restoredExpense.getAmount(), "Expense amount should match");
        assertEquals(testExpense.getRemark(), restoredExpense.getRemark(), "Expense remark should match");
    }

}
