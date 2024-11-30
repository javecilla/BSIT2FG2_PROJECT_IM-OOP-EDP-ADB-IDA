package models;
/**
 * Represents the `Courier` table in the database.
 * The Courier class represents the riders responsible for delivering food to customers. It includes 
 * attributes like riderId, firstName, lastName, company, contactNumber, and status, encapsulating 
 * essential details about the delivery personnel.
 */
public class Courier {
    private int riderId;
    private String firstName;
    private String lastName;
    private String company;
    private String contactNumber;
    private String status;
    
    //Constructors
    public Courier() {}
    
    public Courier(int riderId, String firstName, String lastName, String company, String contactNumber, String status) {
        this.riderId = riderId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.contactNumber = contactNumber;
        this.status = status;
    }
    
    //Getters and Setters in all fields
    public int getRiderId() {
        return riderId;
    }
    
    public void setRiderId(int riderId) {
        this.riderId = riderId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getCompany() {
        return company;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    
    public String getContactNumber() {
        return contactNumber;
    }
    
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
