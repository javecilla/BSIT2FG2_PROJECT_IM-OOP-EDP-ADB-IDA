//eto
package views;
import models.User;
import models.Ingredient;
import models.Category;
import controllers.UserController;
import controllers.IngredientController;
import controllers.CategoryController;
import helpers.Response;
import enums.UserRoles;
import java.util.List;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import static views.RunnerTest.LOGIN_CONTROLLER;

public class MommyVarietyStoreMain {
    getAccessPoint getAccess = new getAccessPoint();
    User userHolder;
    String userPassword = "admin";
    
    String userRole = "";
    String userName = "";
    String saleId = "01";
    String address = "Malolos, Bulacan";
    String quantityType = "";
    String receipt = "";
    String itemId = "";
    
    protected static final UserController LOGIN_CONTROLLER = new UserController();
    protected static final IngredientController INGREDIENT_CONTROLLER = new IngredientController();
    protected static final CategoryController CATEGORY_CONTROLLER = new CategoryController();
    
    private static int categoriesCounter = 0;
    
    boolean startPanel = true;
    boolean loggedIn = false;
    
    LocalDate date = LocalDate.now();
    int userPayment;
    
    int samgyupRiceOrder =0;
    int oneBurgerSteakOrder =0;
    int twoBurgerSteakOrder =0;
    int threeChickenNuggetsOrder =0;
    int hotdogAndEggOrder =0;
    int baconAndEggOrder =0;
    int hamAndEggOrder =0;
    int regularBurgerOrder =0;
    int cheeseBurgerOrder =0;
    int alohaBurgerOrder =0;
    int hamAndEggSandwichOrder =0;
    int baconAndEggSandwichOrder =0;
    int hotdogSandwichOrder =0;
    int miloBlastOrder =0;
    int icedStrawberryBombOrder =0;
    int icedMixedBerriesOrder =0;
    int icedOreoLatteOrder =0;
    int icedCaramelMacchiatoOrder =0;
    int icedSpanishLatteOrder =0;
    
    int smallFriesOrder =0;
    int cheeseSmallFriesOrder =0;
    int barbecueSmallFriesOrder =0;
    int sourCreamSmallFriesOrder =0;
    int chiliBarbecueSmallFriesOrder =0;
    int honeyButterSmallFriesOrder =0;
    int butterCheeseSmallFriesOrder =0;
    
    int mediumFriesOrder =0;
    int cheeseMediumFriesOrder =0;
    int barbecueMediumFriesOrder =0;
    int sourCreamMediumFriesOrder =0;
    int chiliBarbecueMediumFriesOrder =0;
    int honeyButterMediumFriesOrder =0;
    int butterCheeseMediumFriesOrder =0;
    
    int largeFriesOrder =0;
    int cheeseLargeFriesOrder =0;
    int barbecueLargeFriesOrder =0;
    int sourCreamLargeFriesOrder =0;
    int chiliBarbecueLargeFriesOrder =0;
    int honeyButterLargeFriesOrder =0;
    int butterCheeseLargeFriesOrder =0;
    
    int samgyupRiceOrderPrice =0;
    int oneBurgerSteakOrderPrice =0;
    int twoBurgerSteakOrderPrice =0;
    int threeChickenNuggetsOrderPrice =0;
    int hotdogAndEggOrderPrice =0;
    int baconAndEggOrderPrice =0;
    int hamAndEggOrderPrice =0;
    int regularBurgerOrderPrice =0;
    int cheeseBurgerOrderPrice =0;
    int alohaBurgerOrderPrice =0;
    int hamAndEggSandwichOrderPrice =0;
    int baconAndEggSandwichOrderPrice =0;
    int hotdogSandwichOrderPrice =0;
    int miloBlastOrderPrice =0;
    int icedStrawberryBombOrderPrice =0;
    int icedMixedBerriesOrderPrice =0;
    int icedOreoLatteOrderPrice =0;
    int icedCaramelMacchiatoOrderPrice =0;
    int icedSpanishLatteOrderPrice =0;
    int smallFriesOrderPrice =0;
    int cheeseSmallFriesOrderPrice =0;
    int barbecueSmallFriesOrderPrice =0;
    int sourCreamSmallFriesOrderPrice =0;
    int chiliBarbecueSmallFriesOrderPrice =0;
    int honeyButterSmallFriesOrderPrice =0;
    int butterCheeseSmallFriesOrderPrice =0;
    int mediumFriesOrderPrice =0;
    int cheeseMediumFriesOrderPrice =0;
    int barbecueMediumFriesOrderPrice =0;
    int sourCreamMediumFriesOrderPrice =0;
    int chiliBarbecueMediumFriesOrderPrice =0;
    int honeyButterMediumFriesOrderPrice =0;
    int butterCheeseMediumFriesOrderPrice =0;
    int largeFriesOrderPrice =0;
    int cheeseLargeFriesOrderPrice =0;
    int barbecueLargeFriesOrderPrice =0;
    int sourCreamLargeFriesOrderPrice =0;
    int chiliBarbecueLargeFriesOrderPrice =0;
    int honeyButterLargeFriesOrderPrice =0;
    int butterCheeseLargeFriesOrderPrice =0;
    
