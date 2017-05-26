package com.bmco.cratesiounofficial.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bmco.cratesiounofficial.CratesIONetworking;
import com.bmco.cratesiounofficial.OnSummaryChangeListener;
import com.bmco.cratesiounofficial.R;
import com.bmco.cratesiounofficial.TrendingPageAdapter;
import com.bmco.cratesiounofficial.models.Summary;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

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

        mTrendingPager = (ViewPager) view.findViewById(R.id.trending_pager);
        mTrendingPager.setAdapter(new TrendingPageAdapter(getFragmentManager(), view.getContext()));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mTrendingPager);

        Thread summaryThread = new Thread() {
            public void run() {
                summaryThread();
            }
        };
        summaryThread.start();



        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresher);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Thread summaryThread = new Thread() {
                    public void run() {
                        summaryThread();
                    }
                };
                summaryThread.start();
            }
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
            summary = CratesIONetworking.getSummary();
            swipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefresh.setRefreshing(false);
                }
            });
            for (OnSummaryChangeListener l: listener) {
                l.summary(summary);
            }
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
}
