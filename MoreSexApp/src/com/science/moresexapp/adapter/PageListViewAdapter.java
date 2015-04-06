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
 * @description ������ʾ��<br>
 *              �̳� {@link PageAndRefreshBaseAdapter} ����<br>
 *              �����Ѿ�������Դ���˷�װ��ʹ����ֻ��Ҫ��ע��ͼ�Ĵ���
 * 
 * @author ����Science ������
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
		TextView titleTextView; // ��Ŀ
		TextView briefTextView; // ���
		TextView timeTextView; // ʱ��
		ImageView thumbnailImage; // ����ͼ
	}

}
