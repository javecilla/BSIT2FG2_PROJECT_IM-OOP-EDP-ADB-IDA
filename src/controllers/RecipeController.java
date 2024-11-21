package controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Collections;

import models.Recipe;
import services.RecipeService;

public class RecipeController {
    protected final RecipeService recipeService;
    
    public RecipeController() {
        this.recipeService = new RecipeService();
    }
    
    
}
