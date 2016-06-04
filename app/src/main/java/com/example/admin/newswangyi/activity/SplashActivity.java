package com.example.admin.newswangyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.admin.newswangyi.R;
import com.example.admin.newswangyi.utils.SharedPreferenceTools;

public class SplashActivity extends Activity {

    private Boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isFirst = false;
                if (isFirst){
                    Toast.makeText(SplashActivity.this, "欢迎进入主界面", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SplashActivity.this, "欢迎进入引导面", Toast.LENGTH_SHORT).show();
                    isFirst = !isFirst;
                    SharedPreferenceTools.saveBoolean(SplashActivity.this, "isFirst", isFirst);

                    Intent intent = new Intent(SplashActivity.this,GuideActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        },3000);
    }
}
