package com.coursera.aad.capstoneapp;

import static com.coursera.aad.capstoneapp.utils.Utils.isValidSignUpData;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SignUpUnitTest {
    private Context context;

    @Before
    public void setUp() {
        context = mock(Context.class);
    }

    @Test
    public void testValidInput() {
        String email = "test@example.com";
        String pass = "password";
        assertTrue(isValidSignUpData(context, email, pass));
    }

    @Test
    public void testEmptyEmail() {
        String email = "";
        String pass = "password";
        assertFalse(isValidSignUpData(context, email, pass));
    }

    @Test
    public void testEmptyPassword() {
        String email = "test@example.com";
        String pass = "";
        assertFalse(isValidSignUpData(context, email, pass));
    }

    @Test
    public void testEmptyEmailAndPassword() {
        String email = "";
        String pass = "";
        assertFalse(isValidSignUpData(context, email, pass));
    }
}
