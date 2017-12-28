package com.udacity.bakingapp.recipedetails;

import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Step;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 09/06/2017.
 */

public interface RecipeDetailsView {
    void showIngredients(List<Ingredient> ingredients);
    void showSteps(List<Step> steps);
}
