package com.bmco.cratesiounofficial;

import com.bmco.cratesiounofficial.models.Crate;

import java.util.List;

/**
 * Created by bertuswisman on 26/05/2017.
 */

public interface OnSearchResult {
    void onResult(Crate crate);
    void needsClear();
    void downloading();
}
