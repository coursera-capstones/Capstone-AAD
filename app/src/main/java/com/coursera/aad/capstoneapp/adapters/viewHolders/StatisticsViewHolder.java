package com.coursera.aad.capstoneapp.adapters.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coursera.aad.capstoneapp.R;

public class StatisticsViewHolder extends RecyclerView.ViewHolder {

    public TextView totalCase;
    public TextView activeCasesCount;
    public TextView recoveredCasesCount;
    public TextView deathsCasesCount;
    public TextView countryName;

    public StatisticsViewHolder(@NonNull View view) {
        super(view);

        // Initialize views
        totalCase = view.findViewById(R.id.total_case);
        activeCasesCount = view.findViewById(R.id.active_cases);
        recoveredCasesCount = view.findViewById(R.id.recovered_cases);
        deathsCasesCount = view.findViewById(R.id.deaths_cases);
        countryName = view.findViewById(R.id.country_name);
    }
}
