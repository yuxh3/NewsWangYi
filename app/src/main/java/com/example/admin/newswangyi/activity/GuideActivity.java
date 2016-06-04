package com.example.admin.newswangyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.admin.newswangyi.R;
import com.example.admin.newswangyi.adapter.GuideAdapter;
import com.example.admin.newswangyi.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2016/5/30.
 */
public class GuideActivity extends Activity {

    private ViewPager viewPager;
    private Button button;
    private LinearLayout ll_point_group;
    private ImageView dot_foucs;
    private int[] imageResIds = {
            R.drawable.guide_1, R.drawable.guide_2,
            R.drawable.guide_3};// 引导界面资源数组
    private List<ImageView> images;// 图片数据集合
    private int dot_width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        button = (Button) findViewById(R.id.button);
        ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
        dot_foucs = (ImageView) findViewById(R.id.dot_foucs);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        initData();
        GuideAdapter adapter = new GuideAdapter(GuideActivity.this, images);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //选中点在x轴移动的距离 = 点之间的间距*（position +　滑动比例值）
                dot_foucs.setTranslationX(dot_width * (position + positionOffset));
            }

            @Override
            public void onPageSelected(int position) {
                if (position == images.size() - 1) {
                    button.setVisibility(View.VISIBLE);
                } else {
                    button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        for (int i = 0; i < images.size(); i++) {
            ImageView iv = new ImageView(GuideActivity.this);
            iv.setImageResource(R.drawable.dot_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    DensityUtil.dip2px(getApplicationContext(), 6),
                    DensityUtil.dip2px(getApplicationContext(), 6)
            );
            if (i != 0) {
                params.leftMargin = DensityUtil.dip2px(getApplicationContext(), 6);
            }
            iv.setLayoutParams(params);
            ll_point_group.addView(iv);
        }
        dot_foucs.postDelayed(new Runnable() {
            @Override
            public void run() {
                dot_width = ll_point_group.getChildAt(1).getLeft() - ll_point_group.getChildAt(0).getLeft();
                System.out.println("dot_width = " + dot_width);
            }
        }, 20);
    }

    private void initData() {

        images = new ArrayList<>();
        for (int i = 0; i < imageResIds.length; i++) {
            ImageView iv = new ImageView(GuideActivity.this);
            iv.setBackgroundResource(imageResIds[i]);
            images.add(iv);
        }


    }


}
