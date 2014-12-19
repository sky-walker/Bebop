package org.cgz.oseye.common;

/** 
 * @author 陈广志 
 * @Description: 系统常量
 */
public class SystemConstant {
	
	public static final int DEFAULT_PAGESIZE = 20;

	/***-----------用户------------*/
    /**冻结**/
	public static final short USERS_FREEZING = 0;
	/**激活**/
	public static final short USERS_ACTIVE = 1;
	/**离线**/
	public static final short USERS_OFFLINE = 0;
	/**在线**/
	public static final short USERS_ONLINE = 1;
	 /**session中用户对象的key**/
	public static final String SESSIONUSER = "OSEYRUSER";
	
	public static final String CAPTCHACODE = "CAPTCHACODE";
	
	/***-----------文章分类------------*/
	
	/**分类可见**/
	public static final short POST_CATEGORIES_UNVISIBLE = 0;
	/**分类不可见**/
	public static final short POST_CATEGORIES_VISIBLE = 1;
	
    /**上移**/
    public static final short POST_CATEGORIES_SORT_UP = 1;
    /**下移**/
    public static final short POST_CATEGORIES_SORT_DOWN = 0;
    
    /***-------------文章--------------*/
    /**可见(公开)**/
    public static final short POSTS_VISIBLE = 1;
    /**不可见(隐私)**/
    public static final short POSTS_INVISIBLE = 0;
    /**置顶**/
    public static final short POSTS_AS_TOP = 1;
    /**非置顶**/
    public static final short POSTS_NOT_AS_TOP = 0;
    /**原创**/
    public static final short POSTS_TYPE_ORIGINAL = 0;
    /**转帖**/
    public static final short POSTS_TYPE_REPASTE = 1;
    /**文章不允许评论(锁定)**/
    public static final short POSTS_NO_COMMENT = 0;
    /**文章允许评论**/
    public static final short POSTS_CAN_COMMENT = 1;
    /**放入垃圾箱的标记**/
    public static final short POSTS_IS_TRASH = 1;
    /**未被放入垃圾箱**/
    public static final short POSTS_NOT_TRASH = 0;
    
    /**原创**/
    public static final short POSTS_ORIGINAL = 0;
    /**翻译**/
    public static final short POSTS_TRANSLATE = 1;
    /**转载**/
    public static final short POSTS_RELINK = 2;
    
    /***-------------评论相关--------------*/
    public static final short COMMENTS_VISIBLE = 1;
    public static final short COMMENTS_INVISIBLE = 0;
    public static final short COMMENTS_NOT_DEL = 1;
    public static final short COMMENTS_DEL = 0;
    
    
    /***-------------消息相关--------------*/
    public static final short MESSAGE_NEW = 0;
    public static final short MESSAGE_READ = 1;
    public static final short MESSAGE_NOT_DEL = 0;
    public static final short MESSAGE_DEL = 1;
    
    /***-------------聊天记录--------------*/
    public static final short TALK_NOT_DEL = 0;
    public static final short TALK_DEL = 1;
    
    /***-------------关注--------------*/
    public static final short CONCERN_NOT_FRIEND = 0;
    public static final short CONCERN_IS_FRIEND = 1;
    
    /***-------------用户动态的类型--------------*/
    public static final short FEED_TYPE_USER_ADD = 0;
    public static final short FEED_TYPE_POST_ADD = 1;
    public static final short FEED_TYPE_POST_COMMENT_ADD = 2;
    public static final short FEED_TYPE_CONCERN_ADD = 3;
    public static final short FEED_TYPE_FAVORITE_ADD = 4;
    public static final short FEED_TYPE_COMMENT_REPLY = 5;
    
    /***-------------用户动态是否属于通知类型--------------*/
    public static final short FEED_NOT_REMIND = 0;
    public static final short FEED_IS_REMIND = 1;
    
    /***-------------用户动态的阅读状态--------------*/
    public static final short FEED_STATUS_UNREAD = 0;
    public static final short FEED_STATUS_READ = 1;
    
    /***-------------收藏的状态--------------*/
    public static final short FAVORITE_OUTER_LINK = 0;//外链
    public static final short FAVORITE_INNER_LINK = 1;
    
    public static final short FAVORITE_TYPE_DEFAULT = -1;
    public static final short FAVORITE_TYPE_POSTS = 0;
    
    /***-------------用户在线状态--------------*/
    /**离线**/
    public static final short VISITS_STATUS_OFFLINE = 0;
    /**在线**/
    public static final short VISITS_STATUS_ONLINE = 1;	
    /**阅读博客**/
    public static final short VISITS_STATUS_READBLOG = 2;
    /**阅读文章**/
    public static final short VISITS_STATUS_READPOST = 3;
    /**管理博客**/
    public static final short VISITS_STATUS_ADMIN = 4;
    /**阅读消息**/
    public static final short VISITS_STATUS_ADMIN_MESSAGE = 5;
    /**搜索文章**/
    public static final short VISITS_STATUS_SEARCH_POST = 6;
    
    /***-------------用户在线状态缓存 --------------*/
    public static final String CACHE_USER_STATUS_REGION = "userStatus";
    public static final String CACHE_UNREADPRIVATEMESSAGECOUNT_REGION = "unReadPrivateMessageCount";
    public static final String CACHE_UNREADREMINDCOUNT_REGION = "unReadRemindCount";
    public static final String CACHE_USERLASTFEED_REGION = "userLastFeed";
    
    /***-------------用户隐私设置 --------------*/
    public static final short PRIVACY_FEED_HIDE = 0;
    public static final short PRIVACY_FEED_SHOW = 1;
    
    public static final short PRIVACY_BLOG_ADD_HIDE = 0;
    public static final short PRIVACY_BLOG_ADD_SHOW = 1;
    
    public static final short PRIVACY_COMMENT_HIDE = 0;
    public static final short PRIVACY_COMMENT_SHOW = 1;
    
    public static final short PRIVACY_COMMENT_REPLY_HIDE = 0;
    public static final short PRIVACY_COMMENT_REPLY_SHOW = 1;
    
    public static final short PRIVACY_CONCERN_ADD_HIDE = 0;
    public static final short PRIVACY_CONCERN_ADD_SHOW = 1;
    
    public static final short PRIVACY_USER_STATUS_HIDE = 0;
    public static final short PRIVACY_USER_STATUS_SHOW = 1;
    
    public static final short PRIVACY_BLOG_POST_FAVORITE_HIDE = 0;
    public static final short PRIVACY_BLOG_POST_FAVORITE_SHOW = 1;
    
    /***-------------访客相关设置 --------------*/
    public static final short VISITS_KEEP_STATUS = 1;
    public static final short VISITS_DEL_STATUS = 0;
    
    /***-------------登录cookie相关--------------*/
    /**保存cookie时的cookie的key**/
	public static final String COOKIENAME = "OSEYEUSERID";
	/**自定码**/
    public static final String WEBKEY = "^OSEYEWEBKEY^";
	/**cookie的有效期**/
    public static final long COOKIEMAXAGE = 60*60*24*7*2;
    
    public static final String DOMAIN = "http://bebop.gnway.cc/";
}
