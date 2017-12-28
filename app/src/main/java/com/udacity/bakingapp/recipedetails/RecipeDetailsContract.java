package com.udacity.bakingapp.recipedetails;

import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Step;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 28/12/2017.
 */

public interface RecipeDetailsContract {

    interface View {

        void showIngredients(List<Ingredient> ingredients);

        void showSteps(List<Step> steps);
    }

    interface Presenter {

        void getRecipeDetails(int recipeId);
    }
}
