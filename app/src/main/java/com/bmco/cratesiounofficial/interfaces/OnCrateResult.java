package com.bmco.cratesiounofficial.interfaces;

import com.bmco.cratesiounofficial.models.Crate;

/**
 * Created by bertuswisman on 26/05/2017.
 */

public interface OnCrateResult {
    void onResult(Crate crate);
    void needsClear();
    void downloading();
}
