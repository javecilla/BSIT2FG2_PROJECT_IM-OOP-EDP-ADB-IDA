package views.event_driven_project;

import config.MSSQLConnection;
import helpers.Response;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import models.User;
import static views.event_driven_project.EventController.USER_CONTROLLER;

/**
 * Reusable component for changing a user's address
 * Can be used across multiple parts of the application
 */
public class ChangeAddressFrame extends JDialog {
    
    // Interface for callback when address is changed
    public interface AddressChangeListener {
        void onAddressChanged(String newAddress);
    }
    
    private JTextField houseNumberField;
    private JTextField streetField;
    private JTextField barangayField;
    private JTextField cityField;
    private JTextField provinceField;
    private JTextField regionField;
    private JButton saveBtn;
    private JButton cancelBtn;
    
    private AddressChangeListener listener;
    private int userId; // To identify which user's address to update
    
    /**
     * Constructor for creating the address change dialog
     * 
     * @param parent The parent frame
     * @param userId The ID of the user whose address is being changed
     * @param currentAddress The current address to pre-populate fields
     * @param listener Callback listener for when address is changed
     */
    public ChangeAddressFrame(JFrame parent, int userId, String currentAddress, AddressChangeListener listener) {
        super(parent, "Change Delivery Address", true);
        this.listener = listener;
        this.userId = userId;
        
        initComponents(currentAddress);
    }
    
    /**
     * Alternative constructor with just the current address as string
     */
    public ChangeAddressFrame(JFrame parent, String currentAddress, AddressChangeListener listener) {
        super(parent, "Change Delivery Address", true);
        this.listener = listener;
        this.userId = 1; // No specific user ID
        
        initComponents(currentAddress);
    }
    
    private void initComponents(String currentAddress) {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        
        // Split the current address into components
        String[] addressParts = parseAddressParts(currentAddress);
        
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 6, 10));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel houseNumberLabel = new JLabel("House Number:");
        houseNumberField = new JTextField(addressParts[0]);
        
        JLabel streetLabel = new JLabel("Street Address:");
        streetField = new JTextField(addressParts[1]);
        
        JLabel barangayLabel = new JLabel("Barangay:");
        barangayField = new JTextField(addressParts[2]);
        
        JLabel cityLabel = new JLabel("City/Municipality:");
        cityField = new JTextField(addressParts[3]);
        
        JLabel provinceLabel = new JLabel("Province:");
        provinceField = new JTextField(addressParts[4]);
        
        JLabel regionLabel = new JLabel("Region:");
        regionField = new JTextField(addressParts[5]);
        
        formPanel.add(houseNumberLabel);
        formPanel.add(houseNumberField);
        formPanel.add(streetLabel);
        formPanel.add(streetField);
        formPanel.add(barangayLabel);
        formPanel.add(barangayField);
        formPanel.add(cityLabel);
        formPanel.add(cityField);
        formPanel.add(provinceLabel);
        formPanel.add(provinceField);
        formPanel.add(regionLabel);
        formPanel.add(regionField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveBtn = new JButton("Save Address");
        cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> saveAddress());
        cancelBtn.addActionListener(e -> dispose());
        
        buttonPanel.add(cancelBtn);
        buttonPanel.add(saveBtn);
        
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Parse address string into components
     * Handles cases where the address format might be inconsistent
     */
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
    
    /**
     * Save the address both to the UI and to the database if userId is provided
     */
    private void saveAddress() {
        String newAddress = houseNumberField.getText().trim() + ", " 
                          + streetField.getText().trim() + ", " 
                          + barangayField.getText().trim() + ", " 
                          + cityField.getText().trim() + ", " 
                          + provinceField.getText().trim() + ", "
                          + regionField.getText().trim();
        
        String houserNumber = houseNumberField.getText().trim();
        String street = streetField.getText().trim();
        String barangay = barangayField.getText().trim();
        String municipality = cityField.getText().trim();
        String province = provinceField.getText().trim();
        String region = regionField.getText().trim();
        
        // If we have a valid user ID, update in database
        if (userId > 0) {
            updateAddressInDatabase(userId, houserNumber, street, barangay, municipality, province, region);
        }
        
        // Notify the listener about the address change
        if (listener != null) {
            listener.onAddressChanged(newAddress);
        }
        
        dispose();
    }
    
    /**
     * Updates the address in the database for the specified user
     */
private void updateAddressInDatabase(int userId, String houseNumber, String street, String barangay, String municipality, String province, String region) {
    try (Connection conn = MSSQLConnection.getConnection()) {
        String query = "UPDATE ui SET ui.House_number = ?, "
                     + "ui.Street = ?, "
                     + "ui.Barangay = ?, "
                     + "ui.Municipality = ?, "
                     + "ui.Province = ?, "
                     + "ui.Region = ? "
                     + "FROM USER_INFO ui "
                     + "JOIN [USER_] u ON u.UserInfo_ID = ui.UserInfo_ID "
                     + "WHERE u.UserID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, houseNumber);
            stmt.setString(2, street);
            stmt.setString(3, barangay);
            stmt.setString(4, municipality);
            stmt.setString(5, province);
            stmt.setString(6, region);
            stmt.setInt(7, userId);

            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected <= 0) {
                JOptionPane.showMessageDialog(null, 
                    "Failed to update address in database.", 
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, 
                    "Update Successfull", 
                    "Change Address Updated", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, 
            "Database error: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    
    /**
     * Static helper method to show the dialog and get result
     */
    public static void showDialog(JFrame parent, int userId, String currentAddress, AddressChangeListener listener) {
        ChangeAddressFrame dialog = new ChangeAddressFrame(parent, userId, currentAddress, listener);
        dialog.setVisible(true);
    }
    
    /**
     * Static helper method without userId
     */
    public static void showDialog(JFrame parent, String currentAddress, AddressChangeListener listener) {
        ChangeAddressFrame dialog = new ChangeAddressFrame(parent, currentAddress, listener);
        dialog.setVisible(true);
    }
}