package core;

//import models.Admin;
//import models.Customer;
import models.User;

/**
 * Session class responsible for managing the logged-in user, customer, and admin.
 * It handles the session state for different types of users in the system.
 */
public class Session {
    // Static references to store the logged-in entities
    private static User loggedInUser;           
//    private static Customer loggedInCustomer;   
//    private static Admin loggedInAdmin;         


    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

//    public static void setLoggedInCustomer(Customer customer) {
//        loggedInCustomer = customer;
//    }
//
//    public static Customer getLoggedInCustomer() {
//        return loggedInCustomer;
//    }

//    public static void setLoggedInAdmin(Admin admin) {
//        loggedInAdmin = admin;
//    }
//
//    public static Admin getLoggedInAdmin() {
//        return loggedInAdmin;
//    }

    public static void clearSession() {
        loggedInUser = null;
//        loggedInCustomer = null;
//        loggedInAdmin = null;
    }

    public static boolean isUserLoggedIn() {
        return loggedInUser != null;
    }

//    public static boolean isAdminLoggedIn() {
//        return loggedInAdmin != null;
//    }
//    
//    public static boolean isCustomerLoggedIn() {
//        return loggedInCustomer != null;
//    }
}
