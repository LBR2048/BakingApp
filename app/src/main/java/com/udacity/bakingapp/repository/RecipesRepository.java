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

    interface LoadStepsCallback {

        void onStepsLoaded(List<Step> steps);
    }

    void loadRecipes(LoadRecipesCallback loadRecipesCallback);

    void loadSteps(LoadStepsCallback loadStepsCallback, int recipeId);
}
