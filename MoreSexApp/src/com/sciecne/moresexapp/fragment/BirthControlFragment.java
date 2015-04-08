package com.sciecne.moresexapp.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sciecne.moresexapp.MainActivity;
import com.sciecne.moresexapp.R;
import com.science.moresexapp.adapter.PageListViewAdapter;
import com.science.moresexapp.bean.News;
import com.whos.swiperefreshandload.view.SwipeRefreshLayout;
import com.whos.swiperefreshandload.view.SwipeRefreshLayout.OnLoadListener;
import com.whos.swiperefreshandload.view.SwipeRefreshLayout.OnRefreshListener;

/**
 * BirthControl module fragment
 * 
 * @author 幸运Science·陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @date 2015-1-28
 */
public class BirthControlFragment extends Fragment implements
		OnRefreshListener, OnLoadListener {

	private View mView;
	private ListView mArticleListView;
	private List<News> mArticleBriefList = new ArrayList<News>();
	private PageListViewAdapter mListAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;

	private ImageView mImageView;
	private TextView mTextModule;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.recommend_fragment_layout, container,
				false);

		mTextModule = (TextView) mView.findViewById(R.id.text_module);
		mTextModule.setText("避孕");
		mImageView = (ImageView) mView.findViewById(R.id.img_menu);
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Automatically determine the switch
				((MainActivity) getActivity()).getSlidingMenu().toggle();
			}
		});

		mArticleListView = (ListView) mView.findViewById(R.id.article_list);

		initComponent();

		initData();

		return mView;
	}

	@SuppressLint({ "ResourceAsColor", "InlinedApi" })
	// 刷新加载效果
	private void initComponent() {
		mSwipeRefreshLayout = (SwipeRefreshLayout) mView
				.findViewById(R.id.swipe_container);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.setOnLoadListener(this);
		mSwipeRefreshLayout.setColor(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mSwipeRefreshLayout.setMode(SwipeRefreshLayout.Mode.BOTH);
		mSwipeRefreshLayout.setLoadNoFull(false);
	}

	private JSONArray jsonArray;

	private void initData() {
		mSwipeRefreshLayout.setRefreshing(true);
		// 请求队列对象，它可以缓存所有的HTTP请求，然后按照一定的算法并发地发出这些请求
		RequestQueue mQueue = Volley.newRequestQueue(getActivity());
		// 为了要发出一条HTTP请求，我们还需要创建一个JsonArrayRequest对象
		JsonArrayRequest jsonRequest = new JsonArrayRequest(
				"http://m.bitauto.com/appapi/News/List.ashx/",
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray arg0) {

						jsonArray = arg0;

						new Thread() {
							public void run() {
								Message msg = new Message();
								try {
									if (jsonArray.length() > 0) {
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

		mQueue.add(jsonRequest);

	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				Gson gson = new Gson();
				mArticleBriefList = gson.fromJson(jsonArray.toString(),
						new TypeToken<List<News>>() {
						}.getType());
				mListAdapter = new PageListViewAdapter(getActivity(),
						mArticleBriefList);
				mArticleListView.setAdapter(mListAdapter);
				mSwipeRefreshLayout.setRefreshing(false);
			} else if (msg.what == -1) {
				Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_LONG);
			}
		}
	};

	@Override
	public void onRefresh() {
		// values.add(0, "Add " + values.size());
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// 在请求完成的时候
				mSwipeRefreshLayout.setRefreshing(false);
				mListAdapter.notifyDataSetChanged();
			}
		}, 2000);
	}

	@Override
	public void onLoad() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mSwipeRefreshLayout.setLoading(false);
				mListAdapter.notifyDataSetChanged();
			}
		}, 1000);
	}
}
