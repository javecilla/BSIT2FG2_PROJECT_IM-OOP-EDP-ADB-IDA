package controllers;

import enums.CourierStatus;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import models.Courier;
import services.CourierService;
import helpers.Response;
import helpers.Text;
import interfaces.IOperatorsValidators;

public class CourierController implements IOperatorsValidators<Courier> {
    private final CourierService courierService;
    
    public CourierController() {
        this.courierService = new CourierService();
    }
    
    // Create a new courier
    public Response<Courier> addCourier(String firstName, String LastName, String company, String contact) {     
        Courier courier = new Courier();
        courier.setFirstName(firstName);
        courier.setLastName(LastName);
        courier.setCompany(company);
        courier.setContactNumber(contact);
        courier.setStatus(Text.capitalizeFirstLetterInString(CourierStatus.AVAILABLE.name()));
        
        Response<Courier> validationResponse = validateCreate(courier);
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }
        
        // Proceed with creation if validation passes
        try { 
            boolean isCreated = courierService.create(courier);
            
            return (isCreated) 
                ? Response.success("Courier created successfully!", courier)
                : Response.error("Failed to create courier.");

        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    // Get courier by ID
    public Response<Courier> getCourierById(int id) {
        try {
            Courier courier = courierService.getById(id);
            
            if(courier == null) {
                return Response.error("Courier not found with ID: " + id);
            }
            
            return Response.success("Courier retrieved successfully", courier);
        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    // Get all couriers
    public Response<List<Courier>> getAllCouriers() {
        try {
            List<Courier> couriers = courierService.getAll();
            
            if(couriers == null || couriers.isEmpty()) {
                return Response.success("No couriers found", Collections.emptyList());
            }
            
            return Response.success("Couriers retrieved successfully", couriers);
        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    // Get courier list by status either (Available or Unavailable)
    public Response<List<Courier>> getCouriersByStatus(String status) {
        try {
            List<Courier> couriers = courierService.getByStatus(status);
            
            if(couriers == null || couriers.isEmpty()) {
                return Response.success("No couriers found", Collections.emptyList());
            }
            
            return Response.success("Couriers retrieved successfully", couriers);
        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    public Response<Courier> updateCourier(int courierId, String firstName, String LastName, String company, String contact) {     
        Courier courier = new Courier();
        courier.setRiderId(courierId);
        courier.setFirstName(firstName);
        courier.setLastName(LastName);
        courier.setCompany(company);
        courier.setContactNumber(contact);
        
        Response<Courier> validationResponse = validateUpdate(courier);
        if (!validationResponse.isSuccess()) {
            return validationResponse; 
        }

        try {
            Courier existingCourier = courierService.getById(courier.getRiderId());
            if (existingCourier == null) return Response.error("Courier not found with ID: " + courier.getRiderId());
            
            boolean isUpdated = courierService.update(courier);
            return (isUpdated)
                    ? Response.success("Courier updated successfully", courier)
                    : Response.error("Failed to update courier");

        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }

    
    // Update courier status
    public Response<Courier> updateCourierStatus(int riderId, String newStatus) {
        try {
            
            Courier courier = courierService.getById(riderId);

            
            if (courier == null) {
                return Response.error("Courier not found with ID: " + riderId);
            }

            
            String currentStatus = courier.getStatus();
            if (newStatus.equalsIgnoreCase(currentStatus)) {
                return Response.error("New status is the same as the current status.");
            }

            
            boolean isUpdated = courierService.updateStatus(riderId, newStatus);

            
            if (isUpdated) {
                return Response.success("Courier status updated successfully.");
            } else {
                return Response.error("Failed to update courier status.");
            }

        } catch (SQLException e) {
            return Response.error("Error updating courier status: " + e.getMessage());
        }
    }

    
    // Delete courier
    public Response<String> deleteCourier(int id) {
        // Validate delete operation
        Response<Boolean> validationResponse = validateDelete(id);
        if(!validationResponse.isSuccess()) {
            return Response.error("Validation failed: " + validationResponse.getMessage());
        }
            
        try {
            // Check if the courier exists 
            Courier existingCourier = courierService.getById(id);
            if(existingCourier == null) {
                return Response.error("Courier not found with ID: " + id);
            }
            
            boolean isDeleted = courierService.delete(id);
            
            return (isDeleted) 
                ? Response.success("Courier deleted successfully", null)
                : Response.error("Failed to delete courier");

        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }

    @Override
    public Response<Courier> validateCreate(Courier courier) {
        if(courier.getFirstName() == null || courier.getFirstName().trim().isEmpty()) {
            return Response.error("First name cannot be empty");
        }
        
        if(courier.getLastName() == null || courier.getLastName().trim().isEmpty()) {
            return Response.error("Last name cannot be empty");
        }
        
        if(courier.getCompany() == null || courier.getCompany().trim().isEmpty()) {
            return Response.error("Company cannot be empty");
        }
        
        if(courier.getContactNumber() == null || courier.getContactNumber().trim().isEmpty()) {
            return Response.error("Contact number cannot be empty");
        }
        
        //if(courier.getStatus() == null || courier.getStatus().trim().isEmpty()) {
            //return Response.error("Status cannot be empty");
        //}
        
        return Response.success("Validation passed", courier);
    }

    @Override
    public Response<Courier> validateUpdate(Courier courier) {
        if(courier.getRiderId() <= 0) {
            return Response.error("Courier ID must be valid!");
        }
        
        return Response.success("Validation passed", courier);
    }

    @Override
    public Response<Boolean> validateDelete(int id) {
        if(id <= 0) {
            return Response.error("Courier ID must be valid!");
        }
        return Response.success("Validation passed", true);
    }
}
