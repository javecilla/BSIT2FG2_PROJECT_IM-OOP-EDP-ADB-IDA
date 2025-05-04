package views.event_driven_project;

import config.MSSQLConnection;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Reusable component for changing a user's personal information
 * Can be used across multiple parts of the application
 */
public class ChangeInfoFrame extends JDialog {
    
    // Interface for callback when information is changed
    public interface InfoChangeListener {
        void onNameChanged(String firstName, String lastName);
        void onPhoneChanged(String newPhone);
        void onUsernameChanged(String newUsername);
        void onPasswordChanged(String newPassword);
        // Add more callbacks as needed
    }
    
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField phoneField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton saveBtn;
    private JButton cancelBtn;
    
    private InfoChangeListener listener;
    private int userId; // To identify which user's info to update
    
    /**
     * Constructor for creating the info change dialog
     * 
     * @param parent The parent frame
     * @param userId The ID of the user whose info is being changed
     * @param currentFirstName Current first name to pre-populate field
     * @param currentLastName Current last name to pre-populate field
     * @param currentPhone Current phone to pre-populate field
     * @param currentUsername Current username to pre-populate field
     * @param listener Callback listener for when info is changed
     */
    public ChangeInfoFrame(JFrame parent, int userId, String currentFirstName, String currentLastName,
                           String currentPhone, String currentUsername, InfoChangeListener listener) {
        super(parent, "Change Personal Information", true);
        this.listener = listener;
        this.userId = userId;
        
        initComponents(currentFirstName, currentLastName, currentPhone, currentUsername, "");
    }
    
    /**
     * Alternative constructor without userId for UI-only changes
     */
    public ChangeInfoFrame(JFrame parent, String currentFirstName, String currentLastName, 
                          String currentPhone, String currentUsername, String currentPassword, InfoChangeListener listener) {
        super(parent, "Change Personal Information", true);
        this.listener = listener;
        this.userId = -1; // No specific user ID
        
        initComponents(currentFirstName, currentLastName, currentPhone, currentUsername, currentPassword);
    }
    
    private void initComponents(String currentFirstName, String currentLastName, String currentPhone, 
                               String currentUsername, String currentPassword) {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 10)); // 5 rows for all fields
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField(currentFirstName);
        
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField(currentLastName);
        
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneField = new JTextField(currentPhone);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(currentUsername);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(currentPassword);
        
        formPanel.add(firstNameLabel);
        formPanel.add(firstNameField);
        formPanel.add(lastNameLabel);
        formPanel.add(lastNameField);
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveBtn = new JButton("Save Information");
        cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> saveInformation());
        cancelBtn.addActionListener(e -> dispose());
        
        buttonPanel.add(cancelBtn);
        buttonPanel.add(saveBtn);
        
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Save the information both to the UI and to the database if userId is provided
     */
    private void saveInformation() {
        String newFirstName = firstNameField.getText().trim();
        String newLastName = lastNameField.getText().trim();
        String newPhone = phoneField.getText().trim();
        String newUsername = usernameField.getText().trim();
        String newPassword = new String(passwordField.getPassword());
        
        // Basic validation
        if (newFirstName.isEmpty() || newLastName.isEmpty() || newPhone.isEmpty() || newUsername.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "All fields must be filled out except password (only if changing).", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if(!newPhone.matches("^09\\d{9}$")){
            JOptionPane.showMessageDialog(this, 
                "Invalid Phone Number, Please enter a valid one", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // If we have a valid user ID, update in database
        if (userId > 0) {
            updateInfoInDatabase(newFirstName, newLastName, newPhone, newUsername, newPassword);
        }
        
        // Notify the listener about the information changes
        if (listener != null) {
            listener.onNameChanged(newFirstName, newLastName);
            listener.onPhoneChanged(newPhone);
            listener.onUsernameChanged(newUsername);
            if (!newPassword.isEmpty()) {
                listener.onPasswordChanged(newPassword);
            }
        }
        
        dispose();
    }
    
    /**
     * Updates the user information in the database
     */
    private void updateInfoInDatabase(String newFirstName, String newLastName, String newPhone, 
                                     String newUsername, String newPassword) {
        try (Connection conn = MSSQLConnection.getConnection()) {
            String query;
            PreparedStatement stmt;
            
        if (!newPassword.isEmpty()) {
            // Update USER_
            String query1 = "UPDATE USER_ SET Username = ?, User_Password = ?, User_Contact = ? WHERE UserID = ?";
            stmt = conn.prepareStatement(query1);
            stmt.setString(1, newUsername);
            stmt.setString(2, newPassword); // hash this in production
            stmt.setString(3, newPhone); // assuming this maps to User_Contact
            stmt.setInt(4, userId);
            stmt.executeUpdate();
        } else {
            // Update USER_ without password
            String query1 = "UPDATE USER_ SET Username = ?, User_Contact = ? WHERE UserID = ?";
            stmt = conn.prepareStatement(query1);
            stmt.setString(1, newUsername);
            stmt.setString(2, newPhone); // assuming this maps to User_Contact
            stmt.setInt(3, userId);
            stmt.executeUpdate();
        }

        // Update USER_INFO (always do this part)
        String query2 = "UPDATE USER_INFO SET First_Name = ?, Last_Name = ? " +
                        "WHERE UserInfo_ID = (SELECT UserInfo_ID FROM USER_ WHERE UserID = ?)";
        stmt = conn.prepareStatement(query2);
        stmt.setString(1, newFirstName);
        stmt.setString(2, newLastName);
        stmt.setInt(3, userId);
        stmt.executeUpdate();
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "Failed to update information in database.", 
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, 
                    "Change Information Successfull", 
                    "Information Updated", 
                    JOptionPane.INFORMATION_MESSAGE);                
            }
            
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Database error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Static helper method to show the dialog with userId
     */
    public static void showDialog(JFrame parent, int userId, String currentFirstName, String currentLastName,
                                  String currentPhone, String currentUsername, InfoChangeListener listener) {
        ChangeInfoFrame dialog = new ChangeInfoFrame(parent, userId, currentFirstName, currentLastName,
                                                     currentPhone, currentUsername, listener);
        dialog.setVisible(true);
    }
    
    /**
     * Static helper method without userId
     */
    public static void showDialog(JFrame parent, String currentFirstName, String currentLastName, 
                                  String currentPhone, String currentUsername, String currentPassword,
                                  InfoChangeListener listener) {
        ChangeInfoFrame dialog = new ChangeInfoFrame(parent, currentFirstName, currentLastName, 
                                                    currentPhone, currentUsername, currentPassword, listener);
        dialog.setVisible(true);
    }
    
    /**
     * Static utility method to fetch user information from database
     * Returns a String array with [firstName, lastName, phone, username]
     */
    public static String[] getUserInfo(int userId) {
        String[] userInfo = new String[4];
        
        try (Connection conn = MSSQLConnection.getConnection()) {
            String query = "SELECT FirstName, LastName, Phone, Username FROM USERS WHERE User_ID = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, userId);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        userInfo[0] = rs.getString("FirstName");
                        userInfo[1] = rs.getString("LastName");
                        userInfo[2] = rs.getString("Phone");
                        userInfo[3] = rs.getString("Username");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return userInfo;
    }
    
    /**
     * Static utility method to fetch user address from database
     */
}