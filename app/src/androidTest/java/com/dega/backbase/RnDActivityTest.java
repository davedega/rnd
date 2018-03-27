package com.dega.backbase;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.assertEquals;

/**
 * Created by davedega on 27/03/18.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RnDActivityTest {


    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(RnDActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        activityRule.launchActivity(new Intent());
    }

    @Test
    public void showMap() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.dega.backbase", appContext.getPackageName());


        // Given I search for Alabama
        onView(withId(R.id.searchEditText))
                .perform(typeText("Alabama"), closeSoftKeyboard());

        // Then I verify if the first result is  not empty
        onData(anything()).inAdapterView(withId(R.id.entriesListView)).atPosition(0)
                .check(matches(not(withText(""))));

        // Then I verify if the first result is Alabama
        onData(anything())
                .inAdapterView(withId(R.id.entriesListView))
                .atPosition(0)
                .onChildView(withId(R.id.entryName))
                .check(matches(withText(containsString("Alabama, US"))));

        // Then tap on first element
        onData(anything()).inAdapterView(withId(R.id.entriesListView)).atPosition(0)
                .perform(click());


        // Then i tap on the marker with the same title
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains("Alabama, US"));
        marker.click();

    }
}