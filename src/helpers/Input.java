package helpers;

public class Input {
    public static boolean isValidEmail(String email) {
        //Check for single @ symbol
        int atIndex = email.indexOf('@');
        if(atIndex <= 0 || atIndex != email.lastIndexOf('@')) {
            //if ang inenter na email is walang "@" like "jerome.gmail.com" its not valid
            //email is nag start agad sa "@" like "@jerome.gmail.com", not valid
            //email na multiple "@" like "jerome@@gmail.com", not valid
            return false;
        }
        
        //Check for at least one character before @
        String localPart = email.substring(0, atIndex);
        if(localPart.isEmpty()) {
            //if email entered no charaters after ng "@" like "jerome@"
            //email is may space before "@" like " @jerome"
            return false;
        }
        
        //Check for at least one character after @
        String domainPart = email.substring(atIndex + 1);
        if(domainPart.isEmpty()) {
            //"jerome@"
            //"jerome@ "
            return false;
        }
        
        // Check for a dot in the domain part
        int dotIndex = domainPart.lastIndexOf('.');
        if(dotIndex <= 0 || dotIndex == domainPart.length() - 1) {
            //walang domain like "jerome@gmail"
            //nauna yung dot domain like "jerome@.com"
            //dot lang walang domain name like "jerome@gmail."
            return false;
        }
        
        return true;
    }
    
    
    public static boolean fieldChanged(String existingValue, String newValue) {
        if(existingValue == null && newValue == null) {
            return false; // Both are null, no change
        }
        if(existingValue == null || newValue == null) {
            return true; // One is null, change detected
        }
        return !existingValue.equals(newValue); //Detect value differences
    }
}
