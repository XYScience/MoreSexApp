package com.science.moresexapp.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.sciecne.moresexapp.R;
import com.science.moresexapp.MainActivity;

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

	private ProgressDialog progressDialog;

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

		@SuppressWarnings("unchecked")
		@Override
		public void onClick(View v) {

			String username = mUserName.getText().toString();
			String password = mPassword.getText().toString();
			if (username.isEmpty()) {
				Toast.makeText(LoginActivity.this,
						R.string.error_register_user_name_null,
						Toast.LENGTH_LONG).show();
				return;
			}
			if (password.isEmpty()) {
				Toast.makeText(LoginActivity.this,
						R.string.error_register_password_null,
						Toast.LENGTH_LONG).show();
				return;
			}
			progressDialogShow();
			// µÇÂ½²éÑ¯
			AVUser.logInInBackground(username, password, new LogInCallback() {
				public void done(AVUser user, AVException e) {
					if (user != null) {
						progressDialogDismiss();
						Intent mainIntent = new Intent(LoginActivity.this,
								MainActivity.class);
						startActivity(mainIntent);
						LoginActivity.this.finish();
					} else {
						progressDialogDismiss();
						showLoginError();
					}
				}
			});
		}

	}

	private void progressDialogShow() {
		progressDialog = ProgressDialog.show(
				LoginActivity.this,
				LoginActivity.this.getResources().getText(
						R.string.dialog_message_title), LoginActivity.this
						.getResources().getText(R.string.dialog_text_wait),
				true, false);
	}

	private void progressDialogDismiss() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	private void showLoginError() {
		new AlertDialog.Builder(LoginActivity.this)
				.setTitle(
						LoginActivity.this.getResources().getString(
								R.string.dialog_error_title))
				.setMessage(
						LoginActivity.this.getResources().getString(
								R.string.error_login_error))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
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
