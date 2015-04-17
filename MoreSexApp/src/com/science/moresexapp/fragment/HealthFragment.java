package com.science.moresexapp.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
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
import com.sciecne.moresexapp.R;
import com.science.moresexapp.MainActivity;
import com.science.moresexapp.adapter.PageListViewAdapter;
import com.science.moresexapp.bean.Article;
import com.science.moresexapp.ui.ArticleActivity;
import com.science.moresexapp.utils.AppConfig;
import com.science.moresexapp.utils.AppContext;
import com.whos.swiperefreshandload.view.SwipeRefreshLayout;
import com.whos.swiperefreshandload.view.SwipeRefreshLayout.OnLoadListener;
import com.whos.swiperefreshandload.view.SwipeRefreshLayout.OnRefreshListener;

/**
 * health module fragment
 * 
 * @author 幸运Science·陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @date 2015-1-28
 */
public class HealthFragment extends Fragment implements OnRefreshListener,
		OnLoadListener, OnItemClickListener {

	private View mView;
	private ListView mArticleListView;
	private List<Article> mArticleBriefList = new ArrayList<Article>();
	private PageListViewAdapter mListAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;

	private ImageView mImageView;
	private TextView mTextModule;
	private FrameLayout mLoadView;

	private AppContext appContext;// 全局Context

	private Intent mIntent;
	// 点击量URL
	private String mPath;
	private JSONArray jsonArray;
	// 刷新请求和点击量请求
	private int requestStyle;
	private RequestQueue mRequestQueue;
	private JsonArrayRequest mJsonArrayRequest;
	private Gson mGson = new Gson();

	// 下一页
	private int mNextPage = 1;
	private JSONArray mNextJsonArray;
	private int connect = 0;

	@SuppressLint({ "ResourceAsColor", "InlinedApi" })
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.recommend_fragment_layout, container,
				false);

		initComponent();

		if (!appContext.isNetworkConnected()) {
			Toast.makeText(getActivity(), R.string.network_not_connected,
					Toast.LENGTH_LONG).show();
			// 去除加载
			mLoadView.setVisibility(View.GONE);
			connect = 1;

		} else {
			initData(AppConfig.GET_HEALTH_JSON.replace("{1}", "1"), 1);
		}

		return mView;
	}

	@SuppressLint({ "ResourceAsColor", "InlinedApi" })
	private void initComponent() {

		// 网络连接判断
		appContext = (AppContext) getActivity().getApplication();

		mLoadView = (FrameLayout) mView.findViewById(R.id.loadView);
		mTextModule = (TextView) mView.findViewById(R.id.text_module);
		mTextModule.setText("健康");
		mImageView = (ImageView) mView.findViewById(R.id.img_menu);
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Automatically determine the switch
				((MainActivity) getActivity()).getSlidingMenu().toggle();
			}
		});

		mArticleListView = (ListView) mView.findViewById(R.id.article_list);
		mArticleListView.setOnItemClickListener(this);

		// 刷新加载效果
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

		// 请求队列对象，它可以缓存所有的HTTP请求，然后按照一定的算法并发地发出这些请求
		mRequestQueue = Volley.newRequestQueue(getActivity());
	}

	private void initData(String path, int i) {

		requestStyle = i;
		if (requestStyle != 3) {
			mSwipeRefreshLayout.setRefreshing(true);
		}
		// 为了要发出一条HTTP请求，我们还需要创建一个JsonArrayRequest对象
		mJsonArrayRequest = new JsonArrayRequest(path,
		// "http://m.bitauto.com/appapi/News/List.ashx/",
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray arg0) {

						switch (requestStyle) {
						case 1:
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
							break;

						case 2:
							mSwipeRefreshLayout.setRefreshing(false);
							break;

						case 3:
							mNextJsonArray = arg0;
							new Thread() {
								public void run() {
									Message msg = new Message();
									try {
										if (mNextJsonArray.length() > 0) {
											msg.what = 2;
										} else {
											msg.what = -1;
										}
									} catch (Exception e) {
									}
									mHandler.sendMessage(msg);
								}
							}.start();
							break;
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.e("onError", arg0.getMessage(), arg0);
					}
				});

		mRequestQueue.add(mJsonArrayRequest);

	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {

				// 隐藏加载
				mLoadView.setVisibility(View.GONE);

				mArticleBriefList = mGson.fromJson(jsonArray.toString(),
						new TypeToken<List<Article>>() {
						}.getType());
				mListAdapter = new PageListViewAdapter(getActivity(),
						mArticleBriefList);
				mArticleListView.setAdapter(mListAdapter);
				mSwipeRefreshLayout.setRefreshing(false);

			} else if (msg.what == 2) {

				mArticleBriefList = mGson.fromJson(mNextJsonArray.toString(),
						new TypeToken<List<Article>>() {
						}.getType());
				mArticleBriefList = mListAdapter
						.updataArticleList(mArticleBriefList);
				mListAdapter.notifyDataSetChanged();

				mSwipeRefreshLayout.setLoading(false);
				mSwipeRefreshLayout.setRefreshing(false);

			} else if (msg.what == -1) {
				Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_LONG);
			}
		}
	};

	@Override
	public void onRefresh() {
		// values.add(0, "Add " + values.size());
		if (connect == 1) {
			Toast.makeText(getActivity(), "网络错误,请检查网络", Toast.LENGTH_LONG);
			mSwipeRefreshLayout.setRefreshing(false);
		} else {
			initData(AppConfig.GET_HEALTH_JSON.replace("{1}", "1"), 1);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// 在请求完成的时候
					mSwipeRefreshLayout.setRefreshing(false);
					mListAdapter.notifyDataSetChanged();
				}
			}, 2000);
		}
	}

	@Override
	public void onLoad() {
		if (connect == 1) {
			Toast.makeText(getActivity(), "网络错误,请检查网络", Toast.LENGTH_LONG);
			mSwipeRefreshLayout.setLoading(false);
		} else {
			mNextPage++;
			initData(AppConfig.GET_HEALTH_JSON.replace("{1}", "" + mNextPage),
					3);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					mSwipeRefreshLayout.setLoading(false);
					mListAdapter.notifyDataSetChanged();
				}
			}, 1000);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		showArticleItem(mArticleBriefList.get(position));
	}

	private void showArticleItem(Article articleEntry) {

		mIntent = new Intent(getActivity(), ArticleActivity.class);

		mIntent.putExtra("title", articleEntry.getTitle());
		mIntent.putExtra("time", articleEntry.getTime());
		mIntent.putExtra("content", articleEntry.getContent());
		mIntent.putExtra("click", articleEntry.getClick());
		mIntent.putExtra("source", articleEntry.getSource());

		mPath = AppConfig.GET_CLICK_JSON;
		mPath = mPath.replace("{ID}", "" + articleEntry.getId());
		initData(mPath, 2);
		mSwipeRefreshLayout.setRefreshing(false);

		startActivity(mIntent);
	}
}
