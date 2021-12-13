package com.coursera.aad.capstoneapp.services.resultReceiver;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import timber.log.Timber;

public class HistoryServiceResultReceiver extends ResultReceiver {

    private HistoryResultReceiver resultReceiver;

    public HistoryServiceResultReceiver(Handler handler) {
        super(handler);
    }

    public HistoryResultReceiver getResultReceiver() {
        return resultReceiver;
    }

    public void setResultReceiver(HistoryResultReceiver resultReceiver) {
        this.resultReceiver = resultReceiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        try {
            if (resultReceiver != null) {
                resultReceiver.onReceiveHistoryResult(resultCode, resultData);
            }
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    public interface HistoryResultReceiver {
        void onReceiveHistoryResult(int resultCode, Bundle data);
    }
}
