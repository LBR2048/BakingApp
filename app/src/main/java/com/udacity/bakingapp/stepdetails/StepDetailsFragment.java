package com.udacity.bakingapp.stepdetails;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.udacity.bakingapp.R;

/**
 * A fragment representing a list of Items.
 * <p />
 */
public class StepDetailsFragment extends Fragment implements StepDetailsContract.View {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_RECIPE_ID = "recipe-id";
    private static final String ARG_STEP_ID = "step-id";
    private static final String VIDEO_FRAGMENT_TAG = "video-fragment-tag";

    private int mColumnCount = 1;
    private TextView mDescriptionView;
    private Button mPreviousButton;
    private Button mNextButton;
    private StepDetailsPresenter mStepDetailsPresenter;
    private int mRecipeId;
    private int mStepId;
    private boolean mLandscapeMode;
    private FrameLayout mVideoContainer;

    public StepDetailsFragment() {
    }

    @SuppressWarnings("unused")
    public static StepDetailsFragment newInstance(int columnCount, int recipeId, int stepId) {
        StepDetailsFragment fragment = new StepDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(ARG_RECIPE_ID, recipeId);
        args.putInt(ARG_STEP_ID, stepId);
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
            mStepId = getArguments().getInt(ARG_STEP_ID);
        }

        // Instantiate presenter
        mStepDetailsPresenter = new StepDetailsPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view;

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLandscapeMode = true;
            view = inflater.inflate(R.layout.fragment_step_details_landscape, container, false);

            hideSystemUI();

        } else {
            mLandscapeMode = false;
            view = inflater.inflate(R.layout.fragment_step_details, container, false);

            mDescriptionView = view.findViewById(R.id.step_description);
            mPreviousButton = view.findViewById(R.id.previous_button);
            mNextButton = view.findViewById(R.id.next_button);

            setupNavigationButtons();
        }

        mVideoContainer = view.findViewById(R.id.videoContainerLayout);

        mStepDetailsPresenter.getStep(mRecipeId, mStepId);

        return view;
    }
    //endregion

    public void showStep(int recipeId, int stepId) {
        mRecipeId = recipeId;
        mStepId = stepId;

        mStepDetailsPresenter.getStep(mRecipeId, mStepId);
    }

    @Override
    public void setStepId(int stepId) {
        mStepId = stepId;
    }

    @Override
    public void showStepTitle(String stepName) {
        if (getActivity() != null) {
            ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(stepName);
            }
        }
    }

    @Override
    public void showDescription(String description) {
        if (!mLandscapeMode) {
            mDescriptionView.setText(description);
        }
    }

    @Override
    public void showVideo(String videoUrl) {
        mVideoContainer.setVisibility(View.VISIBLE);

        if (getChildFragmentManager().findFragmentByTag(VIDEO_FRAGMENT_TAG) == null) {
            VideoFragment videoFragment = VideoFragment.newInstance(videoUrl);
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.videoContainerLayout, videoFragment, VIDEO_FRAGMENT_TAG)
                    .commit();
        } else {
            VideoFragment videoFragment =
                    (VideoFragment) getChildFragmentManager().findFragmentByTag(VIDEO_FRAGMENT_TAG);
            videoFragment.playVideo(videoUrl);
        }
    }

    @Override
    public void hideVideo() {
        mVideoContainer.setVisibility(View.GONE);

        Fragment videoFragment = getChildFragmentManager().findFragmentByTag(VIDEO_FRAGMENT_TAG);
        if (videoFragment != null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .remove(videoFragment)
                    .commit();
        }
    }

    @Override
    public void setPreviousButtonVisibility(boolean visibility) {
        if (!mLandscapeMode) {
            mPreviousButton.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public void setNextButtonVisibility(boolean visibility) {
        if (!mLandscapeMode) {
            mNextButton.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        }
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

    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

}
