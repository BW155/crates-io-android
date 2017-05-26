package com.bmco.cratesiounofficial;

import com.bmco.cratesiounofficial.models.Crate;

/**
 * Created by bertuswisman on 26/05/2017.
 */

public interface OnResult {
    void onResult(Crate crate);
    void needsClear();
    void downloading();
}
