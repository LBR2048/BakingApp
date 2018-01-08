package com.udacity.bakingapp.recipes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.recipedetails.RecipeDetailsFragment;
import com.udacity.bakingapp.stepdetails.StepDetailsFragment;

public class MainActivity extends AppCompatActivity
        implements RecipesFragment.OnRecipesFragmentInteractionListener,
        RecipeDetailsFragment.OnDetailsFragmentInteraction {

    private static final String RECIPES_FRAGMENT_TAG = "recipes_fragment_tag";
    private static final String RECIPE_DETAILS_FRAGMENT_TAG = "recipe_details_fragment_tag";
    private static final String STEP_DETAILS_FRAGMENT_TAG = "step_details_tag";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        determineTwoPane();

        setTitle();

        final FragmentManager fragmentManager = getSupportFragmentManager();

        if (!mTwoPane) {
            Fragment detailPaneFragment = getSupportFragmentManager().findFragmentById(
                    R.id.main_activity_detail_pane);

            if (detailPaneFragment != null) {
                getSupportFragmentManager().beginTransaction().remove(detailPaneFragment).commit();
                fragmentManager.executePendingTransactions();
            }
        }

        RecipesFragment recipesFragment;
        if (fragmentManager.findFragmentByTag(RECIPES_FRAGMENT_TAG) == null) {
            recipesFragment = RecipesFragment.newInstance(1);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_activity_master_pane, recipesFragment, RECIPES_FRAGMENT_TAG)
                    .commit();
        }

        fragmentManager.addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        Fragment fragment = fragmentManager.findFragmentById(
                                R.id.main_activity_master_pane);
                        if (RECIPES_FRAGMENT_TAG.equals(fragment.getTag())) {
                            setTitle();
                        }
                    }
                });
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        RecipeDetailsFragment recipeDetailsFragment =
                (RecipeDetailsFragment) getSupportFragmentManager().findFragmentByTag(
                        RECIPE_DETAILS_FRAGMENT_TAG);

        if (recipeDetailsFragment == null) {
            recipeDetailsFragment = RecipeDetailsFragment.newInstance(1, recipe.getId());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_activity_master_pane, recipeDetailsFragment, RECIPE_DETAILS_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
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

    private void setTitle() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle("Recipes");
        }
    }

    private void determineTwoPane() {
        mTwoPane = (findViewById(R.id.main_activity_detail_pane) != null);
    }
}
