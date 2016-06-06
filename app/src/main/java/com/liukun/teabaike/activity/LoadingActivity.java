package com.liukun.teabaike.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.liukun.teabaike.R;

public class LoadingActivity  extends  BaseActivity{
    @Override
    protected int getLayoutId() {
        return R.layout.loading_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {


        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SharedPreferences settings=getSharedPreferences("first", MODE_PRIVATE);
                String isFirst= settings.getString("isFirst", "");
                Intent i = new Intent();
                if(TextUtils.isEmpty(isFirst)){
                    i.setClass(LoadingActivity.this, GuideActivity.class);
                }
                else{
                    i.setClass(LoadingActivity.this, MainActivity.class);

                }
                startActivity(i);
                LoadingActivity.this.finish();

            }
        }).start();


    }
}
