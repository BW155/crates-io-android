package com.bmco.cratesiounofficial.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

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
    private Spinner sortSpinner;

    private String[] sortables = new String[] {"Downloads High > Low", "Downloads Low > High"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_crates_layout, container, false);
        itemList = view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.progress);
        sortSpinner = view.findViewById(R.id.sort_spinner);

        ArrayAdapter sortAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.sortables, R.layout.sortable_item_layout);
        sortSpinner.setAdapter(sortAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.sort(sortables[i]);
                if (adapterView.getChildAt(0) != null) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapter = new SearchRecyclerAdapter(itemList.getContext());
        MainActivity.result = new OnCrateResult() {
            @Override
            public void onResult(final Crate crate) {
                itemList.post(() -> {
                    progressBar.post(() -> progressBar.setVisibility(View.GONE));
                    adapter.tryAddCrate(crate);
                    adapter.sort(sortables[sortSpinner.getSelectedItemPosition()]);
                });
            }

            @Override
            public void needsClear() {
                itemList.post(() -> adapter.clearCrates());
            }

            @Override
            public void downloading() {
                progressBar.post(() -> progressBar.setVisibility(View.VISIBLE));
            }
        };
        itemList.setLayoutManager(new LinearLayoutManager(itemList.getContext()));
        itemList.setAdapter(adapter);

        return view;
    }
}
