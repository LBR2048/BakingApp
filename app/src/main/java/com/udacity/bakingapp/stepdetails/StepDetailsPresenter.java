package com.udacity.bakingapp.stepdetails;

import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.data.RecipesRepository;
import com.udacity.bakingapp.data.RecipesRepositoryImpl;

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
                    showOrHideVideo(videoURL);
                    setNavigationButtonsVisibility();
                }
            }
        }, recipeId);
    }

    @Override
    public void getPreviousStep() {
        Step step = mSteps.get(--mCurrentStepId);
        mView.setStepId(step.getId());
        mView.showTitle(step.getShortDescription());
        mView.showDescription(step.getDescription());
        showOrHideVideo(step.getVideoURL());
        setNavigationButtonsVisibility();
    }

    @Override
    public void getNextStep() {
        Step step = mSteps.get(++mCurrentStepId);
        mView.setStepId(step.getId());
        mView.showTitle(step.getShortDescription());
        mView.showDescription(step.getDescription());
        showOrHideVideo(step.getVideoURL());
        setNavigationButtonsVisibility();
    }

    private void showOrHideVideo(String videoURL) {
        if (!videoURL.equals("")) {
            mView.showVideo(videoURL);
        } else {
            mView.hideVideo();
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
            if (step.getId() == stepId) {
                return step;
            }
        }
        return null;
    }

    private int findStepPositionById(int stepId, List<Step> steps) {
        for (int i = 0; i < steps.size(); i++) {
            if (steps.get(i).getId() == stepId) {
                return i;
            }
        }
        return -1;
    }
}
