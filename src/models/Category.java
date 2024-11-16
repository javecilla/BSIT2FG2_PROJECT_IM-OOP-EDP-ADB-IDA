
package models;

public class Category {
    private int id;
    private String name;
    
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
        System.out.printf("%-10d %-30s%n", 
                          getCategoryId(), 
                          getCategoryName()
        );
    }
}
