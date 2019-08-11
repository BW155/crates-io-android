package com.bmco.cratesiounofficial.interfaces

import com.bmco.cratesiounofficial.models.Summary

/**
 * Created by bertuswisman on 26/05/2017.
 */

interface OnSummaryChangeListener {
    fun summary(summary: Summary)
    fun downloadStarted()
}
