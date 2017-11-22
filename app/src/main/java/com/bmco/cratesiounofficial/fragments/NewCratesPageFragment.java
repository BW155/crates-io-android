package com.bmco.cratesiounofficial.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bmco.cratesiounofficial.R;
import com.bmco.cratesiounofficial.interfaces.OnSummaryChangeListener;
import com.bmco.cratesiounofficial.models.Summary;
import com.bmco.cratesiounofficial.recyclers.CrateRecyclerAdapter;

/**
 * Created by Bertus on 25-5-2017.
 */

public class NewCratesPageFragment extends Fragment {

    private RecyclerView itemList;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trending_page, container, false);
        progressBar = view.findViewById(R.id.progress);

        itemList = view.findViewById(R.id.recycler);
        itemList.setLayoutManager(new LinearLayoutManager(itemList.getContext()));
        SummaryFragment.listener.add(new OnSummaryChangeListener() {
            @Override
            public void summary(Summary summary) {
                refreshSummary(summary);
            }

            @Override
            public void downloadStarted() {
                progressBar.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        return view;
    }

    private void refreshSummary(final Summary summary) {
        itemList.post(new Runnable() {
            @Override
            public void run() {
                CrateRecyclerAdapter adapter = new CrateRecyclerAdapter(itemList.getContext(), summary.getNewCrates());
                itemList.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
