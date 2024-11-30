package models;
/**
 * Represents the `Supplier` table in the database.
 * The Supplier class encapsulates information about suppliers, such as name, contactNumber, and 
 * address. It links directly to the Ingredient class, emphasizing the interconnected and describing whose 
 * supplies those ingredients. 
 */
public class Supplier {
    private int id;                 
    private String name;            
    private String address;         
    private String contactNumber;   
    
    // Constructors
    public Supplier() {}
    
    public Supplier(int id, String name, String address, String contactNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
    }
    
    // Getters and setters for all fields
    public int getSupplierId() {
        return id;
    }
    
    public void setSupplierId(int id) {
        this.id = id;
    }
    
    public String getSupplierName() {
        return name;
    }
    
    public void setSupplierName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getContactNumber() {
        return contactNumber;
    }
    
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    public String display() {
        // Return the supplier info as a formatted string
        return "Supplier ID: " + getSupplierId() + 
               "\nFull Name: " + getSupplierName() + 
               "\nContact Number: " + getContactNumber() + 
               "\nAddress: " + getAddress();
    }
}
