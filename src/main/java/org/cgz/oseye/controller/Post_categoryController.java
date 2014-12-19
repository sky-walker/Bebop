package org.cgz.oseye.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.Post_categories;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.Post_categoriesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

@Controller("post_categoryController")
@RequestMapping("/admin")
public class Post_categoryController extends BaseController {

	@Resource
    private Post_categoriesService postCategoriesService;
	
    /**
	 * 分类管理页面的显示
	 * @return
	 */
	@RequestMapping("/post-categories")
	public String toManagePage(ModelMap map) {
		Users users = getSessionUsers();
		Pager<Post_categories> pager = postCategoriesService.getCategories(users.getId(), -1, -1);
		map.put("pager", pager.getResultList());
		return "admin/admin-post-categories";
	}
	
	/**
	 * 添加分类
	 * @param name
	 * @param response
	 */
	@RequestMapping("/category-add")
	public void addCategory(@RequestParam(required=false) String name,HttpServletResponse response) {
		Users user = getSessionUsers();
		if(user!=null) {
			String checkResult = checkCategory(name,user);
			if(null!=checkResult) {
				returnJson(response, false, checkResult);
			}else {
				Post_categories post_category = new Post_categories();
				post_category.setName(HtmlUtils.htmlEscape(name.trim()));
				post_category.setUsers(getSessionUsers());
				post_category.setBlogs(getSessionUsers().getBlogs());
				postCategoriesService.addCategory(post_category);
				returnJson(response, true);
			}
		}
	}
	
	/**
	 * 修改分类
	 * @param id
	 * @param name
	 * @param response
	 */
	@RequestMapping(value="/category-edit",method = RequestMethod.POST)
	public void editCategory(@RequestParam(required=false) Integer id,@RequestParam(required=false) String name,HttpServletResponse response) {
		Users user = getSessionUsers();
		if(user!=null) {
			String checkResult = checkCategory(name,user);
			if(null!=checkResult) {
				returnJson(response, false, checkResult);
			}else {
				Post_categories category = postCategoriesService.getCategory(id);
				name = name.trim();
				if(null!=category && !category.getName().equals(name)) {
					category.setName(HtmlUtils.htmlEscape(name));
					postCategoriesService.modifyCategory(category);
				}
				returnJson(response, true);
			}
		}
	}
	
	
	/**
	 * 删除分类
	 * @param id
	 * @param response
	 */
	@RequestMapping("/category-del")
	public void delCategory(@RequestParam(required=false) Integer id,HttpServletResponse response) {
		Users users = getSessionUsers();
		if(id>=0) {
			Post_categories category = postCategoriesService.getCategory(id);
			if(category!=null) {
				if(category.getPostNum()>0) {
					returnJson(response, false, "此分类下有文章,无法进行删除!");
					return;
				}else if(category.getPostNum()==0) {
					postCategoriesService.deleteCategory(users.getId(),id);
					returnJson(response, true);
				}
			}
		}
	}
	
	/**
	 * 分类排序
	 * 向下移动
	 * @return
	 */
	@RequestMapping("/category-sort-down")
	public String sortCategoryDown(@RequestParam(required=false) Integer currentCategoryId) {
		if(currentCategoryId>=0) {
			postCategoriesService.categorySort(getSessionUsers().getId(), currentCategoryId, SystemConstant.POST_CATEGORIES_SORT_DOWN);
		}
		return "redirect:post-categories";
	}
	
	/**
	 * 分类排序
	 * 向上移动
	 * @return
	 */
	@RequestMapping("/category-sort-up")
	public String sortCategoryUp(@RequestParam(required=false) Integer currentCategoryId) {
		if(currentCategoryId>=0) {
			postCategoriesService.categorySort(getSessionUsers().getId(), currentCategoryId, SystemConstant.POST_CATEGORIES_SORT_UP);
		}
		return "redirect:post-categories";
	}
	
	/**
	 * 输入校验
	 * @param name
	 * @return
	 */
	public String checkCategory(String name,Users user) {
		if(null == name.trim() || "".equals(name.trim())) {
			return "分类名称不能为空!";
		}
		if(name.trim().length()>20) {
			return "分类名称长度不能大于20!";
		}
		if(postCategoriesService.categoryIsExist(name.trim(), user.getId())) {
			return "分类名称已经存在!";
		}
		return null;
	}
}
