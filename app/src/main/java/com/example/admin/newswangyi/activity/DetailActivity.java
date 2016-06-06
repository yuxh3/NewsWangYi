package com.example.admin.newswangyi.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.example.admin.newswangyi.R;

/**
 * Created by admin on 2016/6/5.
 */
public class DetailActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "DetailActivity";
    private WebView webView;
    private ProgressBar pb_loading;
    private Button btn_left;
    private ImageButton imgbtn_left;
    private ImageButton imgbtn_right;
    private ImageButton btn_right;
    private String url;
    private ListView lv;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_news_detail);
        url = getIntent().getStringExtra("url");

        webView = (WebView) findViewById(R.id.news_detail_wv);
        pb_loading = (ProgressBar) findViewById(R.id.pb_loading);
        btn_left = (Button) findViewById(R.id.btn_left);
        imgbtn_left = (ImageButton) findViewById(R.id.imgbtn_left);
        imgbtn_right = (ImageButton) findViewById(R.id.imgbtn_right);
        btn_right = (ImageButton) findViewById(R.id.btn_right);

        initData();


    }

    private void initView() {
//        Log.i(TAG, "77777777777777777777777");
        lv = new ListView(this);
        lv.setBackgroundResource(R.drawable.listview_background);
        // 取出ListView的分割线
        lv.setDivider(null);
        lv.setDividerHeight(0);
        lv.setSelector(android.R.color.transparent);

        String[] objects = new String[]{
                "最小字体", "小字体", "正常字体", "最大字体", "超大字体"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter(this
                , android.R.layout.simple_list_item_1
                , android.R.id.text1
                , objects);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    webView.getSettings().setTextSize(WebSettings.TextSize.SMALLEST);
                    popupWindow.dismiss();
                } else if (position == 1) {
                    webView.getSettings().setTextSize(WebSettings.TextSize.SMALLEST);
                    popupWindow.dismiss();
                } else if (position == 2) {
                    webView.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
                    popupWindow.dismiss();
                } else if (position == 3) {
                    webView.getSettings().setTextSize(WebSettings.TextSize.LARGER);
                    popupWindow.dismiss();
                } else if (position == 4) {
                    webView.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
                    popupWindow.dismiss();
                }
            }
        });
    }


    private void initData() {
        btn_left.setVisibility(View.GONE);
        imgbtn_left.setImageResource(R.drawable.back);
        imgbtn_right.setImageResource(R.drawable.icon_textsize);
        btn_right.setImageResource(R.drawable.icon_share);

        imgbtn_left.setOnClickListener(this);
        imgbtn_right.setOnClickListener(this);
        btn_right.setOnClickListener(this);

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pb_loading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pb_loading.setVisibility(View.VISIBLE);
            }
        });
        
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.imgbtn_left://返回键
                this.finish();
                break;
            case R.id.imgbtn_right://字体设置
                initView();
                popupWindow = new PopupWindow(lv,180,280);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.showAsDropDown(imgbtn_right,0,10);

                break;
            case R.id.btn_right://分享

                break;
        }
    }
}
