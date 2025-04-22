package views.event_driven_project;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Admin
 */
public class OrderFrame extends JFrame implements ActionListener {
    private int ItemId;
    public OrderFrame(int id) {
        this.ItemId = id;
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
        // Handle button clicks or other events
        if(e.getSource() == confirmButton){
            this.dispose();
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
