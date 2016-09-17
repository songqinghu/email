package com.kuaikanwang.email.utils.redis;
/**
 * 获取指定的key
 * <p>Title: RedisKeyUtil.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: Sage</p>
 * @author 五虎将
 * @date 2016年9月3日下午11:58:49
 * @version 1.0
 */
public class RedisKeyUtil {

	/**
	 * 一级限制
	 * <p>Title: getSpiderOneKey</p>
	 * <p>Description: </p>
	 * @param key
	 * @return
	 */
	public static String getSpiderOneKey(String key){
		
		return "spider_one_"+key;
	}
	
	
	/**
	 * 二级限制
	 * <p>Title: getSpiderOneKey</p>
	 * <p>Description: </p>
	 * @param key
	 * @return
	 */
	public static String getSpiderTwoKey(String key){
		
		return "spider_two_"+key;
	}
	
	
	/**
	 * blacklist 黑名单
	 * <p>Title: getSpiderOneKey</p>
	 * <p>Description: </p>
	 * @param key
	 * @return
	 */
	public static String getSpiderBlackListKey(String key){
		
		return "spider_blacklist_"+key;
	}
	
	/**
	 * 发送图片的key
	 * <p>Title: getSendPicStartValue</p>
	 * <p>Description: </p>
	 * @return
	 */
	public static String getSendPicStartValue(){
		return "send_pic_start";
	}
	
	public static String getSendEmailStartValue(){
		return "send_email_start";
	}
	/**
	 * 获取要发送的轮数目
	 * <p>Title: getSendEmailCountKey</p>
	 * <p>Description: </p>
	 * @return
	 */
	public static String getSendEmailCountKey(){
		return "send_email_count";
	}
	
	/**
	 * 
	 * <p>Title: getTimeTaskKey</p>
	 * <p>Description: </p>
	 * @param one 动作 如 spider send
	 * @param two 性质 如 email pic
	 * @return
	 */
	public static String getTimeTaskKey(String one,String two){
		return "task_"+one+"_"+two;
	}
	
}
