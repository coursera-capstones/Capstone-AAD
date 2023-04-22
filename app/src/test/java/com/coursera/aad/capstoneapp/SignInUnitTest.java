package com.coursera.aad.capstoneapp;


import static com.coursera.aad.capstoneapp.utils.Utils.isValidSignInData;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import android.content.Context;

import com.coursera.aad.capstoneapp.models.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SignInUnitTest {
    @Test
    public void testIsValidSignInData() {
        // Create a mock Context object
        Context context = mock(Context.class);

        // Create a User object
        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        // Test the function with valid input
        assertTrue(isValidSignInData(context, user, "password"));

        // Test the function with an empty email
        user.setEmail("");
        assertFalse(isValidSignInData(context, user, "password"));

        // Test the function with an empty password
        user.setEmail("john.doe@example.com");
        user.setPassword("");
        assertFalse(isValidSignInData(context, user, "password"));

        // Test the function with an empty password confirmation
        assertFalse(isValidSignInData(context, user, ""));

        // Test the function with mismatched password and confirmation
        assertFalse(isValidSignInData(context, user, "wrong_password"));

        // Test the function with null User object
        assertFalse(isValidSignInData(context, null, "password"));

        // Test the function with null Context object
        assertFalse(isValidSignInData(null, user, "password"));

        // Test the function with null password confirmation
        assertFalse(isValidSignInData(context, user, null));
    }
}
