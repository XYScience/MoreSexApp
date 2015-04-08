package com.science.moresexapp.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.sciecne.moresexapp.R;
import com.sciecne.moresexapp.utils.VolleyTools;
import com.science.moresexapp.bean.News;

/**
 * @description 适配器示例<br>
 *              继承 {@link PageAndRefreshBaseAdapter} 基类<br>
 *              由于已经对数据源做了封装，使用者只需要关注视图的处理
 * 
 * @author 幸运Science 陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-4-2
 * 
 */

public class PageListViewAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context mContext;
	private List<News> mArticleBriefList = new ArrayList<News>();
	RequestQueue mRequestQueue;

	public PageListViewAdapter(Context context, List<News> articleBriefList) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		this.mArticleBriefList = articleBriefList;
		mRequestQueue = Volley.newRequestQueue(context);
	}

	@Override
	public int getCount() {
		return mArticleBriefList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null || convertView.getTag() == null) {
			convertView = mInflater.inflate(R.layout.item_listview, null);
			viewHolder = new ViewHolder();
			viewHolder.titleTextView = (TextView) convertView
					.findViewById(R.id.item_title);
			viewHolder.briefTextView = (TextView) convertView
					.findViewById(R.id.item_brief);
			viewHolder.timeTextView = (TextView) convertView
					.findViewById(R.id.item_time);
			viewHolder.thumbnailImage = (NetworkImageView) convertView
					.findViewById(R.id.thumbnail_image);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		News news = mArticleBriefList.get(position);

		viewHolder.titleTextView.setText(news.getTitle());
		viewHolder.briefTextView.setText(news.getID());
		viewHolder.timeTextView.setText(news.getPublishTime().toString()
				.substring(0, 10));

		// 设置未加载默认图片
		viewHolder.thumbnailImage
				.setDefaultImageResId(R.drawable.widget_loading);
		// 设置加载异常的图片
		// holder.imageView.setErrorImageResId(R.drawable.error);
		viewHolder.thumbnailImage.setImageUrl(news.getFirstPicUrl(),
				VolleyTools.getInstance(mContext).getImageLoader());

		return convertView;
	}

	private static class ViewHolder {
		TextView titleTextView; // 题目
		TextView briefTextView; // 简介
		TextView timeTextView; // 时间
		NetworkImageView thumbnailImage; // 缩略图
	}

}
