package com.udacity.bakingapp.data.local;

import com.udacity.bakingapp.model.Recipe;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 09/06/2017.
 */

public interface RecipesSaveRepository {

    void saveRecipes(List<Recipe> recipes);
}
