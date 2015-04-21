package com.science.moresexapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sciecne.moresexapp.R;
import com.science.moresexapp.utils.AppConfig;

/**
 * health module fragment
 * 
 * @author ÐÒÔËScience¡¤³ÂÍÁŸö
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @date 2015-1-28
 */
public class HealthFragment extends ModuleBaseFragment {

	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.recommend_fragment_layout, container,
				false);

		initComponent(mView);
		addListener();
		initModule("½¡¿µ", AppConfig.GET_HEALTH_JSON.replace("{1}", "1"), 3);
		if (!isNetworkConnected()) {
			initData(AppConfig.GET_HEALTH_JSON.replace("{1}", "1"), 1);
		}

		return mView;
	}

}
