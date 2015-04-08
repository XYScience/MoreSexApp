package com.sciecne.moresexapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @description 文章列表
 * 
 * @author 幸运Science 陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-4-7
 * 
 */

public class ArticleActivity extends Activity {

	private ImageView mImageBack;
	private TextView mTextArticleTitle;
	private TextView mTextArticleAuthor;
	private TextView mTextArticleTime;
	private ImageView mImageArticle;
	private WebView mWebViewArticleContent;
	private Intent mIntent;

	private String mTitle;
	private String mTime;
	private String mAuthor;
	private String mContent;
	private String mClick;
	private String mSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.article_content);

		initComponent();
		initData();
		addData();
	}

	private void initComponent() {
		mImageBack = (ImageView) findViewById(R.id.back);
		mTextArticleTitle = (TextView) findViewById(R.id.text_article_title);
		mTextArticleAuthor = (TextView) findViewById(R.id.text_article_author);
		mTextArticleTime = (TextView) findViewById(R.id.text_article_time);
		mImageArticle = (ImageView) findViewById(R.id.image_article);
		mWebViewArticleContent = (WebView) findViewById(R.id.webview_article_content);

		mWebViewArticleContent.setHorizontalScrollBarEnabled(false);// 设置水平滚动条，true表示允许使用
		WebSettings webSettings = mWebViewArticleContent.getSettings();
		webSettings.setDefaultTextEncodingName("UTF-8");
		webSettings.setCacheMode(1);
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		mWebViewArticleContent.loadDataWithBaseURL(null,
				"<center/>正在加载 ...<hr>", "text/html", "UTF-8", null);

		// 退出文章页面
		mImageBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initData() {
		mIntent = getIntent();

		mTitle = mIntent.getStringExtra("title");
		mTime = mIntent.getStringExtra("time");
		mAuthor = mIntent.getStringExtra("author");
		mContent = mIntent.getStringExtra("content");
		mClick = mIntent.getStringExtra("click");
		mSource = mIntent.getStringExtra("source");
	}

	private void addData() {

		mTextArticleTitle.setText(mTitle);
		mTextArticleAuthor.setText("作者：" + mAuthor + "    来源：" + mSource);
		mTextArticleTime.setText("发布时间：" + mTime);
	}
}