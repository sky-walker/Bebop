package org.cgz.oseye.cache;

import java.io.Serializable;
import java.util.List;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheBase {

	private static CacheManager cacheManager = null;
	
	private static class CacheBaseHolder {
        private static CacheBase instance = new CacheBase();
    }
	
    private CacheBase() {
        cacheManager = CacheManager.create();
    }
    
    public static CacheBase getInstance() {
        return CacheBaseHolder.instance;
    }
	

	/**
	 * 按缺省配置创建缓存
	 * 
	 * @param cacheName
	 */
	public void createCache(String cacheName, boolean autoCreate) {
		cacheManager.addCache(cacheName);
	}
	
	public net.sf.ehcache.Cache getCache(String cacheName) {
		net.sf.ehcache.Cache cache = cacheManager.getCache(cacheName);
		if(cache==null) {
			cacheManager.addCache(cacheName);
			System.out.println("添加缓存:"+cacheName);
			cache= cacheManager.getCache(cacheName);
		}
		return cache;
	}
	
	/**
	 * 添加缓存
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public void put(String cacheName, Serializable key, Serializable value) {
		net.sf.ehcache.Cache cache = getCache(cacheName);
		cache.put(new Element(key, value));
		System.out.println("["+cacheName+"]的缓存个数："+CacheBase.getInstance().getSize(cacheName));
	}

	/**
	 * 根据缓存名与key获取值
	 * 
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public  Object get(String cacheName, Serializable key) {
		net.sf.ehcache.Cache cache = getCache(cacheName);
		Element e = cache.get(key);
		return e == null ? null : e.getObjectValue();
	}

	/**
	 * 获取缓存名
	 * 
	 * @return
	 */
	public String[] getCacheNames() {
		return cacheManager.getCacheNames();
	}

	/**
	 * 获取缓存的Keys
	 * 
	 * @param cacheName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getKeys(String cacheName) {
		net.sf.ehcache.Cache cache = getCache(cacheName);
		return (List<String>) cache.getKeys();
	}

	/**
	 * 清除所有
	 */
	public void clearAll() {
		cacheManager.clearAll();
	}

	/**
	 * 清空指定缓存
	 * 
	 * @param cacheName
	 */
	public void clear(String cacheName){ 
		getCache(cacheName).removeAll();
	}

	/**
	 * 删除指定对象
	 * 
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public boolean remove(String cacheName, Serializable key) {
		return getCache(cacheName).remove(key);
	}

	/**
	 * 获取缓存大小
	 * 
	 * @param cacheName
	 * @return
	 */
	public int getSize(String cacheName) {
		return getCache(cacheName).getSize();
	}
}
