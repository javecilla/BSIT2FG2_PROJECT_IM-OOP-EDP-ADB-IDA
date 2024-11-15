package controllers;

import models.User;
import services.UserService;
import enums.UserRoles;
import helpers.Response;

import java.sql.SQLException;
import java.util.List;
import java.util.Collections;

public class UserController {
    protected final UserService userService;
    
    public UserController() {
        this.userService = new UserService();
    }
    
    // Create a new food item
    public Response<User> loginUser(String username, String password, String userRole) {
        User user = new User(username, password, userRole);
        
        //validate login input
        Response<User> validationResponse = validateLogin(user);
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }
        
        // Proceed with login if validation passes
        try { 
            if(userService.login(user)) {
                 return Response.success("Login successfully!", user);
            } else {
                return Response.error("Failed to login! Invalid credentials");
            }
        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }    
    } 
    
    public Response<User> validateLogin(User user) {
        if(user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return Response.error("Username cannot be empty!");
        }
        
        if(user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return Response.error("Password cannot be empty!");
        }
        
        if(user.getUserRole() == null || user.getUserRole().trim().isEmpty()) {
            return Response.error("Can't find user role.");
        }
        
        //check if the role is valid
        try {
            UserRoles.valueOf(user.getUserRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            return Response.error("Invalid user role: " + user.getUserRole());
        }
        
        return Response.success("Validation passed", user);
    }
}
