package com.udacity.bakingapp.data;

import com.udacity.bakingapp.model.Recipe;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 09/06/2017.
 */

public interface RefreshRecipesRepository {

    void refreshRecipes(RefreshRecipesCallback refreshRecipesCallback);

    interface RefreshRecipesCallback {

        void onSuccess(List<Recipe> recipes);

        void onFailure();
    }
}
