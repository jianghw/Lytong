package com.zantong.mobilecttx.chongzhi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.baidu.mapapi.search.core.PoiInfo;
import com.zantong.mobilecttx.R;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhoujie on 2017/2/20.
 */

public class ChooseAddressAdapter extends BaseAdapter<PoiInfo>{

    private Context mContext;
    private OnRecyclerviewItemListener mOnRecyclerviewItemListener;

    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, PoiInfo data) {
        ViewHolder holder = (ViewHolder)viewHolder;
        if (data != null){
            holder.mText.setText(data.address);
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_choose_address_list, viewGroup,false);
        return view;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    class ViewHolder extends BaseRecyclerViewHolder implements View.OnClickListener{
        @Bind(R.id.item_common__list_text)
        TextView mText;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnRecyclerviewItemListener.OnItemClick(v, getPosition());
        }
    }

    public void setListener(OnRecyclerviewItemListener l) {
        this.mOnRecyclerviewItemListener = l;
    }

    public void setDatas(List<PoiInfo> mDatas) {
        if (mDatas != null && mDatas.size() > 0) {
            replace(mDatas);
            notifyDataSetChanged();
        }
    }

    public interface OnRecyclerviewItemListener {
        public void OnItemClick(View view, int position);
    }
}
