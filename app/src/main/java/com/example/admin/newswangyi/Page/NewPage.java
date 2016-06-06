package com.example.admin.newswangyi.Page;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.admin.newswangyi.PageIndicator.TabPageIndicator;
import com.example.admin.newswangyi.R;
import com.example.admin.newswangyi.adapter.NewAdapter;
import com.example.admin.newswangyi.bean.NewCenterBean;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/5/31.
 */
public class NewPage extends BasePage {

    private NewCenterBean.DataItem mDataItem;
    private ViewPager pager;

    private List<String> newpageTitles = new ArrayList<String>();// 新闻界面的标题数据集合
    private List<BasePage> newItemPages = new ArrayList<BasePage>();// 新闻界面的条目界面数据集合
    private int mCurrentItem;
    private TabPageIndicator indicator;

    public NewPage(Context context, NewCenterBean.DataItem dataItem) {
        super(context);
        this.mDataItem = dataItem;
    }

    @Override
    public View initView() {
       View view = View.inflate(mcontext, R.layout.simple_tabs,null);
        pager = (ViewPager) view.findViewById(R.id.page);
        indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        return view;
    }

    @Override
    public void initData() {

        for (int i = 0;i<mDataItem.children.size();i++){
            newpageTitles.add(mDataItem.children.get(i).title);
            newItemPages.add(new NewItemPage(mcontext,mDataItem.children.get(i).url));
        }
        NewAdapter adapter = new NewAdapter(mcontext,newItemPages,newpageTitles);
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentItem = position;
                if (mCurrentItem == 0) {
                    mSm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                } else {
                    mSm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }

                //调用对应界面的数据初始化方法
                BasePage basePage = newItemPages.get(position);
                if (!basePage.isLoading) {
                    basePage.initData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        BasePage basePage = newItemPages.get(0);
        basePage.initData();
    }

}
