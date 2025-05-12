package views.event_driven_project;

import config.MSSQLConnection;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.util.ArrayList;

public class OrderReceivedFrame extends JFrame implements ActionListener {
    private EventController controller;
    public OrderReceivedFrame(EventController controller) {
        this.controller = controller;
        orderReceivedFrameConfig();
        loadOrdersData(controller.getUser().getUserId());
    }
    private Connection connection;
    private JTable ordersTable;
    private DefaultTableModel tableModel;
    private Map<Integer, String> orderIdMap = new HashMap<>(); // Map row index to order ID
    
    // Icons
    private ImageIcon orderIcon = new ImageIcon(getClass().getResource("/views/Images/plain.png"));
    
    // Labels
    private JLabel background = new JLabel(orderIcon);
    
    // Buttons
    private JButton refreshButton = new JButton("Refresh");
    private JButton backButton = new JButton("Back");
    
    public void orderReceivedFrameConfig() {
        background.setLayout(null);
        this.setContentPane(background);
        
        setTitle("Order Received Management");
        
        // Create buttons at the top
        refreshButton.setFont(new Font("Poppins", Font.BOLD, 14));
        refreshButton.setForeground(new Color(95, 71, 214));
        refreshButton.setBackground(new Color(245, 245, 245));
        refreshButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(95, 71, 214), 1, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        refreshButton.setFocusPainted(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.addActionListener(this);
        
        backButton.setFont(new Font("Poppins", Font.BOLD, 14));
        backButton.setForeground(new Color(95, 71, 214));
        backButton.setBackground(new Color(245, 245, 245));
        backButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(95, 71, 214), 1, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(this);
        
        // Position buttons
        int buttonY = 20;
        refreshButton.setBounds(50, buttonY, 120, 35);
        backButton.setBounds(1000, buttonY, 100, 35);
        
        background.add(refreshButton);
        background.add(backButton);
        
        // Create table
        String[] columns = {"Ordered Date", "Order ID", "Foods", "Quantity", "Amount to Prepare", "Rider Name", "Action"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only Action column is editable
            }
        };
        
        ordersTable = new JTable(tableModel);
        ordersTable.setRowHeight(40);
        ordersTable.setFont(new Font("Poppins", Font.PLAIN, 16));
        ordersTable.setForeground(new Color(95, 71, 214));
        ordersTable.setOpaque(false);
        ordersTable.setBackground(new Color(0, 0, 0, 0));
        ordersTable.setShowHorizontalLines(true); // Enable horizontal grid lines
        ordersTable.setGridColor(new Color(95, 71, 214, 50)); // Subtle grid color
        ordersTable.setIntercellSpacing(new Dimension(0, 1));
        
        // Set column widths for better proportions
        TableColumnModel columnModel = ordersTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(120);  // Ordered Date column
        columnModel.getColumn(1).setPreferredWidth(80);   // Order ID column
        columnModel.getColumn(2).setPreferredWidth(200);  // Foods column
        columnModel.getColumn(3).setPreferredWidth(80);   // Quantity column
        columnModel.getColumn(4).setPreferredWidth(120);  // Amount column
        columnModel.getColumn(5).setPreferredWidth(120);  // Rider Name column
        columnModel.getColumn(6).setPreferredWidth(150);  // Action column
        
        // Center alignment for text columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < ordersTable.getColumnCount(); i++) {
            if (i != 6) { // Apply center only to non-button columns
                ordersTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        
        // Custom header styling
        JTableHeader header = ordersTable.getTableHeader();
        header.setFont(new Font("Poppins", Font.BOLD, 16));
        header.setForeground(new Color(95, 71, 214));
        header.setBackground(new Color(245, 245, 245));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(95, 71, 214)));
        
        // Set custom renderer for foods column to display multiple items
        ordersTable.getColumnModel().getColumn(2).setCellRenderer(new MultiLineCellRenderer());
        
