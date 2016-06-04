package com.example.admin.newswangyi.Page;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.newswangyi.R;
import com.example.admin.newswangyi.adapter.NewItemAdapter;
import com.example.admin.newswangyi.bean.NewItemBean;
import com.example.admin.newswangyi.utils.DensityUtil;
import com.example.admin.newswangyi.utils.GsonTools;
import com.example.admin.newswangyi.utils.HMAPI;
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
    private ListView iv_item_news;
    private View topView;
    private LinearLayout top_news_viewpager;
    private LinearLayout dots_ll;
    private TextView txt_title;

    public NewItemPage(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    public View initView() {
        View view = View.inflate(mcontext,R.layout.frag_item_news,null);
        iv_item_news = (ListView) view.findViewById(R.id.iv_item_news);
        topView = view.inflate(mcontext, R.layout.layout_roll_view, null);
        top_news_viewpager = (LinearLayout) topView.findViewById(R.id.top_news_viewpager);

        txt_title = (TextView) topView.findViewById(R.id.top_news_title);
        dots_ll = (LinearLayout) topView.findViewById(R.id.dots_ll);
        return view;
    }

    @Override
    public void initData() {
        getNetData();
    }

    public void getNetData() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, HMAPI.BASE_URL + mUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //Log.i(TAG,"LLLLLLLLLLLLLLLLL"+responseInfo.result);
                parseJson(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private List<NewItemBean.DataItem.NewsItem> newsItems = new ArrayList<NewItemBean.DataItem.NewsItem>();//用来管理当前新闻列表数据
    private NewItemAdapter adapter;//新闻列表的适配器
    private void parseJson(String result) {
        newsItems.clear();
        NewItemBean newItemBean = GsonTools.changeGsonToBean(result, NewItemBean.class);
        if (newItemBean.retcode == 200){
            isLoading = true;
            newsItems.addAll(newItemBean.data.news);

            List<String> titles = new ArrayList<String>();//轮播图的标题数据集合
            List<String> imageUrls = new ArrayList<String>();//轮播图的图片地址集合
            for (NewItemBean.DataItem.TopnewsItem item : newItemBean.data.topnews){
                titles.add(item.title);
                imageUrls.add(item.topimage);
            }
            initDots(titles.size());
            //轮播图
            RollViewPager rollView = new RollViewPager(mcontext,dots, new RollViewPager.OnClickListener() {
                @Override
                public void onClick() {
                    Toast.makeText(mcontext,"我被点击了",Toast.LENGTH_SHORT).show();
                }
            });
            rollView.setTitle(titles, txt_title);
            rollView.setmImages(imageUrls);
            rollView.start();
            if (iv_item_news.getHeaderViewsCount()<1){
                top_news_viewpager.addView(rollView);
                iv_item_news.addHeaderView(topView);
            }
            if (adapter == null){
                adapter = new NewItemAdapter(newsItems,mcontext);
                iv_item_news.setAdapter(adapter);
            }else {
                adapter.notifyDataSetChanged();
            }

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
