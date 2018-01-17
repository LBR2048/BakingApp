package com.udacity.bakingapp.repository.remote;

import android.util.Log;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.repository.RecipesRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by leonardo.ardjomand on 09/06/2017.
 */

public class RecipesRemoteRepository implements RecipesRepository {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    @Override
    public void loadRecipes(final LoadRecipesCallback loadRecipesCallback) {
        RecipesEndpointInterface apiService = getRecipesEndpointInterface();

        String recipes = "baking.json";
        Call<List<Recipe>> call = apiService.getRecipes(recipes);
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.i("Retrofit", "Download all recipes from server");
                List<Recipe> recipes = response.body();
                loadRecipesCallback.onRecipesLoaded(recipes);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }

    @Override
    public void loadRecipe(final LoadRecipeCallback loadRecipeCallback, final int recipeId) {
        RecipesEndpointInterface apiService = getRecipesEndpointInterface();

        String recipes = "baking.json";
        Call<List<Recipe>> call = apiService.getRecipes(recipes);
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.i("Retrofit loadRecipe", "Download recipe from server");
                List<Recipe> recipes = response.body();
                Recipe recipe = findRecipeById(recipeId, recipes);

                if (recipe != null) {
                    loadRecipeCallback.onRecipeLoaded(recipe);
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }

    @Override
    public void loadSteps(final LoadStepsCallback loadStepsCallback, final int recipeId) {
        RecipesEndpointInterface apiService = getRecipesEndpointInterface();

        String recipes = "baking.json";
        Call<List<Recipe>> call = apiService.getRecipes(recipes);
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.i("Retrofit loadSteps", "Download steps from server");
                List<Recipe> recipes = response.body();
                Recipe recipe = findRecipeById(recipeId, recipes);

                if (recipe != null) {
                    loadStepsCallback.onStepsLoaded(recipe.getSteps());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }

    private RecipesEndpointInterface getRecipesEndpointInterface() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RecipesEndpointInterface.class);
    }

    private Recipe findRecipeById(int recipeId, List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            if (recipe.getId() == recipeId) {
                return recipe;
            }
        }
        return null;
    }

    @Override
    public Recipe loadRecipe(int recipeId) {
        return null;
    }
}
