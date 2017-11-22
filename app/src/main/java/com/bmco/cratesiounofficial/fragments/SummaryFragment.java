package com.bmco.cratesiounofficial.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bmco.cratesiounofficial.Networking;
import com.bmco.cratesiounofficial.interfaces.OnSummaryChangeListener;
import com.bmco.cratesiounofficial.R;
import com.bmco.cratesiounofficial.TrendingPageAdapter;
import com.bmco.cratesiounofficial.models.Summary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bertuswisman on 26/05/2017.
 */

public class SummaryFragment extends Fragment {
    private ViewPager mTrendingPager;
    public static Summary summary;
    public static List<OnSummaryChangeListener> listener = new ArrayList<>();

    SwipeRefreshLayout swipeRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.summary, container, false);

        mTrendingPager = view.findViewById(R.id.trending_pager);
        mTrendingPager.setAdapter(new TrendingPageAdapter(getFragmentManager(), view.getContext()));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mTrendingPager);

        Thread summaryThread = new Thread() {
            public void run() {
                summaryThread();
            }
        };
        summaryThread.start();



        swipeRefresh = view.findViewById(R.id.refresher);
        swipeRefresh.setOnRefreshListener(() -> {
            Thread summaryThread1 = new Thread() {
                public void run() {
                    summaryThread();
                }
            };
            summaryThread1.start();
        });

        mTrendingPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled( int position, float v, int i1 ) {
            }

            @Override
            public void onPageSelected( int position ) {
            }

            @Override
            public void onPageScrollStateChanged( int state ) {
                enableDisableSwipeRefresh( state == ViewPager.SCROLL_STATE_IDLE );
            }
        });
        mTrendingPager.setOffscreenPageLimit(2);

        return view;
    }

    private void enableDisableSwipeRefresh(boolean enable) {
        if (swipeRefresh != null) {
            swipeRefresh.setEnabled(enable);
        }
    }

    private void summaryThread() {
        try {
            for (OnSummaryChangeListener l: listener) {
                l.downloadStarted();
            }
            summary = Networking.getSummary();
            swipeRefresh.post(() -> swipeRefresh.setRefreshing(false));
            for (OnSummaryChangeListener l: listener) {
                l.summary(summary);
            }
        } catch (IOException e) {
            e.printStackTrace();
            mTrendingPager.post(() -> Toast.makeText(mTrendingPager.getContext(), "Can't load summary", Toast.LENGTH_LONG).show());
        }
    }
}
