package org.cgz.oseye.service;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.Post_categories;

/**
 * @author 陈广志 
 * @Description
 */
public interface Post_categoriesService {

    /**
     * 添加分类
     * @param post_category
     */
    public void addCategory(Post_categories post_category);

    /**
     * 修改分类
     * @param category
     */
    public void modifyCategory(Post_categories category);

    /**
     * 删除分类
     * @param categoryId
     */
    public void deleteCategory(Integer userId, Integer categoryId);

    /**
     * 分类排序
     * @param userId
     * @param currentCategoryId
     * @param sortType
     */
    public void categorySort(Integer userId, Integer currentCategoryId, short sortType);

    /**
     * 判断分类名称是否存在
     * @param categoryName
     * @param blogId
     * @return
     */
    public boolean categoryIsExist(String categoryName, Integer blogId);

    /**
     * 获取分类实体
     * @param categoryId
     * @return
     */
    public Post_categories getCategory(Integer categoryId);
    
    public Post_categories findCategory(Integer categoryId);

    /**
     * 查询分类是否属于某博客 
     * @param categoryId
     * @return
     */
    public boolean getCategory(Integer blogId, Integer categoryId);
    
    /**
     * 获取当前用户下的所有博文的分类
     * @param blogId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pager<Post_categories> getCategories(Integer blogId, Integer pageNo, Integer pageSize);
    
    
    
    /**
     * 更新
     * @param postCategories
     */
    public void update(Post_categories postCategories);
}
