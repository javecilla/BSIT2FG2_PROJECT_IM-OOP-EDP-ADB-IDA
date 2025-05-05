package test;

import models.User; 
import controllers.UserController;
import core.Session;
import helpers.Response;

public class Login {
    protected static final UserController USER_CONTROLLER = new UserController();
    
    public static void main(String[] args) {
        String username = "test1";
        String password = "123456";
        
        Response<User> loginResponse = USER_CONTROLLER.loginUser(username, password);
        if (loginResponse.isSuccess()) {
            User user = loginResponse.getData();
            System.out.println(loginResponse.getMessage());
            
            //"User authenticatedUser = Session.getLoggedInUser();" //no need na netong session since controller.loginUser
            //nirereturn niya yung user data na authenticated and naka set sa session
            //so kung kukunin mo yung mga information kung sino naka login, using user model nalang
            System.out.println("\nWelcome back, " + user.getFullName()); //authenticatedUser.getFullName()
            
            //navigateToDashboard(user);
        } else {
            System.out.println(loginResponse.getMessage());
        }
    }
}
