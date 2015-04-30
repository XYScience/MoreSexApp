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
 * @description ��������
 * 
 * @author ����Science ������
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
		// ����ʽ״̬������
		initSystemBar();
		initComponent();
		initData();
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void initSystemBar() {
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// ͸��״̬��
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// ͸��������
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		// ����״̬���Ĺ���ʵ��
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// ����״̬������
		tintManager.setStatusBarTintEnabled(true);
		// �����������
		tintManager.setNavigationBarTintEnabled(true);
		// ����һ����ɫ��ϵͳ��
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

		mWebViewArticleContent.setHorizontalScrollBarEnabled(false);// ����ˮƽ��������true��ʾ����ʹ��
		WebSettings webSettings = mWebViewArticleContent.getSettings();
		webSettings.setDefaultTextEncodingName("UTF-8");
		webSettings.setCacheMode(1);
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

		// �˳�����ҳ��
		mImageBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ArticleActivity.this.finish();
			}
		});

		// ������ж��������Ի������е�HTTP����Ȼ����һ�����㷨�����ط�����Щ����
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
		mTextArticleAuthor.setText("���ߣ�" + "��վ����" + "    ��Դ��" + mSource);
		mTextArticleTime.setText("����ʱ�䣺" + mTime);
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

		mLoadView.setVisibility(View.GONE);// ȥ������
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
