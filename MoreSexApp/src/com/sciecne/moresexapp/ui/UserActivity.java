package com.sciecne.moresexapp.ui;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sciecne.moresexapp.R;
import com.sciecne.moresexapp.fragment.MyCollectFragment;
import com.sciecne.moresexapp.fragment.MyCommentFragment;
import com.science.moresexapp.adapter.MyFragmentPagerAdapter;
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

public class UserActivity extends FragmentActivity {

	private TextView mTextModule;
	private ImageView mImgMenu;
	private ImageView mUserBackgroundImg;
	private TextView mMyCollectText;
	private TextView mMyCommentFragment;
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentList;
	private TextView mBarText;
	private int currIndex;// 当前页卡编号

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.user_layout);

		// 沉浸式状态栏设置
		initSystemBar();

		initComponent();

		InitTextBar();
		InitViewPager();

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
		mImgMenu = (ImageView) findViewById(R.id.back);
		mImgMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UserActivity.this.finish();
			}
		});
		mPager = (ViewPager) findViewById(R.id.viewpager);
		mMyCollectText = (TextView) findViewById(R.id.my_collect_text);
		mMyCommentFragment = (TextView) findViewById(R.id.my_comment_text);
		mMyCollectText.setOnClickListener(new SelectListener(0));
		mMyCommentFragment.setOnClickListener(new SelectListener(1));

		mUserBackgroundImg = (ImageView) findViewById(R.id.user_background_img);
		DampView view = (DampView) findViewById(R.id.dampview);
		view.setImageView(mUserBackgroundImg);

	}

	public class SelectListener implements View.OnClickListener {
		private int index = 0;

		public SelectListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	}

	/*
	 * 初始化图片的位移像素
	 */
	public void InitTextBar() {
		mBarText = (TextView) super.findViewById(R.id.cursor);
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		// 得到显示屏宽度
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		// 1/3屏幕宽度
		int tabLineLength = metrics.widthPixels / 4;
		LayoutParams lp = (LayoutParams) mBarText.getLayoutParams();
		lp.width = tabLineLength;
		mBarText.setLayoutParams(lp);

	}

	/*
	 * 初始化ViewPager
	 */
	public void InitViewPager() {
		fragmentList = new ArrayList<Fragment>();
		Fragment myCollectFragment = new MyCollectFragment();
		Fragment myCommentFragment = new MyCommentFragment();

		fragmentList.add(myCollectFragment);
		fragmentList.add(myCommentFragment);

		// 给ViewPager设置适配器
		mPager.setAdapter(new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentList));
		mPager.setCurrentItem(0);// 设置当前显示标签页为第一页
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());// 页面变化时的监听器
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// 取得该控件的实例
			LinearLayout.LayoutParams ll = (android.widget.LinearLayout.LayoutParams) mBarText
					.getLayoutParams();

			if (currIndex == arg0) {
				ll.leftMargin = (int) (currIndex * mBarText.getWidth() + arg1
						* mBarText.getWidth());
			} else if (currIndex > arg0) {
				ll.leftMargin = (int) (currIndex * mBarText.getWidth() - (1 - arg1)
						* mBarText.getWidth());
			}
			mBarText.setLayoutParams(ll);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			// currIndex = arg0;
			// int i = currIndex + 1;
			// Toast.makeText(UserActivity.this, "您选择了第" + i + "个页卡",
			// Toast.LENGTH_SHORT).show();
		}
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
