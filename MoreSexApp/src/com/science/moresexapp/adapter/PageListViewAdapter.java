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

	private String mTimeString;
	private Integer mClick;
	private LayoutInflater mInflater;
	private Context mContext;
	private String mArticleContentTitle;
	private List<Article> mArticleBriefList = new ArrayList<Article>();
	// 记录当前展开项的索引
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
				mTimeString = "未知";
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
					+ article.getContent().replaceAll("文章导读", "")
							.replaceAll("<br/>", "\b\b\b\b\b\b"));// 替换<br/>和空格
			// .replaceAll("[\\s\\\u003cbr/\u003e]", " "));// 替换<br/>和空格
			viewHolder.timeTextView.setText("" + mTimeString);
			viewHolder.sourceTextView.setText("" + article.getSource());
			viewHolder.clickTextView.setText("" + mClick);
			// 设置未加载默认图片
			viewHolder.thumbnailImage
					.setDefaultImageResId(R.drawable.widget_loading);
			// 设置加载异常的图片
			// holder.imageView.setErrorImageResId(R.drawable.error);
			viewHolder.thumbnailImage.setImageUrl(article.getImgUrl(),
					VolleyTools.getInstance(mContext).getImageLoader());

		}

		AnimationSet aset = new AnimationSet(true);
		viewHolder.morePraiseCollectImg
				.setOnClickListener(new MorePraiseCollectImg(position));
		// 如果点击的是当前项，则将其展开，否则将其隐藏
		if (expandPosition == position) {

			mArticleContentTitle = mArticleBriefList.get(position).getTitle();

			viewHolder.morePraiseCollectLayout.setVisibility(View.VISIBLE);
			// viewHolder.morePraiseCollectImg
			// .setImageResource(R.drawable.more_select_up);
			RotateAnimation rAnimation = new RotateAnimation(-180, 0,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			// 设置动画执行过程用的时间,单位毫秒
			rAnimation.setDuration(700);
			// 动画执行完之后效果定格在执行完之后的状态
			aset.setFillAfter(true);
			// 将动画加入动画集合中
			aset.addAnimation(rAnimation);
			// imageView是要旋转的控件的引用.
			viewHolder.morePraiseCollectImg.startAnimation(aset);
			notifyDataSetChanged();
		} else {
			viewHolder.morePraiseCollectLayout.setVisibility(View.GONE);
			// viewHolder.morePraiseCollectImg
			// .setImageResource(R.drawable.more_select_down);
			RotateAnimation rAnimation = new RotateAnimation(0, 180,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			// 设置动画执行过程用的时间,单位毫秒
			rAnimation.setDuration(700);
			// 动画执行完之后效果定格在执行完之后的状态
			aset.setFillAfter(true);
			// 将动画加入动画集合中
			aset.addAnimation(rAnimation);
			// imageView是要旋转的控件的引用.
			viewHolder.morePraiseCollectImg.startAnimation(aset);
			notifyDataSetChanged();
		}

		// 点赞
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
		// 收藏
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
			// 如果当前项为展开，则将其置为-1，目的是为了让其隐藏，如果当前项为隐藏，则将当前位置设置给全局变量，让其展开，这也就是借助于中间变量实现布局的展开与隐藏
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
			// 如果当前项为展开，则将其置为-1，目的是为了让其隐藏，如果当前项为隐藏，则将当前位置设置给全局变量，让其展开，这也就是借助于中间变量实现布局的展开与隐藏
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
				Toast.makeText(mContext, "已取消赞", Toast.LENGTH_SHORT).show();
				praiseFlag = true;
			}
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(mContext, "已赞", Toast.LENGTH_LONG).show();
				break;
			case 2:
				Toast.makeText(mContext, "已取消赞", Toast.LENGTH_LONG).show();
				break;
			case 3:
				Toast.makeText(mContext, "非常抱歉，提交出错！", Toast.LENGTH_LONG)
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
			// 如果当前项为展开，则将其置为-1，目的是为了让其隐藏，如果当前项为隐藏，则将当前位置设置给全局变量，让其展开，这也就是借助于中间变量实现布局的展开与隐藏
			if (collectPosition == position) {
				collectPosition = -1;
			} else {
				collectPosition = position;
			}
			notifyDataSetChanged();

			if (collectFlag) {
				Toast.makeText(mContext, "已收藏", Toast.LENGTH_SHORT).show();
				collectFlag = false;
			} else {
				Toast.makeText(mContext, "已取收藏", Toast.LENGTH_SHORT).show();
				collectFlag = true;
			}
		}

	}

	private static class ViewHolder {
		TextView titleTextView; // 题目
		TextView briefTextView; // 简介
		TextView timeTextView; // 时间
		TextView sourceTextView; // 文章来源
		TextView clickTextView; // 点击量
		NetworkImageView thumbnailImage; // 缩略图
		ImageView morePraiseCollectImg;
		LinearLayout morePraiseCollectLayout;
		LinearLayout praiseLayout;
		LinearLayout collectLayout;
		TextView praiseText;
		TextView collectText;
	}

	// 下一页数据
	public List<Article> updataArticleList(List<Article> newList) {
		List<Article> listTemp = new ArrayList<Article>();
		listTemp.addAll(newList);
		mArticleBriefList.addAll(listTemp);
		return mArticleBriefList;
	}

}
