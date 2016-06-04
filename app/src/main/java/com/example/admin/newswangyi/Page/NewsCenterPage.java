package com.example.admin.newswangyi.Page;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.admin.newswangyi.Fragment.MenuFragment2;
import com.example.admin.newswangyi.R;
import com.example.admin.newswangyi.activity.MainActivity;
import com.example.admin.newswangyi.bean.NewCenterBean;
import com.example.admin.newswangyi.utils.GsonTools;
import com.example.admin.newswangyi.utils.HMAPI;
import com.example.admin.newswangyi.utils.SharedPreferenceTools;
import com.google.gson.Gson;
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;

/**
 * Created by admin  2016/5/30.
 */
public class NewsCenterPage extends BasePage {

    private FrameLayout news_center_fl;

    private static final String TAG = "NewsCenterPage";
    private int anInt;
    private StringBuffer sb;


    public NewsCenterPage(Context context) {
        super(context);
    }

    @Override
    public View initView() {
      View view = View.inflate(mcontext, R.layout.news_center_frame, null);
        news_center_fl = (FrameLayout) view.findViewById(R.id.news_center_fl);
        initTitleBar(view);

        return view;
    }

    @Override
    public void initData() {

        //第二次进来的时候，先判断本地是否有缓存，如果有先显示本地数据
       // String result = SharedPreferenceTools.getString(mcontext, HMAPI.NEW_CENTER, "");
//        if (!(TextUtils.isEmpty(result))){
//            paraeJson(result);
//        }
        getNetData();
    }
    //获取网络数据
    private void getNetData() {

        // 请求服务器，获取数据
        /**
         * baseUrl(HMAPI.NEW_CENTER) 设置请求地址 添加一个gson解析类
         * addConverterFactory(GsonConverterFactory.create()) 添加一个gson解析的工厂类
         */
        Retrofit retrofit = new Retrofit.Builder().baseUrl(HMAPI.NEW_CENTER)
                .addConverterFactory(GsonConverterFactory.create()).build();
        Result reslut = retrofit.create(Result.class);
        //通过接口对象调用接口方法，返回一个call对象，进行业务请求方法切换，同步execute与异步enqueue
//		final Call<NewCenterBean> call = reslut.getData();
        final Call<ResponseBody> call = reslut.getData();
        new Thread(){
            public void run() {
//				call.enqueue(new Callback<NewCenterBean>() {
                call.enqueue(new Callback<ResponseBody>() {

                    @Override
//					public void onResponse(Response<NewCenterBean> arg0, Retrofit arg1) {
                    public void onResponse(Response<ResponseBody> arg0, Retrofit arg1) {
                        try {
//							System.out.println(arg0.body().string());

                            parseJson(arg0.body().string());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Throwable arg0) {
                    }
                });
            }
        }.start();
    }

    // 创建一个服务类，用来管理url请求 get post
    public interface Result {
        @GET(HMAPI.NEW_CENTER)
//		Call<NewCenterBean> getData();
        Call<ResponseBody> getData();
    }

    private List<String> menuTitles = new ArrayList<String>();//左边菜单界面的标题数据集合
    private List<BasePage> newsCenterPages = new ArrayList<BasePage>();//新闻中心对应的界面数据集合
    public void parseJson(String json) {

        //isLoading = true;
        SharedPreferenceTools.saveString(mcontext,HMAPI.NEW_CENTER,json);
        Gson gson = new Gson();
        NewCenterBean newCenterBean = GsonTools.changeGsonToBean(json,NewCenterBean.class);
        menuTitles.clear();

        for (int i = 0;i<newCenterBean.data.size();i++){
            NewCenterBean.DataItem dataItem = newCenterBean.data.get(i);
            menuTitles.add(dataItem.title);
            //Log.i(TAG, "iiiiiiiiiiiiiiiiiiiii::::" + menuTitles.size());
        }
        MenuFragment2 menuFragment = ((MainActivity) mcontext).getMenuFragment();
        menuFragment.initMenuTitle(menuTitles);

        newsCenterPages.add(new NewPage(mcontext, newCenterBean.data.get(0)));
        newsCenterPages.add(new TopicPage(mcontext));
        newsCenterPages.add(new PicPage(mcontext));
        newsCenterPages.add(new ActionPage(mcontext));


        switchPage(0,menuTitles.get(0));//界面初始化，让新闻界面进行展示
    }

    public void switchPage(int position,String title){
        anInt = position;
        BasePage basePage = newsCenterPages.get(position);
        txt_title.setText(title);
        switch (position){
            case 0:
                news_center_fl.removeAllViews();
                news_center_fl.addView(basePage.getRootView());
                break;
            case 1:
                news_center_fl.removeAllViews();
                news_center_fl.addView(basePage.getRootView());
                break;
            case 2:
                news_center_fl.removeAllViews();
                news_center_fl.addView(basePage.getRootView());
                break;
            case 3:
                news_center_fl.removeAllViews();
                news_center_fl.addView(basePage.getRootView());
                break;
        }
        basePage.initData();

    }

}
