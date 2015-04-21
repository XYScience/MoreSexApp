package com.science.moresexapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sciecne.moresexapp.R;
import com.science.moresexapp.utils.AppConfig;

/**
 * The recommend fragment
 * 
 * @author ÐÒÔËScience¡¤³ÂÍÁŸö
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @date 2015-1-30
 */
public class RecommendFragment extends ModuleBaseFragment {

	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mView = inflater.inflate(R.layout.recommend_fragment_layout, container,
				false);

		initComponent(mView);
		addListener();
		initModule(getActivity().getString(R.string.recommend),
				AppConfig.GET_RECOMMEND_JSON, 1);
		if (!isNetworkConnected()) {
			initData(AppConfig.GET_RECOMMEND_JSON, 1);
		}

		return mView;
	}

}
