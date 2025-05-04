package views.event_driven_project;

import config.MSSQLConnection;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.sql.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.util.ArrayList;

public class ManageStocksFrame extends JFrame implements ActionListener {
    private EventController controller;
    public ManageStocksFrame(EventController controller) {
        this.controller = controller;
        stocksFrameConfig();
        loadIngredientsData(false); // Initially load all ingredients
    }
    private Connection connection;
    private JTable stocksTable;
    private DefaultTableModel tableModel;
    private HashMap<Integer, String> ingredientIdMap = new HashMap<>(); // Map row index to ingredient ID
    
    // Icons
    private ImageIcon stocksIcon = new ImageIcon(getClass().getResource("/views/Images/plain.png"));
    
    // Labels
    private JLabel background = new JLabel(stocksIcon);
    
    // Buttons
    private JButton showAllButton = new JButton("Show All");
    private JButton showLowStocksButton = new JButton("Show Low Stocks");
    private JButton backButton = new JButton("Back");
    
    public void stocksFrameConfig() {
        background.setLayout(null);
        this.setContentPane(background);
        
        setTitle("Manage Stocks");
        
        // Create filter buttons at the top
        showAllButton.setFont(new Font("Poppins", Font.BOLD, 14));
        showAllButton.setForeground(new Color(95, 71, 214));
        showAllButton.setBackground(new Color(245, 245, 245));
        showAllButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(95, 71, 214), 1, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        showAllButton.setFocusPainted(false);
        showAllButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showAllButton.addActionListener(this);
        
        showLowStocksButton.setFont(new Font("Poppins", Font.BOLD, 14));
        showLowStocksButton.setForeground(new Color(95, 71, 214));
        showLowStocksButton.setBackground(new Color(245, 245, 245));
        showLowStocksButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(95, 71, 214), 1, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        showLowStocksButton.setFocusPainted(false);
        showLowStocksButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showLowStocksButton.addActionListener(this);
        
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
        
        // Position filter buttons
        int buttonY = 20;
        showAllButton.setBounds(50, buttonY, 120, 35);
        showLowStocksButton.setBounds(190, buttonY, 170, 35);
        backButton.setBounds(1000, buttonY, 100, 35);
        
        background.add(showAllButton);
        background.add(showLowStocksButton);
        background.add(backButton);
        
        // Create table
        String[] columns = {"Ingredient ID", "Name", "Current Stock", "Reorder Point", "Action"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only Action column is editable
            }
        };
        
        stocksTable = new JTable(tableModel);
        stocksTable.setRowHeight(40);
        stocksTable.setFont(new Font("Poppins", Font.PLAIN, 16));
        stocksTable.setForeground(new Color(95, 71, 214));
        stocksTable.setOpaque(false);
        stocksTable.setBackground(new Color(0, 0, 0, 0));
        stocksTable.setShowGrid(false);
        stocksTable.setIntercellSpacing(new Dimension(0, 0));
        
