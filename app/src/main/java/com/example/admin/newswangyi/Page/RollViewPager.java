package com.example.admin.newswangyi.Page;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.newswangyi.R;

import java.util.List;

/**
 * Created by admin on 2016/6/3.
 */
public class RollViewPager extends ViewPager {
    private static final String TAG = "RollViewPager";
    private  OnClickListener mListener;
    private List<View> mDots;
    private List<String> mTitle;
    private TextView mTV;
    private List<String> mImages;
    private RollAdapter adapter;
    private int prevousPosition ;
    private boolean istop = false;

    private Handler mHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
           int pos =  RollViewPager.this.getCurrentItem()+1;
            RollViewPager.this.setCurrentItem(pos,true);
            start();
        }
    };
    private int startX;
    private int startY;
    private long startTime;

    public RollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public RollViewPager(Context context, List<View> dots, OnClickListener Listener) {
        super(context);
        this.mListener = Listener;
        this.mDots = dots;
        this.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                mTV.setText(mTitle.get(position % mImages.size()));
                mDots.get(position % mImages.size()).setBackgroundResource(R.drawable.dot_focus);
                mDots.get(prevousPosition).setBackgroundResource(R.drawable.dot_normal);
                prevousPosition = position % mImages.size();
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        stop();
                        startTime = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        long endTime = System.currentTimeMillis();
                        if (endTime - startTime < 200) {
                            if (mListener != null) {
                                mListener.onClick();
                            }
                        }

                        break;
                    case MotionEvent.ACTION_CANCEL:
                        start();
                        break;
                }
                return false;
            }
        });
    }

    public interface OnClickListener{
        public void onClick();
    }
    public void setTitle(List<String> titles, TextView top_news_title) {
        if (titles != null && titles.size()>0 && top_news_title != null){
            top_news_title.setText(titles.get(0));
        }
        this.mTitle = titles;
        this.mTV = top_news_title;
    }
    public void setmImages(List<String> mImages) {
        this.mImages = mImages;
    }

    public void start(){
        if (adapter == null){
            adapter = new RollAdapter();
            this.setAdapter(adapter);
        }
        mHandle.sendEmptyMessageDelayed(0,3000);
        if (!istop) {
            istop = true;
            int mCurrenItem = (Integer.MAX_VALUE) / 2 - 3;
            RollViewPager.this.setCurrentItem(mCurrenItem);
            mDots.get(mCurrenItem % mImages.size()).setBackgroundResource(R.drawable.dot_focus);
        }
    }
    private class RollAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(getContext(), R.layout.viewpager_item,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            Glide.with(getContext()).load(mImages.get(position%mImages.size())).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                Log.i(TAG,"我被按了");
                break;
            case MotionEvent.ACTION_UP:
                start();
                Log.i(TAG, "我被抬起来了");
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();

                int diffX = Math.abs(startX - endX);
                int diffY = Math.abs(startY - endY);
                Log.i(TAG,"我被移动了");
               if (diffX >diffY){
                   getParent().requestDisallowInterceptTouchEvent(true);
                   stop();
               }else {
                   getParent().requestDisallowInterceptTouchEvent(false);
               }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    public void stop(){
        mHandle.removeCallbacksAndMessages(null);
    }
}
