package com.udacity.bakingapp.stepdetails;

import com.udacity.bakingapp.data.RecipesRepository;
import com.udacity.bakingapp.data.RecipesRepositoryImpl;
import com.udacity.bakingapp.model.Step;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 28/12/2017.
 */

public class StepDetailsPresenter implements StepDetailsContract.Presenter {

    private List<Step> mSteps;
    // TODO is this the step id or position?
    private int mCurrentStepId;

    private final StepDetailsContract.View mView;
    private final RecipesRepository mRepository;

    StepDetailsPresenter(StepDetailsContract.View view) {
        mView = view;
        mRepository = new RecipesRepositoryImpl();
    }

    @Override
    public void getStep(int recipeId, final int stepId) {
        mRepository.loadSteps(new RecipesRepository.LoadStepsCallback() {
            @Override
            public void onSuccess(List<Step> steps) {
                mSteps = steps;

                Step step = findStepById(stepId, steps);

                if (step != null) {
                    mCurrentStepId = stepId;
                    mView.setStepId(stepId);
                    mView.showTitle(step.getShortDescription());
                    mView.showDescription(step.getDescription());
                    String videoURL = step.getVideoURL();
                    showOrHideVideo(videoURL, step.getThumbnailURL());
                    setNavigationButtonsVisibility();
                }
            }

            @Override
            public void onFailure() {

            }
        }, recipeId);
    }

    @Override
    public void getPreviousStep() {
        Step step = mSteps.get(--mCurrentStepId);
        mView.setStepId(step.getIdentity());
        mView.showTitle(step.getShortDescription());
        mView.showDescription(step.getDescription());
        showOrHideVideo(step.getVideoURL(), step.getThumbnailURL());
        setNavigationButtonsVisibility();
    }

    @Override
    public void getNextStep() {
        Step step = mSteps.get(++mCurrentStepId);
        mView.setStepId(step.getIdentity());
        mView.showTitle(step.getShortDescription());
        mView.showDescription(step.getDescription());
        showOrHideVideo(step.getVideoURL(), step.getThumbnailURL());
        setNavigationButtonsVisibility();
    }

    private void showOrHideVideo(String videoURL, String imageUrl) {
        if (!videoURL.equals("")) {
            mView.showVideo(videoURL);
        } else {
            mView.showImage(imageUrl);
        }
    }

    private void setNavigationButtonsVisibility() {
        if (mCurrentStepId == 0) {
            mView.setPreviousButtonVisibility(false);
            mView.setNextButtonVisibility(true);
        } else if (mCurrentStepId + 1 == mSteps.size()) {
            mView.setPreviousButtonVisibility(true);
            mView.setNextButtonVisibility(false);
        } else {
            mView.setPreviousButtonVisibility(true);
            mView.setNextButtonVisibility(true);
        }
    }

    private Step findStepById(int stepId, List<Step> steps) {
        for (Step step : steps) {
            if (step.getIdentity() == stepId) {
                return step;
            }
        }
        return null;
    }

    private int findStepPositionById(int stepId, List<Step> steps) {
        for (int i = 0; i < steps.size(); i++) {
            if (steps.get(i).getIdentity() == stepId) {
                return i;
            }
        }
        return -1;
    }
}
