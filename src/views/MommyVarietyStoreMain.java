//eto
package views;
import controllers.CartController;
import models.User;
import models.Ingredient;
import models.Category;
import controllers.UserController;
import controllers.IngredientController;
import controllers.CategoryController;
import controllers.FoodController;
import helpers.Response;
import enums.UserRoles;
import helpers.Date;
import java.util.List;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import core.Cart;
import core.CartItem;
import models.Food;
import models.SalesDetails;
import static views.RunnerTest.CART_CONTROLLER;
import static views.RunnerTest.SCANNER;

public class MommyVarietyStoreMain {
    User userHolder;
    
    String userRole = "";
    String quantityType = "add";
    
    protected static final UserController LOGIN_CONTROLLER = new UserController();
    protected static final IngredientController INGREDIENT_CONTROLLER = new IngredientController();
    protected static final CategoryController CATEGORY_CONTROLLER = new CategoryController();
    protected static final FoodController FOOD_CONTROLLER = new FoodController();
    protected static final CartController CART_CONTROLLER = new CartController();
    
     static int categoriesCounter = 0;
     static int foodsCounter = 0;
     int foodCounter = 0;
    
    boolean startPanel = true;
    boolean loggedIn = false;
    
    LocalDate date = LocalDate.now();
    int userPayment;
    
    Cart found;
    
    public static void main(String[] args) {
        MommyVarietyStoreMain store = new MommyVarietyStoreMain();
        store.home();
    }
    
