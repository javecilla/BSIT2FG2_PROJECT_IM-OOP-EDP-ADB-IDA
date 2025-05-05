/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views.event_driven_project;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Admin
 */
public class RegisterForm2 extends JFrame implements ActionListener{
    private EventController controller;
    public RegisterForm2(EventController eventController){
        this.controller = eventController;
        registerFrame2();
    }
    
    ImageIcon backgroundIcon2 = new ImageIcon(getClass().getResource("/views/Images/form/register-bg-address.png"));
    ImageIcon registerIcon = new ImageIcon(getClass().getResource("/views/Images/form/register.png"));
    ImageIcon fieldIcon = new ImageIcon(getClass().getResource("/views/Images/form/fields.png"));
    
    RoundedTextField houseNumberField = new RoundedTextField(20, fieldIcon);
    RoundedTextField streetField = new RoundedTextField(20, fieldIcon);
    RoundedTextField regionField = new RoundedTextField(20, fieldIcon);
    RoundedTextField provinceField = new RoundedTextField(20, fieldIcon);
    RoundedTextField cityField = new RoundedTextField(20, fieldIcon);
    RoundedTextField barangayField = new RoundedTextField(20, fieldIcon);
    
    JLabel houseNumberLabel = new JLabel("Houser No.:");
    JLabel streetLabel = new JLabel("Street:");
    JLabel barangayLabel = new JLabel("Barangay:");
    JLabel regionLabel = new JLabel("Region");
    JLabel provinceLabel = new JLabel("Province:");
    JLabel cityLabel = new JLabel("City/Municipality:");
    
