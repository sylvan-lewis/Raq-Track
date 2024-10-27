package personal_expense_manager._Category;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// This class contains static methods to handle dates
public class DateUtil {

    // Month names for easy reference
    public static final String[] MONTHS = { "January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December" };

    // Converts a String date to a Date object, supporting multiple formats.
    // The method first tries "dd/MM/yyyy". If parsing fails, it attempts "yyyy-MM".
    // Returns a Date object for the input date string or null if parsing fails.
    // Format examples: "10/08/2000" for dd/MM/yyyy or "2024-10" for yyyy-MM
    public static Date stringToDate(String dateAsString) {
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat yearMonthFormat = new SimpleDateFormat("yyyy-MM");
        
        try {
            // Try parsing with the full date format ("dd/MM/yyyy") first
            Date date = fullDateFormat.parse(dateAsString);
            System.out.println("Parsed date successfully: " + dateAsString + " -> " + date); // Debug statement
            return date;
        } catch (ParseException e) {
            System.out.println("Failed to parse date with format dd/MM/yyyy: " + dateAsString); // Debug statement
        }

        try {
            // If the full date format fails, try year-month format ("yyyy-MM")
            Date date = yearMonthFormat.parse(dateAsString);
            System.out.println("Parsed year-month successfully: " + dateAsString + " -> " + date); // Debug statement
            return date;
        } catch (ParseException ex) {
            System.out.println("Failed to parse date with both formats: " + dateAsString); // Debug statement
            ex.printStackTrace();
            return null;
        }
    }

    // Converts a Date object to a String in "dd/MM/yyyy" format.
    public static String dateToString(Date date) {
        if (date == null) {
            return ""; // Or use "N/A" or some other placeholder if preferred
        }
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }

    // Returns Year and Month from a given Date in "yyyy-MM" format.
    // Format example: "2024-10"
    public static String getYearAndMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        return df.format(date);
    }

    // Returns the year as an Integer for the given Date.
    public static Integer getYear(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        return Integer.parseInt(df.format(date));
    }

    // Returns the month name for the given month number (1-based).
    // Examples: 1 -> "January", 3 -> "March"
    public static String getMonthName(Integer monthNo) {
        if (monthNo < 1 || monthNo > 12) {
            throw new IllegalArgumentException("Month must be between 1-12");
        }
        return MONTHS[monthNo - 1];
    }
}
