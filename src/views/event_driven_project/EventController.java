// Controller Class
package views.event_driven_project;

import javax.swing.JFrame;

public class EventController{
    private Home homeFrame;
    private MenuFrame menuFrame;
    private MenuRiceMeals riceMealsFrame;
    private MenuSandwiches sandwichesFrame;
    private MenuFries friesFrame;
    private MenuDrinks drinksFrame;
    private LoginForm loginFrame;
    private RegisterForm registerFrame;
    private RegisterForm2 registerFrame2;
    private OrderFrame orderFrame;
    private Cart cartFrame;
    private Payment paymentFrame;
    private AdminNavigationFrame adminNavFrame;
    private ManageStocksFrame manageStockFrame;
    private ManageCouriersFrame manageCourierFrame;
    private OtwFrame otwFrame;
    private AdminDashboardFrame dashboardFrame;

    public EventController() {
        homeFrame = new Home(this);
        menuFrame = new MenuFrame(this);
        riceMealsFrame = new MenuRiceMeals(this);
        sandwichesFrame = new MenuSandwiches(this);
        friesFrame = new MenuFries(this);
        drinksFrame = new MenuDrinks(this);
        loginFrame = new LoginForm(this);
        registerFrame = new RegisterForm(this);
        registerFrame2 = new RegisterForm2(this);
        cartFrame = new Cart(this);
        paymentFrame = new Payment(this);
        adminNavFrame = new AdminNavigationFrame(this);
        manageStockFrame = new ManageStocksFrame(this);
        manageCourierFrame = new ManageCouriersFrame(this);
        otwFrame = new OtwFrame(this);
        dashboardFrame = new AdminDashboardFrame(this);
    }

    public void showHomeFrame(JFrame currentFrame) {
        currentFrame.setVisible(false);
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
        currentFrame.setVisible(true);
        loginFrame.setVisible(true);
    }
    
    public void showRegisterFrame(JFrame currentFrame) {
        currentFrame.setVisible(false);
        registerFrame.setVisible(true);
    }
    
    public void showRegisterFrame2(JFrame currentFrame) {
        currentFrame.setVisible(false);
        registerFrame2.setVisible(true);
    }
    
    public void showCartFrame(JFrame currentFrame) {
        currentFrame.setVisible(true);
        cartFrame.setVisible(true);
    }
        
    public void showPaymentFrame(JFrame currentFrame) {
        currentFrame.setVisible(false);
        paymentFrame.setVisible(true);
    }
    
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
    
    public void showOtwFrame(JFrame currentFrame) {
        currentFrame.setVisible(false);
        otwFrame.setVisible(true);
    }
    
    public void showDashboardFrame(JFrame currentFrame) {
        currentFrame.setVisible(false);
        dashboardFrame.setVisible(true);
    }
    
}