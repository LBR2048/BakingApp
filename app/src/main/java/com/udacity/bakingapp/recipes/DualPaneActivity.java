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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual_pane);

        int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, -1);

        determineTwoPane();

        showRecipeDetailsFragment(recipeId);

        if (!mTwoPane) {
            removeDetailPaneFragment();
        }
    }

    @Override
    public void onIngredientClicked(Ingredient ingredient) {

    }

    @Override
    public void onStepClicked(int recipeId, Step step) {
        StepDetailsFragment stepDetailsFragment =
                (StepDetailsFragment) getSupportFragmentManager().findFragmentByTag(
                        STEP_DETAILS_FRAGMENT_TAG);

        Fragment detailPaneFragment = getSupportFragmentManager().findFragmentById(
                R.id.main_activity_detail_pane);

        if (mTwoPane) {
            if (detailPaneFragment != null && detailPaneFragment instanceof StepDetailsFragment) {
                stepDetailsFragment = (StepDetailsFragment) detailPaneFragment;
                stepDetailsFragment.showStep(recipeId, step.getId());
            } else {
                stepDetailsFragment =
                        StepDetailsFragment.newInstance(1, recipeId, step.getId());
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_activity_detail_pane, stepDetailsFragment,
                                STEP_DETAILS_FRAGMENT_TAG)
                        .commit();
            }
        } else {
            if (stepDetailsFragment == null) {
                stepDetailsFragment =
                        StepDetailsFragment.newInstance(1, recipeId, step.getId());
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_activity_master_pane, stepDetailsFragment,
                                STEP_DETAILS_FRAGMENT_TAG)
                        .addToBackStack(null)
                        .commit();
            } else {
                stepDetailsFragment.showStep(recipeId, step.getId());
            }
        }
    }

    private void determineTwoPane() {
        mTwoPane = (findViewById(R.id.main_activity_detail_pane) != null);
    }

    private void showRecipeDetailsFragment(int mRecipeId) {
        RecipeDetailsFragment recipeDetailsFragment =
                (RecipeDetailsFragment) getSupportFragmentManager().findFragmentByTag(
                        RECIPE_DETAILS_FRAGMENT_TAG);

        if (recipeDetailsFragment == null) {
            recipeDetailsFragment = RecipeDetailsFragment.newInstance(1, mRecipeId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_activity_master_pane, recipeDetailsFragment, RECIPE_DETAILS_FRAGMENT_TAG)
                    .commit();
        }
    }

    private void removeDetailPaneFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment detailPaneFragment = fragmentManager.findFragmentById(
                R.id.main_activity_detail_pane);

        if (detailPaneFragment != null) {
            fragmentManager.beginTransaction().remove(detailPaneFragment).commit();
            fragmentManager.executePendingTransactions();
        }
    }
}
