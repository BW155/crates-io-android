package com.bmco.cratesiounofficial.fragments.recyclers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bmco.cratesiounofficial.CrateActivity;
import com.bmco.cratesiounofficial.R;
import com.bmco.cratesiounofficial.models.Crate;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Bertus on 25-5-2017.
 */

public class MostDownloadedRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Crate> crates;

    public MostDownloadedRecyclerAdapter(Context context, List<Crate> crates) {
        this.context = context;
        this.crates = crates;
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

        TextView crateName = (TextView) holder.itemView.findViewById(R.id.crate_title);
        TextView crateDescription = (TextView) holder.itemView.findViewById(R.id.crate_description);
        TextView crateDownloads = (TextView) holder.itemView.findViewById(R.id.crate_downloads);
        TextView crateMaxVersion = (TextView) holder.itemView.findViewById(R.id.crate_max_version);

        crateName.setText(crate.getName());
        crateDescription.setText(crate.getDescription());
        DecimalFormat df = new DecimalFormat("#,##0");
        crateDownloads.setText(df.format(Long.valueOf(crate.getDownloads())));
        crateMaxVersion.setText("v" + crate.getMaxVersion());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CrateActivity.class);
                intent.putExtra("crate", crate);
                context.startActivity(intent);
            }
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
