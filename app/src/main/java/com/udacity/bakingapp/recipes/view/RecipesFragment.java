package com.udacity.bakingapp.recipes.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.recipes.presenter.RecipesPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p />
 * Activities containing this fragment MUST implement the {@link OnRecipesFragmentInteractionListener}
 * interface.
 */
public class RecipesFragment extends Fragment implements RecipesView {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String RECYCLER_VIEW_STATE = "recyclerViewState";

    private int mColumnCount = 1;
    private OnRecipesFragmentInteractionListener mListener;
    private RecipeAdapter mRecipeAdapter;
    private RecipesPresenterImpl mRecipesPresenter;
    private RecyclerView mRecyclerView;

    public RecipesFragment() {
    }

    @SuppressWarnings("unused")
    public static RecipesFragment newInstance(int columnCount) {
        RecipesFragment fragment = new RecipesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        // Instantiate presenter
        mRecipesPresenter = new RecipesPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recipe_list);

        // Set the adapter
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        mRecipeAdapter = new RecipeAdapter(new ArrayList<Recipe>(), mListener);
        mRecyclerView.setAdapter(mRecipeAdapter);

        // Load data
        mRecipesPresenter.loadRecipes();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
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
        mRecipeAdapter.replaceData(recipes);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRecipesFragmentInteractionListener {
        void onRecipeClicked(Recipe recipe);
    }
}
