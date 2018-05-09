package com.nickwelna.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ViewStepsTest {

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

        //verify steps are displayed
        onView(withId(R.id.steps_recycler_view)).check(ViewAssertions.matches(isDisplayed()));

    }

    @After
    public void unregisterIdlingResource() {

        if (idlingResource != null) {

            IdlingRegistry.getInstance().unregister(idlingResource);

        }

    }

}
