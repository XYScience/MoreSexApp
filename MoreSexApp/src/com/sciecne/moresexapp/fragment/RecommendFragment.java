package com.sciecne.moresexapp.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
 * The recommend fragment
 * 
 * @author 幸运Science·陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @date 2015-1-30
 */
public class RecommendFragment extends Fragment implements OnRefreshListener,
		OnLoadListener {

	private ListView mArticleListView;
	private List<News> mArticleBriefList;
	private List<Map<String, Object>> list;
	private PageListViewAdapter mListAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;

	private ImageView mImageView;
	private TextView mTextModule;

	@SuppressLint({ "ResourceAsColor", "InlinedApi" })
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.recommend_fragment_layout,
				container, false);

		list = new ArrayList<Map<String, Object>>();

		mTextModule = (TextView) view.findViewById(R.id.text_module);
		mTextModule.setText("推荐");
		mImageView = (ImageView) view.findViewById(R.id.img_menu);
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Automatically determine the switch
				((MainActivity) getActivity()).getSlidingMenu().toggle();
			}
		});

		mArticleListView = (ListView) view.findViewById(R.id.article_list);

		initData();

		// 刷新加载效果
		mSwipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_container);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.setOnLoadListener(this);
		mSwipeRefreshLayout.setColor(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mSwipeRefreshLayout.setMode(SwipeRefreshLayout.Mode.BOTH);
		mSwipeRefreshLayout.setLoadNoFull(false);

		return view;
	}

	private void initData() {

		// 请求队列对象，它可以缓存所有的HTTP请求，然后按照一定的算法并发地发出这些请求
		RequestQueue mQueue = Volley.newRequestQueue(getActivity());
		// 为了要发出一条HTTP请求，我们还需要创建一个JsonArrayRequest对象
		JsonArrayRequest jsonRequest = new JsonArrayRequest(
				"http://m.bitauto.com/appapi/News/List.ashx/",
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray arg0) {
						Log.d("onResponse", arg0.toString());
						Gson gson = new Gson();
						mArticleBriefList = gson.fromJson(arg0.toString(),
								new TypeToken<List<News>>() {
								}.getType());

						for (Iterator<News> iterator = mArticleBriefList
								.iterator(); iterator.hasNext();) {
							News news = (News) iterator.next();
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("title", news.getTitle());
							map.put("publishTime", news.getPublishTime());
							map.put("id", news.getId());
							list.add(map);
						}

						mListAdapter = new PageListViewAdapter(getActivity(),
								list);
						mArticleListView.setAdapter(mListAdapter);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.e("onError", arg0.getMessage(), arg0);
					}
				});

		mQueue.add(jsonRequest);

		// Thread thread = new Thread(new Runnable() {
		// @Override
		// public void run() {
		//
		// //
		// http://m.weather.com.cn/data/101010100.html，这是中国天气网提供的一个查询天气信息的接口，响应的数据就是以JSON格式返回的
		// String path = "http://m.bitauto.com/appapi/News/List.ashx/";
		// String jsonString = HttpUtils.getJsonContent(path);// 从网络获取数据
		// Log.e("11111111111111111111", jsonString);
		// mArticleBriefList = GsonTools.getArticleBrief(jsonString,
		// News.class);// 解析json数据
		// mListAdapter = new PageListViewAdapter(getActivity(),
		// mArticleBriefList);
		// mArticleListView.setAdapter(mListAdapter);
		//
		// }
		// });
		// thread.start();
	}

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
