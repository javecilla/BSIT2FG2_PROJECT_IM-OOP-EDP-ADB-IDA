package models;
/**
 * Represents the `Ingredient` entity with details about an ingredient,
 * including its name, quantity, reorder points, and associated supplier.
 */
public class Ingredient {
    private int id;
    private String name;
    private int quantity;
    private int reorderPoint;
    
    private Supplier supplier;      // Associated supplier for the ingredient.
    private Admin admin;            // Admin associated with the ingredient.
    
    // Constructors 
    public Ingredient() {}
    
    public Ingredient(int id, String name, int quantity, int reorderPoint) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.reorderPoint = reorderPoint;
    }
    
    // Getters and setters
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
