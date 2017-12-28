package com.udacity.bakingapp.recipes.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.details.DetailsFragment;
import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.stepdetail.StepDetailsFragment;

public class MainActivity extends AppCompatActivity
        implements RecipesFragment.OnRecipesFragmentInteractionListener,
        DetailsFragment.OnDetailsFragmentInteraction,
        StepDetailsFragment.OnListFragmentInteractionListener {

    private static final String RECIPES_FRAGMENT_TAG = "recipes_fragment_tag";
    private static final String STEPS_FRAGMENT_TAG = "steps_fragment_tag";
    private static final String STEP_DETAILS_FRAGMENT_TAG = "step_details_tag";
    private RecipesFragment mRecipesFragment;
    private DetailsFragment mDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mRecipesFragment = (RecipesFragment) getSupportFragmentManager().getFragment(savedInstanceState, RECIPES_FRAGMENT_TAG);
//            mDetailsFragment = (DetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, STEPS_FRAGMENT_TAG);
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

//        if (mDetailsFragment != null) getSupportFragmentManager().putFragment(outState, STEPS_FRAGMENT_TAG, mDetailsFragment);
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        mDetailsFragment = DetailsFragment.newInstance(1);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_acivity_content, mDetailsFragment, STEPS_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();

        getSupportFragmentManager().executePendingTransactions();

//        stepsFragment =
//                (DetailsFragment) getSupportFragmentManager().findFragmentByTag(
//                        STEPS_FRAGMENT_TAG);
        mDetailsFragment.showIngredients(recipe.getIngredients());
        mDetailsFragment.showSteps(recipe.getSteps());
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
    public void previousStep(Step step) {
        Toast.makeText(this, "Previous step", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void nextStep(Step step) {
        Toast.makeText(this, "Next step", Toast.LENGTH_SHORT).show();
    }
}
