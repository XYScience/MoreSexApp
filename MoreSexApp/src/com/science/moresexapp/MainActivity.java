package com.science.moresexapp;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sciecne.moresexapp.R;
import com.science.moresexapp.fragment.MenuFragment;
import com.science.moresexapp.fragment.RecommendFragment;
import com.science.moresexapp.utils.AppContext;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

/**
 * The main entrance
 * 
 * @author science·陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @date 2015-1-27
 */

public class MainActivity extends SlidingActivity {

	private Fragment mRecommendFragment;
	private SlidingMenu mSlidingMenu;
	private FragmentTransaction mFragmentTransaction;

	private AppContext appContext;// 全局Context

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 沉浸式状态栏设置
		initSystemBar();
		// 跟踪统计应用的打开情况
		AVAnalytics.trackAppOpened(getIntent());

		appContext = (AppContext) getApplication();
		// 网络连接判断
		if (!appContext.isNetworkConnected())
			Toast.makeText(this, R.string.network_not_connected,
					Toast.LENGTH_LONG).show();

		// set the behind menu view
		setBehindContentView(R.layout.layout_menu);
		mFragmentTransaction = getFragmentManager().beginTransaction();
		// add the left menu
		MenuFragment menuFragment = new MenuFragment();
		mFragmentTransaction.replace(R.id.layout_menu, menuFragment);
		mFragmentTransaction.commit();

		initSlidingMenu();

		// show recommend module Fragment
		showRecommendFragment();
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

	private void initSlidingMenu() {
		// customize the SlidingMenu
		mSlidingMenu = getSlidingMenu();
		// Sliding to the left
		mSlidingMenu.setMode(SlidingMenu.LEFT);
		// The width of sliding shadows
		mSlidingMenu.setShadowWidth(60);
		// Shadow effect
		mSlidingMenu.setShadowDrawable(R.drawable.shadow);
		// The remaining width of the main page displayed
		mSlidingMenu.setBehindOffset(160);
		// Set slide drag effect
		mSlidingMenu.setBehindScrollScale(0.5f);
		// Fade effect
		mSlidingMenu.setFadeDegree(0.5f);
		// Full-screen slide
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

	}

	/**
	 * recommend module fragment
	 */
	public void showRecommendFragment() {

		if (mRecommendFragment == null) {
			mRecommendFragment = new RecommendFragment();
		}
		if (!mRecommendFragment.isVisible()) {
			FragmentManager fragmentManager = this.getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content, mRecommendFragment).commit();
		}
		mSlidingMenu.showContent(true);
	}

	/**
	 * Capture the return key, if the currently displayed menu, just hide
	 */
	@Override
	public void onBackPressed() {
		if (mSlidingMenu.isMenuShowing()) {
			mSlidingMenu.showContent();
		} else {
			super.onBackPressed();
		}
	}
}
