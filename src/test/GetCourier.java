package test;

import controllers.CourierController;
import helpers.Response;
import models.Courier;

public class GetCourier {
    protected static final CourierController COURIER_CONTROLLER = new CourierController();
    
    public static void main(String[] args) { 
        int courierId = 3;
        Response<Courier> response = COURIER_CONTROLLER.getCourierById(courierId);
        if (response.isSuccess()) {
            Courier courier = response.getData();
            
            String fullName = "" + courier.getFirstName() + " " + courier.getLastName();

            System.out.println("Rider ID: " + courier.getRiderId());
            System.out.println("Name: " + fullName);
            System.out.println("Company: " + courier.getCompany());
            System.out.println("Contact Number: " + courier.getContactNumber());
        } else {
            System.out.println("Error: " + response.getMessage());
        }
    }
}