        // Set column widths for better proportions
        TableColumnModel columnModel = stocksTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);  // ID column
        columnModel.getColumn(1).setPreferredWidth(150);  // Name column
        columnModel.getColumn(2).setPreferredWidth(100);  // Stock column
        columnModel.getColumn(3).setPreferredWidth(100);  // Reorder Point column
        columnModel.getColumn(4).setPreferredWidth(150);  // Action column
        
        // Center alignment for text columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < stocksTable.getColumnCount(); i++) {
            if (i != 4) { // Apply center only to non-button columns
                stocksTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        
        // Custom header styling
        JTableHeader header = stocksTable.getTableHeader();
        header.setFont(new Font("Poppins", Font.BOLD, 16));
        header.setForeground(new Color(95, 71, 214));
        header.setBackground(new Color(245, 245, 245));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(95, 71, 214)));
        
        stocksTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonsRenderer());
        stocksTable.getColumnModel().getColumn(4).setCellEditor(new ButtonsEditor(new JCheckBox()));
        
        // Create custom scrollpane with rounded corners and subtle shadow
        JScrollPane scrollPane = new JScrollPane(stocksTable) {
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

        int width = stocksIcon != null ? (int) (stocksIcon.getIconWidth() * 0.9) : 800;
        int height = stocksIcon != null ? stocksIcon.getIconHeight() - 150 : 500;
        int x = stocksIcon != null ? ((stocksIcon.getIconWidth() - width) / 2) - 5 : 50;
        int y = buttonY + 50; // Position below the filter buttons
        
        scrollPane.setBounds(x, y, width, height);
        background.add(scrollPane);
        
        // Frame configuration
        int frameWidth = stocksIcon != null ? stocksIcon.getIconWidth() : 900;
        int frameHeight = stocksIcon != null ? stocksIcon.getIconHeight() + 10 : 600;
        this.setSize(frameWidth, frameHeight);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showAllButton) {
            loadIngredientsData(false);
        } else if (e.getSource() == showLowStocksButton) {
            loadIngredientsData(true);
        }
        if(e.getSource() == backButton){
            controller.showDashboardFrame(this);
        }
    }
    
    private void loadIngredientsData(boolean lowStocksOnly) {
        // Clear existing data
        tableModel.setRowCount(0);
        ingredientIdMap.clear();
        
        try {
            connection = MSSQLConnection.getConnection();
            String query;
            
            if (lowStocksOnly) {
                query = "SELECT Ingredient_ID, Ingredient_Name, Ingredient_Quantity, Reorder_Point " +
                        "FROM INGREDIENT WHERE Ingredient_Quantity <= Reorder_Point ORDER BY Ingredient_Name";
            } else {
                query = "SELECT Ingredient_ID, Ingredient_Name, Ingredient_Quantity, Reorder_Point " +
                        "FROM INGREDIENT ORDER BY Ingredient_Name";
            }
            
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            int rowIndex = 0;
            while (rs.next()) {
                String id = rs.getString("Ingredient_ID");
                String name = rs.getString("Ingredient_Name");
                int quantity = rs.getInt("Ingredient_Quantity");
                int reorderPoint = rs.getInt("Reorder_Point");
                
                // Add to table model
                tableModel.addRow(new Object[]{id, name, quantity, reorderPoint, ""});
                
                // Store ID mapping
                ingredientIdMap.put(rowIndex, id);
                rowIndex++;
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading ingredients data: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Renders buttons in action column
    class ButtonsRenderer extends JPanel implements TableCellRenderer {
        private final JButton updateButton = new JButton("Update");
        private final JButton reorderButton = new JButton("Reorder");
        
        public ButtonsRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            setOpaque(false);
            
            // Style the buttons
            for (JButton btn : new JButton[]{updateButton, reorderButton}) {
                btn.setFocusable(true);
                btn.setContentAreaFilled(true);
                btn.setBorderPainted(true);
                btn.setOpaque(true);
                btn.setFont(new Font("Poppins", Font.BOLD, 14));
                btn.setBackground(new Color(245, 245, 245));
                btn.setForeground(new Color(95, 71, 214));
                btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(95, 71, 214), 1, true),
                    BorderFactory.createEmptyBorder(2, 8, 2, 8)
                ));
            }
            
            add(updateButton);
            add(reorderButton);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }
    
    // Editor for action buttons with update submenu
    class ButtonsEditor extends DefaultCellEditor {
        protected JPanel panel = new JPanel();
        protected JButton updateButton = new JButton("Update");
        protected JButton reorderButton = new JButton("Reorder");
        private JPopupMenu updateMenu;
        private int currentRow;
        
        public ButtonsEditor(JCheckBox checkBox) {
            super(checkBox);
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            panel.setOpaque(false);
            
            // Create update submenu
            updateMenu = new JPopupMenu();
            JMenuItem updateStocksItem = new JMenuItem("Update Stocks");
            JMenuItem updateReorderItem = new JMenuItem("Update Reorder Point");
            JMenuItem updateSupplierItem = new JMenuItem("Update Supplier");
            
            updateMenu.add(updateStocksItem);
            updateMenu.add(updateReorderItem);
            updateMenu.add(updateSupplierItem);
            
            // Style menu items
            for (Component item : updateMenu.getComponents()) {
                if (item instanceof JMenuItem) {
                    ((JMenuItem) item).setFont(new Font("Poppins", Font.PLAIN, 14));
                    ((JMenuItem) item).setForeground(new Color(95, 71, 214));
                }
            }
            
            // Style buttons
            for (JButton btn : new JButton[]{updateButton, reorderButton}) {
                btn.setFocusable(false);
                btn.setContentAreaFilled(true);
                btn.setBorderPainted(true);
                btn.setOpaque(true);
                btn.setFont(new Font("Poppins", Font.BOLD, 14));
                btn.setBackground(new Color(245, 245, 245));
                btn.setForeground(new Color(95, 71, 214));
                btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(95, 71, 214), 1, true),
                    BorderFactory.createEmptyBorder(2, 8, 2, 8)
                ));
            }
            
            panel.add(updateButton);
            panel.add(reorderButton);
            
            // Handle update button
            updateButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    updateButton.setBackground(new Color(230, 230, 245));
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    updateButton.setBackground(new Color(245, 245, 245));
                }
                
                @Override
                public void mousePressed(MouseEvent e) {
                    updateMenu.show(updateButton, 0, updateButton.getHeight());
                }
            });
            
            // Action listeners for menu items
            updateStocksItem.addActionListener(e -> {
                updateStocks(currentRow);
                fireEditingStopped();
            });
            
            updateReorderItem.addActionListener(e -> {
                updateReorderPoint(currentRow);
                fireEditingStopped();
            });
            
            updateSupplierItem.addActionListener(e -> {
                // Currently empty as requested
                JOptionPane.showMessageDialog(panel, 
                    "Supplier update functionality will be implemented in future updates.",
                    "Feature Coming Soon", JOptionPane.INFORMATION_MESSAGE);
                fireEditingStopped();
            });
            
            // Handle reorder button
            reorderButton.addActionListener(e -> {
                reorderIngredient(currentRow);
                fireEditingStopped();
            });
            
            reorderButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    reorderButton.setBackground(new Color(230, 230, 245));
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    reorderButton.setBackground(new Color(245, 245, 245));
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
        
        private void updateStocks(int row) {
            String ingredientId = ingredientIdMap.get(row);
            String ingredientName = (String) tableModel.getValueAt(row, 1);
            int currentStock = (int) tableModel.getValueAt(row, 2);
            
            String input = JOptionPane.showInputDialog(panel,
                    "Enter new stock quantity for " + ingredientName + ":",
                    "Update Stock", JOptionPane.QUESTION_MESSAGE);
            
            if (input != null && !input.trim().isEmpty()) {
                try {
                    int newStock = Integer.parseInt(input.trim());
                    if (newStock < 0) {
                        JOptionPane.showMessageDialog(panel,
                                "Please enter a non-negative quantity.",
                                "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // Update database
                    try {
                        connection = MSSQLConnection.getConnection();
                        String query = "UPDATE INGREDIENT SET Ingredient_Quantity = ? WHERE Ingredient_ID = ?";
                        PreparedStatement stmt = connection.prepareStatement(query);
                        stmt.setInt(1, newStock);
                        stmt.setString(2, ingredientId);
                        
                        int result = stmt.executeUpdate();
                        if (result > 0) {
                            // Update table model
                            tableModel.setValueAt(newStock, row, 2);
                            JOptionPane.showMessageDialog(panel,
                                    "Stock updated successfully!",
                                    "Update Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                        
                        stmt.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(panel,
                                "Error updating stock: " + e.getMessage(),
                                "Database Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(panel,
                            "Please enter a valid number.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        private void updateReorderPoint(int row) {
            String ingredientId = ingredientIdMap.get(row);
            String ingredientName = (String) tableModel.getValueAt(row, 1);
            int currentReorderPoint = (int) tableModel.getValueAt(row, 3);
            
            String input = JOptionPane.showInputDialog(panel,
                    "Enter new reorder point for " + ingredientName + ":",
                    "Update Reorder Point", JOptionPane.QUESTION_MESSAGE);
            
            if (input != null && !input.trim().isEmpty()) {
                try {
                    int newReorderPoint = Integer.parseInt(input.trim());
                    if (newReorderPoint < 0) {
                        JOptionPane.showMessageDialog(panel,
                                "Please enter a non-negative value.",
                                "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // Update database
                    try {
                        connection = MSSQLConnection.getConnection();
                        String query = "UPDATE INGREDIENT SET Reorder_Point = ? WHERE Ingredient_ID = ?";
                        PreparedStatement stmt = connection.prepareStatement(query);
                        stmt.setInt(1, newReorderPoint);
                        stmt.setString(2, ingredientId);
                        
                        int result = stmt.executeUpdate();
                        if (result > 0) {
                            // Update table model
                            tableModel.setValueAt(newReorderPoint, row, 3);
                            JOptionPane.showMessageDialog(panel,
                                    "Reorder point updated successfully!",
                                    "Update Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                        
                        stmt.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(panel,
                                "Error updating reorder point: " + e.getMessage(),
                                "Database Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(panel,
                            "Please enter a valid number.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        private void reorderIngredient(int row) {
            String ingredientId = ingredientIdMap.get(row);
            String ingredientName = (String) tableModel.getValueAt(row, 1);
            
            String input = JOptionPane.showInputDialog(panel,
                    "Enter quantity to reorder for " + ingredientName + ":",
                    "Reorder Ingredient", JOptionPane.QUESTION_MESSAGE);
            
            if (input != null && !input.trim().isEmpty()) {
                try {
                    int reorderQty = Integer.parseInt(input.trim());
                    if (reorderQty <= 0) {
                        JOptionPane.showMessageDialog(panel,
                                "Please enter a positive quantity.",
                                "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // Get current stock
                    int currentStock = (int) tableModel.getValueAt(row, 2);
                    int newStock = currentStock + reorderQty;
                    
                    // Update database with new stock level
                    try {
                        connection = MSSQLConnection.getConnection();
                        String query = "UPDATE INGREDIENT SET Ingredient_Quantity = ? WHERE Ingredient_ID = ?";
                        PreparedStatement stmt = connection.prepareStatement(query);
                        stmt.setInt(1, newStock);
                        stmt.setString(2, ingredientId);
                        
                        int result = stmt.executeUpdate();
                        if (result > 0) {
                            // Update table model
                            tableModel.setValueAt(newStock, row, 2);
                            
                            // Here you would normally also log the reorder in another table
                            JOptionPane.showMessageDialog(panel,
                                    "Reorder placed successfully! " + reorderQty + 
                                    " units of " + ingredientName + " added to inventory.",
                                    "Reorder Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                        
                        stmt.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(panel,
                                "Error processing reorder: " + e.getMessage(),
                                "Database Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(panel,
                            "Please enter a valid number.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private ImageIcon darkenImageIcon(ImageIcon icon) {
        Image img = icon.getImage();
        BufferedImage bufferedImage = new BufferedImage(
                img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        
        // Apply a filter to darken the image
        RescaleOp rescaleOp = new RescaleOp(0.7f, 0, null);  // Darken by 30%
        rescaleOp.filter(bufferedImage, bufferedImage);
        
        g2d.dispose();
        return new ImageIcon(bufferedImage);
    }
    
}