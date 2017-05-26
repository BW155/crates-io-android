package com.bmco.cratesiounofficial.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bmco.cratesiounofficial.CratesIONetworking;
import com.bmco.cratesiounofficial.R;
import com.bmco.cratesiounofficial.TrendingPageAdapter;
import com.bmco.cratesiounofficial.models.Summary;

import java.io.IOException;

/**
 * Created by bertuswisman on 26/05/2017.
 */

public class SummaryFragment extends Fragment {
    private ViewPager mTrendingPager;
    public static Summary summary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.summary, container, false);

        mTrendingPager = (ViewPager) view.findViewById(R.id.trending_pager);
        mTrendingPager.setAdapter(new TrendingPageAdapter(getFragmentManager(), view.getContext()));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mTrendingPager);

        Thread summaryThread = new Thread() {
            public void run() {
                try {
                    Summary summary = CratesIONetworking.getSummary();
                    System.out.println(summary);
                    SummaryFragment.summary = summary;
                } catch (IOException e) {
                    e.printStackTrace();
                    mTrendingPager.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mTrendingPager.getContext(), "Can't load summary", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        };
        summaryThread.start();
        return view;
    }
}
