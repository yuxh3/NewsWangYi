package com.example.admin.newswangyi.bean;

import java.util.List;

/**
 * Created by admin on 2016/6/2.
 */
public class NewItemBean {

    public DataItem data;
    public int retcode;
    public class DataItem {
        public String countcommenturl;
        public String more;
        public List<NewsItem> news;
        public String title;
        public List<TopnewsItem> topnews;

        public class TopnewsItem{
            public boolean comment;
            public String commentlist;
            public int id;
            public String commenturl;
            public String pubdate;
            public String title;
            public String topimage;
            public String type;
            public String url;
        }

        public class NewsItem{
            public boolean comment;
            public String commentlist;
            public String commenturl;
            public int id;
            public String listimage;
            public String pubdate;
            public String title;
            public String type;
            public String url;
            public boolean isRead = false;
        }
    }
}
