package com.udacity.bakingapp.data;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 09/06/2017.
 */

public interface RecipesRepository {

    void loadRecipes(LoadRecipesCallback loadRecipesCallback);

    interface LoadRecipesCallback {

        void onSuccess(List<Recipe> recipes);

        void onFailure();
    }

    void loadRecipe(LoadRecipeCallback loadRecipeCallback, int recipeId);

    interface LoadRecipeCallback {

        void onSuccess(Recipe recipe);

        void onFailure();
    }

    void loadSteps(LoadStepsCallback loadStepsCallback, int recipeId);

    interface LoadStepsCallback {

        void onSuccess(List<Step> steps);
    }

    Recipe loadRecipe(int recipeId);
}
