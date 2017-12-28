package com.udacity.bakingapp.recipedetails;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.repository.RecipesRepository;
import com.udacity.bakingapp.repository.RecipesRepositoryImpl;

/**
 * Created by leonardo.ardjomand on 28/12/2017.
 */

public class RecipeDetailsPresenter implements RecipeDetailsContract.Presenter {

    private RecipeDetailsContract.View mView;
    private RecipesRepository mRepository;

    public RecipeDetailsPresenter(RecipeDetailsContract.View view) {
        mView = view;
        mRepository = new RecipesRepositoryImpl();
    }

    @Override
    public void getRecipeDetails(int recipeId) {
        mRepository.loadRecipe(new RecipesRepository.LoadRecipeCallback() {
            @Override
            public void onRecipeLoaded(Recipe recipe) {
                mView.showIngredients(recipe.getIngredients());
                mView.showSteps(recipe.getSteps());
            }

            @Override
            public void onDataNotAvailable() {

            }
        }, recipeId);
    }
}
