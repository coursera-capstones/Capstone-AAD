package com.coursera.aad.capstoneapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.coursera.aad.capstoneapp.services.CountriesIntentService;

import timber.log.Timber;

public class CountriesBroadCastReceiver extends BroadcastReceiver  {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        try {
            if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
                startCountriesService();
                Toast.makeText(
                        context,
                        "BroadCast started countries service!",
                        Toast.LENGTH_SHORT
                ).show();
            }
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private void startCountriesService() {
        // Call countries api to retrieve data
        Intent intent = new Intent(context,
                CountriesIntentService.class);
        context.startService(intent);
    }
}
