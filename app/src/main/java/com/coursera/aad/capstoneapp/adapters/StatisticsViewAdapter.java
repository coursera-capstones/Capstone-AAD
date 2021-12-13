package com.coursera.aad.capstoneapp.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coursera.aad.capstoneapp.R;
import com.coursera.aad.capstoneapp.adapters.viewHolders.StatisticsViewHolder;
import com.coursera.aad.capstoneapp.models.Statistic;

import java.util.ArrayList;
import java.util.List;

public class StatisticsViewAdapter extends RecyclerView.Adapter<StatisticsViewHolder> {
    private Context context;
    private List<Statistic> statisticList = new ArrayList<>();

    public StatisticsViewAdapter(Context context, List<Statistic> statisticList) {
        this.context = context;
        this.statisticList.addAll(statisticList);
    }

    @NonNull
    @Override
    public StatisticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_statistics_item,
                parent, false);

        return new StatisticsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsViewHolder holder, int position) {
        Statistic statistic = this.statisticList.get(position);
        String totalCase = String.valueOf(statistic.getCases().getTotal());
        String activeCase = String.valueOf(statistic.getCases().getActive());
        String recoveredCase = String.valueOf(statistic.getCases().getRecovered());
        String deathCase = String.valueOf(statistic.getDeaths().getTotal());
        String country = statistic.getCountry();

        holder.totalCase.setText(TextUtils.isEmpty(totalCase) ? "0" : totalCase);
        holder.activeCasesCount.setText(TextUtils.isEmpty(activeCase) ? "0" : activeCase);
        holder.recoveredCasesCount.setText(TextUtils.isEmpty(recoveredCase) ? "0" : recoveredCase);
        holder.deathsCasesCount.setText(TextUtils.isEmpty(deathCase) ? "0" : deathCase);
        holder.countryName.setText(country);
    }

    @Override
    public int getItemCount() {
        return this.statisticList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    public void updateContent(List<Statistic> results) {
        this.statisticList.clear();
        this.statisticList.addAll(results);
        this.notifyDataSetChanged();
    }
}
