package com.udacity.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.recipes.RecipesFragment;
import com.udacity.bakingapp.utils.GuiUtils;

public class MainActivity extends AppCompatActivity
        implements RecipesFragment.OnRecipesFragmentInteractionListener {

    private static final String RECIPES_FRAGMENT_TAG = "recipes_fragment_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_pane);

        setTitle();

        if(savedInstanceState == null) {
            showRecipesFragment();
        }
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        Intent intent = new Intent(this, DualPaneActivity.class);
        intent.putExtra(DualPaneActivity.EXTRA_RECIPE_ID, recipe.getId());
        startActivity(intent);
    }

    private void setTitle() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.recipes_title);
        }
    }

    private void showRecipesFragment() {
        RecipesFragment recipesFragment = RecipesFragment.newInstance();
        GuiUtils.replaceFragment(this, R.id.single_pane_activity, recipesFragment,
                RECIPES_FRAGMENT_TAG);
    }
}
