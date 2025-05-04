/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views.event_driven_project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Admin
 */
public class OtwFrame extends JFrame implements ActionListener{
    EventController controller;
    public OtwFrame(EventController controller){
        this.controller = controller;
        otwFrameConfig();
    }
    
    private ImageIcon otwIcon = new ImageIcon(getClass().getResource("/views/Images/otw.png"));
    
    JLabel background = new JLabel(otwIcon);
    JButton confirm = new JButton("Confirm");
    
    public void otwFrameConfig(){
        confirm.setBounds(otwIcon.getIconWidth()/2 - 68, otwIcon.getIconHeight() - 120, 120, 40);
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
        if(e.getSource() == confirm){
            controller.showMenuFrame(this);
        }
    }
}
