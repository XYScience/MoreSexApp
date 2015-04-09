package com.sciecne.moresexapp.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
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

import com.sciecne.moresexapp.MainActivity;
import com.sciecne.moresexapp.R;

/**
 * The menu fragment
 * 
 * @author science·陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @date 2015-1-27
 */

public class MenuFragment extends Fragment implements OnItemClickListener {

	private ListView mListView;
	private TextView mFeedback;
	private MenuAdapter mMenuAdapter;
	private Fragment mRecommendFragment, mSkillFragment, mHealthFragment,
			mPhysiologyFragment, mMentalityFragment, mBirthControlFragment,
			FeedBackFragment;
	private FragmentManager mFragmentManager;
	private String[] mMenu = { "推荐", "技巧", "健康", "生理", "心理", "避孕" };
	private int[] mIcMenu = { R.drawable.recommand, R.drawable.ic_skill,
			R.drawable.ic_health, R.drawable.ic_physiology,
			R.drawable.ic_mentality, R.drawable.ic_birthcontrol };
	private ViewHolder holder = null;
	// The flag of menu`s click;
	private int mClickFlag = -1;
	private boolean isClick = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frame_menu, container, false);

		mListView = (ListView) view.findViewById(R.id.menu_listview);
		mFeedback = (TextView) view.findViewById(R.id.feedback);

		mListView.setOnItemClickListener(this);
		mFeedback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showFeedBackFragment();
			}
		});

		mMenuAdapter = new MenuAdapter(getActivity());
		mListView.setAdapter(mMenuAdapter);

		mFragmentManager = this.getFragmentManager();

		return view;
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		isClick = false;
		mClickFlag = arg2;
		// Notify the adapter to update the data
		mMenuAdapter.notifyDataSetChanged();

		switch (arg2) {
		case 0:
			showRecommendFragment();
			break;
		case 1:
			showSkillFragment();
			break;
		case 2:
			showHealthFragment();
			break;
		case 3:
			showPhysiologyFragment();
			break;
		case 4:
			showMentalityFragment();
			break;
		case 5:
			showBirthControlFragment();
			break;

		default:
			break;
		}

	}

	/**
	 * Recommend module fragment
	 */
	public void showRecommendFragment() {

		if (mRecommendFragment == null) {
			mRecommendFragment = new RecommendFragment();
		}
		if (!mRecommendFragment.isVisible()) {
			mFragmentManager.beginTransaction()
					.replace(R.id.content, mRecommendFragment).commit();
		}
		((MainActivity) getActivity()).getSlidingMenu().toggle();
	}

	/**
	 * skill module fragment
	 */
	public void showSkillFragment() {

		if (mSkillFragment == null) {
			mSkillFragment = new SkillFragment();
		}
		if (!mSkillFragment.isVisible()) {
			mFragmentManager.beginTransaction()
					.replace(R.id.content, mSkillFragment).commit();
		}
		((MainActivity) getActivity()).getSlidingMenu().toggle();
	}

	/**
	 * health module fragment
	 */
	public void showHealthFragment() {

		if (mHealthFragment == null) {
			mHealthFragment = new HealthFragment();
		}
		if (!mHealthFragment.isVisible()) {
			mFragmentManager.beginTransaction()
					.replace(R.id.content, mHealthFragment).commit();
		}
		((MainActivity) getActivity()).getSlidingMenu().toggle();
	}

	/**
	 * Physiology module fragment
	 */
	public void showPhysiologyFragment() {

		if (mPhysiologyFragment == null) {
			mPhysiologyFragment = new PhysiologyFragment();
		}
		if (!mPhysiologyFragment.isVisible()) {
			mFragmentManager.beginTransaction()
					.replace(R.id.content, mPhysiologyFragment).commit();
		}
		((MainActivity) getActivity()).getSlidingMenu().toggle();
	}

	/**
	 * Mentality module fragment
	 */
	public void showMentalityFragment() {

		if (mMentalityFragment == null) {
			mMentalityFragment = new MentalityFragment();
		}
		if (!mMentalityFragment.isVisible()) {
			mFragmentManager.beginTransaction()
					.replace(R.id.content, mMentalityFragment).commit();
		}
		((MainActivity) getActivity()).getSlidingMenu().toggle();
	}

	/**
	 * BirthControl module fragment
	 */
	public void showBirthControlFragment() {

		if (mBirthControlFragment == null) {
			mBirthControlFragment = new BirthControlFragment();
		}
		if (!mBirthControlFragment.isVisible()) {
			mFragmentManager.beginTransaction()
					.replace(R.id.content, mBirthControlFragment).commit();
		}
		((MainActivity) getActivity()).getSlidingMenu().toggle();
	}

	/**
	 * FeedBack module fragment
	 */
	public void showFeedBackFragment() {

		if (FeedBackFragment == null) {
			FeedBackFragment = new FeedBackFragment();
		}
		if (!FeedBackFragment.isVisible()) {
			mFragmentManager.beginTransaction()
					.replace(R.id.content, FeedBackFragment).commit();
		}
		((MainActivity) getActivity()).getSlidingMenu().toggle();
	}

}