        // Add button renderer and editor
        ordersTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonsRenderer());
        ordersTable.getColumnModel().getColumn(6).setCellEditor(new ButtonsEditor(new JCheckBox(), controller.getUser().getUserId()));
        
        // Create custom scrollpane with rounded corners and subtle shadow
        JScrollPane scrollPane = new JScrollPane(ordersTable) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Paint subtle shadow and background
                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.fillRoundRect(3, 3, getWidth() - 6, getHeight() - 6, 15, 15);
                g2d.setColor(new Color(255, 255, 255, 180));
                g2d.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 3, 15, 15);
                g2d.dispose();
                
                super.paintComponent(g);
            }
        };
        
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        int width = orderIcon != null ? (int) (orderIcon.getIconWidth() * 0.9) : 800;
        int height = orderIcon != null ? orderIcon.getIconHeight() - 150 : 500;
        int x = orderIcon != null ? ((orderIcon.getIconWidth() - width) / 2) - 5 : 50;
        int y = buttonY + 50; // Position below the filter buttons
        
        scrollPane.setBounds(x, y, width, height);
        background.add(scrollPane);
        
        // Frame configuration
        int frameWidth = orderIcon != null ? orderIcon.getIconWidth() : 900;
        int frameHeight = orderIcon != null ? orderIcon.getIconHeight() + 10 : 600;
        this.setSize(frameWidth, frameHeight);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == refreshButton) {
            loadOrdersData(controller.getUser().getUserId());
        } else if (e.getSource() == backButton) {
            controller.showMenuFrame(this);
            controller.menuFrame.setButtonVisibility();
        }
    }
    
private void loadOrdersData(int userID) {
    tableModel.setRowCount(0);
    orderIdMap.clear();

    try (Connection conn = MSSQLConnection.getConnection()) {
        String query = "SELECT si.Sales_ID, si.Sales_Date, si.Net_Total, si.RiderName " +
                      "FROM SALES_WITH_INFO si " +
                      "JOIN SALE s ON s.Sales_ID = si.Sales_ID " +
                      "JOIN USER_ u ON s.Customer_ID = u.UserID " +
                      "WHERE s.Order_Status = 'Pending' AND u.UserID = ? " +
                      "ORDER BY si.Sales_Date DESC";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                int rowIndex = 0;
                while (rs.next()) {
                    String salesId = rs.getString("Sales_ID");
                    Date orderDate = rs.getDate("Sales_Date");
                    double netTotal = rs.getDouble("Net_Total");
                    String riderName = rs.getString("RiderName");

                    String itemsQuery = "SELECT f.Food_Name, sd.Item_Quantity " +
                                       "FROM SALES_DETAIL sd " +
                                       "JOIN FOOD f ON sd.Food_ID = f.Food_ID " +
                                       "WHERE sd.Sales_ID = ?";

                    try (PreparedStatement itemsStmt = conn.prepareStatement(itemsQuery)) {
                        itemsStmt.setString(1, salesId);
                        try (ResultSet itemsRs = itemsStmt.executeQuery()) {
                            StringBuilder foodsList = new StringBuilder();
                            int totalQuantity = 0;

                            while (itemsRs.next()) {
                                String foodName = itemsRs.getString("Food_Name");
                                int quantity = itemsRs.getInt("Item_Quantity");
                                foodsList.append(foodName).append(" (").append(quantity).append(")\n");
                                totalQuantity += quantity;
                            }

                            tableModel.addRow(new Object[]{
                                orderDate,
                                salesId,
                                foodsList.toString().trim(),
                                totalQuantity,
                                netTotal,
                                riderName,
                                ""
                            });

                            orderIdMap.put(rowIndex, salesId);
                            rowIndex++;
                        }
                    }
                }
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
                "Error loading orders data: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    // Custom renderer for multi-line food items
    class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {
        public MultiLineCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(false);
            setFont(new Font("Poppins", Font.PLAIN, 14));
            setForeground(new Color(95, 71, 214));
        }
        
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            setText((value == null) ? "" : value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), Short.MAX_VALUE);
            
            // Adjust row height based on content
            int preferredHeight = getPreferredSize().height;
            if (preferredHeight > table.getRowHeight(row)) {
                table.setRowHeight(row, preferredHeight);
            }
            
            return this;
        }
    }
    
    // Renders buttons in action column
    class ButtonsRenderer extends JPanel implements TableCellRenderer {
        private final JButton receivedButton = new JButton("Received");
        private final JButton cancelButton = new JButton("Cancel");
        
        public ButtonsRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            setOpaque(false);
            
            // Style the buttons
            for (JButton btn : new JButton[]{receivedButton, cancelButton}) {
                btn.setFocusable(true);
                btn.setContentAreaFilled(true);
                btn.setBorderPainted(true);
                btn.setOpaque(true);
                btn.setFont(new Font("Poppins", Font.BOLD, 14));
                btn.setBackground(new Color(245, 245, 245));
                btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(95, 71, 214), 1, true),
                    BorderFactory.createEmptyBorder(2, 8, 2, 8)
                ));
            }
            
            // Set different colors for buttons
            receivedButton.setForeground(new Color(34, 139, 34)); // Green
            cancelButton.setForeground(new Color(178, 34, 34)); // Red
            
            add(receivedButton);
            add(cancelButton);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }
    
