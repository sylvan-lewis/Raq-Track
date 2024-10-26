package personal_expense_manager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import personal_expense_manager._Category.Expense;
import personal_expense_manager._Category.ReportService;
import personal_expense_manager._Category.Repository;

class ReportServiceTest {

    private ReportService reportService;
    private Repository repo;

    @BeforeEach
    void setUp() {
        reportService = new ReportService();
        repo = Repository.getRepository();

        // Clear existing data in the repository for a clean test environment
        repo.expList.clear();
        repo.catList.clear();

        // Setting up sample data
        Calendar calendar = Calendar.getInstance();

        // Expense 1: January 2023
        calendar.set(2023, Calendar.JANUARY, 15);
        Expense expense1 = new Expense(1L, 50.0f, calendar.getTime(), "Groceries");

        // Expense 2: January 2023
        calendar.set(2023, Calendar.JANUARY, 20);
        Expense expense2 = new Expense(2L, 100.0f, calendar.getTime(), "Utilities");

        // Expense 3: February 2023
        calendar.set(2023, Calendar.FEBRUARY, 10);
        Expense expense3 = new Expense(1L, 75.0f, calendar.getTime(), "Transport");

        // Adding expenses to repo
        repo.expList.add(expense1);
        repo.expList.add(expense2);
        repo.expList.add(expense3);
    }

    @Test
    void testCalculateMonthlyTotal() {
        Map<String, Float> monthlyTotal = reportService.calculateMonthlyTotal();

        // Expected totals by month
        assertEquals(2, monthlyTotal.size(), "Should have two unique months calculated");

        // Check totals for each month
        assertEquals(150.0f, monthlyTotal.get("2023-01"), "Total for January 2023 should be 150.0");
        assertEquals(75.0f, monthlyTotal.get("2023-02"), "Total for February 2023 should be 75.0");
    }
}

