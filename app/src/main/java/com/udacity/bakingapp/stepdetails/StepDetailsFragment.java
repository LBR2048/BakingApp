package com.udacity.bakingapp.stepdetails;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.recipedetails.StepsAdapter;
import com.udacity.bakingapp.recipes.RecipesPresenter;

/**
 * A fragment representing a list of Items.
 * <p />
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class StepDetailsFragment extends Fragment implements StepsDetailsView {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_VIDEO_URL = "video-url";
    private static final String LOG_TAG = "Log Tag";
    private static final String VIDEO_FRAGMENT_TAG = "video-fragment-tag";

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private StepsAdapter mStepsAdapter;
    private RecipesPresenter mRecipesPresenter;
    private TextView mDescriptionView;
    private int mCurrentWindow;
    private String mVideoUrl;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailsFragment() {
    }

    @SuppressWarnings("unused")
    public static StepDetailsFragment newInstance(int columnCount, String videoUrl) {
        StepDetailsFragment fragment = new StepDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_VIDEO_URL, videoUrl);
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
            mVideoUrl = getArguments().getString(ARG_VIDEO_URL);
        }

        // Instantiate presenter
//        mRecipesPresenter = new RecipesPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        mDescriptionView = (TextView) view.findViewById(R.id.step_description);
        final Button previousButton = (Button) view.findViewById(R.id.previous_button);
        Button nextButton  = (Button) view.findViewById(R.id.next_button);

        if (getChildFragmentManager().findFragmentByTag(VIDEO_FRAGMENT_TAG) == null) {
            VideoFragment videoFragment = VideoFragment.newInstance(mVideoUrl);
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.videoContainerLayout, videoFragment, VIDEO_FRAGMENT_TAG)
                    .commit();
        }

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "P", Toast.LENGTH_SHORT).show();
                mListener.previousStep();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "N", Toast.LENGTH_SHORT).show();
                mListener.nextStep();
            }
        });

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

    @Override
    public void showStepDetails(Step step) {
        mDescriptionView.setText(step.getDescription());
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
