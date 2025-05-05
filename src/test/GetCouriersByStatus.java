package test;

import controllers.CourierController;
import helpers.Response;
import java.util.List;
import models.Courier;

public class GetCouriersByStatus {
    protected static final CourierController COURIER_CONTROLLER = new CourierController();
    
    public static void main(String[] args) {    
        String status = "Available";
        Response<List<Courier>> response = COURIER_CONTROLLER.getCouriersByStatus(status);
        if (response.isSuccess()) {
            List<Courier> couriers = response.getData();
            
            System.out.printf("\n%-10s %-30s %-10s %-10s %-10s%n", "Rider ID", "Name", "Company", "Contact", "Status");
            if(couriers.isEmpty()) {
                System.out.println("No record is found.");
            }
            for (Courier courier : couriers) {
                String fullName = "" + courier.getFirstName() + " " + courier.getLastName();
                System.out.printf("%-10s %-30s %-10s %-10s %-10s%n", 
                    courier.getRiderId(),
                    fullName,
                    courier.getCompany(),
                    courier.getContactNumber(),
                    courier.getStatus()
                );
            }
            System.out.println("Total Records: " + couriers.size());
        } else {
            System.out.println("Error: " + response.getMessage());
        }
    }
}