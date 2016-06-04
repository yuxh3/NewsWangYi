package com.example.admin.newswangyi.Page;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * Created by admin on 2016/5/31.
 */
public class ActionPage extends BasePage {

    public ActionPage(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView tv = new TextView(mcontext);
        tv.setText("我是行动中心");
        return tv;
    }

    @Override
    public void initData() {

    }
}
