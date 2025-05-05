package test;

import models.User; 
import controllers.UserController;
import helpers.Response;

public class GetUser {
    protected static final UserController USER_CONTROLLER = new UserController();
    
    public static void main(String[] args) {    
        int userId = 1;
        Response<User> usersResponse = USER_CONTROLLER.getUserById(userId);
        if (usersResponse.isSuccess()) {
            User user = usersResponse.getData();
            
            System.out.println("UserId: " + user.getUserId());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("ContactNumnber: " + user.getContactNumber());
            System.out.println("UserRole: " + user.getUserRole());
            System.out.println("UserInfoId: " + user.getUserInfoId());
            System.out.println("FirstName: " + user.getFirstName());
            System.out.println("LastName: " + user.getLastName());
            System.out.println("Barangay: " + user.getBarangay());
            System.out.println("Street: " + user.getStreet());
            System.out.println("HouseNumber: " + user.getHouseNumber());
            System.out.println("Region: " + user.getRegion());
            System.out.println("Province: " + user.getProvince());
            System.out.println("Municipality: " + user.getMunicipality());
        } else {
            System.out.println("Error: " + usersResponse.getMessage());
        }
    }
}
