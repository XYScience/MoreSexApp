package com.science.moresexapp.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.sciecne.moresexapp.R;
import com.science.moresexapp.bean.Article;
import com.science.moresexapp.utils.VolleyTools;

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

	private String mTimeString;
	private Integer mClick;
	private LayoutInflater mInflater;
	private Context mContext;
	private List<Article> mArticleBriefList = new ArrayList<Article>();
	private RequestQueue mRequestQueue;
	// ��¼��ǰչ���������
	private int expandPosition = -1;
	ViewHolder viewHolder = null;

	public PageListViewAdapter(Context context, List<Article> articleBriefList) {
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

		if (convertView == null || convertView.getTag() == null) {
			convertView = mInflater.inflate(R.layout.item_listview, null);
			viewHolder = new ViewHolder();
			viewHolder.titleTextView = (TextView) convertView
					.findViewById(R.id.item_title);
			viewHolder.briefTextView = (TextView) convertView
					.findViewById(R.id.item_brief);
			viewHolder.timeTextView = (TextView) convertView
					.findViewById(R.id.item_time);
			viewHolder.sourceTextView = (TextView) convertView
					.findViewById(R.id.item_source);
			viewHolder.clickTextView = (TextView) convertView
					.findViewById(R.id.item_click);
			viewHolder.thumbnailImage = (NetworkImageView) convertView
					.findViewById(R.id.thumbnail_image);

			viewHolder.morePraiseCollectImg = (ImageView) convertView
					.findViewById(R.id.more_praise_collect_img);
			viewHolder.morePraiseCollectLayout = (LinearLayout) convertView
					.findViewById(R.id.more_praise_collect_ll);

			viewHolder.praiseLayout = (LinearLayout) convertView
					.findViewById(R.id.praise_layout);
			viewHolder.collectLayout = (LinearLayout) convertView
					.findViewById(R.id.collect_layout);
			viewHolder.praiseText = (TextView) convertView
					.findViewById(R.id.praise);
			viewHolder.collectText = (TextView) convertView
					.findViewById(R.id.collect);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Article article = mArticleBriefList.get(position);
		if (null != article) {

			if (article.getTime() == null) {
				mTimeString = "δ֪";
			} else {
				mTimeString = article.getTime().substring(0, 10);
			}
			if (article.getClick() == null) {
				mClick = 0;
			} else {
				mClick = article.getClick();
			}

			viewHolder.titleTextView.setText("" + article.getTitle());
			viewHolder.briefTextView.setText(""
					+ article.getContent().replaceAll("���µ���", "")
							.replaceAll("<br/>", "\b\b\b\b\b\b"));// �滻<br/>�Ϳո�
			// .replaceAll("[\\s\\\u003cbr/\u003e]", " "));// �滻<br/>�Ϳո�
			viewHolder.timeTextView.setText("" + mTimeString);
			viewHolder.sourceTextView.setText("" + article.getSource());
			viewHolder.clickTextView.setText("" + mClick);
			// ����δ����Ĭ��ͼƬ
			viewHolder.thumbnailImage
					.setDefaultImageResId(R.drawable.widget_loading);
			// ���ü����쳣��ͼƬ
			// holder.imageView.setErrorImageResId(R.drawable.error);
			viewHolder.thumbnailImage.setImageUrl(article.getImgUrl(),
					VolleyTools.getInstance(mContext).getImageLoader());

			// viewHolder.morePraiseCollectImg
			// .setOnClickListener(new MorePraiseCollectImg(position));
			// ���������ǵ�ǰ�����չ��������������
			// if (expandPosition == position) {
			// viewHolder.morePraiseCollectLayout.setVisibility(View.VISIBLE);
			// viewHolder.morePraiseCollectImg
			// .setImageResource(R.drawable.more_select_up);
			// notifyDataSetChanged();
			// AnimationSet aset = new AnimationSet(true);
			// RotateAnimation rAnimation = new RotateAnimation(0, 180,
			// Animation.RELATIVE_TO_SELF, 0.5f,
			// Animation.RELATIVE_TO_SELF, 0.5f);
			// // ���ö���ִ�й����õ�ʱ��,��λ����
			// rAnimation.setDuration(1000);
			// // ����ִ����֮��Ч��������ִ����֮���״̬
			// aset.setFillAfter(true);
			// // ���������붯��������
			// aset.addAnimation(rAnimation);
			// // imageView��Ҫ��ת�Ŀؼ�������.
			// viewHolder.morePraiseCollectImg.startAnimation(aset);
			// } else {
			// viewHolder.morePraiseCollectLayout.setVisibility(View.GONE);
			// viewHolder.morePraiseCollectImg
			// .setImageResource(R.drawable.more_select_down);
			// }

			viewHolder.praiseLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(mContext, "1111111", Toast.LENGTH_LONG);
					Drawable drawable = mContext.getResources().getDrawable(
							R.drawable.praise_selected);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(),
							drawable.getMinimumHeight());
					viewHolder.praiseText.setCompoundDrawables(drawable, null,
							null, null);
				}
			});
			viewHolder.collectLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// Drawable drawable = mContext.getResources().getDrawable(
					// R.drawable.collect_selected);
					// drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					// drawable.getMinimumHeight());
					// viewHolder.collectText.setCompoundDrawables(drawable,
					// null,
					// null, null);
				}
			});

		}

		return convertView;
	}

	class MorePraiseCollectImg implements OnClickListener {

		private int position;

		public MorePraiseCollectImg(int position) {
			super();
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// �����ǰ��Ϊչ����������Ϊ-1��Ŀ����Ϊ���������أ������ǰ��Ϊ���أ��򽫵�ǰλ�����ø�ȫ�ֱ���������չ������Ҳ���ǽ������м����ʵ�ֲ��ֵ�չ��������
			if (expandPosition == position) {
				expandPosition = -1;
			} else {
				expandPosition = position;
			}
			notifyDataSetChanged();
		}

	}

	private static class ViewHolder {
		TextView titleTextView; // ��Ŀ
		TextView briefTextView; // ���
		TextView timeTextView; // ʱ��
		TextView sourceTextView; // ������Դ
		TextView clickTextView; // �����
		NetworkImageView thumbnailImage; // ����ͼ
		ImageView morePraiseCollectImg;
		LinearLayout morePraiseCollectLayout;
		LinearLayout praiseLayout;
		LinearLayout collectLayout;
		TextView praiseText;
		TextView collectText;
	}

	// ��һҳ����
	public List<Article> updataArticleList(List<Article> newList) {
		List<Article> listTemp = new ArrayList<Article>();
		listTemp.addAll(newList);
		mArticleBriefList.addAll(listTemp);
		return mArticleBriefList;
	}

}
