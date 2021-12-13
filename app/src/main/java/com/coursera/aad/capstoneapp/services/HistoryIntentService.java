package com.coursera.aad.capstoneapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;

import com.coursera.aad.capstoneapp.models.History;
import com.coursera.aad.capstoneapp.retrofit.APIClient;
import com.coursera.aad.capstoneapp.retrofit.ApiInterface;
import com.coursera.aad.capstoneapp.retrofit.ApiResponse;

import java.util.List;

import retrofit2.Response;
import timber.log.Timber;

import static android.app.DownloadManager.STATUS_FAILED;
import static android.app.DownloadManager.STATUS_RUNNING;
import static android.app.DownloadManager.STATUS_SUCCESSFUL;
import static com.coursera.aad.capstoneapp.utils.Constants.COMMAND_EXTRA;
import static com.coursera.aad.capstoneapp.utils.Constants.COUNTRY_EXTRA;
import static com.coursera.aad.capstoneapp.utils.Constants.DAY_EXTRA;
import static com.coursera.aad.capstoneapp.utils.Constants.QUERY_EXTRA;
import static com.coursera.aad.capstoneapp.utils.Constants.RECEIVER_EXTRA;
import static com.coursera.aad.capstoneapp.utils.Constants.RESULTS_EXTRA;

public class HistoryIntentService extends IntentService {

    public HistoryIntentService() {
        super(HistoryIntentService.class.getName());
    }

    /**
     * @param name IntentService class's name
     * @deprecated
     */
    public HistoryIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ResultReceiver receiver = intent.getParcelableExtra(RECEIVER_EXTRA);
            String command = intent.getStringExtra(COMMAND_EXTRA);
            String country = intent.getStringExtra(COUNTRY_EXTRA);
            String day = intent.getStringExtra(DAY_EXTRA);
            Timber.e("Date: " + day);
            Timber.e("Country: " + country);
            Bundle bundle = new Bundle();
            if (command.equals(QUERY_EXTRA)) {
                receiver.send(STATUS_RUNNING, Bundle.EMPTY);
                try {
                    // Call web api and get history data
                    Response<ApiResponse<List<History>>> response = APIClient.getClient()
                            .create(ApiInterface.class).getHistory(country, day).execute();
                    if (response.isSuccessful()) {
                        if (response.body() != null && !response.body().getResponse().isEmpty()) {
                            bundle.putParcelable(RESULTS_EXTRA,
                                    (Parcelable) response.body());
                            receiver.send(STATUS_SUCCESSFUL, bundle);
                        } else {
                            bundle.putString(RESULTS_EXTRA,
                                    "No data found!");
                            receiver.send(STATUS_FAILED, bundle);
                        }
                    } else {
                        bundle.putString(Intent.EXTRA_TEXT, response.message());
                        receiver.send(STATUS_FAILED, bundle);
                    }
                } catch (Exception e) {
                    Timber.e(e);
                    bundle.putString(Intent.EXTRA_TEXT, e.toString());
                    receiver.send(STATUS_FAILED, bundle);
                }
            }
        }
    }
}