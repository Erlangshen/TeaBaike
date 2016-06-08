package com.liukun.teabaike.activity;

import android.view.View;

import com.liukun.teabaike.R;

public class MoreActivity extends  BaseActivity{
    @Override
    protected int getLayoutId() {
        return R.layout.more_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
    public void more_back(View view){
        finish();
    }
    public void more_home(View view){
        finish();
    }
}
