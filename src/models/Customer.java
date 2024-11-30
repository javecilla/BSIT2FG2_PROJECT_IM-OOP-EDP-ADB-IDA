package models;
/**
 * Represents the `Customer` table in the database.
 * Similarly, the Customer class extends User to represent customers, introducing attributes like status to 
 * define their account state (e.g., active or inactive). This approach ensures that customer-specific 
 * attributes and behaviors are separated while still inheriting common user account functionalities.
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