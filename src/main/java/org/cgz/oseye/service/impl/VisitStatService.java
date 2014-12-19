package org.cgz.oseye.service.impl;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.*;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cgz.oseye.service.BlogsService;
import org.cgz.oseye.service.PostsService;
import org.cgz.oseye.utils.SpringContextUtil;

/**
 * 访问统计服务
 */
public class VisitStatService extends TimerTask {

	private final static Log log = LogFactory.getLog(VisitStatService.class);
	private static boolean start = false;
	private static VisitStatService daemon;
	private static Timer click_timer;
	private final static long INTERVAL = 500 * 1000;
	
	/**
	 * 支持统计的对象类型
	 */
	private final static byte[] TYPES = new byte[]{
		0x01,0x02,0x03,0x04,0x05
	};
	
	//内存队列
	private final static ConcurrentHashMap<Byte, ConcurrentHashMap<Serializable, Integer>> queues = 
		new ConcurrentHashMap<Byte, ConcurrentHashMap<Serializable, Integer>>(){
			private static final long serialVersionUID = 6711491983782899072L;
		{
			for(byte type : TYPES)
				put(type, new ConcurrentHashMap<Serializable, Integer>());
		}};

	/**
	 * 记录访问统计
	 * @param type
	 * @param obj_id
	 */
	public static void record(byte type, Serializable obj_id) {
		ConcurrentHashMap<Serializable, Integer> queue = queues.get(type);
		if(queue != null){
			Integer nCount = queue.get(obj_id);
			nCount = (nCount==null)?1:nCount+1;
			queue.put(obj_id, nCount.intValue());
			System.out.printf("record (type=%d,id=%d,count=%d)\n",type,obj_id,nCount);
		}
	}

	/**
	 * 启动统计数据写入定时器
	 * @param ctx
	 */
	public static void start() {
		if(!start){
			daemon = new VisitStatService();
			click_timer = new Timer("VisitStatService", true);
			click_timer.schedule(daemon, INTERVAL, INTERVAL);//运行间隔1分钟
			start = true;
		}
		log.info("VisitStatService started.");
	}

	/**
	 * 释放Service
	 */
	public static void destroy(){
		if(start){
			click_timer.cancel();
			start = false;
		}
		log.info("VisitStatService stopped.");
	}
	
	@Override
	public void run() {
		for(byte type : TYPES){
			ConcurrentHashMap<Serializable, Integer> queue = queues.remove(type);
			queues.put(type, new ConcurrentHashMap<Serializable, Integer>());
			try{
				_flush(type, queue);
			}catch(Throwable t){
				t.printStackTrace();
				log.fatal("Failed to flush click stat data.", t);
				//此处发送异常报告
			}finally{
				//此处关闭数据库连接
			}
		}
	}
	
	@Override
	public boolean cancel() {
		boolean b = super.cancel();
		//写回剩余数据，Tomcat停止时不会丢失数据
		this.run();
		return b;
	}
	
	/**
	 * 写访问统计数据到数据库
	 * @param type
	 * @param queue
	 */
	private void _flush(byte type, ConcurrentHashMap<Serializable, Integer> queue){
		if(queue.size()==0) {
			return ;
		}
		switch (type) {
			case 1:
				PostsService postsService = (PostsService) SpringContextUtil.getBean("postsService");
				postsService.updateViewsCount(queue);
				break;
			case 2:
				BlogsService blogsService = (BlogsService) SpringContextUtil.getBean("blogsService");
				blogsService.updateViewsCount(queue);
				break;
			default:
				break;
		}
		System.out.printf("Flush to database: type=%d\n", type);
	}
}