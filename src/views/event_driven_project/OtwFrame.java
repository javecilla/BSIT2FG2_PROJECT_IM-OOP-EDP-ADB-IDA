package views.event_driven_project;

import controllers.CourierController;
import helpers.Response;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import models.Courier;
import static views.event_driven_project.Payment.CART_ITEM_CONTROLLER;

public class OtwFrame extends JFrame implements ActionListener {
    EventController controller;
    protected static final CourierController courierController = new CourierController();
    private Courier selectedCourier;

    public OtwFrame(EventController controller) {
        this.controller = controller;
        otwFrameConfig();
        this.selectedCourier = getRandomCourier(); // select courier on frame creation
    }

    private ImageIcon otwIcon = new ImageIcon(getClass().getResource("/views/Images/otw.png"));
    JLabel background = new JLabel(otwIcon);
    JButton confirm = new JButton("Confirm");

    public void otwFrameConfig() {
        confirm.setBounds(otwIcon.getIconWidth() / 2 - 68, otwIcon.getIconHeight() - 120, 120, 40);
        confirm.setBackground(new Color(95, 71, 214));
        confirm.setForeground(Color.white);
        confirm.setFont(new Font("Poppins", Font.BOLD, 20));
        confirm.addActionListener(this);
        background.add(confirm);

        this.add(background);
        this.setSize(otwIcon.getIconWidth(), otwIcon.getIconHeight());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm) {
            if (selectedCourier != null) {
                Response<Boolean> response = CART_ITEM_CONTROLLER.checkoutCartItem(controller.getUser().getUserId(), getSelectedCourier().getRiderId());
                if (response.isSuccess()) {
                    //System.out.println(response.getMessage());    
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
                    int count = controller.menuFrame.countUserOrders(controller.getUser().getUserId());
                    controller.setOrderCount(count);
                } else {
                    //System.out.println("Error: " + response.getMessage());
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Success", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No available courier.", "No Available", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println("No available courier.");
            }
            controller.manageCourierFrame.loadCouriersData("all");
            controller.frameErase();
            controller.showMenuFrame(this);
            controller.menuFrame.setButtonVisibility();
            controller.menuFrame.setCartItemCount();
            controller.drinksFrame.setCartItemCount();
            controller.friesFrame.setCartItemCount();
            controller.riceMealsFrame.setCartItemCount();
            controller.sandwichesFrame.setCartItemCount();
        }
    }

    public Courier getRandomCourier() {
        Response<List<Courier>> response = courierController.getAllCouriers();
        if (response.isSuccess()) {
            List<Courier> allCouriers = response.getData();
            List<Courier> availableCouriers = new ArrayList<>();

            for (Courier courier : allCouriers) {
                if ("available".equalsIgnoreCase(courier.getStatus())) {
                    availableCouriers.add(courier);
                }
            }

            if (!availableCouriers.isEmpty()) {
                int randomIndex = ThreadLocalRandom.current().nextInt(availableCouriers.size());
                return availableCouriers.get(randomIndex);
            }
        }
        return null;
    }

    public Courier getSelectedCourier() {
        return selectedCourier;
    }
}
