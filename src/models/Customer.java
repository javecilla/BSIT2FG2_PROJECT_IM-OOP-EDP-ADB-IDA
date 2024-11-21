package models;

import javax.swing.JOptionPane;

public class Customer extends User {
    private int id;
    private String status;
    
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
            user.getUserRole()
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
        /*System.out.println("Customer ID: " + getCustomerId());
        System.out.println("Customer Status: " + getCustomerStatus());
        System.out.println("Customer Info: \n");*/
        //JOptionPane.showMessageDialog(null, "Customer ID: " + getCustomerId() + "\nCustomer Status: " + getCustomerStatus() + "\nCustomer Info: \n\n" + super.display());
        return "Customer ID: " + getCustomerId() + "\nCustomer Status: " + getCustomerStatus() + "\nCustomer Info: \n\n" + super.display();
    }   
}