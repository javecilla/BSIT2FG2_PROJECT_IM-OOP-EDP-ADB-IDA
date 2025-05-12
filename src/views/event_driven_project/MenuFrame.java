/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views.event_driven_project;

import config.MSSQLConnection;
import controllers.UserController;
import core.Session;
import helpers.Response;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;
import models.User;
/**
 *
 * @author Admin
 */
public class MenuFrame extends JFrame implements ActionListener, ChangeAddressFrame.AddressChangeListener, ChangeInfoFrame.InfoChangeListener{
    private EventController controller;
    public MenuFrame(EventController eventController){
        this.controller = eventController;
        menuFrameConfig();
        buttonRegister();
        setCartItemCount();
    }
    //INITIALIZATION OF COMPONENTS
    protected static final UserController USER_CONTROLLER = new UserController();
    //String
    String address;
    String firstname;
    String lastname;
    String username;
    String password;
    String phone;
    
    //Image Icon
    ImageIcon logo = new ImageIcon(getClass().getResource("/views/Images/logo.png"));
    
    ImageIcon bannerGif = new ImageIcon(getClass().getResource("/views/Images/categoryMenu/banner.gif"));
    ImageIcon categoryIcon = new ImageIcon(getClass().getResource("/views/Images/categoryMenu/category.png"));
    ImageIcon riceMealStat = new ImageIcon(getClass().getResource("/views/Images/categoryMenu/rice meal stat.png"));
    ImageIcon riceMealHover = new ImageIcon(getClass().getResource("/views/Images/categoryMenu/Ricemeal-hover.png"));
    ImageIcon sandwichStat = new ImageIcon(getClass().getResource("/views/Images/categoryMenu/Sandwich stat.png"));
    ImageIcon sandwichHover = new ImageIcon(getClass().getResource("/views/Images/categoryMenu/Sandwiches-hover.png"));
    ImageIcon friesStat = new ImageIcon(getClass().getResource("/views/Images/categoryMenu/Fries stat.png"));
    ImageIcon friesHover = new ImageIcon(getClass().getResource("/views/Images/categoryMenu/fries-hover.png"));
    ImageIcon drinkStat = new ImageIcon(getClass().getResource("/views/Images/categoryMenu/Drinks stat.png"));
    ImageIcon drinkHover = new ImageIcon(getClass().getResource("/views/Images/categoryMenu/drinks-hover.png"));
    
    ImageIcon homeButtonIcon = new ImageIcon(getClass().getResource("/views/Images/homepage/home button.png"));
    ImageIcon changeInfoIcon = new ImageIcon(getClass().getResource("/views/Images/change-info.png"));
    ImageIcon changeAddressIcon = new ImageIcon(getClass().getResource("/views/Images/change-address.png"));
    ImageIcon cartButtonIcon = new ImageIcon(getClass().getResource("/views/Images/shopping Basket.png"));
    
    //Labels
    JLabel menuLabel = new JLabel(bannerGif);
    JLabel menuLabel2 = new JLabel(categoryIcon);
    JLabel menuLabel3 = new JLabel("Welcome to our Menu!");
    JLabel cartLabel = new JLabel();
    
    //Buttons
    JButton riceMealButton = new JButton();
    JButton sandwichButton = new JButton();
    JButton friesButton = new JButton();
    JButton drinksButton = new JButton();
    JButton homeButton = new JButton();
    JButton cartButton = new JButton();
    JButton changeInfoButton = new JButton();
    JButton changeAddressButton = new JButton();
    JButton orderReceivedButton = new JButton("Order Receive");
    
    //TextField
    
    //Menus
    //Panels
    JPanel menuPanel1 = new JPanel();
    JPanel navPanel = new JPanel();
    
