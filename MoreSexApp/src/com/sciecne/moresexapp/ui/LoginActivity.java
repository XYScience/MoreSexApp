package com.sciecne.moresexapp.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
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
 * @description the user`s Activity, login
 * 
 * @author ÐÒÔËScience ³ÂÍÁŸö
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-4-10
 * 
 */

public class LoginActivity extends Activity {

	private EditText mUserName;
	private EditText mPassword;
	private Button mLoginButton;
	private Button mRegisterButton;
	private ImageView mQQImg;
	private ImageView mWeChatImg;
	private ImageView mSinaWeiBoImg;

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login_layout);

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
		mPassword = (EditText) findViewById(R.id.password);
		mLoginButton = (Button) findViewById(R.id.login);
		mRegisterButton = (Button) findViewById(R.id.register);
		mQQImg = (ImageView) findViewById(R.id.qq);
		mWeChatImg = (ImageView) findViewById(R.id.wechat);
		mSinaWeiBoImg = (ImageView) findViewById(R.id.sina_weibo);
	}

	public void addListener() {

		mLoginButton.setOnClickListener(new LoginListener());
		mRegisterButton.setOnClickListener(new RegisterListener());
		mQQImg.setOnClickListener(new QQLoginListener());
		mWeChatImg.setOnClickListener(new WeChatLoginListener());
		mSinaWeiBoImg.setOnClickListener(new SinaWeiBoLoginListener());
	}

	class LoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {

		}

	}

	class RegisterListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivity(intent);
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
			LoginActivity.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
