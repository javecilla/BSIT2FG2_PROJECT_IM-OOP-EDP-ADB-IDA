// Controller Class
package views.event_driven_project;

import controllers.UserController;
import core.Session;
import javax.swing.JFrame;
import models.User;

public class EventController{
    public Home homeFrame;
    public MenuFrame menuFrame;
    public MenuRiceMeals riceMealsFrame;
    public MenuSandwiches sandwichesFrame;
    public MenuFries friesFrame;
    public MenuDrinks drinksFrame;
    private LoginForm loginFrame;
    private RegisterForm registerFrame;
    //private RegisterForm2 registerFrame2;
    //private OrderFrame orderFrame;
    //private CartFrame cartFrame;
    //private Payment paymentFrame;
    private AdminNavigationFrame adminNavFrame;
    public ManageStocksFrame manageStockFrame;
    public ManageCouriersFrame manageCourierFrame;
    //private OtwFrame otwFrame;
    private AdminDashboardFrame dashboardFrame;
    
    protected static final UserController USER_CONTROLLER = new UserController();
    private User user;
    private static int cartID = -1;
    private int orderCount = -1;

    public EventController() {
        homeFrame = new Home(this);
        menuFrame = new MenuFrame(this);
        riceMealsFrame = new MenuRiceMeals(this);
        sandwichesFrame = new MenuSandwiches(this);
        friesFrame = new MenuFries(this);
        drinksFrame = new MenuDrinks(this);
        loginFrame = new LoginForm(this);
        registerFrame = new RegisterForm(this);
        //registerFrame2 = new RegisterForm2(this);
        //cartFrame = new CartFrame(this);
        //paymentFrame = new Payment(this);
        adminNavFrame = new AdminNavigationFrame(this);
        manageStockFrame = new ManageStocksFrame(this);
        manageCourierFrame = new ManageCouriersFrame(this);
        //otwFrame = new OtwFrame(this);
        dashboardFrame = new AdminDashboardFrame(this);
    }

    public void showHomeFrame(JFrame currentFrame) {
        if (currentFrame != null) {
            currentFrame.setVisible(false);
        }
        homeFrame.setVisible(true);
    }

    public void showMenuFrame(JFrame currentFrame) {
        currentFrame.setVisible(false);
        menuFrame.setVisible(true);
    }

    public void showRiceMealsFrame(JFrame currentFrame) {
        currentFrame.setVisible(false);
        riceMealsFrame.setVisible(true);
    }
    
    public void showSandwichesFrame(JFrame currentFrame) {
        currentFrame.setVisible(false);
        sandwichesFrame.setVisible(true);
    }

    public void showFriesFrame(JFrame currentFrame) {
        currentFrame.setVisible(false);
        friesFrame.setVisible(true);
    }
    
    public void showDrinksFrame(JFrame currentFrame) {
        currentFrame.setVisible(false);
        drinksFrame.setVisible(true);
    }
    
    public void showLoginFrame(JFrame currentFrame) {
        // Don't hide the current frame if you want it to remain visible
        // This allows a login dialog to appear over the current frame
        loginFrame.setVisible(true);
    }
    
    public void showRegisterFrame(JFrame currentFrame) {
        if (currentFrame != null) {
            currentFrame.setVisible(false);
        }
        registerFrame.setVisible(true);
    }
    
//    public void showRegisterFrame2(JFrame currentFrame) {
//        currentFrame.setVisible(false);
//        registerFrame2.setVisible(true);
//    }
    
//    public void showCartFrame(JFrame currentFrame) {
//        currentFrame.setVisible(true);
//        cartFrame.setVisible(true);
//    }
        
//    public void showPaymentFrame(JFrame currentFrame) {
//        currentFrame.setVisible(false);
//        paymentFrame.setVisible(true);
//    }
    
    public void showAdminNavFrame(JFrame currentFrame) {
        currentFrame.setVisible(false);
        adminNavFrame.setVisible(true);
    }
    
    public void showManageStockFrame(JFrame currentFrame) {
        currentFrame.setVisible(false);
        manageStockFrame.setVisible(true);
    }
    
    public void showManageCourierFrame(JFrame currentFrame) {
        currentFrame.setVisible(false);
        manageCourierFrame.setVisible(true);
    }
    
//    public void showOtwFrame(JFrame currentFrame) {
//        currentFrame.setVisible(false);
//        otwFrame.setVisible(true);
//    }
    
    public void showDashboardFrame(JFrame currentFrame) {
        currentFrame.setVisible(false);
        dashboardFrame.setVisible(true);
    }
    
    public void setUser(User user){
        this.user = user;
    }
    
    public User getUser(){
        return user;
    }
    
    public void setCartID(int cartID){
        this.cartID = cartID;
    }
    
    public int getCartID(){
        return cartID;
    }
    
    public void setOrderCount(int orderCount){
        this.orderCount = orderCount;
    }
    
    public int getOrderCount(){
        return orderCount;
    }
    
    public void frameErase(){
        if(this.homeFrame.isVisible()){
            this.homeFrame.setVisible(false);
        }
        if(this.adminNavFrame.isVisible()){
            this.adminNavFrame.setVisible(false);
        }
        if(this.dashboardFrame.isVisible()){
            this.dashboardFrame.setVisible(false);
        }
        if(this.drinksFrame.isVisible()){
            this.drinksFrame.setVisible(false);
        }
        if(this.friesFrame.isVisible()){
            this.friesFrame.setVisible(false);
        }
        if(this.loginFrame.isVisible()){
            this.loginFrame.setVisible(false);
        }
        if(this.manageCourierFrame.isVisible()){
            this.manageCourierFrame.setVisible(false);
        }
        if(this.manageStockFrame.isVisible()){
            this.manageStockFrame.setVisible(false);
        }
        if(this.menuFrame.isVisible()){
            this.menuFrame.setVisible(false);
        }
        if(this.registerFrame.isVisible()){
            this.registerFrame.setVisible(false);
        }
        if(this.riceMealsFrame.isVisible()){
            this.riceMealsFrame.setVisible(false);
        }
        if(this.sandwichesFrame.isVisible()){
            this.sandwichesFrame.setVisible(false);
        }
    }
}