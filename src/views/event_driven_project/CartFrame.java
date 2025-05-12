package views.event_driven_project;

import config.MSSQLConnection;
import controllers.CartItemController;
import core.Session;
import helpers.Response;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import models.Cart;
import models.CartItem;
import models.User;

public class CartFrame extends JFrame implements ActionListener {
    private EventController controller;
    private HashMap<Integer, CartItem> rowToCartItemMap = new HashMap<>(); // Map row index to CartItem object
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private int cartItemCount = 0;
    protected static final CartItemController CART_ITEM_CONTROLLER = new CartItemController();

    // Database connection parameters - adjust these to match your configuration
    private static final String DB_URL = "jdbc:mssql://localhost:3306/your_database_name";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    // icons
    ImageIcon shopCartIcon = new ImageIcon(getClass().getResource("/views/Images/cart.png"));
    ImageIcon checkOutIcon = new ImageIcon(getClass().getResource("/views/Images/check-out.png"));
    
    // labels
    JLabel background = new JLabel(shopCartIcon);

    // Buttons
    JButton checkOutButton = new JButton();
    
    // Table columns
    String[] columns = {"Product", "Quantity", "Price", "Subtotal", "Action"};
    Object[][] data;
    public int cartId = -1; // Default to invalid cart ID

    public CartFrame(EventController controller) {
        this.controller = controller;
        initialFrameSetup();
    }
    
    /**
     * Sets up the initial frame UI without loading data
     */
    public void initialFrameSetup() {
        
        // Set up background and frame
        background.setLayout(null);
        this.setContentPane(background);
        setTitle("Your Cart");
        
        // Initialize the table with empty data
        data = new Object[0][5]; // Empty table with 5 columns
        setupTableAndUI();
        
        // Frame configuration
        this.setSize(shopCartIcon.getIconWidth(), shopCartIcon.getIconHeight() + 10);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        // Load cart data for current user
        loadCartItemsForCurrentUser();
        
        // Validate and make visible
        this.validate();
        this.repaint();
        this.setVisible(false);
    }
    
    /**
     * Sets up the table and other UI components
     */
    private void setupTableAndUI() {
        // Initialize the table model with empty data
        tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only Action column is editable
            }
            
            // Ensure column types are preserved
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) return Integer.class; // Quantity is numeric
                return String.class; // Others are strings
            }
        };
        
        // Create the table
        cartTable = new JTable(tableModel);
        cartTable.setRowHeight(40);
        cartTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cartTable.setForeground(new Color(95, 71, 214));
        
        // Set these properties to make the table content visible
        cartTable.setOpaque(true);
        cartTable.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent white
        cartTable.setGridColor(new Color(240, 240, 240));
        cartTable.setShowGrid(true);
        cartTable.setShowHorizontalLines(true);
        cartTable.setShowVerticalLines(false);
        cartTable.setIntercellSpacing(new Dimension(5, 5));
        cartTable.setFillsViewportHeight(true); // Important to fill the viewport

        // Set column widths for better proportions
        TableColumnModel columnModel = cartTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150);  // Product column wider
        columnModel.getColumn(1).setPreferredWidth(80);   // Quantity column
        columnModel.getColumn(2).setPreferredWidth(80);   // Price column
        columnModel.getColumn(3).setPreferredWidth(100);  // Subtotal column
        columnModel.getColumn(4).setPreferredWidth(100);  // Action column

        // Center alignment for text columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setOpaque(true); // Make sure the renderer is opaque

        for (int i = 0; i < cartTable.getColumnCount(); i++) {
            if (i != 4) { // Apply center only to non-button columns
                cartTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        // Custom header styling
        JTableHeader header = cartTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setForeground(new Color(95, 71, 214));
        header.setBackground(new Color(245, 245, 245));
        header.setOpaque(true);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(95, 71, 214)));

        // Set renderer and editor for Action column
        cartTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        cartTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));

        // Create scroll pane with semi-transparent background
        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scrollPane.getViewport().setOpaque(true);
        scrollPane.getViewport().setBackground(new Color(255, 255, 255, 180));
        scrollPane.setOpaque(true);
        scrollPane.setBackground(new Color(255, 255, 255, 150));
        scrollPane.setVisible(true);

        // Calculate dimensions based on the icon size
        int width = (int) (shopCartIcon.getIconWidth() * 0.9);
        int height = shopCartIcon.getIconHeight() - 250;
        int x = ((shopCartIcon.getIconWidth() - width) / 2) - 5;
        int y = (int) (shopCartIcon.getIconHeight() * 0.2);

        scrollPane.setBounds(x, y, width, height - 30);
        background.add(scrollPane);
        
        // Create total panel at the bottom of the table
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setOpaque(true);
        totalPanel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent white
        
        totalLabel = new JLabel("Total: Php0.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalLabel.setForeground(new Color(95, 71, 214));
        totalPanel.add(totalLabel);
        
        // Position the total panel
        totalPanel.setBounds(x, y + height - 20, width, 30);
        background.add(totalPanel);
        
        // Add checkout button at the bottom
        setupButton(checkOutButton, checkOutIcon);
        
        int buttonWidth = checkOutIcon.getIconWidth();
        int buttonHeight = checkOutIcon.getIconHeight();
        int buttonX = (shopCartIcon.getIconWidth() - buttonWidth) / 2;
        int buttonY = shopCartIcon.getIconHeight() - buttonHeight - 50; // Position at bottom
        
        checkOutButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        background.add(checkOutButton);
    }

    /**
     * Loads cart items specifically for the current user from the database
     */
