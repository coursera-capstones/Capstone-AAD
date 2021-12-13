package com.coursera.aad.capstoneapp.utils;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

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
}
