package test;

import controllers.CourierController;
import helpers.Response;
import models.Courier;

public class CreateCourier {
    protected static final CourierController COURIER_CONTROLLER = new CourierController();
    
    public static void main(String[] args) {
        String firstName = "test1";
        String LastName = "test1";
        String company = "Lala Move"; 
        String contact = "09213984712";
                
        Response<Courier> response = COURIER_CONTROLLER.addCourier(
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
