package com.udacity.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.steps.StepsFragment;
import com.udacity.bakingapp.stepdetails.StepDetailsFragment;
import com.udacity.bakingapp.utils.GuiUtils;

public class DualPaneActivity extends AppCompatActivity
        implements StepsFragment.OnDetailsFragmentInteraction,
        StepDetailsFragment.OnFragmentInteraction {

    //region Constants
    public static final String EXTRA_RECIPE_ID = "recipeId";
    private static final String STEPS_FRAGMENT_TAG = "steps_fragment_tag";
    private static final String STEP_DETAILS_FRAGMENT_TAG = "step_details_fragment_tag";
    private static final String KEY_STEP_SELECTED = "key-step-selected";
    private static final String KEY_STEP_ID = "key-step-id";
    //endregion

    //region Member Variables
    private boolean mTwoPane;
    private boolean mStepSelected;
    private int mStepId;
    //endregion

    //region Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual_pane);

        if (savedInstanceState != null) {
            mStepSelected = savedInstanceState.getBoolean(KEY_STEP_SELECTED, false);
            mStepId = savedInstanceState.getInt(KEY_STEP_ID);
        }

        int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, -1);

        determineTwoPane();

        if (!mTwoPane) {
            // Single pane

            // Detail pane fragment must be removed because it is using setSaveInstanceState(true)
            removeDetailPaneFragment();

            if (!mStepSelected) {
                showSteps(recipeId, R.id.dual_pane_master);
            } else {
                showStepDetails(recipeId, mStepId, R.id.dual_pane_master);
            }

        } else {
            // Two pane
            if (!mStepSelected) {
                showSteps(recipeId, R.id.dual_pane_master);
                removeDetailPaneFragment();
            } else {
                showSteps(recipeId, R.id.dual_pane_master);
                showStepDetails(recipeId, mStepId, R.id.dual_pane_detail);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(KEY_STEP_SELECTED, mStepSelected);
        outState.putInt(KEY_STEP_ID, mStepId);
    }
    //endregion

    @Override
    public void onIngredientClicked(Ingredient ingredient) {

    }

    @Override
    public void onStepClicked(int recipeId, Step step) {
        mStepSelected = true;
        mStepId = step.getId();
        if (mTwoPane) {
            showStepDetails(recipeId, step.getId(), R.id.dual_pane_detail);
            // TODO add to backstack
        } else {
            showStepDetails(recipeId, step.getId(), R.id.dual_pane_master);
        }
    }

    @Override
    public void onStepSelected(int stepId) {
        mStepId = stepId;
    }

    private void determineTwoPane() {
        mTwoPane = (findViewById(R.id.dual_pane_detail) != null);
    }

    private void showSteps(int mRecipeId, int containerViewId) {
        StepsFragment stepsFragment = (StepsFragment) getSupportFragmentManager()
                .findFragmentByTag(STEPS_FRAGMENT_TAG);

        if (stepsFragment == null) {
            stepsFragment = StepsFragment.newInstance(1, mRecipeId);
            GuiUtils.replaceFragment(this, containerViewId, stepsFragment, STEPS_FRAGMENT_TAG);
        }
    }

    private void showStepDetails(int recipeId, int stepId, int containerViewId) {
        StepDetailsFragment stepDetailsFragment = (StepDetailsFragment) getSupportFragmentManager()
                .findFragmentByTag(STEP_DETAILS_FRAGMENT_TAG);

        if (stepDetailsFragment == null) {
            stepDetailsFragment = StepDetailsFragment.newInstance(recipeId, stepId);
            if (mTwoPane) {
                GuiUtils.replaceFragment(this, containerViewId, stepDetailsFragment, STEP_DETAILS_FRAGMENT_TAG);
            } else {
                GuiUtils.replaceFragmentWithBackStack(this, containerViewId, stepDetailsFragment, STEP_DETAILS_FRAGMENT_TAG);
            }
        } else {
            stepDetailsFragment.showStep(recipeId, stepId);
        }
    }


    private void removeDetailPaneFragment() {
        Fragment detailPaneFragment = getSupportFragmentManager().findFragmentById(R.id.dual_pane_detail);

        if (detailPaneFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(detailPaneFragment).commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }

}
