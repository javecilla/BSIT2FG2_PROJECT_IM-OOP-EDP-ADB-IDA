package test;

import models.User; 
import controllers.UserController;
import helpers.Response;
import enums.UserRoles;

public class SignUp {
    protected static final UserController USER_CONTROLLER = new UserController();

    public static void main(String[] args) {
        String username = "test1";
        String password = "123456";
        String role = UserRoles.CUSTOMER.name().toLowerCase(); //UserRoles.CLIENT.name().toLowerCase();
        String email = "test1@gmail.com";
        String contactNumber = "09772465533";
        String firstName = "John";
        String lastName = "Doe";
        String barangay = "Brgy. NagKaisang Nayon";
        String street = "Likod";
        String houseNumber = "3D";
        String region = "Region 3";
        String province = "Bulacan";
        String municipality = "Pandi";
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setUserRole(role);
        user.setEmail(email);
        user.setContactNumber(contactNumber);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBarangay(barangay);
        user.setStreet(street);
        user.setHouseNumber(houseNumber);
        user.setRegion(region);
        user.setProvince(province);
        user.setMunicipality(municipality);
        
        Response<User> signUpResponse = USER_CONTROLLER.registerUser(user);
        if (signUpResponse.isSuccess()) {
            System.out.println(signUpResponse.getMessage());
        } else {
            System.out.println(signUpResponse.getMessage());
        }
    }
    
}
