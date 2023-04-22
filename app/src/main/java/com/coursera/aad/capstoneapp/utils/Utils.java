package com.coursera.aad.capstoneapp.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.coursera.aad.capstoneapp.R;
import com.coursera.aad.capstoneapp.models.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public class Utils {
    public static String selectedCountry = "";

    /**
     * Load a fragment inside a frame layout
     * @param activity The FragmentActivity instance
     * @param frameLayoutID The FrameLayout id
     * @param fragment A new instance of the fragment to load
     */
    public static void loadFragmentToFrameLayout(FragmentActivity activity,
                                                 int frameLayoutID,
                                                 Fragment fragment) {
        try {
            FragmentTransaction transaction = activity
                    .getSupportFragmentManager().beginTransaction();
            transaction.replace(frameLayoutID, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    public static String getCurrentDate(String dateFormat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    dateFormat, Locale.getDefault());
            return simpleDateFormat.format(new Date());
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
    }

    public static String getDeviceDefaultCountryName(Context context) {
        try {
            return Locale.getDefault().getDisplayCountry().toLowerCase();
        } catch (Exception e) {
            Timber.e(e);
            return "";
        }
    }

    /**
     * Check user input
     * @return True if user's input are valid. Otherwise, false
     */
    public static boolean isValidSignUpData(Context context, String email, String pass) {
        try {
            if (email == null || email.isEmpty()) {
                Toast.makeText(context,
                        context.getString(R.string.email_field_error_toast),
                        Toast.LENGTH_SHORT).show();

                return false;
            }

            if (pass == null || pass.isEmpty()) {
                Toast.makeText(context,
                        context.getString(R.string.password_field_error_toast),
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        } catch (Exception e) {
            Timber.e(e);

            return false;
        }

        return true;
    }

    /**
     * Check user input
     * @return True if user's input are valid. Otherwise, false
     */
    public static boolean isValidSignInData(Context context, User user, String passConfirm) {
        try {
            if (user == null || context == null) {
                return false;
            }

            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                Toast.makeText(context,
                        context.getString(R.string.email_field_error_toast),
                        Toast.LENGTH_SHORT).show();

                return false;
            }

            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                Toast.makeText(context,
                        context.getString(R.string.password_field_error_toast),
                        Toast.LENGTH_SHORT).show();

                return false;
            }

            if (passConfirm == null || passConfirm.isEmpty()) {
                Toast.makeText(context,
                        context.getString(R.string.confirm_password_field_error_toast),
                        Toast.LENGTH_SHORT).show();

                return false;
            }

            if (!user.getPassword().trim().equalsIgnoreCase(passConfirm.trim())) {
                Toast.makeText(context,
                        context.getString(R.string.password_validation_error_toast),
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        } catch (Exception e) {
            Timber.e(e);

            return false;
        }

        return true;
    }
}
