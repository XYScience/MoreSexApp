package com.science.moresexapp.guide;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sciecne.moresexapp.R;
import com.science.moresexapp.MainActivity;

/**
 * @description 引导界面
 * 
 * @author 幸运Science 陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-4-16
 * 
 */

public class GuideActivity extends Activity {

	private ImageView iv_man;
	private ParallaxContainer parallaxContainer;
	private Button mButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide);

		// 沉浸式状态栏设置
		initSystemBar();

		// SharedPreferences settingPreferences = getSharedPreferences(
		// "WelcomeActivity", 0);
		// settingPreferences.edit().putBoolean("isFirstIn", false).commit();

		/**
		 * 动画支持11以上sdk,11以下默认不显示动画
		 * 若需要支持11以下动画,也可导入https://github.com/JakeWharton/NineOldAndroids
		 */
		if (android.os.Build.VERSION.SDK_INT > 10) {
			iv_man = (ImageView) findViewById(R.id.iv_show);
			parallaxContainer = (ParallaxContainer) findViewById(R.id.parallax_container);

			if (parallaxContainer != null) {
				parallaxContainer.setImage(iv_man);
				parallaxContainer.setLooping(false);

				iv_man.setVisibility(View.VISIBLE);
				parallaxContainer.setupChildren(getLayoutInflater(),
						R.layout.view_intro_1, R.layout.view_intro_2,
						R.layout.view_start);
			}
		} else {
			setContentView(R.layout.view_start);
		}

		mButton = (Button) findViewById(R.id.start_app);
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GuideActivity.this,
						MainActivity.class);
				startActivity(intent);
				GuideActivity.this.finish();
			}
		});

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
		tintManager.setTintColor(Color.parseColor("#ffffff"));
	}
}
