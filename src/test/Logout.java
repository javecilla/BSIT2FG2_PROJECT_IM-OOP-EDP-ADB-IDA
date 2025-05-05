package test;

import models.User; 
import controllers.UserController;
import helpers.Response;

public class Logout {
    protected static final UserController USER_CONTROLLER = new UserController();
    
    public static void main(String[] args) {        
        Response<User> logoutResponse = USER_CONTROLLER.logoutUser();
        if (logoutResponse.isSuccess()) {
            System.out.println(logoutResponse.getMessage());
            
            //dito bahala kana kung saan mo reredirect yung user after logout, o
            //anong frame papakita mo
            //navigateToLogin();
        } else {
            System.out.println(logoutResponse.getMessage());
        }
    }
}
