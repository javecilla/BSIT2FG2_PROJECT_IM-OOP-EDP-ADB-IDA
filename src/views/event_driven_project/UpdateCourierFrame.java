package views.event_driven_project;

import config.MSSQLConnection;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class UpdateCourierFrame extends JFrame {
    private Connection connection;
    private ManageCouriersFrame parentFrame;
    private String riderId;
    
    // Form components
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField companyField;
    private JTextField contactField;
    private JButton updateButton;
    private JButton cancelButton;
    
    // Background
    private ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/views/Images/plain.png"));
    private JLabel background = new JLabel(backgroundIcon);
    
    public UpdateCourierFrame(ManageCouriersFrame parent, String riderId, String firstName, 
                              String lastName, String company, String contact) {
        this.parentFrame = parent;
        this.riderId = riderId;
        setupFrame();
        initComponents();
        layoutComponents();
        
        // Set initial field values
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        companyField.setText(company);
        contactField.setText(contact);
    }
    
    private void setupFrame() {
        setTitle("Update Courier Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(parentFrame);
        setResizable(false);
        
        // Configure background
        background.setLayout(null);
        setContentPane(background);
        
        // Add a title label at the top
        JLabel titleLabel = new JLabel("Update Courier Details");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        titleLabel.setForeground(new Color(95, 71, 214));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBounds(0, 20, 500, 40);
        background.add(titleLabel);
    }
    
    private void initComponents() {
        // Initialize form fields
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        companyField = new JTextField(20);
        contactField = new JTextField(20);
        
        // Create buttons
        updateButton = new JButton("Update");
        cancelButton = new JButton("Cancel");
        
        // Style components
        Font fieldFont = new Font("Poppins", Font.PLAIN, 14);
        
        firstNameField.setFont(fieldFont);
        lastNameField.setFont(fieldFont);
        companyField.setFont(fieldFont);
        contactField.setFont(fieldFont);
        
        // Style buttons
        updateButton.setFont(new Font("Poppins", Font.BOLD, 14));
        updateButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(95, 71, 214));
        updateButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 50, 180), 1, true),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        updateButton.setFocusPainted(false);
        updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        cancelButton.setFont(new Font("Poppins", Font.BOLD, 14));
        cancelButton.setForeground(new Color(95, 71, 214));
        cancelButton.setBackground(new Color(245, 245, 245));
        cancelButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(95, 71, 214), 1, true),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add action listeners
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCourier();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Initialize database connection
        try {
            connection = MSSQLConnection.getConnection();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Database connection error: " + ex.getMessage(),
                "Connection Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void layoutComponents() {
        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);
        
        // Add form labels and fields
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(new Font("Poppins", Font.BOLD, 14));
        firstNameLabel.setForeground(new Color(50, 50, 50));
        
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("Poppins", Font.BOLD, 14));
        lastNameLabel.setForeground(new Color(50, 50, 50));
        
        JLabel companyLabel = new JLabel("Company:");
        companyLabel.setFont(new Font("Poppins", Font.BOLD, 14));
        companyLabel.setForeground(new Color(50, 50, 50));
        
        JLabel contactLabel = new JLabel("Contact Number:");
        contactLabel.setFont(new Font("Poppins", Font.BOLD, 14));
        contactLabel.setForeground(new Color(50, 50, 50));
        
        // First row - First Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(firstNameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        mainPanel.add(firstNameField, gbc);
        
        // Second row - Last Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        mainPanel.add(lastNameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        mainPanel.add(lastNameField, gbc);
        
        // Third row - Company
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        mainPanel.add(companyLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        mainPanel.add(companyField, gbc);
        
        // Fourth row - Contact
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        mainPanel.add(contactLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        mainPanel.add(contactField, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 5, 10, 5);
        mainPanel.add(buttonPanel, gbc);
        
        // Position the main panel on the background
        mainPanel.setBounds(0, 70, 500, 300);
        background.add(mainPanel);
    }
    
    private void updateCourier() {
        // Validate input fields
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String company = companyField.getText().trim();
        String contact = contactField.getText().trim();
        
        if (firstName.isEmpty() || lastName.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "First name, last name, and contact are required fields.",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!contact.matches("^(09|\\+639)\\d{9}$")) {
            JOptionPane.showMessageDialog(this,
                "Contact number must be valid, (09), 11 digit or with +63.",
                "Contact Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Prepare SQL statement
            String sql = "UPDATE COURIER SET First_Name = ?, Last_Name = ?, Courier_Company = ?, Courier_Contact = ? " +
                         "WHERE Rider_ID = ?";
            
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, company);
            statement.setString(4, contact);
            statement.setString(5, riderId);
            
            // Execute the query
            int rowsUpdated = statement.executeUpdate();
            
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this,
                    "Courier details updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh the parent frame's courier list
                parentFrame.refreshCourierList();
                
                // Close this frame
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to update courier details. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
            statement.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Database error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void dispose() {
        // Close database connection if open
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.err.println("Error closing connection: " + ex.getMessage());
            }
        }
        super.dispose();
    }
}