    int totalFoodCount;
    int totalFoodPrice;
    
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
                    getAccess.showProfile(user);
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
        while(true){
            Object[] options = {"RICE MEALS", "SANDWICHES", "FRIES", "DRINKS","CART","DASHBOARD", "HOME", "EXIT"};
            int foodChoice = JOptionPane.showOptionDialog(null, "==================================================WELCOME TO OUR MENU===================================================\n", "MOMMY'S VARIETY STORE", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            
            switch(foodChoice){
                case 0:
                    riceMealsMenu();
                    break;
                case 1:
                    sandwichesMenu();
                    break;
                case 2:
                    friesMenu();
                    break;
                case 3:
                    drinksMenu();
                    break;
                case 4:
                    printReceipt();
                    break;
                case 5:
                    clientDashboard(userHolder);
                    break;
                case 6:
                    home();
                    break;
                case 7:
                    systemExit();
                    break;
                default:
                    systemExit();
                    break;
            }
        }
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
    
    void riceMealsMenu(){
        Object[] options = {"[1]", "[2]", "[3]", "[4]", "[5]", "[6]", "[7]", "MAIN MENU", "CART", "DASHBOARD","HOME", "EXIT"};
        
        String riceMealsMenu = "====================================================================RICE MEALS MENU=========================================================================\n\n"
                        + "[1] SAMGYUP RICE ----------------------------------- Php 60\n"
                        + "[2] 1pc. BURGER STEAK ---------------------------- Php 45\n"
                        + "[3] 2pc. BURGER STEAK ---------------------------- Php 65\n"
                        + "[4] 3pc. CHICKEN NUGGETS ----------------------- Php 55\n"
                        + "[5] HOTDOG & EGG ----------------------------------- Php 40\n"
                        + "[6] BACON & EGG ------------------------------------- Php 60\n"
                        + "[7] HAM & EGG ---------------------------------------- Php 50";
        
        int choice = JOptionPane.showOptionDialog(null, riceMealsMenu, "MOMMY'S VARIETY STORE", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        
        switch(choice){
            case 0:
                if(quantityType.equalsIgnoreCase("add")){
                    samgyupRiceOrder += foodOrderedCounter();}
                else{
                    samgyupRiceOrder = foodOrderedCounter();
                }//return number of food Inputted
                break;
            case 1:
                if(quantityType.equalsIgnoreCase("add")){
                    oneBurgerSteakOrder += foodOrderedCounter();}
                else{
                    oneBurgerSteakOrder = foodOrderedCounter();
                }
                break;
            case 2:
                if(quantityType.equalsIgnoreCase("add")){
                    twoBurgerSteakOrder += foodOrderedCounter();}
                else{
                    twoBurgerSteakOrder = foodOrderedCounter();
                }
                break;
            case 3:
                if(quantityType.equalsIgnoreCase("add")){
                    threeChickenNuggetsOrder += foodOrderedCounter();}
                else{
                    threeChickenNuggetsOrder = foodOrderedCounter();
                }
                break;
            case 4:
                if(quantityType.equalsIgnoreCase("add")){
                    hotdogAndEggOrder += foodOrderedCounter();}
                else{
                    hotdogAndEggOrder = foodOrderedCounter();
                }
                break;
            case 5:
                if(quantityType.equalsIgnoreCase("add")){
                    baconAndEggOrder += foodOrderedCounter();}
                else{
                    baconAndEggOrder = foodOrderedCounter();
                }
                break;
            case 6:
                if(quantityType.equalsIgnoreCase("add")){
                    hamAndEggOrder += foodOrderedCounter();}
                else{
                    hamAndEggOrder = foodOrderedCounter();
                }
                break;
            case 7:
                break;
            case 8:
                printReceipt();
                break;
            case 9:
                clientDashboard(userHolder);
                break;
            case 10:
                home();
                break;
            case 11:
                systemExit();
                break;
            default:
                systemExit();
                break;
        }
    }
    
    void sandwichesMenu(){
        Object[] options = {"[1]", "[2]", "[3]", "[4]", "[5]", "[6]", "MAIN MENU","CART","DASHBOARD", "HOME", "EXIT"};
        
        String sandwichesMenu = "================================================================SANDWICHES MENU=====================================================================\n\n"
                        + "[1] REGULAR BURGER --------------------------------- Php 35\n"
                        + "[2] CHEESE BURGER ----------------------------------- Php 45\n"
                        + "[3] ALOHA BURGER ------------------------------------- Php 65\n"
                        + "[4] HAM & EGG SANDWICH --------------------------- Php 45\n"
                        + "[5] BACON & EGG SANDWICH ----------------------- Php 55\n"
                        + "[6] HOTODG SANDWICH ------------------------------- Php 35\n";
        
        int choice = JOptionPane.showOptionDialog(null, sandwichesMenu, "MOMMY'S VARIETY STORE", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        
        switch(choice){
            case 0:
                if(quantityType.equalsIgnoreCase("add")){
                    regularBurgerOrder += foodOrderedCounter();}
                else{
                    regularBurgerOrder = foodOrderedCounter();
                }
                break;
            case 1:
                if(quantityType.equalsIgnoreCase("add")){
                    cheeseBurgerOrder += foodOrderedCounter();}
                else{
                    cheeseBurgerOrder = foodOrderedCounter();
                }
                break;
            case 2:
                if(quantityType.equalsIgnoreCase("add")){
                    alohaBurgerOrder += foodOrderedCounter();}
                else{
                    alohaBurgerOrder = foodOrderedCounter();
                }
                break;
            case 3:
                if(quantityType.equalsIgnoreCase("add")){
                    hamAndEggSandwichOrder += foodOrderedCounter();}
                else{
                    hamAndEggSandwichOrder = foodOrderedCounter();
                }
                break;
            case 4:
                if(quantityType.equalsIgnoreCase("add")){
                    baconAndEggSandwichOrder += foodOrderedCounter();}
                else{
                    baconAndEggSandwichOrder = foodOrderedCounter();
                }
                break;
            case 5:
                if(quantityType.equalsIgnoreCase("add")){
                    hotdogSandwichOrder += foodOrderedCounter();}
                else{
                    hotdogSandwichOrder = foodOrderedCounter();
                }
                break;
            case 6:
                break;
            case 7:
                printReceipt();
                break;
            case 8:
                clientDashboard(userHolder);
                break;
            case 9:
                home();
                break;
            case 10:
                systemExit();
                break;
            default:
                systemExit();
                break;
        }
    }
    
    void friesMenu(){
        Object[] options = {"[1]", "[2]", "[3]", "MAIN MENU","CART","DASHBOARD", "HOME", "EXIT"};
        
        String FriesMenu = "===============================================FRIES MENU===============================================\n\n"
                        + "[1] SMALL FRIES ------------------------------------ Php 20\n"
                        + "[2] MEDIUM FRIES ---------------------------------- Php 35\n"
                        + "[3] LARGE FRIES ------------------------------------ Php 55\n";
        
        int choice = JOptionPane.showOptionDialog(null, FriesMenu, "MOMMY'S VARIETY STORE", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        
        switch(choice){
            case 0:
                friesFlavorChoice("small");
                break;
            case 1:
                friesFlavorChoice("medium");
                break;
            case 2:
                friesFlavorChoice("large");
                break;
            case 3:
                break;
            case 4:
                printReceipt();
                break;
            case 5:
                clientDashboard(userHolder);
                break;
            case 6:
                home();
                break;
            case 7:
                systemExit();
                break;
            default:
                systemExit();
                break;
        }
    }
    
    void friesFlavorChoice(String size){
        Object[] options = {"[1]","[2]","[3]","[4]","[5]","[6]"};
        
        String flavors = "=================FRIES FLAVORS===================\n\n"
                        + "[1] Cheese\n"
                        + "[2] Barbeque\n"
                        + "[3] Sour Cream\n"
                        + "[4] Chili Barbeque\n"
                        + "[5] Honey Butter\n"
                        + "[6] Butter Cheese\n";
        int choice = JOptionPane.showOptionDialog(null, flavors, "MOMMY'S VARIETY STORE", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        
        switch(choice){
            case 0:
                if(size.equalsIgnoreCase("small")){
                    if(quantityType.equalsIgnoreCase("add")){
                        cheeseSmallFriesOrder += foodOrderedCounter();}
                    else{
                        cheeseSmallFriesOrder = foodOrderedCounter();
                    }
                }else if(size.equalsIgnoreCase("medium")){
                    if(quantityType.equalsIgnoreCase("add")){
                        cheeseMediumFriesOrder += foodOrderedCounter();}
                    else{
                        cheeseMediumFriesOrder = foodOrderedCounter();
                    }
                }else {
                    if(quantityType.equalsIgnoreCase("add")){
                        cheeseLargeFriesOrder += foodOrderedCounter();}
                    else{
                        cheeseLargeFriesOrder = foodOrderedCounter();
                    }
                }
                break;
            case 1:
                if(size.equalsIgnoreCase("small")){
                    if(quantityType.equalsIgnoreCase("add")){
                        barbecueSmallFriesOrder += foodOrderedCounter();}
                    else{
                        barbecueSmallFriesOrder = foodOrderedCounter();
                    }
                }else if(size.equalsIgnoreCase("medium")){
                    if(quantityType.equalsIgnoreCase("add")){
                        barbecueMediumFriesOrder += foodOrderedCounter();}
                    else{
                        barbecueMediumFriesOrder = foodOrderedCounter();
                    }
                }else {
                    if(quantityType.equalsIgnoreCase("add")){
                        barbecueLargeFriesOrder += foodOrderedCounter();}
                    else{
                        barbecueLargeFriesOrder = foodOrderedCounter();
                    }
                }
                break;
            case 2:
                if(size.equalsIgnoreCase("small")){
                    if(quantityType.equalsIgnoreCase("add")){
                        sourCreamSmallFriesOrder += foodOrderedCounter();}
                    else{
                        sourCreamSmallFriesOrder = foodOrderedCounter();
                    }
                }else if(size.equalsIgnoreCase("medium")){
                    if(quantityType.equalsIgnoreCase("add")){
                        sourCreamMediumFriesOrder += foodOrderedCounter();}
                    else{
                        sourCreamMediumFriesOrder = foodOrderedCounter();
                    }
                }else {
                    if(quantityType.equalsIgnoreCase("add")){
                        sourCreamLargeFriesOrder += foodOrderedCounter();}
                    else{
                        sourCreamLargeFriesOrder = foodOrderedCounter();
                    }
                }
                break;
            case 3:
                if(size.equalsIgnoreCase("small")){
                    if(quantityType.equalsIgnoreCase("add")){
                        chiliBarbecueSmallFriesOrder += foodOrderedCounter();}
                    else{
                        chiliBarbecueSmallFriesOrder = foodOrderedCounter();
                    }
                }else if(size.equalsIgnoreCase("medium")){
                    if(quantityType.equalsIgnoreCase("add")){
                        chiliBarbecueMediumFriesOrder += foodOrderedCounter();}
                    else{
                        chiliBarbecueMediumFriesOrder = foodOrderedCounter();
                    }
                }else {
                    if(quantityType.equalsIgnoreCase("add")){
                        chiliBarbecueLargeFriesOrder += foodOrderedCounter();}
                    else{
                        chiliBarbecueLargeFriesOrder = foodOrderedCounter();
                    }
                }
                break;
            case 4:
                if(size.equalsIgnoreCase("small")){
                    if(quantityType.equalsIgnoreCase("add")){
                        honeyButterSmallFriesOrder += foodOrderedCounter();}
                    else{
                        honeyButterSmallFriesOrder = foodOrderedCounter();
                    }
                }else if(size.equalsIgnoreCase("medium")){
                    if(quantityType.equalsIgnoreCase("add")){
                        honeyButterMediumFriesOrder += foodOrderedCounter();}
                    else{
                        honeyButterMediumFriesOrder = foodOrderedCounter();
                    }
                }else {
                    if(quantityType.equalsIgnoreCase("add")){
                        honeyButterLargeFriesOrder += foodOrderedCounter();}
                    else{
                        honeyButterLargeFriesOrder = foodOrderedCounter();
                    }
                }
                break;
            case 5:
                if(size.equalsIgnoreCase("small")){
                    if(quantityType.equalsIgnoreCase("add")){
                        butterCheeseSmallFriesOrder += foodOrderedCounter();}
                    else{
                        butterCheeseSmallFriesOrder = foodOrderedCounter();
                    }
                }else if(size.equalsIgnoreCase("medium")){
                    if(quantityType.equalsIgnoreCase("add")){
                        butterCheeseMediumFriesOrder += foodOrderedCounter();}
                    else{
                        butterCheeseMediumFriesOrder = foodOrderedCounter();
                    }
                }else {
                    if(quantityType.equalsIgnoreCase("add")){
                        butterCheeseLargeFriesOrder += foodOrderedCounter();}
                    else{
                        butterCheeseLargeFriesOrder = foodOrderedCounter();
                    }
                }
                break;
            default:
                break;
        }
    }
    
    void drinksMenu(){
        Object[] options = {"[1]", "[2]", "[3]", "[4]", "[5]", "[6]", "MAIN MENU","CART" ,"DASHBOARD", "HOME", "EXIT"};
        
        String drinksMenu = "======================================================DRINKS MENU===========================================================\n\n"
                        + "[1] MILO BLAST ------------------------------------------------- Php 35\n"
                        + "[2] ICED STRAWBERRY BOMB ------------------------------ Php 45\n"
                        + "[3] ICED MIXED BERRIES ------------------------------------- Php 65\n"
                        + "[4] ICED OREO LATTE ----------------------------------------- Php 45\n"
                        + "[5] ICED CARAMEL MACCHIATO ---------------------------- Php 55\n"
                        + "[6] ICED SPANISH LATTE ------------------------------------- Php 35\n";
        
        int choice = JOptionPane.showOptionDialog(null, drinksMenu, "MOMMY'S VARIETY STORE", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        
        switch(choice){
            case 0:
                if(quantityType.equalsIgnoreCase("add")){
                    miloBlastOrder += foodOrderedCounter();}
                else{
                    miloBlastOrder = foodOrderedCounter();
                }
                break;
            case 1:
                if(quantityType.equalsIgnoreCase("add")){
                    icedStrawberryBombOrder += foodOrderedCounter();}
                else{
                    icedStrawberryBombOrder = foodOrderedCounter();
                }
                break;
            case 2:
                if(quantityType.equalsIgnoreCase("add")){
                    icedMixedBerriesOrder += foodOrderedCounter();}
                else{
                    icedMixedBerriesOrder = foodOrderedCounter();
                }
                break;
            case 3:
                if(quantityType.equalsIgnoreCase("add")){
                    icedOreoLatteOrder += foodOrderedCounter();}
                else{
                    icedOreoLatteOrder = foodOrderedCounter();
                }
                break;
            case 4:
                if(quantityType.equalsIgnoreCase("add")){
                    icedCaramelMacchiatoOrder += foodOrderedCounter();}
                else{
                    icedCaramelMacchiatoOrder = foodOrderedCounter();
                }
                break;
            case 5:
                if(quantityType.equalsIgnoreCase("add")){
                    icedSpanishLatteOrder += foodOrderedCounter();}
                else{
                    icedSpanishLatteOrder = foodOrderedCounter();
                }
                break;
            case 6:
                break;
            case 7:
                printReceipt();
                break;
            case 8:
                clientDashboard(userHolder);
                break;
            case 9:
                home();
                break;
            case 10:
                systemExit();
                break;
            default:
                systemExit();
                break;
        }
    }
    
    int foodOrderedCounter(){
        /*if(foodStockCount >= 0){
                String input = JOptionPane.showInputDialog(null, "How Many?", "MOMMY'S VARIETY STORE\nStock: " + stockcount, JOptionPane.QUESTION_MESSAGE);
                int count = Integer.parseInt(input);
        
                if(count > stock){
                //create a message that the stock is only ....
                return;
        }
        }else{
            JOptinPane.showMessageDialog("this item is out of stock", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
                
        }*/
        
        int count = 0;
        while(true){
            try{
                String input = JOptionPane.showInputDialog(null, "How Many?", "MOMMY'S VARIETY STORE", JOptionPane.QUESTION_MESSAGE);
                if(input == null){
                    break;
                }else {
                    count = Integer.parseInt(input);
                    quantityType = "add";
                    return count;
                }
            }catch(NumberFormatException er){
                JOptionPane.showMessageDialog(null, er,"ERROR!",JOptionPane.ERROR_MESSAGE);
                continue;
            }
        }
        return count;
    }
    
    void printReceipt(){
        String receipt = "===================YOUR RECEIPT=====================\n\n"; 
        calculatePrice();
        if(samgyupRiceOrder > 0){
            receipt += samgyupRiceOrder + "x SAMGYUP RICE --------------------" + samgyupRiceOrderPrice + "\n";
        }
        if(oneBurgerSteakOrder > 0){
            receipt += oneBurgerSteakOrder + "x 1pc. BURGER STEAK ---------------" + oneBurgerSteakOrderPrice + "\n";
        }
        if(twoBurgerSteakOrder > 0){
            receipt += twoBurgerSteakOrder + "x 2pc. BURGER STEAK --------------------" + twoBurgerSteakOrderPrice + "\n";
        }
        if(threeChickenNuggetsOrder > 0){
            receipt += threeChickenNuggetsOrder + "x 3pc.CHICKEN NUGGETS --------------------" + threeChickenNuggetsOrderPrice + "\n";
        }
        if(hotdogAndEggOrder > 0){
            receipt += hotdogAndEggOrder + "x HOTDOG & EGG--------------------" + hotdogAndEggOrderPrice + "\n";
        }
        if(baconAndEggOrder > 0){
            receipt += baconAndEggOrder + "x BACON & EGG --------------------" + baconAndEggOrderPrice + "\n";
        }
        if(hamAndEggOrder > 0){
            receipt += hamAndEggOrder + "x HAM & EGG --------------------" + hamAndEggOrderPrice + "\n";
        }
        if(regularBurgerOrder > 0){
            receipt += regularBurgerOrder + "x REGULAR BURGER --------------------" + regularBurgerOrderPrice + "\n";
        }
        if(cheeseBurgerOrder > 0){
            receipt += cheeseBurgerOrder + "x CHEESE BURGER --------------------" + cheeseBurgerOrderPrice + "\n";
        }
        if(alohaBurgerOrder > 0){
            receipt += alohaBurgerOrder + "x ALOHA BURGER --------------------" + alohaBurgerOrderPrice + "\n";
        }
        if(hamAndEggSandwichOrder > 0){
            receipt += hamAndEggSandwichOrder + "x HAM & EGG SANDWICH --------------------" + hamAndEggSandwichOrderPrice + "\n";
        }
        if(baconAndEggSandwichOrder > 0){
            receipt += baconAndEggSandwichOrder + "x BACON & EGG SANDWICH --------------------" + baconAndEggSandwichOrderPrice + "\n";
        }
        if(hotdogSandwichOrder > 0){
            receipt += hotdogSandwichOrder + "x HOTDOG SANDWICH --------------------" + hotdogSandwichOrderPrice + "\n";
        }
        
        //print fries small section
        if(cheeseSmallFriesOrder > 0){
            receipt += cheeseSmallFriesOrder + "x CHEESE SMALL FRIES --------------------" + cheeseSmallFriesOrderPrice + "\n";
        }
        if(barbecueSmallFriesOrder > 0){
            receipt += barbecueSmallFriesOrder + "x BARBECUE SMALL FRIES --------------------" + barbecueSmallFriesOrderPrice + "\n";
        }
        if(sourCreamSmallFriesOrder > 0){
            receipt += sourCreamSmallFriesOrder + "x SOUR CREAM SMALL FRIES --------------------" + sourCreamSmallFriesOrderPrice + "\n";
        }
        if(chiliBarbecueSmallFriesOrder > 0){
            receipt += chiliBarbecueSmallFriesOrder + "x CHILI BARBECUE SMALL FRIES --------------------" + chiliBarbecueSmallFriesOrderPrice + "\n";
        }
        if(honeyButterSmallFriesOrder > 0){
            receipt += honeyButterSmallFriesOrder + "x HONEY BUTTER SMALL FRIES --------------------" + honeyButterSmallFriesOrderPrice + "\n";
        }
        if(butterCheeseSmallFriesOrder > 0){
            receipt += butterCheeseSmallFriesOrder + "x BUTTER CHEESE SMALL FRIES --------------------" + butterCheeseSmallFriesOrderPrice + "\n";
        }
        
        //print fries medium section
        if(cheeseMediumFriesOrder > 0){
            receipt += cheeseMediumFriesOrder + "x CHEESE MEDIUM FRIES --------------------" + cheeseMediumFriesOrderPrice + "\n";
        }
        if(barbecueMediumFriesOrder > 0){
            receipt += barbecueMediumFriesOrder + "x BARBECUE MEDIUM FRIES --------------------" + barbecueMediumFriesOrderPrice + "\n";
        }
        if(sourCreamMediumFriesOrder > 0){
            receipt += sourCreamMediumFriesOrder + "x SOUR CREAM MEDIUM FRIES --------------------" + sourCreamMediumFriesOrderPrice + "\n";
        }
        if(chiliBarbecueMediumFriesOrder > 0){
            receipt += chiliBarbecueMediumFriesOrder + "x CHILI BARBECUE MEDIUM FRIES --------------------" + chiliBarbecueMediumFriesOrderPrice + "\n";
        }
        if(honeyButterMediumFriesOrder > 0){
            receipt += honeyButterMediumFriesOrder + "x HONEY BUTTER MEDIUM FRIES --------------------" + honeyButterMediumFriesOrderPrice + "\n";
        }
        if(butterCheeseMediumFriesOrder > 0){
            receipt += butterCheeseMediumFriesOrder + "x BUTTER CHEESE MEDIUM FRIES --------------------" + butterCheeseMediumFriesOrderPrice + "\n";
        }
        
        //LARGE FRIES SECTION
        if(cheeseLargeFriesOrder > 0){
            receipt += cheeseLargeFriesOrder + "x CHEESE LARGE FRIES --------------------" + cheeseLargeFriesOrderPrice + "\n";
        }
        if(barbecueLargeFriesOrder > 0){
            receipt += barbecueLargeFriesOrder + "x BARBECUE LARGE FRIES --------------------" + barbecueLargeFriesOrderPrice + "\n";
        }
        if(sourCreamLargeFriesOrder > 0){
            receipt += sourCreamLargeFriesOrder + "x SOUR CREAM LARGE FRIES --------------------" + sourCreamLargeFriesOrderPrice + "\n";
        }
        if(chiliBarbecueLargeFriesOrder > 0){
            receipt += chiliBarbecueLargeFriesOrder + "x CHILI BARBECUE LARGE FRIES --------------------" + chiliBarbecueLargeFriesOrderPrice + "\n";
        }
        if(honeyButterLargeFriesOrder > 0){
            receipt += honeyButterLargeFriesOrder + "x HONEY BUTTER LARGE FRIES --------------------" + honeyButterLargeFriesOrderPrice + "\n";
        }
        if(butterCheeseLargeFriesOrder > 0){
            receipt += butterCheeseLargeFriesOrder + "x BUTTER CHEESE LARGE FRIES --------------------" + butterCheeseLargeFriesOrderPrice + "\n";
        }
        
        
        if(miloBlastOrder > 0){
            receipt += miloBlastOrder + "x MILO BLAST --------------------" + miloBlastOrderPrice + "\n";
        }
        if(icedStrawberryBombOrder > 0){
            receipt += icedStrawberryBombOrder + "x ICED STRAWBERRY BOMB --------------------" + icedStrawberryBombOrderPrice + "\n";
        }
        if(icedMixedBerriesOrder > 0){
            receipt += icedMixedBerriesOrder + "x ICED MIXED BERRIES --------------------" + icedMixedBerriesOrderPrice + "\n";
        }
        if(icedOreoLatteOrder > 0){
            receipt += icedOreoLatteOrder + "x ICED OREO LATTE --------------------" + icedOreoLatteOrderPrice + "\n";
        }
        if(icedCaramelMacchiatoOrder > 0){
            receipt += icedCaramelMacchiatoOrder + "x ICED CARAMEL MACCHIATO --------------------" + icedCaramelMacchiatoOrderPrice + "\n";
        }
        if(icedSpanishLatteOrder > 0){
            receipt += icedSpanishLatteOrder + "x ICED SPANISH LATTE --------------------" + icedSpanishLatteOrderPrice  + "\n";
        }
        if(totalFoodCount > 0){
            receipt += "\nTotal Amount ------------------------" + totalFoodPrice + "\n";
        }
        
        this.receipt = receipt;
            
        Object[] options = {"OK","CHECK OUT", "EDIT QUANTITY"};
        
        int choice = JOptionPane.showOptionDialog(null, receipt,"MOMMY'S VARIETY STORE", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, options,options[0]);
        
        if(choice == 0){
            return;
        }else if(choice == 1){
            payment();
            return;
        }else if(choice == 2){
            quantityType = "edit";
            foodCategoryChoice();
        }else{
            return;
        }
    }
    
    void calculatePrice(){
        samgyupRiceOrderPrice       = (samgyupRiceOrder * 60);
        oneBurgerSteakOrderPrice    =(oneBurgerSteakOrder * 45);
        twoBurgerSteakOrderPrice    =(twoBurgerSteakOrder * 65);
        threeChickenNuggetsOrderPrice =(threeChickenNuggetsOrder * 55);
        hotdogAndEggOrderPrice      =(hotdogAndEggOrder * 40);
        baconAndEggOrderPrice       =(baconAndEggOrder * 60);
        hamAndEggOrderPrice         =(hamAndEggOrder * 50);
        regularBurgerOrderPrice     =(regularBurgerOrder * 35);
        cheeseBurgerOrderPrice      =(cheeseBurgerOrder * 45);
        alohaBurgerOrderPrice       =(alohaBurgerOrder * 65);
        hamAndEggSandwichOrderPrice =(hamAndEggSandwichOrder * 45);
        baconAndEggSandwichOrderPrice =(baconAndEggSandwichOrder * 55);
        hotdogSandwichOrderPrice    =(hotdogSandwichOrder * 35);
        miloBlastOrderPrice         =(miloBlastOrder * 40);
        icedStrawberryBombOrderPrice =(icedStrawberryBombOrder * 69);
        icedMixedBerriesOrderPrice  =(icedMixedBerriesOrder * 69);
        icedOreoLatteOrderPrice     =(icedOreoLatteOrder * 69);
        icedCaramelMacchiatoOrderPrice =(icedCaramelMacchiatoOrder * 59);
        icedSpanishLatteOrderPrice  =(icedSpanishLatteOrder * 59);
        
        cheeseSmallFriesOrderPrice  =(cheeseSmallFriesOrder * 20);
        barbecueSmallFriesOrderPrice  =(barbecueSmallFriesOrder * 20);
        sourCreamSmallFriesOrderPrice  =(sourCreamSmallFriesOrder * 20);
        chiliBarbecueSmallFriesOrderPrice  =(chiliBarbecueSmallFriesOrder * 20);
        honeyButterSmallFriesOrderPrice  =(honeyButterSmallFriesOrder * 20);
        butterCheeseSmallFriesOrderPrice  =(butterCheeseSmallFriesOrder * 20);
        
        cheeseMediumFriesOrderPrice  =(cheeseMediumFriesOrder * 35);
        barbecueMediumFriesOrderPrice  =(barbecueMediumFriesOrder * 35);
        sourCreamMediumFriesOrderPrice  =(sourCreamMediumFriesOrder * 35);
        chiliBarbecueMediumFriesOrderPrice  =(chiliBarbecueMediumFriesOrder * 35);
        honeyButterMediumFriesOrderPrice  =(honeyButterMediumFriesOrder * 35);
        butterCheeseMediumFriesOrderPrice  =(butterCheeseMediumFriesOrder * 35);
        
        cheeseLargeFriesOrderPrice  =(cheeseLargeFriesOrder * 55);
        barbecueLargeFriesOrderPrice  =(barbecueLargeFriesOrder * 55);
        sourCreamLargeFriesOrderPrice  =(sourCreamLargeFriesOrder * 55);
        chiliBarbecueLargeFriesOrderPrice  =(chiliBarbecueLargeFriesOrder * 55);
        honeyButterLargeFriesOrderPrice  =(honeyButterLargeFriesOrder * 55);
        butterCheeseLargeFriesOrderPrice  =(butterCheeseLargeFriesOrder * 55);
        
        smallFriesOrder = (cheeseSmallFriesOrder + barbecueSmallFriesOrder + sourCreamSmallFriesOrder + chiliBarbecueSmallFriesOrder + honeyButterSmallFriesOrder + butterCheeseSmallFriesOrder);
        mediumFriesOrder = (cheeseMediumFriesOrder + barbecueMediumFriesOrder + sourCreamMediumFriesOrder + chiliBarbecueMediumFriesOrder + honeyButterMediumFriesOrder + butterCheeseMediumFriesOrder);
        largeFriesOrder = (cheeseLargeFriesOrder + barbecueLargeFriesOrder + sourCreamLargeFriesOrder + chiliBarbecueLargeFriesOrder + honeyButterLargeFriesOrder + butterCheeseLargeFriesOrder);
        
        smallFriesOrderPrice        =(smallFriesOrder * 20);
        mediumFriesOrderPrice       =(mediumFriesOrder * 35);
        largeFriesOrderPrice        =(largeFriesOrder * 55);
        
        totalFoodPrice = (samgyupRiceOrderPrice + oneBurgerSteakOrderPrice + twoBurgerSteakOrderPrice + threeChickenNuggetsOrderPrice + hotdogAndEggOrderPrice + baconAndEggOrderPrice + hamAndEggOrderPrice
                            + regularBurgerOrderPrice + cheeseBurgerOrderPrice + alohaBurgerOrderPrice + hamAndEggSandwichOrderPrice + baconAndEggSandwichOrderPrice + hotdogSandwichOrderPrice + miloBlastOrderPrice
                            + icedStrawberryBombOrderPrice + icedMixedBerriesOrderPrice + icedOreoLatteOrderPrice + icedCaramelMacchiatoOrderPrice + icedSpanishLatteOrderPrice + smallFriesOrderPrice + mediumFriesOrderPrice
                            + largeFriesOrderPrice);
        
        totalFoodCount = (samgyupRiceOrder + oneBurgerSteakOrder + twoBurgerSteakOrder + threeChickenNuggetsOrder + hotdogAndEggOrder + baconAndEggOrder + hamAndEggOrder
                            + regularBurgerOrder + cheeseBurgerOrder + alohaBurgerOrder + hamAndEggSandwichOrder + baconAndEggSandwichOrder + hotdogSandwichOrder + miloBlastOrder
                            + icedStrawberryBombOrder + icedMixedBerriesOrder + icedOreoLatteOrder + icedCaramelMacchiatoOrder + icedSpanishLatteOrder + smallFriesOrder + mediumFriesOrder
                            + largeFriesOrder);
    }
    
    void payment(){
        if(totalFoodCount <= 0){
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
        }
    }

    void checkOut() {
        //JOptionPane.showInputDialog(null, "ENTER NAME",)
        //JOptionPane.showInputDialog(null,receipt + "\n");
        String saleDetails = "---------------------------------------\n"
                            + "Name: " + userName + "         Sales ID: "+ saleId +"\n"
                            + "Adress: " + address + "          Date: " + date + "\n"
                            + receipt + "\n"
                            + "Payment: " + userPayment + "\n"
                            + "Change: " + (userPayment - totalFoodPrice);
        
        JOptionPane.showMessageDialog(null, saleDetails, "MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
        checkOutDone();
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
    
    void checkOutDone(){
        samgyupRiceOrder =0;
        oneBurgerSteakOrder =0;
        twoBurgerSteakOrder =0;
        threeChickenNuggetsOrder =0;
        hotdogAndEggOrder =0;
        baconAndEggOrder =0;
        hamAndEggOrder =0;
        regularBurgerOrder =0;
        cheeseBurgerOrder =0;
        alohaBurgerOrder =0;
        hamAndEggSandwichOrder =0;
        baconAndEggSandwichOrder =0;
        hotdogSandwichOrder =0;
        miloBlastOrder =0;
        icedStrawberryBombOrder =0;
        icedMixedBerriesOrder =0;
        icedOreoLatteOrder =0;
        icedCaramelMacchiatoOrder =0;
        icedSpanishLatteOrder =0;
        
        smallFriesOrder =0;
        cheeseSmallFriesOrder =0;
        barbecueSmallFriesOrder =0;
        sourCreamSmallFriesOrder =0;
        chiliBarbecueSmallFriesOrder =0;
        honeyButterSmallFriesOrder =0;
        butterCheeseSmallFriesOrder =0;
    
        mediumFriesOrder =0;
        cheeseMediumFriesOrder =0;
        barbecueMediumFriesOrder =0;
        sourCreamMediumFriesOrder =0;
        chiliBarbecueMediumFriesOrder =0;
        honeyButterMediumFriesOrder =0;
        butterCheeseMediumFriesOrder =0;
        
        largeFriesOrder =0;
        cheeseLargeFriesOrder =0;
        barbecueLargeFriesOrder =0;
        sourCreamLargeFriesOrder =0;
        chiliBarbecueLargeFriesOrder =0;
        honeyButterLargeFriesOrder =0;
        butterCheeseLargeFriesOrder =0;
    
        samgyupRiceOrderPrice =0;
        oneBurgerSteakOrderPrice =0;
        twoBurgerSteakOrderPrice =0;
        threeChickenNuggetsOrderPrice =0;
        hotdogAndEggOrderPrice =0;
        baconAndEggOrderPrice =0;
        hamAndEggOrderPrice =0;
        regularBurgerOrderPrice =0;
        cheeseBurgerOrderPrice =0;
        alohaBurgerOrderPrice =0;
        hamAndEggSandwichOrderPrice =0;
        baconAndEggSandwichOrderPrice =0;
        hotdogSandwichOrderPrice =0;
        miloBlastOrderPrice =0;
        icedStrawberryBombOrderPrice =0;
        icedMixedBerriesOrderPrice =0;
        icedOreoLatteOrderPrice =0;
        icedCaramelMacchiatoOrderPrice =0;
        icedSpanishLatteOrderPrice =0;
        
        smallFriesOrderPrice =0;
        cheeseSmallFriesOrderPrice =0;
        barbecueSmallFriesOrderPrice =0;
        sourCreamSmallFriesOrderPrice =0;
        chiliBarbecueSmallFriesOrderPrice =0;
        honeyButterSmallFriesOrderPrice =0;
        butterCheeseSmallFriesOrderPrice =0;
        
        mediumFriesOrderPrice =0;
        cheeseMediumFriesOrderPrice =0;
        barbecueMediumFriesOrderPrice =0;
        sourCreamMediumFriesOrderPrice =0;
        chiliBarbecueMediumFriesOrderPrice =0;
        honeyButterMediumFriesOrderPrice =0;
        butterCheeseMediumFriesOrderPrice =0;
        
        largeFriesOrderPrice =0;
        cheeseLargeFriesOrderPrice =0;
        barbecueLargeFriesOrderPrice =0;
        sourCreamLargeFriesOrderPrice =0;
        chiliBarbecueLargeFriesOrderPrice =0;
        honeyButterLargeFriesOrderPrice =0;
        butterCheeseLargeFriesOrderPrice =0;
    
        totalFoodCount =0;
        totalFoodPrice =0;
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
                                                                    + "[7] Back");
                    if(input == null){
                        break;
                    }
                    switch(input){
                        case "1":
                            getAccess.showAllStocks();
                            break;
                        case "2":
                            getAccess.showLowStock();
                            break;
                        case "3":
                            getAccess.updateStocks();
                            break;
                        case "4":
                            getAccess.updateReorderPoints();
                            break;
                        case "5":
                            getAccess.showOneItemInformation();
                            break;
                        case "6":
                            getAccess.checkForReorder();
                            break;
                        case "7":
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "INVALID INPUT!!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);      
                    }
                    break;
                case 1:
                    /*while(true){
                    try{
                    String inputId = JOptionPane.showInputDialog(null,"===============REORDER SECTION=================\n\n"
                    + "Enter Food ID You Want To Reorder" , "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
                    if(inputId == null){
                    break;
                    }
                    int foodId = Integer.parseInt(inputId);
                    
                    if(foodId != foodId){
                    JOptionPane.showMessageDialog(null, "Food ID not Found!!", "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
                    break;
                    }
                    
                    
                    //if quantity working
                    String inputReorderCount = JOptionPane.showInputDialog(null,"===============REORDER SECTION=================\n\n"
                    + "Enter quantity You Want To Reorder" , "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
                    if(inputReorderCount == null){
                    break;
                    }
                    int reorderCount = Integer.parseInt(inputReorderCount);
                    
                    
                    }catch(NumberFormatException er){
                    JOptionPane.showMessageDialog(null, er,"ERROR!",JOptionPane.ERROR_MESSAGE);
                    continue;
                    }
                    }*/
                    getAccess.showProfile(user);
                    break;
                case 2:
                    home();
                    break;
                    /*                case 3:
                    home();
                    break;*/
                default:
                    systemExit();
                    break;
            }
        }
    }
}
