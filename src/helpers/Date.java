package helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {
    public static String getCurrentDate() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        // Define the desired date format
        //kase ang format sa database is mm/dd/yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        // Format the current date
        String formattedDate = currentDate.format(formatter);
        
        return formattedDate;
    }
}
