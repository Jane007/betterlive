package com.kingleadsw.betterlive.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService{
	/**
	 * 获取redis连接
	 * @param jedisPool
	 * @return
	 */
	public Jedis getJedis(JedisPool jedisPool);
	/**
	 * 把redis连接放回连接池
	 * @param jedis
	 */
	public void returnResource(Jedis jedis);
	/**
	 * 把损坏的redis连接安全放回连接池
	 * @param jedis
	 */
	
	public void returnBrokenResource(Jedis jedis);
	/**
	 * 保存字符串类型
	 * @param key
	 * @param value
	 */
	public void setString(String key, String value);
	/**
	 * 获取字符串类型
	 * @param key
	 * @return
	 */
	public String getString(String key);
	/**
	 * 保存字符串类型
	 * @param key
	 * @param value
	 */
	public void setString(String key, float value);
	/**
	 * 获取字符串类型
	 * @param key
	 * @return
	 */
	public float getString_float(String key);
	/**
	 * 向list插入byte数组（靠右插入）
	 * @param key
	 * @param value
	 * @return
	 */
	public long rpushList(String key, byte[] value);
	/**
	 * 获取指定key的指定范围的list元素
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public List<byte[]> lrangeList(String key,int start,int end);
	/**
	 * 直接向redis的list中添加序列化的对象
	 * @param key
	 * @param t
	 * @return
	 */
	public <T> T pushObjectToList(String key, T t);
	/**
	 * 获取指定key的指定范围的对象list
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public <T> List<T> lrangeObjectList(String key,int start,int end);
	/**
	 * 直接向redis的String存储类型中添加序列化的list对象
	 * @param key
	 * @param list 对象集合
	 * @return
	 */
	public <T> T setList(String key, List<T> list);
	/**
	 * 获取反序列化的对象list
	 * @param key
	 * @return
	 */
	public <T> List<T> getList(String key);
	/**
	 * 直接向redis的String存储类型中添加序列化的对象
	 * @param key
	 * @param t
	 * @return
	 */
	public <T> T setObject(String key, T t);
	/**
	 * 获取反序列化的对象
	 * @param key
	 * @return
	 */
	public <T> T getObject(String key);
	/**
	 * 保存字符串类型
	 * @param key
	 * @param value byte数组
	 */
	public void setString(String key ,byte[] value);
	/**
	 * 保存字符串类型
	 * @param key
	 * @param value byte数组
	 */
	public byte[] getbyteString(String key);
	/**
	 * 设置指定秒就失效的string值
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public void setex(String key, String value,int seconds);
	/**
	 * 删除指定key的缓存
	 * @param key
	 */
	public void delKey(String key);
	/**
	 * 保存学生得分到list
	 * @param key 格式:pushId:studentId:表简称
	 * @param df
	 */
	public void lpushNum(String key ,float df);
	/**
	 * 保存学生得分到list
	 * @param key 格式:pushId:studentId:表简称
	 * @param df
	 */
	public void lpushNum(String key ,String value);
	/**
	 * 保存值到list（向右插入）
	 * @param key
	 * @param value
	 */
	public void rpush(String key,String value);
	/**
	 * 获取得分的list，获取之前先从大到小排序
	 * @param key
	 * @return
	 */
	public List<String> lrangeDFList(String key);
	/**
	 * 将哈希表 key 中的域 field 的值设为 value 。
	 * @param key
	 * @param field
	 * @param value （float类型的，最后会转为string）
	 */
	public void hset(String key, String field,float value);
	/**
	 * 将哈希表 key 中的域 field 的值设为 value 。
	 * @param key
	 * @param field
	 * @param value
	 */
	public void hset(String key, String field,String value);
	/**
	 * 获取哈希表 key 中的域 field 的值。
	 * @param key
	 * @param field
	 */
	public int hget(String key, String field);
	/**
	 * 获取哈希表 key 中的域 field 的值。
	 * @param key
	 * @param fields 一次型获取多个field的值，用,分割
	 */
	public List<String> hmget(String key, String... fields);
	/**
	 * 返回哈希表 key 中，所有的域和值(自动封装为Map)
	 * @param key
	 * @return
	 */
	public Map<String,String> hgetAllMap(String key);
	/**
	 * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
	 * @param key
	 * @param member
	 * @param score
	 */
	public void zdd(String key,String member,double score);
	/**
	 * 返回有序集 key 中，指定区间内的成员。
	 * 其中成员的位置按 score 值递增(从小到大)来排序。
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public String zrangeSortSET(String key ,long start,long stop);
	/**
	 * 返回指定区间的有序集合的箱子编码
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public List<String> zrangeSortSet(String key ,long start,long stop);
	/**
	 * 返回有序集合中的数量
	 * @param key
	 * @return
	 */
	public int getSortSet(String key);
	/**
	 * 根据参数 count 的值，移除列表中与参数 value 相等的元素。
	 *	count 的值可以是以下几种：
     *	count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
     *	count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
	 *  count = 0 : 移除表中所有与 value 相等的值。
	 * @param key
	 * @param count
	 * @param value
	 */
	public void lremListValue(String key,int count,String value);
	/**
	 * 往set里面设置值
	 * @param key
	 * @param value
	 */
	public void saddSetValue(String key ,String value);
	/**
	 * 移除并返回集合中的一个随机元素。
	 * 然后删除此key
	 * @param key
	 * @return
	 */
	public String spop(String key);
	/**
	 * 返回有序集 key 中，成员 member 的 score 值。
	 * @param key
	 * @param member
	 * @return
	 */
	public double zcore(String key ,String member);
	/**
	 * 轮换list的头和尾。并返回尾元素
	 * @param srckey 源list
	 * @param deskey 目标list
	 * @return
	 */
	public String rpoplpush(String srckey ,String deskey);
	/**
	 * 为制定的key设置有效期时间
	 * @param key 需要设置过期时间的key
	 * @param seconds 有效时间，单位：秒
	 */
	public void expireKey(String key,int seconds);
	/**
	 * 返回集合 key 中的所有成员。
	 * 不存在的 key 被视为空集合。
	 * @param key
	 * @return
	 */
	public Set<String> smembers(String key);
	/**
	 * 判断 member 元素是否集合 key 的成员。
	 * 是返回true，否返回false
	 * @param key
	 * @param member
	 * @return
	 */
	public boolean sismember(String key,String member);
	/**
	 * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
	 * @param key
	 * @param member
	 */
	public void srem(String key,String member);
	
	/**
	 * @param key 分布式锁
	 * @param timeout 等待锁释放时间
	 * @param expireTime 锁过期时间 单位 s
	 * @return
	 */
	boolean lock(String key, long timeout,int expireTime);
	
	void unLock(String key);
	
	/**
	 * 获取key的有效时间
	 * @param key
	 * @return
	 */
	long ttl(String key);
	
}
