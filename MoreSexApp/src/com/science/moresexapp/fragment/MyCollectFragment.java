package com.science.moresexapp.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sciecne.moresexapp.R;
import com.science.moresexapp.adapter.PageListViewAdapter;
import com.science.moresexapp.bean.Article;
import com.science.moresexapp.ui.ArticleActivity;
import com.science.moresexapp.utils.AppConfig;

/**
 * 类说明 我的收藏界面
 * 
 * @author 幸运Science·陈土燊
 * @school University of South China
 * @Email chentushen.science@gmail.com && 274240671@qq.com
 * @version 1.0
 * @2015-4-13
 * 
 */

public class MyCollectFragment extends Fragment implements OnItemClickListener {

	private View mView;
	private ListView mArticleListView;
	private List<Article> mArticleBriefList = new ArrayList<Article>();
	private List<Article> listTemp = new ArrayList<Article>();
	private PageListViewAdapter mListAdapter;
	private Intent mIntent;
	private String mClickPath;
	private String mArticlePath;
	private Number mArticleId;

	private RequestQueue mRequestQueue;
	private AVUser currentUser;
	private String userId;
	private List<AVObject> responseCollectList;
	private Gson mGson;
	private JSONArray mJsonArray;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.mycollect_layout, container, false);// 关联布局文件

		currentUser = AVUser.getCurrentUser();
		if (currentUser != null) {
			userId = currentUser.getObjectId();
		}

		initComponent();
		findCollectCallBack();

		return mView;
	}

	private void initComponent() {
		mArticleListView = (ListView) mView.findViewById(R.id.article_list);

		// 请求队列对象，它可以缓存所有的HTTP请求，然后按照一定的算法并发地发出这些请求
		mRequestQueue = Volley.newRequestQueue(getActivity());
		mArticleListView.setOnItemClickListener(this);
	}

	private void findCollectCallBack() {
		FindCallback<AVObject> findCallback = new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {

				Message msg = new Message();
				if (e == null) {
					responseCollectList = avObjects;
					msg.what = 1;
				} else {
					msg.what = 2;
				}
				mCollectHandler.sendMessage(msg);
			}
		};

		AVQuery<AVObject> query = new AVQuery<AVObject>("ArticleCollect");
		query.whereEqualTo("userObjectId", userId);
		query.findInBackground(findCallback);
	}

	private Handler mCollectHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				for (int i = 0; i < responseCollectList.size(); i++) {

					mArticleId = responseCollectList.get(i).getNumber(
							"articleId");
					mArticlePath = AppConfig.GET_ARTICLE_JSON.replace("{ID}",
							"" + mArticleId);
					initData(mArticlePath);
					Log.e("111111111", "1111111111111111:" + mArticleId);
				}

				break;
			case 2:
				Toast.makeText(getActivity(), "网络出错!", Toast.LENGTH_SHORT)
						.show();
				break;

			}
		}
	};

	private void initData(String ArticlePath) {

		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ArticlePath,
				new Response.Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray jsonArray) {

						mJsonArray = jsonArray;
						new Thread() {
							public void run() {
								Message msg = new Message();
								try {
									if (mJsonArray.length() > 0) {
										msg.what = 1;
									} else {
										msg.what = -1;
									}
								} catch (Exception e) {
								}
								mHandler.sendMessage(msg);
							}
						}.start();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.e("onError", arg0.getMessage(), arg0);
					}
				});
		mRequestQueue.add(jsonArrayRequest);
	}

	private int i = 0;
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				i++;
				mGson = new Gson();
				listTemp = mGson.fromJson(mJsonArray.toString(),
						new TypeToken<List<Article>>() {
						}.getType());
				mArticleBriefList.addAll(listTemp);
				listTemp.clear();

				if (responseCollectList.size() <= i) {
					mListAdapter = new PageListViewAdapter(getActivity(),
							mArticleBriefList);
					mArticleListView.setAdapter(mListAdapter);
					mListAdapter.notifyDataSetChanged();
					i = 0;
				}

			} else if (msg.what == 2) {
				Toast.makeText(getActivity(), "还没有收藏(⊙o⊙)哦!", Toast.LENGTH_LONG)
						.show();
			}
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		showArticleItem(mArticleBriefList.get(position));
	}

	private void showArticleItem(Article articleEntry) {

		mIntent = new Intent(getActivity(), ArticleActivity.class);

		mIntent.putExtra("id", articleEntry.getId());
		mIntent.putExtra("title", articleEntry.getTitle());
		mIntent.putExtra("time", articleEntry.getTime());
		mIntent.putExtra("click", articleEntry.getClick());
		mIntent.putExtra("source", articleEntry.getSource());
		mIntent.putExtra("imgurl", articleEntry.getImgUrl());
		startActivity(mIntent);

		mClickPath = AppConfig.GET_CLICK_JSON;
		mClickPath = mClickPath.replace("{ID}", "" + articleEntry.getId());
		initData(mClickPath);
	}

}
