package com.sciecne.moresexapp.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.avos.avoscloud.AVOSCloud;

/**
 * @description ȫ��Ӧ�ó����ࣺ���ڱ���͵���ȫ��Ӧ�����ü�������������
 * 
 * @author ����Science ������
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-4-10
 * 
 */

public class AppContext extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		// U need your AVOS key and so on to run the code.
		AVOSCloud.initialize(getApplicationContext(),
				"60pzw82x5d1bl18atisy26c3436vtvp0l4t0ma71yqu0udea",
				"g2epsoajhdloisvszkoeblyyugpjfedq7sgwjeahpimgx2ci");
	}

	/**
	 * ��������Ƿ����
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
}
