/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views.event_driven_project;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.*;
import javax.swing.border.*;
/**
 *
 * @author Admin
 */
public class RegisterForm extends JFrame implements ActionListener{
    private EventController controller;
    public RegisterForm(EventController eventController){
        this.controller = eventController;
        registerFrame();
    }
    //INITIALIZATION OF COMPONENTS
    
    //Image Icon
    
    ImageIcon backgroundIcon1 = new ImageIcon(getClass().getResource("/views/Images/form/register-bg-pi.png"));
    ImageIcon loginIcon = new ImageIcon(getClass().getResource("/views/Images/form/login.png"));
    ImageIcon fieldIcon = new ImageIcon(getClass().getResource("/views/Images/form/fields.png"));
    ImageIcon nextIcon = new ImageIcon(getClass().getResource("/views/Images/form/next.png"));
    
    //TextField
    RoundedTextField usernameField = new RoundedTextField(20, fieldIcon);
    RoundedPasswordField passwordField = new RoundedPasswordField(20, fieldIcon);
    RoundedTextField firstNameField = new RoundedTextField(20, fieldIcon);
    RoundedTextField lastNameField = new RoundedTextField(20, fieldIcon);
    RoundedTextField contactField = new RoundedTextField(20, fieldIcon);
    RoundedTextField emailField = new RoundedTextField(20, fieldIcon);
    
    //Labels
    JLabel usernameLabel = new JLabel("Username:");
    JLabel passwordLabel = new JLabel("Password:");
    JLabel firstNameLabel = new JLabel("First name:");
    JLabel lastNameLabel = new JLabel("Last name:");
    JLabel contactLabel = new JLabel("Contact number:");
    JLabel emailLabel = new JLabel("Email:");
    JLabel contactErrorLabel = new JLabel("Invalid Contact Number. It must begin with 09 and 11 digits.");
    JLabel emailErrorLabel = new JLabel("Invalid Email Address. Must have @ and like .com, etc...");

    //Buttons
    JButton loginButton = new JButton();
    JButton nextButton = new JButton();
    
    //Panels

