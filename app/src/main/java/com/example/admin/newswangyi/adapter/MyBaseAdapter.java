package com.example.admin.newswangyi.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by admin on 2016/5/31.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter{

    public  List<T> mDatas;
    public  Context mContext;


    public MyBaseAdapter(List<T> datas,Context context) {
        this.mDatas = datas;
        this.mContext = context;

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
