package com.coursera.aad.capstoneapp.services.resultReceiver;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import timber.log.Timber;

public class StatisticsServiceResultReceiver extends ResultReceiver {

    private StatisticResultReceiver receiver;

    public StatisticsServiceResultReceiver(Handler handler) {
        super(handler);
    }

    public StatisticResultReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(StatisticResultReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        try {
            if (receiver != null) {
                receiver.onReceiveStatisticsResult(resultCode, resultData);
            }
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    public interface StatisticResultReceiver {
        void onReceiveStatisticsResult(int resultCode, Bundle data);
    }
}
