package com.example.admin.newswangyi.Fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.example.admin.newswangyi.Page.BasePage;
import com.example.admin.newswangyi.Page.FuctionPage;
import com.example.admin.newswangyi.Page.GovPage;
import com.example.admin.newswangyi.Page.NewsCenterPage;
import com.example.admin.newswangyi.Page.SettingPage;
import com.example.admin.newswangyi.Page.SmartServicePage;
import com.example.admin.newswangyi.R;
import com.example.admin.newswangyi.adapter.HomeAdapter;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/5/30.
 */
public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener{


    private static final String TAG = "HomeFragment";
    private ViewPager view_pager;
    private RadioGroup main_radio;
    private List<BasePage> homePages;// 内容页面对象的数据集合

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.frag_home, null);

        view_pager = (ViewPager) view.findViewById(R.id.view_pager);
        main_radio = (RadioGroup) view.findViewById(R.id.main_radio);
        return view;
    }

    @Override
    public void initData() {
        homePages = new ArrayList<>();
        homePages.add(new FuctionPage(mContext));
        homePages.add(new NewsCenterPage(mContext));
        homePages.add(new SmartServicePage(mContext));
        homePages.add(new GovPage(mContext));
        homePages.add(new SettingPage(mContext));


        HomeAdapter adapter = new HomeAdapter(mContext, homePages);
        view_pager.setAdapter(adapter);
        main_radio.check(R.id.rb_function);
        view_pager.setOnPageChangeListener(this);

        main_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_function:// 首页
                        view_pager.setCurrentItem(0, false);//控制界面切换时，是否有滑动效果
                        //让侧滑菜单不显示
                        mSm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                        break;
                    case R.id.rb_news_center:// 新闻中心
                        view_pager.setCurrentItem(1, false);
                        //让侧滑菜单显示
                        mSm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                        //获取新闻中心页面对象，调用它的数据初始化方法
                        //homePages.get(1).initData();
                        break;
                    case R.id.rb_smart_service:// 智慧服务
                        view_pager.setCurrentItem(2, false);
                        //让侧滑菜单显示
                        mSm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                        break;
                    case R.id.rb_gov_affairs:// 政务指南
                        view_pager.setCurrentItem(3, false);
                        //让侧滑菜单显示
                        mSm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                        break;
                    case R.id.rb_setting:// 设置
                        view_pager.setCurrentItem(4, false);
                        //让侧滑菜单不显示
                        mSm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                        break;

                    default:
                        break;
                }
            }

        });
    }



    public NewsCenterPage getNewsCenterPage() {
        NewsCenterPage newsCenterPage = (NewsCenterPage) homePages.get(1);
        return newsCenterPage;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        BasePage basePage = homePages.get(position);
        if (!basePage.isLoading){
            basePage.initData();
            Log.i(TAG,"iiiiiiiiiiiiiiiiiiiii");
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
