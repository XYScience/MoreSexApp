package com.science.moresexapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.sciecne.moresexapp.R;
import com.science.moresexapp.MainActivity;
import com.science.moresexapp.ui.FeedBackActivity;
import com.science.moresexapp.ui.LoginActivity;
import com.science.moresexapp.ui.UserActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * The menu fragment
 * 
 * @author science·陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @date 2015-1-27
 */

public class MenuFragment extends Fragment implements OnItemClickListener {

	private static final int RECOMMEND = 0;
	private static final int SKILL = 1;
	private static final int HEALTH = 2;
	private static final int PHYSIOLOGY = 3;
	private static final int MENTALITY = 4;
	private static final int BIRTHCONTROL = 5;

	private View mRootView; // 缓存Fragment view
	private FragmentManager mFragmentManager;
	private MainActivity mActivity;

	private ListView mListView;
	private TextView mFeedback;
	private CircleImageView mUserCircleImage;
	private TextView mUserName;
	private MenuAdapter mMenuAdapter;
	private Fragment mRecommendFragment, mSkillFragment, mHealthFragment,
			mPhysiologyFragment, mMentalityFragment, mBirthControlFragment;
	private String[] mMenu = { "推荐", "生活", "健康", "生理", "心理", "避孕" };
	private int[] mIcMenu = { R.drawable.recommand, R.drawable.ic_skill,
			R.drawable.ic_health, R.drawable.ic_physiology,
			R.drawable.ic_mentality, R.drawable.ic_birthcontrol };
	private ViewHolder holder = null;
	// The flag of menu`s click;
	private int mClickFlag = -1;
	private boolean isClick = true;

