package com.example.admin.newswangyi.bean;

import java.util.List;

/**
 * Created by admin on 2016/5/30.
 */
public class NewCenterBean {
    public List<DataItem> data;
    public List<String> extend;
    public int retcode;

    public class DataItem {
        public List<ChildrenItem> children;
        public int id;
        public String title;
        public int type;
        public String url;
        public String url1;
        public String weekurl;
    }

    public class ChildrenItem {
        public int id;
        public String title;
        public int type;
        public String url;
    }
}
