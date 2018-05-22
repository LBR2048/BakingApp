package com.udacity.bakingapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingapp.stepdetails.StepDetailsFragment;
import com.udacity.bakingapp.utils.GuiUtils;

public class SinglePaneActivity extends AppCompatActivity
        implements StepDetailsFragment.OnFragmentInteraction {

    public static final String EXTRA_RECIPE_ID = "extra-recipe-id";
    public static final String EXTRA_STEP_ID = "extra-step-id";
    private static final String STEP_DETAILS_FRAGMENT_TAG = "step-details-fragment-tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_pane);

        setTitle();

        if (savedInstanceState == null) {
            int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, -1);
            int stepId = getIntent().getIntExtra(EXTRA_STEP_ID, -1);

            showStepDetailsFragment(recipeId, stepId);
        }
    }

    @Override
    public void onStepSelected(int stepId) {

    }

    private void setTitle() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.recipes_title);
        }
    }

    private void showStepDetailsFragment(int recipeId, int stepId) {
        StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(recipeId, stepId);
        GuiUtils.replaceFragment(this, R.id.single_pane_activity, stepDetailsFragment,
                STEP_DETAILS_FRAGMENT_TAG);
    }
}
