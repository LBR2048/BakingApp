package com.udacity.bakingapp.recipes;

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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//        getSupportFragmentManager().putFragment(outState, RECIPES_FRAGMENT_TAG, mRecipesFragment);

//        if (mRecipeDetailsFragment != null) getSupportFragmentManager().putFragment(outState, STEPS_FRAGMENT_TAG, mRecipeDetailsFragment);
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
//        stepsFragment =
//                (RecipeDetailsFragment) getSupportFragmentManager().findFragmentByTag(
//                        STEPS_FRAGMENT_TAG);
    }

    @Override
    public void onIngredientClicked(Ingredient ingredient) {

    }

    // TODO ao clicar no step, não mandamos as infos sobre o step, mas sim sobre a receita toda?
    @Override
    public void onStepClicked(Step step) {
        Toast.makeText(this, step.getShortDescription() + " clicked", Toast.LENGTH_SHORT).show();

        if (getSupportFragmentManager().findFragmentByTag(STEP_DETAILS_FRAGMENT_TAG) == null) {
            StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(1,
                    step.getVideoURL(), step.getId());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_acivity_content, stepDetailsFragment, STEP_DETAILS_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        }
//        stepDetailsFragment =
//                (StepDetailsFragment) getSupportFragmentManager().findFragmentByTag(
//                        STEP_DETAILS_FRAGMENT_TAG);
//        stepDetailsFragment.showStep(step);
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
