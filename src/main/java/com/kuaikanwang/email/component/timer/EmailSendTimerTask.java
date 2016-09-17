package com.kuaikanwang.email.component.timer;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;

import com.kuaikanwang.email.redis.RedisDao;
import com.kuaikanwang.email.service.SendEmailService;
import com.kuaikanwang.email.utils.redis.RedisKeyUtil;
/**
 * 邮件定时发送
 * <p>Title: EmailSendTimerTask.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: Sage</p>
 * @author 五虎将
 * @date 2016年9月12日下午11:37:33
 * @version 1.0
 */
public class EmailSendTimerTask implements InitializingBean{

	/**
	 * 这个定时任务会启动两次,先暂时解决下 单例 加判断符号
	 */
	
	@Resource
	private SendEmailService sendEmailServiceImpl;
	@Resource
	private RedisDao redisDaoImpl;
	//这里要设置一个可发送的邮箱集合 --以后看看到底一台机器一天能发送多少封邮件再做打算
	public void work(){
		//启动项目注册此key值 防止跳过
		
		if(redisDaoImpl.getValueByKeyAndUpdate(RedisKeyUtil.getTimeTaskKey("send", "email"))){
			System.out.println("send mail start !");
			Long start = redisDaoImpl.getValueByKeyNum(RedisKeyUtil.getSendEmailStartValue());
			Long count = redisDaoImpl.getValueByKeyNum(RedisKeyUtil.getSendEmailCountKey());
			try {
				Long num =0l;
				for (int i = 0; i < count; i++) {//多少轮设置成可调节的 从redis中取 先设置为3次
					num = num + sendEmailServiceImpl.sendEmail(start);
					Thread.sleep(500);//肯定能防止了吧
				}
			
				System.out.println("send emial num is : " + num + " and start is : " + start);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//结束后 --将控制符设置回去
			redisDaoImpl.setValueByKey(RedisKeyUtil.getTimeTaskKey("send", "email"), "1");
			
		}else{
			//未获取到控制符 跳过
			System.out.println("the send mail task skip!");
		}
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		redisDaoImpl.setValueByKey(RedisKeyUtil.getTimeTaskKey("send", "email"), "1");
	}
	
	
}
