package org.cgz.oseye.service;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.Comments;

public interface CommentsService {

	/**
	 * 获取一条评论
	 * @param commentId
	 * @return
	 */
	public Comments loadComment(Integer commentId);
	
	/**
	 * 获取一条评论
	 * @param commentId
	 * @return
	 */
	public Comments getComment(Integer commentId);
	
	/**
	 * 添加一条评论
	 * @param comment
	 */
	public void addComment(Comments comment);
	
	/**
	 * 评论可见
	 * @param commentId
	 */
	public void visibleComment(Integer userId,Integer commentId);
	
	/**
	 * 隐藏评论
	 * @param commentId
	 */
	public void invisibleComment(Integer userId,Integer commentId);
	
	/**
	 * 删除一条评论
	 * @param commentId
	 */
	public void deleteComment(Integer userId,Integer commentId);
	
	/**
	 * 删除文章下所有的评论
	 * @param userId
	 * @param postsId
	 */
	public void deleteCommentsByPostsId(Integer userId,Integer postsId);
	
	/**
	 * 获取博客下的评论
	 * @param blogId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Comments> getBlogComments(Integer blogId,Integer pageNo,Integer pageSize);
	
	/**
	 * 获得博客下可见的评论
	 * @param blogId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Comments> getBlogVisibleComments(Integer blogId,Integer pageNo,Integer pageSize);
	
	/**
	 * 获得博客下被隐藏的评论
	 * @param blogId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Comments> getBlogInvisibleComments(Integer blogId,Integer pageNo,Integer pageSize);
	
	/**
	 * 获得文章下的评论
	 * @param postId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Comments> getPostComments(Integer postId,Integer pageNo,Integer pageSize);
	
	/**
	 * 获取我的评论
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Comments> getMyComments(Integer userId,Integer pageNo,Integer pageSize);
}
