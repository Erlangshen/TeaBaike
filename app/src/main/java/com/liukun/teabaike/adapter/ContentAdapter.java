package com.liukun.teabaike.adapter;

import java.util.List;


import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liukun.teabaike.R;
import com.liukun.teabaike.TeaApplication;
import com.liukun.teabaike.bean.Tea;
import com.liukun.teabaike.utils.ImageDownLoader;

public class ContentAdapter extends BaseAdapter {

	private Context con;
	private List<Tea> teaList;
	private ImageDownLoader loader;


	public ContentAdapter(Context con, List<Tea> teaList) {
		super();
		this.con = con;
		this.teaList = teaList;
		loader= TeaApplication.getApp().getLoaderInstance();
	}

	// listview有多少条数据
	@Override
	public int getCount() {
		return teaList.size();
	}

	// 得到每条数据
	@Override
	public Object getItem(int position) {
		return teaList.get(position);
	}

	// 每个item的id
	@Override
	public long getItemId(int position) {
		return position;
	}

	// 得到当前的item对应的视图
	// position：当前的位置
	// convertView：创建的视图
	// parent：listview
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		Tea t = teaList.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(con).inflate(
					R.layout.my_item_layout, null);
			holder = new Holder();
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.desc = (TextView) convertView.findViewById(R.id.desc);
			holder.src = (TextView) convertView.findViewById(R.id.src);
			holder.auther = (TextView) convertView.findViewById(R.id.auther);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.title.setText(t.getTitle());
		holder.desc.setText(t.getDescription());
		holder.src.setText(t.getSource());
		holder.date.setText(t.getCreate_time());
		holder.auther.setText(t.getNickname());
		final String imageUrl=t.getWap_thumb();
		holder.image.setTag(imageUrl);
		if (!TextUtils.isEmpty(imageUrl)) {
			holder.image.setVisibility(View.VISIBLE);
			holder.image.setImageResource(R.drawable.defaultcovers);
			Bitmap bitmap=loader.downLoader(holder.image, new ImageDownLoader.ImageLoaderlistener() {
				
				@Override
				public void onImageLoader(Bitmap bitmap, ImageView imageView) {
					if(imageView.getTag()!=null&&imageView.getTag().equals(imageUrl)){
						imageView.setImageBitmap(bitmap);
					}
				}
			});
			if(bitmap!=null){
				holder.image.setImageBitmap(bitmap);
			}
		}else{
			holder.image.setVisibility(View.GONE);
		}
		return convertView;
	}

	class Holder {
		TextView title, desc, src, auther, date;
		ImageView image;
	}
}