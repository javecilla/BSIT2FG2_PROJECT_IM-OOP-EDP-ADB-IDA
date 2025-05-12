package views.event_driven_project;

import config.MSSQLConnection;
import helpers.Response;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import models.CartItem;
import models.User;
import controllers.CartItemController;
import helpers.Response;
import java.util.List;
import models.CartItem;
import controllers.CartItemController;

public class Payment extends JFrame implements ActionListener, ChangeAddressFrame.AddressChangeListener {

    private EventController controller;
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> paymentComboBox;
    private JButton placeOrderBtn, changeAddressBtn;
    private JLabel nameLabel, addressLabel, totalLabel;
    private JPanel userInfoPanel;
    private String address;
    private int cartID;
    protected static final CartItemController CART_ITEM_CONTROLLER = new CartItemController();
    private double total = 0;

    private ImageIcon paymentIcon = new ImageIcon(getClass().getResource("/views/Images/payment option.png"));
    private ImageIcon changeAddressIcon = new ImageIcon(getClass().getResource("/views/Images/change-address.png"));

    public Payment(EventController controller) {
        this.controller = controller;
        paymentFrameConfig();
    }

    private void paymentFrameConfig() {
        setTitle("Place Order");
        setSize(paymentIcon.getIconWidth(), paymentIcon.getIconHeight() + 10);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Background panel with Image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(paymentIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        // Create a semi-transparent panel for user info (positioned on the right of the logo)
        userInfoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 180));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.dispose();
            }
        };
        userInfoPanel.setLayout(new GridLayout(2, 1));
        userInfoPanel.setOpaque(false);
        userInfoPanel.setBounds(getWidth() / 3, 10, getWidth() * 2 / 3 - 30, 50);
        
        // Name and address on the semi-transparent panel
        nameLabel = new JLabel(controller.getUser().getFirstName() + " " + controller.getUser().getLastName());
        addressLabel = new JLabel(getAddress(controller.getUser()));
        nameLabel.setFont(new Font("Poppins", Font.BOLD, 16));
        addressLabel.setFont(new Font("Poppins", Font.ITALIC, 13));
        
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(addressLabel);
        backgroundPanel.add(userInfoPanel);

        // Change Address button
        changeAddressBtn = new JButton("Change Address");
        changeAddressBtn.setBounds(getWidth() - 170, 60, changeAddressIcon.getIconWidth()-20, changeAddressIcon.getIconHeight()-25);
        changeAddressBtn.setBackground(new Color(95,71,214));
        changeAddressBtn.setForeground(Color.white);
        changeAddressBtn.setFont(new Font("Poppins", Font.BOLD, 12));
        setupButton(changeAddressBtn);
        backgroundPanel.add(changeAddressBtn);

        // Payment option panel
        JPanel paymentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(245, 245, 245, 220));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.dispose();
            }
        };
        paymentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        paymentPanel.setOpaque(false);
        paymentPanel.setBounds(20, 160, getWidth() - 40, 60);

        JLabel paymentLabel = new JLabel("Payment Method:");
        paymentLabel.setFont(new Font("Arial", Font.BOLD, 14));
        paymentComboBox = new JComboBox<>(new String[]{"Cash on Delivery"});
        paymentComboBox.setPreferredSize(new Dimension(180, 30));
        
        paymentPanel.add(paymentLabel);
        paymentPanel.add(paymentComboBox);
        backgroundPanel.add(paymentPanel);

        // Table for cart with transparent background
        String[] columnNames = {"Food Name", "Quantity", "Price (Php)"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        cartTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                comp.setBackground(new Color(255, 255, 255, 180));
                return comp;
            }
        };
        
        // Style the table
        cartTable.setOpaque(false);
        cartTable.setShowGrid(false);
        cartTable.setIntercellSpacing(new Dimension(0, 0));
        cartTable.setRowHeight(25);
        cartTable.getTableHeader().setOpaque(false);
        cartTable.getTableHeader().setBackground(new Color(60, 60, 60, 220));
        cartTable.getTableHeader().setForeground(Color.WHITE);
        cartTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        // Center align some columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        cartTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        cartTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        
        // Set column widths
        cartTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        cartTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        cartTable.getColumnModel().getColumn(2).setPreferredWidth(100);

        // Create a scroll pane with custom transparency
        JScrollPane scrollPane = new JScrollPane(cartTable) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 120));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
                g2d.dispose();
            }
        };
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBounds(20, 250, getWidth() - 40, 300);
        backgroundPanel.add(scrollPane);

        totalLabel = new JLabel();
                
        loadCartItems(); // Load data from DB

        // Total amount panel
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setOpaque(false);
        totalPanel.setBounds(20, 550, getWidth() - 40, 30);
        
        totalLabel.setFont(new Font("Poppins", Font.BOLD, 16));
        totalLabel.setForeground(Color.WHITE);
        totalPanel.add(totalLabel);
        backgroundPanel.add(totalPanel);

        // Place Order button - With your specified color and no transparency
        placeOrderBtn = new JButton("Place Order");
        placeOrderBtn.setFont(new Font("Poppins", Font.BOLD, 14));
        placeOrderBtn.setBackground(new Color(95, 71, 214));
        placeOrderBtn.setForeground(Color.WHITE);
        placeOrderBtn.setBounds(getWidth() / 4 - 75, 580, 150, 40);
        placeOrderBtn.addActionListener(this);
        placeOrderBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backgroundPanel.add(placeOrderBtn);

        setVisible(false);
    }

    /**
     * Loads cart items specifically for the current user from the database
     * Using direct SQL query approach similar to CartFrame
     */
    private void loadCartItems() {
        // Clear the existing table data and reset total
        tableModel.setRowCount(0);
        total = 0.0;
        
        //System.out.println("Loading cart items for payment view...");
        
        // Check if there's a logged-in user from controller
        User currentUser = controller.getUser();
        if (currentUser == null) {
            //System.out.println("No user is currently logged in.");
            JOptionPane.showMessageDialog(this, 
                "Please log in to view your cart items.", 
                "Authentication Required", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int userId = currentUser.getUserId();
        //System.out.println("Loading cart for user ID: " + userId);
        
        // SQL query to filter by the current user's ID
        String sql = "SELECT c.Cart_ID, ci.Food_ID, f.Food_Name, ci.Item_Quantity, " +
                     "f.Price, (ci.Item_Quantity * f.Price) as SubTotal " +
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
            
            // Process the result set
            while (rs.next()) {
                int cartId = rs.getInt("Cart_ID");
                String foodName = rs.getString("Food_Name");
                int quantity = rs.getInt("Item_Quantity");
                double price = rs.getDouble("Price");
                double subtotal = rs.getDouble("SubTotal");
                
                // Store the cart ID for later use
                this.cartID = cartId;
                
             // Print debug info
//                System.out.println("Adding to payment table - Food: " + foodName + 
//                                  ", Quantity: " + quantity + 
//                                  ", Price: " + price + 
//                                  ", Subtotal: " + subtotal);
                
                // Add to table model (without subtotal column as per requirements)
                tableModel.addRow(new Object[] {
                    foodName,                       // Food Name
                    quantity,                       // Quantity
                    String.format("%.2f", price)    // Price (formatted)
                });
                
                // Add to total
                total += subtotal;
            }
            
            // Update the total display
            totalLabel.setText(String.format("Total: Php %.2f", total));
            
            //System.out.println("Loaded items with total: " + total + " for user ID: " + userId);
            
            if (tableModel.getRowCount() == 0) {
                //System.out.println("No items in cart for user ID: " + userId);
                JOptionPane.showMessageDialog(this, "No items in cart for user ID: " + userId, "No Items", JOptionPane.INFORMATION_MESSAGE);
            }
            
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
                //System.err.println("Error closing database resources: " + e.getMessage());
                JOptionPane.showMessageDialog(this, 
                "Error closing database resources: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            }
        }

        // Refresh the table UI
        tableModel.fireTableDataChanged();
        cartTable.revalidate();
        cartTable.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == placeOrderBtn) {
            String selectedPayment = (String) paymentComboBox.getSelectedItem();

            if ("Cash on Delivery".equals(selectedPayment)) {
                OtwFrame otw = new OtwFrame(controller);
                otw.setVisible(true);
                this.dispose();
            }

        } else if (e.getSource() == changeAddressBtn) {
            address = getAddress(controller.getUser());
            
            ChangeAddressFrame.showDialog(this, controller.getUser().getUserId(),address, this);
        }
    }

    private void setupButton(JButton button) {
        button.addActionListener(this);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    
    /**
     * Implementation of AddressChangeListener interface
     * This method will be called when the address is changed in ChangeAddressFrame
     */
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
        
        addressLabel.setText(address);
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
    
    public String getAddress(User user){
        address = user.getHouseNumber() + ", " + 
                    user.getStreet() + ", " + 
                    user.getBarangay() + ", " + 
                    user.getMunicipality() + ", " +
                    user.getProvince() + ", " +
                    user.getRegion();
                    
        return address;
    }
}