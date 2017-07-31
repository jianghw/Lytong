package com.zantong.mobilecttx.weizhang.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zantong.mobilecttx.contract.ItemClickListener;
import com.zantong.mobilecttx.contract.SectionStateChangeListener;
import com.zantong.mobilecttx.weizhang.activity.ViolationHistoryAcitvity;
import com.zantong.mobilecttx.weizhang.bean.ViolationCarInfo;
import com.zantong.mobilecttx.weizhang.bean.ViolationItemInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by bpncool on 2/23/2016.
 */
public class SectionedExpandableLayoutHelper implements SectionStateChangeListener {

    private ViolationHistoryAcitvity mActivity;
    //data list
    private LinkedHashMap<ViolationCarInfo, ArrayList<ViolationItemInfo>> mSectionDataMap = new LinkedHashMap<ViolationCarInfo, ArrayList<ViolationItemInfo>>();
    private ArrayList<Object> mDataArrayList = new ArrayList<Object>();

    //section map
    //TODO : look for a way to avoid this
    private HashMap<String, ViolationCarInfo> mSectionMap = new HashMap<String, ViolationCarInfo>();

    //adapter
    private SectionedExpandableListAdapter mSectionedExpandableGridAdapter;

    public SectionedExpandableLayoutHelper(Context context, RecyclerView recyclerView, ItemClickListener itemClickListener,
                                           int gridSpanCount) {
        mActivity = (ViolationHistoryAcitvity)context;
        //setting the recycler view
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, gridSpanCount);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        mSectionedExpandableGridAdapter = new SectionedExpandableListAdapter(context, mDataArrayList,
                layoutManager, itemClickListener, this);
        recyclerView.setAdapter(mSectionedExpandableGridAdapter);
    }

    public void notifyDataSetChanged() {
        generateDataList();
        mSectionedExpandableGridAdapter.notifyDataSetChanged();
    }

    public void addSection(ViolationCarInfo section, ArrayList<ViolationItemInfo> items) {
        mSectionedExpandableGridAdapter.refresh();
        ViolationCarInfo newSection;
        mSectionMap.put(section.getCarnum(), (newSection = new ViolationCarInfo(
                section.getCarnum(),section.getEnginenum(),section.getTotalPrice(),true)));
        mSectionDataMap.put(newSection, items);
    }

    public void addItem(String section, ViolationItemInfo item) {
        mSectionDataMap.get(mSectionMap.get(section)).add(item);
    }

    public void removeItem(String section, ViolationItemInfo item) {
        mSectionDataMap.get(mSectionMap.get(section)).remove(item);
    }
    public void removeAll() {
        mSectionDataMap.clear();
        mSectionMap.clear();
        mDataArrayList.clear();
        notifyDataSetChanged();
    }
    public void closeItem(ViolationCarInfo section) {
    }

    public void removeSection(String section) {
        mSectionDataMap.remove(mSectionMap.get(section));
        mSectionMap.remove(section);
    }

    private void generateDataList () {
        mDataArrayList.clear();
        for (Map.Entry<ViolationCarInfo, ArrayList<ViolationItemInfo>> entry : mSectionDataMap.entrySet()) {
            ViolationCarInfo key;
            mDataArrayList.add((key = entry.getKey()));
            if (key.isExpanded)
                mDataArrayList.addAll(entry.getValue());
        }
    }

    @Override
    public void onSectionStateChanged(ViolationCarInfo section, boolean isOpen) {
        for(ViolationCarInfo info : mActivity.getViolationCarList()){
            info.setExpanded(false);
        }
        section.isExpanded = isOpen;
        notifyDataSetChanged();
    }
}
