package com.udacity.bakingapp.recipes;

import com.udacity.bakingapp.model.Recipe;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 28/12/2017.
 */

public interface RecipesContract {

    interface View {
        void showRecipes(List<Recipe> recipes);
    }

    interface Presenter {
        void loadRecipes();
    }
}