    public void menuFrameConfig(){
        //panel1 configuration
        menuPanel1.setLayout(null);
        menuPanel1.setBounds(0, 0, categoryIcon.getIconWidth(), categoryIcon.getIconHeight());
        
        navPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        navPanel.setOpaque(false);
        navPanel.setBounds(0, 160, categoryIcon.getIconWidth()-10, homeButtonIcon.getIconHeight() + 5);
        
        cartButton.setOpaque(false);
        cartButton.setBounds(categoryIcon.getIconWidth()-150, 222, cartButtonIcon.getIconWidth(), cartButtonIcon.getIconHeight());
        
        //label configuration
        menuLabel.setBounds(0,0,bannerGif.getIconWidth(),bannerGif.getIconHeight());
        menuLabel2.setBounds(0,0, categoryIcon.getIconWidth(),categoryIcon.getIconHeight());
        menuLabel3.setBounds(50, 145, 1000, 200);
        menuLabel3.setFont(new Font("Arial Black",Font.BOLD,40));
        menuLabel3.setForeground(new Color(90,79,207));
        
        cartLabel.setFont(new Font("Arial Black",Font.BOLD,30));
        cartLabel.setForeground(Color.ORANGE);
        
        setupButton(homeButton, homeButtonIcon);
        setupButton(cartButton, cartButtonIcon);
        setupButton(changeInfoButton, changeInfoIcon);
        setupButton(changeAddressButton, changeAddressIcon);
        setupButton1(orderReceivedButton);
        
        cartButton.add(cartLabel);
        
        navPanel.add(orderReceivedButton);
        navPanel.add(homeButton);
        navPanel.add(changeInfoButton);
        navPanel.add(changeAddressButton);
        
        //rice meal button
        riceMealButton.setIcon(riceMealStat);
        riceMealButton.setBorder(new EmptyBorder(0,0,0,0));
        riceMealButton.setFocusPainted(false);
        riceMealButton.setContentAreaFilled(false);
        riceMealButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        riceMealButton.setBounds(30, 300, riceMealStat.getIconWidth(), riceMealStat.getIconHeight());
        riceMealButton.setRolloverIcon(riceMealHover);
        
        riceMealButton.setPressedIcon(riceMealHover);
        riceMealButton.setSelectedIcon(riceMealHover);
        
        //sandwich button
        sandwichButton.setIcon(sandwichStat);
        sandwichButton.setBorder(new EmptyBorder(0,0,0,0));
        sandwichButton.setFocusPainted(false);
        sandwichButton.setContentAreaFilled(false);
        sandwichButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sandwichButton.setBounds(615, 300, sandwichStat.getIconWidth(), sandwichStat.getIconHeight());
        sandwichButton.setRolloverIcon(sandwichHover);
        
        sandwichButton.setPressedIcon(sandwichHover);
        sandwichButton.setSelectedIcon(sandwichHover);
        
        //fries button
        friesButton.setIcon(friesStat);
        friesButton.setBorder(new EmptyBorder(0,0,0,0));
        friesButton.setFocusPainted(false);
        friesButton.setContentAreaFilled(false);
        friesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        friesButton.setBounds(30, 550, friesStat.getIconWidth(), friesStat.getIconHeight());
        friesButton.setRolloverIcon(friesHover);
        
        friesButton.setPressedIcon(friesHover);
        friesButton.setSelectedIcon(friesHover);
        
        //drinks button
        drinksButton.setIcon(drinkStat);
        drinksButton.setBorder(new EmptyBorder(0,0,0,0));
        drinksButton.setFocusPainted(false);
        drinksButton.setContentAreaFilled(false);
        drinksButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        drinksButton.setBounds(615, 550, drinkStat.getIconWidth(), drinkStat.getIconHeight());
        drinksButton.setRolloverIcon(drinkHover);
        
        drinksButton.setPressedIcon(drinkHover);
        drinksButton.setSelectedIcon(drinkHover);
        
        //adding to the panel1
        menuPanel1.add(riceMealButton);
        menuPanel1.add(sandwichButton);
        menuPanel1.add(friesButton);
        menuPanel1.add(drinksButton);
        menuPanel1.add(menuLabel3);
        menuPanel1.add(menuLabel2);
        
        //adding to the menuFrame
        this.add(menuLabel);
        this.add(cartButton);
        this.add(navPanel);
        this.add(menuPanel1);
        
        //frame configuration
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(categoryIcon.getIconWidth(), categoryIcon.getIconHeight());
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(false);
    }
    
    public void buttonRegister(){
        riceMealButton.addActionListener(this);
        sandwichButton.addActionListener(this);
        friesButton.addActionListener(this);
        drinksButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == riceMealButton){
            controller.showRiceMealsFrame(this);
        }
        
        if(e.getSource() == sandwichButton){
            controller.showSandwichesFrame(this);
        }
        
        if(e.getSource() == homeButton){
            controller.showHomeFrame(this);
        }
        
        if(e.getSource() == friesButton){
            controller.showFriesFrame(this);
        }
        
        if(e.getSource() == drinksButton){
            controller.showDrinksFrame(this);
        }
        
        if(e.getSource() == cartButton){
            //controller.showCartFrame(this);
            CartFrame cart = new CartFrame(controller);
            cart.setVisible(true);
        }
        
        if(e.getSource() == changeAddressButton){
            User user = controller.getUser();
            address = user.getHouseNumber() + ", " + 
                    user.getStreet() + ", " + 
                    user.getBarangay() + ", " + 
                    user.getMunicipality() + ", " +
                    user.getProvince() + ", " +
                    user.getRegion();
            
            ChangeAddressFrame.showDialog(this, user.getUserId(),address, this);
        }
        
