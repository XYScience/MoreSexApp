package com.sciecne.moresexapp.fragment;

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
import com.sciecne.moresexapp.ArticleActivity;
import com.sciecne.moresexapp.MainActivity;
import com.sciecne.moresexapp.R;
import com.sciecne.moresexapp.utils.AppConfig;
import com.science.moresexapp.adapter.PageListViewAdapter;
import com.science.moresexapp.bean.Article;
import com.whos.swiperefreshandload.view.SwipeRefreshLayout;
import com.whos.swiperefreshandload.view.SwipeRefreshLayout.OnLoadListener;
import com.whos.swiperefreshandload.view.SwipeRefreshLayout.OnRefreshListener;

/**
 * skill module fragment
 * 
 * @author 幸运Science·陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @date 2015-1-28
 */
public class SkillFragment extends Fragment implements OnRefreshListener,
		OnLoadListener, OnItemClickListener {

	private View mView;
	private ListView mArticleListView;
	private List<Article> mArticleBriefList = new ArrayList<Article>();
	private PageListViewAdapter mListAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;

	private ImageView mImageView;
	private TextView mTextModule;

	private Intent mIntent;
	// 点击量URL
	private String mPath;
	private JSONArray jsonArray;
	// 刷新请求和点击量请求
	private int requestStyle;
	private RequestQueue mRequestQueue;
	private JsonArrayRequest mJsonArrayRequest;
	private Gson mGson;

	@SuppressLint({ "ResourceAsColor", "InlinedApi" })
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.recommend_fragment_layout, container,
				false);

		mTextModule = (TextView) mView.findViewById(R.id.text_module);
		mTextModule.setText("技巧");
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

		initComponent();

		initData(AppConfig.GET_SKILL_JSON.replace("{1}", "1"), 1);

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

		// 请求队列对象，它可以缓存所有的HTTP请求，然后按照一定的算法并发地发出这些请求
		mRequestQueue = Volley.newRequestQueue(getActivity());
	}

	private void initData(String path, int i) {

		mSwipeRefreshLayout.setRefreshing(true);
		requestStyle = i;
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
				mGson = new Gson();
				mArticleBriefList = mGson.fromJson(jsonArray.toString(),
						new TypeToken<List<Article>>() {
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
		initData(AppConfig.GET_SKILL_JSON.replace("{1}", "1"), 1);
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
		initData(AppConfig.GET_SKILL_JSON.replace("{1}", "1"), 1);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mSwipeRefreshLayout.setLoading(false);
				mListAdapter.notifyDataSetChanged();
			}
		}, 1000);
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
		mIntent.putExtra("author", articleEntry.getAuthor());
		mIntent.putExtra("content", articleEntry.getContent());
		mIntent.putExtra("click", articleEntry.getClick());
		mIntent.putExtra("source", articleEntry.getSource());

		mPath = AppConfig.GET_CLICK_JSON;
		mPath = mPath.replace("{ID}", "" + articleEntry.getId());
		Log.e("11111111", "11111111111111111:" + articleEntry.getId());
		initData(mPath, 2);
		mSwipeRefreshLayout.setRefreshing(false);

		startActivity(mIntent);
	}
}
