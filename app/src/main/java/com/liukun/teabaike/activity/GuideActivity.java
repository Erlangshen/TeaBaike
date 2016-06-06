package com.liukun.teabaike.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.liukun.teabaike.R;
import com.liukun.teabaike.adapter.GuideAdapter;

public class GuideActivity extends BaseActivity implements OnPageChangeListener {
	private ViewPager vp;
	private List<View> viewList;
	private LinearLayout linear;
	private int prePosition = 0;

	@Override
	protected int getLayoutId() {
		return R.layout.guide_layout;
	}

	@Override
	protected void initView() {
		vp = (ViewPager) findViewById(R.id.gp);
		linear = (LinearLayout) findViewById(R.id.linear);
	}

	@Override
	protected void initData() {
		for (int i = 0; i < 3; i++) {
			ImageView image = new ImageView(GuideActivity.this);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			params.leftMargin = 5;
			image.setLayoutParams(params);
			image.setImageResource(R.drawable.page);
			linear.addView(image);
		}
		ImageView image = (ImageView) linear.getChildAt(0);
		image.setImageResource(R.drawable.page_now);
		viewList = new ArrayList<View>();
		LayoutInflater inflater1 = LayoutInflater.from(GuideActivity.this);
		View view1 = inflater1.inflate(R.layout.layout_1, null);
		LayoutInflater inflater2 = getLayoutInflater();
		View view2 = inflater2.inflate(R.layout.layout_2, null);
		LayoutInflater inflater3 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view3 = inflater3.inflate(R.layout.layout_3, null);
		viewList.add(view1);
		viewList.add(view2);
		viewList.add(view3);
		vp.setAdapter(new GuideAdapter(viewList));
		vp.setOnPageChangeListener(this);
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		final int width = wm.getDefaultDisplay().getWidth();
		final int height = wm.getDefaultDisplay().getHeight();
		view3.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float x = event.getX();
				float y = event.getY();
				x/=width;
				y/=height;
				if(x>=0.33&&x<=0.69&&y>=0.29&&y<=0.37){
					SharedPreferences settings=getSharedPreferences("first",MODE_PRIVATE );
					Editor editor=settings.edit();
					editor.putString("isFirst", "no");
					editor.commit();
					startActivity(new Intent(GuideActivity.this, MainActivity.class));
					GuideActivity.this.finish();
				}
				return false;
			}
		});
	}

	@Override
	public void onPageSelected(int position) {
		ImageView image = (ImageView) linear.getChildAt(position);
		ImageView preImage = (ImageView) linear.getChildAt(prePosition);
		preImage.setImageResource(R.drawable.page);
		image.setImageResource(R.drawable.page_now);
		prePosition = position;

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

}