        if(e.getSource() == changeInfoButton){
            User user = controller.getUser();
            ChangeInfoFrame.showDialog(this, user.getUserId(), user.getFirstName(), user.getLastName(), user.getContactNumber(), user.getUsername(), this);
        }
        
        if(e.getSource() == orderReceivedButton){
            OrderReceivedFrame received = new OrderReceivedFrame(controller);
            received.setVisible(true);
            this.dispose();
        }
    }
    
    public void setCartItemCount(){
        if(controller.getUser() != null){
            CartFrame cart = new CartFrame(controller);
            int itemCount = cart.getCartItemCount();
            if(itemCount == 0){
                cartLabel.setVisible(false);
            }else{
                cartLabel.setVisible(true);
                cartLabel.setText(itemCount + "");
            }
        }
    }

    private void setupButton(JButton button, ImageIcon icon) {
        button.setIcon(icon);                      // Set the icon for the button
        button.setBorder(new EmptyBorder(0, 0, 0, 0));  // Remove the button's default border
        button.setFocusPainted(false);             // Disable the focus highlight when the button is clicked
        button.setContentAreaFilled(false);        // Disable the default content area fill (transparent background)
        button.addActionListener(this);            // Attach the action listener (so the button can trigger actions)
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Change icon when mouse is rolled over
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(darkenImageIcon(icon));  // Darken the image when hovered
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(icon);  // Restore the original image when not hovered
            }
        });
    }
    
   private void setupButton1(JButton button) {                     
        button.setBorder(new EmptyBorder(0, 0, 0, 0));  
        button.setBackground(new Color(95, 71, 214));
        button.setForeground(Color.WHITE);
        button.addActionListener(this);           
        button.setPreferredSize(new Dimension(homeButtonIcon.getIconWidth(), homeButtonIcon.getIconHeight()));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
        
        private ImageIcon darkenImageIcon(ImageIcon icon) {
        Image img = icon.getImage();  // Get the image from the icon
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(img, 0, 0, null);

        // Apply a filter to darken the image
        RescaleOp rescaleOp = new RescaleOp(0.7f, 0, null);  // Darken by 30%
        rescaleOp.filter(bufferedImage, bufferedImage);

        g2d.dispose();
        return new ImageIcon(bufferedImage);  // Return the modified darkened image icon
    }

    @Override
    public void onAddressChanged(String newAddress) {
        address = newAddress;
        String parts[] = parseAddressParts(address);
        
        controller.getUser().setHouseNumber(parts[0]);
        controller.getUser().setStreet(parts[1]);
        controller.getUser().setBarangay(parts[2]);
        controller.getUser().setMunicipality(parts[3]);
        controller.getUser().setProvince(parts[4]);
        controller.getUser().setRegion(parts[5]);
    }

    @Override
    public void onNameChanged(String firstName, String lastName) {
        controller.getUser().setFirstName(firstName);
        controller.getUser().setLastName(lastName);
    }

    @Override
    public void onPhoneChanged(String newPhone) {
        controller.getUser().setContactNumber(newPhone);
    }

    @Override
    public void onUsernameChanged(String newUsername) {
        controller.getUser().setUsername(newUsername);
    }

    @Override
    public void onPasswordChanged(String newPassword) {
        controller.getUser().setPassword(newPassword);
    }
    
    private String[] parseAddressParts(String address) {
        String[] parts = new String[6];
        
        // Initialize with empty strings
        for (int i = 0; i < parts.length; i++) {
            parts[i] = "";
        }
        
        if (address != null && !address.isEmpty()) {
            String[] splitAddress = address.split(",");
            
            // Copy available parts
            for (int i = 0; i < Math.min(splitAddress.length, parts.length); i++) {
                parts[i] = splitAddress[i].trim();
            }
        }
        
        return parts;
    }
    
    public int countUserOrders(int userID) {
        int orderCount = -1;
        try (Connection connection = MSSQLConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                 "SELECT COUNT(*) FROM SALE WHERE Customer_ID = ? AND Order_Status = 'Pending'")) {
            stmt.setInt(1, userID); // Bind the userID parameter
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    orderCount = rs.getInt(1); // Get the count from the first column
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error counting orders: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            // Optionally log the error for debugging
            //System.err.println("Error counting orders for userID " + userID + ": " + e.getMessage());
        }
        return orderCount;
    }
    
    public void setButtonVisibility(){
        if(controller.getOrderCount() <= 0){
            orderReceivedButton.setVisible(false);
        }else{
            orderReceivedButton.setVisible(true);
        }
    }
}
