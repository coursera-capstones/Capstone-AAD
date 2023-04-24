package com.coursera.aad.capstoneapp.screens;

import static android.app.DownloadManager.STATUS_FAILED;
import static android.app.DownloadManager.STATUS_RUNNING;
import static android.app.DownloadManager.STATUS_SUCCESSFUL;
import static com.coursera.aad.capstoneapp.utils.Constants.COMMAND_EXTRA;
import static com.coursera.aad.capstoneapp.utils.Constants.COUNTRY_EXTRA;
import static com.coursera.aad.capstoneapp.utils.Constants.DATE_FORMAT;
import static com.coursera.aad.capstoneapp.utils.Constants.DAY_EXTRA;
import static com.coursera.aad.capstoneapp.utils.Constants.QUERY_EXTRA;
import static com.coursera.aad.capstoneapp.utils.Constants.RECEIVER_EXTRA;
import static com.coursera.aad.capstoneapp.utils.Constants.RESULTS_EXTRA;
import static com.coursera.aad.capstoneapp.utils.Utils.getCurrentDate;
import static com.coursera.aad.capstoneapp.utils.Utils.getDeviceDefaultCountryName;
import static com.coursera.aad.capstoneapp.utils.Utils.selectedCountry;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import com.coursera.aad.capstoneapp.adapters.HistoryViewAdapter;
import com.coursera.aad.capstoneapp.models.History;
import com.coursera.aad.capstoneapp.retrofit.ApiResponse;
import com.coursera.aad.capstoneapp.services.HistoryIntentService;
import com.coursera.aad.capstoneapp.services.resultReceiver.HistoryServiceResultReceiver;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class HistoryFragment extends Fragment implements
        HistoryServiceResultReceiver.HistoryResultReceiver {

    private RecyclerView recyclerView;
    private TextView tvApiCallError;
    private ProgressBar progressBar;
    private View root;
    private HistoryServiceResultReceiver resultReceiver;
    private HistoryViewAdapter adapter;
    private final List<History> results = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // Initialize intentService's result receiver
            resultReceiver = new HistoryServiceResultReceiver(new Handler());
            resultReceiver.setResultReceiver(this);
            // Create service's intent
            Intent intent = new Intent(
                    Intent.ACTION_SYNC, null,
                    requireContext(), HistoryIntentService.class);
            // Add extra's to intent
            intent.putExtra(RECEIVER_EXTRA, resultReceiver);
            intent.putExtra(COMMAND_EXTRA, QUERY_EXTRA);
            intent.putExtra(COUNTRY_EXTRA,
                    TextUtils.isEmpty(selectedCountry) ?
                            getDeviceDefaultCountryName(requireContext()) :
                    selectedCountry);
            intent.putExtra(DAY_EXTRA, getCurrentDate(DATE_FORMAT));
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
        root = inflater.inflate(
                R.layout.fragment_history,
                container,
                false);

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
            resultReceiver.setResultReceiver(null);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public void onReceiveHistoryResult(int resultCode, Bundle data) {
        switch (resultCode) {
            case STATUS_RUNNING:
                // Show progress bar
                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                tvApiCallError.setVisibility(View.GONE);
                break;

            case STATUS_SUCCESSFUL:
                try {
                    results.clear();
                    List<History> list = getHistory(data);
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
                tvApiCallError.setText(requireContext().getString(R.string.history_api_call_error));
                tvApiCallError.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * This function can be used to extract a list of History
     * objects from a Bundle object that has been passed between
     * components in the app.
     * @param bundle It takes a Bundle object as a parameter.
     * @return a list of History objects
     */
    private List<History> getHistory(Bundle bundle) {
        try {
            List<History> histories = new ArrayList<>();
            ApiResponse<List<History>> parcelable = bundle.getParcelable(RESULTS_EXTRA);
            if (parcelable != null) {
                histories.addAll(parcelable.getResponse());
            }
            return histories;
        } catch (Exception e) {
            Timber.e(e);

            return null;
        }
    }

    /**
     * Initialized the views used in this fragment
     */
    private void initView() {
        try {
            tvApiCallError = root.findViewById(R.id.text_error);
            recyclerView = root.findViewById(R.id.history_recycler);
            progressBar = root.findViewById(R.id.progressBar);

            // Click on one item at a time
            recyclerView.setMotionEventSplittingEnabled(false);
            recyclerView.setNestedScrollingEnabled(true);

            initRecyclerView();
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    /**
     * This function initializes the History RecyclerView by
     * setting up its adapter, layout manager, and fixed size.
     */
    private void initRecyclerView() {
        try {
            adapter = new HistoryViewAdapter(requireContext(), results);
            adapter.setHasStableIds(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Timber.e(e);
        }
    }
}