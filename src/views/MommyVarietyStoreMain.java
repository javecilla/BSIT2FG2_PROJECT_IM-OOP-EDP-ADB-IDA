package views;

import java.util.List;
import javax.swing.*;

import models.*;
import controllers.*;
import helpers.*;
import enums.*;
import core.*;

public class MommyVarietyStoreMain {
    public double balance = 0;
    public String userRole = "";
    public String quantityType = "add";
    public int userPayment;
    public static int categoriesCounter = 0;
    public static int foodsCounter = 0;
    public int foodCounter = 0;
    public boolean startPanel = true;
    public boolean loggedIn = false;
    
    protected static final UserController USER_CONTROLLER = new UserController();
    protected static final IngredientController INGREDIENT_CONTROLLER = new IngredientController();
    protected static final CategoryController CATEGORY_CONTROLLER = new CategoryController();
    protected static final FoodController FOOD_CONTROLLER = new FoodController();
    protected static final CartController CART_CONTROLLER = new CartController();
    protected static final CourierController COURIER_CONTROLLER = new CourierController();
    
    private Cart found;
    private User userHolder;
   
    
    public static void main(String[] args) {
        MommyVarietyStoreMain store = new MommyVarietyStoreMain();
        store.home();
    }
    
    void home(){
        while(true){
            Object[] options = {"LOG IN", "REGISTER"};
        
            // Display JOptionPane with custom buttons
            int choice = JOptionPane.showOptionDialog(null, "=====================WELCOME TO MOMMY'S VARIETY STORE======================\n", "MOMMY'S VARIETY STORE", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            // Handle the button clicks
            switch (choice) {
                case 0:
                    handleLogin();
                    break;
                case 1:
                    handleRegistration();
                    break;
                default:
                    // Code if no button is clicked or the dialog is closed
                    systemExit();
                    break;
            }
        }
    }
    
    void clientDashboard(User user){
        Object[] options = {"ORDER NOW", "PROFILE","LOG OUT"};
        
            // Display JOptionPane with custom buttons
            int choice = JOptionPane.showOptionDialog(null, "=====================WELCOME TO MOMMY'S VARIETY STORE======================\nWelcome back!, " + user.getFullName(), "MOMMY'S VARIETY STORE", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch(choice){
                case 0:
                    foodCategoryChoice();
                    break;
                case 1:
                    showProfile(user);
                    clientDashboard(user);
                    break;
                case 2:
                    home();
                    break;
                default:
                    systemExit();
                    break;
            }
    }
    
    void foodCategoryChoice(){
        showCategoryMenu();
    }
    
    void systemExit(){
        int exitIndex = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit confirmation", JOptionPane.YES_NO_OPTION);
            if(exitIndex == JOptionPane.YES_OPTION){
                System.exit(0);
            }else{
                if(exitIndex == JOptionPane.CLOSED_OPTION){
                    System.exit(0);
                }
            return;
            }
    }
    
    void handleLogin() {
    while (true) {
        //System.out.println("\nLogin to your account");
        //System.out.print("Username: ");
        //String usernameInput = SCANNER.next().trim();
        //System.out.print("Password: ");
        //String passwordInput = SCANNER.next().trim();

        String usernameInput = JOptionPane.showInputDialog(null, "Login to your account\n\nUsername: ","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
        if(usernameInput == null){
            return;
        }
        
        String passwordInput = JOptionPane.showInputDialog(null, "Login to your account\n\nPassword: ","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
        if(passwordInput == null){
            return;
        }
        
        // Attempt login with username and password
        Response<User> loginResponse = USER_CONTROLLER.loginUser(usernameInput, passwordInput);
        
        if (loginResponse.isSuccess()) {
            User user = loginResponse.getData();
            userHolder = user;
            balance = 0;
            //System.out.println(loginResponse.getMessage());
            JOptionPane.showMessageDialog(null, loginResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
            //JOptionPane.showMessageDialog(null, "Welcome Back, " + user.getFullName(), "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
            //System.out.println("\nWelcome back, " + user.getFullName());
            
            // Navigate to appropriate dashboard based on user role
            navigateToDashboard(user);
            break; // Exit login loop after successful login and dashboard navigation
        } else {
            //System.out.println(loginResponse.getMessage());
            JOptionPane.showMessageDialog(null, loginResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
            //System.out.println("Would you like to try again? (y/n)");
            int option = JOptionPane.showConfirmDialog(null, "Would you like to try again?", "MOMMY'S VARIETY STORE",JOptionPane.YES_NO_OPTION);
            
            if(option == JOptionPane.NO_OPTION){
                break;
            }
            //String tryAgain = SCANNER.next().trim().toLowerCase();
            
            /*if (!tryAgain.equals("y")) {
            break; // Exit login loop if user doesn't want to try again
            }*/
        }

    }
}
    
     void handleRegistration() {
         String firstName = JOptionPane.showInputDialog(null,"Registering Your Account\n\nEnter your First Name:","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
         if(firstName == null){
             home();
         }
         String lastName = JOptionPane.showInputDialog(null,"Registering Your Account\n\nEnter your Last Name:","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
         if(lastName == null){
             home();
         }
         String barangay = JOptionPane.showInputDialog(null,"Registering Your Account\n\nEnter your Barangay:","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
         if(barangay == null){
             home();
         }
         String street = JOptionPane.showInputDialog(null,"Registering Your Account\n\nEnter your Street:","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
         if(street == null){
             home();
         }
         String houseNumber = JOptionPane.showInputDialog(null,"Registering Your Account\n\nEnter your House Number:","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
         if(houseNumber == null){
             home();
         }
         String region = JOptionPane.showInputDialog(null,"Registering Your Account\n\nEnter your Region:","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
         if(region == null){
             home();
         }
         String province = JOptionPane.showInputDialog(null,"Registering Your Account\n\nEnter your Province:","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
         if(province == null){
             home();
         }
         String municipality = JOptionPane.showInputDialog(null,"Registering Your Account\n\nEnter your Municipality:","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
         if(municipality == null){
             home();
         }
    UserInfo userInfo = new UserInfo(
       0,
       firstName,
       lastName,
       barangay,
       street,
       houseNumber,
       region,
       province,
       municipality
    );
        String username = JOptionPane.showInputDialog(null,"Registering Your Account\n\nEnter your Username:","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
         if(username == null){
             home();
         }
         String password = JOptionPane.showInputDialog(null,"Registering Your Account\n\nEnter your Password:","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
         if(password == null){
             home();
         }
         String email = JOptionPane.showInputDialog(null,"Registering Your Account\n\nEnter your Email Address:","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
         if(email == null){
             home();
         }
         String contactNumber = JOptionPane.showInputDialog(null,"Registering Your Account\n\nEnter your Contact Information:","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
         if(contactNumber == null){
             home();
         }
    User user = new User(
        0,
        username,
        password,
        email,
        contactNumber,
        userInfo
    );
    user.setUserRole(Text.capitalizeFirstLetterInString(UserRoles.CLIENT.name()));
    Response<User> registrationResponse = USER_CONTROLLER.registerUser(user);
    
        
     if(registrationResponse.isSuccess()) {
         //System.out.println(registrationResponse.getMessage());
         JOptionPane.showMessageDialog(null, registrationResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
        userHolder = user;
     } else {   
         //System.out.println(registrationResponse.getMessage());
         JOptionPane.showMessageDialog(null, registrationResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
     }
}

    
     void navigateToDashboard(User user) {
    
            if (user.getUserRole().equals(Text.capitalizeFirstLetterInString(UserRoles.CLIENT.name()))) {
                clientDashboard(user);
            } else if (user.getUserRole().equals(Text.capitalizeFirstLetterInString(UserRoles.ADMIN.name()))) {
                adminSection(user);
            } else {
                //System.out.println("No dashboard found for this user role.");
                JOptionPane.showMessageDialog(null, "No dashboard found for this user role.", "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            }
        }
    
    
     void showAllCouriers() {
        // Fetch all ingredients (not just low stock)
        Response<List<Courier>> couriersResponse = COURIER_CONTROLLER.getAllCouriers();
        if (couriersResponse.isSuccess()) {
            List<Courier> couriers = couriersResponse.getData();
            //displayCouriers(couriers);
            JOptionPane.showMessageDialog(null, "<html><pre>"+displayCouriers(couriers)+"<html><pre>", "MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
        } else {
            //System.out.println("Error: " + couriersResponse.getMessage());
            JOptionPane.showMessageDialog(null, couriersResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     void showCouriersByStatus() {
         /*System.out.println("[1] - Available");
         System.out.println("[2] - Unavailable");
         System.out.print("Enter choice: ");
         int choice = SCANNER.nextInt();*/
         boolean print = false;
        String status = JOptionPane.showInputDialog(null,"[1] Available\n"
                                                        + "[2] Unavailable\n"
                                                        + "[3] Back", "MOMMYS VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
        
        if(status == null){
            return;
        }
        if(status.equals("1")) {
            status = Text.capitalizeFirstLetterInString(CourierStatus.AVAILABLE.name());
            print = true;
        } else if(status.equals("2")) {
            status = Text.capitalizeFirstLetterInString(CourierStatus.UNAVAILABLE.name());
            print = true;
        }else if (status.equals("3")){
            adminSection(userHolder);
        }else{
            JOptionPane.showMessageDialog(null, "Invalid Input", "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            showCouriersByStatus();
        }
        if(print){
            // Fetch courier
            Response<List<Courier>> couriersByStatus = COURIER_CONTROLLER.getCouriersByStatus(status);
            if (couriersByStatus.isSuccess()) {
                List<Courier> couriers = couriersByStatus.getData();
                //displayCouriers(couriers);
                JOptionPane.showMessageDialog(null, "<html><pre>"+displayCouriers(couriers)+"<html><pre>", "MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
            } else {
                //System.out.println("Error: " + couriersByStatus.getMessage());
                JOptionPane.showMessageDialog(null, couriersByStatus.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
     
     void showCourierById() {
        String input = JOptionPane.showInputDialog(null, 
            "Enter Courier ID:", 
            "MOMMY'S VARIETY STORE", 
            JOptionPane.PLAIN_MESSAGE);

        if (input == null) {
            return; 
        }

            try {
                int id = Integer.parseInt(input); 
                Response<Courier> courierResponse = COURIER_CONTROLLER.getCourierById(id);
                if (courierResponse.isSuccess()) {
                    Courier courier = courierResponse.getData();
                    String details = String.format(
                        "Courier Details:\n" +
                        "Rider ID: %d\n" +
                        "First Name: %s\n" +
                        "Last Name: %s\n" +
                        "Company: %s\n" +
                        "Contact Number: %s\n" +
                        "Status: %s",
                        courier.getRiderId(),
                        courier.getFirstName(),
                        courier.getLastName(),
                        courier.getCompany(),
                        courier.getContactNumber(),
                        courier.getStatus()
                    );

                    JOptionPane.showMessageDialog(null, 
                        "<html><pre>" + details + "</pre></html>", 
                        "Courier Found", 
                        JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, 
                        courierResponse.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, 
                    "Invalid ID format. Please enter a valid number.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                showCourierById(); // Restart if invalid input
            }
        }

     
       public void updateCourierDetails() {
    try {
        String riderIdStr = JOptionPane.showInputDialog(null, "Enter Rider ID:", "Update Courier", JOptionPane.QUESTION_MESSAGE);
        if (riderIdStr == null || riderIdStr.trim().isEmpty()) return;

        int riderId = Integer.parseInt(riderIdStr);

        // Fetch the existing courier by ID
        Courier existingCourier = COURIER_CONTROLLER.getCourierById(riderId).getData();
        if (existingCourier == null) {
            JOptionPane.showMessageDialog(null, "Courier not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Collect the new details
        String firstName = JOptionPane.showInputDialog(null, "First Name:", existingCourier.getFirstName());
        String lastName = JOptionPane.showInputDialog(null, "Last Name:", existingCourier.getLastName());
        String company = JOptionPane.showInputDialog(null, "Company:", existingCourier.getCompany());
        String contactNumber = JOptionPane.showInputDialog(null, "Contact Number:", existingCourier.getContactNumber());
        String status = JOptionPane.showInputDialog(null, "Status (Available/Unavailable):", existingCourier.getStatus());

        // Check if the user canceled the update
        if (firstName == null || lastName == null || company == null || contactNumber == null || status == null) {
            JOptionPane.showMessageDialog(null, "Update canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Create an updated courier object
        Courier updatedCourier = new Courier(riderId, firstName, lastName, company, contactNumber, status);

        // Call the controller to update the courier
        Response<Courier> response = COURIER_CONTROLLER.updateCourier(updatedCourier);

        // Display the result to the user
        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(null, "Courier updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Update failed: " + response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Invalid Rider ID!", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

       
       public void updateCourierStatus() {
    // Prompt the user for the courier ID
    String riderIdInput = JOptionPane.showInputDialog(null, "Enter Courier ID:", "Update Courier Status", JOptionPane.PLAIN_MESSAGE);
    
    if (riderIdInput == null || riderIdInput.trim().isEmpty()) {
        return; // If input is empty or canceled, return without doing anything
    }

    int riderId;
    try {
        riderId = Integer.parseInt(riderIdInput); // Convert to integer
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Invalid ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        return; // Exit if invalid ID
    }

    // Validate if the courier exists by ID
    Response<Courier> courierResponse = COURIER_CONTROLLER.getCourierById(riderId);
    if (!courierResponse.isSuccess()) {
        JOptionPane.showMessageDialog(null, "Courier not found with ID: " + riderId, "Error", JOptionPane.ERROR_MESSAGE);
        return; // Exit if courier with the given ID doesn't exist
    }

    // Courier exists, now prompt the user for the new status
    Courier courier = courierResponse.getData();
    String currentStatus = courier.getStatus();
    String newStatus = JOptionPane.showInputDialog(null, "Current Status: "+ courier.getStatus() +"\n\n[1] Available\n[2] Unavailable\nEnter choice:", "Update Courier Status", JOptionPane.PLAIN_MESSAGE);
    
    if (newStatus == null || newStatus.trim().isEmpty()) {
        return; // If input is empty or canceled, return without doing anything
    }

    // Convert status choice to string
    if (newStatus.equals("1")) {
        newStatus = "Available";
    } else if (newStatus.equals("2")) {
        newStatus = "Unavailable";
    } else {
        JOptionPane.showMessageDialog(null, "Invalid status choice.", "Error", JOptionPane.ERROR_MESSAGE);
        return; // Exit if invalid status
    }

    // Call the controller to update the courier status
    Response<Courier> response = COURIER_CONTROLLER.updateCourierStatus(riderId, newStatus);

    if (response.isSuccess()) {
        JOptionPane.showMessageDialog(null, response.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(null, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

       void addNewCourier() {
    try {
        
        String firstName = JOptionPane.showInputDialog(null,
                "Enter First Name:",
                "Add New Courier - First Name",
                JOptionPane.PLAIN_MESSAGE);
        if (firstName == null || firstName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "First Name cannot be empty!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        String lastName = JOptionPane.showInputDialog(null,
                "Enter Last Name:",
                "Add New Courier - Last Name",
                JOptionPane.PLAIN_MESSAGE);
        if (lastName == null || lastName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Last Name cannot be empty!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

     
        String company = JOptionPane.showInputDialog(null,
                "Enter Company Name:",
                "Add New Courier - Company",
                JOptionPane.PLAIN_MESSAGE);
        if (company == null || company.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Company cannot be empty!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

     
        String contactNumber = JOptionPane.showInputDialog(null,
                "Enter Contact Number:",
                "Add New Courier - Contact Number",
                JOptionPane.PLAIN_MESSAGE);
        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Contact Number cannot be empty!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

      
        String status = JOptionPane.showInputDialog(null,
                "Enter Status (Available/Unavailable):",
                "Add New Courier - Status",
                JOptionPane.PLAIN_MESSAGE);
        if (status == null || (!status.equalsIgnoreCase("Available") && !status.equalsIgnoreCase("Unavailable"))) {
            JOptionPane.showMessageDialog(null,
                    "Invalid Status! Please enter 'Available' or 'Unavailable'.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

      
        Courier courier = new Courier();
        courier.setFirstName(firstName);
        courier.setLastName(lastName);
        courier.setCompany(company);
        courier.setContactNumber(contactNumber);
        courier.setStatus(status);


        Response<Courier> response = COURIER_CONTROLLER.addCourier(courier);


        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(null,
                    "Courier added successfully!\n" + response.getData(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Failed to add courier: " + response.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
                "Something went wrong: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
 }
 
        
        void deleteCourier() {
            String input = JOptionPane.showInputDialog(null, 
                "Enter Courier ID to delete:", 
                "MOMMY'S VARIETY STORE", 
                JOptionPane.PLAIN_MESSAGE);

            if (input == null) {
                return; // User canceled
            }

            try {
                int id = Integer.parseInt(input); // Parse input

                // Confirm deletion
                int confirm = JOptionPane.showConfirmDialog(null, 
                    "Are you sure you want to delete Courier with ID: " + id + "?", 
                    "Confirm Deletion", 
                    JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    Response<String> deleteResponse = COURIER_CONTROLLER.deleteCourier(id);
                    if (deleteResponse.isSuccess()) {
                        JOptionPane.showMessageDialog(null, 
                            deleteResponse.getMessage(), 
                            "Success", 
                            JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, 
                            deleteResponse.getMessage(), 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, 
                    "Invalid ID format. Please enter a valid number.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }


    
    String displayCouriers(List<Courier> couriers) {
        StringBuilder courierReport = new StringBuilder();

        // Add header
        courierReport.append(String.format("%-10s %-30s %-10s %-20s %-10s\n", 
            "Courier ID", "Name", "Company", "Contact No.", "Status"));
        courierReport.append("---------------------------------------------------------------\n");

        // Add each courier's details
        for (Courier courier : couriers) {
            String name = courier.getFirstName() + ", " + courier.getLastName();
            courierReport.append(String.format("%-10s %-30s %-10s %-20s %-10s\n", 
                courier.getRiderId(), 
                name, 
                courier.getCompany(), 
                courier.getContactNumber(),
                courier.getStatus()
            ));
        }

        // Add total records at the end
        courierReport.append("\nTotal Records: ").append(couriers.size());

        // Display the result using JOptionPane
        //JOptionPane.showMessageDialog(null, courierReport.toString(), "Courier List", JOptionPane.INFORMATION_MESSAGE);
        return courierReport.toString();
    }

    
    void adminSection(User user){
        while(true){
         Object[] options = {"MANAGE STOCKS","MANAGE COURIER", "PROFILE","LOG OUT"};
            int adminChoice = JOptionPane.showOptionDialog(null, "===================================WELCOME TO ADMIN SECTION====================================\n" + "Welcome Back!, " + user.getFullName(), "MOMMY'S VARIETY STORE", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            
            switch(adminChoice){
                case 0:
                    String input = JOptionPane.showInputDialog(null, "--- Select Action ---\n"
                                                                    + "[1] Show All\n"
                                                                    + "[2] Show All (Low Stocks)\n"
                                                                    + "[3] Update (Stocks)\n"
                                                                    + "[4] Update (Re-order Points)\n"
                                                                    + "[5] Show One (All Information)\n"
                                                                    + "[6] Check One (Reorder or Not)\n"
                                                                    + "[7] Back","MOMMY'S VERIETY STORE", JOptionPane.PLAIN_MESSAGE);
                    if(input == null){
                        break;
                    }
                    switch(input){
                        case "1":
                            showAllStocks();
                            break;
                        case "2":
                            showLowStock();
                            break;
                        case "3":
                            updateStocks();
                            break;
                        case "4":
                            updateReorderPoints();
                            break;
                        case "5":
                            showOneItemInformation();
                            break;
                        case "6":
                            checkForReorder();
                            break;
                        case "7":
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "INVALID INPUT!!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);      
                    }
                    break;
                case 1:
                    input = JOptionPane.showInputDialog(null, "--- Select Action ---\n"
                                                            + "[1] Show All\n"
                                                            + "[2] Show All (By Status)\n"
                                                            + "[3] Update Courier Details\n"
                                                            + "[4] Update Courier Status\n"
                                                            + "[5] delete\n"
                                                            + "[6] Show One (All Information)\n"
                                                            + "[7] Add Couriers\n"
                                                            + "[8] Back","MOMMY'S VERIETY STORE", JOptionPane.PLAIN_MESSAGE);
                        if(input == null){
                            break;
                        }
                        switch(input){
                            case "1":
                                showAllCouriers();
                                break;
                            case "2":
                                showCouriersByStatus();
                                break;
                            case "3":
                                updateCourierDetails();
                                break;
                            case "4":
                                updateCourierStatus();
                                break;
                            case "5":
                                deleteCourier();
                                break;
                            case "6":
                                showCourierById();
                                break;
                            case "7":
                                addNewCourier();
                                break;
                            case "8":
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "INVALID INPUT!!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);      
                        }
                    break;
                case 2:
                    showProfile(user);
                    break;
                case 3:
                    home();
                    break;
                default:
                    systemExit();
                    break;
            }
        }
    }
    
    void showLowStock() {
        // Fetch ingredients with low stock
        Response<List<Ingredient>> ingredientsWithLowStocksResponse = INGREDIENT_CONTROLLER.getAllIngredientsLowStocks();
        if (ingredientsWithLowStocksResponse.isSuccess()) {
            List<Ingredient> ingredients = ingredientsWithLowStocksResponse.getData();
            //displayIngredientItems(ingredients);
            JOptionPane.showMessageDialog(null, "<html><pre>" + displayIngredientItems(ingredients) + "</pre></html>","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, ingredientsWithLowStocksResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
        }
    }

      void showOneItemInformation() {
        while(true){
          try{
              //System.out.print("Enter ingredient id: ");
            String ingredientIDInput = JOptionPane.showInputDialog(null,"Enter ingredient id: ","MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
        
            if(ingredientIDInput == null){
                return;
            }

            Response<Ingredient> ingredientsResponse = INGREDIENT_CONTROLLER.getIngredientById(Integer.parseInt(ingredientIDInput));
            if (ingredientsResponse.isSuccess()) {
                Ingredient ingredient = ingredientsResponse.getData();
                //ingredient.display();
                JOptionPane.showMessageDialog(null, ingredient.display(),"MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
            } else {
                //System.out.println("Error retrieving ingredient: " + ingredientsResponse.getMessage());
                JOptionPane.showMessageDialog(null,"Error retrieving ingredient: " + ingredientsResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            }
            
          }catch(NumberFormatException er){
            JOptionPane.showMessageDialog(null, er, "ERROR!", JOptionPane.ERROR_MESSAGE);
          }
        }
    }

      void showAllStocks() {
        // Fetch all ingredients (not just low stock)
        Response<List<Ingredient>> ingredientsResponse = INGREDIENT_CONTROLLER.getAllIngredients();
        if (ingredientsResponse.isSuccess()) {
            List<Ingredient> ingredients = ingredientsResponse.getData();
            JOptionPane.showMessageDialog(null, "<html><pre>" + displayIngredientItems(ingredients) + "</pre></html>","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, ingredientsResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
      void checkForReorder() {
        //System.out.print("Enter ingredient id: ");while(true){
          while(true){
            try{
                String ingredientIDInput = JOptionPane.showInputDialog(null,"Enter ingredient id: ","MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
        
                if(ingredientIDInput == null){
                    return;
                }
        
                    Response<String> reorderCheckResponse = INGREDIENT_CONTROLLER.checkReorderNeed(Integer.parseInt(ingredientIDInput));
                    if(reorderCheckResponse.isSuccess()) {
                    //System.out.println(reorderCheckResponse.getMessage());
                    JOptionPane.showMessageDialog(null, reorderCheckResponse.getMessage(),"MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    //System.out.println("Error checking ingredient: " + reorderCheckResponse.getMessage());
                    JOptionPane.showMessageDialog(null,"Error checking ingredient: " + reorderCheckResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
                }
                    
            }catch(NumberFormatException er){
                JOptionPane.showMessageDialog(null, er, "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
          }
    }

      void updateStocks(){
        while(true){
          try{
            //System.out.print("Enter ingredient id: ");
            String ingredientIDInput = JOptionPane.showInputDialog(null,"Enter ingredient id: ","MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
        
            if(ingredientIDInput == null){
                return;
            }

            //System.out.print("Enter quantity: ");
            String ingredientQuantityInput = JOptionPane.showInputDialog(null,"Enter quantity: ","MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
        
            if(ingredientQuantityInput == null){
                return;
            }
        
            Response<Ingredient> updateStocksResponse = INGREDIENT_CONTROLLER.updateQuantity(
                Integer.parseInt(ingredientIDInput),
                Integer.parseInt(ingredientQuantityInput)
            );
            if(updateStocksResponse.isSuccess()) {
                //System.out.println(updateStocksResponse.getMessage());
                JOptionPane.showMessageDialog(null, updateStocksResponse.getMessage(),"MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
            } else {
                //System.out.println("Error updating ingredient: " + updateStocksResponse.getMessage());
                JOptionPane.showMessageDialog(null, "Error updating ingredient: " + updateStocksResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            }
            }catch(NumberFormatException er){
                JOptionPane.showMessageDialog(null, er, "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
          }
        
    }
    
      void updateReorderPoints() {
        while(true){
          try{
            //System.out.print("Enter ingredient id: ");
            String ingredientIDInput = JOptionPane.showInputDialog(null,"Enter ingredient id: ","MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
        
            if(ingredientIDInput == null){
                return;
            }

            // System.out.print("Enter reorder points: ");
            String ingredientReorderPointsInput = JOptionPane.showInputDialog(null,"Enter reorder points: ","MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
        
            if(ingredientReorderPointsInput == null){
                return;
            }
        
            Response<Ingredient> updateReorderPointsResponse = INGREDIENT_CONTROLLER.updateReorderPoints(
                Integer.parseInt(ingredientIDInput),
                Integer.parseInt(ingredientReorderPointsInput)
            );
            if(updateReorderPointsResponse.isSuccess()) {
                //System.out.println(updateReorderPointsResponse.getMessage());
                JOptionPane.showMessageDialog(null, updateReorderPointsResponse.getMessage(),"MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
            } else {
                //System.out.println("Error updating ingredient: " + updateReorderPointsResponse.getMessage());
                JOptionPane.showMessageDialog(null, "Error updating ingredient: " + updateReorderPointsResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            }
          }catch(NumberFormatException er){
                JOptionPane.showMessageDialog(null, er, "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
          }
    }

    public String displayIngredientItems(List<Ingredient> ingredients) {
        StringBuilder output = new StringBuilder();

        // Header with formatted alignment
        output.append(String.format("%-15s %-30s %-15s %-20s%n", 
            "Ingredient ID", "Name", "Current Stock", "Reorder Point"));
        output.append("-------------------------------------------------------------------------------\n");

        // Rows
        for (Ingredient ingredient : ingredients) {
            output.append(String.format("%-15s %-30s %-15s %-20s%n", 
                ingredient.getIngredientId(), 
                ingredient.getIngredientName(),
                ingredient.getQuantity(), 
                ingredient.getReorderPoint()));
    }

    // Footer
    output.append(String.format("%nTotal Records: %d", ingredients.size()));
    
        return output.toString();
    }
    
    void showProfile(User user) {
        JOptionPane.showMessageDialog(null, "YOUR PROFILE:\n\nFull Name: " + user.getFullName() + "\nAddress: " + user.getFullAddress() + "\n\nYour Balance: " + balance,"MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
    }
    
    void showCart() {
    Object[] options = {"ORDER AGAIN", "EDIT QUANTITY", "REMOVE ITEM", "CHECK OUT"};
    StringBuilder cartHolder = new StringBuilder();

    // Header
    cartHolder.append("\n----------------------------- Your Cart ------------------------------\n");
    cartHolder.append(String.format("%-5s %-35s %-10s %-10s %-10s%n", 
        "No.", "Food Name", "Price", "Qty", "Total"));
    cartHolder.append("-----------------------------------------------------------------------\n");

    // Fetch cart data
    Response<Cart> cartResponse = CART_CONTROLLER.viewCart();

    if (cartResponse.isSuccess()) {
        Cart cart = cartResponse.getData();

        if (cart.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Your cart is empty.", "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
            foodCategoryChoice();
        } else {
            int itemNumber = 1;

            // Add cart items
            for (CartItem item : cart.getItems()) {
                cartHolder.append(String.format("%-5d %-35s %-10.2f %-10d %-10.2f%n", 
                    itemNumber++, 
                    item.getFoodName(), 
                    item.getFoodPrice(), 
                    item.getQuantity(), 
                    item.getTotalPrice()));
            }

            // Footer
            cartHolder.append("-----------------------------------------------------------------------\n");
            cartHolder.append(String.format("%-59s Php %.2f%n", "Total Amount:", cart.getTotalAmount()));

            // Display the cart and options
            int choice = JOptionPane.showOptionDialog(null, 
                "<html><pre>" + cartHolder + "</pre></html>", 
                "MOMMY'S VARIETY STORE", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                options, 
                options[0]);

            // Handle user choice
            switch (choice) {
                case 0: foodCategoryChoice(); // OK
                    break;
                case 1: 
                    quantityType = "edit";
                    foodCategoryChoice(); // EDIT QUANTITY
                    break;
                case 2: 
                    quantityType = "remove";
                    foodCategoryChoice(); // REMOVE ITEM
                    break;
                case 3:
                    // CHECKOUT
                     double amountPayment = 0;
            double change = 0;
            boolean changeIndicator = true;

            do {
                String paymentOption = JOptionPane.showInputDialog(null, 
                    "----- Select Payment Method -----\n" +
                    "[1] Online Payment\n" +
                    "[2] Cash On Delivery\n" +
                    "[3] Back", 
                    "MOMMY'S VARIETY STORE", 
                    JOptionPane.PLAIN_MESSAGE);

                if (paymentOption == null || paymentOption.equals("3")) {
                    // Back to the previous menu
                    showCart();
                    return;
                }

                if (paymentOption.equals("1")) {
                    // ONLINE PAYMENT
                    while (true) {
                        String paymentInput = JOptionPane.showInputDialog(null, 
                            "Payable amount: Php " + cart.getTotalAmount() + "\n\nEnter your payment amount:", 
                            "MOMMY'S VARIETY STORE", 
                            JOptionPane.PLAIN_MESSAGE);

                        if (paymentInput == null) {
                            showCart(); // Cancel and return to the cart view
                            return;
                        }

                        try {
                            amountPayment = Double.parseDouble(paymentInput);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid number.", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                            continue;
                        }

                        if (amountPayment <= 0) {
                            JOptionPane.showMessageDialog(null, "Invalid payment amount! Please enter a positive number.", 
                                "Invalid Payment", JOptionPane.ERROR_MESSAGE);
                        } else if (amountPayment < cart.getTotalAmount()) {
                            JOptionPane.showMessageDialog(null, "Insufficient payment! The total amount is Php " + cart.getTotalAmount(), 
                                "Insufficient Payment", JOptionPane.ERROR_MESSAGE);
                        } else {
                            break; // Valid payment entered
                        }
                    }

                    // Calculate change for online payment
                    change = amountPayment - cart.getTotalAmount();
                    break;

                } else if (paymentOption.equals("2")) {
                    // CASH ON DELIVERY
                    amountPayment = cart.getTotalAmount(); // No upfront payment
                    changeIndicator = false;
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Input! Please select a valid option.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } while (true);

            // Confirm or Cancel for Cash On Delivery
            while (true) {
                Object[] option = {"CANCEL", "ORDER RECEIVED"};
                int choices = JOptionPane.showOptionDialog(null, 
                    "Your order is on the way...\n\nPlease confirm:", 
                    "MOMMY'S VARIETY STORE", 
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.INFORMATION_MESSAGE, 
                    null, 
                    option, 
                    option[0]);

                if (choices == 0) {
                    // Cancel Order
                    int confirmation = JOptionPane.showConfirmDialog(null, 
                        "Are you sure you want to cancel your order?", 
                        "Cancel Order", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.QUESTION_MESSAGE);

                    if (confirmation == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(null, 
                            "Your order has been successfully canceled.\n\nYour Payment Php" + amountPayment + " will credit to your account", 
                            "Order Canceled", 
                            JOptionPane.INFORMATION_MESSAGE);
                        balance += amountPayment;
                        clientDashboard(userHolder); // Exit checkout process
                    }
                } else if (choices == 1) {
                    // ORDER RECEIVED: Process order
                    cart.setPaymentAmount(amountPayment);
                    Response<Cart> cartOrderResponse = CART_CONTROLLER.checkOutOrder();
                    if (cartOrderResponse.isSuccess()) {
                        JOptionPane.showMessageDialog(null, 
                            cartOrderResponse.getMessage(), 
                            "Order Successful", 
                            JOptionPane.INFORMATION_MESSAGE);

                        // Retrieve sales report
                        Response<List<SalesDetails>> salesDetailsResponse = CART_CONTROLLER.getOrderReports();
                        if (salesDetailsResponse.isSuccess()) {
                            List<SalesDetails> salesDetails = salesDetailsResponse.getData();
                            StringBuilder salesReport = new StringBuilder();

                            // Header
                            salesReport.append("==================== SALES REPORT ====================\n");
                            User customer = Session.getLoggedInUser();
                            salesReport.append("Name: ").append(customer.getFullName()).append("\n");

                            // Truncate long addresses
                            String address = customer.getFullAddress();
                            final int MAX_ADDRESS_LENGTH = 50;
                            if (address.length() > MAX_ADDRESS_LENGTH) {
                                address = address.substring(0, MAX_ADDRESS_LENGTH) + "...";
                            }
                            salesReport.append("Address: ").append(address).append("\n");
                            salesReport.append("Date: ").append(Date.formatToReadableDate(salesDetails.get(0).getSale().getSaleDate())).append("\n");
                            salesReport.append("Sales ID: ").append(salesDetails.get(0).getSale().getSaleId()).append("\n");
                            salesReport.append("-----------------------------------------------------\n");

                            // Table Header
                            salesReport.append(String.format("%-20s %-15s %-10s %-15s\n", "Food Name", "Price", "Quantity", "Subtotal"));
                            salesReport.append("-----------------------------------------------------\n");

                            // Table Rows
                            double netTotal = 0;
                            for (SalesDetails detail : salesDetails) {
                                double subTotal = detail.getItemQuantity() * detail.getFood().getPrice();
                                netTotal += subTotal;
                                salesReport.append(String.format("%-20s %-15.2f %-10d %-15.2f\n", 
                                    detail.getFood().getFoodName(), 
                                    detail.getFood().getPrice(), 
                                    detail.getItemQuantity(), 
                                    subTotal));
                            }

                            // Footer
                            salesReport.append("-----------------------------------------------------\n");
                            salesReport.append(String.format("%-43s Php %.2f\n", "Net Total:", netTotal));
                            salesReport.append(String.format("%-43s Php %.2f\n", "Payment:", amountPayment));
                            salesReport.append(String.format("%-43s Php %.2f\n", "Change:", change));
                            salesReport.append("=====================================================\n");

                            // Display Sales Report
                            JOptionPane.showMessageDialog(null, "<html><pre>" + salesReport.toString() + "</pre></html>", 
                                "Sales Report", 
                                JOptionPane.INFORMATION_MESSAGE);

                            // Clear cart and exit
                            cart.getItems().clear();
                            clientDashboard(userHolder);
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null, 
                                "Error: " + salesDetailsResponse.getMessage(), 
                                "Error Retrieving Sales Report", 
                                JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, 
                            "Error: " + cartOrderResponse.getMessage(), 
                            "Order Processing Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    break; // Exit the loop for any other choice
                }
            }
                            break;

                        default: foodCategoryChoice(); // Cancel/Close
                            break;
                    }
                }
            } else {
                // Handle cart fetch error
                JOptionPane.showMessageDialog(null, 
                    "Error: " + cartResponse.getMessage(), 
                    "MOMMY'S VARIETY STORE", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }


    
     void showFoodMenu(int categoryId) {
        try{
        do {
            String holder = "<html><pre>" + displayFoodListsByCategory(categoryId)+"<html><pre>"; 
            //System.out.print("Enter your choice: ");
            String input = JOptionPane.showInputDialog(null, holder + "\nEnter your choice: ", "MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
            
            if(input == null){
                foodCategoryChoice();
                return;
            }
            
            int choice = Integer.parseInt(input);

            // Check if the choice is valid for categories (1 to foodsCounter)
            if (choice > 0 && choice <= foodCounter) {
                Response<Food> foodResponse = FOOD_CONTROLLER.getFoodById(choice);
                if (foodResponse.isSuccess()) {
                    Food selectedFood = foodResponse.getData();
                    promptQuantityOrder(selectedFood.getFoodId(), selectedFood.getFoodName(), selectedFood.getPrice());
                } else {
                    //System.out.println("Error: " + foodResponse.getMessage());
                    JOptionPane.showMessageDialog(null, "Error: " + foodResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
                }
            } else if (choice == (foodCounter + 1)) {
                showCart();
                return;  // Go back to the category menu
            }else if(choice == (foodCounter + 2)){
                clientDashboard(userHolder);
                return;
            }else if(choice == (foodCounter + 3)) {
                foodCategoryChoice();
                return;
            }else {
                //System.out.println("Invalid choice. Please select again.");
                JOptionPane.showMessageDialog(null, "Invalid choice. Please select again.", "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            }
        } while (true);
        }catch(NumberFormatException er){
            JOptionPane.showMessageDialog(null, er,"ERROR!", JOptionPane.ERROR_MESSAGE);
            showFoodMenu(categoryId);
        }
    }
    
     void promptQuantityOrder(int foodId, String foodName, double foodPrice) {
        int quantity = 1;
        try{
        do {
            if (quantityType.equals("add") || quantityType.equals("edit")) {
                
                StringBuilder clientDashboard = new StringBuilder();
                clientDashboard.append("\n---------- Client Dashboard ----------\n");
                clientDashboard.append(String.format("%-15s %-30s%n", "Food Name:", foodName));
                clientDashboard.append(String.format("%-15s %-30.2f%n", "Food Price:", foodPrice));
                clientDashboard.append("--------------------------------------\n");
                clientDashboard.append("[0] Back\n\n");
                clientDashboard.append("Enter quantity: ");

                // Show input dialog
                String input = JOptionPane.showInputDialog(null, 
                    "<html><pre>" + clientDashboard + "</pre></html>", 
                    "MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);


            if(input == null){
                return;
            }
            
            quantity = Integer.parseInt(input);
            }

            if (quantity > 0) {
                if(quantityType.equals("add")){
                        Response<Cart> addToCartResponse = CART_CONTROLLER.addToCart(foodId, foodName, foodPrice, quantity);
                    if (addToCartResponse.isSuccess()) {
                        //System.out.println("Item added to cart successfully.");
                        JOptionPane.showMessageDialog(null, "Item added to cart successfully.","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
                        int choice = JOptionPane.showConfirmDialog(null, "Do you want to view your cart?", "MOMMY'S VARIETY STORE", JOptionPane.YES_NO_OPTION);
                        if(choice == JOptionPane.YES_OPTION){
                            showCart();
                        }else{
                            continue;
                        }
                    } else {
                        //System.out.println("Error: " + addToCartResponse.getMessage());
                        JOptionPane.showMessageDialog(null, "Error: " + addToCartResponse.getMessage(),"MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
                    }
                }else if(quantityType.equals("edit")){
                    Response<Cart> updateQuantityResponse = CART_CONTROLLER.updateQuantity(foodId, quantity);
                    if (updateQuantityResponse.isSuccess()) {
                        //System.out.println("Item added to cart successfully.");
                        JOptionPane.showMessageDialog(null, "Item Update quanity successfully.","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
                        quantityType = "add";
                        int choice = JOptionPane.showConfirmDialog(null, "Do you want to view your cart?", "MOMMY'S VARIETY STORE", JOptionPane.YES_NO_OPTION);
                        if(choice == JOptionPane.YES_OPTION){
                            showCart();
                        }else{
                            
                            continue;
                        }
                    } else {
                        //System.out.println("Error: " + addToCartResponse.getMessage());
                        JOptionPane.showMessageDialog(null, "Error: " + updateQuantityResponse.getMessage(),"MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
                        showCart();
                        return;
                    }
                }else if(quantityType.equals("remove")){
                    Response<Cart> removeFromCartResponse = CART_CONTROLLER.removeFromCart(foodId);
                    if (removeFromCartResponse.isSuccess()) {
                        //System.out.println("Item added to cart successfully.");
                        JOptionPane.showMessageDialog(null, "Item removed successfully.","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
                        quantityType = "add";
                        int choice = JOptionPane.showConfirmDialog(null, "Do you want to view your cart?", "MOMMY'S VARIETY STORE", JOptionPane.YES_NO_OPTION);
                        if(choice == JOptionPane.YES_OPTION){
                            showCart();
                        }else{
                            continue;
                        }
                    } else {
                        //System.out.println("Error: " + addToCartResponse.getMessage());
                        JOptionPane.showMessageDialog(null, "Error: " + removeFromCartResponse.getMessage(),"MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
                        showCart();
                        return;
                    }
                }
            } else if (quantity == 0) { 
                return;  // Go back to the food menu
            } else {
                //System.out.println("Invalid choice. Please select again.");
                JOptionPane.showMessageDialog(null, "Invalid choice. Please select again.","MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
                continue;
            }
        
        } while (true);
        }catch(NumberFormatException er){
            JOptionPane.showMessageDialog(null, er, "ERROR", JOptionPane.ERROR_MESSAGE);
            promptQuantityOrder(foodId,foodName,foodPrice);
        }
    }       
     
    String displayFoodListsByCategory(int categoryId) {
        foodsCounter = 0;

        Response<List<Food>> foodsResponse = FOOD_CONTROLLER.getFoodsByCategory(categoryId);
        if (foodsResponse.isSuccess()) {
            List<Food> foods = foodsResponse.getData();

            StringBuilder selectFood = new StringBuilder();
            selectFood.append("------------------- Select Food -------------------\n");
            selectFood.append(String.format("%-5s %-30s %-10s%n", "ID", "Food Name", "Price")); // Header
            selectFood.append("--------------------------------------------------\n");

            // Loop through food items
            for (Food food : foods) {
                selectFood.append(String.format("%-5s %-30s %-10.2f%n", 
                    "[" + food.getFoodId() + "]", food.getFoodName(), food.getPrice()));
                foodsCounter++;
                foodCounter = food.getFoodId();
            }

            // Add options
            selectFood.append(String.format("%-5s %-30s%n", "[" + (foodCounter + 1) + "]", "Cart"));
            selectFood.append(String.format("%-5s %-30s%n", "[" + (foodCounter + 2) + "]", "Dashboard"));
            selectFood.append(String.format("%-5s %-30s%n", "[" + (foodCounter + 3) + "]", "Back"));

            return selectFood.toString();
        } else {
            JOptionPane.showMessageDialog(null, 
                "Error: " + foodsResponse.getMessage(), 
                "MOMMY'S VARIETY STORE", 
                JOptionPane.ERROR_MESSAGE);
            return "";
        }
}

    
     void showCategoryMenu() {
        int choice;
        try{
        do {
            String holder = displayCategories(); 
            //System.out.print("Enter your choice: ");
            String input = JOptionPane.showInputDialog(null, holder + "\nEnter your choice: ","MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
            //choice = SCANNER.nextInt();
            
            if(input == null){
                clientDashboard(userHolder);
            }
            choice = Integer.parseInt(input);

            // Check if the choice is valid for categories (1 to categoriesCounter)
            if (choice > 0 && choice <= categoriesCounter) {
                showFoodMenu(choice);
            } else if (choice == (categoriesCounter + 1)) {
                showCart();
                return;  // Go back to the client dashboard
            }else if (choice == (categoriesCounter + 2)) { 
                clientDashboard(userHolder);
                return;  // Go back to the client dashboard
            }else {
                //System.out.println("Invalid choice. Please select again.");
                JOptionPane.showMessageDialog(null,"Invalid choice. Please select again." , "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            }
        } while (true);
        }catch(NumberFormatException er){
            JOptionPane.showMessageDialog(null, er, "ERROR!", JOptionPane.ERROR_MESSAGE);
            showCategoryMenu();
        }
    }
    
     String displayCategories() {
        categoriesCounter = 0;  
        
        Response<List<Category>> categoriesResponse = CATEGORY_CONTROLLER.getAllCategories();
        if (categoriesResponse.isSuccess()) {
            List<Category> categories = categoriesResponse.getData();
            String selectCategory = "------------------- Select Category -------------------\n";
            //System.out.println("\n--- Select Category ---");
            for (Category category : categories) {
                //System.out.println("[" + category.getCategoryId() + "] " + category.getCategoryName());
                selectCategory += "[" + category.getCategoryId() + "]    " + category.getCategoryName() + "\n";
                categoriesCounter++;
            }
 
            //System.out.println("[" + (categories.size() + 1) + "] Back\n");
            selectCategory += "[" + (categories.size() + 1) + "]    Cart\n";
            selectCategory += "[" + (categories.size() + 2) + "]    Dashboard\n";
            return selectCategory;
        } else {
            //System.out.println("Error: " + categoriesResponse.getMessage());
            JOptionPane.showMessageDialog(null, "Error: " + categoriesResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }
     
}
