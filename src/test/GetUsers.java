package test;

import models.User; 
import controllers.UserController;
import helpers.Response;
import java.util.List;

public class GetUsers {
    protected static final UserController USER_CONTROLLER = new UserController();
    
    public static void main(String[] args) {                
        Response<List<User>> usersResponse = USER_CONTROLLER.getAllUsers();
        if (usersResponse.isSuccess()) {
            List<User> users = usersResponse.getData();
            
            System.out.printf("\n%-10s %-30s %-10s %-20s %-10s%n", "User ID", "Full Name", "Email", "Contact No.", "Role");
            if(users.isEmpty()) {
                System.out.println("No record is found.");
            }
            for (User user : users) {
                System.out.printf("%-10s %-30s %-10s %-20s %-10s%n", 
                    user.getUserId(), 
                    user.getFullName(), 
                    user.getEmail(), 
                    user.getContactNumber(),
                    user.getUserRole()
                );
            }
            System.out.println("Total Records: " + users.size());
        } else {
            System.out.println("Error: " + usersResponse.getMessage());
        }
    }
}
