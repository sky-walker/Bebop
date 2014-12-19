package org.cgz.oseye.service.impl;

import javax.annotation.Resource;

import org.cgz.oseye.common.BaseDao;
import org.cgz.oseye.common.Pager;
import org.cgz.oseye.common.QLBuilder;
import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.Comments;
import org.cgz.oseye.model.Posts;
import org.cgz.oseye.service.CommentsService;
import org.cgz.oseye.service.FeedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("commentsService")
@Transactional
public class CommentsServiceImpl implements CommentsService {

	@Resource
	private BaseDao baseDao;
	@Resource
	private FeedService feedService;
	
	/**
	 * 获取一条评论
	 * @param commentId
	 * @return
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Comments loadComment(Integer commentId) {
		return baseDao.find(Comments.class, commentId);
	}
	
	/**
	 * 获取一条评论
	 * @param commentId
	 * @return
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Comments getComment(Integer commentId) {
		return baseDao.get(Comments.class, commentId);
	}
	
	/**
	 * 添加一条评论
	 * @param comment
	 */
	public void addComment(Comments comment) {
		Posts post = comment.getPosts();
		baseDao.save(comment);
		post.setCommentsNum(post.getCommentsNum()+1);
		baseDao.update(post);
		//评论添加的动态
		feedService.addFeed(comment);
	}
	
	/**
	 * 评论可见
	 * @param commentId
	 */
	public void visibleComment(Integer userId,Integer commentId) {
		Comments comment = baseDao.findByWhere(QLBuilder.select(Comments.class).where("id").and("blogs.users.id").setQlParams(commentId,userId));
		if(comment!=null && comment.getVisible()==SystemConstant.COMMENTS_INVISIBLE) {
			comment.setVisible(SystemConstant.COMMENTS_VISIBLE);
		}
	}
	
	/**
	 * 隐藏评论
	 * @param commentId
	 */
	public void invisibleComment(Integer userId,Integer commentId) {
		Comments comment = baseDao.findByWhere(QLBuilder.select(Comments.class).where("id").and("blogs.users.id").setQlParams(commentId,userId));
		if(comment!=null && comment.getVisible()==SystemConstant.COMMENTS_VISIBLE) {
			comment.setVisible(SystemConstant.COMMENTS_INVISIBLE);
		}
	}
	
	/**
	 * 删除一条评论
	 * @param commentId
	 */
	public void deleteComment(Integer userId,Integer commentId) {
		Comments comment = baseDao.findByWhere(QLBuilder.select(Comments.class).where("id").and("blogs.users.id").setQlParams(commentId,userId));
		comment.setDelStatus(SystemConstant.COMMENTS_DEL);
		Posts post = comment.getPosts();
		if(post.getCommentsNum()>=1) {
			int commentNum = (int) baseDao.getCountByWhere(QLBuilder.count(Comments.class).where("posts.id").and("delStatus").setQlParams(comment.getPosts().getId(),SystemConstant.COMMENTS_NOT_DEL));
			post.setCommentsNum(commentNum);
			baseDao.update(post);
			//删除对应的动态
			feedService.delFeedByObjId(userId, comment);
		}
	}
	
	/**
	 * 删除文章下所有的评论
	 * @param userId
	 * @param postsId
	 */
	public void deleteCommentsByPostsId(Integer userId,Integer postsId) {
		Posts posts = baseDao.find(Posts.class, postsId);
		if(posts!=null) {
			baseDao.deleteByWhere(QLBuilder.delete(Comments.class).where("blogs.id").and("posts.id").setQlParams(userId,postsId));
		}
	}
	
	/**
	 * 获取博客下的所有评论
	 * @param blogId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Pager<Comments> getBlogComments(Integer blogId,Integer pageNo,Integer pageSize) {
		return baseDao.getScrollData(QLBuilder.select(Comments.class).where("blogs.id").and("delStatus").asc("id").setQlParams(blogId,SystemConstant.COMMENTS_NOT_DEL), pageNo, pageSize);
	}
	
	/**
	 * 获得博客下可见的评论
	 * @param blogId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Comments> getBlogVisibleComments(Integer blogId,Integer pageNo,Integer pageSize) {
		return baseDao.getScrollData(QLBuilder.select(Comments.class).where("blogs.id").and("visible").and("delStatus").desc("id").setQlParams(blogId,SystemConstant.COMMENTS_VISIBLE,SystemConstant.COMMENTS_NOT_DEL), pageNo, pageSize);
	}
	
	/**
	 * 获得博客下被隐藏的评论
	 * @param blogId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<Comments> getBlogInvisibleComments(Integer blogId,Integer pageNo,Integer pageSize) {
		return baseDao.getScrollData(QLBuilder.select(Comments.class).where("blogs.id").and("visible").and("delStatus").desc("id").setQlParams(blogId,SystemConstant.COMMENTS_INVISIBLE,SystemConstant.COMMENTS_NOT_DEL), pageNo, pageSize);
	}
	
	/**
	 * 获取某文章下的所有评论
	 * @param blogId
	 * @param postId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Pager<Comments> getPostComments(Integer postId,Integer pageNo,Integer pageSize) {
		return baseDao.getScrollData(QLBuilder.select(Comments.class).where("posts.id").and("delStatus").desc("id").setQlParams(postId,SystemConstant.COMMENTS_NOT_DEL), pageNo, pageSize);
	}
	
	/**
	 * 获取我的评论
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Pager<Comments> getMyComments(Integer userId,Integer pageNo,Integer pageSize) {
		return baseDao.getScrollData(QLBuilder.select(Comments.class).where("users.id").and("delStatus").desc("id").setQlParams(userId,SystemConstant.COMMENTS_NOT_DEL), pageNo, pageSize);
	}
}
