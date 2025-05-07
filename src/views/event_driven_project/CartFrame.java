package views.event_driven_project;

import controllers.CartItemController;
import core.Session;
import helpers.Response;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
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
    protected static final CartItemController CART_ITEM_CONTROLLER = new CartItemController();

    public CartFrame(EventController eventController) {
        this.controller = eventController;
        cartFrameConfig();
    }
    String[] columns = {"Product", "Quantity", "Price", "Action"};
    Object[][] data;
    public int cartId;
    
    //icons
    ImageIcon shopCartIcon = new ImageIcon(getClass().getResource("/views/Images/cart.png"));
    ImageIcon checkOutIcon = new ImageIcon(getClass().getResource("/views/Images/check-out.png"));
    
    //lables
    JLabel background = new JLabel(shopCartIcon);

    //Buttons
    JButton checkOutButton = new JButton();
    
    public void cartFrameConfig() {
        System.out.println("Initializing CartFrame...");
        
        // Set up background and frame
        background.setLayout(null);
        this.setContentPane(background);
        setTitle("Your Cart");
        
        // First get the logged in user
        User user = Session.getLoggedInUser();
        if (user == null) {
            System.out.println("Not logged in.");
            JOptionPane.showMessageDialog(this, 
                "You must be logged in to view your cart.", 
                "Authentication Error", 
                JOptionPane.ERROR_MESSAGE);
            this.dispose();
            return;
        } 
        System.out.println("User: " + user.getUserId());
        
        // Then get the cart for this user
        Response<Cart> cartResponse = CART_ITEM_CONTROLLER.getCartByUserId(user.getUserId());
        if (cartResponse.isSuccess()) {
            cartId = cartResponse.getData().getCartId();
            System.out.println("Successfully retrieved cart with ID: " + cartId);
        } else {
            System.out.println("Error retrieving cart: " + cartResponse.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Could not retrieve your cart: " + cartResponse.getMessage(), 
                "Cart Error", 
                JOptionPane.ERROR_MESSAGE);
            this.dispose();
            return;
        }
        
        // Load cart items and set up the table
        loadCartItems();

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
        int width = (int) (shopCartIcon.getIconWidth() * 0.9);
        int height = shopCartIcon.getIconHeight() - 250;
        int x = ((shopCartIcon.getIconWidth() - width) / 2) - 5;
        int y = (int) (shopCartIcon.getIconHeight() * 0.2);
        
        totalPanel.setBounds(x, y + height + 10, width, 30);
        background.add(totalPanel);
        
        // Calculate and update the total
        updateTotalDisplay();

        // Add checkout button at the bottom
        setupButton(checkOutButton, checkOutIcon);
        checkOutButton.addActionListener(this); // Make sure action listener is added
        
        int buttonWidth = checkOutIcon.getIconWidth();
        int buttonHeight = checkOutIcon.getIconHeight();
        int buttonX = (shopCartIcon.getIconWidth() - buttonWidth) / 2;
        int buttonY = shopCartIcon.getIconHeight() - buttonHeight - 50; // Position at bottom
        
        checkOutButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        background.add(checkOutButton);

        // Frame configuration
        this.setSize(shopCartIcon.getIconWidth(), shopCartIcon.getIconHeight() + 10);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        // Make sure all components are properly validated
        this.validate();
        this.repaint();
        
        // Finally make the frame visible
        this.setVisible(true);
        
        System.out.println("CartFrame initialized and visible");
    }
    
    private void loadCartItems() {
        // Debug message to verify method is called
        System.out.println("Loading cart items for cart ID: " + cartId);
        
        Response<java.util.List<CartItem>> itemsCartResponse = CART_ITEM_CONTROLLER.getAllItemsInCart(cartId);
        if (itemsCartResponse.isSuccess()) {
            java.util.List<CartItem> items = itemsCartResponse.getData();
            java.util.List<Object[]> dataList = new java.util.ArrayList<>();
            rowToCartItemMap.clear(); // Clear the map to avoid stale data
            
            System.out.println("Retrieved " + (items != null ? items.size() : 0) + " items");
            
            if (items != null && !items.isEmpty()) {
                int rowIndex = 0;
                for (CartItem item : items) {
                    if (item != null && item.getFood() != null) {
                        // Debug output for each item
                        System.out.println("Adding item to table - Cart Item ID: " + item.getCartItemId() +
                                           ", Food: " + item.getFood().getFoodName() +
                                           ", Quantity: " + item.getCartItemQuantity() +
                                           ", Price: " + item.getFood().getPrice());
                        
                        // Add row to the list, including Action column
                        dataList.add(new Object[] {
                            item.getFood().getFoodName(),           // Product
                            item.getCartItemQuantity(),             // Quantity
                            "Php" + String.format("%.2f", item.getFood().getPrice()), // Price
                            ""                                      // Placeholder for Action column
                        });
                        
                        // Map the CartItem object to the row index for easy access
                        rowToCartItemMap.put(rowIndex, item);
                        rowIndex++;
                    } else {
                        System.out.println("Warning: Skipping null item or item with null food");
                    }
                }
                
                // Convert the list to a 2D array
                data = dataList.toArray(new Object[dataList.size()][4]);
                System.out.println("Created data array with " + data.length + " rows");
            } else {
                System.out.println("No items in cart.");
                data = new Object[0][4]; // Empty table with 4 columns
            }
        } else {
            System.out.println("Error retrieving cart items: " + itemsCartResponse.getMessage());
            data = new Object[0][4]; // Empty table with 4 columns
        }

        // Initialize the table model with the data
        tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only Action column is editable
            }
            
            // Ensure column types are preserved
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) return Integer.class; // Quantity is numeric
                return String.class; // Others are strings
            }
        };
        
        System.out.println("Created table model with " + tableModel.getRowCount() + " rows");

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
        columnModel.getColumn(3).setPreferredWidth(100);  // Action column

        // Center alignment for text columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setOpaque(true); // Make sure the renderer is opaque

        for (int i = 0; i < cartTable.getColumnCount(); i++) {
            if (i != 3) { // Apply center only to non-button columns
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
        cartTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        cartTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox()));

        // Create scroll pane with semi-transparent background
        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scrollPane.getViewport().setOpaque(true);
        scrollPane.getViewport().setBackground(new Color(255, 255, 255, 180));
        scrollPane.setOpaque(true);
        scrollPane.setBackground(new Color(255, 255, 255, 150));

        // Calculate dimensions based on the icon size
        int width = (int) (shopCartIcon.getIconWidth() * 0.9);
        int height = shopCartIcon.getIconHeight() - 250;
        int x = ((shopCartIcon.getIconWidth() - width) / 2) - 5;
        int y = (int) (shopCartIcon.getIconHeight() * 0.2);

        scrollPane.setBounds(x, y, width, height);
        background.add(scrollPane);
        
        // Debug message
        System.out.println("Added table to scrollpane, dimensions: " + width + "x" + height);
        
        // Validate and repaint to ensure proper display
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == checkOutButton){
            controller.showPaymentFrame(this);
        }
    }
    
    // Calculate and display the total price of all items in the cart
    private void updateTotalDisplay() {
        double total = 0.0;
        
        // Iterate through all rows in the table
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            CartItem item = rowToCartItemMap.get(i);
            if (item != null) {
                total += item.getCartItemQuantity() * item.getFood().getPrice();
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

    // Allows clicking the + and - buttons, also with transparent background
    class ButtonEditor extends DefaultCellEditor {
        protected JPanel panel = new JPanel();
        protected JButton plus = new JButton("+");
        protected JButton minus = new JButton("-");

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

            plus.addActionListener(e -> {
                updateQuantity(lastRow, 1);
                fireEditingStopped();
            });
            
            minus.addActionListener(e -> {
                updateQuantity(lastRow, -1);
                fireEditingStopped();
            });
        }

        private int lastRow;

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
          boolean isSelected, int row, int column) {
            lastRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }

        private void updateQuantity(int row, int change) {
            // Debug message
            System.out.println("Updating quantity for row " + row + " with change: " + change);
            
            // Get the CartItem object for this row
            CartItem item = rowToCartItemMap.get(row);
            if (item == null) {
                System.out.println("Error: CartItem not found for row " + row);
                return;
            }
            
            System.out.println("Found item with ID: " + item.getCartItemId() + 
                              ", current quantity: " + item.getCartItemQuantity());
            
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
                    System.out.println("Removing item with ID: " + item.getCartItemId());
                    
                    // Remove from the database
                    Response<?> response = CART_ITEM_CONTROLLER.removeItemFromCart(item.getCartItemId());
                    if (response.isSuccess()) {
                        System.out.println("Successfully removed item from database");
                        
                        // Remove from the table
                        tableModel.removeRow(row);
                        
                        // Create a new map to rebuild correct row mappings
                        HashMap<Integer, CartItem> newMap = new HashMap<>();
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            CartItem currentItem = null;
                            
                            // Find the item that should be at this position
                            if (i < row) {
                                // Items before the deleted row keep their position
                                currentItem = rowToCartItemMap.get(i);
                            } else {
                                // Items after the deleted row move up by one
                                currentItem = rowToCartItemMap.get(i + 1);
                            }
                            
                            if (currentItem != null) {
                                newMap.put(i, currentItem);
                                System.out.println("Remapped item " + currentItem.getCartItemId() + " to row " + i);
                            }
                        }
                        
                        // Replace the old map with the new one
                        rowToCartItemMap = newMap;
                        
                        // Update the total
                        updateTotalDisplay();
                        
                        // Refresh the table
                        cartTable.revalidate();
                        cartTable.repaint();
                    } else {
                        System.out.println("Failed to remove item: " + response.getMessage());
                        JOptionPane.showMessageDialog(
                            CartFrame.this,
                            "Failed to remove item: " + response.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            } else {
                System.out.println("Updating quantity to " + newQty + " for item " + item.getCartItemId());
                
                // Update the quantity in the database
                // Pass the change value directly to the controller: +1 for increase, -1 for decrease
                Response<?> response = CART_ITEM_CONTROLLER.updateCartItemQuantity(item.getCartItemId(), change);
                
                if (response.isSuccess()) {
                    System.out.println("Successfully updated quantity in database");
                    
                    // Update the quantity in the item object
                    item.setCartItemQuantity(newQty);
                    
                    // Update the quantity in the table
                    tableModel.setValueAt(newQty, row, 1);
                    System.out.println("Updated table display with new quantity: " + newQty);
                    
                    // Update the total
                    updateTotalDisplay();
                    
                    // Refresh the table
                    cartTable.revalidate();
                    cartTable.repaint();
                } else {
                    System.out.println("Failed to update quantity: " + response.getMessage());
                    JOptionPane.showMessageDialog(
                        CartFrame.this,
                        "Failed to update quantity: " + response.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
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
}