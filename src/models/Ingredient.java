package models;
/**
 * Represents the `Ingredient` table in the database.
 * The Ingredient class encapsulates details about individual ingredients, such as name, quantity, and 
 * reorderPoint. It establishes relationships with Supplier and Admin classes to manage sourcing and 
 * oversight.
 */
public class Ingredient {
    private int id;
    private String name;
    private int quantity;
    private int reorderPoint;
    
    private Supplier supplier;          // Associated supplier for the ingredient.
    //private Admin admin;            
    private User admin;                 // Admin associated with the ingredient.
    
    public Ingredient() {}
    
    public Ingredient(int id, String name, int quantity, int reorderPoint) {
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
    
    public User getUserAdmin() {
        return this.admin;
    }
    
    public void setUserAdmin(User admin) {
        this.admin = admin;
    }
    
    public Supplier getSupplier() {
        return supplier;
    }
    
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String display() {
        return "Ingredient ID: " + getIngredientId() + "\nIngredient Name: " + getIngredientName() + "\nCurrent Stock: " + getQuantity() + "\nRe-order Points: " + getReorderPoint() + "\nSupplier Info: \n" + supplier.display();
    }
}
