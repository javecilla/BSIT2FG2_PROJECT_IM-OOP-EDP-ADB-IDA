package models;

import javax.swing.JOptionPane;

public class Ingredient extends Supplier {
    private int id;
    private String name;
    private int quantity;
    private int reorderPoint;
    
    protected Admin admin;
    
    public Ingredient() {
        super();
    }
    
    public Ingredient(int id, String name, int quantity, int reorderPoint) {
        super();
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.reorderPoint = reorderPoint;
    }

    
    public Ingredient(int id, String name, int quantity, int reorderPoint, Supplier supplier) {
        super(
            supplier.getSupplierId(),
            supplier.getSupplierName(),
            supplier.getAddress(),
            supplier.getContactNumber()     
        );
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.reorderPoint = reorderPoint;
    }
    
    public int getIngredientId() {
        return id;
    }
    
    public void setIngredientId(int id) {
        this.id = id;
    }
    
    public String getIngredientName() {
        return name;
    }
    
    public void setIngredientName(String name) {
        this.name = name;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public int getReorderPoint() {
        return reorderPoint;
    }
    
    public void setReorderPoint(int reorderPoint) {
        this.reorderPoint = reorderPoint;
    }
    
    public Admin getAdmin() {
        return this.admin;
    }
    
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    
    @Override
    public String display() {
        /*System.out.println("Ingredient ID: " + getIngredientId());
        System.out.println("Ingredient Name: " + getIngredientName());
        System.out.println("Current Stock: " + getQuantity());
        System.out.println("Re-order Points: " + getReorderPoint());
        System.out.println("\nSupplier Info: ");
        super.display();*/
        
        //JOptionPane.showMessageDialog(null, "Ingredient ID: " + getIngredientId() + "\nIngredient Name: " + getIngredientName() + "\nCurrent Stock: " + getQuantity() + "Re-order Points: " + getReorderPoint() + "\nSupplier Info: \n" + super.display());
        return "Ingredient ID: " + getIngredientId() + "\nIngredient Name: " + getIngredientName() + "\nCurrent Stock: " + getQuantity() + "Re-order Points: " + getReorderPoint() + "\nSupplier Info: \n" + super.display();
    }
}
