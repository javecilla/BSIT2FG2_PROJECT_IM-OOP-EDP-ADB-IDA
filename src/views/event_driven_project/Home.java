/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views.event_driven_project;

import controllers.UserController;
import core.Session;
import helpers.Response;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.*;
import javax.swing.border.*;
import models.User; 
import static views.event_driven_project.LoginForm.USER_CONTROLLER;

/**
 *
 * @author Admin
 */
public class Home extends JFrame implements ActionListener{
    private EventController controller;
    public Home(EventController eventController){
        this.controller = eventController;
        homeFrameConfig();
    }
    //INITIALIZATION OF COMPONENTS
    //Frames
    
    //Image Icon
    ImageIcon logo = new ImageIcon(getClass().getResource("/views/Images/logo.png"));
    
    ImageIcon homeIcon = new ImageIcon(getClass().getResource("/views/Images/homepage/landing.png"));
    ImageIcon homeGif = new ImageIcon(getClass().getResource("/views/Images/homepage/Landing_gif.gif"));
    ImageIcon orderNowIcon = new ImageIcon(getClass().getResource("/views/Images/homepage/order.png"));
    ImageIcon logoutIcon = new ImageIcon(getClass().getResource("/views/Images/logout.png"));
    ImageIcon homeButtonIcon = new ImageIcon(getClass().getResource("/views/Images/homepage/home button.png"));
    ImageIcon browseButtonIcon = new ImageIcon(getClass().getResource("/views/Images/homepage/browse button.png"));
    ImageIcon loginButtonIcon = new ImageIcon(getClass().getResource("/views/Images/homepage/big-login.png"));

    //Labels
    JLabel homeLabel = new JLabel(homeIcon);
    JLabel homeLabel2 = new JLabel(homeGif);

    //Buttons
    JButton orderNow = new JButton();
    JButton browseMenu = new JButton();
    JButton home = new JButton();
    JButton login = new JButton();
    JButton logout = new JButton();
    
    //TextField
    
    //Panels
    JPanel homePanel = new JPanel();
    
    public void homeFrameConfig(){
        //panel configuration
        homePanel.setLayout(null);
        homePanel.setBounds(0, 0, homeIcon.getIconWidth(), homeIcon.getIconHeight());
        
        //labelConfiguration
        homeLabel.setBounds(0,0,homeIcon.getIconWidth(),homeIcon.getIconHeight());
        homeLabel2.setBounds(0,0,homeGif.getIconWidth(),homeGif.getIconHeight());
        
        //buttonconfiguration
        setupButton(orderNow, orderNowIcon);
        setupButton(login, loginButtonIcon);
        setupButton(logout, logoutIcon);
        setupButton(browseMenu, browseButtonIcon);
        
        orderNow.setBounds(110, 600, orderNowIcon.getIconWidth(), orderNowIcon.getIconHeight());
        
        login.setBounds(840, 80, loginButtonIcon.getIconWidth(),loginButtonIcon.getIconHeight() );
        logout.setBounds(840, 80, logoutIcon.getIconWidth(),logoutIcon.getIconHeight() );
        
        browseMenu.setBounds(1000, 80, browseButtonIcon.getIconWidth(),browseButtonIcon.getIconHeight() );
        
        logout.setVisible(false);
        
        //adding to the panel
        homePanel.add(orderNow);
        homePanel.add(browseMenu);
        homePanel.add(login);
        homePanel.add(logout);
        homePanel.add(homeLabel2);startAnimation();
        homePanel.add(homeLabel);
        
        //adding to the frame
        this.add(homePanel);
        
        //frame configuration
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(homeIcon.getIconWidth(), homeIcon.getIconHeight());
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    private void startAnimation() {
        int targetX = 800; // The x position where the JLabel should stop
        int animationDelay = 5; // Delay in milliseconds between position updates

        // Create a Timer for the animation
        Timer animationTimer = new Timer(animationDelay, new ActionListener() {
            int x = 1500; // Initial x position

            @Override
            public void actionPerformed(ActionEvent e) {
                if (x > targetX) {
                    x -= 5; // Move 5 pixels to the left
                    homeLabel2.setLocation(x, 580); // Keep y constant at 400
                } else {
                    ((Timer) e.getSource()).stop(); // Stop the animation timer when target position is reached
                }
            }
        });

        animationTimer.start(); // Start the animation timer
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == orderNow || e.getSource() == browseMenu){
            User user = Session.getLoggedInUser();
            if(user == null){
                controller.showLoginFrame(this);
            }else{
                controller.showMenuFrame(this);
                controller.menuFrame.setButtonVisibility();
            }
        }
        
        if(e.getSource() == login){
            controller.showLoginFrame(this);
        }
        
        if(e.getSource() == logout){
            Response<User> logoutResponse = USER_CONTROLLER.logoutUser();
                if (logoutResponse.isSuccess()) {
                    controller.setUser(null);
                    controller.setCartID(-1);
                    controller.setOrderCount(-1);
                    controller.homeFrame.logout.setVisible(false);
                    controller.homeFrame.login.setVisible(true);
                    controller.showHomeFrame(this);
                    JOptionPane.showMessageDialog(this, logoutResponse.getMessage(), "logging out...", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    //System.out.println(logoutResponse.getMessage());
                    JOptionPane.showMessageDialog(this, logoutResponse.getMessage(), "logging out...", JOptionPane.ERROR_MESSAGE);
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
