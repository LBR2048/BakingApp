package com.udacity.bakingapp.stepdetails;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.recipedetails.StepsAdapter;
import com.udacity.bakingapp.recipes.RecipesPresenter;

/**
 * A fragment representing a list of Items.
 * <p />
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class StepDetailsFragment extends Fragment implements StepDetailsContract.View {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_RECIPE_ID = "recipe-id";
    private static final String ARG_VIDEO_URL = "video-url";
    private static final String LOG_TAG = "Log Tag";
    private static final String VIDEO_FRAGMENT_TAG = "video-fragment-tag";

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private StepsAdapter mStepsAdapter;
    private RecipesPresenter mRecipesPresenter;
    private TextView mDescriptionView;
    private int mCurrentWindow;
    private Button mPreviousButton;
    private Button mNextButton;
    private StepDetailsPresenter mStepDetailsPresenter;
    private int mRecipeId;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailsFragment() {
    }

    @SuppressWarnings("unused")
    public static StepDetailsFragment newInstance(int columnCount, int recipeId) {
        StepDetailsFragment fragment = new StepDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(ARG_RECIPE_ID, recipeId);
        fragment.setArguments(args);
        return fragment;
    }

    //region Lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mRecipeId = getArguments().getInt(ARG_RECIPE_ID);
        }

        // Instantiate presenter
        mStepDetailsPresenter = new StepDetailsPresenter(this);
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        mDescriptionView = view.findViewById(R.id.step_description);
        mPreviousButton = view.findViewById(R.id.previous_button);
        mNextButton = view.findViewById(R.id.next_button);

        setupNavigationButtons();

//        mPlayerView.requestFocus();

//        initializePlayer();

        // Load data
//        mRecipesPresenter.loadRecipes();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnIngredientsFragmentInteractionListener");
        }
    }
    //endregion

    public void showStep(int recipeId, int stepId) {
        mStepDetailsPresenter.getStep(recipeId, stepId);
    }

    @Override
    public void showDescription(String description) {
        mDescriptionView.setText(description);
    }

    @Override
    public void showVideo(String videoUrl) {
        showVideoFragment(videoUrl);
    }

    private void showVideoFragment(String videoUrl) {
        if (getChildFragmentManager().findFragmentByTag(VIDEO_FRAGMENT_TAG) == null) {
            VideoFragment videoFragment = VideoFragment.newInstance(videoUrl);
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.videoContainerLayout, videoFragment, VIDEO_FRAGMENT_TAG)
                    .commit();
            getChildFragmentManager().executePendingTransactions();
        }

        VideoFragment videoFragment = (VideoFragment) getChildFragmentManager().findFragmentByTag(
                VIDEO_FRAGMENT_TAG);
        videoFragment.playVideo(videoUrl);
    }

    private void setupNavigationButtons() {
        mPreviousButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                mStepDetailsPresenter.getPreviousStep();
            }
        });

        mNextButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                mStepDetailsPresenter.getNextStep();
            }
        });
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
    public interface OnListFragmentInteractionListener {
        void previousStep();
        void nextStep();
    }
}
