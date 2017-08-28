package com.bmco.cratesiounofficial.interfaces;

import com.bmco.cratesiounofficial.models.Summary;

/**
 * Created by bertuswisman on 26/05/2017.
 */

public interface OnSummaryChangeListener {
    void summary(Summary summary);
    void downloadStarted();
}
