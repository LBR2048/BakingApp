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

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p />
 * Activities containing this fragment MUST implement the {@link OnRecipesFragmentInteractionListener}
 * interface.
 */
public class RecipesFragment extends android.support.v4.app.Fragment implements RecipesContract.View {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String RECYCLER_VIEW_STATE = "recyclerViewState";

    private int mColumnCount = 1;
    private OnRecipesFragmentInteractionListener mListener;
    private RecipesAdapter mRecipesAdapter;
    private RecipesPresenter mRecipesPresenter;
    private RecyclerView mRecyclerView;

    public RecipesFragment() {
    }

    @SuppressWarnings("unused")
    public static RecipesFragment newInstance(int columnCount) {
        RecipesFragment recipesFragment = new RecipesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        recipesFragment.setArguments(args);
        return recipesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        // Instantiate presenter
        mRecipesPresenter = new RecipesPresenter(this);
    }

    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        mRecyclerView = view.findViewById(R.id.recipe_list);

        // Set the adapter
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        mRecipesAdapter = new RecipesAdapter(new ArrayList<Recipe>(), mListener);
        mRecyclerView.setAdapter(mRecipesAdapter);

        // Load data
        mRecipesPresenter.loadRecipes();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Parcelable recyclerViewState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(RECYCLER_VIEW_STATE, recyclerViewState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
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

    @Override
    public void showRecipes(List<Recipe> recipes) {
        mRecipesAdapter.replaceData(recipes);
    }

    public interface OnRecipesFragmentInteractionListener {
        void onRecipeClicked(Recipe recipe);
    }
}
