package models;

public class Sale extends Customer {
    private int id;
    private String date;
    private double netTotal;
    
    public Sale() {}
    
    public Sale(int id, String date, double netTotal) {
        super();
        this.id = id;
        this.date = date;
        this.netTotal = netTotal;
    }
    
    public Sale(String date, double netTotal, Customer customer) {
        super(
            customer.getCustomerId(),
            customer.getCustomerStatus()
        );
        this.id = id;
        this.date = date;
        this.netTotal = netTotal;
    }
    
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
    
     @Override
    public String display() {
     return "Sale ID: " + getSaleId() + "\nSale Date: " + getSaleDate() + "\nNet Total: " + getNetTotal() + "\nCustomer Info: \n\n" + super.display();
    }   
}
