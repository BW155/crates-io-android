package com.bmco.cratesiounofficial.interfaces

import com.bmco.cratesiounofficial.models.Crate

/**
 * Created by bertuswisman on 26/05/2017.
 */

interface OnCrateResult {
    fun onResult(crate: Crate)
    fun needsClear()
    fun downloading()
}
