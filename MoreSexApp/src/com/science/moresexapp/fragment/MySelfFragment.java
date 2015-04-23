package com.science.moresexapp.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.sciecne.moresexapp.R;
import com.science.moresexapp.MainActivity;
import com.science.moresexapp.ui.AlterActivity;
import com.science.moresexapp.utils.AVService;

/**
 * 类说明 个人资料页面
 * 
 * @author 幸运Science·陈土燊
 * @school University of South China
 * @Email chentushen.science@gmail.com && 274240671@qq.com
 * @version 1.0
 * @2015-4-13
 * 
 */

public class MySelfFragment extends Fragment {

	private View mRootView;

	private TextView mAccountText;
	private static TextView mUsernameText;
	private static TextView mSexText;
	private static TextView mBirthText;
	private static TextView mHomeTownText;
	private ImageView mAlterImg;
	private Button mBackAccount;

	private AVUser mUser;
	private String userId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.myself_layout, container, false);// 关联布局文件

		initComponent();
		initData();
		addListener();

		return mRootView;
	}

	public void initComponent() {
		mAccountText = (TextView) mRootView.findViewById(R.id.account);
		mUsernameText = (TextView) mRootView.findViewById(R.id.username);
		mSexText = (TextView) mRootView.findViewById(R.id.sex);
		mBirthText = (TextView) mRootView.findViewById(R.id.birth);
		mHomeTownText = (TextView) mRootView.findViewById(R.id.hometown);
		mAlterImg = (ImageView) mRootView.findViewById(R.id.alter_img);
		mBackAccount = (Button) mRootView.findViewById(R.id.back_account);
	}

	private void initData() {
		mUser = AVUser.getCurrentUser();
		if (mUser != null) {
			userId = mUser.getObjectId();
			mAccountText.setText(mUser.getEmail());
			mUsernameText.setText(mUser.getUsername());
			findCallback();
		}
	}

	private void findCallback() {
		FindCallback<AVObject> findCallback = new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
					Message msg = new Message();
					msg.what = 1;
					msg.obj = avObjects;
					mHandler.sendMessage(msg);
				} else {
					Toast.makeText(getActivity(), "请检查网络！", Toast.LENGTH_LONG)
							.show();
				}
			}
		};

		AVQuery<AVObject> query = new AVQuery<AVObject>("UserInformation");
		query.whereEqualTo("userObjectId", userId);
		query.findInBackground(findCallback);
	}

	private Handler mHandler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				List<AVObject> responseList = (List<AVObject>) msg.obj;
				if (responseList != null && responseList.size() != 0) {
					mSexText.setText(responseList.get(responseList.size() - 1)
							.getString("userSex"));
					mBirthText.setText(responseList
							.get(responseList.size() - 1)
							.getString("userBirth"));
					mHomeTownText.setText(responseList.get(
							responseList.size() - 1).getString("userHome"));
				}
				break;
			}
		}
	};

	public static void sendDataFragment(String username, String sex,
			String birth, String home) {
		mUsernameText.setText(username);
		mSexText.setText(sex);
		mBirthText.setText(birth);
		mHomeTownText.setText(home);
	}

	private void addListener() {
		mAlterImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), AlterActivity.class);
				getActivity().startActivityForResult(intent, 0);// getActivity().startActivityForResult调用FragmentActivity的onActivityResult
			}
		});

		mBackAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AVService.logout();
				Intent loginIntent = new Intent(getActivity(),
						MainActivity.class);
				startActivity(loginIntent);
				getActivity().finish();
			}

		});
	}
}
