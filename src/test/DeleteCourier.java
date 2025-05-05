package test;

import controllers.CourierController;
import helpers.Response;
import models.Courier;

public class DeleteCourier {
    protected static final CourierController COURIER_CONTROLLER = new CourierController();
    
    public static void main(String[] args) {
        int courierId = 21;                
        Response<String> deletedResponse = COURIER_CONTROLLER.deleteCourier(courierId);
        if (deletedResponse.isSuccess()) {
            System.out.println(deletedResponse.getMessage());
        } else {
            System.out.println(deletedResponse.getMessage());
        }
    }
    
}


