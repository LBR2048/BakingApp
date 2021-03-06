package com.udacity.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.stepdetails.StepDetailsFragment;
import com.udacity.bakingapp.steps.StepsFragment;
import com.udacity.bakingapp.utils.GuiUtils;

import static com.udacity.bakingapp.StepDetailsActivity.EXTRA_RECIPE_ID;
import static com.udacity.bakingapp.StepDetailsActivity.EXTRA_STEP_ID;

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

        showStepsFragment(mRecipeId);

        if (!mTwoPane) {
            removeDetailPaneFragment();
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
        mStepId = step.getIdentity();

        if (mTwoPane) {
            showStepDetailsFragment(recipeId, step.getIdentity());
        } else {
            showStepDetailsActivity();
        }
    }

    @Override
    public void onStepSelected(int stepId) {
        mStepId = stepId;
    }

    private void determineTwoPane() {
        mTwoPane = (findViewById(R.id.dual_pane_detail) != null);
    }

    private void showStepsFragment(int mRecipeId) {
        StepsFragment stepsFragment = (StepsFragment) getSupportFragmentManager()
                .findFragmentByTag(STEPS_FRAGMENT_TAG);

        if (stepsFragment == null) {
            stepsFragment = StepsFragment.newInstance(1, mRecipeId);
            GuiUtils.replaceFragment(this, R.id.dual_pane_master,
                    stepsFragment, STEPS_FRAGMENT_TAG);
        }
    }

    private void showStepDetailsFragment(int recipeId, int stepId) {
        StepDetailsFragment stepDetailsFragment = (StepDetailsFragment) getSupportFragmentManager()
                .findFragmentByTag(STEP_DETAILS_FRAGMENT_TAG);

        if (stepDetailsFragment == null) {
            stepDetailsFragment = StepDetailsFragment.newInstance(recipeId, stepId);
            GuiUtils.replaceFragment(this, R.id.dual_pane_detail,
                    stepDetailsFragment, STEP_DETAILS_FRAGMENT_TAG);
        } else {
            stepDetailsFragment.showStep(recipeId, stepId);
        }
    }

    private void showStepDetailsActivity() {
        Intent intent = new Intent(this, StepDetailsActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, mRecipeId);
        intent.putExtra(EXTRA_STEP_ID, mStepId);
        startActivity(intent);
    }

    private void removeDetailPaneFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment detailPaneFragment = fragmentManager.findFragmentById(
                R.id.dual_pane_detail);

        if (detailPaneFragment != null) {
            fragmentManager.beginTransaction().remove(detailPaneFragment).commit();
            fragmentManager.executePendingTransactions();
        }
    }
}
