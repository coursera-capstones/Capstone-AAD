package com.coursera.aad.capstoneapp.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.coursera.aad.capstoneapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import timber.log.Timber;

import static com.coursera.aad.capstoneapp.utils.Utils.loadFragmentToFrameLayout;

public class CovidNewsFragment extends Fragment {

    private FrameLayout frameLayout;
    private BottomNavigationView navigationView;

    private View root = null;
    private int currentView = 0;

    public CovidNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_covid_news, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            initView();
            initListener();
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private void initView() {
        try {
            // Find view by ID
            frameLayout = root.findViewById(R.id.frame_container);
            navigationView = root.findViewById(R.id.bottom_navigation);

            // Load first view in the frame layout
            loadView(new StatisticsFragment());
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private void initListener() {
        try {
            navigationView.setOnItemSelectedListener(item -> {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.statistics_page:
                        fragment = new StatisticsFragment();
                        break;

                    case R.id.history_page:
                        fragment = new HistoryFragment();
                        break;

                    case R.id.settings_page:
                        fragment = new SettingsFragment();
                        break;
                }
                loadView(fragment);

                return true;
            });
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private void loadView(Fragment fragment) {
        try {
            // Load statistics fragment
            loadFragmentToFrameLayout(
                    requireActivity(),
                    frameLayout.getId(),
                    fragment);
        } catch (Exception e) {
            Timber.e(e);
        }
    }
}