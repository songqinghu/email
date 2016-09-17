package com.kuaikanwang.email.domain;

import java.util.Date;

public class MainEmail {

	
	private long id;
	private long pre_id;//副表id
	private long emailweb_id;// 站点id
	private String email;//邮箱
	private long clickcount;//点击邮件次数
	private long detailcount;//进入网站次数
	private Date cratedate;//创建时间
	private int flag;//图片标示位 默认0 可用
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPre_id() {
		return pre_id;
	}
	public void setPre_id(long pre_id) {
		this.pre_id = pre_id;
	}
	public long getEmailweb_id() {
		return emailweb_id;
	}
	public void setEmailweb_id(long emailweb_id) {
		this.emailweb_id = emailweb_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getClickcount() {
		return clickcount;
	}
	public void setClickcount(long clickcount) {
		this.clickcount = clickcount;
	}
	public long getDetailcount() {
		return detailcount;
	}
	public void setDetailcount(long detailcount) {
		this.detailcount = detailcount;
	}
	public Date getCratedate() {
		return cratedate;
	}
	public void setCratedate(Date cratedate) {
		this.cratedate = cratedate;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
}
