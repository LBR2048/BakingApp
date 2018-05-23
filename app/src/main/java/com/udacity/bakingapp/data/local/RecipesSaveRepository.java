package com.udacity.bakingapp.data.local;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 09/06/2017.
 */

public interface RecipesSaveRepository {

    interface LoadRecipesCallback {

        void onRecipesSaved(List<Recipe> recipes);

        void onDataNotAvailable();
    }

    interface LoadRecipeCallback {

        void onRecipeSaved(Recipe recipe);

        void onDataNotAvailable();
    }

    interface LoadStepsCallback {

        void onStepsSaved(List<Step> steps);
    }

    void saveRecipes(List<Recipe> recipes);
}
