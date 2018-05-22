package com.udacity.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    public static final int SLEEP_MILLIS = 3000;
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void openApp_checkRecipeNames() {

        try{
            Thread.sleep(SLEEP_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recipe_list))
                .check(matches(hasDescendant(withText("Nutella Pie"))));
        onView(withId(R.id.recipe_list))
                .check(matches(hasDescendant(withText("Brownies"))));
        onView(withId(R.id.recipe_list))
                .check(matches(hasDescendant(withText("Yellow Cake"))));
        onView(withId(R.id.recipe_list))
                .check(matches(hasDescendant(withText("Cheesecake"))));
    }

    @Test
    public void clickNutellaPie_checkRecipeIntroductionExists() {

        try{
            Thread.sleep(SLEEP_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        try{
            Thread.sleep(SLEEP_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.step_list))
                .check(matches(hasDescendant(withText("Recipe Introduction"))));

    }

    @Test
    public void clickNutellaPieRecipeIntroduction_checkButtonsVisibility() {

        try{
            Thread.sleep(SLEEP_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        try{
            Thread.sleep(SLEEP_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.step_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        try{
            Thread.sleep(SLEEP_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.next_button)).check(matches(isDisplayed()));
        onView(withId(R.id.previous_button)).check(matches(not(isDisplayed())));
    }
}
