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

		// ����δ����Ĭ��ͼƬ
		viewHolder.thumbnailImage
				.setDefaultImageResId(R.drawable.widget_loading);
		// ���ü����쳣��ͼƬ
		// holder.imageView.setErrorImageResId(R.drawable.error);
		viewHolder.thumbnailImage.setImageUrl(news.getFirstPicUrl(),
				VolleyTools.getInstance(mContext).getImageLoader());

		return convertView;
	}

	private static class ViewHolder {
		TextView titleTextView; // ��Ŀ
		TextView briefTextView; // ���
		TextView timeTextView; // ʱ��
		NetworkImageView thumbnailImage; // ����ͼ
	}

}
