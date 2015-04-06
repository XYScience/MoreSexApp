package com.science.moresexapp.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sciecne.moresexapp.R;

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
	private List<Map<String, Object>> mArticleBriefList = null;

	public PageListViewAdapter(Context context,
			List<Map<String, Object>> articleBriefList) {
		mInflater = LayoutInflater.from(context);
		this.mArticleBriefList = articleBriefList;
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
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.titleTextView.setText(mArticleBriefList.get(position)
				.toString());
		viewHolder.briefTextView.setText(mArticleBriefList.get(position)
				.toString());
		viewHolder.timeTextView.setText(mArticleBriefList.get(position)
				.toString());

		return convertView;
	}

	private static class ViewHolder {
		TextView titleTextView; // 题目
		TextView briefTextView; // 简介
		TextView timeTextView; // 时间
		ImageView thumbnailImage; // 缩略图
	}

}
