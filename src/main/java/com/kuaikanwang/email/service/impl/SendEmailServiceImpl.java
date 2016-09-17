package com.kuaikanwang.email.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuaikanwang.email.dao.ImageAccessMapper;
import com.kuaikanwang.email.dao.MainEmailMapper;
import com.kuaikanwang.email.domain.PicEmail;
import com.kuaikanwang.email.redis.RedisDao;
import com.kuaikanwang.email.service.SendEmailService;
import com.kuaikanwang.email.utils.email.SendmailUtil;
import com.kuaikanwang.email.utils.redis.RedisKeyUtil;
@Service
@Transactional
public class SendEmailServiceImpl implements SendEmailService,InitializingBean{
	
	private static Logger logger = LoggerFactory.getLogger(SendEmailServiceImpl.class);
	
	//测试126邮箱 当个帐号发送100个邮件是不成问题的 一共十个帐号,先保证每天发送1000封邮件吧
	//一次 limit为100 设置轮数目
	
	@Value("${email.limit}")
	private long limit;
	
	@Resource
	private MainEmailMapper mainEmailMapper;
	
	@Resource
	private ImageAccessMapper imageAccessMapper;
	
	@Resource
	private RedisDao redisDaoImpl;
	
	private List<String> usernames = new ArrayList<String>();
	private List<String> passwords = new ArrayList<String>();
	
	private ExecutorService pools = Executors.newCachedThreadPool();
	
	public Long sendEmail(long start) throws InterruptedException{//开始坐标从redis中取,默认为0---对应id
		
		Long startPic = redisDaoImpl.getValueByKeyNum(RedisKeyUtil.getSendPicStartValue());
		
		String url = imageAccessMapper.findImageByEmail(startPic);
		
		redisDaoImpl.incrValueByKey(RedisKeyUtil.getSendPicStartValue());
		
		//这里先查一下一共邮箱的数量 --计算这次发送规则,存入redis下次的起始位置
		
		Long max = mainEmailMapper.findEmailMaxNum();
		
		redisDaoImpl.setValueByKey(RedisKeyUtil.getSendEmailStartValue(), (start+limit)+"");
		
		if(max<start+limit){
			limit = max -start;
			redisDaoImpl.setValueByKey(RedisKeyUtil.getSendEmailStartValue(),"0");
		}
		
		//这里都是一个线程发了 邮箱有发送限制,一个线程就够了 唉
		CountDownLatch latch = new CountDownLatch(1);	
		
		SendEmail sendEmail = new SendEmail(start,start+limit,url,latch);
			
		pools.submit(sendEmail);
		//阻塞	
	    latch.await();
	    logger.warn("send email end ,the startPic is : " + startPic + " next send email start location is : " +(start+limit) );
		return limit;
	}
	
	/**
	 * 多线程发送邮件
	 * <p>Title: SendEmailServiceImpl.java</p>
	 * <p>Description: </p>
	 * <p>Copyright: Copyright (c) 2016</p>
	 * <p>Company: Sage</p>
	 * @author 五虎将
	 * @date 2016年9月11日下午10:03:03
	 * @version 1.0
	 */
	class SendEmail implements Runnable{

		private long start;
		
		private long limit;
		
		private String picUrl;
		
		private CountDownLatch latch ;

		public SendEmail(long start,long limit,String picUrl,CountDownLatch latch ) {
			 this.start = start;
			 this.limit = limit;
			 this.picUrl = picUrl;
			 this.latch=latch;
		}
		
		
		@Override
		public void run() {
			long timestart = System.currentTimeMillis();
			PicEmail picEmail = new PicEmail();
			SimpleDateFormat sdf = new SimpleDateFormat("-第MMdd期");
			picEmail.setHeadName("每日美图"+sdf.format(new Date()));
			picEmail.setPicUrl(picUrl);
			
			while(start<limit){
				List<String> emails = mainEmailMapper.findEmailByRandge(start);
				int i=0;
				for (String email : emails) {
					try {
						
						    //这里要防止发送太快了 14.4秒发送一封! 1小时 250封
						    long sendStart = System.currentTimeMillis();
							SendmailUtil sendmailUtil = new SendmailUtil(usernames.get(i),passwords.get(i),"smtp.126.com",false);
							i++;
							sendmailUtil.doSendHtmlEmail(picEmail.getHeadName(), picEmail,email);
							long sendEnd = System.currentTimeMillis();
							long frequency =14400 -(sendEnd-sendStart);
							if(frequency>0){
								Thread.sleep(frequency);
							}
					} catch (UnsupportedEncodingException |MessagingException |InterruptedException   e) {
						logger.error("my email is : " + usernames.get(i) +" send email occor error ,the email is "+email+"the error is : "+ e);
					}
				}
				start = start+10;
			}
		 latch.countDown();
		 long endtime = System.currentTimeMillis();	
		 logger.warn("send email is end ,the end limit is :"+limit +" and send cost time is :" + (endtime-timestart)+"ms");
		}
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//初始化邮箱帐号和密码-->后期放入数据库中
//		usernames.add("251518179@qq.com");
//		usernames.add("205483531@qq.com");
//		
//		passwords.add("bijhynwphiesbhad");
//		passwords.add("pvugjmrvdftubhej");
		
		usernames.add("wenjushuf182759@126.com");
		usernames.add("biedanq8726@126.com");
		usernames.add("layuj16960@126.com");
		usernames.add("gaohuangt692723@126.com");
		usernames.add("miaofuhs7883@126.com");
		usernames.add("qutongcaxg49137@126.com");
		usernames.add("tangdiannq059478@126.com");
		usernames.add("paaihw696794@126.com");
		usernames.add("dujindq286079@126.com");
		usernames.add("weibopd3196@126.com");
		
		passwords.add("kunfuz96537");
		passwords.add("tanxb90133");
		passwords.add("binggg86682");
		passwords.add("ziup9148");
		passwords.add("jipq42963");
		passwords.add("huavvi63910");
		passwords.add("dailv33527");
		passwords.add("yanik0334");
		passwords.add("xiaohk91432");
		passwords.add("xiaxrj97630");
	}
}
