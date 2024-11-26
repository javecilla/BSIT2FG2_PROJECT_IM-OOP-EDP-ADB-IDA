package models;
/**
 * Represents the `Customer` table in the database.
 * Extends the `User` class to include fields specific to customers.
 * Includes attributes for customer ID and status (e.g., active, inactive).
 */
public class Customer extends User {
    private int id;
    private String status; // Status of the customer (e.g., active, inactive)
    
    public Customer() {
        super();
    }
    
    public Customer(int customerId, String customerStatus) {
        super();
        this.id = customerId;
        this.status = customerStatus;
    }
    
    public Customer(int customerId, String customerStatus, User user) {
        super(
            user.getUserId(), 
            user.getUsername(),
            user.getPassword(),
            user.getUserRole(),
            user.getEmail(),
            user.getContactNumber()
        );
        this.id = customerId;
        this.status = customerStatus;
    }
    
    public int getCustomerId() {
        return id;
    }
    
    public void setCustomerId(int id) {
        this.id = id;
    }
    
    public String getCustomerStatus() {
        return status;
    }
    
    public void setCustomerStatus(String customerStatus) {
        this.status = customerStatus;
    }
    
    @Override
    public String display() {      
        return "Customer ID: " + getCustomerId() + "\nCustomer Status: " + getCustomerStatus() + "\nCustomer Info: \n\n" + super.display();
    }   
}