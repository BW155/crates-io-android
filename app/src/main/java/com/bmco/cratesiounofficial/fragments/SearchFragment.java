package com.bmco.cratesiounofficial.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bmco.cratesiounofficial.MainActivity;
import com.bmco.cratesiounofficial.OnSearchResult;
import com.bmco.cratesiounofficial.R;
import com.bmco.cratesiounofficial.fragments.recyclers.SearchRecyclerAdapter;
import com.bmco.cratesiounofficial.models.Crate;

/**
 * Created by bertuswisman on 26/05/2017.
 */

public class SearchFragment extends Fragment {
    private RecyclerView itemList;
    private SearchRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trending_page, container, false);
        itemList = (RecyclerView) view.findViewById(R.id.recycler);
        adapter = new SearchRecyclerAdapter(itemList.getContext());
        MainActivity.result = new OnSearchResult() {
            @Override
            public void onResult(final Crate crate) {
                itemList.post(new Runnable() {
                    @Override
                    public void run() {
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

            }
        };
        itemList.setLayoutManager(new LinearLayoutManager(itemList.getContext()));
        itemList.setAdapter(adapter);

        return view;
    }
}
