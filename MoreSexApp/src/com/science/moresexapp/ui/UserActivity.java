package com.science.moresexapp.ui;

import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sciecne.moresexapp.R;
import com.science.moresexapp.adapter.ViewPagerFragmentAdapter;
import com.science.moresexapp.fragment.MyCollectFragment;
import com.science.moresexapp.fragment.MyCommentFragment;
import com.science.moresexapp.fragment.MySelfFragment;
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
	private ImageView mImageBack;
	private ImageView mUserBackgroundImg;
	private TextView mBgImgUsername;
	private TextView mPersonalStatement;
	private TextView mBarText;
	private int currIndex;// 当前页卡编号
	private TextView mMyCollectText;
	private TextView mMyCommentText;
	private TextView mMySelfText;
	private ViewPager viewPager;
	private ViewPagerFragmentAdapter adapter;
	private MyListener listener = new MyListener();
	private AVUser currentUser;
	private String userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_layout);

		// 沉浸式状态栏设置
		initSystemBar();
		initComponent();
		findCallback();
		initTextBar();
		initViewPager();

		// 模拟网络请求完成之后重置ViewPager高度
		new myAsyncTask().execute();
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
		mTextModule.setText("关于");
		mImageBack = (ImageView) findViewById(R.id.back);
		mImageBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UserActivity.this.finish();
			}
		});
		// 背景下拉变大
		mUserBackgroundImg = (ImageView) findViewById(R.id.user_background_img);
		DampView view = (DampView) findViewById(R.id.dampview);
		view.setImageView(mUserBackgroundImg);

		mPersonalStatement = (TextView) findViewById(R.id.personal_statement);
		// 头部用户名
		mBgImgUsername = (TextView) findViewById(R.id.username);

		// 导航条目
		mMySelfText = (TextView) findViewById(R.id.myself_text);
		mMyCollectText = (TextView) findViewById(R.id.my_collect_text);
		mMyCommentText = (TextView) findViewById(R.id.my_comment_text);
		mMySelfText.setTextColor(0xff5ddd57); // 默认第一个
		mMySelfText.setOnClickListener(new SelectListener(0));
		mMyCollectText.setOnClickListener(new SelectListener(1));
		mMyCommentText.setOnClickListener(new SelectListener(2));
	}

	private void findCallback() {

		currentUser = AVUser.getCurrentUser();
		if (currentUser != null) {
			userId = currentUser.getObjectId();
			mBgImgUsername.setText(currentUser.getUsername());
		}

		FindCallback<AVObject> findCallback = new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
					Message msg = new Message();
					msg.what = 1;
					msg.obj = avObjects;
					mHandler.sendMessage(msg);
				} else {
					Toast.makeText(UserActivity.this, "请检查网络！",
							Toast.LENGTH_LONG).show();
				}
			}
		};

		AVQuery<AVObject> query = new AVQuery<AVObject>("UserInformation");
		query.whereEqualTo("userObjectId", userId);
		query.findInBackground(findCallback);
	}

	private Handler mHandler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				List<AVObject> responseList = (List<AVObject>) msg.obj;
				if (responseList != null && responseList.size() != 0) {
					mPersonalStatement.setText(responseList.get(
							responseList.size() - 1).getString(
							"personalStatement"));
				}
				break;
			}
		}
	};

	public class SelectListener implements View.OnClickListener {
		private int index = 0;

		public SelectListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			viewPager.setCurrentItem(index);
		}
	}

	/*
	 * 初始化图片的位移像素
	 */
	public void initTextBar() {
		mBarText = (TextView) super.findViewById(R.id.cursor);
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		// 得到显示屏宽度
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		// 1/2屏幕宽度
		int tabLineLength = metrics.widthPixels / 3;
		LayoutParams lp = (LayoutParams) mBarText.getLayoutParams();
		lp.width = tabLineLength;
		mBarText.setLayoutParams(lp);
		mBarText.setGravity(Gravity.CENTER_HORIZONTAL);
	}

	/*
	 * 初始化ViewPager
	 */
	public void initViewPager() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());
		adapter.addFragment(new MySelfFragment());
		adapter.addFragment(new MyCollectFragment());
		adapter.addFragment(new MyCommentFragment());
		viewPager.setOffscreenPageLimit(3);
		viewPager.setOnPageChangeListener(listener);
		viewPager.setAdapter(adapter);
	}

	/**
	 * 重新设置viewPager高度
	 * 
	 * @param position
	 */
	public void resetViewPagerHeight(int position) {
		View child = viewPager.getChildAt(position);
		if (child != null) {
			child.measure(0, 0);
			int h = child.getMeasuredHeight();
			LinearLayout.LayoutParams params = (LayoutParams) viewPager
					.getLayoutParams();
			params.height = h + 50;
			viewPager.setLayoutParams(params);
		}
	}

	public class MyListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			// 取得该控件的实例
			LinearLayout.LayoutParams ll = (android.widget.LinearLayout.LayoutParams) mBarText
					.getLayoutParams();

			if (currIndex == position) {
				ll.leftMargin = (int) (currIndex * mBarText.getWidth() + positionOffset
						* mBarText.getWidth());
			} else if (currIndex > position) {
				ll.leftMargin = (int) (currIndex * mBarText.getWidth() - (1 - positionOffset)
						* mBarText.getWidth());
			}
			mBarText.setLayoutParams(ll);
		}

		@Override
		public void onPageSelected(int position) {

			currIndex = position;
			// 页面切换后重置ViewPager高度
			resetViewPagerHeight(position);
			switch (position) {
			case 0:
				mMySelfText.setTextColor(0xff5ddd57);
				mMyCollectText.setTextColor(0xff000000);
				mMyCommentText.setTextColor(0xff000000);
				break;
			case 1:
				mMySelfText.setTextColor(0xff000000);
				mMyCollectText.setTextColor(0xff5ddd57);
				mMyCommentText.setTextColor(0xff000000);
				break;
			case 2:
				mMySelfText.setTextColor(0xff000000);
				mMyCollectText.setTextColor(0xff000000);
				mMyCommentText.setTextColor(0xff5ddd57);
				break;
			}
		}
	}

	public class myAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			resetViewPagerHeight(0);
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				mPersonalStatement.setText(data
						.getStringExtra("personalStatement"));
				String username = data.getStringExtra("username");
				String sex = data.getStringExtra("sex");
				String birth = data.getStringExtra("birth");
				String home = data.getStringExtra("home");
				MySelfFragment.sendDataFragment(username, sex, birth, home);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
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
