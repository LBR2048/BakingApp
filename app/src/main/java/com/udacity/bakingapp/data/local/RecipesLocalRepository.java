package com.udacity.bakingapp.data.local;

import android.util.Log;

import com.udacity.bakingapp.data.RecipesRepository;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;

import java.util.List;

import co.uk.rushorm.core.RushSearch;

/**
 * Created by leonardo.ardjomand on 16/01/2018.
 */

public class RecipesLocalRepository implements RecipesRepository, RecipesSaveRepository {

    @Override
    public void loadRecipes(RecipesRepository.LoadRecipesCallback loadRecipesCallback) {
        Log.d("Race", "RecipesLocalRepository loadRecipes");
        List<Recipe> recipes = new RushSearch().find(Recipe.class);
        //TODO improve this logic. How to check if the data is outdated?
        if (recipes != null && !recipes.isEmpty()) {
            loadRecipesCallback.onSuccess(recipes);
        } else {
            loadRecipesCallback.onFailure();
        }
    }

    @Override
    public void loadRecipe(RecipesRepository.LoadRecipeCallback loadRecipeCallback, int recipeId) {
        List<Recipe> recipes = new RushSearch().whereEqual("identity", String.valueOf(recipeId)).find(Recipe.class);
        if (!recipes.isEmpty()) {
            Recipe recipe = recipes.get(0);
            //TODO improve this logic. How to check if the data is outdated?
            if (recipe != null) {
                loadRecipeCallback.onSuccess(recipe);
            } else {
                loadRecipeCallback.onFailure();
            }
        } else {
            loadRecipeCallback.onFailure();
        }
    }

    @Override
    public void loadSteps(RecipesRepository.LoadStepsCallback loadStepsCallback, int recipeId) {
        // TODO load steps using RushOrm capabilities instead of loading a Recipe and then getting it Ingredients
        Recipe recipe = loadRecipe(recipeId);
        if (recipe != null) {
            List<Step> steps = recipe.getSteps();
            if (steps != null && !steps.isEmpty()) {
                loadStepsCallback.onSuccess(steps);
            } else {
                loadStepsCallback.onFailure();
            }
        } else {
            loadStepsCallback.onFailure();
        }
    }

    @Override
    public Recipe loadRecipe(int recipeId) {
        List<Recipe> recipes = new RushSearch().whereEqual("identity", String.valueOf(recipeId)).find(Recipe.class);
        if (!recipes.isEmpty()) {
            Recipe recipe = recipes.get(0);
            //TODO improve this logic. How to check if the data is outdated?
            if (recipe != null) {
                return recipe;
            }
        }
        return null;
    }

    @Override
    public void saveRecipes(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            recipe.save();
        }
    }
}