    void home(){
        while(true){
            Object[] options = {"USER", "ADMIN"};
        
            // Display JOptionPane with custom buttons
            int choice = JOptionPane.showOptionDialog(null, "=====================WELCOME TO MOMMY'S VARIETY STORE======================\n", "MOMMY'S VARIETY STORE", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            // Handle the button clicks
            switch (choice) {
                case 0:
                    //userType = "Client";
                    userRole = UserRoles.CLIENT.name();
                    // Code for Browse Menu/Order Now
                    logInForm();
                    break;
                case 1:
                    userRole = UserRoles.ADMIN.name();
                    //userType = "Admin";
                    logInForm();
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
    
    void payment(){
        /*        if(totalFoodCount <= 0){
        JOptionPane.showMessageDialog(null, "No food/s to check out!", "MOMMY'S VARIETY STORE",JOptionPane.INFORMATION_MESSAGE);
        return;
        }
        while(true){
        try{
        String input = JOptionPane.showInputDialog(null, "Payable Amount:" + totalFoodPrice + "\nEnter Payment: ", "MOMMY'S VARIETY STORE",JOptionPane.PLAIN_MESSAGE);
        if(input == null){
        break;
        }else{
        userPayment = Integer.parseInt(input);
        if(userPayment < totalFoodPrice){
        JOptionPane.showMessageDialog(null, "Not enough balance to check out this food/s!!");
        return;
        }else{
        checkOut();
        return;
        }
        }
        }catch(NumberFormatException er){
        JOptionPane.showMessageDialog(null, "Error!", "ERROR!", JOptionPane.ERROR_MESSAGE);
        continue;
        }
        }*/
    }

    void checkOut() {

    }
    
    void logInForm(){
        boolean loginSuccess = false;
        while(!loginSuccess){
        String userNameHolder = JOptionPane.showInputDialog(null,"Enter UserName: ", "MOMMY'S VARIETY STORE",JOptionPane.INFORMATION_MESSAGE);
        if(userNameHolder ==  null){
            home();
        }
        String userPasswordHolder = JOptionPane.showInputDialog(null, "Enter Password: ", "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
        if(userPasswordHolder == null){
            JOptionPane.showMessageDialog(null, "NO INPUTTED PASSWORD", "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            logInForm();
        }
        
        Response<User> loginResponse = LOGIN_CONTROLLER.loginUser(userNameHolder, userPasswordHolder, userRole);
        
        if(loginResponse.isSuccess()){
            
            JOptionPane.showMessageDialog(null, loginResponse.getMessage());
            User user = loginResponse.getData();
            userHolder = user;
            
            loggedIn = true;
            loginSuccess = true;
            userHolder = user;
            if (UserRoles.ADMIN.name().equals(userRole)) {
                adminSection(user);
            } else if (UserRoles.CLIENT.name().equals(userRole)){
                clientDashboard(user);
            }
            loggedIn = false; // Logout after navigating the dashboard to return to the role selection screen
            startPanel = true;
            
            
            
            return;
        }else{
            JOptionPane.showMessageDialog(null, "INVALID CREDENTIALS!","ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            continue;
        }
        }
    }
    
    void adminSection(User user){
        int choice;
        boolean showLowStock = false; // Flag to track if we're showing low stock
        boolean showAllStocks = false; // Flag to track if we're showing all stocks
        boolean showOneStocks = false; // Flag to track if we're showing one item's full info
        while(true){
         Object[] options = {"MANAGE STOCKS", "PROFILE","LOG OUT"};
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
                    showProfile(user);
                    break;
                case 2:
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
        /*        System.out.println("Full Name: " + user.getFullName());
        System.out.println("Address: " + user.getFullAddress());*/
        JOptionPane.showMessageDialog(null, "YOUR PROFILE:\n\nFull Name: " + user.getFullName() + "\nAddress: " + user.getFullAddress(),"MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /*    void showCart() {
    //System.out.println("\n--- Your Cart ---");
    Object[] options = {"OK", "EDIT QUANTITY","REMOVE ITEM" ,"CHECK OUT"};
    String cartHolder = "\n-------------------------------------------- Your Cart ---------------------------------------------";
    Response<Cart> cartResponse = CART_CONTROLLER.viewCart();
    
    if (cartResponse.isSuccess()) {
    Cart cart = cartResponse.getData();
    
    if (cart.getItems().isEmpty()) {
    //System.out.println("Your cart is empty.");
    JOptionPane.showMessageDialog(null, "Your cart is empty.", "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
    foodCategoryChoice();
    } else {
    int itemNumber = 1;
    //System.out.printf("%-5s %-20s %-10s %-10s %-10s\n", "No.", "Food Name", "Price", "Qty", "Total");
    cartHolder += "\n" +"No.    "+ "Food Name                               "+"Price                      "+ "Qty                   "+ "Total\n";
    //System.out.println("-----------------------------------------------------------");
    cartHolder += "--------------------------------------------------------------------------------------------------------";
    
    for (CartItem item : cart.getItems()) {
    double totalPrice = item.getTotalPrice();
    
    cartHolder += "\n" + itemNumber++ +"    "+ item.getFoodName() +"                        "+ item.getFoodPrice() +"                   "+ item.getQuantity() +"                  "+ totalPrice;
    }
    
    //System.out.println("-----------------------------------------------------------");
    cartHolder += "\n----------------------------------------------------------------------------------------------------------\n";
    //System.out.printf("%-35s %-10.2f\n", "Total Amount:", cart.getTotalAmount());
    cartHolder += "Total Amount: Php "+ cart.getTotalAmount() + "";
    //JOptionPane.showMessageDialog(null, cartHolder, "MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
    int choice = JOptionPane.showOptionDialog(null, cartHolder, "MOMMY'S VARIETY STORE", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    
    if(choice == 0){
    foodCategoryChoice();
    }else if(choice == 1){
    //editQuantity
    quantityType = "edit";
    foodCategoryChoice();
    }else if(choice == 2){
    quantityType = "remove";
    foodCategoryChoice();
    }else if(choice == 3){
    //checkout
    }else{
    foodCategoryChoice();
    }
    return ;
    }
    } else {
    //System.out.println("Error: " + cartResponse.getMessage());
    JOptionPane.showMessageDialog(null, "Error: " + cartResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
    return ; // Exit if there's an issue fetching the cart
    }
    
    // Provide options to the user
    }*/
    
    void showCart() {
    Object[] options = {"OK", "EDIT QUANTITY", "REMOVE ITEM", "CHECK OUT"};
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
                    do {
                        // Show input dialog and get the payment amount
                        String paymentInput = JOptionPane.showInputDialog(null, 
                            "Payable amount: " + cart.getTotalAmount()+ "\n\nEnter your payment amount:", "MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);

                        // Check if the input is null (user pressed Cancel) or empty (user entered nothing)
                        if (paymentInput == null) {
                            showCart();
                            return;
                        }

                        try {
                            amountPayment = Double.parseDouble(paymentInput); // Try to parse the input as a double
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, e,"ERROR", JOptionPane.ERROR_MESSAGE);
                            amountPayment = -1; // If invalid input, set amountPayment to -1
                            continue;
                        }

                        // Display error or insufficient payment message
                        if (amountPayment <= 0) {
                            JOptionPane.showMessageDialog(null, "Invalid payment amount! Please enter a positive number.", 
                                                          "Invalid Payment", JOptionPane.ERROR_MESSAGE);
                        } else if (amountPayment < cart.getTotalAmount()) {
                            JOptionPane.showMessageDialog(null, "Insufficient payment! The total amount is " + cart.getTotalAmount(), 
                                                          "Insufficient Payment", JOptionPane.ERROR_MESSAGE);
                        }

                    } while (amountPayment < cart.getTotalAmount() || amountPayment <= 0);

                    double change = amountPayment - cart.getTotalAmount();

                    // Process order
                    Response<Cart> cartOrderResponse = CART_CONTROLLER.checkOutOrder();
                    if (cartOrderResponse.isSuccess()) {
                        JOptionPane.showMessageDialog(null, cartOrderResponse.getMessage(), "Order Successful", JOptionPane.INFORMATION_MESSAGE);

                        // Get sales details / reports
                        Response<List<SalesDetails>> salesDetailsResponse = CART_CONTROLLER.getOrderReports();
                        if (salesDetailsResponse.isSuccess()) {
                            List<SalesDetails> salesDetails = salesDetailsResponse.getData();
                            StringBuilder salesReport = new StringBuilder();

                            // Add header
                            salesReport.append("========================== SALES REPORT ==========================\n");
                            salesReport.append("Name: ").append(salesDetails.get(0).getCustomer().getFullName()).append("\n");
                            // Define a maximum length for the address
                                final int MAX_ADDRESS_LENGTH = 50; // Adjust this value as needed

                                // Truncate the address if it exceeds the maximum length
                                String address = salesDetails.get(0).getCustomer().getFullAddress();
                                if (address.length() > MAX_ADDRESS_LENGTH) {
                                    address = address.substring(0, MAX_ADDRESS_LENGTH) + "..."; // Add ellipsis
                                }

                                salesReport.append("Address: ").append(address).append("\n");

                            salesReport.append("Date: ").append(Date.formatToReadableDate(salesDetails.get(0).getSale().getSaleDate())).append("\n");
                            salesReport.append("Sales ID: ").append(salesDetails.get(0).getSale().getSaleId()).append("\n");
                            salesReport.append("------------------------------------------------------------------\n");

                            // Add table header
                            salesReport.append(String.format("%-20s %-15s %-10s %-15s\n", "Food Name", "Price", "Quantity", "Subtotal"));
                            salesReport.append("------------------------------------------------------------------\n");

                            // Add table rows
                            double subTotal = 0;
                            double netTotal = 0;

                            for (CartItem item : cart.getItems()) {
                                subTotal = item.getQuantity() * item.getFoodPrice(); // Calculate subtotal
                                netTotal += subTotal; // Calculate net total

                                salesReport.append(String.format("%-20s %-15.2f %-10d %-15.2f\n",
                                        item.getFoodName(),
                                        item.getFoodPrice(),
                                        item.getQuantity(),
                                        subTotal));
                            }

                            // Add footer
                            salesReport.append("------------------------------------------------------------------\n");
                            salesReport.append(String.format("%-43s Php %.2f%n", "Net Total:", netTotal));
                            salesReport.append(String.format("%-43s Php %.2f%n", "Payment:", amountPayment));
                            salesReport.append(String.format("%-43s Php %.2f%n", "Change:", change));
                            salesReport.append("\n==================================================================\n");

                            // Display the sales report in a dialog box
                            JOptionPane.showMessageDialog(null,"<html><pre>"+ salesReport.toString()+"<html><pre>", "Sales Report", JOptionPane.PLAIN_MESSAGE);

                            // Clear the cart items after displaying the report
                            cart.getItems().clear();
                            clientDashboard(userHolder);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error: " + salesDetailsResponse.getMessage(), 
                                                          "Error Retrieving Sales Details", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: " + cartOrderResponse.getMessage(), 
                                                      "Order Checkout Error", JOptionPane.ERROR_MESSAGE);
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
    
     /*     String displayFoodListsByCategory(int categoryId) {
     foodsCounter = 0;
     
     Response<List<Food>> foodsResponse = FOOD_CONTROLLER.getFoodsByCategory(categoryId);
     if (foodsResponse.isSuccess()) {
     List<Food> foods = foodsResponse.getData();
     String selectFood = "------------------- Select Food -------------------\n";
     //System.out.println("\n--- Select Food ---");
     for (Food food : foods) {
     //System.out.println("[" + food.getFoodId() + "] \t" + food.getFoodName() + "\t\t" + food.getPrice());
     selectFood += "[" + food.getFoodId() + "]     " + food.getFoodName() + "     " + food.getPrice()+"\n";
     foodsCounter++;
     foodCounter = food.getFoodId();
     }
     
     //System.out.println("[" + (foods.size() + 1) + "] Back\n");
     selectFood += "[" + (foodCounter + 1) + "]    Cart\n";
     selectFood += "[" + (foodCounter + 2) + "]    Dashboard\n";
     selectFood += "[" + (foodCounter + 3) + "]    Back\n";
     
     return selectFood;
     } else {
     JOptionPane.showMessageDialog(null, "Error: " + foodsResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
     return "";
     }
     }*/
     
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
