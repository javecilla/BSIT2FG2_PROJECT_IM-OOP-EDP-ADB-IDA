package models;

public class Session {
    private static User loggedInUser; // Common to all roles
    private static Customer loggedInCustomer; // For clients
    private static Admin loggedInAdmin;       // For admins

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInCustomer(Customer customer) {
        loggedInCustomer = customer;
    }

    public static Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    public static void setLoggedInAdmin(Admin admin) {
        loggedInAdmin = admin;
    }

    public static Admin getLoggedInAdmin() {
        return loggedInAdmin;
    }
}

