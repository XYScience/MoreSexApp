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
 * ��˵�� �û�����
 * 
 * @author ����Science��������
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
	private int currIndex;// ��ǰҳ�����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.user_layout);

		// ����ʽ״̬������
		initSystemBar();

		initComponent();

		InitTextBar();
		InitViewPager();

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

	public void initComponent() {

		mTextModule = (TextView) findViewById(R.id.text_module);
		mTextModule.setText("��������");
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
	 * ��ʼ��ͼƬ��λ������
	 */
	public void InitTextBar() {
		mBarText = (TextView) super.findViewById(R.id.cursor);
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		// �õ���ʾ�����
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		// 1/3��Ļ���
		int tabLineLength = metrics.widthPixels / 4;
		LayoutParams lp = (LayoutParams) mBarText.getLayoutParams();
		lp.width = tabLineLength;
		mBarText.setLayoutParams(lp);

	}

	/*
	 * ��ʼ��ViewPager
	 */
	public void InitViewPager() {
		fragmentList = new ArrayList<Fragment>();
		Fragment myCollectFragment = new MyCollectFragment();
		Fragment myCommentFragment = new MyCommentFragment();

		fragmentList.add(myCollectFragment);
		fragmentList.add(myCommentFragment);

		// ��ViewPager����������
		mPager.setAdapter(new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentList));
		mPager.setCurrentItem(0);// ���õ�ǰ��ʾ��ǩҳΪ��һҳ
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());// ҳ��仯ʱ�ļ�����
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// ȡ�øÿؼ���ʵ��
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
			// Toast.makeText(UserActivity.this, "��ѡ���˵�" + i + "��ҳ��",
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