/**
 * Loads cart items specifically for the current user from the database
 * This improved version has better error handling and thread safety
 */
public void loadCartItemsForCurrentUser() {
    // Cancel any active cell editing to prevent exceptions
    if (cartTable.isEditing()) {
        cartTable.getCellEditor().cancelCellEditing();
    }
    
    // Clear the existing table data
    tableModel.setRowCount(0); // Better approach than removing rows one by one
    rowToCartItemMap.clear();
    
    // Check if there's a logged-in user from Session
    User currentUser = controller.getUser();
    if (currentUser == null) {
        JOptionPane.showMessageDialog(this, 
            "Please log in to view your cart items.", 
            "Authentication Required", 
            JOptionPane.INFORMATION_MESSAGE);
        return;
    }
    
    int userId = currentUser.getUserId();
    
    // Modified SQL query to filter by the current user's ID
    String sql = "SELECT c.Cart_ID, ci.Food_ID, f.Food_Name, ci.Item_Quantity, " +
                 "(ci.Item_Quantity * f.Price) as SubTotal, f.Price, ci.CartItem_ID " +
                 "FROM USER_ u JOIN CART c " +
                 "ON u.UserID = c.UserID " +
                 "JOIN CART_ITEM ci " +
                 "ON c.Cart_ID = ci.Cart_ID " +
                 "JOIN FOOD f " +
                 "ON ci.Food_ID = f.Food_ID " +
                 "WHERE u.UserID = ?";
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try {
        // Establish database connection
        conn = MSSQLConnection.getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, userId);  // Set the user ID parameter
        rs = pstmt.executeQuery();
        
        double total = 0.0;
        int rowIndex = 0;
        
        // Process the result set
        while (rs.next()) {
            int cartId = rs.getInt("Cart_ID");
            int foodId = rs.getInt("Food_ID");
            String foodName = rs.getString("Food_Name");
            int quantity = rs.getInt("Item_Quantity");
            double subtotal = rs.getDouble("SubTotal");
            double price = rs.getDouble("Price");
            int cartItemId = rs.getInt("CartItem_ID");
            
            // Track the cart ID
            this.cartId = cartId;
            controller.setCartID(cartId);
            
            // Add to table model
            tableModel.addRow(new Object[] {
                foodName,                       // Product
                quantity,                       // Quantity
                "Php" + String.format("%.2f", price),  // Price
                "Php" + String.format("%.2f", subtotal), // Subtotal
                ""                              // Placeholder for Action buttons
            });
            
            // Create CartItem object for this row
            CartItem item = new CartItem();
            item.setCartItemId(cartItemId);
            item.setCartItemQuantity(quantity);
            
            // Create a simple Food object to store the necessary info
            models.Food food = new models.Food();
            food.setFoodId(foodId);
            food.setFoodName(foodName);
            food.setPrice(price);
            item.setFood(food);
            
            // Map row to CartItem for easy access when updating
            rowToCartItemMap.put(rowIndex, item);
            rowIndex++;
            
            // Add to total
            total += subtotal;
        }
        
        cartItemCount = rowIndex;
        // Update the total display
        totalLabel.setText(String.format("Total: Php%.2f", total));
        
    } catch (SQLException e) {
        //System.err.println("Database error: " + e.getMessage());
        JOptionPane.showMessageDialog(this, 
            "Error loading cart items: " + e.getMessage(), 
            "Database Error", 
            JOptionPane.ERROR_MESSAGE);
    } finally {
        // Close database resources
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error closing database resources: " + e.getMessage());
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // More aggressive refresh of the table UI
    tableModel.fireTableDataChanged();
    cartTable.revalidate();
    cartTable.repaint();

    // Make sure the frame itself is revalidated
    this.revalidate();
    this.repaint();
}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == checkOutButton){
            // Check if items are in cart before proceeding
            if (tableModel.getRowCount() > 0) {
                Payment payment = new Payment(controller);
                payment.setVisible(true);
                this.dispose();
                //controller.showPaymentFrame(this);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Your cart is empty.", 
                    "Empty Cart", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    // Calculate and display the total price of all items in the cart
    private void updateTotalDisplay() {
        double total = 0.0;
        
        // Iterate through all rows in the table
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String subtotalStr = (String) tableModel.getValueAt(i, 3);
            if (subtotalStr != null && subtotalStr.startsWith("Php")) {
                try {
                    // Extract the numeric value from the string
                    double subtotal = Double.parseDouble(subtotalStr.substring(3));
                    total += subtotal;
                } catch (NumberFormatException e) {
                    //System.err.println("Error parsing subtotal: " + subtotalStr);
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        // Update the total label
        totalLabel.setText(String.format("Total: Php%.2f", total));
    }

    // Renders two buttons centered in a cell with transparent background
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private final JButton plus = new JButton("+");
        private final JButton minus = new JButton("-");

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            setOpaque(false); // Make the panel transparent
            
            // Style the buttons
            for (JButton btn : new JButton[]{plus, minus}) {
                btn.setFocusable(true);
                btn.setContentAreaFilled(true);
                btn.setBorderPainted(true);
                btn.setOpaque(true);
                btn.setFont(new Font("Poppins", Font.BOLD, 16));
                btn.setBackground(new Color(245, 245, 245));
                btn.setForeground(new Color(95, 71, 214));
                btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(95, 71, 214), 1, true),
                    BorderFactory.createEmptyBorder(2, 8, 2, 8)
                ));
            }
            
            add(minus);
            add(plus);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

// Complete replacement for the ButtonEditor class to fix the multiple deletion issue

class ButtonEditor extends DefaultCellEditor {
    protected JPanel panel = new JPanel();
    protected JButton plus = new JButton("+");
    protected JButton minus = new JButton("-");
    private int lastRow;
    private boolean isDeleting = false;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel.setOpaque(false); // Make panel transparent

        // Style the buttons
        for (JButton btn : new JButton[]{plus, minus}) {
            btn.setFocusable(false);
            btn.setContentAreaFilled(true);
            btn.setBorderPainted(true);
            btn.setOpaque(true);
            btn.setFont(new Font("Poppins", Font.BOLD, 16));
            btn.setBackground(new Color(245, 245, 245));
            btn.setForeground(new Color(95, 71, 214));
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(95, 71, 214), 1, true),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
            ));
        }

        panel.add(minus);
        panel.add(plus);

        // Modify action listeners to use SwingUtilities.invokeLater for better thread safety
        plus.addActionListener(e -> {
            if (!isDeleting) {
                final int row = lastRow;
                SwingUtilities.invokeLater(() -> {
                    updateQuantity(row, 1);
                    fireEditingStopped();
                });
            }
        });
        
        minus.addActionListener(e -> {
            if (!isDeleting) {
                final int row = lastRow;
                SwingUtilities.invokeLater(() -> {
                    updateQuantity(row, -1);
                    fireEditingStopped();
                });
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {
        lastRow = row;
        isDeleting = false;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }

    private void updateQuantity(int row, int change) {
        // Safety check - make sure row is valid
        if (row < 0 || row >= tableModel.getRowCount()) {
            //System.out.println("Invalid row index: " + row);
            JOptionPane.showMessageDialog(null, "Invalid row index: " + row, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get the CartItem object for this row
        CartItem item = rowToCartItemMap.get(row);
        if (item == null) {
            //System.out.println("Error: CartItem not found for row " + row);
            JOptionPane.showMessageDialog(null, "Error: CartItem not found for row " + row, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int currentQty = item.getCartItemQuantity();
        int newQty = currentQty + change;
        
        if (newQty <= 0) {
            // If quantity would go to 0 or less, remove the item
            int confirm = JOptionPane.showConfirmDialog(
                CartFrame.this,
                "Remove " + item.getFood().getFoodName() + " from cart?",
                "Remove Item",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                isDeleting = true; // Set flag to prevent concurrent actions
                
                // Execute SQL to remove the item
                Connection conn = null;
                PreparedStatement pstmt = null;
                
                try {
                    conn = MSSQLConnection.getConnection();
                    String sql = "DELETE FROM CART_ITEM WHERE CartItem_ID = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, item.getCartItemId());
                    
                    int affected = pstmt.executeUpdate();
                    if (affected > 0) {
                        
                        // Cancel any active cell editing before reloading
                        if (cartTable.isEditing()) {
                            cartTable.getCellEditor().cancelCellEditing();
                        }
                        
                        // Use SwingUtilities.invokeLater to ensure UI updates happen on EDT
                        SwingUtilities.invokeLater(() -> {
                            // Reload the entire cart content to ensure proper UI refresh
                            loadCartItemsForCurrentUser();
                            
                            // Update the cart item count in the menu frame
                            controller.menuFrame.setCartItemCount();
                            controller.drinksFrame.setCartItemCount();
                            controller.friesFrame.setCartItemCount();
                            controller.riceMealsFrame.setCartItemCount();
                            controller.sandwichesFrame.setCartItemCount();
                            
                            isDeleting = false; // Reset deletion flag
                        });
                    } else {
                        //System.out.println("Failed to remove item: No rows affected");
                        JOptionPane.showMessageDialog(
                            CartFrame.this,
                            "Failed to remove item from database",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                        isDeleting = false; // Reset deletion flag
                    }
                } catch (SQLException ex) {
                    //System.err.println("Database error: " + ex.getMessage());
                    JOptionPane.showMessageDialog(
                        CartFrame.this,
                        "Database error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    isDeleting = false; // Reset deletion flag
                } finally {
                    try {
                        if (pstmt != null) pstmt.close();
                        if (conn != null) conn.close();
                    } catch (SQLException ex) {
                        //System.err.println("Error closing resources: " + ex.getMessage());
                        JOptionPane.showMessageDialog(null, "Error closing resources: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            // Update quantity code remains mostly the same
            //System.out.println("Updating quantity to " + newQty + " for item " + item.getCartItemId());
            
            // Update the quantity in the database
            Connection conn = null;
            PreparedStatement pstmt = null;
            
            try {
                conn = MSSQLConnection.getConnection();
                String sql = "UPDATE CART_ITEM SET Item_Quantity = ? WHERE CartItem_ID = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, newQty);
                pstmt.setInt(2, item.getCartItemId());
                
                int affected = pstmt.executeUpdate();
                if (affected > 0) {
                    //System.out.println("Successfully updated quantity in database");
                    
                    // Update the quantity in the item object
                    item.setCartItemQuantity(newQty);
                    
                    // Use SwingUtilities.invokeLater to ensure UI updates happen on EDT
                    SwingUtilities.invokeLater(() -> {
                        // Safety check before updating UI
                        if (row < tableModel.getRowCount()) {
                            // Update the quantity in the table
                            tableModel.setValueAt(newQty, row, 1);
                            
                            // Calculate and update the subtotal
                            double price = item.getFood().getPrice();
                            double subtotal = price * newQty;
                            tableModel.setValueAt("Php" + String.format("%.2f", subtotal), row, 3);
                            
                            //System.out.println("Updated table display with new quantity: " + newQty + 
                                              //" and subtotal: " + subtotal);
                            
                            // Update the total
                            updateTotalDisplay();
                            
                            // Refresh the table
                            cartTable.revalidate();
                            cartTable.repaint();
                        }
                    });
                } else {
                    //System.out.println("Failed to update quantity: No rows affected");
                    JOptionPane.showMessageDialog(
                        CartFrame.this,
                        "Failed to update quantity in database",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (SQLException ex) {
                //System.err.println("Database error: " + ex.getMessage());
                JOptionPane.showMessageDialog(
                    CartFrame.this,
                    "Database error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } finally {
                try {
                    if (pstmt != null) pstmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    //System.err.println("Error closing resources: " + ex.getMessage());
                    JOptionPane.showMessageDialog(
                    CartFrame.this,
                    "Error closing resources: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                }
            }
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
    
    public int getCartItemCount(){
        return cartItemCount;
    }
}