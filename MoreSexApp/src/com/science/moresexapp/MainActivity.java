package com.science.moresexapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sciecne.moresexapp.R;
import com.science.moresexapp.fragment.MenuFragment;
import com.science.moresexapp.fragment.RecommendFragment;
import com.science.moresexapp.utils.AppContext;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * The main entrance
 * 
 * @author science��������
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @date 2015-1-27
 */

public class MainActivity extends SlidingFragmentActivity {

	private SlidingMenu mSlidingMenu;
	private RecommendFragment mRecommendFragment;
	private MenuFragment mMenuFragment;

	private AppContext appContext;// ȫ��Context
	// ����һ������������ʶ�Ƿ��˳�
	private static boolean isExit = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ����ͳ��Ӧ�õĴ����
		AVAnalytics.trackAppOpened(getIntent());

		appContext = (AppContext) getApplication();
		// ���������ж�
		if (!appContext.isNetworkConnected())
			Toast.makeText(this, R.string.network_not_connected,
					Toast.LENGTH_LONG).show();

		// ����ʽ״̬������
		initSystemBar();
		initSlidingMenu();
		initListener();
		// showRecommendFragment();
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void initSystemBar() {
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// ͸��״̬��
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// ͸��������
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		// ����״̬���Ĺ���ʵ��
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// ����״̬������
		tintManager.setStatusBarTintEnabled(true);
		// �����������
		tintManager.setNavigationBarTintEnabled(true);
		// ����һ����ɫ��ϵͳ��
		tintManager.setTintColor(Color.parseColor("#5ddd57"));
	}

	private void initSlidingMenu() {

		mSlidingMenu = getSlidingMenu();

		// set the behind menu view
		setBehindContentView(R.layout.layout_menu);
		// add the left menu
		mMenuFragment = new MenuFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.layout_menu, mMenuFragment).commit();

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

	public void initListener() {

		// �����򿪼����¼�
		mSlidingMenu.setOnOpenListener(new OnOpenListener() {
			@Override
			public void onOpen() {
			}
		});
		// �����رռ����¼�
		mSlidingMenu.setOnClosedListener(new OnClosedListener() {

			@Override
			public void onClosed() {
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
					Toast.LENGTH_SHORT).show();
			// ����handler�ӳٷ��͸���״̬��Ϣ
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			finish();
			// ɱ����Ӧ�ý���
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}
	}

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};

	// /**
	// * recommend module fragment
	// */
	// public void showRecommendFragment() {
	//
	// if (mRecommendFragment == null) {
	// mRecommendFragment = new RecommendFragment();
	// }
	// if (!mRecommendFragment.isVisible()) {
	// FragmentManager fragmentManager = this.getSupportFragmentManager();
	// fragmentManager.beginTransaction()
	// .replace(R.id.content, mRecommendFragment).commit();
	// }
	// mSlidingMenu.showContent(true);
	// }

}
