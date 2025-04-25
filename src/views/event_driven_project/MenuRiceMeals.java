package views.event_driven_project;

import config.MSSQLConnection;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.image.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuRiceMeals extends JFrame implements ActionListener {
    private EventController controller;
        public MenuRiceMeals(EventController eventController){
            this.controller = eventController;
            riceMealFrameConfig();
            setCartItemCount();
    }
    // Image Icons
    ImageIcon logo = new ImageIcon(getClass().getResource("/views/Images/logo.png"));
    ImageIcon oneBSteakIcon = new ImageIcon(getClass().getResource("/views/Images/ricemeals/1pbsteak.png"));
    ImageIcon twoBSteakIcon = new ImageIcon(getClass().getResource("/views/Images/ricemeals/2p bsteak.png"));
    ImageIcon baconAndEggIcon = new ImageIcon(getClass().getResource("/views/Images/ricemeals/bac n egg rice.png"));
    ImageIcon hamAndEggIcon = new ImageIcon(getClass().getResource("/views/Images/ricemeals/ham n egg rice.png"));
    ImageIcon hotdogAndEggIcon = new ImageIcon(getClass().getResource("/views/Images/ricemeals/hotdognegg.png"));
    ImageIcon nuggetsIcon = new ImageIcon(getClass().getResource("/views/Images/ricemeals/nuggs.png"));
    ImageIcon samgyupRiceIcon = new ImageIcon(getClass().getResource("/views/Images/ricemeals/sang.png"));
    ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/views/Images/ricemeals/menu-RiceMeals.png"));
    
    ImageIcon banner = new ImageIcon(getClass().getResource("/views/Images/categoryMenu/banner.gif"));
    
    ImageIcon homeButtonIcon = new ImageIcon(getClass().getResource("/views/Images/homepage/home button.png"));
    ImageIcon browseButtonIcon = new ImageIcon(getClass().getResource("/views/Images/homepage/browse button.png"));
    ImageIcon cartButtonIcon = new ImageIcon(getClass().getResource("/views/Images/shopping Basket.png"));

    JLabel bannerLabel = new JLabel(banner);
    JLabel cartLabel = new JLabel();
    // Buttons
    JButton oneSteakButton = new JButton();
    JButton twoSteakButton = new JButton();
    JButton baconEggButton = new JButton();
    JButton hamEggButton = new JButton();
    JButton hotdogEggButton = new JButton();
    JButton nuggetsButton = new JButton();
    JButton samgyupButton = new JButton();
    JButton homeButton = new JButton();
    JButton browseButton = new JButton();
    JButton cartButton = new JButton();

    // Panels
    JPanel gridPanel = new JPanel();
    JPanel navPanel = new JPanel();

    public void riceMealFrameConfig() {

        bannerLabel.setBounds(0, 0, backgroundIcon.getIconWidth(), banner.getIconHeight());
        
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPanel.setLayout(null);
        this.setContentPane(contentPanel);

        // Use GridLayout for better gap control and visibility
        gridPanel.setLayout(new GridLayout(0, 2, 0, 0));
        gridPanel.setOpaque(false);
        
        navPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        navPanel.setOpaque(false);
        navPanel.setBounds(0, 160, backgroundIcon.getIconWidth()-10, homeButtonIcon.getIconHeight());
        
        cartButton.setOpaque(false);
        cartButton.setBounds(backgroundIcon.getIconWidth()-150, 222, cartButtonIcon.getIconWidth(), cartButtonIcon.getIconHeight());

        setupButton(oneSteakButton, oneBSteakIcon);
        setupButton(twoSteakButton, twoBSteakIcon);
        setupButton(baconEggButton, baconAndEggIcon);
        setupButton(hamEggButton, hamAndEggIcon);
        setupButton(hotdogEggButton, hotdogAndEggIcon);
        setupButton(nuggetsButton, nuggetsIcon);
        setupButton(samgyupButton, samgyupRiceIcon);
        setupButton(homeButton, homeButtonIcon);
        setupButton(browseButton, browseButtonIcon);
        setupButton(cartButton, cartButtonIcon);
        
        cartLabel.setFont(new Font("Arial Black",Font.BOLD,30));
        cartLabel.setForeground(Color.ORANGE);
        
        cartButton.add(cartLabel);

        // Add buttons to the panel
        gridPanel.add(oneSteakButton);
        gridPanel.add(twoSteakButton);
        gridPanel.add(baconEggButton);
        gridPanel.add(hamEggButton);
        gridPanel.add(hotdogEggButton);
        gridPanel.add(nuggetsButton);
        gridPanel.add(samgyupButton);
        
        navPanel.add(homeButton);
        navPanel.add(browseButton);

        // Scroll pane setup
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBounds(0, 320, backgroundIcon.getIconWidth() - 15, backgroundIcon.getIconHeight() - 400);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Custom Scrollbar Design
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.LIGHT_GRAY;  
                this.trackColor = new Color(0, 0, 0, 0);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createArrowButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createArrowButton();
            }

            private JButton createArrowButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                button.setFocusable(false);
                return button;
            }
        });

        scrollPane.getVerticalScrollBar().setUnitIncrement(20); // small scroll
        scrollPane.getVerticalScrollBar().setBlockIncrement(100); // bigger scroll

        contentPanel.add(scrollPane);
        contentPanel.add(bannerLabel);
        contentPanel.add(cartButton);
        contentPanel.add(navPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        this.setLocationRelativeTo(null);
        this.setTitle("Rice Meals Menu");
        this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Example: handle button clicks here if needed
        if (e.getSource() == homeButton) {
            controller.showHomeFrame(this);
        }
        if (e.getSource() == browseButton) {
            controller.showMenuFrame(this);
        }
        if (e.getSource() == oneSteakButton) {
            OrderFrame orderFrame = new OrderFrame(0);
            orderFrame.setVisible(true);
        }
        if (e.getSource() == twoSteakButton) {
            OrderFrame orderFrame = new OrderFrame(0);
            orderFrame.setVisible(true);
        }
        if (e.getSource() == baconEggButton) {
            OrderFrame orderFrame = new OrderFrame(0);
            orderFrame.setVisible(true);
        }
        if (e.getSource() == hamEggButton) {
            OrderFrame orderFrame = new OrderFrame(0);
            orderFrame.setVisible(true);
        }
        if (e.getSource() == hotdogEggButton) {
            OrderFrame orderFrame = new OrderFrame(0);
            orderFrame.setVisible(true);
        }
        if (e.getSource() == nuggetsButton) {
            OrderFrame orderFrame = new OrderFrame(0);
            orderFrame.setVisible(true);
        }
        if (e.getSource() == samgyupButton) {
            OrderFrame orderFrame = new OrderFrame(0);
            orderFrame.setVisible(true);
        }
        if(e.getSource() == cartButton){
            controller.showCartFrame(this);
        }
    }
    
    public void setCartItemCount(){
        cartLabel.setText(getCartItemCount() + "");
    }
    
    public int getCartItemCount() {
        String sql = "SELECT COUNT(*) AS total FROM CART_ITEM";
        int count = 0;

        try (Connection conn = MSSQLConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt("total"); // or rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // or log the error
        }
        return count;
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

    // Method to darken the image icon
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
