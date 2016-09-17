package com.kuaikanwang.email.dao;

import java.util.List;

import com.kuaikanwang.email.domain.MainEmail;

public interface MainEmailMapper {

	
	/**
	 * 通过email 查询是否存在
	 * <p>Title: findPrePicByUrl</p>
	 * <p>Description: </p>
	 * @return
	 */
	public  long  findMainEmailByEmail(String email);
	
	/**
	 * 插入数据到数据库中
	 */
	public void insertMainEmail (MainEmail email);
	
	
	/**
	 * 获取指定范围内的email 每次10个
	 */
	public List<String> findEmailByRandge(long start);
	
	/**
	 * 查询邮箱最大数目
	 */
	public Long  findEmailMaxNum();
	
	
	/**
	 * 根据email更新 点击邮件的次数 clickcount
	 */
	
	public void updateClickCountByEmail(String email);
	
	/**
	 * 据email更新 点击邮件进入网站的次数 clickcount
	 */
	public void updateDetailCountByEmail(String email);
	
	
}
