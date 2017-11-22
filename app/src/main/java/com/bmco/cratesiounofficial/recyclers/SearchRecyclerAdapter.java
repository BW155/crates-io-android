package com.bmco.cratesiounofficial.recyclers;

/**
 * Created by bertuswisman on 26/05/2017.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bmco.cratesiounofficial.activities.CrateActivity;
import com.bmco.cratesiounofficial.R;
import com.bmco.cratesiounofficial.models.Crate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Bertus on 25-5-2017.
 */

public class SearchRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Crate> crates;

    public SearchRecyclerAdapter(Context context) {
        this.context = context;
        crates = new ArrayList<>();
    }

    public void tryAddCrate(Crate crate) {
        for (Crate internal: crates) {
            if (crate.getId() != null && internal.getId() != null) {
                if (internal.getId().equals(crate.getId())) {
                    return;
                }
            }
        }
        crates.add(crate);
        this.notifyItemInserted(crates.indexOf(crate));
    }

    public void clearCrates() {
        crates.clear();
        this.notifyDataSetChanged();
    }

    public void sort(String by) {
        switch (by) {
            case "Downloads High > Low":
                Collections.sort(crates, (crate, crate1) -> Integer.compare(crate1.getDownloads(), crate.getDownloads()));
                break;
            case "Downloads Low > High":
                Collections.sort(crates, (crate, crate1) -> Integer.compare(crate.getDownloads(), crate1.getDownloads()));
                break;
        }
        this.notifyItemRangeChanged(0, crates.size());
    }

    @Override
    @SuppressLint("InflateParams")
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.crate_item, null);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Crate crate = crates.get(position);

        TextView crateName = holder.itemView.findViewById(R.id.crate_title);
        TextView crateDescription = holder.itemView.findViewById(R.id.crate_description);
        TextView crateDownloads = holder.itemView.findViewById(R.id.crate_downloads);
        TextView crateMaxVersion = holder.itemView.findViewById(R.id.crate_max_version);

        crateName.setText(crate.getName());
        crateDescription.setText(crate.getDescription());
        DecimalFormat df = new DecimalFormat("#,##0");
        crateDownloads.setText(df.format(Long.valueOf(crate.getDownloads())));
        crateMaxVersion.setText("v" + crate.getMaxVersion());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CrateActivity.class);
            intent.putExtra("crate", crate);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return crates.size();
    }

    private static class CustomViewHolder extends RecyclerView.ViewHolder {
        CustomViewHolder(View itemView) {
            super(itemView);
        }
    }
}

