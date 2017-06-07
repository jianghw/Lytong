package com.zantong.mobilecttx.widght;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.common.MyPopWindowAdapter;
import com.zantong.mobilecttx.interf.IBasePopWindow;


public class MyPopWindow extends RelativeLayout implements IBasePopWindow {

	private ListView mListView;
	private OnSelectListener mOnSelectListener;
	private MyPopWindowAdapter adapter;
	private String mDistance;
	private String showText = "一年内";
	private Context mContext;
	String[] content;

	public String getShowText() {
		return showText;
	}

	public MyPopWindow(Context context) {
		super(context);
		init(context);
	}

	public MyPopWindow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public MyPopWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.mine_payhistory_popwindow, this, true);
		mListView = (ListView) findViewById(R.id.listView);

		content = getResources().getStringArray(R.array.mine_violation_date);
//		if(content!=null){
//			for(int i=0;i<content.length;i++){
//				items.add(content[i]);
//			}
//		}else{
//			ToastUtils.showShort(mContext, "加载失败");
//		}

		adapter = new MyPopWindowAdapter(context, content);
		adapter.setTextSize(17);
		if (mDistance != null) {
			for (int i = 0; i < content.length; i++) {
				if (content[i].equals(mDistance)) {
					adapter.setSelectedPositionNoNotify(i);
					showText = content[i];
					break;
				}
			}
		}
		mListView.setAdapter(adapter);

		adapter.setOnItemClickListener(new MyPopWindowAdapter.OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {

				if (mOnSelectListener != null) {
					showText = content[position];
					mOnSelectListener.getValue(content[position],position);
				}
			}
		});
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String data, int position);
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void show() {
		
	}

}
