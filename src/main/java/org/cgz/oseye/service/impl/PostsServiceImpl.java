package org.cgz.oseye.service.impl;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.cgz.oseye.common.*;
import org.cgz.oseye.model.Blogs;
import org.cgz.oseye.model.Post_categories;
import org.cgz.oseye.model.Posts;
import org.cgz.oseye.service.BlogsService;
import org.cgz.oseye.service.CommentsService;
import org.cgz.oseye.service.FeedService;
import org.cgz.oseye.service.PostsService;
import org.cgz.oseye.service.Post_categoriesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("postsService")
@Transactional
public class PostsServiceImpl implements PostsService {

	@Resource
	private BaseDao baseDao;
	@Resource
	private Post_categoriesService postCategoriesService;
	@Resource
	private BlogsService blogsService;
	@Resource
	private FeedService feedService;
	@Resource
	private CommentsService commentsService;
	
	/**
	 * 添加文章
	 */
	@Override
	public void addPost(Posts post) {
		baseDao.save(post);
		Post_categories category = post.getPost_categories();
		Blogs blog = post.getBlogs();
		category.setPostNum(category.getPostNum()+1);//更新博客分类的文章数
		blog.setPostNum(blog.getPostNum()+1);
		postCategoriesService.update(category);
		blogsService.update(blog);
		feedService.addFeed(post);
	}
	
	/**
	 * 更新文章
	 */
	@Override
	public void modifyPost(Integer userId,Posts post) {
		baseDao.clear();
		Posts oldPost = baseDao.get(Posts.class, post.getId());
		if(oldPost!=null) {
			Post_categories oldCategory = baseDao.get(Post_categories.class, oldPost.getPost_categories().getId());
			Post_categories nowCategory = baseDao.get(Post_categories.class, post.getPost_categories().getId());
			if(!oldCategory.getId().equals(nowCategory.getId())) {	//如果更新了文章的分类,需要更新分类下的文章数
				Integer oldPostsNum = oldCategory.getPostNum();
				Integer nowPostsNum = nowCategory.getPostNum();
				if(oldPostsNum >= 1) {
					oldCategory.setPostNum(oldPostsNum-1);
					nowCategory.setPostNum(nowPostsNum+1);
					postCategoriesService.update(oldCategory);
					postCategoriesService.update(nowCategory);
					baseDao.flush();
				}
			}
		}
		baseDao.clear();
		baseDao.update(post);
	}
	
	/**
     * 批量更新阅读数
     * @param queue
     */
	public void updateViewsCount(ConcurrentHashMap<Serializable, Integer> queue) {
		for(Serializable key : queue.keySet()) {
			Posts post = this.getPost((Integer)key);
			if(post!=null) {
				baseDao.update(QLBuilder.update(Posts.class,"viewsNum").where("id").setQlParams(post.getViewsNum()+queue.get(key),key));
			}
		}
	}
	
	/**
	 * 删除文章
	 */
	@Override
	public void deletePost(Integer userId, Integer postId) {
		Posts post = getPostByWhere(userId,postId);
		Post_categories category = post.getPost_categories();
		Blogs blog = post.getBlogs();
		if(category.getPostNum()>=1) {
			category.setPostNum((category.getPostNum()-1));
			blog.setPostNum(blog.getPostNum()-1);
		}
		//删除所有和文章有关的动态(包括发表文章和文章下所有评论所产生的动态)
		feedService.delFeedByObjId(userId,post);
		commentsService.deleteCommentsByPostsId(userId, postId);
		baseDao.delete(Posts.class, postId);
		baseDao.update(category);
		baseDao.update(blog);
	}

	/**
	 * 根据id取得文章
	 */
	@Override
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Posts getPost(Integer postId) {
		return baseDao.find(Posts.class, postId);
	}
	
	/**
	 * 根据id和userId取得文章
	 */
	@Override
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Posts getPost(Integer userId,Integer postId) {
		return baseDao.findByWhere(QLBuilder.select(Posts.class).where(Posts.ID).and("blogs.id").setQlParams(postId,userId));
	}
	
	
	@Override
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Posts getPost(Integer postId, String visitorIp) {
		return null;
	}
	
	
	/**
	 * 获取博客下所有的文章
	 */
	@Override
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Pager<Posts> getPosts(Integer blogId, int pageNo,int pageSize) {
		return baseDao.getScrollData(QLBuilder.select(Posts.class).where("blogs.id").desc(Posts.ASTOP).desc(Posts.ID).setQlParams(blogId), pageNo, pageSize);
	}
	
