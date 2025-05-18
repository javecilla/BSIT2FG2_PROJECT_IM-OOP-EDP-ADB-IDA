# Project Name: Mommy's Variety Store Management System

## Overview
Mommy's Variety Store Management System is a desktop application built with Java Swing for the graphical user interface (GUI) and Microsoft SQL Server as the primary database. 
The application is designed to manage inventory, categories, and other store-related operations. 
It also supports a legacy connection to Microsoft Access for backward compatibility with earlier database versions. 
This project is developed as part of a BSIT (Bachelor of Science in Information Technology) course requirement.

## Introduction
The **E-commerce Store** system is designed to provide an online platform for customers to browse, select, and purchase products conveniently. It addresses the traditional challenges of in-person shopping, such as limited accessibility, restricted operating hours, and geographical barriers. 

## Purpose
- **Convenience**: Offers customers the ability to shop anytime and anywhere through a user-friendly interface.  
- **Efficiency**: Streamlines the purchasing process, including product selection, payment, and delivery tracking.  
- **Management**: Simplifies inventory, order, and user management for the store administrator.  

## Problems Addressed
1. **Accessibility**: Overcomes location and time constraints, making shopping available 24/7.  
2. **Variety**: Provides a broader range of products compared to physical stores, enhancing customer choice.  
3. **Customer Experience**: Offers personalized recommendations and easy navigation to improve user satisfaction.  
4. **Manual Errors**: Reduces errors in order processing and inventory tracking by automating workflows.  
5. **Operational Costs**: Lowers overhead costs associated with physical store operations, enabling competitive pricing.  

## Features and Functionalities
The developed E-Commerce system includes several features and functionalities that ensure a smooth experience for the users:

