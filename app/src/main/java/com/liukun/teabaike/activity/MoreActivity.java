package com.liukun.teabaike.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.liukun.teabaike.R;

public class MoreActivity extends  BaseActivity implements View.OnClickListener,View.OnFocusChangeListener{
    private LinearLayout searchLayout;
    private Button goSearch;
    private EditText keyWord;
    @Override
    protected int getLayoutId() {
        return R.layout.more_layout;
    }

    @Override
    protected void initView() {
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        goSearch= (Button) findViewById(R.id.goSearch);
        keyWord= (EditText) findViewById(R.id.keyWorld);

        goSearch.setOnClickListener(this);
        keyWord.setOnFocusChangeListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goSearch:
                showShortToast("search");
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            searchLayout.setBackgroundResource(R.drawable.linear_pressed);
        }else {
            searchLayout.setBackgroundResource(R.drawable.linear_on);
        }
    }
}
