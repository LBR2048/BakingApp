package com.udacity.bakingapp.recipes;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.utils.GuiUtils;

import java.util.ArrayList;
import java.util.List;

public class RecipesFragment extends android.support.v4.app.Fragment implements RecipesContract.View {

    //region Constants
    private static final String RECYCLER_VIEW_STATE = "recyclerViewState";
    private static final String RECYCLER_VIEW_DATA = "recyclerViewData";
    private static final int COLUMN_COUNT_THREE = 3;
    private static final int MIN_WIDTH_THREE_COLUMNS = 800;
    //endregion

    //region Member Variables
    private OnRecipesFragmentInteractionListener mListener;
    private RecipesAdapter mAdapter;
    private RecipesPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private List<Recipe> recipes;
    //endregion

    //region Constructors
    public RecipesFragment() {
    }

    @SuppressWarnings("unused")
    public static RecipesFragment newInstance() {
        RecipesFragment recipesFragment = new RecipesFragment();
        Bundle args = new Bundle();
        recipesFragment.setArguments(args);
        return recipesFragment;
    }
    //endregion

    //region Lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instantiate presenter
        mPresenter = new RecipesPresenter(this);
    }

    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        mRecyclerView = view.findViewById(R.id.recipe_list);

        // Set the adapter
        Context context = view.getContext();
        float widthDp = GuiUtils.getWidthDp(context);
        if (widthDp <= MIN_WIDTH_THREE_COLUMNS) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(context, COLUMN_COUNT_THREE));
        }
        mAdapter = new RecipesAdapter(new ArrayList<Recipe>(), mListener);
        mRecyclerView.setAdapter(mAdapter);

        // Load data
        if (savedInstanceState == null) {
            mPresenter.loadRecipes();
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Parcelable recyclerViewState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(RECYCLER_VIEW_STATE, recyclerViewState);
        outState.putParcelableArrayList(RECYCLER_VIEW_DATA, (ArrayList) recipes);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            // Restore RecyclerView data
            recipes = savedInstanceState.getParcelableArrayList(RECYCLER_VIEW_DATA);
            mAdapter.replaceData(recipes);

            // Restore RecyclerView state
            Parcelable recyclerViewState = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipesFragmentInteractionListener) {
            mListener = (OnRecipesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnIngredientsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    //endregion

    @Override
    public void showRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        mAdapter.replaceData(recipes);
    }

    public interface OnRecipesFragmentInteractionListener {
        void onRecipeClicked(Recipe recipe);
    }
}
