package com.kuaikanwang.email.redis;


public interface RedisDao {
	public String getValueByKey(String key);
	
	public Long getValueByKeyNum(String key);
	
	public Boolean incrValueByKey(String key,Integer seconds,Integer max);
	
	public Boolean decrValueByKey(String key);
	
	public Boolean incrValueByKey(String key);
	
	public Boolean setValueByKey(String key,String value);
	
	public boolean getValueByKeyAndUpdate( String key);
	
}
