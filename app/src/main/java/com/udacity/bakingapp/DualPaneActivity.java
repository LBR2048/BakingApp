package com.udacity.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.stepdetails.StepDetailsFragment;
import com.udacity.bakingapp.steps.StepsFragment;
import com.udacity.bakingapp.utils.GuiUtils;

import static com.udacity.bakingapp.SinglePaneActivity.EXTRA_RECIPE_ID;
import static com.udacity.bakingapp.SinglePaneActivity.EXTRA_STEP_ID;

public class DualPaneActivity extends AppCompatActivity implements
        StepsFragment.OnDetailsFragmentInteraction,
        StepDetailsFragment.OnFragmentInteraction {

    //region Constants
    private static final String STEPS_FRAGMENT_TAG = "steps_fragment_tag";
    private static final String STEP_DETAILS_FRAGMENT_TAG = "step_details_fragment_tag";
    private static final String STATE_IS_STEP_SELECTED = "state-is-key-step-selected";
    private static final String STATE_STEP_ID = "state-step-id";
    //endregion

    //region Member Variables
    private boolean mTwoPane;
    private boolean mStepSelected;
    private int mStepId;
    private int mRecipeId;
    //endregion

    //region Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual_pane);

        if (savedInstanceState != null) {
            mStepSelected = savedInstanceState.getBoolean(STATE_IS_STEP_SELECTED, false);
            mStepId = savedInstanceState.getInt(STATE_STEP_ID);
        }

        mRecipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, -1);

        determineTwoPane();

        if (mTwoPane) {
            if (mStepSelected) {
                showSteps(mRecipeId, R.id.dual_pane_master);
                showStepDetails(mRecipeId, mStepId, R.id.dual_pane_detail);
            } else {
                showSteps(mRecipeId, R.id.dual_pane_master);
            }
        } else {
            showSteps(mRecipeId, R.id.dual_pane_master);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(STATE_IS_STEP_SELECTED, mStepSelected);
        outState.putInt(STATE_STEP_ID, mStepId);
    }
    //endregion

    @Override
    public void onStepClicked(int recipeId, Step step) {
        mStepSelected = true;
        mStepId = step.getId();

        if (mTwoPane) {
            showStepDetails(recipeId, step.getId(), R.id.dual_pane_detail);
        } else {
            // Open step details in SinglePaneActivity
            Intent intent = new Intent(this, SinglePaneActivity.class);
            intent.putExtra(EXTRA_RECIPE_ID, mRecipeId);
            intent.putExtra(EXTRA_STEP_ID, mStepId);
            startActivity(intent);
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
}
