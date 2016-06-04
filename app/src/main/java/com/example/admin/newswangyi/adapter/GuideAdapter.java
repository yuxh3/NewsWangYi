package com.example.admin.newswangyi.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by admin on 2016/5/30.
 */
public class GuideAdapter extends PagerAdapter {
    private final Context mContext;
    private final List<ImageView> mImages;

    public GuideAdapter(Context context, List<ImageView> images) {
        this.mContext = context;
        this.mImages = images;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mImages.get(position));
        return mImages.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
