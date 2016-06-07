package com.example.admin.newswangyi.Page;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.newswangyi.PullToRefresh.PullToRefreshBase;
import com.example.admin.newswangyi.PullToRefresh.PullToRefreshListView;
import com.example.admin.newswangyi.R;
import com.example.admin.newswangyi.activity.DetailActivity;
import com.example.admin.newswangyi.adapter.NewItemAdapter;
import com.example.admin.newswangyi.bean.NewItemBean;
import com.example.admin.newswangyi.utils.CommonUtil;
import com.example.admin.newswangyi.utils.DensityUtil;
import com.example.admin.newswangyi.utils.GsonTools;
import com.example.admin.newswangyi.utils.HMAPI;
import com.example.admin.newswangyi.utils.SharedPreferenceTools;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/6/2.
 */
public class NewItemPage extends BasePage {

    private static final String TAG = "NewItemPage";
    private final String mUrl;
    private ListView iv;
    private View topView;
    private LinearLayout top_news_viewpager;
    private LinearLayout dots_ll;
    private TextView txt_title;
    private PullToRefreshListView lv;
    private String moreUrl;
    private RollViewPager rollView;
    private List<NewItemBean.DataItem.NewsItem> newsItems = new ArrayList<NewItemBean.DataItem.NewsItem>();//用来管理当前新闻列表数据

    public NewItemPage(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    public View initView() {
        View view = View.inflate(mcontext, R.layout.frag_item_news, null);
        lv = (PullToRefreshListView) view.findViewById(R.id.iv_item_news);
        topView = view.inflate(mcontext, R.layout.layout_roll_view, null);
        top_news_viewpager = (LinearLayout) topView.findViewById(R.id.top_news_viewpager);

        txt_title = (TextView) topView.findViewById(R.id.top_news_title);
        dots_ll = (LinearLayout) topView.findViewById(R.id.dots_ll);
        lv.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewItemBean.DataItem.NewsItem itemAtPosition = (NewItemBean.DataItem.NewsItem)lv.getRefreshableView().getItemAtPosition(position);
                if (!itemAtPosition.isRead){
                    itemAtPosition.isRead = true;
                    adapter.notifyDataSetChanged();
                }
                Intent intent = new Intent(mcontext,DetailActivity.class);
                intent.putExtra("url",itemAtPosition.url);
                mcontext.startActivity(intent);
            }
        });

        lv.setPullLoadEnabled(false);
        lv.setPullRefreshEnabled(true);
        lv.setScrollLoadEnabled(true);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                if (TextUtils.isEmpty(moreUrl)){
                    Toast.makeText(mcontext,"没有更多的数据了",Toast.LENGTH_SHORT).show();
                    lv.onPullDownRefreshComplete();
                }else {
                    getNetData(true, moreUrl);
                }
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (TextUtils.isEmpty(moreUrl)){
                    Toast.makeText(mcontext,"没有等多的数据了",Toast.LENGTH_SHORT).show();
                    lv.onPullUpRefreshComplete();
                }else {
                    getNetData(false, moreUrl);
                }
            }
        });

        String date = SharedPreferenceTools.getString(mcontext,"LastUpdatedLabel","");
        lv.setLastUpdatedLabel(date);
        return view;


    }

    @Override
    public void initData() {
        String result = SharedPreferenceTools.getString(mcontext, HMAPI.BASE_URL + mUrl, "");
        if (!TextUtils.isEmpty(result)){
            parseJson(result,true);
        }
        getNetData(true,mUrl);
    }



    public void getNetData(final boolean isFresh, String mUrl) {

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, HMAPI.BASE_URL + mUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                // Log.i(TAG,responseInfo.result);
                parseJson(responseInfo.result, isFresh);

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private NewItemAdapter adapter;//新闻列表的适配器
    protected void parseJson(String result, boolean isFresh) {
        String Data = CommonUtil.getStringDate();
        lv.setLastUpdatedLabel(Data);
        SharedPreferenceTools.saveString(mcontext, "LastUpdatedLabel", Data);
       // newsItems.clear();
        NewItemBean newItemBean = GsonTools.changeGsonToBean(result, NewItemBean.class);
        if (newItemBean.retcode == 200){
            isLoading = true;
            SharedPreferenceTools.saveString(mcontext, HMAPI.BASE_URL+mUrl, result);
                newsItems.addAll(newItemBean.data.news);

                initDots(newItemBean.data.topnews.size());

            if (isFresh) {
                initDots(newItemBean.data.topnews.size());

                if (rollView != null) {
                    rollView.stop();
                }

                //轮播图
                rollView = new RollViewPager(mcontext, dots, new RollViewPager.OnClickListener() {
                    @Override
                    public void onClick() {
                        Toast.makeText(mcontext, "我被点击了", Toast.LENGTH_SHORT).show();
                    }
                });
                List<String> titles = new ArrayList<String>();//轮播图的标题数据集合
                List<String> imageUrls = new ArrayList<String>();//轮播图的图片地址集合
                for (NewItemBean.DataItem.TopnewsItem item : newItemBean.data.topnews) {
                    titles.add(item.title);
                    imageUrls.add(item.topimage);
                }
                rollView.setTitle(titles, txt_title);
                rollView.setmImages(imageUrls);
                rollView.start();
                top_news_viewpager.removeAllViews();
                top_news_viewpager.addView(rollView);//把轮播图添加到topview中
                if (lv.getRefreshableView().getHeaderViewsCount() < 1) {
                    lv.getRefreshableView().addHeaderView(topView);
                }
            }
            if (isFresh){
                Log.i(TAG, "moreUrl" + "HHHHHHHHHHHHHHHHHHHHHHH");
                newsItems.clear();
                newsItems.addAll(newItemBean.data.news);
            }else {
                newsItems.addAll(newItemBean.data.news);


            }
            moreUrl = newItemBean.data.more;

            if (!TextUtils.isEmpty(moreUrl)){
                lv.setHasMoreData(true);

            }else {
                lv.setHasMoreData(false);
            }

            if (adapter == null){
                adapter = new NewItemAdapter(newsItems,mcontext);
                lv.getRefreshableView().setAdapter(adapter);
            }else {
                adapter.mDatas=newsItems;
                adapter.notifyDataSetChanged();
            }

            lv.onPullUpRefreshComplete();
            lv.onPullDownRefreshComplete();
        }
    }
    private List<View> dots = new ArrayList<View>();// 点的集合，用来管理，当轮播图滑动时候，进行改变
    private void initDots(int size) {
        dots_ll.removeAllViews();
        dots.clear();

        for (int i = 0;i<size;i++){
            View dot = new View(mcontext);
           if (i == 0){
                dot.setBackgroundResource(R.drawable.dot_focus);
         }else {
                dot.setBackgroundResource(R.drawable.dot_normal);
          }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    DensityUtil.dip2px(mcontext, 5), DensityUtil.dip2px(
                    mcontext, 5));
            if (i!= 0){
                params.leftMargin = DensityUtil.dip2px(mcontext, 5);
            }
            dot.setLayoutParams(params);
            dots_ll.addView(dot);
            dots.add(dot);
        }

    }
}
