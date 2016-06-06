package com.liukun.teabaike.activity;


import android.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.liukun.teabaike.R;
import com.liukun.teabaike.adapter.FragmentContentAdapter;
import com.liukun.teabaike.constant.AppConstant;
import com.liukun.teabaike.fragment.BaseFragment;
import com.liukun.teabaike.fragment.ContentFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements
        RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private RadioGroup teaRg;
    private RadioButton toutiao, baike, zixun, jingying, shuju, more;
    private LinearLayout slide;
    private ViewPager teaVp;
    private List<BaseFragment> fList;
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
        FragmentManager manager = getFragmentManager();
        teaVp.setAdapter(new FragmentContentAdapter(fList, manager));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        changeRbColor();
        switch (checkedId) {
            case R.id.toutiao:
                toutiao.setTextColor(0xFF3D9E01);
                teaVp.setCurrentItem(0);
                break;
            case R.id.baike:
                baike.setTextColor(0xFF3D9E01);
                teaVp.setCurrentItem(1);
                break;
            case R.id.zixun:
                zixun.setTextColor(0xFF3D9E01);
                teaVp.setCurrentItem(2);
                break;
            case R.id.jingying:
                jingying.setTextColor(0xFF3D9E01);
                teaVp.setCurrentItem(3);
                break;
            case R.id.shuju:
                shuju.setTextColor(0xFF3D9E01);
                teaVp.setCurrentItem(4);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
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

            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
