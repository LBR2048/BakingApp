package com.udacity.bakingapp.data.remote;

import android.util.Log;

import com.udacity.bakingapp.data.RecipesRepository;
import com.udacity.bakingapp.model.Recipe;

import java.io.IOException;
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
                loadRecipesCallback.onSuccess(recipes);
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
                    loadRecipeCallback.onSuccess(recipe);
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
                    loadStepsCallback.onSuccess(recipe.getSteps());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }

    @Override
    public Recipe loadRecipe(int recipeId) {
        RecipesEndpointInterface apiService = getRecipesEndpointInterface();

        String recipePath = "baking.json";
        Call<List<Recipe>> call = apiService.getRecipes(recipePath);
        try {
            List<Recipe> recipes = call.execute().body();
            return findRecipeById(recipeId, recipes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
            if (recipe.getIdentity() == recipeId) {
                return recipe;
            }
        }
        return null;
    }
}
