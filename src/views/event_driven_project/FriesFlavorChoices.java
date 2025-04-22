package views.event_driven_project;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FriesFlavorChoices extends JFrame implements ActionListener {
    private int itemId;

    public FriesFlavorChoices(int id) {
        this.itemId = id;
        orderFrameConfig();
    }

    // Image Icons
    ImageIcon logo = new ImageIcon(getClass().getResource("/views/Images/logo.png"));
    ImageIcon confirmIcon = new ImageIcon(getClass().getResource("/views/Images/Confirm.png"));

    JLabel countLabel = new JLabel("Choose Flavor: ");

    // ComboBox for flavor choices
    JComboBox<String> flavorComboBox = new JComboBox<>(new String[] {
        "Plain", "Cheese", "Barbecue", "Sour Cream", "Salted"
    });

    // Button
    JButton confirmButton = new JButton();

    public void orderFrameConfig() {
        // Create a panel with white background
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Set blue-violet color
        Color blueViolet = new Color(115, 20, 226);

        // Label styling
        countLabel.setFont(new Font("Arial", Font.BOLD, 25));
        countLabel.setForeground(blueViolet);

        // ComboBox styling
        flavorComboBox.setPreferredSize(new Dimension(180, 50));
        flavorComboBox.setFont(new Font("Arial", Font.BOLD, 18));
        flavorComboBox.setForeground(Color.WHITE);
        flavorComboBox.setBackground(blueViolet);
        flavorComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        flavorComboBox.setFocusable(false);

        // Confirm button setup
        setupButton(confirmButton, confirmIcon);

        // Add to panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(countLabel, gbc);

        gbc.gridy = 1;
        mainPanel.add(flavorComboBox, gbc);

        gbc.gridy = 2;
        mainPanel.add(confirmButton, gbc);

        setContentPane(mainPanel);
        setSize(300, 230);
        setResizable(false);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            String selectedFlavor = (String) flavorComboBox.getSelectedItem();
            this.dispose(); // Close the frame
            OrderFrame orderFrame = new OrderFrame(itemId);
            orderFrame.setVisible(true);
        }
    }

    private void setupButton(JButton button, ImageIcon icon) {
        button.setIcon(icon);
        button.setBorder(new EmptyBorder(0, 0, 0, 0));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.addActionListener(this);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
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

        RescaleOp rescaleOp = new RescaleOp(0.7f, 0, null); // Darken 30%
        rescaleOp.filter(bufferedImage, bufferedImage);

        g2d.dispose();
        return new ImageIcon(bufferedImage);
    }
}
