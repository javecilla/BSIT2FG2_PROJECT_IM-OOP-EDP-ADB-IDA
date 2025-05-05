package controllers;

import core.Session;
import java.sql.SQLException;

import models.User;
import services.UserService;
import enums.UserRoles;
import helpers.Input;
import helpers.Response;
import java.util.Collections;
import java.util.List;

public class UserController {
    protected final UserService userService;
    
    public UserController() {
        this.userService = new UserService();
    }
   
    public Response<User> loginUser(String username, String password) { 
        User user = new User(username, password);
        
        Response<User> validationResponse = validateLogin(user);
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }

        try { 
            boolean isLoggedIn = userService.login(user);
            
            return (isLoggedIn) 
                ? Response.success("Login successfully!", Session.getLoggedInUser()) //user
                : Response.error("Failed to login! Invalid credentials");

        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }    
    } 
    
    protected Response<User> validateLogin(User user) {
        if(user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return Response.error("Username cannot be empty!");
        }
        
        if(user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return Response.error("Password cannot be empty!");
        }

        return Response.success("Validation passed", user);
    }
    public Response<User> logoutUser() {
        User user = Session.getLoggedInUser();
        if(user == null) {
            return Response.error("Failed to logout, No session active found.");
        }
        
        Session.clearSession();
        return Response.success("Logout Successfuly", null);
    }
    
    public Response<User> registerUser(User user) { 
        Response<User> validationResponse = validateRegistration(user);
        if(!validationResponse.isSuccess()) {
            return validationResponse;
        }

        try { 
            if(userService.isUsernameTaken(user.getUsername())) {
                return Response.error("Error: Username '" + user.getUsername() + "' already taken!");
            }
            
            if(userService.isEmailTaken(user.getEmail())) {
                return Response.error("Error: Email '" + user.getUsername() + "' already taken!");
            }
            
            if(userService.isContactNumberTaken(user.getContactNumber())) {
                return Response.error("Error: Contact Number '" + user.getUsername() + "' already taken!");
            }
            
            boolean isRegistered = userService.create(user);
   
            return (isRegistered) 
                ? Response.success("Registered successfully!", user)
                : Response.error("Failed to register! Invalid data");
        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }   
    }

    protected Response<User> validateRegistration(User user) {
        //User Registration validation
        if(user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return Response.error("Username cannot be empty!");
        }
        
        if(user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return Response.error("Password cannot be empty!");
        }
        
        if(user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return Response.error("Password cannot be empty!");
        }
        
        //check if the role is valid
        try {
            UserRoles.valueOf(user.getUserRole().toUpperCase());
        } catch(IllegalArgumentException e) {
            return Response.error("Invalid user role: " + user.getUserRole());
        }
        
        if(user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return Response.error("Email cannot be empty!");
        }
        
        if(!Input.isValidEmail(user.getEmail())) {
            return Response.error("Invalid email!");
        }
        
        if(user.getContactNumber() == null || user.getContactNumber().trim().isEmpty()) {
            return Response.error("Contact number cannot be empty!");
        }
        
        //User Info registration validation
        if(user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            return Response.error("Username cannot be empty!");
        }
        
        if(user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            return Response.error("Last name cannot be empty!");
        }

        if (user.getBarangay() == null || user.getBarangay().trim().isEmpty()) {
            return Response.error("Barangay cannot be empty!");
        }

        if(user.getStreet() == null || user.getStreet().trim().isEmpty()) {
            return Response.error("Street cannot be empty!");
        }

        if(user.getHouseNumber() == null || user.getHouseNumber().trim().isEmpty()) {
            return Response.error("House number cannot be empty!");
        }

        if(user.getRegion() == null || user.getRegion().trim().isEmpty()) {
            return Response.error("Region cannot be empty!");
        }

        if(user.getProvince() == null || user.getProvince().trim().isEmpty()) {
            return Response.error("Province cannot be empty!");
        }

        if(user.getMunicipality() == null || user.getMunicipality().trim().isEmpty()) {
            return Response.error("Municipality cannot be empty!");
        }
        
        return Response.success("Validation passed", user);
    }
    
    
    public Response<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAll();

            if (users == null || users.isEmpty()) {
                return Response.success("No users found", Collections.emptyList());
            }

            return Response.success("Users retrieved successfully", users);
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    public Response<User> getUserById(int id) {
        try {
            User user = userService.getById(id);
            if (user == null) {
                return Response.error("User not found with ID: " + id);
            }

            return Response.success("User retrieved successfully", user);
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
}
