package com.kuaikanwang.email.controller;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kuaikanwang.email.domain.PicEmail;
import com.kuaikanwang.email.redis.RedisDao;
import com.kuaikanwang.email.service.SendEmailService;
import com.kuaikanwang.email.utils.email.SendmailUtil;
import com.kuaikanwang.email.utils.redis.RedisKeyUtil;

@Controller
@RequestMapping("/email/send")
public class EmailSendController {

	@Resource
	private SendEmailService sendEmailServiceImpl;
	@Resource
	private RedisDao redisDaoImpl;
	
	
	/**
	 * 
	 * <p>Title: sendEmail</p>
	 * <p>Description: </p>
	 * @param flag
	 * @param count 发送的次数 一次100封 每个账号50封 这里自己控制
	 * @return
	 */
	@RequestMapping("/pic")
	@ResponseBody
	public String sendEmail(String flag,@RequestParam(defaultValue="1")Integer count){
		Long start = redisDaoImpl.getValueByKeyNum(RedisKeyUtil.getSendEmailStartValue());
		Long num = 0l;
		if(StringUtils.isNotBlank(flag)){
			for (int i = 0; i < count; i++) {
				
				try {
					num=num +sendEmailServiceImpl.sendEmail(start);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return "start is :"+start +" send num is :"+ num;
	}
	
	/**
	 * 测试方法,用于测试
	 * 多帐号同IP是否可以发送邮件而不被限制
	 * 测试发送频域为多少合适
	 * 测试发送数目为多少合适
	 * <p>Title: sendEmailTest</p>
	 * <p>Description: </p>
	 * @param username 发送者帐号
	 * @param password	发送者密码
	 * @param to  发送给谁
	 * @param num 一次发送的数目
	 * @param frequency 大于此时间才能再次发送 默认 14400 ms 14.4s
	 * @return
	 */
	@RequestMapping("/test")
	@ResponseBody
	public String sendEmailTest(String username,String password,
			@RequestParam(defaultValue="smtp.126.com")String smtp,@RequestParam(defaultValue="false")Boolean isSSL,
			String to,@RequestParam(defaultValue="1")Integer num,
			@RequestParam(defaultValue="14400")Long frequency){
		String result="false";
		if(StringUtils.isNotBlank(username)&&StringUtils.isNotBlank(password)&&StringUtils.isNotBlank(to)){
				long testStart = System.currentTimeMillis();
				PicEmail picEmail = new PicEmail();
				picEmail.setPicUrl("http://www.2cto.com/meinv/uploads/allimg/160507/1-16050G44635-50.jpg ");
				for (int i = 0; i < num; i++) {
					//这里要防止发送太快了 14.4秒发送一封! 1小时 250封
				 try {
						long sendStart = System.currentTimeMillis();
						
						SendmailUtil sendmailUtil = new SendmailUtil(username, password, smtp, isSSL);
						sendmailUtil.doSendHtmlEmail(picEmail.getHeadName(), picEmail,to);
						long sendEnd = System.currentTimeMillis();
						frequency =frequency -(sendEnd-sendStart);
						if(frequency>0){
							Thread.sleep(frequency);
						}
					} catch (UnsupportedEncodingException |MessagingException|InterruptedException e) {
						System.out.println("occur error is : "+ e);;
					} 
				}
				long testEnd = System.currentTimeMillis();
				result ="success and cost time is : " +(testEnd -testStart)+" ms";
		}
		return result;
	}
	
}
