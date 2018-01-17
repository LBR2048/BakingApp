package com.udacity.bakingapp.repository;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.repository.local.RecipesLocalRepository;
import com.udacity.bakingapp.repository.remote.RecipesRemoteRepository;

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
            public void onRecipesLoaded(List<Recipe> recipes) {
                loadRecipesCallback.onRecipesLoaded(recipes);
            }

            @Override
            public void onDataNotAvailable() {
                loadRecipesCallback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void loadRecipe(final LoadRecipeCallback loadRecipeCallback, int recipeId) {
        mRecipesRemoteRepository.loadRecipe(new LoadRecipeCallback() {
            @Override
            public void onRecipeLoaded(Recipe recipe) {
                loadRecipeCallback.onRecipeLoaded(recipe);
            }

            @Override
            public void onDataNotAvailable() {
                loadRecipeCallback.onDataNotAvailable();
            }
        }, recipeId);
    }

    @Override
    public void loadSteps(final LoadStepsCallback loadStepsCallback, int recipeId) {
        mRecipesRemoteRepository.loadSteps(new LoadStepsCallback() {
            @Override
            public void onStepsLoaded(List<Step> steps) {
                loadStepsCallback.onStepsLoaded(steps);
            }
        }, recipeId);
    }

    @Override
    public Recipe loadRecipe(int recipeId) {
        return null;
    }
}
