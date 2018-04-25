package com.udacity.bakingapp.steps;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.data.RecipesRepository;
import com.udacity.bakingapp.data.remote.RecipesRemoteRepository;

/**
 * Created by leonardo.ardjomand on 28/12/2017.
 */

public class StepsPresenter implements StepsContract.Presenter {

    private final StepsContract.View mView;
    private final RecipesRepository mRepository;

    public StepsPresenter(StepsContract.View view) {
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
