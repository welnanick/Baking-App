package com.nickwelna.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nickwelna.bakingapp.activities.SelectRecipeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ViewDetailsTest {

    @Rule
    public final ActivityTestRule<SelectRecipeActivity> activityTestRule =
            new ActivityTestRule<>(SelectRecipeActivity.class);
    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResource() {

        idlingResource = activityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);

    }

    @Test
    public void idlingResourceTest() {

        // Click on the first recipe
        onView(withId(R.id.recipe_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //click view steps button
        onView(withId(R.id.recipe_steps_button)).perform(click());

        //click on first step
        onView(withId(R.id.steps_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Verify details are displayed
        onView(withId(R.id.step_video)).check(matches(isDisplayed()));
        onView(withId(R.id.description_text_view)).check(matches(withText("Recipe Introduction")));

    }

    @After
    public void unregisterIdlingResource() {

        if (idlingResource != null) {

            IdlingRegistry.getInstance().unregister(idlingResource);

        }

    }

}
