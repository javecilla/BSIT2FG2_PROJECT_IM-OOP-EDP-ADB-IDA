package test;

import controllers.CourierController;
import helpers.Response;
import models.Courier;

public class UpdateCourier {
    protected static final CourierController COURIER_CONTROLLER = new CourierController();
    
    public static void main(String[] args) {
        int courierId = 21;
        String firstName = "Updated";
        String LastName = "test1 Updated";
        String company = "Lala Updated"; 
        String contact = "09213984666";
                
        Response<Courier> response = COURIER_CONTROLLER.updateCourier(
            courierId,    
            firstName,
            LastName,
            company,
            contact
        );
        if (response.isSuccess()) {
            System.out.println(response.getMessage());
        } else {
            System.out.println(response.getMessage());
        }
    }
    
}
