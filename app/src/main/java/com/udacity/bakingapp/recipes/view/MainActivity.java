package com.udacity.bakingapp.recipes.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.recipedetails.RecipeDetailsFragment;
import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.stepdetails.StepDetailsFragment;

public class MainActivity extends AppCompatActivity
        implements RecipesFragment.OnRecipesFragmentInteractionListener,
        RecipeDetailsFragment.OnDetailsFragmentInteraction,
        StepDetailsFragment.OnListFragmentInteractionListener {

    private static final String RECIPES_FRAGMENT_TAG = "recipes_fragment_tag";
    private static final String STEPS_FRAGMENT_TAG = "steps_fragment_tag";
    private static final String STEP_DETAILS_FRAGMENT_TAG = "step_details_tag";
    private RecipesFragment mRecipesFragment;
    private RecipeDetailsFragment mRecipeDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mRecipesFragment = (RecipesFragment) getSupportFragmentManager().getFragment(savedInstanceState, RECIPES_FRAGMENT_TAG);
//            mRecipeDetailsFragment = (RecipeDetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, STEPS_FRAGMENT_TAG);
        } else {
            mRecipesFragment = RecipesFragment.newInstance(1);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_acivity_content, mRecipesFragment, RECIPES_FRAGMENT_TAG)
                    .commit();
        }
//        } else {
//            recipesFragment = (RecipesFragment) getSupportFragmentManager().findFragmentByTag(
//                    RECIPES_FRAGMENT_TAG);
//        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//        getSupportFragmentManager().putFragment(outState, RECIPES_FRAGMENT_TAG, mRecipesFragment);

//        if (mRecipeDetailsFragment != null) getSupportFragmentManager().putFragment(outState, STEPS_FRAGMENT_TAG, mRecipeDetailsFragment);
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        mRecipeDetailsFragment = RecipeDetailsFragment.newInstance(1);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_acivity_content, mRecipeDetailsFragment, STEPS_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();

        getSupportFragmentManager().executePendingTransactions();

//        stepsFragment =
//                (RecipeDetailsFragment) getSupportFragmentManager().findFragmentByTag(
//                        STEPS_FRAGMENT_TAG);
        mRecipeDetailsFragment.showIngredients(recipe.getIngredients());
        mRecipeDetailsFragment.showSteps(recipe.getSteps());
    }

    @Override
    public void onIngredientClicked(Ingredient ingredient) {

    }

    @Override
    public void onStepClicked(Step step) {
        Toast.makeText(this, step.getShortDescription() + " clicked", Toast.LENGTH_SHORT).show();

        StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(1, step.getVideoURL());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_acivity_content, stepDetailsFragment,
                        STEP_DETAILS_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();

        getSupportFragmentManager().executePendingTransactions();

//        stepDetailsFragment =
//                (StepDetailsFragment) getSupportFragmentManager().findFragmentByTag(
//                        STEP_DETAILS_FRAGMENT_TAG);
        stepDetailsFragment.showStepDetails(step);
    }

    @Override
    public void previousStep() {
        Toast.makeText(this, "Previous step", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void nextStep() {
        Toast.makeText(this, "Next step", Toast.LENGTH_SHORT).show();
    }
}
