package com.coursera.aad.capstoneapp.services.resultReceiver;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import timber.log.Timber;

public class CountriesResultReceiver extends ResultReceiver {

    private OnResultReceiverListener resultReceiver;

    public CountriesResultReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        try {
            if (resultReceiver != null) {
                resultReceiver.onReceiveCountriesResult(resultCode, resultData);
            }
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    public OnResultReceiverListener getResultReceiver() {
        return resultReceiver;
    }

    public void setResultReceiver(OnResultReceiverListener resultReceiver) {
        this.resultReceiver = resultReceiver;
    }

    public interface OnResultReceiverListener {
        void onReceiveCountriesResult(int resultCode, Bundle data);
    }
}
