package com.tzly.ctcyh.router.custom.dialog;

import android.widget.DatePicker;

import java.util.Date;

/**
 * Created by jianghw on 2017/12/12.
 * Description:
 * Update by:
 * Update day:
 */

public interface IOnDateSetListener {
    void onDateSet(DatePicker view, Date date, boolean usable);
}
