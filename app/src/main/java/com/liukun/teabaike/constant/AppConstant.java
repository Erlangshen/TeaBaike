package com.liukun.teabaike.constant;
public class AppConstant {
	
	/** 头条滚动广告url */
	public final static String JSON_URL = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getSlideshow";
	/** 头条url */
	public final static String JSON_LIST_DATA_0 = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines";
	/** 百科url */
	public final static String JSON_LIST_DATA_1 = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&type=16";
	/** 资讯url */
	public final static String JSON_LIST_DATA_2 = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&type=52";
	/** 经营url */
	public final static String JSON_LIST_DATA_3 = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&type=53";
	/** 数据url */
	public final static String JSON_LIST_DATA_4 = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&type=54";
	/** 搜索网络地址 */
	public static final String SEARCH = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.searcListByTitle";
	/** 内容新页的网络地址 后面加&id= */
	public static final String CONTENT = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent";
	/** 数据库sd卡文件夹路径 */
	public static final String DBPATH = "/Tea/";
}
