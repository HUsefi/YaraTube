package com.yaratech.yaratube.ui;

import android.support.v4.app.Fragment;

import com.yaratech.yaratube.data.model.Product;

/**
 * Created by Vah on 8/19/2018.
 */

public interface Connects {

    public interface OnProductItemClick {
        void onClick(Product product);
    }

    public interface DismissDialog {
        void dismiss(Fragment fragment);

        void correction();
    }
}
