package com.liukun.teabaike.activity;


import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.liukun.teabaike.R;
import com.liukun.teabaike.adapter.FragmentContentAdapter;
import com.liukun.teabaike.constant.AppConstant;
import com.liukun.teabaike.fragment.BaseFragment;
import com.liukun.teabaike.fragment.ContentFragment;
import com.liukun.teabaike.fragment.EmptyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements
        RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private RadioGroup teaRg;
    private RadioButton toutiao, baike, zixun, jingying, shuju, more;
    private LinearLayout slide;
    private ViewPager teaVp;
    private List<BaseFragment> fList;
    private int width;//屏幕宽
    private int prePosition = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        teaRg = (RadioGroup) findViewById(R.id.teaRg);
        toutiao = (RadioButton) findViewById(R.id.toutiao);
        baike = (RadioButton) findViewById(R.id.baike);
        zixun = (RadioButton) findViewById(R.id.zixun);
        jingying = (RadioButton) findViewById(R.id.jingying);
        shuju = (RadioButton) findViewById(R.id.shuju);
        more = (RadioButton) findViewById(R.id.more);
        slide = (LinearLayout) findViewById(R.id.slide);
        teaVp = (ViewPager) findViewById(R.id.teavp);

        teaRg.setOnCheckedChangeListener(this);
        teaVp.setOnPageChangeListener(this);
    }

    @Override
    protected void initData() {
        fList = new ArrayList<BaseFragment>();
        fList.add(new ContentFragment(AppConstant.JSON_URL + "#"
                + AppConstant.JSON_LIST_DATA_0, 0));
        fList.add(new ContentFragment(AppConstant.JSON_LIST_DATA_1, 1));
        fList.add(new ContentFragment(AppConstant.JSON_LIST_DATA_2, 2));
        fList.add(new ContentFragment(AppConstant.JSON_LIST_DATA_3, 3));
        fList.add(new ContentFragment(AppConstant.JSON_LIST_DATA_4, 4));
        fList.add(new EmptyFragment());
        FragmentManager manager = getFragmentManager();
        teaVp.setAdapter(new FragmentContentAdapter(fList, manager));

        initSlide();

    }

    /**
     * 初始化小滑块
     */
    private void initSlide() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;  // 屏幕宽度（像素）
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width - dip2px(32), dip2px(2));
        slide.setLayoutParams(lp);
        for (int i = 0; i < 5; i++) {
            View v = new View(MainActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1.0f;
            v.setLayoutParams(params);
            v.setBackgroundColor(0xFFEBEBEB);
            slide.addView(v);
        }
        View view = slide.getChildAt(0);
        view.setBackgroundColor(0xFF3D9D01);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.toutiao:
                changeRbColor();
                toutiao.setTextColor(0xFF3D9E01);
                teaVp.setCurrentItem(0);
                break;
            case R.id.baike:
                changeRbColor();
                baike.setTextColor(0xFF3D9E01);
                teaVp.setCurrentItem(1);
                break;
            case R.id.zixun:
                changeRbColor();
                zixun.setTextColor(0xFF3D9E01);
                teaVp.setCurrentItem(2);
                break;
            case R.id.jingying:
                changeRbColor();
                jingying.setTextColor(0xFF3D9E01);
                teaVp.setCurrentItem(3);
                break;
            case R.id.shuju:
                changeRbColor();
                shuju.setTextColor(0xFF3D9E01);
                teaVp.setCurrentItem(4);
                break;
            case R.id.more:
                toMoreActivity();
                break;
            default:
                break;
        }
    }

    private void changeRbColor() {
        toutiao.setTextColor(0xFF979797);
        baike.setTextColor(0xFF979797);
        zixun.setTextColor(0xFF979797);
        jingying.setTextColor(0xFF979797);
        shuju.setTextColor(0xFF979797);
    }

    /**
     * 跳转到MoreActivity
     */
    private void toMoreActivity() {
        Intent i = new Intent();
        i.setClass(MainActivity.this, MoreActivity.class);
        startActivityForResult(i, 666);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position != 5) {
            View view = slide.getChildAt(position);
            View preView = slide.getChildAt(prePosition);
            view.setBackgroundColor(0xFF3D9D01);
            preView.setBackgroundColor(0xFFEBEBEB);
            prePosition = position;
        }
        switch (position) {
            case 0:
                // 模拟真实点击事件，相当于直接点击了头条
                toutiao.performClick();
                break;
            case 1:
                // 让RadioGroup选中百科
                teaRg.check(R.id.baike);
                break;
            case 2:
                teaRg.check(R.id.zixun);
                break;
            case 3:
                teaRg.check(R.id.jingying);
                break;
            case 4:
                teaRg.check(R.id.shuju);
                break;
            case 5:
                toMoreActivity();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666 && teaVp.getCurrentItem() == 5) {
            teaVp.setCurrentItem(4);
            View view = slide.getChildAt(4);
            View preView = slide.getChildAt(3);
            view.setBackgroundColor(0xFF3D9D01);
            preView.setBackgroundColor(0xFFEBEBEB);
            prePosition = 4;
        }
    }

    /**
     * dp转成px
     */
    public int dip2px(float dipValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
