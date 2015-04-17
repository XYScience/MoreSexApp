package com.science.moresexapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.sciecne.moresexapp.R;
import com.science.moresexapp.MainActivity;
import com.science.moresexapp.guide.GuideActivity;

/**
 * @description 欢迎界面
 * 
 * @author 幸运Science 陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-4-17
 * 
 */

public class WelcomeActivity extends Activity {

	private ImageView mWelcomeImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences settingPreferences = getSharedPreferences(
				"WelcomeActivity", 0);
		boolean isFirstIn = settingPreferences.getBoolean("isFirstIn", true);
		if (isFirstIn) {
			settingPreferences.edit().putBoolean("isFirstIn", false).commit();
			Intent intent = new Intent(WelcomeActivity.this,
					GuideActivity.class);
			startActivity(intent);
			WelcomeActivity.this.finish();
		} else {
			setContentView(R.layout.welcome);

			mWelcomeImg = (ImageView) findViewById(R.id.welcome);
			AlphaAnimation aa = new AlphaAnimation(0f, 1f);
			aa.setDuration(3000);
			mWelcomeImg.startAnimation(aa);
			aa.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					Intent intent = new Intent(WelcomeActivity.this,
							MainActivity.class);
					startActivity(intent);
					WelcomeActivity.this.finish();
				}
			});
		}
	}

}