	private AVUser mCurrentUser;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mRootView == null) {
			mRootView = inflater.inflate(R.layout.frame_menu, container, false);
			// mRootView = inflater.inflate(R.layout.frame_menu, null);
		}

		initView();
		initListener();

		mMenuAdapter = new MenuAdapter(getActivity());
		mListView.setAdapter(mMenuAdapter);
		mFragmentManager = this.getFragmentManager();

		OnTabSelected(RECOMMEND);

		return mRootView;
	}

	private void initView() {
		mListView = (ListView) mRootView.findViewById(R.id.menu_listview);
		mFeedback = (TextView) mRootView.findViewById(R.id.feedback);
		mUserCircleImage = (CircleImageView) mRootView
				.findViewById(R.id.user_flexiImage);
		mUserName = (TextView) mRootView.findViewById(R.id.user_name);
		// 用户圆形头像
		// mUserFlexiImage.setShape(FlexiImageView.SHAPE_CIRCLE)
		// .setShadow(true, 50.0f, 0.0f, 10.0f, Color.BLACK).draw();

		// 得到用户名称
		mCurrentUser = AVUser.getCurrentUser();
		if (mCurrentUser != null) {
			mUserName.setText(mCurrentUser.getUsername());
		} else {
			mUserName.setText(R.string.click_login);
		}
	}

	private void initListener() {

		mListView.setOnItemClickListener(this);
		mFeedback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showFeedBackFragment();
			}
		});

		mUserCircleImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showUserFragment();
			}
		});
		mUserName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showUserFragment();
			}
		});
	}

	private class MenuAdapter extends BaseAdapter {

		private LayoutInflater mInflater = null;

		private MenuAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mMenu.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.list_menu, null);
				holder.itemImage = (ImageView) convertView
						.findViewById(R.id.ic_item);
				holder.textMenu = (TextView) convertView
						.findViewById(R.id.text_menu);
				holder.menuItem = (RelativeLayout) convertView
						.findViewById(R.id.menu_item);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.itemImage.setImageResource(mIcMenu[position]);
			holder.textMenu.setText(mMenu[position]);

			// 判断是否被点击
			if (mClickFlag == position) {
				holder.textMenu.setTextColor(0xff5cdc57);
				// java代码实现drawableLeft属性
				// holder.textMenu.setCompoundDrawablesWithIntrinsicBounds(
				// getResources().getDrawable(R.drawable.start_select),
				// null, null, null);
			} else {
				holder.textMenu.setTextColor(0xffffffff);
			}
			if (position == 0 && isClick) {
				holder.textMenu.setTextColor(0xff5cdc57);
			}
			return convertView;
		}
	}

	class ViewHolder {
		public ImageView itemImage;
		public TextView textMenu;
		public RelativeLayout menuItem;
	}

	@Override
	public void onAttach(Activity activity) {
		mActivity = (MainActivity) activity;
		super.onAttach(activity);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		isClick = false;
		mClickFlag = arg2;
		// Notify the adapter to update the data
		mMenuAdapter.notifyDataSetChanged();

		switch (arg2) {
		case 0:
			OnTabSelected(RECOMMEND);
			break;
		case 1:
			OnTabSelected(SKILL);
			break;
		case 2:
			OnTabSelected(HEALTH);
			break;
		case 3:
			OnTabSelected(PHYSIOLOGY);
			break;
		case 4:
			OnTabSelected(MENTALITY);
			break;
		case 5:
			OnTabSelected(BIRTHCONTROL);
			break;

		default:
			break;
		}

		mActivity.getSlidingMenu().toggle();

	}

	// 选中导航中对应的tab选项
	private void OnTabSelected(int index) {
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		hideFragments(transaction);
		switch (index) {
		case RECOMMEND: // 推荐页面
			if (null == mRecommendFragment) {
				mRecommendFragment = new RecommendFragment();
				transaction.add(R.id.content, mRecommendFragment);
			} else {
				transaction.show(mRecommendFragment);
			}
			break;
		case SKILL: // 技巧界面
			if (null == mSkillFragment) {
				mSkillFragment = new SkillFragment();
				transaction.add(R.id.content, mSkillFragment);
			} else {
				transaction.show(mSkillFragment);
			}
			break;
		case HEALTH:// 健康界面
			if (null == mHealthFragment) {
				mHealthFragment = new HealthFragment();
				transaction.add(R.id.content, mHealthFragment);
			} else {
				transaction.show(mHealthFragment);
			}
			break;
		case PHYSIOLOGY:// 生理界面
			if (null == mPhysiologyFragment) {
				mPhysiologyFragment = new PhysiologyFragment();
				transaction.add(R.id.content, mPhysiologyFragment);
			} else {
				transaction.show(mPhysiologyFragment);
			}
			break;
		case MENTALITY:// 心里界面
			if (null == mMentalityFragment) {
				mMentalityFragment = new MentalityFragment();
				transaction.add(R.id.content, mMentalityFragment);
			} else {
				transaction.show(mMentalityFragment);
			}
			break;
		case BIRTHCONTROL:// 避孕界面
			if (null == mBirthControlFragment) {
				mBirthControlFragment = new BirthControlFragment();
				transaction.add(R.id.content, mBirthControlFragment);
			} else {
				transaction.show(mBirthControlFragment);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * 将所有fragment都置为隐藏状态
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (mRecommendFragment != null) {
			transaction.hide(mRecommendFragment);
		}
		if (mSkillFragment != null) {
			transaction.hide(mSkillFragment);
		}
		if (mHealthFragment != null) {
			transaction.hide(mHealthFragment);
		}
		if (mPhysiologyFragment != null) {
			transaction.hide(mPhysiologyFragment);
		}
		if (mMentalityFragment != null) {
			transaction.hide(mMentalityFragment);
		}
		if (mBirthControlFragment != null) {
			transaction.hide(mBirthControlFragment);
		}
	}

	public void showUserFragment() {
		if (mCurrentUser == null) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent(getActivity(), UserActivity.class);
			startActivity(intent);
		}
	}

	/**
	 * FeedBack module fragment
	 */
	public void showFeedBackFragment() {

		Intent intent = new Intent(getActivity(), FeedBackActivity.class);
		startActivity(intent);
	}
}
