package com.coursera.aad.capstoneapp.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coursera.aad.capstoneapp.R;
import com.coursera.aad.capstoneapp.adapters.viewHolders.HistoryViewHolder;
import com.coursera.aad.capstoneapp.models.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryViewAdapter extends RecyclerView.Adapter<HistoryViewHolder> {
    private Context context;
    private List<History> histories = new ArrayList<>();

    public HistoryViewAdapter(Context context, List<History> statisticList) {
        this.context = context;
        this.histories.addAll(statisticList);
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_history_item,
                parent, false);

        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        History history = this.histories.get(position);
        String totalCase = String.valueOf(history.getCases().getTotal());
        String activeCase = String.valueOf(history.getCases().getActive());
        String recoveredCase = String.valueOf(history.getCases().getRecovered());
        String deathCase = String.valueOf(history.getDeaths().getTotal());
        String continent = history.getContinent();

        holder.totalCase.setText(TextUtils.isEmpty(totalCase) ? "0" : totalCase);
        holder.activeCasesCount.setText(TextUtils.isEmpty(activeCase) ? "0" : activeCase);
        holder.recoveredCasesCount.setText(TextUtils.isEmpty(recoveredCase) ? "0" : recoveredCase);
        holder.deathsCasesCount.setText(TextUtils.isEmpty(deathCase) ? "0" : deathCase);
        holder.countryName.setText(continent);
    }

    @Override
    public int getItemCount() {
        return this.histories.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    public void updateContent(List<History> results) {
        this.histories.clear();
        this.histories.addAll(results);
        this.notifyDataSetChanged();
    }
}
