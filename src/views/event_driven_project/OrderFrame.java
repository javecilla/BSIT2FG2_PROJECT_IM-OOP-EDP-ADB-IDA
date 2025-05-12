package views.event_driven_project;

import com.sun.jdi.connect.spi.Connection;
import config.MSSQLConnection;
import helpers.Response;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import models.CartItem;
import static views.event_driven_project.CartFrame.CART_ITEM_CONTROLLER;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class OrderFrame extends JFrame implements ActionListener {
    private int ItemId;
    private EventController controller;
    public OrderFrame(int id, EventController controller) {
        this.ItemId = id;
        this.controller = controller;
        orderFrameConfig();
    }

    // Image Icons
    ImageIcon logo = new ImageIcon(getClass().getResource("/views/Images/logo.png"));
    ImageIcon confirmIcon = new ImageIcon(getClass().getResource("/views/Images/Confirm.png"));
    
    JLabel countLabel = new JLabel("Enter Amount: ");
    
    // Spinner
    JSpinner itemCountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
    
    //buttons
    JButton confirmButton = new JButton();

    public void orderFrameConfig() {
        // Create a panel with white background
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Customize spinner appearance
        itemCountSpinner.setPreferredSize(new Dimension(150, 50));
        itemCountSpinner.setFont(new Font("Arial", Font.BOLD, 20));
        itemCountSpinner.setForeground(Color.WHITE);

        // Set blue-violet color
        Color blueViolet = new Color(115, 20, 226);
        
        countLabel.setFont(new Font("Arial", Font.BOLD, 25));
        countLabel.setForeground(blueViolet);

        // Customize spinner editor
        JComponent editor = itemCountSpinner.getEditor();
        editor.setBackground(blueViolet);
        editor.setForeground(Color.WHITE);

        if (editor instanceof JSpinner.DefaultEditor) {
            JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) editor;
            JFormattedTextField textField = spinnerEditor.getTextField();

            // Set background and foreground color
            textField.setBackground(blueViolet);
            textField.setForeground(Color.WHITE);
            textField.setCaretColor(Color.WHITE);

            // ✅ Center-align the text
            textField.setHorizontalAlignment(JTextField.CENTER);
        }
        
        setupButton(confirmButton, confirmIcon);

        // Add to panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(countLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(itemCountSpinner, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(confirmButton, gbc);

        setContentPane(mainPanel);
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

@Override
public void actionPerformed(ActionEvent e) {
    java.sql.Connection conn = null;
    PreparedStatement pstmt = null;
    if(e.getSource() == confirmButton) {
        try {
            int cartId = controller.getCartID();
            
            // If user doesn't have a cart yet, create one
        if(cartId == -1) {
            conn = MSSQLConnection.getConnection();
            if(conn != null) {
                try {
                    // First check if a cart already exists for this user
                    String checkCartSQL = "SELECT Cart_ID FROM CART WHERE UserID = ?";
                    pstmt = conn.prepareStatement(checkCartSQL);
                    pstmt.setInt(1, controller.getUser().getUserId());

                    ResultSet rs = pstmt.executeQuery();

                    // If cart exists, use that existing cart ID
                    if (rs.next()) {
                        cartId = rs.getInt("Cart_ID");
                        controller.setCartID(cartId);
                    } else {
                        // No existing cart found, create a new one
                        pstmt.close();

                        // Create a new cart for the user
                        String insertCartSQL = "INSERT INTO CART (UserID) VALUES (?)";
                        pstmt = conn.prepareStatement(insertCartSQL, Statement.RETURN_GENERATED_KEYS);
                        pstmt.setInt(1, controller.getUser().getUserId());

                        int affectedRows = pstmt.executeUpdate();

                        if (affectedRows > 0) {
                            // Get the newly generated cart ID
                            ResultSet generatedKeys = pstmt.getGeneratedKeys();
                            if (generatedKeys.next()) {
                                cartId = generatedKeys.getInt(1);
                                // Update the controller with the new cart ID
                                controller.setCartID(cartId);
                            }
                        }
                    }

                    pstmt.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
            
            // Now add the item to the cart
            Response<CartItem> response = CART_ITEM_CONTROLLER.addItemToCart(controller.getCartID(), ItemId, (Integer)itemCountSpinner.getValue());
            
            if (response.isSuccess()) {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Successfull", JOptionPane.INFORMATION_MESSAGE);     
                controller.menuFrame.setCartItemCount();
                controller.drinksFrame.setCartItemCount();
                controller.friesFrame.setCartItemCount();
                controller.riceMealsFrame.setCartItemCount();
                controller.sandwichesFrame.setCartItemCount();
                
            } else {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            this.dispose();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
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
