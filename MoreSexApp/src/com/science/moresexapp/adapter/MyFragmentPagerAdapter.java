package com.science.moresexapp.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 类说明
 *
 * @author 幸运Science·陈土燊
 * @school University of South China
 * @Email chentushen.science@gmail.com && 274240671@qq.com
 * @version 1.0
 * @2015-4-13
 *
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

	ArrayList<Fragment> list;

	public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
		super(fm);
		this.list = list;

	}

	    @Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Fragment getItem(int arg0) {
		return list.get(arg0);
	}

}
