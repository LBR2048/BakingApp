package com.udacity.bakingapp.data;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.data.local.RecipesLocalRepository;
import com.udacity.bakingapp.data.remote.RecipesRemoteRepository;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 16/01/2018.
 */

public class RecipesRepositoryImpl implements RecipesRepository {

    private RecipesRepository mRecipesLocalRepository = new RecipesLocalRepository();
    private RecipesRepository mRecipesRemoteRepository = new RecipesRemoteRepository();

    @Override
    public void loadRecipes(final LoadRecipesCallback loadRecipesCallback) {
        mRecipesRemoteRepository.loadRecipes(new LoadRecipesCallback() {
            @Override
            public void onSuccess(List<Recipe> recipes) {
                loadRecipesCallback.onSuccess(recipes);
            }

            @Override
            public void onFailure() {
                loadRecipesCallback.onFailure();
            }
        });
    }

    @Override
    public void loadRecipe(final LoadRecipeCallback loadRecipeCallback, int recipeId) {
        mRecipesRemoteRepository.loadRecipe(new LoadRecipeCallback() {
            @Override
            public void onSuccess(Recipe recipe) {
                loadRecipeCallback.onSuccess(recipe);
            }

            @Override
            public void onFailure() {
                loadRecipeCallback.onFailure();
            }
        }, recipeId);
    }

    @Override
    public void loadSteps(final LoadStepsCallback loadStepsCallback, int recipeId) {
        mRecipesRemoteRepository.loadSteps(new LoadStepsCallback() {
            @Override
            public void onSuccess(List<Step> steps) {
                loadStepsCallback.onSuccess(steps);
            }
        }, recipeId);
    }

    @Override
    public Recipe loadRecipe(int recipeId) {
        return mRecipesRemoteRepository.loadRecipe(recipeId);
    }
}
