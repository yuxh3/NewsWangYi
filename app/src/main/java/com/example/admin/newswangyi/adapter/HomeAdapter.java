package com.example.admin.newswangyi.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.newswangyi.Page.BasePage;

import java.util.List;

/**
 * Created by admin on 2016/5/30.
 */
public class HomeAdapter extends PagerAdapter {

    private final Context context;
    private final List<BasePage> mHomePage;

    public HomeAdapter(Context mContext, List<BasePage> homePages) {
        this.context = mContext;
        this.mHomePage = homePages;
    }

    @Override
    public int getCount() {
        return mHomePage.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mHomePage.get(position).getRootView());
        return mHomePage.get(position).getRootView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View) object);
    }
}
