package com.science.moresexapp.ui;

import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sciecne.moresexapp.R;
import com.science.moresexapp.utils.AVService;

/**
 * @description 更改信息界面
 * 
 * @author 幸运Science 陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-4-14
 * 
 */

public class AlterActivity extends Activity {

	private TextView mTextModule;
	private ImageView mImageBack;

	private EditText mUsernameText;
	private EditText mSexText;
	private EditText mBirthText;
	private EditText mHomeTownText;
	private Button mAlter;

	private String userId;

	private ProgressDialog mProgressDialog;
	private AVUser currentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alter);

		// 沉浸式状态栏设置
		initSystemBar();
		initComponent();
		findCallback();
		addListener();
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

		mTextModule = (TextView) findViewById(R.id.text_module);
		mTextModule.setText("更改资料");
		mImageBack = (ImageView) findViewById(R.id.back);
		mUsernameText = (EditText) findViewById(R.id.username);
		mSexText = (EditText) findViewById(R.id.sex);
		mBirthText = (EditText) findViewById(R.id.birth);
		mHomeTownText = (EditText) findViewById(R.id.hometown);
		mAlter = (Button) findViewById(R.id.alter);
	}

	private void findCallback() {
		FindCallback<AVObject> findCallback = new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
					Message msg = new Message();
					msg.what = 3;
					msg.obj = avObjects;
					mHandler.sendMessage(msg);
				} else {
					Toast.makeText(AlterActivity.this, "请检查网络！",
							Toast.LENGTH_LONG).show();
				}
			}
		};
		currentUser = AVUser.getCurrentUser();
		if (currentUser != null) {
			userId = currentUser.getObjectId();
			mUsernameText.setText(currentUser.getUsername());
		}
		AVQuery<AVObject> query = new AVQuery<AVObject>("UserInformation");
		query.whereEqualTo("UserObjectId", userId);
		query.findInBackground(findCallback);
	}

	private void addListener() {

		mImageBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlterActivity.this.finish();
			}
		});

		mAlter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mUsernameText.getText().toString().isEmpty()) {

					if (!mSexText.getText().toString().isEmpty()) {

						if (!mBirthText.getText().toString().isEmpty()) {

							if (!mHomeTownText.getText().toString().isEmpty()) {

								progressDialogShow();
								alter();

							} else {
								Toast.makeText(AlterActivity.this,
										R.string.error_hometown_null,
										Toast.LENGTH_LONG).show();
							}

						} else {
							Toast.makeText(AlterActivity.this,
									R.string.error_birth_null,
									Toast.LENGTH_LONG).show();
						}

					} else {
						Toast.makeText(AlterActivity.this,
								R.string.error_sex_null, Toast.LENGTH_LONG)
								.show();
					}

				} else {
					Toast.makeText(AlterActivity.this,
							R.string.error_register_user_name_null,
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	private void progressDialogShow() {
		mProgressDialog = ProgressDialog.show(
				AlterActivity.this,
				AlterActivity.this.getResources().getText(
						R.string.dialog_message_title), AlterActivity.this
						.getResources().getText(R.string.dialog_text_wait),
				true, false);
	}

	private void alter() {

		SaveCallback saveCallback = new SaveCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					mHandler.obtainMessage(1).sendToTarget();
				} else {
					mHandler.obtainMessage(2).sendToTarget();
				}
			}
		};
		String username = mUsernameText.getText().toString();
		String sex = mSexText.getText().toString();
		String birth = mBirthText.getText().toString();
		String home = mHomeTownText.getText().toString();
		AVService.alterUserInformation(userId, username, sex, birth, home,
				saveCallback);
	}

	private Handler mHandler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				AlterActivity.this.finish();
				Toast.makeText(AlterActivity.this, "保存成功！", Toast.LENGTH_LONG)
						.show();
				break;
			case 2:
				Toast.makeText(AlterActivity.this, "非常抱歉，提交出错！",
						Toast.LENGTH_LONG).show();
				break;
			case 3:
				showOldInformation((List<AVObject>) msg.obj);
				break;
			default:
				break;
			}
		}
	};

	private void showOldInformation(List<AVObject> responseList) {
		if (responseList != null && responseList.size() != 0) {
			mSexText.setText(responseList.get(responseList.size() - 1)
					.getString("UserSex"));
			mBirthText.setText(responseList.get(responseList.size() - 1)
					.getString("UserBirth"));
			mHomeTownText.setText(responseList.get(responseList.size() - 1)
					.getString("UserHome"));
		}
	}
}
