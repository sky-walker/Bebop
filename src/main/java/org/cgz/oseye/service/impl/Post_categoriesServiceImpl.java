package org.cgz.oseye.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.cgz.oseye.common.*;
import org.cgz.oseye.model.Post_categories;
import org.cgz.oseye.service.Post_categoriesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** 
 * @author 陈广志 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
@Service("post_categoriesService")
@Transactional
public class Post_categoriesServiceImpl implements Post_categoriesService {

	@Resource
	private BaseDao baseDao;
	
    /**
     * 添加分类
     * @param post_category
     */
    @Override
    public void addCategory(Post_categories post_category) {
        baseDao.save(post_category);
        post_category.setSort(post_category.getId());
    }

    /**
     * 修改分类
     *
     * @param post_category
     */
    @Override
    public void modifyCategory(Post_categories post_category) {
        baseDao.update(post_category);
    }

    /**
     * 删除分类
     *
     * @param categoryId
     */
    @Override
    public void deleteCategory(Integer userId,Integer categoryId) {
    	if(baseDao.isExistedByWhere(QLBuilder.count(Post_categories.class).where(Post_categories.ID).and("users.id").setQlParams(categoryId,userId))) {
    		baseDao.delete(Post_categories.class,categoryId);
    	}
    }

    /**
     * 分类排序
     * @param currentCategoryId
     * @param sortType
     */
    @Override
    public void categorySort(Integer userId,Integer currentCategoryId, short sortType) {
    	/**
    	 * 按sort从小到大排列
    	 * 1.获取当前记录的前面一条记录
    	 *   select o from o where o.sort<currentSort  order by sort DESC
    	 * 2.后去当前记录的后一条记录
    	 * 	 select o from o where o.sort>currentSort  order by sort ASC	
    	 */
    	//获取当前的分类
    	Post_categories currentCategory = baseDao.find(Post_categories.class, currentCategoryId);
    	Integer currentSort = currentCategory.getSort();
    	//上移
    	if(sortType==SystemConstant.POST_CATEGORIES_SORT_UP) {
    		Pager<Post_categories> queryResult = baseDao.getScrollData(QLBuilder.select(Post_categories.class).where("users.id").and(Post_categories.SORT,QLCompare.LT).desc(Post_categories.SORT).setQlParams(userId,currentSort),0,1);
    		List<Post_categories> preCategories = queryResult.getResultList();
    		if(preCategories!=null && preCategories.size()>0) {
    			Post_categories preCategory = preCategories.get(0);
    			currentCategory.setSort(preCategory.getSort());
    			preCategory.setSort(currentSort);
    			baseDao.update(currentCategory);
    			baseDao.update(preCategory);
    			baseDao.flush();
    		}
    	}
    	
    	//上移
    	if(sortType==SystemConstant.POST_CATEGORIES_SORT_DOWN) {
            Pager<Post_categories> queryResult = baseDao.getScrollData(QLBuilder.select(Post_categories.class).where("users.id").and(Post_categories.SORT,QLCompare.GT).asc(Post_categories.SORT).setQlParams(userId,currentSort),0,1);
    		List<Post_categories> nextCategories = queryResult.getResultList();
    		if(nextCategories!=null && nextCategories.size()>0) {
    			Post_categories nextCategory = nextCategories.get(0);
    			currentCategory.setSort(nextCategory.getSort());
    			nextCategory.setSort(currentSort);
    			baseDao.update(currentCategory);
    			baseDao.update(nextCategory);
    			baseDao.flush();
    		}
    	}
    }

    /**
     * 判断分类名称是否存在
     *
     * @param categoryName
     * @param blogId
     * @return
     */
    @Override
    @Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
    public boolean categoryIsExist(String categoryName, Integer blogId) {
    	Post_categories category = baseDao.findByWhere(QLBuilder.select(Post_categories.class).where("blogs.id").and(Post_categories.NAME).setQlParams(blogId,categoryName.trim()));
    	if(category!=null && category.getName().equals(categoryName)) {	//解决mysql大小写敏感的问题
    		return true;
    	}
        return false;
    }

    /**
     * 获取分类实体
     * @param categoryId
     * @return
     */
    @Override
    @Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
    public Post_categories getCategory(Integer categoryId) {
        return baseDao.get(Post_categories.class,categoryId);
    }
    
    /**
     * 获取分类实体
     * @param categoryId
     * @return
     */
    @Override
    @Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
    public Post_categories findCategory(Integer categoryId) {
        return baseDao.find(Post_categories.class,categoryId);
    }
    
    /**
     * 查询分类是否属于某博客 
     * @param categoryId
     * @return
     */
    @Override
    @Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
    public boolean getCategory(Integer blogId,Integer categoryId) {
        return baseDao.isExistedByWhere(QLBuilder.count(Post_categories.class).where(Post_categories.ID).and("blogs.id").setQlParams(categoryId,blogId));
    }

    /**
     * 获取当前用户下的所有博文的分类(排序是按sort从小到大排列)
     * @param blogId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    @Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
    public Pager<Post_categories> getCategories(Integer blogId, Integer pageNo, Integer pageSize) {
        return baseDao.getScrollData(QLBuilder.select(Post_categories.class).where("blogs.id").asc(Post_categories.SORT).setQlParams(blogId),pageNo,pageSize);
    }

	@Override
	public void update(Post_categories postCategories) {
		baseDao.update(postCategories);
	}
}
