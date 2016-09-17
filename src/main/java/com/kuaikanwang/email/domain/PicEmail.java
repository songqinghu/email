package com.kuaikanwang.email.domain;
/**
 * 每日美图邮件信息
 * <p>Title: PicEmail.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: Sage</p>
 * @author 五虎将
 * @date 2016年9月11日下午10:29:21
 * @version 1.0
 */
public class PicEmail {

	
	private  String headName="每日美图";
	
	private String picUrl;//图片地址
	
	private String email;//要发送的邮件

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//这里变成邮箱内容体
	@Override
	public String toString() {
		
		String htmlBody= "<body><img src=\"http://zuiyuyue.com/email/click/reflact?email="+email+"\" style=\"width:0px;height:0px\"/></body>"
			//	+ "<h1>今天的美图到啦! <a href=\"https://client.luosimao.com/u/unsubscribe/04-A0B4EE6C-C1F0-785D-9F11-A12B88F683D8\" style=\"font-family: 'Microsoft Yahei',sans-serif; color: #999; font-size: 24px; text-decoration: none;\">点此查看更多美图</a> </h1><a href=\"www.zuiyuyue.com/email/click/detail?email="+email+"\"> <img src=\""+picUrl+"\" /></a><br>";
					+ "<h1> <a href=\"http://www.zuiyuyue.com/email/click/detail?email="+email+"\" style=\"font-family: 'Microsoft Yahei',sans-serif; color: #999; font-size: 36px; text-decoration: none;\">今天的美图到啦! -- 点此查看更多美图</a> </h1><a href=\"http://www.zuiyuyue.com/email/click/detail?email="+email+"\" style=\"font-family: 'Microsoft Yahei',sans-serif; color: #999; font-size: 36px; text-decoration: none;\"> <img src=\""+picUrl+"\" /></a><br>";
		
		return htmlBody;
	}
	
	
	
	
}
