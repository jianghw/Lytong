package com.tzly.ctcyh.router.custom.dialog;

import android.widget.DatePicker;

/**
 * Created by jianghw on 2017/12/12.
 * Description:
 * Update by:
 * Update day:
 */

public interface IOnDateSetListener {
    void onDateSet(DatePicker view, int year, int month, int dayOfMonth, boolean usable);
}
