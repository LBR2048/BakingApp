package com.udacity.bakingapp.data;

import android.util.Log;

import com.udacity.bakingapp.data.local.RecipesLocalRepository;
import com.udacity.bakingapp.data.local.RecipesSaveRepository;
import com.udacity.bakingapp.data.local.RecipesSaveRepositoryImpl;
import com.udacity.bakingapp.data.remote.RecipesRemoteRepository;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 16/01/2018.
 */

public class RecipesRepositoryImpl implements RecipesRepository {

    private RecipesRepository mRecipesLocalLoadRepository = new RecipesLocalRepository();
    private RecipesSaveRepository mRecipesLocalSaveRepository = new RecipesSaveRepositoryImpl();
    private RecipesRepository mRecipesRemoteRepository = new RecipesRemoteRepository();

    @Override
    public void loadRecipes(final LoadRecipesCallback loadRecipesCallback) {
        Log.d("Race", "RecipesRepositoryImpl loadRecipes");
        mRecipesLocalLoadRepository.loadRecipes(new LoadRecipesCallback() {
            @Override
            public void onSuccess(List<Recipe> recipes) {
                Log.d("Race", "RecipesRepositoryImpl loadRecipes_onRecipesLoaded");
                loadRecipesCallback.onSuccess(recipes);
            }

            @Override
            public void onFailure() {
                Log.d("Race", "RecipesRepositoryImpl loadRecipes_onDataNotAvailable");
                mRecipesRemoteRepository.loadRecipes(new LoadRecipesCallback() {
                    @Override
                    public void onSuccess(List<Recipe> recipes) {
                        Log.d("Race", "RecipesRepositoryImpl loadRecipes_onRecipesLoaded");
                        loadRecipesCallback.onSuccess(recipes);
                        mRecipesLocalSaveRepository.saveRecipes(recipes);
                    }

                    @Override
                    public void onFailure() {
                        Log.d("Race", "RecipesRepositoryImpl loadRecipes_onDataNotAvailable");
                        loadRecipesCallback.onFailure();
                    }
                });
            }
        });
    }

    @Override
    public void loadRecipe(final LoadRecipeCallback loadRecipeCallback, final int recipeId) {
        mRecipesLocalLoadRepository.loadRecipe(new LoadRecipeCallback() {
            @Override
            public void onSuccess(Recipe recipe) {
                loadRecipeCallback.onSuccess(recipe);
            }

            @Override
            public void onFailure() {
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
        }, recipeId);
    }

    @Override
    public void loadSteps(final LoadStepsCallback loadStepsCallback, final int recipeId) {
        mRecipesLocalLoadRepository.loadSteps(new LoadStepsCallback() {
            @Override
            public void onSuccess(List<Step> steps) {
                loadStepsCallback.onSuccess(steps);
            }

            @Override
            public void onFailure() {
                mRecipesRemoteRepository.loadSteps(new LoadStepsCallback() {
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
        return mRecipesRemoteRepository.loadRecipe(recipeId);
    }
}
