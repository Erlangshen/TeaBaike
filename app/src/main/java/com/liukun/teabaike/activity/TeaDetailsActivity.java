package com.liukun.teabaike.activity;

import android.text.TextUtils;

import com.liukun.teabaike.R;
import com.liukun.teabaike.bean.Details;
import com.liukun.teabaike.constant.AppConstant;
import com.liukun.teabaike.http.RequestAsyncTask;
import com.liukun.teabaike.interfaces.AsyncTaskCallBack;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 详情页面
 */
public class TeaDetailsActivity extends BaseActivity{
    private String dataUrl="";
    private Details details;
    @Override
    protected int getLayoutId() {
        return R.layout.tea_details_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        dataUrl= AppConstant.CONTENT+"&id="+getIntent().getStringExtra("id");
        new RequestAsyncTask(TeaDetailsActivity.this, dataUrl, new AsyncTaskCallBack() {
            @Override
            public void post(String rest) {
                if (TextUtils.isEmpty(rest)) {
                    showShortToast("网络错误");
                } else {
                    try {
                        JSONObject obj = new JSONObject(rest);
                        String msg = obj.getString("errorMessage");
                        if ("success".equals(msg)) {
                            JSONObject data = obj.getJSONObject("data");
                            details = com.alibaba.fastjson.JSONObject.parseObject(data.toString(), Details.class);
                        } else {
                            showShortToast("数据请求失败");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).execute();
    }
}
