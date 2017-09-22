package com.zantong.mobile.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zantong.mobile.R;

import java.util.List;

public class MyPopWindowAdapter extends ArrayAdapter<String> {

	private Context mContext;
	private List<String> mListData;
	private String[] mArrayData;
	private int selectedPos = -1;
	private String selectedItem = "1周内";
	private int normalDrawbleId;
	private Drawable selectedDrawble;
	private float textSize;
	private OnClickListener onClickListener;
	private OnItemClickListener mOnItemClickListener;

	public MyPopWindowAdapter(Context context, List<String> listData, int sId, int nId) {
		super(context, R.string.no_data, listData);
		mContext = context;
		mListData = listData;
		selectedDrawble = mContext.getResources().getDrawable(sId);
		normalDrawbleId = nId;

		init();
	}

	private void init() {
		onClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				selectedPos = (Integer) view.getTag();
				setSelectedPosition(selectedPos);
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(view, selectedPos);
				}
			}
		};
	}

	public MyPopWindowAdapter(Context context, String[] arrayData) {
		super(context, R.string.no_data,arrayData);
		mContext = context;
		mArrayData = arrayData;
		init();
	}

	/**
	 * 设置选中的position,并通知列表刷新
	 */
	public void setSelectedPosition(int pos) {
		if (mListData != null && pos < mListData.size()) {
			selectedPos = pos;
			selectedItem = mListData.get(pos);
			notifyDataSetChanged();
		} else if (mArrayData != null && pos < mArrayData.length) {
			selectedPos = pos;
			selectedItem = mArrayData[pos];
			notifyDataSetChanged();
		}

	}

	/**
	 * 设置选中的position,但不通知刷新
	 */
	public void setSelectedPositionNoNotify(int pos) {
		selectedPos = pos;
		if (mListData != null && pos < mListData.size()) {
			selectedItem = mListData.get(pos);
		} else if (mArrayData != null && pos < mArrayData.length) {
			selectedItem = mArrayData[pos];
		}
	}

	/**
	 * 获取选中的position
	 */
	public int getSelectedPosition() {
		if (mArrayData != null && selectedPos < mArrayData.length) {
			return selectedPos;
		}
		if (mListData != null && selectedPos < mListData.size()) {
			return selectedPos;
		}

		return -1;
	}

	/**
	 * 设置列表字体大小
	 */
	public void setTextSize(float tSize) {
		textSize = tSize;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.view_popwindow_choose_item, parent, false);
		} else {
			view = convertView;
		}
		TextView mText = (TextView)view.findViewById(R.id.view_popu_choose_text);
		ImageView mImage = (ImageView)view.findViewById(R.id.view_popu_choose_img);
		view.setTag(position);
		String entity = null;
		if (mListData != null) {
			if (position < mListData.size()) {
				entity = mListData.get(position);
			}
		} else if (mArrayData != null) {
			if (position < mArrayData.length) {
				entity = mArrayData[position];
			}
		}
		if (entity.contains("不限"))
			mText.setText("不限");
		else
			mText.setText(entity);
//		view.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);

		if (selectedItem != null && selectedItem.equals(entity)) {
			mText.setTextColor(mContext.getResources().getColor(R.color.gray_66));
			mImage.setVisibility(View.VISIBLE);
		} else {
			mImage.setVisibility(View.GONE);
//			mText.setTextColor(mContext.getResources().getColor(R.color.gray_33));
//			mImage.setBackgroundDrawable(mContext.getResources().getDrawable(normalDrawbleId));//设置未选中状态背景图片
		}
		view.setOnClickListener(onClickListener);
		return view;
	}

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}

	/**
	 * 重新定义菜单选项单击接口
	 */
	public interface OnItemClickListener {
		public void onItemClick(View view, int position);
	}

}