	/**
	 * 按分类取得文章
	 */
	@Override
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Pager<Posts> getPostsByCategory(Integer categoryId,int pageNo, int pageSize) {
		return baseDao.getScrollData(QLBuilder.select(Posts.class).where(Posts.POST_CATEGORIES+".id").desc(Posts.ASTOP).desc(Posts.ID).setQlParams(categoryId), pageNo, pageSize);
	}
	
	/**
	 * 根据评论数查询出文章
	 */
	@Override
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Pager<Posts> getPostsByCommentsNumSort(Integer blogId,int pageNo, int pageSize) {
		return baseDao.getScrollData(QLBuilder.select(Posts.class).where("blogs.id").desc(Posts.COMMENTSNUM).setQlParams(blogId), pageNo, pageSize);
	}
	
	/**
	 * 根据阅读数查询出文章
	 */
	@Override
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Pager<Posts> getPostsByReadNumSort(Integer blogId,int pageNo, int pageSize) {
		return baseDao.getScrollData(QLBuilder.select(Posts.class).where("blogs.id").desc(Posts.VIEWSNUM).setQlParams(blogId), pageNo, pageSize);
	}
	
	/**
	 * 查询出所有回收站中的文章
	 */
	@Override
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Pager<Posts> getTrashPosts(Integer blogId, int pageNo,int pageSize) {
		return baseDao.getScrollData(QLBuilder.select(Posts.class).where("blogs.id").and(Posts.ASTRASH).setQlParams(blogId,SystemConstant.POSTS_IS_TRASH), pageNo, pageSize);
	}
	
	/**
	 * 锁定文章 不给评论
	 */
	@Override
	public void lockPost(Integer userId,Integer postId) {
		Posts post = getPostByWhere(userId, postId);
		if(post!=null && post.getCanComment()==SystemConstant.POSTS_CAN_COMMENT) {
			post.setCanComment(SystemConstant.POSTS_NO_COMMENT);
			baseDao.update(post);
		}
	}

	/**
	 * 解除文章锁定
	 */
	@Override
	public void unLockPost(Integer userId,Integer postId) {
		Posts post = getPostByWhere(userId, postId);
		if(post!=null && post.getCanComment()==SystemConstant.POSTS_NO_COMMENT) {
			post.setCanComment(SystemConstant.POSTS_CAN_COMMENT);
			baseDao.update(post);
		}
	}
	
	
	/**
	 * 置顶文章
	 */
	@Override
	public void topPost(Integer userId,Integer postId) {
		Posts post = getPostByWhere(userId, postId);
		if(post!=null && post.getAsTop()==SystemConstant.POSTS_NOT_AS_TOP) {
			post.setAsTop(SystemConstant.POSTS_AS_TOP);
			baseDao.update(post);
		}
	}
	
	/**
	 * 取消文章置顶
	 */
	@Override
	public void unTopPost(Integer userId,Integer postId) {
		Posts post = getPostByWhere(userId, postId);
		if(post!=null && post.getAsTop()==SystemConstant.POSTS_AS_TOP) {
			post.setAsTop(SystemConstant.POSTS_NOT_AS_TOP);
			baseDao.update(post);
		}
	}
	
	/**
	 * 文章可见
	 */
	@Override
	public void visiblePost(Integer userId,Integer postId) {
		Posts post = getPostByWhere(userId, postId);
		if(post!=null && post.getVisible()==SystemConstant.POSTS_VISIBLE) {
			post.setVisible(SystemConstant.POSTS_INVISIBLE);
			baseDao.update(post);
		}
	}
	
	/**
	 * 文章不可见
	 */
	@Override
	public void unvisiblePost(Integer userId,Integer postId) {
		Posts post = getPostByWhere(userId, postId);
		if(post!=null && post.getVisible()==SystemConstant.POSTS_INVISIBLE) {
			post.setVisible(SystemConstant.POSTS_VISIBLE);
			baseDao.update(post);
		}
	}
	
	/**
	 * 根据用户id和文章id查询出文章
	 * @param userId
	 * @param postId
	 * @return
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	private Posts getPostByWhere(Integer userId,Integer postId) {
		return baseDao.findByWhere(QLBuilder.select(Posts.class).where(Posts.ID).and("blogs.id").setQlParams(postId,userId));
	}
	
	public void update(Posts post) {
		baseDao.update(post);
	}
}
