package com.hsj.pullrefresh.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hsj.pullrefresh.R;

/**
 * 这个类封装了下拉刷新的布局
 *
 * @author weicy引用自  Li Hong
 * @since 2013-7-30
 * @time 2014/07/01
 */
public class FooterLoadingLayout extends LoadingLayout {
	/** 进度条 */
	private ProgressBar mProgressBar;
	/** 显示的文本 */
	private TextView mHintView;

	/**
	 * 构造方法
	 *
	 * @param context
	 *            context
	 */
	public FooterLoadingLayout(Context context) {
		super(context);
		init(context);
	}

	/**
	 * 构造方法
	 *
	 * @param context
	 *            context
	 * @param attrs
	 *            attrs
	 */
	public FooterLoadingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * 初始化
	 *
	 * @param context
	 *            context
	 */
	private void init(Context context) {
		mProgressBar = (ProgressBar) findViewById(R.id.pull_to_load_footer_progressbar);
		mHintView = (TextView) findViewById(R.id.pull_to_load_footer_hint_textview);

		setState(State.RESET);
	}

	@Override
	protected View createLoadingView(Context context, AttributeSet attrs) {
		View container = LayoutInflater.from(context).inflate(
				R.layout.pull_to_load_footer, null);
		return container;
	}

	@Override
	public void setLastUpdatedLabel(CharSequence label) {
	}

	@Override
	public int getContentSize() {
		View view = findViewById(R.id.pull_to_load_footer_content);
		if (null != view) {
			return view.getHeight();
		}

		return (int) (getResources().getDisplayMetrics().density * 30);
	}

	@Override
	protected void onStateChanged(State curState, State oldState) {
		if (curState != State.NO_MORE_DATA)
			show(true);
		mProgressBar.setVisibility(View.GONE);
		mHintView.setVisibility(View.INVISIBLE);

		super.onStateChanged(curState, oldState);
	}

	@Override
	protected void onReset() {
		mHintView.setText(R.string.pull_to_refresh_header_hint_loading);
	}

	@Override
	protected void onPullToRefresh() {
		mHintView.setVisibility(View.VISIBLE);
		mHintView.setText(R.string.pull_to_load_footer_hint_normal);
	}

	@Override
	protected void onReleaseToRefresh() {
		mHintView.setVisibility(View.VISIBLE);
		mHintView.setText(R.string.pull_to_load_footer_hint_ready);
	}

	@Override
	protected void onRefreshing() {
		mProgressBar.setVisibility(View.VISIBLE);
		mHintView.setVisibility(View.VISIBLE);
		mHintView.setText(R.string.pull_to_load_footer_hint_loading);
	}

	@Override
	protected void onNoMoreData() {
		show(false);
		mHintView.setVisibility(View.VISIBLE);
		mHintView.setText(R.string.pull_to_load_no_more_data);
	}

	public TextView getmHintView() {
		return mHintView;
	}
}
