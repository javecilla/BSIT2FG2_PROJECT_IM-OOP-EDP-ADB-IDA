package views.event_driven_project;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

public class Cart extends JFrame implements ActionListener{
    private EventController controller;
    private HashMap<Integer, String> foodIdMap = new HashMap<>(); // Map row index to food ID
    private JTable cartTable;
    private DefaultTableModel tableModel;

    public Cart(EventController eventController) {
        this.controller = eventController;
        cartFrameConfig();
    }
    
    //icons
    ImageIcon shopCartIcon = new ImageIcon(getClass().getResource("/views/Images/cart.png"));
    ImageIcon checkOutIcon = new ImageIcon(getClass().getResource("/views/Images/check-out.png"));
    
    //lables
    JLabel background = new JLabel(shopCartIcon);

    //Buttons
    JButton checkOutButton = new JButton();
    
    public void cartFrameConfig() {
        background.setLayout(null);
        this.setContentPane(background);
        
        setTitle("Your Cart");

        String[] columns = {"Product", "Quantity", "Price", "Action"};
        Object[][] data = {
            {"Sample Item 1", 2, "$10.00"},
            {"Sample Item 2", 1, "$20.00"}
        };

        // Simulate corresponding food IDs for each item
        foodIdMap.put(0, "F001");
        foodIdMap.put(1, "F002");

        tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only Action column is editable
            }
        };

        cartTable = new JTable(tableModel);
        cartTable.setRowHeight(40);
        cartTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cartTable.setForeground(new Color(95, 71, 214));
        cartTable.setOpaque(false);
        cartTable.setBackground(new Color(0, 0, 0, 0));
        cartTable.setShowGrid(false);
        cartTable.setIntercellSpacing(new Dimension(0, 0));

        // Set column widths for better proportions
        TableColumnModel columnModel = cartTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150);  // Product column wider
        columnModel.getColumn(1).setPreferredWidth(80);   // Quantity column
        columnModel.getColumn(2).setPreferredWidth(80);   // Price column
        columnModel.getColumn(3).setPreferredWidth(100);  // Action column

        // Center alignment for text columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

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
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(95, 71, 214)));

        cartTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        cartTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox()));

        // Create a custom scrollpane with rounded corners and subtle shadow
        JScrollPane scrollPane = new JScrollPane(cartTable) {
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

        int width = (int) (shopCartIcon.getIconWidth() * 0.9);
        int height = shopCartIcon.getIconHeight() - 250;
        int x = ((shopCartIcon.getIconWidth() - width) / 2) - 5;
        int y = (int) (shopCartIcon.getIconHeight() * 0.2);

        scrollPane.setBounds(x, y, width, height);
        background.add(scrollPane);

        // Create total panel at the bottom of the table
        JPanel totalPanel = new JPanel();
        totalPanel.setOpaque(false);
        totalPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        JLabel totalLabel = new JLabel("Total: $30.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalLabel.setForeground(new Color(95, 71, 214));
        totalPanel.add(totalLabel);
        
        totalPanel.setBounds(x, y + height + 10, width, 30);
        background.add(totalPanel);

        // Add checkout button at the bottom
        setupButton(checkOutButton, checkOutIcon);
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
        this.setLayout(null);
        this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == checkOutButton){
            controller.showPaymentFrame(this);
        }
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
            String foodId = foodIdMap.get(row);
            System.out.println("Food ID: " + foodId);
            
            // Get current quantity from table model
            int currentQty = (int) tableModel.getValueAt(row, 1);
            int newQty = Math.max(1, currentQty + change); // Ensure quantity doesn't go below 1
            
            // Update the table
            tableModel.setValueAt(newQty, row, 1);
            
            // In a real app, you would update price too based on item price
            // For demo, just multiply by 10 or 20 depending on row
            double unitPrice = row == 0 ? 5.0 : 20.0;
            tableModel.setValueAt("$" + (unitPrice * newQty), row, 2);
            
            // Update total - in a real app this would be more sophisticated
            updateTotal();
        }
        
        private void updateTotal() {
            // This is a placeholder - in a real app you would calculate from all items
            double total = 0;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String priceStr = (String) tableModel.getValueAt(i, 2);
                total += Double.parseDouble(priceStr.replace("$", ""));
            }
            
            // Update the total label
            for (Component c : background.getComponents()) {
                if (c instanceof JPanel) {
                    for (Component panelC : ((JPanel)c).getComponents()) {
                        if (panelC instanceof JLabel && ((JLabel)panelC).getText().startsWith("Total:")) {
                            ((JLabel)panelC).setText(String.format("Total: $%.2f", total));
                        }
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
}