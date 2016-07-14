package com.liukun.teabaike.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.liukun.teabaike.R;
import com.liukun.teabaike.TeaApplication;
import com.liukun.teabaike.activity.MainActivity;
import com.liukun.teabaike.activity.TeaDetailsActivity;
import com.liukun.teabaike.adapter.ContentAdapter;
import com.liukun.teabaike.adapter.GuideAdapter;
import com.liukun.teabaike.bean.Advert;
import com.liukun.teabaike.bean.Tea;
import com.liukun.teabaike.http.RequestAsyncTask;
import com.liukun.teabaike.interfaces.AdvertCallBack;
import com.liukun.teabaike.interfaces.AsyncTaskCallBack;
import com.liukun.teabaike.utils.ImageDownLoader;

@SuppressLint("ValidFragment")
public class ContentFragment extends BaseFragment implements AdvertCallBack, PullToRefreshBase.OnRefreshListener2<ListView> {
    private String url;
    private int flag;
    private String dataUrl = "";
    private String headUrl = "";
    //数据返回的集合，全部数据集合
    private List<Tea> tList, dataList;
    private PullToRefreshListView fListView;
    //广告数据集合
    private List<Advert> adList;
    //广告imageview集合
    private List<View> headList;
    private ImageDownLoader loader;
    //广告viewpager
    private ViewPager headVp;
    private int count = 0;
    //广告文字内容
    private List<String> textList;
    private int prePosition = 0;
    private ListView listView;
    private int page=1;
    private  ContentAdapter adapter=null;
    private GuideAdapter headAdapter=null;
    private View view;

    public ContentFragment() {
    }

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
        fListView = (PullToRefreshListView) v.findViewById(R.id.fListView);
        fListView.setOnRefreshListener(ContentFragment.this);
        fListView.setMode(PullToRefreshBase.Mode.BOTH);
        listView = fListView.getRefreshableView();
    }

    @Override
    protected void initData() {
        getUrl();
        dataList=new ArrayList<Tea>();
        adapter=new ContentAdapter(getActivity(),dataList);
        listView.setAdapter(adapter);
        initHeader();
        initTeaData(-1,dataUrl);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), TeaDetailsActivity.class);
                Tea tea = (Tea) parent.getAdapter().getItem(position);
                if (tea != null) {
                    i.putExtra("id", tea.getId());
                }
                startActivity(i);
            }
        });
    }

    /**
     * 加载数据，flag＝0:加在第一条，不设置默认加在最后
     */
    private void initTeaData(final int position, String teaUrl) {
        if (flag == 0) {
           getHeadView();
        }
        registerForContextMenu(listView);
        new RequestAsyncTask(getActivity(), teaUrl, new AsyncTaskCallBack() {

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
                            tList = com.alibaba.fastjson.JSONArray.parseArray(array.toString(), Tea.class);
                            if (0 == position) {
                                dataList.addAll(0, tList);
                            }  else {
                                dataList.addAll(tList);
                            }
                            adapter.notifyDataSetChanged();
                            fListView.onRefreshComplete();
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
    /**配置一些头视图信息*/
    private void initHeader() {
        headList = new ArrayList<View>();
        headAdapter=new GuideAdapter(headList, ContentFragment.this, 1);
        view = LayoutInflater.from(getActivity()).inflate(R.layout.head_view, null);
        headVp = (ViewPager) view.findViewById(R.id.headVp);
        headVp.setAdapter(headAdapter);
        final TextView headTv = (TextView) view.findViewById(R.id.headTv);
        final LinearLayout headLinear = (LinearLayout) view.findViewById(R.id.headLinear);
        RelativeLayout headRelative = (RelativeLayout) view.findViewById(R.id.headRelative);
        headRelative.getBackground().setAlpha(100);
        textList = new ArrayList<String>();
        loader = TeaApplication.getApp().getLoaderInstance();
        for (int i = 0; i < 3; i++) {
            ImageView image = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            image.setLayoutParams(params);
            image.setImageResource(R.drawable.page);
            headLinear.addView(image);
        }
        ImageView image = (ImageView) headLinear.getChildAt(0);
        image.setImageResource(R.drawable.page_now);
        headVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                headTv.setText(textList.get(position));
                ImageView image = (ImageView) headLinear.getChildAt(position);
                ImageView preImage = (ImageView) headLinear.getChildAt(prePosition);
                preImage.setImageResource(R.drawable.page);
                image.setImageResource(R.drawable.page_now);
                prePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (count % 3) {
                    case 0:
                        headVp.setCurrentItem(0);
                        break;
                    case 1:
                        headVp.setCurrentItem(1);
                        break;
                    case 2:
                        headVp.setCurrentItem(2);
                        break;
                }
            }
        };
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(count);
                count++;
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000, 1500);

        if (0==flag){
            listView.addHeaderView(getHeadView());
        }
    }

    /**
     * 加载listview的headview
     */
    private View getHeadView() {
        new RequestAsyncTask(getActivity(), headUrl, new AsyncTaskCallBack() {
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
                            adList = com.alibaba.fastjson.JSONArray.parseArray(array.toString(), Advert.class);
                            List<View> vList=new ArrayList<View>();
                            List<String> tList=new ArrayList<String>();
                            for (Advert ad : adList) {
                                String imageUrl = ad.getImage();
                                ImageView image = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.head_image, null);
                                image.setTag(imageUrl);
                                Bitmap bitmap = loader.downLoader(image, new ImageDownLoader.ImageLoaderlistener() {
                                    @Override
                                    public void onImageLoader(Bitmap bitmap, ImageView imageView) {
                                        imageView.setImageBitmap(bitmap);
                                    }
                                });
                                if (bitmap != null) {
                                    image.setImageBitmap(bitmap);
                                }
                                vList.add(image);
                                tList.add(ad.getTitle());
                            }
                            headList.clear();
                            headList.addAll(vList);
                            textList.clear();
                            textList.addAll(tList);
                            headAdapter.notifyDataSetChanged();
                        } else {
                            showToast("数据请求失败");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).execute();
        return view;
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

    @Override
    public void callBack(int position) {
        Intent intent = new Intent(getActivity(), TeaDetailsActivity.class);
        intent.putExtra("id", adList.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        page++;
        initTeaData(0,dataUrl+"&page="+page);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page++;
        initTeaData(1,dataUrl+"&page="+page);
    }
}
