package com.udacity.bakingapp.recipes.repository;

import com.udacity.bakingapp.model.Recipe;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 09/06/2017.
 */

public interface RecipesRepository {

    interface LoadRecipesCallback {

        void onRecipesLoaded(List<Recipe> recipes);

        void onDataNotAvailable();
    }

    void loadRecipes(LoadRecipesCallback loadRecipesCallback);
}
