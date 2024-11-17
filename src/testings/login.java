package testings;

import models.User;
import controllers.UserController;
import helpers.Response;


public class login {
    public static void main(String[] args) {
        UserController controller = new UserController();
        
        //kunyare na store the yung mga credentials ng user
        String username = "admin";
        String password = "admin";
        String role = "Admin";
        
        //send ka ngayon ng request sa controller na ivalidate yung login
        Response<User> loginResponse = controller.loginUser(username, password, role);
        if (loginResponse.isSuccess()) {
            System.out.println(loginResponse.getMessage());
            
            //display bulk information, 
            
            
            User user = loginResponse.getData();  
            user.display();
            
            //pero pede ka paden mag display ng  individually kase may mga getter nayan call mo lang,
            //nyare kukunin yung firstName, lastName or yung fullName na
            /*
            System.out.println("User ID: " + user.getUserId());
            System.out.println("Username: " + user.getUsername());
            
            System.out.println("UserInfo ID: " + user.getUserInfoId());
            System.out.println("First Name: " + user.getFirstName());
            System.out.println("Last Name: " + user.getFirstName());
            
            and so on, tingnan mo nalang sa [models] package
            tapos [class] USER / USER_INFO
            
            System.out.println("Full Name: " + user.getFullName());
            System.out.println("Address: " + user.getFullAddress());
            
            */

                       
            
        } else {
            System.out.println(loginResponse.getMessage());
        }
    }
            
}
