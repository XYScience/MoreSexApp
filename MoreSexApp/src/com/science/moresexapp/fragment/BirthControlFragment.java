package com.science.moresexapp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sciecne.moresexapp.R;
import com.science.moresexapp.utils.AppConfig;

/**
 * BirthControl module fragment
 * 
 * @author –“‘ÀScience°§≥¬Õ¡üˆ
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @date 2015-1-28
 */
@SuppressLint("ShowToast")
public class BirthControlFragment extends ModuleBaseFragment {

	private View mView;

	@SuppressLint({ "ResourceAsColor", "InlinedApi" })
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mView = inflater.inflate(R.layout.recommend_fragment_layout, container,
				false);

		initComponent(mView);
		addListener();
		initModule("±‹‘–", AppConfig.GET_BIRTHCONTROL_JSON.replace("{1}", "1"), 2);
		if (!isNetworkConnected()) {
			initData(AppConfig.GET_BIRTHCONTROL_JSON.replace("{1}", "1"), 1);
		}

		return mView;
	}

}
