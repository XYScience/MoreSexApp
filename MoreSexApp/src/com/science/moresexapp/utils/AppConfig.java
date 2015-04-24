package com.science.moresexapp.utils;

/**
 * @description
 * 
 * @author 幸运Science 陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-4-9
 * 
 */

public class AppConfig {

	// 获取推荐10条数据
	public static final String GET_RECOMMEND_JSON = "http://123.56.93.109:8080/MoreSexClient/getRecommendJson.action";
	// 获取技巧分页数据
	public static final String GET_SKILL_JSON = "http://123.56.93.109:8080/MoreSexClient/getSkillJson.action?page={1}";
	// 获取健康分页数据
	public static final String GET_HEALTH_JSON = "http://123.56.93.109:8080/MoreSexClient/getHeathlJson.action?page={1}";
	// 获取生理分页数据
	public static final String GET_PHYSIOLOGY_JSON = "http://123.56.93.109:8080/MoreSexClient/getPhysiologyJson.action?page={1}";
	// 获取心理分页数据
	public static final String GET_MENTALITY_JSON = "http://123.56.93.109:8080/MoreSexClient/getMentalityJson.action?page={1}";
	// 获取避孕分页数据
	public static final String GET_BIRTHCONTROL_JSON = "http://123.56.93.109:8080/MoreSexClient/getBirthControlJson.action?page={1}";
	// 获取点击量数据
	public static final String GET_CLICK_JSON = "http://123.56.93.109:8080/MoreSexClient/clickAction.action?id={ID}";
	// 获取完整文章内容数据
	public static final String GET_Content_JSON = "http://123.56.93.109:8080/MoreSexClient/ContentAction.action?id={ID}";
	// 获取一篇文章数据
	public static final String GET_ARTICLE_JSON = "http://123.56.93.109:8080/MoreSexClient/getArticleAction.action?id={ID}";
}
