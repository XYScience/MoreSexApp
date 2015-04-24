package com.science.moresexapp.utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FunctionCallback;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.science.moresexapp.ui.LoginActivity;

/**
 * The main entrance Created by lzw on 14-9-11.
 * 
 * @author science·陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @date 2015-1-27
 */
public class AVService {
	public static void countDoing(String doingObjectId,
			CountCallback countCallback) {
		AVQuery<AVObject> query = new AVQuery<AVObject>("DoingList");
		query.whereEqualTo("doingListChildObjectId", doingObjectId);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -10);
		// query.whereNotEqualTo("userObjectId", userId);
		query.whereGreaterThan("createdAt", c.getTime());
		query.countInBackground(countCallback);
	}

	// Use callFunctionMethod
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void getAchievement(String userObjectId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userObjectId", userObjectId);
		AVCloud.callFunctionInBackground("hello", parameters,
				new FunctionCallback() {
					@Override
					public void done(Object object, AVException e) {
						if (e == null) {
							Log.e("at", object.toString());// processResponse(object);
						} else {
							// handleError();
						}
					}
				});
	}

	public static void createDoing(String userId, String doingObjectId) {
		AVObject doing = new AVObject("DoingList");
		doing.put("userObjectId", userId);
		doing.put("doingListChildObjectId", doingObjectId);
		doing.saveInBackground();
	}

	public static void requestPasswordReset(String email,
			RequestPasswordResetCallback callback) {
		AVUser.requestPasswordResetInBackground(email, callback);
	}

	public static void findDoingListGroup(FindCallback<AVObject> findCallback) {
		AVQuery<AVObject> query = new AVQuery<AVObject>("DoingListGroup");
		query.orderByAscending("Index");
		query.findInBackground(findCallback);
	}

	public static void findChildrenList(String groupObjectId,
			FindCallback<AVObject> findCallback) {
		AVQuery<AVObject> query = new AVQuery<AVObject>("DoingListChild");
		query.orderByAscending("Index");
		query.whereEqualTo("parentObjectId", groupObjectId);
		query.findInBackground(findCallback);
	}

	public static void initPushService(Context ctx) {
		PushService.setDefaultPushCallback(ctx, LoginActivity.class);
		PushService.subscribe(ctx, "public", LoginActivity.class);
		AVInstallation.getCurrentInstallation().saveInBackground();
	}

	// 注册
	public static void signUp(String username, String password, String email,
			SignUpCallback signUpCallback) {
		AVUser user = new AVUser();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.signUpInBackground(signUpCallback);
	}

	// 用户基本资料
	public static void alterUserInformation(String userId, String username,
			String sex, String birth, String home, String personalStatement,
			SaveCallback saveCallback) {
		AVObject doing = new AVObject("UserInformation");
		doing.put("userObjectId", userId);
		doing.put("userSex", sex);
		doing.put("userBirth", birth);
		doing.put("userHome", home);
		doing.put("personalStatement", personalStatement);
		doing.saveInBackground(saveCallback);
	}

	// 意见反馈
	public static void createAdvice(String userId, String advice,
			SaveCallback saveCallback) {
		AVObject doing = new AVObject("SuggestionByUser");
		doing.put("userObjectId", userId);
		doing.put("userSuggestion", advice);
		doing.saveInBackground(saveCallback);
	}

	// 查询文章点赞数
	public static void countArticlePraise(String articleContentTitle,
			CountCallback countCallback) {

		AVQuery<AVObject> query = new AVQuery<AVObject>("ArticlePraise");
		query.whereEqualTo("articleContentTitle", articleContentTitle);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -10);// 10分钟前
		// query.whereNotEqualTo("userObjectId", userId);
		query.whereGreaterThan("createdAt", c.getTime());
		query.countInBackground(countCallback);
	}

	// 创建文章点赞用户
	public static void createArticlePraise(String articleContentTitle,
			String userId, SaveCallback saveCallback) {

		AVObject articlePraise = new AVObject("ArticlePraise");
		articlePraise.put("articleContentTitle", articleContentTitle);
		articlePraise.put("userObjectId", userId);
		articlePraise.saveInBackground(saveCallback);
	}

	// 删除文章点赞用户
	public static void removeArticlePraise(String objectId) {
		AVQuery<AVObject> query = new AVQuery<AVObject>("ArticlePraise");
		AVObject articlePraise;
		try {
			articlePraise = query.get(objectId);
			articlePraise.delete();
		} catch (AVException e) {
			e.printStackTrace();
		}
	}

	// 创建文章收藏用户
	public static void createArticleCollect(String articleContentTitle,
			String userId, Integer articleId, SaveCallback saveCallback) {

		AVObject articleCollect = new AVObject("ArticleCollect");
		articleCollect.put("articleContentTitle", articleContentTitle);
		articleCollect.put("userObjectId", userId);
		articleCollect.put("articleId", articleId);
		articleCollect.saveInBackground(saveCallback);
	}

	// 删除文章收藏用户
	public static void removeArticleCollect(String objectId) {
		AVQuery<AVObject> query = new AVQuery<AVObject>("ArticleCollect");
		AVObject articleCollect;
		try {
			articleCollect = query.get(objectId);
			articleCollect.delete();
		} catch (AVException e) {
			e.printStackTrace();
		}
	}

	// 退出登录
	public static void logout() {
		AVUser.logOut();
	}
}
