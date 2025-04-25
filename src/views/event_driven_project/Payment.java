package views.event_driven_project;

import config.MSSQLConnection;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class Payment extends JFrame implements ActionListener {

    private EventController controller;
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> paymentComboBox;
    private JButton placeOrderBtn, changeAddressBtn;
    private JLabel nameLabel, addressLabel, totalLabel;
    private JPanel userInfoPanel;

    private ImageIcon paymentIcon = new ImageIcon(getClass().getResource("/views/Images/payment option.png"));
    private ImageIcon changeAddressIcon = new ImageIcon(getClass().getResource("/views/Images/change-address.png"));

    public Payment(EventController controller) {
        this.controller = controller;
        paymentFrameConfig();
    }

//    public static void main(String[] args) {
////        try {
////            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        SwingUtilities.invokeLater(() -> new Payment());
//    }

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
        nameLabel = new JLabel("Juan Dela Cruz");
        addressLabel = new JLabel("123, Lorem Ipsum Street, Dolor Sit Amet, Bulacan, Region III");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        addressLabel.setFont(new Font("Arial", Font.ITALIC, 13));
        
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(addressLabel);
        backgroundPanel.add(userInfoPanel);

        // Change Address button
        changeAddressBtn = new JButton();
        changeAddressBtn.setBounds(getWidth() - 170, 60, changeAddressIcon.getIconWidth(), changeAddressIcon.getIconHeight());
        setupButton(changeAddressBtn, changeAddressIcon);
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
        paymentComboBox = new JComboBox<>(new String[]{"Cash on Delivery", "Online Payment"});
        paymentComboBox.setPreferredSize(new Dimension(180, 30));
        
        paymentPanel.add(paymentLabel);
        paymentPanel.add(paymentComboBox);
        backgroundPanel.add(paymentPanel);

        // Table for cart with transparent background
        String[] columnNames = {"Food Name", "Quantity", "Price (₱)"};
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

        loadCartItems(); // Load data from DB

        // Total amount panel
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setOpaque(false);
        totalPanel.setBounds(20, 550, getWidth() - 40, 30);
        
        totalLabel = new JLabel("Total: ₱ " + calculateTotal());
        totalLabel.setFont(new Font("Poppins", Font.BOLD, 16));
        totalLabel.setForeground(Color.WHITE);
        totalPanel.add(totalLabel);
        backgroundPanel.add(totalPanel);

        // Place Order button - With your specified color and no transparency
        placeOrderBtn = new JButton("Place Order");
        placeOrderBtn.setFont(new Font("Poppins", Font.BOLD, 14));
        placeOrderBtn.setBackground(new Color(95, 71, 214));
        placeOrderBtn.setForeground(Color.WHITE);
        placeOrderBtn.setOpaque(true); // Make sure it's not transparent
        placeOrderBtn.setBorderPainted(false); // Often looks better without border when using custom colors
        placeOrderBtn.setBounds(getWidth() / 4 - 75, 580, 150, 40);
        placeOrderBtn.addActionListener(this);
        placeOrderBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backgroundPanel.add(placeOrderBtn);

        setVisible(false);
    }

    private double calculateTotal() {
        double total = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            total += Double.parseDouble(tableModel.getValueAt(i, 2).toString());
        }
        return total;
    }

    private void loadCartItems() {
        tableModel.setRowCount(0); // clear old rows
        String query = "SELECT CartItem_ID, Food_ID, Item_Quantity FROM CART_ITEM";
        try (Connection conn = MSSQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("CartItem_ID");
                int qty = rs.getInt("Food_ID");
                double price = rs.getDouble("Item_Quantity");
                tableModel.addRow(new Object[]{name, qty, price});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load cart items:\n" + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == placeOrderBtn) {
            String selectedPayment = (String) paymentComboBox.getSelectedItem();

            if ("Online Payment".equals(selectedPayment)) {
                showOnlinePaymentPrompt();
            } else if ("Cash on Delivery".equals(selectedPayment)) {
                // Using default JOptionPane
                JOptionPane.showMessageDialog(this, "Order placed with Cash on Delivery.");
                // Here you would process the order in your database
            }

        } else if (e.getSource() == changeAddressBtn) {
            showChangeAddressDialog();
        }
    }

    private void showChangeAddressDialog() {
        // Using standard JDialog with default styling
        JDialog dialog = new JDialog(this, "Change Delivery Address", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 10));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel houseNumberLabel = new JLabel("Street Address:");
        JTextField houseNumberField = new JTextField(addressLabel.getText().split(",")[0].trim());
        
        JLabel streetLabel = new JLabel("Street Address:");
        JTextField streetField = new JTextField(addressLabel.getText().split(",")[1].trim());
        
        JLabel cityLabel = new JLabel("City/Municipality:");
        JTextField cityField = new JTextField(addressLabel.getText().split(",")[2].trim());
        
        JLabel provinceLabel = new JLabel("Province:");
        JTextField provinceField = new JTextField(addressLabel.getText().split(",")[3].trim());
        
        JLabel regionLabel = new JLabel("Region:");
        JTextField regionField = new JTextField(addressLabel.getText().split(",")[4].trim());
        
        formPanel.add(houseNumberLabel);
        formPanel.add(houseNumberField);
        formPanel.add(streetLabel);
        formPanel.add(streetField);
        formPanel.add(cityLabel);
        formPanel.add(cityField);
        formPanel.add(provinceLabel);
        formPanel.add(provinceField);
        formPanel.add(regionLabel);
        formPanel.add(regionField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save Address");
        saveBtn.addActionListener(e -> {
            String newAddress = houseNumberField.getText() + ", " 
                              + streetField.getText() + ", " 
                              + cityField.getText() + ", " 
                              + provinceField.getText() + ", "
                              + regionField.getText();
            addressLabel.setText(newAddress);
            dialog.dispose();
        });
        buttonPanel.add(saveBtn);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showOnlinePaymentPrompt() {
        // Using standard JDialog with default styling
        JDialog dialog = new JDialog(this, "Online Payment", true);
        dialog.setSize(350, 220);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel totalAmountLabel = new JLabel("Total Amount: ₱ " + calculateTotal());
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalAmountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel paymentLabel = new JLabel("Enter Payment Amount:");
        paymentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        paymentLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
        
        JTextField amountField = new JTextField(10);
        amountField.setMaximumSize(new Dimension(150, 30));
        amountField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton submitBtn = new JButton("Submit Payment");
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.setBorder(BorderFactory.createRaisedBevelBorder());

        submitBtn.addActionListener(evt -> {
            String amount = amountField.getText();
            if (amount.matches("\\d+(\\.\\d{1,2})?")) {
                double paid = Double.parseDouble(amount);
                double total = calculateTotal();
                
                if (paid >= total) {
                    // Using default JOptionPane
                    JOptionPane.showMessageDialog(dialog, 
                        String.format("Payment of ₱%.2f accepted.\nChange: ₱%.2f", 
                        paid, paid - total));
                    dialog.dispose();
                } else {
                    // Using default JOptionPane
                    JOptionPane.showMessageDialog(dialog, 
                        "Insufficient payment. Please enter at least ₱" + total);
                }
            } else {
                // Using default JOptionPane
                JOptionPane.showMessageDialog(dialog, "Invalid amount. Please enter numbers only.");
            }
        });

        contentPanel.add(totalAmountLabel);
        contentPanel.add(paymentLabel);
        contentPanel.add(amountField);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(submitBtn);
        
        dialog.add(contentPanel);
        dialog.setVisible(true);
    }

    private void setupButton(JButton button, ImageIcon icon) {
        button.setIcon(icon);
        button.setBorder(new EmptyBorder(0, 0, 0, 0));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.addActionListener(this);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(darkenImageIcon(icon));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(icon);
            }
        });
    }

    private ImageIcon darkenImageIcon(ImageIcon icon) {
        Image img = icon.getImage();
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(img, 0, 0, null);

        RescaleOp rescaleOp = new RescaleOp(0.7f, 0, null);
        rescaleOp.filter(bufferedImage, bufferedImage);

        g2d.dispose();
        return new ImageIcon(bufferedImage);
    }
}