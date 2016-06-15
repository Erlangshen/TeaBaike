package com.liukun.teabaike.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.liukun.teabaike.R;
import com.liukun.teabaike.bean.Details;
import com.liukun.teabaike.constant.AppConstant;
import com.liukun.teabaike.http.RequestAsyncTask;
import com.liukun.teabaike.interfaces.AsyncTaskCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 详情页面
 */
public class TeaDetailsActivity extends BaseActivity implements View.OnClickListener {
    private String dataUrl = "";
    private Details details;
    private TextView detailsTitle, detailsAuthor, detailsTime;
    private WebView detailsWeb;
    private Button contentback, contentshare, collectcontent;
    private ProgressDialog dialog;
    private Timer timer;
    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (dialog.isShowing()){
                dialog.dismiss();
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.tea_details_layout;
    }

    @Override
    protected void initView() {
        detailsTitle = (TextView) findViewById(R.id.detailsTitle);
        detailsAuthor = (TextView) findViewById(R.id.detailsAuthor);
        detailsTime = (TextView) findViewById(R.id.detailsTime);
        detailsWeb = (WebView) findViewById(R.id.detailsWeb);
        contentback = (Button) findViewById(R.id.contentback);
        contentshare = (Button) findViewById(R.id.contentshare);
        collectcontent = (Button) findViewById(R.id.collectcontent);
        contentback.setOnClickListener(this);
        contentshare.setOnClickListener(this);
        collectcontent.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        dataUrl = AppConstant.CONTENT + "&id=" + getIntent().getStringExtra("id");
        dialog=new ProgressDialog(TeaDetailsActivity.this);
        dialog.setMessage("正在请求数据...");
        dialog.show();
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
                            initInfo();
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

    private void initInfo() {
        detailsTitle.setText(details.getTitle());
        detailsAuthor.setText(details.getAuthor());
        detailsTime.setText(details.getCreate_time());
        initWebView();
    }

    private void initWebView() {
        detailsWeb.getSettings().setJavaScriptEnabled(true);
        detailsWeb.setWebViewClient(new TeaWebViewClient());
        //控制图片不超出屏幕范围
        detailsWeb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        detailsWeb.loadDataWithBaseURL(null,details.getWap_content(),"text/html","utf-8",null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contentback:
                finish();
                break;
            case R.id.contentshare:
                break;
            case R.id.collectcontent:
                break;
        }
    }
    class TeaWebViewClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            timer=new Timer();
            TimerTask task=new TimerTask() {
                @Override
                public void run() {
                    if (dialog.isShowing()){
                        handler.sendEmptyMessage(0);
                        timer.cancel();
                        timer.purge();
                    }
                }
            };
            timer.schedule(task,100000);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (dialog!=null&&dialog.isShowing()){
                dialog.dismiss();
            }
            super.onPageFinished(view, url);
        }
    }
}
