package com.example.admin.newswangyi.Page;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * Created by admin on 2016/5/30.
 */
public class FuctionPage extends BasePage {

    public FuctionPage(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView tv = new TextView(mcontext);
        tv.setText("我是首页");
        return tv;
    }

    @Override
    public void initData() {

    }
}
