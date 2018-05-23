package com.udacity.bakingapp.recipes;

import com.udacity.bakingapp.data.RecipesRepository;
import com.udacity.bakingapp.data.RecipesRepositoryImpl;
import com.udacity.bakingapp.data.RefreshRecipesRepository;
import com.udacity.bakingapp.model.Recipe;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 09/06/2017.
 */

public class RecipesPresenter implements RecipesContract.Presenter {

    private final RecipesContract.View mView;

    private final RecipesRepositoryImpl mRecipesRepository;

    RecipesPresenter(RecipesContract.View view) {
        mView = view;
        mRecipesRepository = new RecipesRepositoryImpl();
    }

    @Override
    public void loadRecipes() {
        mRecipesRepository.loadRecipes(new RecipesRepository.LoadRecipesCallback() {
            @Override
            public void onSuccess(List<Recipe> recipes) {
                mView.showRecipes(recipes);
            }

            @Override
            public void onFailure() {
                // TODO show toast
            }
        });
    }

    @Override
    public void refreshRecipes() {
        mRecipesRepository.refreshRecipes(new RefreshRecipesRepository.RefreshRecipesCallback() {
            @Override
            public void onSuccess(List<Recipe> recipes) {
                mView.showRecipes(recipes);
            }

            @Override
            public void onFailure() {
                // TODO show toast
            }
        });
    }
}
