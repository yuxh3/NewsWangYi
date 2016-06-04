package com.example.admin.newswangyi.Page;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.admin.newswangyi.R;
import com.example.admin.newswangyi.activity.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by admin on 2016/5/30.
 */
public abstract class BasePage  {

    public View view;
    public Context mcontext;
    public boolean isLoading = false;//用于记录数据是否已经加载，默认为未加载，false
    public SlidingMenu mSm;//主界面的侧滑菜单对象
    public TextView txt_title;//标题文本，由子类来设置标题数据
    public BasePage(Context context) {
        this.mcontext = context;
        view = initView();
        mSm = ((MainActivity)mcontext).getSlidingMenu();
    }

    public abstract View initView();
    public View getRootView(){
        return view;
    }
    public abstract void initData();

    //标题栏初始化操作
    public void initTitleBar(View view) {
        Button btn_left = (Button) view.findViewById(R.id.btn_left);
        btn_left.setVisibility(View.GONE);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        ImageButton imgbtn_right = (ImageButton) view.findViewById(R.id.imgbtn_right);
        imgbtn_right.setVisibility(View.GONE);
        ImageButton imgbtn_left = (ImageButton) view.findViewById(R.id.imgbtn_left);
//		imgbtn_left.setBackgroundResource(R.drawable.img_menu);
        imgbtn_left.setImageResource(R.drawable.img_menu);
        imgbtn_left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mSm.toggle();
            }
        });
    }
}
