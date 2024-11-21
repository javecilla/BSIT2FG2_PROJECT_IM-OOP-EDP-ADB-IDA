package models;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private int quantity;
    private Food food;
    private Ingredient ingredient; //ingredient for one
    private List<Ingredient> ingredients;  //ingredients for multiple

    public Recipe() {}

    // Constructor to initialize with a list of ingredients
    public Recipe(int quantity, Food food, List<Ingredient> ingredients) {
        this.quantity = quantity;
        this.food = food;
        this.ingredients = ingredients;
    }  
    
    // Constructor for a single ingredient
    public Recipe(int quantity, Food food, Ingredient ingredient) {
        this.quantity = quantity;
        this.food = food;
        this.ingredients = new ArrayList<>();
        this.ingredients.add(ingredient); // Add the single ingredient to the list
    }

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
