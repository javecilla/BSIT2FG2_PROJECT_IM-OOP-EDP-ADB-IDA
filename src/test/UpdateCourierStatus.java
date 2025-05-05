package test;

import controllers.CourierController;
import helpers.Response;
import models.Courier;

public class UpdateCourierStatus {
    protected static final CourierController COURIER_CONTROLLER = new CourierController();
    
    public static void main(String[] args) {
        int courierId = 21;
        String status = "Unavailable"; //Available
                
        Response<Courier> response = COURIER_CONTROLLER.updateCourierStatus(
            courierId,    
            status
        );
        if (response.isSuccess()) {
            System.out.println(response.getMessage());
        } else {
            System.out.println(response.getMessage());
        }
    }
    
}
