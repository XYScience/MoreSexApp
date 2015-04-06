package com.sciecne.moresexapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sciecne.moresexapp.MainActivity;
import com.sciecne.moresexapp.R;

/**
 * Mentality module fragment
 * 
 * @author 幸运Science·陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @date 2015-1-28
 */
public class MentalityFragment extends Fragment {

	private ImageView mImageView;
	private TextView mTextModule;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.health_fragment_layout,
				container, false);

		mTextModule = (TextView) view.findViewById(R.id.text_module);
		mTextModule.setText("心理");
		mImageView = (ImageView) view.findViewById(R.id.img_menu);
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Automatically determine the switch
				((MainActivity) getActivity()).getSlidingMenu().toggle();
			}
		});
		return view;
	}
}
