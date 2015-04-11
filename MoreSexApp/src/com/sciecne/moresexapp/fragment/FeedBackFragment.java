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
 * ��˵�� ���ⷴ��
 * 
 * @author ����Science��������
 * @school University of South China
 * @Email chentushen.science@gmail.com && 274240671@qq.com
 * @version 1.0
 * @2015-4-10
 * 
 */

public class FeedBackFragment extends Fragment {

	private View mView;
	// ���ϽǷ���ͼ��
	private ImageView mImageView;
	private TextView mTextModule;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mView = inflater.inflate(R.layout.feedback_fragment_layout, container,
				false);

		mTextModule = (TextView) mView.findViewById(R.id.text_module);
		mTextModule.setText("����");
		mImageView = (ImageView) mView.findViewById(R.id.img_menu);
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Automatically determine the switch
				((MainActivity) getActivity()).getSlidingMenu().toggle();
			}
		});

		return mView;
	}
}
