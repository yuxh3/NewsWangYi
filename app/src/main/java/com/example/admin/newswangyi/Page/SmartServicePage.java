package com.example.admin.newswangyi.Page;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * Created by admin on 2016/5/30.
 */
public class SmartServicePage extends BasePage {

    public SmartServicePage(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView tv = new TextView(mcontext);
        tv.setText("我是服务中心");
        return tv;
    }

    @Override
    public void initData() {

    }
}
