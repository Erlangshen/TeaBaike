package com.liukun.teabaike.fragment;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.liukun.teabaike.R;
import com.liukun.teabaike.adapter.ContentAdapter;
import com.liukun.teabaike.bean.Tea;
import com.liukun.teabaike.http.RequestAsyncTask;
import com.liukun.teabaike.interfaces.AsyncTaskCallBack;

@SuppressLint("ValidFragment")
public class ContentFragment extends BaseFragment {
	private String url;
	private int flag;
	private String dataUrl = "";
	private String headUrl = "";
	private List<Tea> tList;
	private ListView fListView;

	public ContentFragment(){}
	public ContentFragment(String url, int flag) {
		this.url = url;
		this.flag = flag;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_layout;
	}

	@Override
	protected void initView(View v) {
		fListView=(ListView) v.findViewById(R.id.fListView);
	}

	@Override
	protected void initData() {
		getUrl();
		new RequestAsyncTask(getActivity(), dataUrl, new AsyncTaskCallBack() {
			
			@Override
			public void post(String rest) {

				if (TextUtils.isEmpty(rest)) {
					showToast("网络错误");
				} else {
					try {
						JSONObject obj = new JSONObject(rest);
						String msg = obj.getString("errorMessage");
						if ("success".equals(msg)) {
							JSONArray array = obj.getJSONArray("data");
							tList=com.alibaba.fastjson.JSONArray.parseArray(array.toString(), Tea.class);
							fListView.setAdapter(new ContentAdapter(getActivity(), tList));
						} else {
							showToast("数据请求失败");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			
			}
		}).execute();
	}

	private void getUrl() {
		switch (flag) {
		case 0:
			// 34#56===[34,56],34#5#389===[34,5,389]
			String[] split = url.split("#");
			dataUrl = split[1];
			headUrl = split[0];
			break;
		case 1:
		case 2:
		case 3:
		case 4:
			dataUrl = url;
			break;
		default:
			break;
		}
	}

}
