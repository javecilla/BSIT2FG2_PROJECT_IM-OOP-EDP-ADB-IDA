package helpers;

public class Text {
    public static String capitalizeFirstLetterInString(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }
}
