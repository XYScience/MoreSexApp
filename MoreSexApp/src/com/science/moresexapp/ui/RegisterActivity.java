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
import com.avos.avoscloud.SignUpCallback;
import com.sciecne.moresexapp.R;
import com.science.moresexapp.MainActivity;
import com.science.moresexapp.utils.AVService;

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
	private EditText mPasswordAgain;
	private Button mRegisterButton;
	private ImageView mQQImg;
	private ImageView mWeChatImg;
	private ImageView mSinaWeiBoImg;

	private ProgressDialog progressDialog;

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
		mPasswordAgain = (EditText) findViewById(R.id.password_again);
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
			if (mPassword.getText().toString()
					.equals(mPasswordAgain.getText().toString())) {
				if (!mPassword.getText().toString().isEmpty()) {
					if (!mUserName.getText().toString().isEmpty()) {
						if (!mEmail.getText().toString().isEmpty()) {
							progressDialogShow();
							register();
						} else {
							Toast.makeText(RegisterActivity.this,
									R.string.error_register_email_address_null,
									Toast.LENGTH_LONG).show();
						}
					} else {
						Toast.makeText(RegisterActivity.this,
								R.string.error_register_user_name_null,
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(RegisterActivity.this,
							R.string.error_register_password_null,
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(RegisterActivity.this,
						R.string.error_register_password_not_equals,
						Toast.LENGTH_LONG).show();
			}
		}

	}

	public void register() {
		SignUpCallback signUpCallback = new SignUpCallback() {
			public void done(AVException e) {
				progressDialogDismiss();
				if (e == null) {
					showRegisterSuccess();
					Intent mainIntent = new Intent(RegisterActivity.this,
							MainActivity.class);
					startActivity(mainIntent);
					RegisterActivity.this.finish();
				} else {
					switch (e.getCode()) {
					case 202:
						Toast.makeText(
								RegisterActivity.this,
								getString(R.string.error_register_user_name_repeat),
								Toast.LENGTH_LONG).show();
						break;
					case 203:
						Toast.makeText(
								RegisterActivity.this,
								getString(R.string.error_register_email_repeat),
								Toast.LENGTH_LONG).show();
						break;
					default:
						Toast.makeText(RegisterActivity.this,
								getString(R.string.network_not_connected),
								Toast.LENGTH_LONG).show();
						break;
					}
				}
			}
		};
		String username = mUserName.getText().toString();
		String password = mPassword.getText().toString();
		String email = mEmail.getText().toString();

		AVService.signUp(username, password, email, signUpCallback);
	}

	private void progressDialogDismiss() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	private void progressDialogShow() {
		progressDialog = ProgressDialog.show(
				RegisterActivity.this,
				RegisterActivity.this.getResources().getText(
						R.string.dialog_message_title), RegisterActivity.this
						.getResources().getText(R.string.dialog_text_wait),
				true, false);
	}

	private void showRegisterSuccess() {
		new AlertDialog.Builder(RegisterActivity.this)
				.setTitle(
						RegisterActivity.this.getResources().getString(
								R.string.dialog_message_title))
				.setMessage(
						RegisterActivity.this.getResources().getString(
								R.string.success_register_success))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
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
