package com.example.admin.newswangyi.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;

import com.example.admin.newswangyi.Fragment.HomeFragment;
import com.example.admin.newswangyi.Fragment.MenuFragment2;
import com.example.admin.newswangyi.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Created by admin on 2016/5/30.
 */
public class MainActivity extends SlidingFragmentActivity {

    private static final String TAG = "MainActivity";
    private SlidingMenu mSm;
    private MenuFragment2 menuFragment;
    public HomeFragment homeFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置内容页面
        setContentView(R.layout.activity_main);
        //设置左边菜单页面
        setBehindContentView(R.layout.activity_menu);
        //获取侧滑菜单的对象
        mSm = getSlidingMenu();
        //设置侧滑菜单的显示模式
        mSm.setMode(SlidingMenu.LEFT);
        //设置触摸模式
        mSm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置菜单页面显示之后，主界面显示的宽度
        mSm.setBehindOffsetRes(R.dimen.sliding_width);
        //设置阴影
       // mSm.setShadowDrawable(R.drawable.shadow);
        //设置阴影宽度
        mSm.setShadowWidthRes(R.dimen.shadow_width);

        menuFragment = new MenuFragment2();
        getSupportFragmentManager().beginTransaction().replace(R.id.menu, menuFragment).commit();

        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main, homeFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main,fragment).commit();
        mSm.toggle();
    }

    public MenuFragment2 getMenuFragment() {
        return menuFragment;
    }

    public HomeFragment getHomeFragment() {
        return homeFragment;
    }
}
