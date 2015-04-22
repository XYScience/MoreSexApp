package com.science.moresexapp.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.sciecne.moresexapp.R;
import com.science.moresexapp.bean.Article;
import com.science.moresexapp.utils.AVService;
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
	private String mArticleContentTitle;
	private List<Article> mArticleBriefList = new ArrayList<Article>();
	// ��¼��ǰչ���������
	private int expandPosition = -1, praisePosition = -1, collectPosition = -1;
	ViewHolder viewHolder = null;
	private boolean praiseFlag = true, collectFlag = true;

	public PageListViewAdapter(Context context, List<Article> articleBriefList) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
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
	public View getView(final int position, View convertView, ViewGroup parent) {

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

		}

		AnimationSet aset = new AnimationSet(true);
		viewHolder.morePraiseCollectImg
				.setOnClickListener(new MorePraiseCollectImg(position));
		// ���������ǵ�ǰ�����չ��������������
		if (expandPosition == position) {

			mArticleContentTitle = mArticleBriefList.get(position).getTitle();

			viewHolder.morePraiseCollectLayout.setVisibility(View.VISIBLE);
			// viewHolder.morePraiseCollectImg
			// .setImageResource(R.drawable.more_select_up);
			RotateAnimation rAnimation = new RotateAnimation(-180, 0,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			// ���ö���ִ�й����õ�ʱ��,��λ����
			rAnimation.setDuration(700);
			// ����ִ����֮��Ч��������ִ����֮���״̬
			aset.setFillAfter(true);
			// ���������붯��������
			aset.addAnimation(rAnimation);
			// imageView��Ҫ��ת�Ŀؼ�������.
			viewHolder.morePraiseCollectImg.startAnimation(aset);
			notifyDataSetChanged();
		} else {
			viewHolder.morePraiseCollectLayout.setVisibility(View.GONE);
			// viewHolder.morePraiseCollectImg
			// .setImageResource(R.drawable.more_select_down);
			RotateAnimation rAnimation = new RotateAnimation(0, 180,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			// ���ö���ִ�й����õ�ʱ��,��λ����
			rAnimation.setDuration(700);
			// ����ִ����֮��Ч��������ִ����֮���״̬
			aset.setFillAfter(true);
			// ���������붯��������
			aset.addAnimation(rAnimation);
			// imageView��Ҫ��ת�Ŀؼ�������.
			viewHolder.morePraiseCollectImg.startAnimation(aset);
			notifyDataSetChanged();
		}

		// ����
		viewHolder.praiseLayout.setOnClickListener(new PraiseLayout(position));
		if (praisePosition == position) {
			Drawable drawable1 = mContext.getResources().getDrawable(
					R.drawable.praise_selected);
			drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),
					drawable1.getMinimumHeight());
			viewHolder.praiseText.setCompoundDrawables(drawable1, null, null,
					null);
			notifyDataSetChanged();
		} else {
			Drawable drawable1 = mContext.getResources().getDrawable(
					R.drawable.praise);
			drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),
					drawable1.getMinimumHeight());
			viewHolder.praiseText.setCompoundDrawables(drawable1, null, null,
					null);
			notifyDataSetChanged();
		}
		// �ղ�
		viewHolder.collectLayout
				.setOnClickListener(new CollectLayout(position));
		if (collectPosition == position) {

			Drawable drawable2 = mContext.getResources().getDrawable(
					R.drawable.collect_selected);
			drawable2.setBounds(0, 0, drawable2.getMinimumWidth(),
					drawable2.getMinimumHeight());
			viewHolder.collectText.setCompoundDrawables(drawable2, null, null,
					null);
			notifyDataSetChanged();
		} else {
			Drawable drawable2 = mContext.getResources().getDrawable(
					R.drawable.collect);
			drawable2.setBounds(0, 0, drawable2.getMinimumWidth(),
					drawable2.getMinimumHeight());
			viewHolder.collectText.setCompoundDrawables(drawable2, null, null,
					null);
			notifyDataSetChanged();
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

	class PraiseLayout implements OnClickListener {

		private int position;

		public PraiseLayout(int position) {
			super();
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// �����ǰ��Ϊչ����������Ϊ-1��Ŀ����Ϊ���������أ������ǰ��Ϊ���أ��򽫵�ǰλ�����ø�ȫ�ֱ���������չ������Ҳ���ǽ������м����ʵ�ֲ��ֵ�չ��������
			if (praisePosition == position) {
				praisePosition = -1;
			} else {
				praisePosition = position;
			}
			notifyDataSetChanged();

			if (praiseFlag) {
				SaveCallback saveCallback = new SaveCallback() {
					@Override
					public void done(AVException e) {
						if (e == null) {
							mHandler.obtainMessage(1).sendToTarget();
						} else {
							mHandler.obtainMessage(3).sendToTarget();
						}
					}
				};
				AVService.article(mArticleContentTitle, 1, 0, "", saveCallback);

				praiseFlag = false;
			} else {
				Toast.makeText(mContext, "��ȡ����", Toast.LENGTH_SHORT).show();
				praiseFlag = true;
			}
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(mContext, "����", Toast.LENGTH_LONG).show();
				break;
			case 2:
				Toast.makeText(mContext, "��ȡ����", Toast.LENGTH_LONG).show();
				break;
			case 3:
				Toast.makeText(mContext, "�ǳ���Ǹ���ύ����", Toast.LENGTH_LONG)
						.show();
				break;
			default:
				break;
			}
		}
	};

	class CollectLayout implements OnClickListener {

		private int position;

		public CollectLayout(int position) {
			super();
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// �����ǰ��Ϊչ����������Ϊ-1��Ŀ����Ϊ���������أ������ǰ��Ϊ���أ��򽫵�ǰλ�����ø�ȫ�ֱ���������չ������Ҳ���ǽ������м����ʵ�ֲ��ֵ�չ��������
			if (collectPosition == position) {
				collectPosition = -1;
			} else {
				collectPosition = position;
			}
			notifyDataSetChanged();

			if (collectFlag) {
				Toast.makeText(mContext, "���ղ�", Toast.LENGTH_SHORT).show();
				collectFlag = false;
			} else {
				Toast.makeText(mContext, "��ȡ�ղ�", Toast.LENGTH_SHORT).show();
				collectFlag = true;
			}
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
