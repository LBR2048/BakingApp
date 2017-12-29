package com.udacity.bakingapp.stepdetails;

import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.repository.RecipesRepository;
import com.udacity.bakingapp.repository.RecipesRepositoryImpl;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 28/12/2017.
 */

public class StepDetailsPresenter implements StepDetailsContract.Presenter {

    private List<Step> mSteps;
    private int mCurrentStepPosition;

    private StepDetailsContract.View mView;
    private RecipesRepository mRepository;

    public StepDetailsPresenter(StepDetailsContract.View view) {
        mView = view;
        mRepository = new RecipesRepositoryImpl();
    }

    @Override
    public void getFirstStep(int recipeId) {
        mRepository.loadSteps(new RecipesRepository.LoadStepsCallback() {
            @Override
            public void onStepsLoaded(List<Step> steps) {
                Step step = steps.get(0);
                mView.showDescription(step.getDescription());

                String videoURL = step.getVideoURL();
                if (!videoURL.equals("")) {
                    mView.showVideo(videoURL);
                }
//                mView.showStep(step);
            }
        }, recipeId);

    }

    @Override
    public void getPreviousStep() {
//        mView.showStep(mSteps.get(--mCurrentStepPosition));
    }

    @Override
    public void getNextStep() {
//        mView.showStep(mSteps.get(++mCurrentStepPosition));
    }
}
