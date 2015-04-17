package com.science.moresexapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;

/**
 * @description 框架的工具类
 * 
 * @author 幸运Science 陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-4-7
 * 
 */

public class VolleyTools {

	// 初始化请求队列、图片加载器
	private RequestQueue queue;
	private ImageLoader imageLoader;

	// 私有静态实例
	private static VolleyTools instance;

	// 私有构造方法
	private VolleyTools(Context context) {
		// 创建请求队列
		queue = Volley.newRequestQueue(context);
		// 创建图片加载器
		imageLoader = new ImageLoader(queue, new LruImageCache());
	}

	// 公共、静态的方法
	public static VolleyTools getInstance(Context context) {
		if (instance == null) {
			instance = new VolleyTools(context);
		}
		return instance;
	}

	// 得到请求队列
	public RequestQueue getQueue() {
		return queue;
	}

	// 得到图片加载器
	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	/**
	 * 使用LRU回收算法的缓存类
	 */
	class LruImageCache implements ImageCache {

		// 缓存容器
		private LruCache<String, Bitmap> cache;

		public LruImageCache() {
			// 计算缓存的最值
			int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
			// 创建缓存对象实例
			cache = new LruCache<String, Bitmap>(maxSize) {
				@Override
				protected int sizeOf(String key, Bitmap value) {
					// 返回bitmap占用的内存大小
					return value.getRowBytes() * value.getHeight();
				}
			};
		}

		// 从缓存中取图片对象
		@Override
		public Bitmap getBitmap(String url) {
			return cache.get(url);
		}

		// 将图片对象保存到缓存容器中
		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			cache.put(url, bitmap);
		}

	}
}
