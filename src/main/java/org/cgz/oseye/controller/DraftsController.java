package org.cgz.oseye.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.Blogs;
import org.cgz.oseye.model.Drafts;
import org.cgz.oseye.model.Post_categories;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.BlogsService;
import org.cgz.oseye.service.DraftsService;
import org.cgz.oseye.service.Post_categoriesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

@Controller("draftsController")
@RequestMapping("/admin")
public class DraftsController extends BaseController {

	@Resource
	private DraftsService draftsService;
	
	@Resource
	private Post_categoriesService postCategoriesService;
	@Resource
	private BlogsService blogsService;
	
	@Resource
	private PostsController postsController;
	
	/**
	 * 草稿管理页面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/drafts",method = RequestMethod.GET)
	public String toManagePage(ModelMap map) {
		Users user = getSessionUsers();
		List<Drafts> draftsList = draftsService.getDrafts(user.getId());
		map.put("pager", draftsList);
		return "admin/admin-drafts";
	}
	
	/**
	 * 草稿编辑页面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/drafts/edit",method = RequestMethod.GET)
	public String draft_edit_page(@RequestParam(value="id",required=false) Integer draftId,ModelMap map) {
		Users user = getSessionUsers();
		if(user!=null && draftId>=0) {
			Drafts draft = draftsService.getDraft(user.getId(), draftId);
			map.put("draft", draft);
			List<Post_categories> categories = postCategoriesService.getCategories(user.getId(), -1, -1).getResultList();
			map.put("categories", categories);
		}
		return "admin/admin-draft-edit";
	}
	
	/**
	 * 保存为草稿
	 */
	@RequestMapping(value="/drafts/{type}-do",method = RequestMethod.POST)
	public void draft_new_edit (@PathVariable("type") String type,
							    @RequestParam(value="id",required=false) Integer draftId,
							    @RequestParam(value="title",required=false) String title,
							    @RequestParam(value="category",required=false) Integer category,
							    @RequestParam(value="content",required=false) String content,
							    @RequestParam(value="postType",required=false) Integer postType,
							    @RequestParam(value="reLink",required=false) String reLink,
							    @RequestParam(value="musicUrl",required=false) String musicUrl,
							    @RequestParam(value="tags",required=false) String tags,
							    @RequestParam(value="visible",required=false) Integer visible,
							    @RequestParam(value="canComment",required=false) Integer canComment,
							    @RequestParam(value="asTop",required=false,defaultValue = "0") Integer asTop,
							    HttpServletResponse response,HttpServletRequest request,ModelMap map) {
		Users user = getSessionUsers();
		String errMsg = postsController.checkPostNew(HtmlUtils.htmlEscape(title), category,content,postType,reLink,visible,canComment,musicUrl,asTop);
		if(errMsg!=null) {
			returnJson(response, errMsg);
			return;
		}
		if(user!=null&&category!=null) {
			Post_categories postCategory = postCategoriesService.getCategory(category);
			if("new".equals(type)) {				//发表文章
				Drafts draft = new Drafts(postCategory, user.getBlogs(), HtmlUtils.htmlEscape(title.trim()),content,visible.shortValue(), canComment.shortValue(),postType.shortValue(),reLink,asTop.shortValue());
				draftsService.addDraft(draft);
				//更新session中的dratsNum
				Blogs blog = user.getBlogs();
				blog.setDraftsNum(blogsService.getDraftNum(user.getId()));
				user.setBlogs(blog);
				getSession().setAttribute(SystemConstant.SESSIONUSER, user);
			}
			if("edit".equals(type)&&draftId!=null) {	//修改文章
				Drafts draft = draftsService.getDraft(user.getId(), draftId);
				draft.setTitle(HtmlUtils.htmlEscape(title.trim()));
				draft.setPost_categories(postCategory);
				draft.setContent(content);
				draft.setPostType(postType.shortValue());
				draft.setCanComment(canComment.shortValue());
				draft.setVisible(visible.shortValue());
				draft.setAsTop(asTop.shortValue());
				draft.setMusicUrl(musicUrl);
				draft.setReLink(reLink);
				draftsService.modifyDraft(draft);
			}
			returnJson(response, true);
		}
	}
	
	/**
	 * 草稿的删除
	 * @param draftId
	 * @param response
	 */
	@RequestMapping(value="/drafts/del",method=RequestMethod.POST)
	public void draft_del(@RequestParam(value="id",required=false) Integer draftId,HttpServletResponse response) {
		Users user = getSessionUsers();
		if(user!=null && draftId>=0) {
			draftsService.delDraft(user.getId(), draftId);
			//更新session中的dratsNum
			Blogs blog = user.getBlogs();
			blog.setDraftsNum(blogsService.getDraftNum(user.getId()));
			user.setBlogs(blog);
			getSession().setAttribute(SystemConstant.SESSIONUSER, user);
			returnJson(response, true);
		}
	}  
}
