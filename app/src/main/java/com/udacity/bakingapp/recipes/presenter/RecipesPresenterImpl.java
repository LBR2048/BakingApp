package com.udacity.bakingapp.recipes.presenter;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.recipes.repository.RecipesRepository;
import com.udacity.bakingapp.recipes.repository.RecipesRepositoryImpl;
import com.udacity.bakingapp.recipes.view.RecipesView;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 09/06/2017.
 */

public class RecipesPresenterImpl implements RecipesPresenter {

    private RecipesView mRecipesView;

    private RecipesRepository mRecipesRepository;

    private String urlString = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public RecipesPresenterImpl(RecipesView recipesView) {
        mRecipesView = recipesView;
        mRecipesRepository = new RecipesRepositoryImpl();
    }

    @Override
    public void loadRecipes() {
        mRecipesRepository.loadRecipes(new RecipesRepository.LoadRecipesCallback() {
            @Override
            public void onRecipesLoaded(List<Recipe> recipes) {
                mRecipesView.showRecipes(recipes);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
