package com.example.admin.newswangyi.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.newswangyi.Page.BasePage;

import java.util.List;

/**
 * Created by admin on 2016/6/2.
 */
public class NewAdapter extends PagerAdapter {

    private final List<String> mNewPageTitles;
    public  Context mContext;
    public  List<BasePage> mNewItemPage;
    private int mCurrentPosition;

    public NewAdapter(Context mcontext, List<BasePage> newItemPages, List<String> newpageTitles) {
        super();
        this.mContext = mcontext;
        this.mNewItemPage = newItemPages;
        this.mNewPageTitles = newpageTitles;
    }

    @Override
    public int getCount() {
        return mNewItemPage.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mNewItemPage.get(position).getRootView());
        return mNewItemPage.get(position).getRootView();
    }
    // 设置标题，注意必须要实现！！！！！
    @Override
    public CharSequence getPageTitle(int position) {
        return mNewPageTitles.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mNewItemPage.get(position).getRootView());
    }
}
