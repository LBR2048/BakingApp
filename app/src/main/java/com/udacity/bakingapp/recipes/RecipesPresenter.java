package com.udacity.bakingapp.recipes;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.repository.RecipesRepository;
import com.udacity.bakingapp.repository.RecipesRepositoryImpl;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 09/06/2017.
 */

public class RecipesPresenter implements RecipesContract.Presenter {

    private RecipesContract.View mView;

    private RecipesRepository mRecipesRepository;

    private String urlString = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public RecipesPresenter(RecipesContract.View view) {
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

            }
        });
    }
}
