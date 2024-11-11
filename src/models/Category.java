
package models;

public class Category {
    private int categoryId;
    private String categoryName;
    
    public Category() {}
   
    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
    
    // Getters and setters for all fields
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public void displayInfo() {
        System.out.printf("%-10d %-30s%n", 
                          categoryId, 
                          categoryName
        );
    }
}
