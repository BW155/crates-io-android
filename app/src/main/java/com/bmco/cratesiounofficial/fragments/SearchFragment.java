package com.bmco.cratesiounofficial.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bmco.cratesiounofficial.activities.MainActivity;
import com.bmco.cratesiounofficial.interfaces.OnCrateResult;
import com.bmco.cratesiounofficial.R;
import com.bmco.cratesiounofficial.recyclers.SearchRecyclerAdapter;
import com.bmco.cratesiounofficial.models.Crate;

/**
 * Created by bertuswisman on 26/05/2017.
 */

public class SearchFragment extends Fragment {
    private RecyclerView itemList;
    private SearchRecyclerAdapter adapter;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trending_page, container, false);
        itemList = view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.progress);
        adapter = new SearchRecyclerAdapter(itemList.getContext());
        MainActivity.result = new OnCrateResult() {
            @Override
            public void onResult(final Crate crate) {
                itemList.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                        adapter.tryAddCrate(crate);
                    }
                });
            }

            @Override
            public void needsClear() {
                itemList.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clearCrates();
                    }
                });
            }

            @Override
            public void downloading() {
                progressBar.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
            }
        };
        itemList.setLayoutManager(new LinearLayoutManager(itemList.getContext()));
        itemList.setAdapter(adapter);

        return view;
    }
}
