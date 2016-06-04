package com.example.admin.newswangyi.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.newswangyi.R;
import com.example.admin.newswangyi.activity.MainActivity;
import com.example.admin.newswangyi.adapter.MyBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/5/30.
 */
public class MenuFragment2 extends BaseFragment {

    private static final String TAG = "MenuFragment2";
    private ListView lv_menu_news_center;
    private ListView lv_menu_smart_service;
    private ListView lv_menu_govaffairs;
    private MenuAdapter menuAdapter;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.layout_left_menu, null);
        lv_menu_news_center = (ListView) view.findViewById(R.id.lv_menu_news_center);
        lv_menu_smart_service = (ListView) view.findViewById(R.id.lv_menu_smart_service);
        lv_menu_govaffairs = (ListView) view.findViewById(R.id.lv_menu_govaffairs);
       // Log.i(TAG, "iiiiiiiiiiiiiiiiiiiii:::::::"+"mNewsCenterTitles");

        return view;


    }

    @Override
    public void initData() {

        lv_menu_news_center.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuAdapter.setClickItemPosition(position);
                mSm.toggle();

                //控制新闻中心，进行界面切换
                ((MainActivity)mContext).getHomeFragment().getNewsCenterPage().switchPage(position,mNewsCenterTitles.get(position));
            }
        });

    }

    public List<String> mNewsCenterTitles = new ArrayList<String>();
    public void initMenuTitle(List<String> menuTitles) {
        mNewsCenterTitles.clear();

        mNewsCenterTitles.addAll(menuTitles);

        menuAdapter = new MenuAdapter(mNewsCenterTitles,mContext);
        lv_menu_news_center.setAdapter(menuAdapter);
       // Log.i(TAG, "iiiiiiiiiiiiiiiiiiiii:::::::"+"mNewsCenterTitles"+mNewsCenterTitles.size());


    }
    private class MenuAdapter extends MyBaseAdapter<String>{

        public MenuAdapter(List<String> datas, Context context) {
            super(datas, context);
        }

        private int mClickPosition;//对应被点击条目的位置
        //对应被点击条目的位置
        public void setClickItemPosition(int position) {
            this.mClickPosition = position;
            menuAdapter.notifyDataSetChanged();//让自己刷新一下
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.layout_item_menu, null);
            }
            ImageView iv = (ImageView) convertView.findViewById(R.id.iv_menu_item);
            TextView tv_menu_item = (TextView) convertView.findViewById(R.id.tv_menu_item);
            tv_menu_item.setText(mDatas.get(position));

            if (mClickPosition == position) {//如果是被点击的选中位置，则设置为选中状态
                tv_menu_item.setTextColor(Color.RED);
                iv.setImageResource(R.drawable.menu_arr_select);
                convertView.setBackgroundResource(R.drawable.menu_item_bg_select);
            }else{
                tv_menu_item.setTextColor(Color.WHITE);
                iv.setImageResource(R.drawable.menu_arr_normal);
                convertView.setBackgroundResource(R.drawable.transparent);
            }

            return convertView;
        }
    }
}
