package com.kuaikanwang.email.dao;

/**
 * 图片访问
 * <p>Title: ImageAccessMapper.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: Sage</p>
 * @author 五虎将
 * @date 2016年7月21日下午9:02:59
 * @version 1.0
 */
public interface ImageAccessMapper {


	/**
	 * 
	 * 获取要发送的图片地址
	 * <p>Title: findImageListByPid</p>
	 * <p>Description: </p>
	 * @param pid
	 * @return
	 */
	public String findImageByEmail(Long start);
}