    JButton registerButton = new JButton();
    
public void registerFrame2() {
    // Panel with background image
    JPanel contentPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundIcon2.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    };
    contentPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 10, 0, 10); // Padding

    // Setup buttons and fields
    setupButton(registerButton, registerIcon);
    
    setupField(houseNumberField);
    setupField(streetField);
    setupField(barangayField);
    setupField(regionField);
    setupField(provinceField);
    setupField(cityField);

    // Transparent panel to hold components vertically
    JPanel formPanel = new JPanel();
    formPanel.setLayout(new GridBagLayout());  // Change this to GridBagLayout
    formPanel.setOpaque(false);

    // Add form components
    houseNumberLabel.setForeground(new Color(93, 48, 140));
    streetLabel.setForeground(new Color(93, 48, 140));
    barangayLabel.setForeground(new Color(93, 48, 140));
    regionLabel.setForeground(new Color(93, 48, 140));
    provinceLabel.setForeground(new Color(93, 48, 140));
    cityLabel.setForeground(new Color(93, 48, 140));
    
    houseNumberLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    streetLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    barangayLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    regionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    provinceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    cityLabel.setFont(new Font("Arial", Font.PLAIN, 12));

    // Set grid positions for labels (aligned left) and fields (centered)
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.WEST; // Align labels to the left
    formPanel.add(houseNumberLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.anchor = GridBagConstraints.CENTER; // Center username field
    formPanel.add(houseNumberField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.anchor = GridBagConstraints.WEST; // Align password label to the left
    formPanel.add(streetLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.anchor = GridBagConstraints.CENTER; // Center password field
    formPanel.add(streetField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.anchor = GridBagConstraints.WEST; // Align password label to the left
    formPanel.add(barangayLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.anchor = GridBagConstraints.CENTER; // Center password field
    formPanel.add(barangayField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 6;
    gbc.anchor = GridBagConstraints.WEST; 
    formPanel.add(regionLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 7;
    gbc.anchor = GridBagConstraints.CENTER; 
    formPanel.add(regionField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 8;
    gbc.anchor = GridBagConstraints.WEST; 
    formPanel.add(provinceLabel, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 9;
    gbc.anchor = GridBagConstraints.CENTER; 
    formPanel.add(provinceField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 10;
    gbc.anchor = GridBagConstraints.WEST; 
    formPanel.add(cityLabel, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 11;
    gbc.anchor = GridBagConstraints.CENTER; 
    formPanel.add(cityField, gbc);
    
    // Add buttons in the center
    gbc.gridx = 0;
    gbc.gridy = 12;
    gbc.gridwidth = 2; // Make the buttons span across both columns
    gbc.anchor = GridBagConstraints.CENTER; // Center the buttons
    formPanel.add(registerButton, gbc);

    // Center form panel in content panel
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(120, 0, 0, 0); // Move it down by pixels
    contentPanel.add(formPanel, gbc);

    registerButton.setEnabled(false);
    
    houseNumberField.addFocusListener(new FocusAdapter(){
        public void focusLost(FocusEvent e) {
            checkAllFieldsValid();
        }
    });
    streetField.addFocusListener(new FocusAdapter(){
        public void focusLost(FocusEvent e) {
            checkAllFieldsValid();
        }
    });
    barangayField.addFocusListener(new FocusAdapter(){
        public void focusLost(FocusEvent e) {
            checkAllFieldsValid();
        }
    });
    regionField.addFocusListener(new FocusAdapter(){
        public void focusLost(FocusEvent e) {
            checkAllFieldsValid();
        }
    });
    provinceField.addFocusListener(new FocusAdapter(){
        public void focusLost(FocusEvent e) {
            checkAllFieldsValid();
        }
    });
    cityField.addFocusListener(new FocusAdapter(){
        public void focusLost(FocusEvent e) {
            checkAllFieldsValid();
        }
    });
    
    registerButton.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            if (!registerButton.isEnabled()) {
                JOptionPane.showMessageDialog(null, "Please complete your address to proceed", "Cannot Proceed", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    });
    
    // Frame config
    this.setContentPane(contentPanel);
    this.setSize(backgroundIcon2.getIconWidth(), backgroundIcon2.getIconHeight()+100);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registerButton){
            controller.showHomeFrame(this);
        }
    }
    
    private void checkAllFieldsValid() {
        boolean allFilled = !houseNumberField.getText().trim().isEmpty() &&
                            !streetField.getText().trim().isEmpty() &&
                            !regionField.getText().trim().isEmpty() &&
                            !provinceField.getText().trim().isEmpty() &&
                            !cityField.getText().trim().isEmpty();

        registerButton.setEnabled(allFilled);
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
        
private void setupField(JTextField field) {
    field.setPreferredSize(new Dimension(fieldIcon.getIconWidth(), fieldIcon.getIconHeight()));
    field.setMaximumSize(new Dimension(fieldIcon.getIconWidth(), fieldIcon.getIconHeight()));
    field.setMinimumSize(new Dimension(fieldIcon.getIconWidth(), fieldIcon.getIconHeight()));
    field.setForeground(Color.white);

    field.setFont(new Font("Arial", Font.PLAIN, 18));
    field.setHorizontalAlignment(JTextField.CENTER);
    field.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Add focus effect
    field.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            field.repaint(); // optional if you want to animate or add glow later
        }

        @Override
        public void focusLost(FocusEvent e) {
            field.repaint();
        }
    });
}

}
class RoundedTextField extends JTextField {
    private float opacity = 0.1f;
    private int arc = 28;
    private ImageIcon backgroundImage;

    public RoundedTextField(int columns, ImageIcon backgroundImage) {
    super(columns);
    this.backgroundImage = backgroundImage;
    setOpaque(false);
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
}


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background image
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        } else {
            g2.setColor(new Color(255, 255, 255, (int)(255 * opacity)));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        }

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        g2.dispose();
    }
}

class RoundedPasswordField extends JPasswordField {
    private float opacity = 0.1f;
    private int arc = 28;
    private ImageIcon backgroundImage;

    public RoundedPasswordField(int columns, ImageIcon backgroundImage) {
        super(columns);
        this.backgroundImage = backgroundImage;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background image if available
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        } else {
            g2.setColor(new Color(255, 255, 255, (int)(255 * opacity)));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        }

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        g2.dispose();
    }
}