class ButtonsEditor extends DefaultCellEditor {
    protected JPanel panel = new JPanel();
    protected JButton receivedButton = new JButton("Received");
    protected JButton cancelButton = new JButton("Cancel");
    private int currentRow;
    private int userID;

    public ButtonsEditor(JCheckBox checkBox, int userID) {
        super(checkBox);
        this.userID = userID;
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel.setOpaque(false);

        // Style buttons
        for (JButton btn : new JButton[]{receivedButton, cancelButton}) {
            btn.setFocusable(false);
            btn.setContentAreaFilled(true);
            btn.setBorderPainted(true);
            btn.setOpaque(true);
            btn.setFont(new Font("Poppins", Font.BOLD, 14));
            btn.setBackground(new Color(245, 245, 245));
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(95, 71, 214), 1, true),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
            ));
        }

        receivedButton.setForeground(new Color(34, 139, 34));
        cancelButton.setForeground(new Color(178, 34, 34));

        panel.add(receivedButton);
        panel.add(cancelButton);

        receivedButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                receivedButton.setBackground(new Color(230, 245, 230));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                receivedButton.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (!orderIdMap.containsKey(currentRow) || currentRow >= tableModel.getRowCount()) {
                    JOptionPane.showMessageDialog(panel,
                            "Invalid row selected. Please refresh the table.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    fireEditingStopped();
                    return;
                }
                markOrderAsReceived(currentRow);
            }
        });

        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancelButton.setBackground(new Color(245, 230, 230));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cancelButton.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (!orderIdMap.containsKey(currentRow) || currentRow >= tableModel.getRowCount()) {
                    JOptionPane.showMessageDialog(panel,
                            "Invalid row selected. Please refresh the table.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    fireEditingStopped();
                    return;
                }
                cancelOrder(currentRow);
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        currentRow = row;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }

    private void markOrderAsReceived(int row) {
        if (!orderIdMap.containsKey(row)) {
            JOptionPane.showMessageDialog(panel,
                    "Invalid row selected. Please refresh the table.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            fireEditingStopped();
            return;
        }

        String salesId = orderIdMap.get(row);
        int option = JOptionPane.showConfirmDialog(panel,
                "Are you sure you want to mark this order as received?",
                "Confirm Order Received", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try (Connection conn = MSSQLConnection.getConnection()) {
                conn.setAutoCommit(false); // Start transaction

                // 1. Update order status to 'Received'
                try (PreparedStatement orderStmt = conn.prepareStatement(
                        "UPDATE SALE SET Order_Status = 'Received' WHERE Sales_ID = ?")) {
                    orderStmt.setString(1, salesId);
                    int orderResult = orderStmt.executeUpdate();

                    // 2. Get the Rider_ID associated with this order
                    try (PreparedStatement riderStmt = conn.prepareStatement(
                            "SELECT Rider_ID FROM SALE WHERE Sales_ID = ?")) {
                        riderStmt.setString(1, salesId);
                        try (ResultSet rs = riderStmt.executeQuery()) {
                            if (rs.next()) {
                                String riderId = rs.getString("Rider_ID");

                                // 3. Update the courier status to available
                                try (PreparedStatement courierStmt = conn.prepareStatement(
                                        "UPDATE COURIER SET Rider_Status = 'Available' WHERE Rider_ID = ?")) {
                                    courierStmt.setString(1, riderId);
                                    int courierResult = courierStmt.executeUpdate();

                                    if (orderResult > 0 && courierResult > 0) {
                                        conn.commit(); // Commit the transaction
                                        JOptionPane.showMessageDialog(panel,
                                                "Order marked as received and courier set as available!",
                                                "Update Success", JOptionPane.INFORMATION_MESSAGE);

                                        fireEditingStopped();
                                        loadOrdersData(userID);
                                        int count = controller.menuFrame.countUserOrders(userID);
                                        controller.setOrderCount(count);
                                        controller.manageCourierFrame.loadCouriersData("all");
                                    } else {
                                        conn.rollback(); // Roll back if updates failed
                                        JOptionPane.showMessageDialog(panel,
                                                "Failed to update order status or courier availability.",
                                                "Update Failed", JOptionPane.ERROR_MESSAGE);
                                        fireEditingStopped();
                                    }
                                }
                            } else {
                                conn.rollback(); // Roll back if no rider found
                                JOptionPane.showMessageDialog(panel,
                                        "Could not find courier associated with this order.",
                                        "Update Failed", JOptionPane.ERROR_MESSAGE);
                                fireEditingStopped();
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(panel,
                        "Error updating order status: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                fireEditingStopped();
            }
        } else {
            fireEditingStopped();
        }
    }

    private void cancelOrder(int row) {
        if (!orderIdMap.containsKey(row)) {
            JOptionPane.showMessageDialog(panel,
                    "Invalid row selected. Please refresh the table.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            fireEditingStopped();
            return;
        }

        String salesId = orderIdMap.get(row);
        int option = JOptionPane.showConfirmDialog(panel,
                "Are you sure you want to cancel this order?",
                "Confirm Order Cancellation", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try (Connection conn = MSSQLConnection.getConnection()) {
                conn.setAutoCommit(false); // Start transaction

                // 1. Update order status to 'Cancelled'
                try (PreparedStatement orderStmt = conn.prepareStatement(
                        "UPDATE SALE SET Order_Status = 'Cancelled' WHERE Sales_ID = ?")) {
                    orderStmt.setString(1, salesId);
                    int orderResult = orderStmt.executeUpdate();

                    // 2. Get the Rider_ID associated with this order
                    try (PreparedStatement riderStmt = conn.prepareStatement(
                            "SELECT Rider_ID FROM SALE WHERE Sales_ID = ?")) {
                        riderStmt.setString(1, salesId);
                        try (ResultSet rs = riderStmt.executeQuery()) {
                            if (rs.next()) {
                                String riderId = rs.getString("Rider_ID");

                                // 3. Update the courier status to available
                                try (PreparedStatement courierStmt = conn.prepareStatement(
                                        "UPDATE COURIER SET Rider_Status = 'Available' WHERE Rider_ID = ?")) {
                                    courierStmt.setString(1, riderId);
                                    int courierResult = courierStmt.executeUpdate();

                                    if (orderResult > 0 && courierResult > 0) {
                                        conn.commit(); // Commit the transaction
                                        JOptionPane.showMessageDialog(panel,
                                                "Order cancelled and courier set as available!",
                                                "Update Success", JOptionPane.INFORMATION_MESSAGE);

                                        fireEditingStopped();
                                        loadOrdersData(userID);
                                        int count = controller.menuFrame.countUserOrders(userID);
                                        controller.setOrderCount(count);
                                        controller.manageCourierFrame.loadCouriersData("all");
                                    } else {
                                        conn.rollback(); // Roll back if updates failed
                                        JOptionPane.showMessageDialog(panel,
                                                "Failed to cancel order or update courier availability.",
                                                "Update Failed", JOptionPane.ERROR_MESSAGE);
                                        fireEditingStopped();
                                    }
                                }
                            } else {
                                // If there's no rider associated, we can still cancel the order
                                if (orderResult > 0) {
                                    conn.commit();
                                    JOptionPane.showMessageDialog(panel,
                                            "Order cancelled successfully! (No courier was assigned)",
                                            "Update Success", JOptionPane.INFORMATION_MESSAGE);

                                    fireEditingStopped();
                                    loadOrdersData(userID);
                                    int count = controller.menuFrame.countUserOrders(userID);
                                    controller.setOrderCount(count);
                                } else {
                                    conn.rollback();
                                    JOptionPane.showMessageDialog(panel,
                                            "Failed to cancel order.",
                                            "Update Failed", JOptionPane.ERROR_MESSAGE);
                                    fireEditingStopped();
                                }
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(panel,
                        "Error cancelling order: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                fireEditingStopped();
            }
        } else {
            fireEditingStopped();
        }
    }
}
}