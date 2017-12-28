package com.udacity.bakingapp.recipes.view;

import com.udacity.bakingapp.model.Recipe;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 09/06/2017.
 */

public interface RecipesView {
    void showRecipes(List<Recipe> recipes);
}
