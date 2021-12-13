package com.coursera.aad.capstoneapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;

import com.coursera.aad.capstoneapp.models.Statistic;
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
import static com.coursera.aad.capstoneapp.utils.Constants.QUERY_EXTRA;
import static com.coursera.aad.capstoneapp.utils.Constants.RECEIVER_EXTRA;
import static com.coursera.aad.capstoneapp.utils.Constants.RESULTS_EXTRA;

public class StatisticsIntentService extends IntentService {

    // Declare to avoid error from AndroidManifest file
    public StatisticsIntentService() {
        super(StatisticsIntentService.class.getName());
    }

    /**
     * @param name IntentService class's name
     * @deprecated
     */
    public StatisticsIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ResultReceiver receiver = intent.getParcelableExtra(RECEIVER_EXTRA);
            String command = intent.getStringExtra(COMMAND_EXTRA);
            Bundle bundle = new Bundle();
            if (command.equals(QUERY_EXTRA)) {
                receiver.send(STATUS_RUNNING, Bundle.EMPTY);
                try {
                    // Call web api and get statistics data
                    Response<ApiResponse<List<Statistic>>> response = APIClient.getClient()
                            .create(ApiInterface.class).getStatistics().execute();
                    if (response.isSuccessful()) {
                        if (response.body() != null && !response.body().getResponse().isEmpty()) {
                            bundle.putParcelable(RESULTS_EXTRA,
                                    (Parcelable) response.body());
                            receiver.send(STATUS_SUCCESSFUL, bundle);
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