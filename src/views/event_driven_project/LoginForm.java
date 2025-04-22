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
public class LoginForm extends JFrame implements ActionListener{
    private EventController controller;
    public LoginForm(EventController eventController){
        this.controller = eventController;
        loginFrame();
    }
    //INITIALIZATION OF COMPONENTS
    
    
    //Image Icon
    ImageIcon logo = new ImageIcon(getClass().getResource("/views/Images/logo.png"));
    
    ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/views/Images/form/login-bg.png"));
    ImageIcon loginIcon = new ImageIcon(getClass().getResource("/views/Images/form/login.png"));
    ImageIcon registerIcon = new ImageIcon(getClass().getResource("/views/Images/form/register.png"));
    ImageIcon fieldIcon = new ImageIcon(getClass().getResource("/views/Images/form/fields.png"));
    
    //TextField
    RoundedTextField usernameField = new RoundedTextField(20, fieldIcon);
    RoundedPasswordField passwordField = new RoundedPasswordField(20, fieldIcon);

    
    //Labels
    JLabel usernameLabel = new JLabel("Email Address:");
    JLabel passwordLabel = new JLabel("Password:");
    
    //Buttons
    JButton loginButton = new JButton();
    JButton registerButton = new JButton();
    
    //Panels

   public void loginFrame() {
    // Panel with background image
    JPanel contentPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    };
    contentPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); // Padding

    // Setup buttons and fields
    setupButton(loginButton, loginIcon);
    setupButton(registerButton, registerIcon);
    setupField(usernameField);
    setupField(passwordField);

    // Transparent panel to hold components vertically
    JPanel formPanel = new JPanel();
    formPanel.setLayout(new GridBagLayout());  // Change this to GridBagLayout
    formPanel.setOpaque(false);

    // Add form components
    usernameLabel.setForeground(new Color(93, 48, 140));
    passwordLabel.setForeground(new Color(93, 48, 140));
    usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
    passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));

    // Set grid positions for labels (aligned left) and fields (centered)
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.WEST; // Align labels to the left
    formPanel.add(usernameLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.anchor = GridBagConstraints.CENTER; // Center username field
    formPanel.add(usernameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.anchor = GridBagConstraints.WEST; // Align password label to the left
    formPanel.add(passwordLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.anchor = GridBagConstraints.CENTER; // Center password field
    formPanel.add(passwordField, gbc);

    // Add buttons in the center
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2; // Make the buttons span across both columns
    gbc.anchor = GridBagConstraints.CENTER; // Center the buttons
    formPanel.add(loginButton, gbc);

    gbc.gridy = 5;  // Move to the next line for the register button
    formPanel.add(registerButton, gbc);

    // Center form panel in content panel
    gbc.gridx = 0;
    gbc.gridy = 0;
    contentPanel.add(formPanel, gbc);

    // Frame config
    this.setContentPane(contentPanel);
    this.setSize(backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setVisible(false);
}
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registerButton){
            controller.showRegisterFrame(this);
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


