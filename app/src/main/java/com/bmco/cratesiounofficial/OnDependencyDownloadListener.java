package com.bmco.cratesiounofficial;

import com.bmco.cratesiounofficial.models.Dependency;

import java.util.List;

/**
 * Created by bertuswisman on 26/05/2017.
 */

public interface OnDependencyDownloadListener {
    public void onDependenciesReady(List<Dependency> dependency);
}
