package com.sciecne.moresexapp.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sciecne.moresexapp.R;
import com.science.moresexapp.widget.DampView;

/**
 * 类说明 用户资料
 * 
 * @author 幸运Science·陈土燊
 * @school University of South China
 * @Email chentushen.science@gmail.com && 274240671@qq.com
 * @version 1.0
 * @2015-4-11
 * 
 */

public class UserActivity extends Activity {

	private TextView mTextModule;
	private ImageView mUserBackgroundImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.user_layout);

		// 沉浸式状态栏设置
		initSystemBar();

		initComponent();

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

	public void initComponent() {

		mTextModule = (TextView) findViewById(R.id.text_module);
		mTextModule.setText("个人资料");

		mUserBackgroundImg = (ImageView) findViewById(R.id.user_background_img);
		DampView view = (DampView) findViewById(R.id.dampview);
		view.setImageView(mUserBackgroundImg);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			UserActivity.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
