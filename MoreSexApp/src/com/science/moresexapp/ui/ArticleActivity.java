package com.science.moresexapp.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sciecne.moresexapp.R;
import com.science.moresexapp.utils.AppConfig;
import com.science.moresexapp.utils.VolleyTools;

/**
 * @description 文章内容
 * 
 * @author 幸运Science 陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-4-7
 * 
 */

public class ArticleActivity extends Activity {

	private ImageView mImageBack;
	private FrameLayout mLoadView;
	private TextView mTextArticleTitle;
	private TextView mTextArticleAuthor;
	private TextView mTextArticleTime;
	private NetworkImageView mImageArticle;
	private WebView mWebViewArticleContent;
	private Intent mIntent;

	private int mId;
	private String mTitle;
	private String mTime;
	private String mImgUrl;
	private String mSource;

	private String mContentPath;
	private RequestQueue mRequestQueue;
	private String mContentString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.article_content);
		// 沉浸式状态栏设置
		initSystemBar();
		initComponent();
		initData();
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void initSystemBar() {
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		// 创建状态栏的管理实例
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// 激活状态栏设置
		tintManager.setStatusBarTintEnabled(true);
		// 激活导航栏设置
		tintManager.setNavigationBarTintEnabled(true);
		// 设置一个颜色给系统栏
		tintManager.setTintColor(Color.parseColor("#5ddd57"));
	}

	private void initComponent() {
		mImageBack = (ImageView) findViewById(R.id.back);
		mLoadView = (FrameLayout) findViewById(R.id.loadView);
		mTextArticleTitle = (TextView) findViewById(R.id.text_article_title);
		mTextArticleAuthor = (TextView) findViewById(R.id.text_article_author);
		mTextArticleTime = (TextView) findViewById(R.id.text_article_time);
		mImageArticle = (NetworkImageView) findViewById(R.id.image_article);
		mWebViewArticleContent = (WebView) findViewById(R.id.webview_article_content);

		mWebViewArticleContent.setHorizontalScrollBarEnabled(false);// 设置水平滚动条，true表示允许使用
		WebSettings webSettings = mWebViewArticleContent.getSettings();
		webSettings.setDefaultTextEncodingName("UTF-8");
		webSettings.setCacheMode(1);
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

		// 退出文章页面
		mImageBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ArticleActivity.this.finish();
			}
		});

		// 请求队列对象，它可以缓存所有的HTTP请求，然后按照一定的算法并发地发出这些请求
		mRequestQueue = Volley.newRequestQueue(this);
	}

	private void initData() {
		mIntent = getIntent();
		mId = mIntent.getIntExtra("id", 22);
		mTitle = mIntent.getStringExtra("title");
		mTime = mIntent.getStringExtra("time");
		mImgUrl = mIntent.getStringExtra("imgurl");
		mSource = mIntent.getStringExtra("source");
		// mContent.replaceAll(" ", "\r\n");
		getContent();
	}

	private void getContent() {
		mTextArticleTitle.setText(mTitle);
		mTextArticleAuthor.setText("作者：" + "本站整理" + "    来源：" + mSource);
		mTextArticleTime.setText("发布时间：" + mTime);
		mImageArticle.setImageUrl(mImgUrl, VolleyTools.getInstance(this)
				.getImageLoader());

		mContentPath = AppConfig.GET_Content_JSON;
		mContentPath = mContentPath.replace("{ID}", "" + mId);
		getJsonData(mContentPath);

	}

	private void getJsonData(String contentPath) {
		StringRequest mStringRequest = new StringRequest(contentPath,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String string) {
						mContentString = string;
						new Thread() {
							public void run() {
								Message msg = new Message();
								try {
									if (mContentString.length() > 0) {
										msg.what = 1;
									} else {
										msg.what = -1;
									}
								} catch (Exception e) {
								}
								mHandler.sendMessage(msg);
							}
						}.start();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.e("onError", arg0.getMessage(), arg0);
					}
				});

		mRequestQueue.add(mStringRequest);

	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				addData();
			}
		}
	};

	private void addData() {

		mLoadView.setVisibility(View.GONE);// 去除加载
		mWebViewArticleContent.loadDataWithBaseURL(null, mContentString,
				"text/html", "UTF-8", null);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			ArticleActivity.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