- **Login System**: A secure login system prevents unauthorized and unregistered users from accessing the platform.
  ![SSImage](https://avecilla-project-multipurpose.infinityfreeapp.com/assets/images/bulsu/zlogin1.png)  
- **Courier Management System**: Allows administrators to add, edit, or delete riders, and filter based on availability. 
  ![SSImage](https://avecilla-project-multipurpose.infinityfreeapp.com/assets/images/bulsu/zcourier2.png)   
- **Stock Management System**: Enables administrators to adjust stock quantities and reorder items as needed. It also highlights low-stock items.  
  ![SSImage](https://avecilla-project-multipurpose.infinityfreeapp.com/assets/images/bulsu/zstock5.png)  
- **Cart Management**: Users can manage their shopping cart and retain cart contents even after logging out.  
  ![SSImage](https://avecilla-project-multipurpose.infinityfreeapp.com/assets/images/bulsu/zcourier6.png)  
- **Improved UI/UX Experience**: Enhanced user interface design to ensure ease of use and a seamless shopping experience.  
  ![SSImage](https://avecilla-project-multipurpose.infinityfreeapp.com/assets/images/bulsu/zpanel4.png)  
- **Administrator Dashboard**: Real-time statistics and data visualization for administrators, including user count, total revenue, and sales metrics.
  ![SSImage](https://avecilla-project-multipurpose.infinityfreeapp.com/assets/images/bulsu/zadmin3.png)  

## Challenges and Solutions
During the development of the E-commerce Store application, several challenges were encountered and resolved to ensure a robust and scalable system. Migrating from MS Access to SQL Server is a challenge especially connecting it to the system. Migrating from MS Access to SQL Server was addressed by manually migrating the database and adding optimized SQL codes to ensure a smooth transaction between the system and database. On the hand, connecting the database to the system required the utilization of JDBC library. Maintaining code consistency in a collaborative environment was addressed by enforcing a centralized coding standards document and using peer reviews with frequent Git commits. Implementing the MVC pattern effectively required restructuring to separate concerns clearly, ensuring controllers handled workflows, models managed data logic, and views focused on UI presentation.  
 
Another challenge was the enhancement of user interface. Since the former system utilizes JOptionPane to create the user interface, it posed several issues such as poor error handling, unattractive design, and difficulty in usage, the team redesigned the system using Swing to make it easier for users to use. The developers used several Swing components such as JFrame, JPanel, JLabel, JButtons, etc. and just used JOptionPane for error handling. To address the design consistency, the team used Figma to develop the initial design of the system and used the components size to ensure the design consistency.

## Conclusion
By incorporating OOP principles into its design, the E-commerce Store application achieves a structured, maintainable, and scalable architecture. Encapsulation ensures controlled data access, abstraction simplifies complexity, inheritance promotes code reuse, and polymorphism enhances flexibility. These principles, combined with the MVC pattern, make the system robust and adaptable for future growth and enhancements. 

### UML Class Diagram Model Packages
The UML class diagram presented below represents the Model Package, which is fundamental to the system's architecture. This diagram highlights the logical relationships and associations among the core classes, providing a clear overview of how data is structured and interconnected. As the backbone of the system, the Model Package defines the attributes and behaviors essential to the application's functionality.  
To explore the complete UML class diagrams for all packages within the project, please refer to the following link:  
[UML Class Diagram](https://drive.google.com/drive/folders/1aNwJ0dXP1p0v5vgDpiBEpM_J-_goiDP1?usp=drive_link)

![Diagram Image](https://avecilla-project-multipurpose.infinityfreeapp.com/assets/images/bulsu/uml.png)

### Preview for Java Source Code: Model Packages 
The source code below showcases the implementation of the Model Package, which serves as the foundation for data management and representation within the system. These classes define the attributes, behaviors, and relationships that drive the application's functionality.  

```java
package models;
/**
 * Represents the `UserInfo` table in the database.
 * Define a base class UserInfo for shared attributes and behaviors, 
 * such as the user's name, address, and location details. This class encapsulates the structure 
 * of a user’s general information.
 */
public class UserInfo {
    // Attributes (represent column name in the database)
    private int id;
    private String firstName;
    private String lastName;
    private String barangay;
    private String street;
    private String houseNumber;
    private String region;
    private String province;
    private String municipality;
    
    // Constructors
    public UserInfo() {}
    
    public UserInfo(
       int userInfoId,
       String firstName,
       String lastName,
       String barangay,
       String street,
       String houseNumber,
       String region,
       String province,
       String municipality
    ) {
       this.id = userInfoId;
       this.firstName = firstName;
       this.lastName = lastName;
       this.barangay = barangay;
       this.street = street;
       this.houseNumber = houseNumber;
       this.region = region;
       this.province = province;
       this.municipality = municipality;
    }
    
    public int getUserInfoId() {
        return id;
    }

    public void setUserInfoId(int userInfoId) {
        this.id = userInfoId;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    //Get full name
    public String getFullName() {
        //Jerome Avecilla
        return "" + firstName + " " + lastName;
    }
    
    public String getBarangay() {
        return barangay;
    }
    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }
    
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getHouseNumber() {
        return houseNumber;
    }
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
    
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    
    public String getMunicipality() {
        return municipality;
    }
    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getFullAddress() {
        //2331, St.Piltover, Brgy. Mapulang Lupa, Pandi, Bulacan.
        return "" + houseNumber + ", St." + street + ", Brgy." + barangay + ", " + municipality + ", " + province + ", " + region + ".";
    }
    
    public String display() {
        return "UserInfo ID: " +  getUserInfoId() + "\nFull Name: " + getFullName() + "\nAddress: " + getFullAddress();
    }
}
```
---
```java
package models;
/**
 * Represents the `User` table in the database.
 * Building on UserInfo, the User class extends it by adding account-specific attributes such as username, 
 * password, email, and role. This design allows us to differentiate between users at the account level 
 * while inheriting general user details. User serves as a bridge for further specialization into distinct user 
 * roles.
 */
public class User extends UserInfo {
    private int id;
    private String username;
    private String password;
    private String role;  // Role of the user (e.g., Admin, Client).
    private String email;
    private String contactNumber;
    
    public User() {
      super();
    }
    
    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }
    
    public User(String username, String password, String userRole, String email, String contactNumber) {...}
    
    public User(int userId, String username, String password, String userRole, String email, String contactNumber) {...}
    
    public User(int userId, String username, String password, String email, String contactNumber, UserInfo userInfo) {...}
    
    public User(int userId, String username, String password, String userRole, String email, String contactNumber, UserInfo userInfo) {...}
    
    public int getUserId() {
        return id;
    }
    
    public void setUserId(int userId) {
        this.id = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getUserRole() {
        return role;
    }
    
    public void setUserRole(String userRole) {
        this.role = userRole;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getContactNumber() {
        return contactNumber;
    }
    
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    @Override
    public String display() {
        return "User ID: " + getUserId() +  "\nUsername: " + getUsername() + "\nPassword: " + getPassword() + "User Info: \n" + super.display();
    }
}
```

### Preview for Java Source Code: Services Packages 
Below is the java code showcases the implementation of the Services working with Model Package. This Services package Handles complex business logic beyond the controllers' scope, supporting abstraction, inheritance, and code reuse.

```java
package services;

//other imports...
import config.MSSQLConnection;
import interfaces.IDatabaseOperators;
import models.User;
import core.Session;
import java.util.ArrayList;
import java.util.Arrays;
import models.Courier;

public class UserService implements IDatabaseOperators<User> {

    public boolean login(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = MSSQLConnection.getConnection();

            String query = """
                SELECT TOP 1 * FROM USER_WITH_PROFILE WHERE BINARY Username = ? OR Email = ? OR User_Contact = ?
            """;
            
            pst = conn.prepareStatement(query);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getUsername());
            pst.setString(3, user.getUsername());
            
            rs = pst.executeQuery();
            if (!rs.next()) {
                return false; //user not exist
            }
            
            String storedPassword = rs.getString("User_Password");
            if (!storedPassword.equals(user.getPassword())) {
                return false;
            }
            
            user.setUserId(rs.getInt("UserID"));
            user.setUsername(rs.getString("Username"));
            user.setEmail(rs.getString("Email"));
            user.setContactNumber(rs.getString("User_Contact"));
            user.setUserRole(rs.getString("User_Role"));
            user.setUserInfoId(rs.getInt("UserInfo_ID"));
            user.setFirstName(rs.getString("First_Name"));
            user.setLastName(rs.getString("Last_Name"));
            user.setBarangay(rs.getString("Barangay"));
            user.setStreet(rs.getString("Street"));
            user.setHouseNumber(rs.getString("House_Number"));
            user.setRegion(rs.getString("Region"));
            user.setProvince(rs.getString("Province"));
            user.setMunicipality(rs.getString("Municipality"));
            
            Session.setLoggedInUser(user);
           
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to login an error occured in our end.");
        } finally {
            MSSQLConnection.closeResources(rs, pst); 
        }
    }
    
   //other methods...

    @Override
    public boolean create(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
       
        try {
            conn = MSSQLConnection.getConnection();
            conn.setAutoCommit(false);

            String query = """
                EXEC SignUp
                    @Username = ?,
                    @User_Password = ?,
                    @User_Role = ?,
                    @Email = ?,
                    @User_Contact = ?,
                    @First_Name = ?,
                    @Last_Name = ?,
                    @Barangay = ?,
                    @Street = ?,
                    @House_Number = ?,
                    @Region = ?,
                    @Province = ?,
                    @Municipality = ?
            """;
            pst = conn.prepareStatement(query);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getUserRole());
            pst.setString(4, user.getEmail());
            pst.setString(5, user.getContactNumber());
            //user info
            pst.setString(6, user.getFirstName());
            pst.setString(7, user.getLastName());
            pst.setString(8, user.getBarangay());
            pst.setString(9, user.getStreet());
            pst.setString(10, user.getHouseNumber());
            pst.setString(11, user.getRegion());
            pst.setString(12, user.getProvince());
            pst.setString(13, user.getMunicipality());
            
            boolean created = pst.executeUpdate() > 0;
            if(!created) {
                throw new SQLException("An error occured during user creation.");
            }
            
            conn.commit();
            return true;  
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to registered an error occured in our end.");
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            MSSQLConnection.closeResources(rs, pst);
        }
    }

    @Override
    public User getById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = MSSQLConnection.getConnection();
            String query = """
               SELECT TOP 1 * FROM USER_WITH_PROFILE WHERE UserID = ?
            """;            
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setContactNumber(rs.getString("User_Contact"));
                user.setUserRole(rs.getString("User_Role"));
                user.setUserInfoId(rs.getInt("UserInfo_ID"));
                user.setFirstName(rs.getString("First_Name"));
                user.setLastName(rs.getString("Last_Name"));
                user.setBarangay(rs.getString("Barangay"));
                user.setStreet(rs.getString("Street"));
                user.setHouseNumber(rs.getString("House_Number"));
                user.setRegion(rs.getString("Region"));
                user.setProvince(rs.getString("Province"));
                user.setMunicipality(rs.getString("Municipality"));
                
                return user;
            }
            
            return null;
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to retrieved user an error occured in our end.");
        } finally {
            MSSQLConnection.closeResources(rs, pst);
        }    
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = MSSQLConnection.getConnection();
            String query = """
               SELECT * FROM USER_WITH_PROFILE
            """;            
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            while (rs.next()) {       
                User user = new User();
                user.setUserId(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setContactNumber(rs.getString("User_Contact"));
                user.setUserRole(rs.getString("User_Role"));
                user.setUserInfoId(rs.getInt("UserInfo_ID"));
                user.setFirstName(rs.getString("First_Name"));
                user.setLastName(rs.getString("Last_Name"));
                user.setBarangay(rs.getString("Barangay"));
                user.setStreet(rs.getString("Street"));
                user.setHouseNumber(rs.getString("House_Number"));
                user.setRegion(rs.getString("Region"));
                user.setProvince(rs.getString("Province"));
                user.setMunicipality(rs.getString("Municipality"));
                
                users.add(user);
            }
            
            return users;
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to retrieved users an error occured in our end.");
        } finally {
            MSSQLConnection.closeResources(rs, pst);
        } 
    }

    @Override
    public boolean update(User user) throws SQLException {
        //update process...
    }

    @Override
    public boolean delete(int id) throws SQLException {
        //delete process...
    }
}
```

### Finally Preview for Java Source Code: Controller Packages 
In above it show case what model is and what services different from model. While services works on database transaction and deal with model classes, this controller classes
coordinates application workflows, handles requests, and processes logic as the Controller layer in MVC. Controllers interact with services and models to manage specific tasks.

```java
package controllers;

//other imports...
import models.User;
import services.UserService;
import core.Session;
import enums.UserRoles;
import helpers.Input;
import helpers.Response;

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

 //other methods...

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
```

### How's Controllers Used? 
Having Controllers All in with Models and Services makes effortlessly for front end developer to deal with, by just calling the method for specific task and the job gets done. Below is the java sources showcasing usage of controllers method when it comes frontend.

```java
package views;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.*;
import javax.swing.border.*;
//other imports...
import models.User;
import controllers.UserController;
import core.Session;
import helpers.Response;

public class LoginForm extends JFrame implements ActionListener{
   //other codes...
  @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registerButton){
            controller.showRegisterFrame(this);
        }
        if(e.getSource() == loginButton){
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);
            Response<User> loginResponse = USER_CONTROLLER.loginUser(usernameField.getText(), password);
            
            if (loginResponse.isSuccess()) {
                User user = Session.getLoggedInUser();//loginResponse.getData();
                controller.setUser(user);
                controller.menuFrame.setCartItemCount();
                controller.friesFrame.setCartItemCount();
                controller.drinksFrame.setCartItemCount();
                controller.riceMealsFrame.setCartItemCount();
                controller.sandwichesFrame.setCartItemCount();
                int count = controller.menuFrame.countUserOrders(user.getUserId());
                controller.setOrderCount(count);
                
                JOptionPane.showMessageDialog(null, loginResponse.getMessage() + "\n" + "Welcome Back, " + user.getFullName(), "Login Suceessful", JOptionPane.INFORMATION_MESSAGE);
                controller.homeFrame.setVisible(false);
                controller.homeFrame.login.setVisible(false);
                controller.homeFrame.logout.setVisible(true);
                
                if(user.getUserRole().equalsIgnoreCase("admin")){
                    //controller.showDashboardFrame(controller.homeFrame);
                    AdminDashboardFrame adFrame = new AdminDashboardFrame(controller);
                    adFrame.setVisible(true);
                }else{
                    controller.homeFrame.setVisible(true);
                }
                this.dispose();
                //navigateToDashboard(user);
            } else {
                JOptionPane.showMessageDialog(null, loginResponse.getMessage(), "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
  //other codes...
}
```

For access to the complete source code and repository, visit: 
[Project Repository](https://github.com/javecilla/BSIT2FG2_PROJECT_IM-OOP-EDP-ADB-IDA)

## Technologies Used
- **Java**: Core programming language (JDK 23).
- **Java Swing**: GUI framework for the desktop interface.
- **MS SQL Server**: Primary database for storing application data.
- **MS Access**: Legacy database support via UCanAccess JDBC driver.
- **JDBC**: Java Database Connectivity for database interactions.
- **NetBeans**: Core programming language (JDK 23).
- **Java**: IDE used for development (version 24 or compatible).

## Prerequisites
Before setting up the project, ensure you have the following installed:
- **Java Development Kit (JDK)**: Version 23 or later. [Download](https://www.oracle.com/ph/java/technologies/downloads/)
- **NetBeans IDE**: Version 24 or compatible (optional, but recommended). [Download](https://netbeans.apache.org/front/main/download/)
- **MS SQL Server**: Express Edition. [Download](https://www.microsoft.com/en-us/sql-server/sql-server-downloads)
- **MS SQL Server JDBC Driver**: Download from [Microsoft](https://learn.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver16)
- **UCanAccess JDBC Driver**: For MS Access support, download from [UCanAccess](https://ucanaccess.sourceforge.net/site.html)
- **MS Access**: For MS Database Access (Optional)

## Project Installation and Setup
1. Install MS SQL Developer version
2. Install standalone server
3. Enable Login in SQL authentication
4. Download Microsoft SQL JDBC Driver
5. Move MS SQL auth.dll file to Java SDK
6. Enable TCP IP port for SQL Server
7. Import JAR file to protect library in java compiler
8. Configure Database connection with associated credentials.

## Project Structure
```
BSIT2FG2_PROJECT_IM-OOP-EDP-ADB-IDA/
├── src/
│   ├── config/                           # Configuration settings, including database connections, to streamline management.
│   ├── controllers/                      # Coordinates application workflows, handles requests, and processes logic as the Controller layer in MVC. Controllers interact with services and models to manage specific tasks.
│   ├── core/                             # Contains essential classes and foundational components that define the application's core functionality. These classes are not tied to any specific layer (e.g., MVC) but represent key abstractions, utilities, or domain-specific logic that other packages rely on. The core package ensures reusability and encapsulation of central features.
│   ├── enums/                            # Stores predefined constants like status codes and categories for consistent and readable code.
│   ├── exceptions/                       # Contains custom exception classes for precise error control and user-friendly error reporting.
│   ├── helpers/                          # Provides utility methods for common tasks such as data formatting and input validation, enhancing reusability.
│   ├── interfaces/                       # Defines interface contracts to promote polymorphism and abstraction, enabling extensibility.
│   ├── models/                           # Represents data structures and encapsulates database tables. Models implement the Model layer in MVC, ensuring data encapsulation and manipulation consistency.
│   ├── services/                         # Handles complex business logic beyond the controllers' like database transactions, scope, supporting abstraction, inheritance, and code reuse.
│   └── views/                            # Manages UI presentation and user input as part of the View layer in MVC.
│       └── gui/                          # Java Swing GUI classes       
├── vendors/                              # External libraries (JDBC drivers)
│   ├── mssql-jdbc-x.x.x.jre11.jar        # MS SQL Server JDBC driver
│   └── ucanaccess-x.x.x.jar              # UCanAccess driver (with dependencies)
├── docs/                                 # Constains the application documentations.
├── .gitignore
├── README.md
├── build.xml (or build.gradle)           # Build file (if using Maven/Gradle)
├── desktop.ini
└── manifest.mf
```

## Additional Resources

**System Report:** For a detailed report, refer to the documentation: [OOP-Project-Documentation](https://bulsumain-my.sharepoint.com/:w:/r/personal/2023100300_ms_bulsu_edu_ph/_layouts/15/Doc.aspx?sourcedoc=%7B99CADDA3-A216-4E13-9B3A-59EEA76BE35E%7D&file=Document%201.docx&action=default&mobileredirect=true).  
**UML Class Diagram:** Explore object relationships through the UML Class Diagram: [UML Diagram](https://lucid.app/lucidchart/52f8ebd5-3998-4227-bdbc-c2304db243d2/edit?viewport_loc=-1636%2C141%2C3371%2C1346%2C0_0&invitationId=inv_c9475dd8-c346-4a94-b439-b54470b0287a).

This project combines foundational features from its initial development with new enhancements. Below is an overview of both:
- [Old features from 2nd Year-1st Semester](./docs/README-IM-OOP.md)
  - Core functionality developed during the Information Management (IM) and Object-Oriented Programming (OOP) phases.
  - Refer to the linked documentation for a detailed list.
- [Old features from 2nd Year-2nd Semester](./docs/README-EDP-ADB-IDA.md)
  - Advanced features introduced in the Enterprise Data Processing (EDP), Advanced Database (ADB), and Integrated Data Analytics (IDA) phases.
  - Includes support for MS SQL Server, improved GUI, and more—see the linked documentation for specifics.

## Rules
Adherence to consistent coding practices is crucial. Please review the repository’s coding standards: [README.STANDARDS.md](./docs/README.STANDARDS.md)

## Teams

#### Development Team
- **Jerson Valdez** Front End (UI & UX) 
- **Jerome Avecilla** Back End
#### Database Team
- **Gillian Bandiola Sioco** Entity Relationship Diagram (ERD)  
- **Pauline Caballero** Enhanced Entity-Relationship Diagram (EERD)  and Relational Data Model (RDM)
- **Raizen Acuña** Database Optimization, Converter ERD to MS Access to MS SQL Server 
#### Research and Documentation
- **Francis Palma** Researcher and Documentation 

## License
This project is for educational purposes and does not include a formal license.
