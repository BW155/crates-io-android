package com.bmco.cratesiounofficial.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bmco.cratesiounofficial.R;
import com.bmco.cratesiounofficial.fragments.recyclers.JustUpdatedRecyclerAdapter;

/**
 * Created by Bertus on 25-5-2017.
 */

public class JustUpdatedPageFragment extends Fragment {
    private RecyclerView itemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trending_page, container, false);

        itemList = (RecyclerView) view.findViewById(R.id.recycler);
        itemList.setLayoutManager(new LinearLayoutManager(itemList.getContext()));
        Thread waitThread = new Thread() {
            public void run() {
                while(SummaryFragment.summary == null) {
                    //ignore
                }
                itemList.post(new Runnable() {
                    @Override
                    public void run() {
                        JustUpdatedRecyclerAdapter adapter = new JustUpdatedRecyclerAdapter(itemList.getContext(), SummaryFragment.summary.getJustUpdated());
                        itemList.setAdapter(adapter);
                    }
                });
            }
        };
        waitThread.start();
        return view;
    }
}