   public void registerFrame() {
    // Panel with background image
    JPanel contentPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundIcon1.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    };
    contentPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 10, 0, 10); // Padding

    // Setup buttons and fields
    setupButton(loginButton, loginIcon);
    setupButton(nextButton, nextIcon);
    
    setupField(usernameField);
    setupField(passwordField);
    setupField(firstNameField);
    setupField(lastNameField);
    setupField(contactField);
    setupField(emailField);

    // Transparent panel to hold components vertically
    JPanel formPanel = new JPanel();
    formPanel.setLayout(new GridBagLayout());  // Change this to GridBagLayout
    formPanel.setOpaque(false);

    // Add form components
    usernameLabel.setForeground(new Color(93, 48, 140));
    passwordLabel.setForeground(new Color(93, 48, 140));
    firstNameLabel.setForeground(new Color(93, 48, 140));
    lastNameLabel.setForeground(new Color(93, 48, 140));
    contactLabel.setForeground(new Color(93, 48, 140));
    emailLabel.setForeground(new Color(93, 48, 140));
    
    usernameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    passwordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    firstNameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    lastNameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    contactLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    emailLabel.setFont(new Font("Arial", Font.PLAIN, 12));

    // Set grid positions for labels (aligned left) and fields (centered)
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.WEST; // Align labels to the left
    formPanel.add(firstNameLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.anchor = GridBagConstraints.CENTER; // Center username field
    formPanel.add(firstNameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.anchor = GridBagConstraints.WEST; // Align password label to the left
    formPanel.add(lastNameLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.anchor = GridBagConstraints.CENTER; // Center password field
    formPanel.add(lastNameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.anchor = GridBagConstraints.WEST; 
    formPanel.add(usernameLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.anchor = GridBagConstraints.CENTER; 
    formPanel.add(usernameField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 6;
    gbc.anchor = GridBagConstraints.WEST; 
    formPanel.add(emailLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 7;
    gbc.anchor = GridBagConstraints.CENTER; 
    formPanel.add(emailField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 8;
    gbc.anchor = GridBagConstraints.CENTER; 
    formPanel.add(emailErrorLabel, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 9;
    gbc.anchor = GridBagConstraints.WEST; 
    formPanel.add(contactLabel, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 10;
    gbc.anchor = GridBagConstraints.CENTER; 
    formPanel.add(contactField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 11;
    gbc.anchor = GridBagConstraints.CENTER; 
    formPanel.add(contactErrorLabel, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 12;
    gbc.anchor = GridBagConstraints.WEST; 
    formPanel.add(passwordLabel, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 13;
    gbc.anchor = GridBagConstraints.CENTER; 
    formPanel.add(passwordField, gbc);
    
    // Add buttons in the center
    gbc.gridx = 0;
    gbc.gridy = 14;
    gbc.gridwidth = 2; // Make the buttons span across both columns
    gbc.anchor = GridBagConstraints.CENTER; // Center the buttons
    formPanel.add(nextButton, gbc);

    // Center form panel in content panel
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(135, 0, 0, 0); // Move it down by 80 pixels
    contentPanel.add(formPanel, gbc);

    nextButton.setEnabled(false);
    contactErrorLabel.setVisible(false);
    emailErrorLabel.setVisible(false);
    
    firstNameField.addFocusListener(new FocusAdapter(){
        public void focusLost(FocusEvent e) {
            checkAllFieldsValid();
        }
    });
    
    lastNameField.addFocusListener(new FocusAdapter(){
        public void focusLost(FocusEvent e) {
            checkAllFieldsValid();
        }
    });
    
    passwordField.addFocusListener(new FocusAdapter(){
        public void focusLost(FocusEvent e) {
            checkAllFieldsValid();
        }
    });
    
    usernameField.addFocusListener(new FocusAdapter(){
        public void focusLost(FocusEvent e) {
            checkAllFieldsValid();
        }
    });
    
    contactField.addFocusListener(new FocusAdapter() {
    @Override
    public void focusLost(FocusEvent e) {
        String contact = contactField.getText();
        if (!contact.matches("^09\\d{9}$")) {
            contactErrorLabel.setForeground(Color.red);
            contactErrorLabel.setVisible(true);
            nextButton.setEnabled(false);
        } else {
            contactErrorLabel.setVisible(false);
            checkAllFieldsValid(); // enable button if all fields are valid
        }
        }
    });
    
    emailField.addFocusListener(new FocusAdapter() {
    @Override
    public void focusLost(FocusEvent e) {
        String contact = emailField.getText();
        if (!contact.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            emailErrorLabel.setForeground(Color.red);
            emailErrorLabel.setVisible(true);
            nextButton.setEnabled(false);
        } else {
            emailErrorLabel.setVisible(false);
            checkAllFieldsValid(); // enable button if all fields are valid
        }
        }
    });

    nextButton.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            if (!nextButton.isEnabled()) {
                JOptionPane.showMessageDialog(null, "Please complete all the informations to proceed", "Cannot Proceed", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    });
    
    // Frame config
    this.setContentPane(contentPanel);
    this.setSize(backgroundIcon1.getIconWidth(), backgroundIcon1.getIconHeight() + 50);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setVisible(false);
}
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == nextButton){
            //controller.showRegisterFrame2(this);
            String password = new String(passwordField.getPassword());
            RegisterForm2 regForm2 = new RegisterForm2(firstNameField.getText(),
                                                        lastNameField.getText(),
                                                        usernameField.getText(),
                                                        contactField.getText(),
                                                        password, 
                                                        emailField.getText(),
                                                        controller);
            regForm2.setVisible(true);
            this.setVisible(false);
        }
    }

    private void checkAllFieldsValid() {
    boolean allFilled = !firstNameField.getText().trim().isEmpty() &&
                        !lastNameField.getText().trim().isEmpty() &&
                        !usernameField.getText().trim().isEmpty() &&
                        !String.valueOf(passwordField.getPassword()).trim().isEmpty() &&
                        contactField.getText().matches("^09\\d{9}$") &&
                        emailField.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    nextButton.setEnabled(allFilled);
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
