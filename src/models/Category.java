package models;

import javax.swing.JOptionPane;

/**
 * Represents the `Category` table in the database.
 * For product categorization, the Category class serves as a base class to manage shared attributes like 
 * id and name. The Food class extends Category to represent specific food items, adding fields such as 
 * price and name.
 */
public class Category {
    private int id;
    private String name;
    
    // Constructors
    public Category() {}
   
    public Category(int categoryId, String categoryName) {
        this.id = categoryId;
        this.name = categoryName;
    }
    
    // Getters and setters for all fields
    public int getCategoryId() {
        return id;
    }

    public void setCategoryId(int categoryId) {
        this.id = categoryId;
    }

    public String getCategoryName() {
        return name;
    }

    public void setCategoryName(String categoryName) {
        this.name = categoryName;
    }
    
    public void displayInfo() {
        String message = String.format("%-10d %-30s%n", getCategoryId(), getCategoryName());
        JOptionPane.showMessageDialog(null, message);
    }
}
