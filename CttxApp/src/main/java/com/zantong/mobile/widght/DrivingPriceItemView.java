package com.zantong.mobile.widght;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zantong.mobile.R;

/**
 * 服务价格item
 * @author zyb
 *
 *  
 *    *  *   *  *     
 *  *      *      *   
 *  *             *   
 *   *           *    
 *      *     *       
 *         *          
 * 
 *
 * create at 17/2/21 下午4:46
 */

public class DrivingPriceItemView extends RelativeLayout {

    public DrivingPriceItemView(Context context) {
        super(context);
    }

    public DrivingPriceItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public DrivingPriceItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.widget_driving_price_item, this, true);
        View layout = view.findViewById(R.id.driving_price_item_layout);
        TextView time = (TextView) view.findViewById(R.id.driving_price_item_time);
        TextView price = (TextView) view.findViewById(R.id.driving_price_item_price);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrivingPriceItemView);
        time.setText(typedArray.getString(R.styleable.DrivingPriceItemView_driving_time));
        price.setText(typedArray.getString(R.styleable.DrivingPriceItemView_driving_price));
        layout.setBackgroundColor(typedArray.getColor(R.styleable.DrivingPriceItemView_bgColor,R.color.white));
        typedArray.recycle();
    }

}
