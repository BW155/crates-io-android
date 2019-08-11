package com.bmco.cratesiounofficial.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bmco.cratesiounofficial.R

/**
 * Created by Bertus on 25-5-2017.
 */

class PopularKeywordsPageFragment : Fragment() {

    private var mPage: Int = 0
    private val itemList: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPage = arguments!!.getInt(ARG_PAGE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.trending_page, container, false)
    }

    companion object {
        val ARG_PAGE = "ARG_PAGE"

        fun newInstance(page: Int): PopularKeywordsPageFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = PopularKeywordsPageFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
