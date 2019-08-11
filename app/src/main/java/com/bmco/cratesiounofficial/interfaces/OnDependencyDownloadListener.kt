package com.bmco.cratesiounofficial.interfaces

import com.bmco.cratesiounofficial.models.Dependency

/**
 * Created by bertuswisman on 26/05/2017.
 */

interface OnDependencyDownloadListener {
    fun onDependenciesReady(dependency: List<Dependency>)
}
