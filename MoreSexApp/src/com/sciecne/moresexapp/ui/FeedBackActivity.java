package com.sciecne.moresexapp.ui;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sciecne.moresexapp.R;
import com.sciecne.moresexapp.utils.AVService;

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

public class FeedBackActivity extends Activity {

	// ���ϽǷ���ͼ��
	private ImageView mBackImage;
	private TextView mTextModule;
	private String userId;
	private AVUser currentUser;
	private EditText mFeedbackContentEdit;
	private Button mSend;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback_layout);

		// ����ʽ״̬������
		initSystemBar();
		initComponent();
		initFindCallback();
		addListner();
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void initSystemBar() {
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// ͸��״̬��
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// ͸��������
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		// ����״̬���Ĺ���ʵ��
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// ����״̬������
		tintManager.setStatusBarTintEnabled(true);
		// �����������
		tintManager.setNavigationBarTintEnabled(true);
		// ����һ����ɫ��ϵͳ��
		tintManager.setTintColor(Color.parseColor("#5ddd57"));
	}

	public void initComponent() {
		mTextModule = (TextView) findViewById(R.id.text_module);
		mTextModule.setText("����");
		mBackImage = (ImageView) findViewById(R.id.back);

		mFeedbackContentEdit = (EditText) findViewById(R.id.feedback_content_edit);
		mSend = (Button) findViewById(R.id.send_feedback);
	}

	public void initFindCallback() {
		currentUser = AVUser.getCurrentUser();
		if (currentUser != null) {
			userId = currentUser.getObjectId();
		}
		FindCallback<AVObject> findCallback = new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
				} else {
					Toast.makeText(FeedBackActivity.this, "�������磡",
							Toast.LENGTH_LONG).show();
				}
			}
		};
		AVQuery<AVObject> query = new AVQuery<AVObject>("SuggestionByUser");
		query.whereEqualTo("UserObjectId", userId);
		query.findInBackground(findCallback);
	}

	public void addListner() {
		mBackImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Automatically determine the switch
				FeedBackActivity.this.finish();
			}
		});

		mSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String advice = mFeedbackContentEdit.getText().toString();

				if (advice.isEmpty()) {
					Toast.makeText(FeedBackActivity.this, "���ݲ���Ϊ��!",
							Toast.LENGTH_LONG).show();
				} else {
					SaveCallback saveCallback = new SaveCallback() {
						@Override
						public void done(AVException e) {
							if (e == null) {
								mHandler.obtainMessage(1).sendToTarget();
							} else {
								mHandler.obtainMessage(2).sendToTarget();
							}
						}
					};
					AVService.createAdvice(userId, advice, saveCallback);
				}
			}
		});
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(FeedBackActivity.this, "��л�����ύ��ף��������졣",
						Toast.LENGTH_LONG).show();
				break;
			case 2:
				Toast.makeText(FeedBackActivity.this, "�ǳ���Ǹ���ύ����",
						Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
	};

}
