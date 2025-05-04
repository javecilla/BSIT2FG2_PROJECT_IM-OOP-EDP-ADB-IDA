/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views.event_driven_project;

import config.MSSQLConnection;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;
/**
 *
 * @author Admin
 */
public class MenuFrame extends JFrame implements ActionListener{
    private EventController controller;
    public MenuFrame(EventController eventController){
        this.controller = eventController;
        menuFrameConfig();
        buttonRegister();
        setCartItemCount();
    }
    //INITIALIZATION OF COMPONENTS
    
    //Frames
    
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
        
        cartButton.add(cartLabel);
        
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
            controller.showCartFrame(this);
        }
    }
    
    public void setCartItemCount(){
        cartLabel.setText(getCartItemCount() + "");
    }
    
    public int getCartItemCount() {
        String sql = "SELECT COUNT(*) AS total FROM CART_ITEM";
        int count = 0;

        try (Connection conn = MSSQLConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt("total"); // or rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // or log the error
        }
        return count;
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
}
