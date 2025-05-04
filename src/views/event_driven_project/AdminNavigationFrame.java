package views.event_driven_project;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AdminNavigationFrame extends JFrame implements ActionListener {
    private EventController controller;
    public AdminNavigationFrame(EventController eventController){
        this.controller = eventController;
        initializeFrame();
        initializeComponents();
        layoutComponents();
    }
    
    // Main components
    private JPanel mainPanel;
    private JLabel titleLabel;
    
    // Navigation buttons
    private JButton manageStocksButton;
    private JButton manageCourierButton;
    private JButton logoutButton;
    
    // Icon for background if needed
    private ImageIcon backgroundIcon;
    private JLabel backgroundLabel;
    
    // Frame dimensions
    private final int FRAME_WIDTH = 600;
    private final int FRAME_HEIGHT = 400;
    
    // Colors
    private final Color PRIMARY_COLOR = new Color(95, 71, 214);
    private final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private final Color TEXT_COLOR = new Color(50, 50, 50);
    
    private void initializeFrame() {
        setTitle("Admin System Navigation");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setResizable(false);
        
        // Try to load a background image if available
        try {
            backgroundIcon = new ImageIcon(getClass().getResource("/views/Images/nav_background.png"));
            backgroundLabel = new JLabel(backgroundIcon);
            backgroundLabel.setLayout(null);
            setContentPane(backgroundLabel);
        } catch (Exception e) {
            // Fall back to plain background if image not found
            mainPanel = new JPanel(null);
            mainPanel.setBackground(BACKGROUND_COLOR);
            setContentPane(mainPanel);
        }
    }
    
    private void initializeComponents() {
        // Create the title
        titleLabel = new JLabel("Admin System Navigation");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Create navigation buttons
        manageStocksButton = createStyledButton("Manage Stocks", "");
        manageCourierButton = createStyledButton("Manage Courier", "");
        logoutButton = createStyledButton("Logout", "");
        
        // Add action listeners
        manageStocksButton.addActionListener(this);
        manageCourierButton.addActionListener(this);
        logoutButton.addActionListener(this);
    }
    
    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        
        // Try to load icon if available
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            // Resize icon if needed
            Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
            button.setIconTextGap(10);
        } catch (Exception e) {
            // Continue without icon if not found
        }
        
        // Style the button
        button.setFont(new Font("Poppins", Font.BOLD, 16));
        button.setForeground(PRIMARY_COLOR);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(230, 230, 245));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
            }
        });
        
        return button;
    }
    
    private void layoutComponents() {
        // Title positioning
        titleLabel.setBounds(100, 40, FRAME_WIDTH - 200, 50);
        getContentPane().add(titleLabel);
        
        // Position buttons vertically in the center with spacing
        int buttonWidth = 250;
        int buttonHeight = 50;
        int buttonX = (FRAME_WIDTH - buttonWidth) / 2;
        int startY = 120;
        int spacing = 70;
        
        manageStocksButton.setBounds(buttonX, startY, buttonWidth, buttonHeight);
        manageCourierButton.setBounds(buttonX, startY + spacing, buttonWidth, buttonHeight);
        logoutButton.setBounds(buttonX, startY + spacing * 2, buttonWidth, buttonHeight);
        
        // Add components to the frame
        getContentPane().add(manageStocksButton);
        getContentPane().add(manageCourierButton);
        getContentPane().add(logoutButton);
        
        // Add a styled footer if using a panel
        if (mainPanel != null) {
            JPanel footerPanel = new JPanel();
            footerPanel.setBackground(PRIMARY_COLOR);
            footerPanel.setBounds(0, FRAME_HEIGHT - 30, FRAME_WIDTH, 30);
            mainPanel.add(footerPanel);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == manageStocksButton) {
            controller.showManageStockFrame(this);
        } else if (e.getSource() == manageCourierButton) {
            controller.showManageCourierFrame(this);
        } else if (e.getSource() == logoutButton) {
            handleLogout();
        }
    }
    
    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        if (choice == JOptionPane.YES_OPTION) {
            // Here would handle any cleanup or session end tasks
            
            // For example, want to open a login frame
            // LoginForm LoginForm = new LoginForm();
            // LoginForm.setVisible(true);
            
            // Close this frame
            this.dispose();
            controller.showHomeFrame(this);
            // Or exit the application if this is the main entry point
            // System.exit(0);
        }
    }
 
}