package com.udacity.bakingapp.recipedetails;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.recipes.RecipesPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p />
 * Activities containing this fragment MUST implement the {@link OnDetailsFragmentInteraction}
 * interface.
 */
public class RecipeDetailsFragment extends Fragment implements RecipeDetailsContract.View {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String RECYCLER_VIEW_STATE = "recycler_view_state";
    private static final String ARG_RECIPE_ID = "arg_recipe_id";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnDetailsFragmentInteraction mListener;
    private StepsAdapter mStepsAdapter;
    private IngredientsAdapter mIngredientsAdapter;
    private RecipesPresenter mRecipesPresenter;
    private RecyclerView mStepList;
    private RecyclerView mIngredientList;
    private TextView mIngredientText;
    private Bundle mBundleRecyclerViewState;
    private List<Step> mSteps;
    private List<Ingredient> mIngredients;
    private RecipeDetailsPresenter mRecipeDetailsPresenter;
    private int mRecipeId;

    public RecipeDetailsFragment() {
    }

    @SuppressWarnings("unused")
    public static RecipeDetailsFragment newInstance(int columnCount, int recipeId) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(ARG_RECIPE_ID, recipeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mRecipeId = getArguments().getInt(ARG_RECIPE_ID);
        }

        // Instantiate presenter
        mRecipeDetailsPresenter = new RecipeDetailsPresenter(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Parcelable recyclerViewState = mStepList.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(RECYCLER_VIEW_STATE, recyclerViewState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mStepsAdapter.replaceData(mSteps);

            Parcelable recyclerViewState = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE);
            mStepList.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_details, container, false);
        mStepList = (RecyclerView) view.findViewById(R.id.step_list);
//        mIngredientList = (RecyclerView) view.findViewById(R.id.ingredient_list);
        mIngredientText = (TextView) view.findViewById(R.id.ingredient_text);

        // Set the adapter
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            mStepList.setLayoutManager(new LinearLayoutManager(context));
        } else {
            mStepList.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        mStepsAdapter = new StepsAdapter(new ArrayList<Step>(), mListener);
        mStepList.setAdapter(mStepsAdapter);

        mRecipeDetailsPresenter.getRecipeDetails(mRecipeId);

//        mIngredientsAdapter = new IngredientsAdapter(new ArrayList<Ingredient>(), mListener);
//        mIngredientList.setAdapter(mIngredientsAdapter);
        // Load data
//        mRecipesPresenter.loadRecipes();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailsFragmentInteraction) {
            mListener = (OnDetailsFragmentInteraction) context;
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
    public void showIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
//        mIngredientsAdapter.replaceData(ingredients);
        mIngredientText.setText(ingredients.toString());
    }

    @Override
    public void showSteps(List<Step> steps) {
        mSteps = steps;
        mStepsAdapter.replaceData(steps);
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
    public interface OnDetailsFragmentInteraction {
        void onIngredientClicked(Ingredient ingredient);
        void onStepClicked(Step step);
    }
}
