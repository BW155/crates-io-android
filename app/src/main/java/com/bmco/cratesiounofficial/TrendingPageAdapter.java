package com.bmco.cratesiounofficial;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bmco.cratesiounofficial.fragments.JustUpdatedPageFragment;
import com.bmco.cratesiounofficial.fragments.MostDownloadedPageFragment;
import com.bmco.cratesiounofficial.fragments.NewCratesPageFragment;
import com.bmco.cratesiounofficial.fragments.PopularCategoriesPageFragment;
import com.bmco.cratesiounofficial.fragments.PopularKeywordsPageFragment;

/**
 * Created by Bertus on 25-5-2017.
 */

public class TrendingPageAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "New Crates", "Most Downloaded", "Just Updated", "Popular Keywords", "Popular Categories" };
    private Context context;

    public TrendingPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewCratesPageFragment();
            case 1:
                return new MostDownloadedPageFragment();
            case 2:
                return new JustUpdatedPageFragment();
            case 3:
                return PopularKeywordsPageFragment.newInstance(position);
            case 4:
                return PopularCategoriesPageFragment.newInstance(position);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
