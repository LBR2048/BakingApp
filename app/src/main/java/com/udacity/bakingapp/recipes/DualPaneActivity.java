package com.udacity.bakingapp.recipes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.recipedetails.RecipeDetailsFragment;
import com.udacity.bakingapp.stepdetails.StepDetailsFragment;

public class DualPaneActivity extends AppCompatActivity
        implements RecipeDetailsFragment.OnDetailsFragmentInteraction {

    public static final String EXTRA_RECIPE_ID = "recipeId";
    private static final String RECIPE_DETAILS_FRAGMENT_TAG = "recipe_details_fragment_tag";
    private static final String STEP_DETAILS_FRAGMENT_TAG = "step_details_tag";

    private boolean mTwoPane;
    private boolean mIsRecipeSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual_pane);

        int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, -1);

        determineTwoPane();

        showRecipeDetails(recipeId, R.id.dual_pane_master);

        if (!mTwoPane) {// || mIsRecipeSelected) {
        // TODO remove detail pane if no video selected
            removeDetailPaneFragment();

        // TODO remove master pane if video selected

        // TODO the Activity must know if a video is selected or not
        // TODO or even the Activity presenter
        }
    }

    @Override
    public void onIngredientClicked(Ingredient ingredient) {

    }

    @Override
    public void onStepClicked(int recipeId, Step step) {
        if (mTwoPane) {
            showStepDetails(recipeId, step.getId(), R.id.dual_pane_detail);
            // TODO add to backstack
        } else {
            showStepDetails(recipeId, step.getId(), R.id.dual_pane_master);
        }
    }

    private void determineTwoPane() {
        mTwoPane = (findViewById(R.id.dual_pane_detail) != null);
    }

    private void showRecipeDetails(int mRecipeId, int containerViewId) {
        RecipeDetailsFragment recipeDetailsFragment = getRecipeDetailsFragment();

        if (recipeDetailsFragment == null) {
            recipeDetailsFragment = RecipeDetailsFragment.newInstance(1, mRecipeId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(containerViewId, recipeDetailsFragment, RECIPE_DETAILS_FRAGMENT_TAG)
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }

    private void showStepDetails(int recipeId, int stepId, int containerViewId) {
        StepDetailsFragment stepDetailsFragment = getStepDetailsFragment();

        if (stepDetailsFragment == null) {
            stepDetailsFragment = StepDetailsFragment.newInstance(recipeId, stepId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(containerViewId, stepDetailsFragment, STEP_DETAILS_FRAGMENT_TAG)
                    .commit();
        } else {
            stepDetailsFragment.showStep(recipeId, stepId);
        }
    }

    private void removeStepDetailsFragment() {
        StepDetailsFragment stepDetailsFragment = getStepDetailsFragment();

        if (stepDetailsFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(stepDetailsFragment)
                    .commit();
        }
    }

    private void removeRecipeDetailsFragment() {
        RecipeDetailsFragment recipeDetailsFragment = getRecipeDetailsFragment();

        if (recipeDetailsFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(recipeDetailsFragment)
                    .commit();
        }
    }

    private void removeDetailPaneFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment detailPaneFragment = fragmentManager.findFragmentById(R.id.dual_pane_detail);

        if (detailPaneFragment != null) {
            fragmentManager.beginTransaction().remove(detailPaneFragment).commit();
            fragmentManager.executePendingTransactions();
        }
    }

    private RecipeDetailsFragment getRecipeDetailsFragment() {
        return (RecipeDetailsFragment) getSupportFragmentManager().findFragmentByTag(
                RECIPE_DETAILS_FRAGMENT_TAG);
    }

    private StepDetailsFragment getStepDetailsFragment() {
        return (StepDetailsFragment) getSupportFragmentManager().findFragmentByTag(
                STEP_DETAILS_FRAGMENT_TAG);
    }
}
