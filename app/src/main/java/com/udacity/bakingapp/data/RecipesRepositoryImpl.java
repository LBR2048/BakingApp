package com.udacity.bakingapp.data;

import android.util.Log;

import com.udacity.bakingapp.data.local.RecipesLocalRepository;
import com.udacity.bakingapp.data.remote.RecipesRemoteRepository;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 16/01/2018.
 */

public class RecipesRepositoryImpl implements RecipesRepository, RefreshRecipesRepository {

    public static final String TAG = RecipesRepositoryImpl.class.getSimpleName();

    private RecipesLocalRepository mLocalRepository = new RecipesLocalRepository();
    private RecipesRemoteRepository mRemoteRepository = new RecipesRemoteRepository();

    @Override
    public void loadRecipes(final RecipesRepository.LoadRecipesCallback loadRecipesCallback) {
        Log.d(TAG, "Loading recipes from local storage");
        mLocalRepository.loadRecipes(new RecipesRepository.LoadRecipesCallback() {
            @Override
            public void onSuccess(List<Recipe> recipes) {
                Log.d(TAG, "Recipes successfully loaded from local storage");
                loadRecipesCallback.onSuccess(recipes);
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "Could not load recipes from local storage");
                Log.d(TAG, "Loading recipes from remote source");
                mRemoteRepository.loadRecipes(new RecipesRepository.LoadRecipesCallback() {
                    @Override
                    public void onSuccess(List<Recipe> recipes) {
                        Log.d(TAG, "Recipes successfully loaded from remote source");
                        loadRecipesCallback.onSuccess(recipes);
                        mLocalRepository.saveRecipes(recipes);
                    }

                    @Override
                    public void onFailure() {
                        Log.d(TAG, "Could not load recipes from remote source");
                        loadRecipesCallback.onFailure();
                    }
                });
            }
        });
    }

    @Override
    public void loadRecipe(final LoadRecipeCallback loadRecipeCallback, final int recipeId) {
        mLocalRepository.loadRecipe(new LoadRecipeCallback() {
            @Override
            public void onSuccess(Recipe recipe) {
                loadRecipeCallback.onSuccess(recipe);
            }

            @Override
            public void onFailure() {
                mRemoteRepository.loadRecipe(new LoadRecipeCallback() {
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
        }, recipeId);
    }

    @Override
    public void loadSteps(final LoadStepsCallback loadStepsCallback, final int recipeId) {
        mLocalRepository.loadSteps(new LoadStepsCallback() {
            @Override
            public void onSuccess(List<Step> steps) {
                loadStepsCallback.onSuccess(steps);
            }

            @Override
            public void onFailure() {
                mRemoteRepository.loadSteps(new LoadStepsCallback() {
                    @Override
                    public void onSuccess(List<Step> steps) {
                        loadStepsCallback.onSuccess(steps);
                    }

                    @Override
                    public void onFailure() {
                        loadStepsCallback.onFailure();
                    }
                }, recipeId);
            }
        }, recipeId);
    }

    @Override
    public Recipe loadRecipe(int recipeId) {
        return mRemoteRepository.loadRecipe(recipeId);
    }


    @Override
    public void refreshRecipes(RefreshRecipesCallback refreshRecipesCallback) {
        mRemoteRepository.refreshRecipes(refreshRecipesCallback);
    }
}
