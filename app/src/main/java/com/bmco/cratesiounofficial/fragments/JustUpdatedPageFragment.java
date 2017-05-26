package com.bmco.cratesiounofficial.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bmco.cratesiounofficial.OnSummaryChangeListener;
import com.bmco.cratesiounofficial.R;
import com.bmco.cratesiounofficial.fragments.recyclers.JustUpdatedRecyclerAdapter;
import com.bmco.cratesiounofficial.models.Summary;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Bertus on 25-5-2017.
 */

public class JustUpdatedPageFragment extends Fragment {
    private RecyclerView itemList;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trending_page, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);

        itemList = (RecyclerView) view.findViewById(R.id.recycler);
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
                        progressBar.setVisibility(VISIBLE);
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
                JustUpdatedRecyclerAdapter adapter = new JustUpdatedRecyclerAdapter(itemList.getContext(), summary.getJustUpdated());
                itemList.setAdapter(adapter);
                progressBar.setVisibility(GONE);
            }
        });
    }
}
