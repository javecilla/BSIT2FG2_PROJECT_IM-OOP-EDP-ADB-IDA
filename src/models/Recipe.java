package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Recipe that contains a list of ingredients and food details.
 * It supports both single and multiple ingredients.
 */
public class Recipe {
    private int quantity;
    private Food food;                      // The food item associated with the recipe.
    private Ingredient ingredient;          // A single ingredient for the recipe.
    private List<Ingredient> ingredients;   // A list of ingredients for multiple ingredients.
    
    // Constructors
    public Recipe() {}

    public Recipe(int quantity, Food food, List<Ingredient> ingredients) {
        this.quantity = quantity;
        this.food = food;
        this.ingredients = ingredients;
    }  
    
    public Recipe(int quantity, Food food, Ingredient ingredient) {
        this.quantity = quantity;
        this.food = food;
        this.ingredients = new ArrayList<>();
        this.ingredients.add(ingredient); // Add the single ingredient to the list
    }
    
    // Getters and setters for all fields
    public int getRecipeQuantity() {
        return quantity;
    }

    public void setRecipeQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
