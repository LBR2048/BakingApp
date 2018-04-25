package com.udacity.bakingapp.recipedetails;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.data.RecipesRepository;
import com.udacity.bakingapp.data.remote.RecipesRemoteRepository;

/**
 * Created by leonardo.ardjomand on 28/12/2017.
 */

public class RecipeDetailsPresenter implements RecipeDetailsContract.Presenter {

    private final RecipeDetailsContract.View mView;
    private final RecipesRepository mRepository;

    public RecipeDetailsPresenter(RecipeDetailsContract.View view) {
        mView = view;
        mRepository = new RecipesRemoteRepository();
    }

    @Override
    public void getRecipeDetails(int recipeId) {
        mRepository.loadRecipe(new RecipesRepository.LoadRecipeCallback() {
            @Override
            public void onSuccess(Recipe recipe) {
                mView.showRecipeName(recipe.getName());
                mView.showIngredients(recipe.getIngredients());
                mView.showSteps(recipe.getSteps());
            }

            @Override
            public void onFailure() {

            }
        }, recipeId);
    }
}
