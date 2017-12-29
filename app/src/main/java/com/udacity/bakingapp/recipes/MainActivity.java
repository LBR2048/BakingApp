package com.udacity.bakingapp.recipes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.recipedetails.RecipeDetailsFragment;
import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.stepdetails.StepDetailsFragment;

public class MainActivity extends AppCompatActivity
        implements RecipesFragment.OnRecipesFragmentInteractionListener,
        RecipeDetailsFragment.OnDetailsFragmentInteraction {

    private static final String RECIPES_FRAGMENT_TAG = "recipes_fragment_tag";
    private static final String STEPS_FRAGMENT_TAG = "steps_fragment_tag";
    private static final String STEP_DETAILS_FRAGMENT_TAG = "step_details_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipesFragment recipesFragment;
        if (getSupportFragmentManager().findFragmentByTag(RECIPES_FRAGMENT_TAG) == null) {
            recipesFragment = RecipesFragment.newInstance(1);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_acivity_content, recipesFragment, RECIPES_FRAGMENT_TAG)
                    .commit();
        }
//        } else {
//            recipesFragment = (RecipesFragment) getSupportFragmentManager().findFragmentByTag(
//                    RECIPES_FRAGMENT_TAG);
//        }
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        if (getSupportFragmentManager().findFragmentByTag(STEPS_FRAGMENT_TAG) == null) {
            RecipeDetailsFragment recipeDetailsFragment = RecipeDetailsFragment.newInstance(1,
                    recipe.getId());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_acivity_content, recipeDetailsFragment, STEPS_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onIngredientClicked(Ingredient ingredient) {

    }

    @Override
    public void onStepClicked(int recipeId, Step step) {
        if (getSupportFragmentManager().findFragmentByTag(STEP_DETAILS_FRAGMENT_TAG) == null) {
            StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(1,
                    recipeId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_acivity_content, stepDetailsFragment, STEP_DETAILS_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        }

        StepDetailsFragment stepDetailsFragment =
                (StepDetailsFragment) getSupportFragmentManager().findFragmentByTag(
                        STEP_DETAILS_FRAGMENT_TAG);
        stepDetailsFragment.showStep(recipeId, step.getId());
    }
}
