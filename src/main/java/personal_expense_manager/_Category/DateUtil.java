package personal_expense_manager._Category;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//This class contains static methods to handle dates
public class DateUtil {

	public static final String[] MONTHS = { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };

//This method converts Date object to String 
//returns a data object for input date string
// string formatted date (ex. 10/08/2000) : DD/MM//YYYY
	public static Date stringToDate(String dateAsString) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			return df.parse(dateAsString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String dateToString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(date);

	}
	// This method returns Year and Month from given Date in Year,Month Format
	// string formatted date (ex. 2000/08 and or 2024/10) : YYYY,MM
	// Year and month will be extracted for this date for input

	public static String getYearAndMonth(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		return df.format(date);
	}

	// Returns year for input date
	public static Integer getYear(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		// String yearString = df.format(date);
		return Integer.parseInt(df.format(date));
	}

	// This returns month name for given month number
	// (ex. 01: January, 03: March, 07: July)
	public static String getMonthName(Integer monthNo) {
		if (monthNo < 1 || monthNo > 12) {
			throw new IllegalArgumentException("Month must be between 1-12");
		}
		return MONTHS[monthNo - 1];

		// validate that its within the range of normal calendar months 1-12
	}
}
