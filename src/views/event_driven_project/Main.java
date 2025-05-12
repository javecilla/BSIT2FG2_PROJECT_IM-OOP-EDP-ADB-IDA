/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views.event_driven_project;

import core.Session;
import models.User;

/**
 *
 * @author Admin
 */
public class Main {
    public static void main(String[] args) {
        // Create the controller
        EventController controller = new EventController();
        
        // Make the home frame visible to start the application
        controller.homeFrame.setVisible(true);
    }
}