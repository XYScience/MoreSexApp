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
import android.view.View;
import android.view.View.OnClickListener;
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
import com.science.moresexapp.bean.ArticleContent;
import com.science.moresexapp.ui.ArticleActivity;
import com.science.moresexapp.utils.AppConfig;
import com.science.moresexapp.utils.AppContext;
import com.whos.swiperefreshandload.view.SwipeRefreshLayout;
import com.whos.swiperefreshandload.view.SwipeRefreshLayout.OnLoadListener;
import com.whos.swiperefreshandload.view.SwipeRefreshLayout.OnRefreshListener;

/**
 * @description fragment基类
 * 
 * @author 幸运Science 陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-4-18
 * 
 */

@SuppressLint("ShowToast")
public class ModuleBaseFragment extends Fragment implements OnRefreshListener,
		OnLoadListener, OnItemClickListener {

	private AppContext appContext;// 全局Context
	private boolean isNetworkConnected = false;

	private ListView mArticleListView;
	private List<Article> mArticleBriefList = new ArrayList<Article>();
	private List<ArticleContent> mArticleContentList = new ArrayList<ArticleContent>();
	private PageListViewAdapter mListAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;

	private ImageView mImageView;
	private TextView mTextModule;
	private FrameLayout mLoadView;

	private Intent mIntent;
	// 点击量URL
	private String mClickPath;
	private String mContentPath;
	// 请求数据URL
	private String mUrlPathString;
	private JSONArray jsonArray;
	// 刷新请求和点击量请求
	private int requestStyle;
	private RequestQueue mRequestQueue;
	private Gson mGson;
	private JSONArray mNextJsonArray;
	private int mNextPage2 = 1, mNextPage3 = 1, mNextPage4 = 1, mNextPage5 = 1,
			mNextPage6 = 1;
	private int mMmoduleStyle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	// 初始化
	@SuppressLint({ "ResourceAsColor", "InlinedApi" })
	public void initComponent(View mView) {

		mLoadView = (FrameLayout) mView.findViewById(R.id.loadView);
		mTextModule = (TextView) mView.findViewById(R.id.text_module);
		mImageView = (ImageView) mView.findViewById(R.id.img_menu);
		mArticleListView = (ListView) mView.findViewById(R.id.article_list);

		// 刷新加载效果
		mSwipeRefreshLayout = (SwipeRefreshLayout) mView
				.findViewById(R.id.swipe_container);
		mSwipeRefreshLayout.setColor(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mSwipeRefreshLayout.setMode(SwipeRefreshLayout.Mode.BOTH);
		mSwipeRefreshLayout.setLoadNoFull(false);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.setOnLoadListener(this);

		// 请求队列对象，它可以缓存所有的HTTP请求，然后按照一定的算法并发地发出这些请求
		mRequestQueue = Volley.newRequestQueue(getActivity());
	}

	// 判断网络连接情况并提示
	public boolean isNetworkConnected() {

		appContext = (AppContext) getActivity().getApplication();
		if (!appContext.isNetworkConnected()) {
			Toast.makeText(getActivity(), R.string.network_not_connected,
					Toast.LENGTH_LONG).show();
			// 去除加载
			mLoadView.setVisibility(View.GONE);
			isNetworkConnected = true;
		} else {
			isNetworkConnected = false;
		}
		return isNetworkConnected;
	}

	// 设置模块
	public void initModule(String module, String urlPathString, int moduleStyle) {
		mTextModule.setText(module);
		mUrlPathString = urlPathString;
		mMmoduleStyle = moduleStyle;
	}

	public void addListener() {
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Automatically determine the switch
				((MainActivity) getActivity()).getSlidingMenu().toggle();
			}
		});

		mArticleListView.setOnItemClickListener(this);
	}

	public void initData(String path, int i) {

		requestStyle = i;
		if (requestStyle != 3) {
			mSwipeRefreshLayout.setRefreshing(true);
		}
		// 为了要发出一条HTTP请求，我们还需要创建一个JsonArrayRequest对象
		JsonArrayRequest mJsonArrayRequest = new JsonArrayRequest(path,
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

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				mLoadView.setVisibility(View.GONE);
				mGson = new Gson();
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

		new Thread(new Runnable() {// 下拉触发的函数，这里是谁2s然后加入一个数据，然后更新界面
					@Override
					public void run() {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						initData(mUrlPathString, 1);
						handler.sendEmptyMessage(0);
					}
				}).start();

		// if (!appContext.isNetworkConnected()) {
		// mSwipeRefreshLayout.setRefreshing(false);
		// } else {
		// initData(AppConfig.GET_RECOMMEND_JSON, 1);
		// new Handler().postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// // 在请求完成的时候
		// mSwipeRefreshLayout.setRefreshing(false);
		// mListAdapter.notifyDataSetChanged();
		// }
		// }, 2000);
		// }
	}

	private MyHandler handler = new MyHandler();

	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				mSwipeRefreshLayout.setRefreshing(false);
				mListAdapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void onLoad() {

		if (isNetworkConnected()) {
			mSwipeRefreshLayout.setLoading(false);
		} else {
			switch (mMmoduleStyle) {
			case 1:
				initData(mUrlPathString, 1);
				break;

			case 2:
				mNextPage2++;
				initData(
						AppConfig.GET_BIRTHCONTROL_JSON.replace("{1}", ""
								+ mNextPage2), 3);
				break;

			case 3:
				mNextPage3++;
				initData(
						AppConfig.GET_HEALTH_JSON.replace("{1}", ""
								+ mNextPage3), 3);
				break;

			case 4:
				mNextPage4++;
				initData(
						AppConfig.GET_MENTALITY_JSON.replace("{1}", ""
								+ mNextPage4), 3);
				break;

			case 5:
				mNextPage5++;
				initData(
						AppConfig.GET_PHYSIOLOGY_JSON.replace("{1}", ""
								+ mNextPage5), 3);
				break;

			case 6:
				mNextPage6++;
				initData(AppConfig.GET_SKILL_JSON.replace("{1}", ""
						+ mNextPage6), 3);
				break;
			}

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

		mIntent.putExtra("id", articleEntry.getId());
		mIntent.putExtra("title", articleEntry.getTitle());
		mIntent.putExtra("time", articleEntry.getTime());
		mIntent.putExtra("click", articleEntry.getClick());
		mIntent.putExtra("source", articleEntry.getSource());
		startActivity(mIntent);

		mClickPath = AppConfig.GET_CLICK_JSON;
		mClickPath = mClickPath.replace("{ID}", "" + articleEntry.getId());
		initData(mClickPath, 2);
		mSwipeRefreshLayout.setRefreshing(false);
	}
}
