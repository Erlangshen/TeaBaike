package com.liukun.teabaike.adapter;

import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.liukun.teabaike.fragment.BaseFragment;

public class FragmentContentAdapter extends PagerAdapter {
	private List<BaseFragment> fList;
	private FragmentManager manger;

	public FragmentContentAdapter(List<BaseFragment> fList, FragmentManager manger) {
		super();
		this.fList = fList;
		this.manger = manger;
	}

	@Override
	public int getCount() {
		return fList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(fList.get(position).getView());
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		BaseFragment fragment = fList.get(position);
		FragmentTransaction transaction = manger.beginTransaction();
		if (!fragment.isAdded()) {
			transaction.add(fragment, fragment.getClass().getSimpleName());
			// PagerAdapter里面一般这样用：
			transaction.commitAllowingStateLoss();
			manger.executePendingTransactions();
		}
		if(fragment.getView().getParent()==null){
			container.addView(fragment.getView());
		}
		return fragment.getView();
	}
}
