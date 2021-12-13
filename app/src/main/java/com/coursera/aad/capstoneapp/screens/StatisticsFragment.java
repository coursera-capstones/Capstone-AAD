package com.coursera.aad.capstoneapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coursera.aad.capstoneapp.R;
import com.coursera.aad.capstoneapp.adapters.StatisticsViewAdapter;
import com.coursera.aad.capstoneapp.models.Statistic;
import com.coursera.aad.capstoneapp.retrofit.ApiResponse;
import com.coursera.aad.capstoneapp.services.StatisticsIntentService;
import com.coursera.aad.capstoneapp.services.resultReceiver.StatisticsServiceResultReceiver;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static android.app.DownloadManager.STATUS_FAILED;
import static android.app.DownloadManager.STATUS_RUNNING;
import static android.app.DownloadManager.STATUS_SUCCESSFUL;
import static com.coursera.aad.capstoneapp.utils.Constants.COMMAND_EXTRA;
import static com.coursera.aad.capstoneapp.utils.Constants.QUERY_EXTRA;
import static com.coursera.aad.capstoneapp.utils.Constants.RECEIVER_EXTRA;
import static com.coursera.aad.capstoneapp.utils.Constants.RESULTS_EXTRA;

public class StatisticsFragment extends Fragment implements
        StatisticsServiceResultReceiver.StatisticResultReceiver {

    private StatisticsServiceResultReceiver resultReceiver;
    private RecyclerView recyclerView;
    private TextView tvApiCallError;
    private ProgressBar progressBar;
    private View root;
    private StatisticsViewAdapter adapter;
    private final List<Statistic> results = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // Initialize intentService's result receiver
            resultReceiver = new StatisticsServiceResultReceiver(new Handler());
            resultReceiver.setReceiver(this);
            // Create service's intent
            Intent intent = new Intent(
                    Intent.ACTION_SYNC, null,
                    requireContext(), StatisticsIntentService.class);
            // Add extra's to intent
            intent.putExtra(RECEIVER_EXTRA, resultReceiver);
            intent.putExtra(COMMAND_EXTRA, QUERY_EXTRA);
            // Start service
            requireActivity().startService(intent);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_statistics, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            initView();
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            resultReceiver.setReceiver(null);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public void onReceiveStatisticsResult(int resultCode, Bundle data) {
        switch (resultCode) {
            case STATUS_RUNNING:
                // Show progress bar
                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                tvApiCallError.setVisibility(View.GONE);
                break;

            case STATUS_SUCCESSFUL:
                try {
                    // Display statistics in recyclerView
                    results.clear();
                    List<Statistic> list = getStatistic(data);
                    if (list != null && !list.isEmpty()) {
                        results.addAll(list);
                        adapter.updateContent(results);
                        recyclerView.setVisibility(View.VISIBLE);
                        tvApiCallError.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    Timber.e(e);
                }
                break;

            case STATUS_FAILED:
                // Handle the error
                tvApiCallError.setText(requireContext().getString(R.string.statistic_api_call_error));
                tvApiCallError.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                break;
        }
    }

    private List<Statistic> getStatistic(Bundle bundle) {
        try {
            List<Statistic> statistics = new ArrayList<>();
            ApiResponse<List<Statistic>> parcelable = bundle.getParcelable(RESULTS_EXTRA);
            if (parcelable != null) {
                statistics.addAll(parcelable.getResponse());
            }
            return statistics;
        } catch (Exception e) {
            Timber.e(e);

            return null;
        }
    }

    private void initView() {
        try {
            tvApiCallError = root.findViewById(R.id.text_error);
            recyclerView = root.findViewById(R.id.statistics_recycler);
            progressBar = root.findViewById(R.id.progressBar);

            // Click on one item at a time
            recyclerView.setMotionEventSplittingEnabled(false);
            recyclerView.setNestedScrollingEnabled(true);

            initRecyclerView();
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private void initRecyclerView() {
        try {
            adapter = new StatisticsViewAdapter(requireContext(), results);
            adapter.setHasStableIds(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Timber.e(e);
        }
    }
}