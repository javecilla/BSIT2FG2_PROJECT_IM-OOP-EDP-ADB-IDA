package models;
/**
 * Represents the `Sale` table in the database.
 * Extends the `Customer` class to associate sales with a specific customer.
 * Includes attributes for sale ID, date, and net total amount of the sale.
 */
public class Sale {
    private int id;
    private String date;
    private double netTotal;        // Total amount of the sale after any deductions.
    private double paymentAmount;   // Total ammout nung binayad
    
    private Customer customer;
    private Courier courier;
   
    // Constructors
    public Sale() {}
    
    public Sale(String date, double netTotal, double paymentAmount) {
        this.date = date;
        this.netTotal = netTotal;
        this.paymentAmount = paymentAmount;
    }
    
    public Sale(int id, String date, double netTotal) {
        this.id = id;
        this.date = date;
        this.netTotal = netTotal;
    }
  
    public Sale(int id, String date, double netTotal, double paymentAmount) {
        this.id = id;
        this.date = date;
        this.netTotal = netTotal;
        this.paymentAmount = paymentAmount;
    }
    
    // Getters and setters for all fields
    public int getSaleId() {
        return id;
    }
    
    public void setSaleId(int id) {
        this.id = id;
    }
    
    public String getSaleDate() {
        return date;
    }
    
    public void setSaleDate(String date) {
        this.date = date;
    }
    
    public double getNetTotal() {
        return netTotal;
    }
    
    public void setNetTotal(double netTotal) {
        this.netTotal = netTotal;
    }
    
    public double getPaymentAmount() {
        return paymentAmount;
    }
    
    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Courier getCourier() {
        return courier;
    }
    
    public void setCourier(Courier courier) {
        this.courier = courier;
    }
}
