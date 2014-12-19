package org.cgz.oseye.service;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.Posts;


public interface PostsService {

	/**
     * 添加博文
     * @param post
     */
    public void addPost(Posts post);


    /**
     * 修改博文
     * @param post
     */
    public void modifyPost(Integer userId, Posts post);

    /**
     * 批量修改阅读数
     * @param queue
     */
    public void updateViewsCount(ConcurrentHashMap<Serializable, Integer> queue);
    
    /**
     * 删除博文
     * @param postId
     */
    public void deletePost(Integer userId, Integer postId);

    /**
     * 查询出博客下所有的博文
     * @param blogId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pager<Posts> getPosts(Integer blogId, int pageNo, int pageSize);

    
    /**
     * 查询出所有垃圾箱中的文章
     * @param blogId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pager<Posts> getTrashPosts(Integer blogId, int pageNo, int pageSize);
    
    /**
     * 根据ID查询出博文
     * @param postId
     * @return
     */
     public Posts getPost(Integer postId);
     
     /**
 	 * 根据id取得文章
 	 */
 	public Posts getPost(Integer userId, Integer postId);
    
    /**
     * 根据ID查询出博文,并添加阅读数
     * @param postId
     * @param visitorIp
     * @return
     */
    public Posts getPost(Integer postId, String visitorIp);


    /**
     * 根据分类查询出博文
     * @param categoryId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pager<Posts> getPostsByCategory(Integer categoryId, int pageNo, int pageSize);
    
    /**
     * 根据评论数查询出文章
     * @param blogId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pager<Posts> getPostsByCommentsNumSort(Integer blogId, int pageNo, int pageSize);
    
    /**
     * 根据阅读数来查询出文章
     * @param blogId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pager<Posts> getPostsByReadNumSort(Integer blogId, int pageNo, int pageSize);

    /**
     * 文章置顶
     * @param post
     */
    public void topPost(Integer userId,Integer postId);
    
    /**
     * 取消文章置顶
     * @param post
     */
    public void unTopPost(Integer userId,Integer postId);
    
    /**
     * 锁定文章
     * @param post
     */
    public void lockPost(Integer userId,Integer postId);
    
    /**
     * 解锁文章
     * @param post
     */
    public void unLockPost(Integer userId,Integer postId);
    
    /**
	 * 文章可见
	 */
	public void visiblePost(Integer userId,Integer postId);
	
	/**
	 * 文章不可见
	 */
	public void unvisiblePost(Integer userId,Integer postId);
    
	public void update(Posts post);
}
