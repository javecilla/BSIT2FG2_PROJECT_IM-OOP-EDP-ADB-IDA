package helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Date {
    public static String getCurrentDate() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        // Format the date in MM/dd/yyyy for MS Access
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);
        return currentDate.format(formatter);
    }
    
    public static String formatDateForSQL(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
        String formattedDate = parsedDate.format(outputFormatter);
        
        return formattedDate;
    }
}
