package com.example.admin.newswangyi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.newswangyi.R;
import com.example.admin.newswangyi.bean.NewItemBean;

import java.util.List;

/**
 * Created by admin on 2016/6/3.
 */
public class NewItemAdapter extends MyBaseAdapter {

    public NewItemAdapter(List datas, Context context) {
        super(datas, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewItemBean.DataItem.NewsItem newsItem = (NewItemBean.DataItem.NewsItem) mDatas.get(position);
       if (convertView == null){
           convertView = View.inflate(mContext, R.layout.layout_news_item,null);
       }
        ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tv_pub_date = (TextView) convertView.findViewById(R.id.tv_pub_date);

        Glide.with(mContext).load(newsItem.listimage).into(iv_img);
        tv_title.setText(newsItem.title);
        tv_pub_date.setText(newsItem.pubdate);

        if (newsItem.isRead){
            tv_title.setTextColor(Color.RED);
        }else {
            tv_title.setTextColor(Color.BLACK);
        }
        return convertView;
    }
}
