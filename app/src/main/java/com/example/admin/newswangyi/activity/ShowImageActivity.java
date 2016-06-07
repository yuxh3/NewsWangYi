package com.example.admin.newswangyi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.admin.newswangyi.R;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by admin on 2016/6/7.
 */
public class ShowImageActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showimage);

        PhotoView photoView = (PhotoView) findViewById(R.id.photoView);
        String url = getIntent().getStringExtra("url");
        Glide.with(ShowImageActivity.this).load(url).into(photoView);
    }
}
