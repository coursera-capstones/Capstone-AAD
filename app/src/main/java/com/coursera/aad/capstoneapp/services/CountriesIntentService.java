package com.coursera.aad.capstoneapp.services;

import static com.coursera.aad.capstoneapp.MainActivity.countryDao;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.coursera.aad.capstoneapp.database.dao.CountryDao;
import com.coursera.aad.capstoneapp.retrofit.APIClient;
import com.coursera.aad.capstoneapp.retrofit.ApiInterface;
import com.coursera.aad.capstoneapp.retrofit.ApiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import timber.log.Timber;

public class CountriesIntentService extends IntentService {

    public static final String ACTION_FINISH = "ACTION_FINISH";
    public static final String ACTION_STARTED = "ACTION_STARTED";
    public static final String ACTION_FAILURE = "ACTION_FAILURE";
    private final List<String> countries = new ArrayList<>();

    public CountriesIntentService() {
        super(CountriesIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            if (intent != null) {
                // Start work
                getCountries(countryDao);
            }
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    public void getCountries(CountryDao countryDao) {
        try {
            Response<ApiResponse<List<String>>> response = APIClient.getClient()
                    .create(ApiInterface.class).getCountries().execute();
            if (response.isSuccessful()) {
                if (response.body() != null && !response.body().getResponse().isEmpty()) {
                    countries.clear();
                    countries.addAll(response.body().getResponse());
                    // Save data to local db
                    saveCountriesToDB(countryDao);
                } else {
                    restartRequest(countryDao);
                }
            } else {
                restartRequest(countryDao);
            }
        } catch (Exception e) {
            Timber.e(e);
            restartRequest(countryDao);
        }
    }

    private void saveCountriesToDB(CountryDao countryDao) {
        countryDao.insertAll(countries);
    }

    private void restartRequest(CountryDao dao) {
        new Handler().postDelayed(() -> getCountries(dao), 1000);
    }
}