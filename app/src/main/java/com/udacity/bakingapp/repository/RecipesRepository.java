package com.udacity.bakingapp.repository;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 09/06/2017.
 */

public interface RecipesRepository {

    interface LoadRecipesCallback {

        void onRecipesLoaded(List<Recipe> recipes);

        void onDataNotAvailable();
    }

    interface LoadRecipeCallback {

        void onRecipeLoaded(Recipe recipe);

        void onDataNotAvailable();
    }

    interface LoadStepsCallback {

        void onStepsLoaded(List<Step> steps);
    }

    void loadRecipes(LoadRecipesCallback loadRecipesCallback);

    void loadRecipe(LoadRecipeCallback loadRecipeCallback, int recipeId);

    void loadSteps(LoadStepsCallback loadStepsCallback, int recipeId);
}
