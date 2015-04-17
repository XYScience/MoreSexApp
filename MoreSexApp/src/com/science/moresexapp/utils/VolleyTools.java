package com.science.moresexapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;

/**
 * @description ��ܵĹ�����
 * 
 * @author ����Science ������
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-4-7
 * 
 */

public class VolleyTools {

	// ��ʼ��������С�ͼƬ������
	private RequestQueue queue;
	private ImageLoader imageLoader;

	// ˽�о�̬ʵ��
	private static VolleyTools instance;

	// ˽�й��췽��
	private VolleyTools(Context context) {
		// �����������
		queue = Volley.newRequestQueue(context);
		// ����ͼƬ������
		imageLoader = new ImageLoader(queue, new LruImageCache());
	}

	// ��������̬�ķ���
	public static VolleyTools getInstance(Context context) {
		if (instance == null) {
			instance = new VolleyTools(context);
		}
		return instance;
	}

	// �õ��������
	public RequestQueue getQueue() {
		return queue;
	}

	// �õ�ͼƬ������
	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	/**
	 * ʹ��LRU�����㷨�Ļ�����
	 */
	class LruImageCache implements ImageCache {

		// ��������
		private LruCache<String, Bitmap> cache;

		public LruImageCache() {
			// ���㻺�����ֵ
			int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
			// �����������ʵ��
			cache = new LruCache<String, Bitmap>(maxSize) {
				@Override
				protected int sizeOf(String key, Bitmap value) {
					// ����bitmapռ�õ��ڴ��С
					return value.getRowBytes() * value.getHeight();
				}
			};
		}

		// �ӻ�����ȡͼƬ����
		@Override
		public Bitmap getBitmap(String url) {
			return cache.get(url);
		}

		// ��ͼƬ���󱣴浽����������
		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			cache.put(url, bitmap);
		}

	}
}
