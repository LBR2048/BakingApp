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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.utils.GuiUtils;

/**
 * A fragment representing a list of Items.
 * <p />
 */
public class StepDetailsFragment extends Fragment implements StepDetailsContract.View {

    //region Constants
    private static final String ARG_RECIPE_ID = "recipe-id";
    private static final String ARG_STEP_ID = "step-id";
    private static final String VIDEO_FRAGMENT_TAG = "video-fragment-tag";
    private static final String STATE_RECIPE_ID = "state-recipe-id";
    private static final String STATE_STEP_ID = "state-step-id";
    public static final String IMAGE_VIEW_TAG = "image-view-tag";
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

        if (savedInstanceState == null) {
            if (getArguments() != null) {
                mRecipeId = getArguments().getInt(ARG_RECIPE_ID);
                mStepId = getArguments().getInt(ARG_STEP_ID);
            }
        } else {
            mRecipeId = savedInstanceState.getInt(STATE_RECIPE_ID);
            mStepId = savedInstanceState.getInt(STATE_STEP_ID);
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
        if (getActivity() != null) {
            if (GuiUtils.isHandsetLandscape(getActivity())) {
                GuiUtils.hideSystemUI(getActivity());
            }
        }

        mVideoContainer = view.findViewById(R.id.videoContainerLayout);
        mDescriptionView = view.findViewById(R.id.step_description);
        mPreviousButton = view.findViewById(R.id.previous_button);
        mNextButton = view.findViewById(R.id.next_button);

        if (savedInstanceState == null) {
            mStepDetailsPresenter.getStep(mRecipeId, mStepId);
        }

        setupNavigationButtons();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(STATE_RECIPE_ID, mRecipeId);
        outState.putInt(STATE_STEP_ID, mStepId);
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
        // Hide image
        ImageView imageView = mVideoContainer.findViewWithTag(IMAGE_VIEW_TAG);
        if (imageView != null) {
            mVideoContainer.removeView(imageView);
        }

        // Show video
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
    public void showImage(String imageUrl) {
        // Hide video
        Fragment videoFragment = getChildFragmentManager().findFragmentByTag(VIDEO_FRAGMENT_TAG);
        if (videoFragment != null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .remove(videoFragment)
                    .commit();
        }

        // Show image
        ImageView imageView = mVideoContainer.findViewWithTag(IMAGE_VIEW_TAG);
        if (imageView == null) {
            imageView = new ImageView(getContext());
            imageView.setTag(IMAGE_VIEW_TAG);
            mVideoContainer.addView(imageView);
        }

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).error(R.mipmap.ic_launcher).into(imageView);
        } else {
            imageView.setImageResource(R.mipmap.ic_launcher);
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
