package com.sciecne.moresexapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sciecne.moresexapp.R;

/**
 * 类说明 我的评论界面
 * 
 * @author 幸运Science·陈土燊
 * @school University of South China
 * @Email chentushen.science@gmail.com && 274240671@qq.com
 * @version 1.0
 * @2015-4-13
 * 
 */

public class MyCommentFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.mycomment_layout, container,
				false);// 关联布局文件

		return rootView;
	}
}
