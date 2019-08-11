package com.bmco.cratesiounofficial


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.bmco.cratesiounofficial.fragments.JustUpdatedPageFragment
import com.bmco.cratesiounofficial.fragments.MostDownloadedPageFragment
import com.bmco.cratesiounofficial.fragments.NewCratesPageFragment
import com.bmco.cratesiounofficial.fragments.PopularCategoriesPageFragment
import com.bmco.cratesiounofficial.fragments.PopularKeywordsPageFragment

/**
 * Created by Bertus on 25-5-2017.
 */

class TrendingPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val pageCount = 3
    private val tabTitles = arrayOf("New Crates", "Most Downloaded", "Just Updated", "Popular Keywords", "Popular Categories")

    override fun getCount(): Int {
        return pageCount
    }

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> NewCratesPageFragment()
            1 -> MostDownloadedPageFragment()
            2 -> JustUpdatedPageFragment()
            3 -> PopularKeywordsPageFragment.newInstance(position)
            4 -> PopularCategoriesPageFragment.newInstance(position)
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // Generate title based on item position
        return tabTitles[position]
    }
}
