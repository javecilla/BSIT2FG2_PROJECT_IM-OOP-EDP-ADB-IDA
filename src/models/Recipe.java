package models;

public class Recipe {
    private int quantiy;
    private Food food;
    private Ingredient ingredient;
    
    public Recipe() {}
    
    public Recipe(int quantity) {
       this.quantiy = quantity;
    }  
    
    public Recipe(int quantity, Food food, Ingredient ingredient) {
       this.quantiy = quantity;
       this.food = food;
       this.ingredient = ingredient;
    }  
    
    public int getRecipeQuantity() {
        return quantiy;
    }
    
    public void setRecipeQuantity(int quantity) {
        this.quantiy = quantity;
    }
}
