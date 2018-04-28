package com.kingleadsw.betterlive.redis.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.kingleadsw.betterlive.core.util.SerializeUtil;
import com.kingleadsw.betterlive.redis.RedisService;

@Service(value = "redisService")
public class RedisServiceImpl implements RedisService {
	private static final Logger logger = Logger.getLogger(RedisServiceImpl.class);
	@Autowired
	private JedisPool jedisPool;
	
	@Override
	public Jedis getJedis(JedisPool jedisPool) {
		Jedis jedis = jedisPool.getResource();
		jedis.select(10);
		return jedis;
	}
	@Override
	public void returnResource(Jedis jedis) {
		jedisPool.returnResource(jedis);
	}

	@Override
	public void returnBrokenResource(Jedis jedis) {
		jedisPool.returnBrokenResource(jedis);
	}
	
	@Override
	public void setString(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			jedis.set(key, value);
		} catch (Exception e) {
			logger.error("调用setString key="+key+",value="+value+"时异常:", e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
	}
	
	@Override
	public String getString(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			return jedis.get(key);
		} catch (Exception e) {
			logger.error("调用getString key="+key+"时异常:", e);
			returnBrokenResource(jedis);
			return null;
		}finally {
			returnResource(jedis);
		}
	}
	@Override
	public long rpushList(String key,byte[] value) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			return jedis.rpush(key.getBytes(), value);
		} catch (Exception e) {
			logger.error("调用rpushList(保存byte数组时) key="+key+"value="+value+"时异常:", e);
			returnBrokenResource(jedis);
			return 0;
		}finally {
			returnResource(jedis);
		}
	}
	@Override
	public List<byte[]> lrangeList(String key, int start, int end) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			return jedis.lrange(key.getBytes(),start,end);
		} catch (Exception e) {
			logger.error("调用lrangeList key="+key+"start="+start+"end="+end+"时异常:", e);
			returnBrokenResource(jedis);
			return null;
		}finally {
			returnResource(jedis);
		}
	}
	@Override
	public void setString(String key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			jedis.set(key.getBytes(), value);
		} catch (Exception e) {
			logger.error("调用setString(保存byte数组时) key="+key+",value="+value+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
	}

	@Override
	public byte[] getbyteString(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			return jedis.get(key.getBytes());
		} catch (Exception e) {
			logger.error("调用getbyteString key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
			return null;
		}finally {
			returnResource(jedis);
		}
	}

	@Override
	public <T> List<T> getList(String key) {
		try {
			byte[] bytes = getbyteString(key);
			List<T> list = SerializeUtil.unserializeList(bytes);
			return list;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	@Override
	public <T> T pushObjectToList(String key, T t) {
		rpushList(key, SerializeUtil.serialize(t));
		return null;
	}
	@Override
	public <T> List<T> lrangeObjectList(String key, int start, int end) {
		List<T> tlist = null;
		List<byte[]> bytelist = lrangeList(key, start, end);
		if(null!=bytelist)
		{
			tlist = new ArrayList<T>();
			for (byte[] bs : bytelist) {
	    		@SuppressWarnings("unchecked")
				T t = (T) SerializeUtil.unserialize(bs);
	    		tlist.add(t);
			}
		}
		
		return tlist;
	}
	@Override
	public <T> T setList(String key, List<T>list) {
		setString(key, SerializeUtil.serializeList(list));
		return null;
	}
	@Override
	public <T> T setObject(String key, T t) {
		setString(key, SerializeUtil.serialize(t));
		return null;
	}
	@Override
	public <T> T getObject(String key) {
		byte[] bytes = getbyteString(key);
		if(bytes==null)
		{
			return null;
		}
		@SuppressWarnings("unchecked")
		T t = (T) SerializeUtil.unserialize(bytes);
		return t;
	}

	@Override
	public void setex(String key, String value, int seconds) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			jedis.setex(key, seconds, value);
		} catch (Exception e) {
			logger.error("调用setex key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
	}
	@Override
	public void delKey(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			jedis.del(key);
		} catch (Exception e) {
			logger.error("调用delKey key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
	}
	@Override
	public void lpushNum(String key, float df) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			jedis.lpush(key, df+"");
		} catch (Exception e) {
			logger.error("调用delKey key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
	}
	@Override
	public List<String> lrangeDFList(String key) {
		Jedis jedis = null;
		List<String> list=null;
		try {
			jedis = getJedis(jedisPool);
			list = jedis.lrange(key, 0, -1);
		} catch (Exception e) {
			logger.error("调用delKey key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
		return list;
	}
	@Override
	public void setString(String key, float value) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			jedis.set(key, value+"");
		} catch (Exception e) {
			logger.error("调用setString key="+key+",value="+value+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
	}
	@Override
	public float getString_float(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			return jedis.get(key)==null?-1:Float.valueOf(jedis.get(key));
		} catch (Exception e) {
			logger.error("调用getString key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
			return -1;
		}finally {
			returnResource(jedis);
		}
	}
	@Override
	public void hset(String key, String field, float value) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			jedis.hset(key, field, String.valueOf(value));
		} catch (Exception e) {
			logger.error("调用hset key="+key+",field="+field+",value="+value+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
	}
	@Override
	public Map<String, String> hgetAllMap(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			return jedis.hgetAll(key);
		} catch (Exception e) {
			logger.error("调用hgetAllFloatMap key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
			return null;
		}finally {
			returnResource(jedis);
		}
	}
	@Override
	public void zdd(String key, String member, double score) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			jedis.zadd(key, score, member);
		} catch (Exception e) {
			logger.error("调用zdd key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
	}
	@Override
	public String zrangeSortSET(String key, long start, long end) {
		Jedis jedis = null;
		String result =null;
		try {
			jedis = getJedis(jedisPool);
			Set<String> set = jedis.zrange(key, start, end);
			Iterator<String> interator = set.iterator();
			while(interator.hasNext())
			{
				result = interator.next();
			}
		} catch (Exception e) {
			logger.error("调用zdd key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
			result = null;
		}finally {
			returnResource(jedis);
		}
		return result;
	}
	@Override
	public int hget(String key, String field) {
		Jedis jedis = null;
		int resultcode = 0;
		try {
			jedis = getJedis(jedisPool);
			String result = jedis.hget(key, field);
			if(result!=null)
			{
				double xx = Double.valueOf(result);
				resultcode = (int)xx;
			}
		} catch (Exception e) {
			logger.error("调用hget key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
		return resultcode;
	}
	@Override
	public void hset(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			jedis.hset(key, field, value);
		} catch (Exception e) {
			logger.error("调用hset key="+key+",field="+field+",value="+value+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
	}
	@Override
	public List<String> hmget(String key, String... fields) {
		Jedis jedis = null;
		List<String> list=null;
		try {
			jedis = getJedis(jedisPool);
			list = jedis.hmget(key, fields);
		} catch (Exception e) {
			logger.error("调用hmget key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
		return list;
	}
	@Override
	public void lpushNum(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			jedis.lpush(key, value);
		} catch (Exception e) {
			logger.error("调用delKey key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
	}
	@Override
	public void rpush(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			jedis.rpush(key, value);
		} catch (Exception e) {
			logger.error("调用rpush key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
	}
	@Override
	public void lremListValue(String key, int count, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			jedis.lrem(key, count, value);
		} catch (Exception e) {
			logger.error("调用lrem key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
	}
	@Override
	public void saddSetValue(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			jedis.sadd(key, value);
		} catch (Exception e) {
			logger.error("调用sadd key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
	}
	@Override
	public String spop(String key) {
		Jedis jedis = null;
		String resultCode=null;
		try {
			jedis = getJedis(jedisPool);
			resultCode = jedis.spop(key);
			jedis.del(key);
		} catch (Exception e) {
			logger.error("调用spop key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
		return resultCode;
	}
	@Override
	public double zcore(String key, String member) {
		Jedis jedis = null;
		Double resultCode=0.0;
		try {
			jedis = getJedis(jedisPool);
			resultCode = jedis.zscore(key, member);
			if(resultCode==null)
			{
				resultCode = 0.0;
			}
		} catch (Exception e) {
			logger.error("调用spop key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
		return resultCode;
	}
	@Override
	public String rpoplpush(String srckey, String deskey) {
		Jedis jedis = null;
		String resultCode=null;
		try {
			jedis = getJedis(jedisPool);
			resultCode = jedis.rpoplpush(srckey, deskey);
		} catch (Exception e) {
			logger.error("调用rpoplpush srckey="+srckey+"deskey="+deskey+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
		return resultCode;
	}
	@Override
	public int getSortSet(String key) {
		Jedis jedis = null;
		int  result =0;
		try {
			jedis = getJedis(jedisPool);
			Set<String> set = jedis.zrange(key, 0, -1);
			result = set.size();
		} catch (Exception e) {
			logger.error("调用zdd key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
			result = 0;
		}finally {
			returnResource(jedis);
		}
		return result;
	}
	@Override
	public List<String> zrangeSortSet(String key, long start, long stop) {
		Jedis jedis = null;
		String result =null;
		List<String> list = new ArrayList<String>();
		try {
			jedis = getJedis(jedisPool);
			Set<String> set = jedis.zrange(key, start, stop);
			Iterator<String> interator = set.iterator();
			while(interator.hasNext())
			{
				result = interator.next();
				list.add(result);
			}
		} catch (Exception e) {
			logger.error("调用zdd key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
			result = null;
		}finally {
			returnResource(jedis);
		}
		return list;
	}
	@Override
	public void expireKey(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			jedis.expire(key, seconds);
		} catch (Exception e) {
			logger.error("调用expireKey key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
	}
	@Override
	public Set<String> smembers(String key) {
		Jedis jedis = null;
		Set<String> set = null;
		try {
			jedis = getJedis(jedisPool);
			set = jedis.smembers(key);
		} catch (Exception e) {
			logger.error("调用smembers key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
		return set;
	}
	@Override
	public boolean sismember(String key, String member) {
		Jedis jedis = null;
		boolean flag = false;
		try {
			jedis = getJedis(jedisPool);
			flag = jedis.sismember(key, member);
		} catch (Exception e) {
			logger.error("调用smembers key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
		return flag;
	}
	@Override
	public void srem(String key, String member) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			jedis.srem(key, member);
		} catch (Exception e) {
			logger.error("调用srem key="+key+"时异常:"+e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
	}
	
	
	/**
	 * @param key 分布式锁
	 * @param timeout 等待锁释放时间
	 * @param expireTime 锁过期时间 单位 毫秒
	 * @return
	 */
	@Override
	public boolean lock(String key, long timeout,int expireTime) {
		boolean lockSuccess = false; 	
		
		Jedis redis=this.getJedis(jedisPool);
		try{
			long start = System.currentTimeMillis(); 
			String lockKey = key;
			do{
				long result = redis.setnx(lockKey, String.valueOf(System.currentTimeMillis()+expireTime+1)); 	
				if(result == 1){
						lockSuccess = true;
						break;
					}else{
						String lockTimeStr = redis.get(lockKey);

						//如果key存在，锁存在 
						if(StringUtils.isNumeric(lockTimeStr)){
							
							long lockTime = Long.valueOf(lockTimeStr);
							
							//锁已过期	
							if(lockTime < System.currentTimeMillis()){
								
									String originStr = redis.getSet(lockKey, String.valueOf(System.currentTimeMillis()+expireTime*1000+1)); 	
									//表明锁由该线程获得
									if(StringUtils.isNotBlank(originStr)&&originStr.equals(lockTimeStr)){
										lockSuccess = true; 	
										break; 		
									 }				
							 }
						 } 		
				 }
						
		 	
				//如果不等待，则直接返回 		
				if(timeout == 0){ 	
					break; 		
			    } 			
				//等待300ms继续加锁 
				Thread.sleep(300);
			}while((System.currentTimeMillis()-start) < timeout);
			}catch(Exception e){ 
				logger.error("调用lock key="+key+",timeout="+timeout+",expireTime="+expireTime+"时异常:", e);
			 }
		 return lockSuccess; 	
     }  	
	
	/**释放锁
	 * @param key
	 */
	public void unLock(String key) { 
		
		Jedis redis=this.getJedis(jedisPool);
		try{ 	
			String lockKey = key; 		
			redis.del(lockKey); 		
		}catch(Exception e){ 	
			logger.error("调用unLock key="+key+"时异常:", e);
		}finally{ 
			
		} 
	 }
	
	/**
	 * 获取key的有效时间
	 * @param key
	 */
	@Override
	public long ttl(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis(jedisPool);
			return jedis.ttl(key);
		} catch (Exception e) {
			logger.error("调用ttl key="+key+"时异常:", e);
			returnBrokenResource(jedis);
		}finally {
			returnResource(jedis);
		}
		return 0;
	}
	
}
