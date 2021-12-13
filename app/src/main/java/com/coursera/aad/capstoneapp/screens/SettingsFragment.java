package com.coursera.aad.capstoneapp.screens;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.coursera.aad.capstoneapp.MainActivity;
import com.coursera.aad.capstoneapp.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.List;

import timber.log.Timber;

import static com.coursera.aad.capstoneapp.utils.Utils.selectedCountry;

public class SettingsFragment extends Fragment {

    private MaterialAutoCompleteTextView autoCompleteDropdown;
    private View root;

    public SettingsFragment() {
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
        root = inflater.inflate(R.layout.fragment_settings,
                container, false);

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

    private void initView() {
        try {
            // Initialize views
            TextView tvFullName = root.findViewById(R.id.full_name);
            TextView tvEmail = root.findViewById(R.id.email);
            TextView tvStatus = root.findViewById(R.id.status);
            autoCompleteDropdown = root.findViewById(R.id.autoCompleteDropdown);

            // Display user details
            tvFullName.setText(MainActivity.user.getFullName());
            tvEmail.setText(MainActivity.user.getEmail());
            if (!MainActivity.user.getSignIn()) {
                tvStatus.setText(
                        requireContext()
                                .getString(R.string.user_connected_text));
            } else {
                tvStatus.setText(
                        requireContext()
                                .getString(R.string.user_offline_text));
            }
            new Handler().postDelayed(this::initCountryDropdown, 2000);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private void initCountryDropdown() {
        try {
            List<String> countries = MainActivity.countryDao.readAll();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    requireContext(), R.layout.country_item, countries);
            autoCompleteDropdown.setAdapter(adapter);
            autoCompleteDropdown.setOnItemClickListener((adapterView, view, position, id) -> {
                String country = countries.get(position);
                selectedCountry = country.toLowerCase();
            });
            if (!TextUtils.isEmpty(selectedCountry)) {
                autoCompleteDropdown.setText(selectedCountry);
            }
        } catch (Exception e) {
            Timber.e(e);
        }
    }
}