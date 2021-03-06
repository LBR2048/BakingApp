package com.udacity.bakingapp.steps;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p />
 * Activities containing this fragment MUST implement the {@link OnDetailsFragmentInteraction}
 * interface.
 */
public class StepsFragment extends Fragment
        implements StepsAdapter.OnStepsAdapterInteraction, StepsContract.View {

    //region Constants
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String RECYCLER_VIEW_STATE = "recycler_view_state";
    private static final String RECYCLER_VIEW_DATA = "recycler_view_data";
    private static final String ARG_RECIPE_ID = "arg_recipe_id";
    //endregion

    //region Member Variables
    private int mColumnCount = 1;
    private OnDetailsFragmentInteraction mListener;
    private StepsAdapter mStepsAdapter;
    private RecyclerView mStepList;
    private TextView mIngredientText;
    private List<Step> mSteps;
    private StepsPresenter mStepsPresenter;
    private int mRecipeId;
    //endregion

    //region Constructors
    public StepsFragment() {
    }

    @SuppressWarnings("unused")
    public static StepsFragment newInstance(int columnCount, int recipeId) {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(ARG_RECIPE_ID, recipeId);
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

    //region Lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mRecipeId = getArguments().getInt(ARG_RECIPE_ID);
        }

        // Instantiate presenter
        mStepsPresenter = new StepsPresenter(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save RecyclerView data
        outState.putParcelableArrayList(RECYCLER_VIEW_DATA, (ArrayList) mSteps);

        // Save RecyclerView state
        Parcelable recyclerViewState = mStepList.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(RECYCLER_VIEW_STATE, recyclerViewState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            // Restore RecyclerView data
            mSteps = savedInstanceState.getParcelableArrayList(RECYCLER_VIEW_DATA);
            mStepsAdapter.replaceData(mSteps);

            // Restore RecyclerView state
            Parcelable recyclerViewState = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE);
            mStepList.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }

    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_details, container, false);
        mStepList = view.findViewById(R.id.step_list);
        mIngredientText = view.findViewById(R.id.ingredient_text);

        // Set the adapter
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            mStepList.setLayoutManager(new LinearLayoutManager(context));
        } else {
            mStepList.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        mStepsAdapter = new StepsAdapter(new ArrayList<Step>(), this);
        mStepList.setAdapter(mStepsAdapter);

        if (savedInstanceState == null) {
            mStepsPresenter.getRecipeDetails(mRecipeId);
        }

        ViewCompat.setNestedScrollingEnabled(mStepList, false);

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
    //endregion

    @Override
    public void showRecipeName(String title) {
        if (getActivity() != null) {
            ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(title);
            }
        }
    }

    @Override
    public void showIngredients(List<Ingredient> ingredients) {
        mIngredientText.setText(Utils.formatIngredientsString(ingredients));
    }

    @Override
    public void showSteps(List<Step> steps) {
        mSteps = steps;
        mStepsAdapter.replaceData(steps);
    }

    @Override
    public void onStepClicked(Step step) {
        mListener.onStepClicked(mRecipeId, step);
    }

    public interface OnDetailsFragmentInteraction {
        void onStepClicked(int recipeId, Step step);
    }
}
