package com.coursera.aad.capstoneapp;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.coursera.aad.capstoneapp.utils.Constants.USER_TABLE_NAME;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;
import android.view.View;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.coursera.aad.capstoneapp.database.CountriesDbHelper;
import com.coursera.aad.capstoneapp.database.UserDbHelper;
import com.coursera.aad.capstoneapp.database.dao.CountryDao;
import com.coursera.aad.capstoneapp.database.dao.UserDao;
import com.coursera.aad.capstoneapp.models.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AppInstrumentedTest {
    private static final String TEST_FULL_NAME = "John Doe";
    private static final String TEST_EMAIL = "johndoe@example.com";
    private static final String TEST_PASSWORD = "johndoepassword";

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    private UserDao userDao;
    private CountryDao countryDao;

    private View decorView;
    private Context context;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        activityScenarioRule.getScenario().onActivity(activity -> decorView = activity.getWindow().getDecorView());

        UserDbHelper userDbHelper = new UserDbHelper(context);
        CountriesDbHelper countriesDbHelper = new CountriesDbHelper(context);

        // Clear the database
        userDbHelper.getWritableDatabase().execSQL("DELETE FROM " + USER_TABLE_NAME);

        // Initialize dbHelper instance
        userDao = new UserDao(userDbHelper);
        countryDao = new CountryDao(countriesDbHelper);
    }

    @Test
    public void testSignInSuccess() {
        onView(withId(R.id.etFullName)).perform(typeText(TEST_FULL_NAME));
        onView(withId(R.id.etEmail)).perform(typeText(TEST_EMAIL));
        onView(withId(R.id.etPassword)).perform(typeText(TEST_PASSWORD));
        onView(withId(R.id.etConfirmPassword)).perform(typeText(TEST_PASSWORD), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnSignIn)).perform(click());
        User user = userDao.read(1);
        assertNotNull(user);
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));
        onView(withId(R.id.statistics_page)).perform(click());
        onView(withId(R.id.fragment_statistics)).check(matches(isDisplayed()));
    }

    @Test
    public void testSignInFailureEmptyFields() {
        onView(withId(R.id.btnSignIn)).perform(click());
        User user = userDao.read(1);
        assertNull(user);
        onView(withText(context.getString(R.string.email_field_error_toast)))
                .inRoot(withDecorView(not(decorView))).check(matches(isDisplayed()));
    }

    @Test
    public void testSignUpSuccess() {
        onView(withId(R.id.etEmail)).perform(typeText(TEST_EMAIL));
        onView(withId(R.id.etPassword)).perform(typeText(TEST_PASSWORD));
        onView(withId(R.id.btnSignUp)).perform(click());
        User user = userDao.read(1);
        assertNotNull(user);
        assertEquals(TEST_EMAIL, user.getEmail());
        assertEquals(TEST_PASSWORD, user.getPassword());
        assertEquals(0, user.getStatus());
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));
        onView(withId(R.id.statistics_page)).perform(click());
        onView(withId(R.id.fragment_statistics)).check(matches(isDisplayed()));
    }

    @Test
    public void testSignUpFailureEmptyFields() {
        onView(withId(R.id.btnSignUp)).perform(click());
        User user = userDao.read(1);
        assertNull(user);
        onView(withText(R.string.email_field_error_toast)).inRoot(withDecorView(not(decorView))).check(matches(isDisplayed()));
    }
}
