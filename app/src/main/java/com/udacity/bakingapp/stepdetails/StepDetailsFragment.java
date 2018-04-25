package com.udacity.bakingapp.stepdetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.udacity.bakingapp.GuiUtils;
import com.udacity.bakingapp.R;

/**
 * A fragment representing a list of Items.
 * <p />
 */
public class StepDetailsFragment extends Fragment implements StepDetailsContract.View {

    //region Constants
    private static final String ARG_RECIPE_ID = "recipe-id";
    private static final String ARG_STEP_ID = "step-id";
    private static final String VIDEO_FRAGMENT_TAG = "video-fragment-tag";
    //endregion

    //region Member Variables
    private FrameLayout mVideoContainer;
    private TextView mDescriptionView;
    private Button mPreviousButton;
    private Button mNextButton;
    private int mRecipeId;
    private int mStepId;
    private StepDetailsPresenter mStepDetailsPresenter;
    private OnFragmentInteraction mListener;
    //endregion

    //region Constructors
    public StepDetailsFragment() {
    }

    @SuppressWarnings("unused")
    public static StepDetailsFragment newInstance(int recipeId, int stepId) {
        StepDetailsFragment fragment = new StepDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RECIPE_ID, recipeId);
        args.putInt(ARG_STEP_ID, stepId);
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

    //region Lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null) {
            mRecipeId = getArguments().getInt(ARG_RECIPE_ID);
            mStepId = getArguments().getInt(ARG_STEP_ID);
        }

        // Instantiate presenter
        mStepDetailsPresenter = new StepDetailsPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Hide system UI if device is a handset in landscape mode (show fullscreen video)
        if (GuiUtils.isHandsetLandscape(getActivity())) {
            GuiUtils.hideSystemUI(getActivity());
        }

        mVideoContainer = view.findViewById(R.id.videoContainerLayout);
        mDescriptionView = view.findViewById(R.id.step_description);
        mPreviousButton = view.findViewById(R.id.previous_button);
        mNextButton = view.findViewById(R.id.next_button);

        mStepDetailsPresenter.getStep(mRecipeId, mStepId);

        setupNavigationButtons();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteraction) {
            mListener = (OnFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement StepDetailsFragment.OnFragmentInteraction");
        }
    }
    //endregion

    public void showStep(int recipeId, int stepId) {
        mRecipeId = recipeId;
        mStepId = stepId;

        mListener.onStepSelected(stepId);
        mStepDetailsPresenter.getStep(mRecipeId, mStepId);
    }

    // TODO is this method really needed?
    @Override
    public void setStepId(int stepId) {
        mListener.onStepSelected(stepId);
        mStepId = stepId;
    }

    @Override
    public void showTitle(String stepName) {
        if (getActivity() != null) {
            ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(stepName);
            }
        }
    }

    @Override
    public void showDescription(String description) {
        if (mDescriptionView != null) {
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
        if (mPreviousButton != null) {
            mPreviousButton.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public void setNextButtonVisibility(boolean visibility) {
        if (mNextButton != null) {
            mNextButton.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        }
    }

    private void setupNavigationButtons() {
        if (mPreviousButton != null) {
            mPreviousButton.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    mStepDetailsPresenter.getPreviousStep();
                }
            });
        }

        if (mNextButton != null) {
            mNextButton.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    mStepDetailsPresenter.getNextStep();
                }
            });
        }
    }

    public interface OnFragmentInteraction {
        void onStepSelected(int stepId);
    }
}
