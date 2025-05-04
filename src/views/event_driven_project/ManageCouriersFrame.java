package views.event_driven_project;

import config.MSSQLConnection;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.sql.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.*;

public class ManageCouriersFrame extends JFrame implements ActionListener {
    private EventController controller;
    public ManageCouriersFrame(EventController controller) {
        this.controller = controller;
        couriersFrameConfig();
        loadCouriersData("all"); // Initially load all couriers
    }
    private Connection connection;
    private JTable couriersTable;
    private DefaultTableModel tableModel;
    private HashMap<Integer, String> riderIdMap = new HashMap<>(); // Map row index to rider ID
    
    // Icons
    private ImageIcon couriersIcon = new ImageIcon(getClass().getResource("/views/Images/plain.png"));
    
    // Labels
    private JLabel background = new JLabel(couriersIcon);
    
    // Filter Buttons
    private JButton showAllButton = new JButton("Show All");
    private JButton showAvailableButton = new JButton("Show Available");
    private JButton showUnavailableButton = new JButton("Show Unavailable");
    private JButton backButton = new JButton("Back");
    
    // Add Courier Button
    private JButton addCourierButton = new JButton("Add Courier");
    
    public void couriersFrameConfig() {
        background.setLayout(null);
        this.setContentPane(background);
        
        setTitle("Manage Couriers");
        
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
        
        showAvailableButton.setFont(new Font("Poppins", Font.BOLD, 14));
        showAvailableButton.setForeground(new Color(95, 71, 214));
        showAvailableButton.setBackground(new Color(245, 245, 245));
        showAvailableButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(95, 71, 214), 1, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        showAvailableButton.setFocusPainted(false);
        showAvailableButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showAvailableButton.addActionListener(this);
        
        showUnavailableButton.setFont(new Font("Poppins", Font.BOLD, 14));
        showUnavailableButton.setForeground(new Color(95, 71, 214));
        showUnavailableButton.setBackground(new Color(245, 245, 245));
        showUnavailableButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(95, 71, 214), 1, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        showUnavailableButton.setFocusPainted(false);
        showUnavailableButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showUnavailableButton.addActionListener(this);
        
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
        
        // Add courier button
        addCourierButton.setFont(new Font("Poppins", Font.BOLD, 14));
        addCourierButton.setForeground(Color.WHITE);
        addCourierButton.setBackground(new Color(95, 71, 214));
        addCourierButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 50, 180), 1, true),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        addCourierButton.setFocusPainted(false);
        addCourierButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addCourierButton.addActionListener(this);
        
        // Position filter buttons
        int buttonY = 20;
        showAllButton.setBounds(50, buttonY, 120, 35);
        showAvailableButton.setBounds(190, buttonY, 150, 35);
        showUnavailableButton.setBounds(360, buttonY, 170, 35);
        backButton.setBounds(1000, buttonY, 100, 35);
        
        background.add(showAllButton);
        background.add(showAvailableButton);
        background.add(showUnavailableButton);
        background.add(backButton);
        
        // Create table
        String[] columns = {"Courier ID", "Name", "Company", "Contact No.", "Status", "Action"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only Action column is editable
            }
        };
        
        couriersTable = new JTable(tableModel);
        couriersTable.setRowHeight(40);
        couriersTable.setFont(new Font("Poppins", Font.PLAIN, 16));
        couriersTable.setForeground(new Color(95, 71, 214));
        couriersTable.setOpaque(false);
        couriersTable.setBackground(new Color(0, 0, 0, 0));
        couriersTable.setShowGrid(false);
        couriersTable.setIntercellSpacing(new Dimension(0, 0));
        
        // Set column widths for better proportions
        TableColumnModel columnModel = couriersTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80);   // ID column
        columnModel.getColumn(1).setPreferredWidth(150);  // Name column
        columnModel.getColumn(2).setPreferredWidth(120);  // Company column
        columnModel.getColumn(3).setPreferredWidth(120);  // Contact column
        columnModel.getColumn(4).setPreferredWidth(100);  // Status column
        columnModel.getColumn(5).setPreferredWidth(150);  // Action column
        
        // Center alignment for text columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < couriersTable.getColumnCount(); i++) {
            if (i != 5) { // Apply center only to non-button columns
                couriersTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        
        // Status column with colored status
            couriersTable.getColumnModel().getColumn(4).setCellRenderer(new StatusRenderer());
        
        // Custom header styling
        JTableHeader header = couriersTable.getTableHeader();
        header.setFont(new Font("Poppins", Font.BOLD, 16));
        header.setForeground(new Color(95, 71, 214));
        header.setBackground(new Color(245, 245, 245));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(95, 71, 214)));
        
        couriersTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonsRenderer());
        couriersTable.getColumnModel().getColumn(5).setCellEditor(new ButtonsEditor(new JCheckBox()));
        
        // Create custom scrollpane with rounded corners and subtle shadow
        JScrollPane scrollPane = new JScrollPane(couriersTable) {
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

        int width = couriersIcon != null ? (int) (couriersIcon.getIconWidth() * 0.9) : 800;
        int height = couriersIcon != null ? couriersIcon.getIconHeight() - 220 : 500; // Make room for Add button
        int x = couriersIcon != null ? ((couriersIcon.getIconWidth() - width) / 2) - 5 : 50;
        int y = buttonY + 50; // Position below the filter buttons
        
        scrollPane.setBounds(x, y, width, height);
        background.add(scrollPane);
        
        // Position Add Courier button at the bottom
        int addButtonY = y + height + 20;
        addCourierButton.setBounds(width / 2 - 80 + x, addButtonY, 160, 45);
        background.add(addCourierButton);
        
        // Frame configuration
        int frameWidth = couriersIcon != null ? couriersIcon.getIconWidth() : 900;
        int frameHeight = couriersIcon != null ? couriersIcon.getIconHeight() + 10 : 600;
        this.setSize(frameWidth, frameHeight);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
    }
    
    public void refreshCourierList() {
        tableModel.setRowCount(0);
        riderIdMap.clear();
        try {
            // Clear the existing model
            DefaultTableModel model = (DefaultTableModel) couriersTable.getModel();
            model.setRowCount(0);

            // Execute a query to get all couriers
            String sql = "SELECT Rider_ID, First_Name, Last_Name, Courier_Company, Courier_Contact, Rider_Status FROM COURIER ORDER BY First_Name, Last_Name";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Populate the table with fresh data
            int rowIndex = 0;
            while (resultSet.next()) {
                String id = resultSet.getString("Rider_ID");
                String name = resultSet.getString("First_Name") + " " + resultSet.getString("Last_Name");
                String company = resultSet.getString("Courier_Company");
                String contact = resultSet.getString("Courier_Contact");
                String status = resultSet.getString("Rider_Status");

                model.addRow(new Object[]{id, name, company, contact, status, ""});
                
                riderIdMap.put(rowIndex, id);
                rowIndex++;
            }
                
            resultSet.close();
            statement.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                "Error refreshing courier list: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showAllButton) {
            loadCouriersData("all");
        } else if (e.getSource() == showAvailableButton) {
            loadCouriersData("available");
        } else if (e.getSource() == showUnavailableButton) {
            loadCouriersData("unavailable");
        } else if (e.getSource() == addCourierButton) {
            // Open AddCourierFrame instead of a dialog
            AddCourierFrame addCourierFrame = new AddCourierFrame(this);
            addCourierFrame.setVisible(true);
        } else if (e.getSource() == backButton) {
            // Add navigation back to main menu or previous screen
            controller.showAdminNavFrame(this);
        }
    }
    
    public void loadCouriersData(String filter) {
        // Clear existing data
        tableModel.setRowCount(0);
        riderIdMap.clear();
        
        try {
            connection = MSSQLConnection.getConnection();
            String query;
            
            if (filter.equals("available")) {
                query = "SELECT Rider_ID, First_Name, Last_Name, Courier_Company, Courier_Contact, Rider_Status " +
                        "FROM COURIER WHERE Rider_Status = 'Available' ORDER BY First_Name, Last_Name";
            } else if (filter.equals("unavailable")) {
                query = "SELECT Rider_ID, First_Name, Last_Name, Courier_Company, Courier_Contact, Rider_Status " +
                        "FROM COURIER WHERE Rider_Status = 'Unavailable' ORDER BY First_Name, Last_Name";
            } else {
                query = "SELECT Rider_ID, First_Name, Last_Name, Courier_Company, Courier_Contact, Rider_Status " +
                        "FROM COURIER ORDER BY First_Name, Last_Name";
            }
            
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            int rowIndex = 0;
            while (rs.next()) {
                String id = rs.getString("Rider_ID");
                String firstName = rs.getString("First_Name");
                String lastName = rs.getString("Last_Name");
                String fullName = firstName + " " + lastName;
                String company = rs.getString("Courier_Company");
                String contact = rs.getString("Courier_Contact");
                String status = rs.getString("Rider_Status");
                
                // Add to table model
                tableModel.addRow(new Object[]{id, fullName, company, contact, status, ""});
                
                // Store ID mapping
                riderIdMap.put(rowIndex, id);
                rowIndex++;
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading couriers data: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
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
    
    // Custom renderer for status column with colored status
    class StatusRenderer extends DefaultTableCellRenderer {
        public StatusRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
            
            Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
            
            if (value != null) {
                String status = value.toString();
                if (status.equalsIgnoreCase("Available")) {
                    setForeground(new Color(0, 130, 0));
                    setFont(new Font("Poppins", Font.BOLD, 16));
                } else {
                    setForeground(new Color(180, 0, 0));
                    setFont(new Font("Poppins", Font.BOLD, 16));
                }
            }
            
            return c;
        }
    }
    
    // Renders buttons in action column
    class ButtonsRenderer extends JPanel implements TableCellRenderer {
        private final JButton updateButton = new JButton("Update");
        private final JButton deleteButton = new JButton("Delete");
        
        public ButtonsRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            setOpaque(false);
            
            // Style the buttons
            for (JButton btn : new JButton[]{updateButton, deleteButton}) {
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
            add(deleteButton);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }
    
    // Editor for action buttons with update submenu
    class ButtonsEditor extends DefaultCellEditor {
        protected JPanel panel = new JPanel();
        protected JButton updateButton = new JButton("Update");
        protected JButton deleteButton = new JButton("Delete");
        private JPopupMenu updateMenu;
        private int currentRow;
        
        public ButtonsEditor(JCheckBox checkBox) {
            super(checkBox);
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            panel.setOpaque(false);
            
            // Create update submenu
            updateMenu = new JPopupMenu();
            JMenuItem updateDetailsItem = new JMenuItem("Update Details");
            JMenuItem updateAvailabilityItem = new JMenuItem("Update Availability");
            
            updateMenu.add(updateDetailsItem);
            updateMenu.add(updateAvailabilityItem);
            
            // Style menu items
            for (Component item : updateMenu.getComponents()) {
                if (item instanceof JMenuItem) {
                    ((JMenuItem) item).setFont(new Font("Poppins", Font.PLAIN, 14));
                    ((JMenuItem) item).setForeground(new Color(95, 71, 214));
                }
            }
            
            // Style buttons
            for (JButton btn : new JButton[]{updateButton, deleteButton}) {
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
            panel.add(deleteButton);
            
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
            updateDetailsItem.addActionListener(e -> {
                updateCourierDetails(currentRow);
                fireEditingStopped();
            });
            
            updateAvailabilityItem.addActionListener(e -> {
                updateCourierAvailability(currentRow);
                fireEditingStopped();
            });
            
            // Handle delete button
            deleteButton.addActionListener(e -> {
                deleteCourier(currentRow);
                fireEditingStopped();
            });
            
            deleteButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    deleteButton.setBackground(new Color(255, 230, 230));
                    deleteButton.setForeground(new Color(200, 0, 0));
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    deleteButton.setBackground(new Color(245, 245, 245));
                    deleteButton.setForeground(new Color(95, 71, 214));
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
        
        private void updateCourierDetails(int row) {
            String riderId = riderIdMap.get(row);
            String fullName = (String) tableModel.getValueAt(row, 1);
            String company = (String) tableModel.getValueAt(row, 2);
            String contact = (String) tableModel.getValueAt(row, 3);
            
            try {
                // Get current courier details
                connection = MSSQLConnection.getConnection();
                String query = "SELECT First_Name, Last_Name FROM COURIER WHERE Rider_ID = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, riderId);
                
                ResultSet rs = stmt.executeQuery();
                String firstName = "";
                String lastName = "";
                
                if (rs.next()) {
                    firstName = rs.getString("First_Name");
                    lastName = rs.getString("Last_Name");
                }
                
                rs.close();
                stmt.close();
                
                // Create update dialog with UpdateCourierFrame
                UpdateCourierFrame updateFrame = new UpdateCourierFrame(
                    ManageCouriersFrame.this, riderId, firstName, lastName, company, contact);
                updateFrame.setVisible(true);
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(panel.getTopLevelAncestor(),
                    "Error retrieving courier details: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        private void updateCourierAvailability(int row) {
            String riderId = riderIdMap.get(row);
            String currentStatus = (String) tableModel.getValueAt(row, 4);
            String newStatus = currentStatus.equalsIgnoreCase("Available") ? "Unavailable" : "Available";
            
            int confirm = JOptionPane.showConfirmDialog(panel.getTopLevelAncestor(),
                "Change status to " + newStatus + "?",
                "Update Availability", JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    connection = MSSQLConnection.getConnection();
                    String query = "UPDATE COURIER SET Rider_Status = ? WHERE Rider_ID = ?";
                    PreparedStatement stmt = connection.prepareStatement(query);
                    stmt.setString(1, newStatus);
                    stmt.setString(2, riderId);
                    
                    int result = stmt.executeUpdate();
                    if (result > 0) {
                        tableModel.setValueAt(newStatus, row, 4);
                        JOptionPane.showMessageDialog(panel.getTopLevelAncestor(),
                            "Courier status updated successfully!",
                            "Update Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                    stmt.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(panel.getTopLevelAncestor(),
                        "Error updating courier status: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        private void deleteCourier(int row) {
            String riderId = riderIdMap.get(row);
            String fullName = (String) tableModel.getValueAt(row, 1);
            
            int confirm = JOptionPane.showConfirmDialog(panel.getTopLevelAncestor(),
                "Are you sure you want to delete courier " + fullName + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    connection = MSSQLConnection.getConnection();
                    String query = "DELETE FROM COURIER WHERE Rider_ID = ?";
                    PreparedStatement stmt = connection.prepareStatement(query);
                    stmt.setString(1, riderId);
                    
                    int result = stmt.executeUpdate();
                    if (result > 0) {
                        // Remove from model
                        tableModel.removeRow(row);
                        
                        // Update ID map
                        riderIdMap.clear();
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            String id = (String) tableModel.getValueAt(i, 0);
                            riderIdMap.put(i, id);
                        }
                        
                        JOptionPane.showMessageDialog(panel.getTopLevelAncestor(),
                            "Courier deleted successfully!",
                            "Delete Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                    stmt.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(panel.getTopLevelAncestor(),
                        "Error deleting courier: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}