package com.sciecne.moresexapp.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sciecne.moresexapp.R;

/**
 * @description the user`s Activity, register
 * 
 * @author ÐÒÔËScience ³ÂÍÁŸö
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-4-10
 * 
 */

public class RegisterActivity extends Activity {

	private EditText mUserName;
	private EditText mEmail;
	private EditText mPassword;
	private Button mRegisterButton;
	private ImageView mQQImg;
	private ImageView mWeChatImg;
	private ImageView mSinaWeiBoImg;

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.register_layout);

		initComponent();

		addListener();

		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// Í¸Ã÷×´Ì¬À¸
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// Í¸Ã÷µ¼º½À¸
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}

	}

	public void initComponent() {

		mUserName = (EditText) findViewById(R.id.username);
		mEmail = (EditText) findViewById(R.id.email);
		mPassword = (EditText) findViewById(R.id.password);
		mRegisterButton = (Button) findViewById(R.id.register);
		mQQImg = (ImageView) findViewById(R.id.qq);
		mWeChatImg = (ImageView) findViewById(R.id.wechat);
		mSinaWeiBoImg = (ImageView) findViewById(R.id.sina_weibo);
	}

	public void addListener() {

		mRegisterButton.setOnClickListener(new RegisterListener());
		mQQImg.setOnClickListener(new QQLoginListener());
		mWeChatImg.setOnClickListener(new WeChatLoginListener());
		mSinaWeiBoImg.setOnClickListener(new SinaWeiBoLoginListener());
	}

	class RegisterListener implements OnClickListener {

		@Override
		public void onClick(View v) {

		}

	}

	class QQLoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {

		}

	}

	class WeChatLoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {

		}

	}

	class SinaWeiBoLoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {

		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			RegisterActivity.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
