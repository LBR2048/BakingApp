package com.udacity.bakingapp.repository.local;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.repository.RecipesRepository;

/**
 * Created by leonardo.ardjomand on 16/01/2018.
 */

public class RecipesLocalRepository implements RecipesRepository {

    @Override
    public void loadRecipes(LoadRecipesCallback loadRecipesCallback) {

    }

    @Override
    public void loadRecipe(LoadRecipeCallback loadRecipeCallback, int recipeId) {

    }

    @Override
    public void loadSteps(LoadStepsCallback loadStepsCallback, int recipeId) {

    }

    @Override
    public Recipe loadRecipe(int recipeId) {
        return null;
    }
}
