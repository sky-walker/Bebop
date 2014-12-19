package org.cgz.oseye.controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.Favorite_tags;
import org.cgz.oseye.model.Favorites;
import org.cgz.oseye.model.Posts;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.Favorite_tagsService;
import org.cgz.oseye.service.FavoritesService;
import org.cgz.oseye.service.PostsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

@Controller("favoritesController")
public class FavoritesController extends BaseController{

	@Resource
	private FavoritesService favoritesService;
	@Resource
	private Favorite_tagsService favorite_tagsService;
	@Resource
	private PostsService postsService;
	
	/**
	 * 收藏管理页面[前台页面需要进行tag url的编码处理]
	 * @param page
	 * @param tag
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/admin/favorites",method=RequestMethod.GET)
	public String toFavoritesManagePage(@RequestParam(value="page",required=false) Integer page,@RequestParam(value="tag",required=false) String tag,ModelMap map) {
		Users user = getSessionUsers();
		Pager<Favorites> pager = new Pager<Favorites>(); 
		if(tag!=null && !"".equals(tag.trim())) {
//			try {
//				//clound foundry 中文参数编码问题
//				tag = new String(tag.getBytes("ISO-8859-1"),"UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			} 
			pager = favoritesService.getFavoritesPagerByTag(user.getId(), tag, (page==null||page<0)?1:page, pager.getPageSize());
			map.put("tag", tag);
			pager.addQueryBy("tag", tag);
		}else {
			pager =	favoritesService.getFavoritesPager(user.getId(),(page==null||page<0)?1:page, pager.getPageSize());
		}
		Pager<Favorite_tags> tagsPager = new Pager<Favorite_tags>();
		tagsPager =	favorite_tagsService.getFavorite_tagsPagert(user.getId());
		map.put("tagsPager", tagsPager);
		map.put("pager", pager);
		return "/admin/admin-favorites";
	}
	
	/**
	 * 获取标签
	 * @param page
	 * @param tag
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/admin/favorite/tags",method=RequestMethod.GET)
	public String toFavoritesTagsManagePage(ModelMap map) {
		Users user = getSessionUsers();
		Pager<Favorite_tags> tagsPager = new Pager<Favorite_tags>();
		tagsPager =	favorite_tagsService.getFavorite_tagsPagert(user.getId());
		map.put("tagsPager", tagsPager);
		return "/admin/admin-favorite-tags";
	}
	
	/**
	 * 添加外链
	 * @param title
	 * @param url
	 * @param tags
	 * @param map
	 * @param response
	 */
	@RequestMapping(value="/favorites/add",method=RequestMethod.POST)
	public void addFavorite(@RequestParam(value="title",required=false) String title,@RequestParam(value="url",required=false) String url,@RequestParam(value="tags",required=false) String tags,ModelMap map,HttpServletResponse response) {
		Users user = getSessionUsers();
		//判断用户是否已经登录
		if(user==null) {
			returnJson(response, false, "您尚未登录,请先登录!");
			return;
		}
		//校验标题 url是否合法
		if(!checkFavorite(user,title, url,response)) {
			return;
		}
		Favorites favorites = new Favorites();
		favorites.setTitle(HtmlUtils.htmlEscape(title));
		favorites.setUrl(url);
		favorites.setUsers(user);
		Set<Favorite_tags> favorite_tags = new HashSet<Favorite_tags>();
		Set<String> tagsStr = checkTags(tags, response);
		if(tagsStr!=null) {
			Iterator<String> iter = tagsStr.iterator();
			while(iter.hasNext()) {
				Favorite_tags favorite_tag = new Favorite_tags(iter.next(), user, favorites);
				favorite_tags.add(favorite_tag);
			}
		}
		if(favoritesService.checkUrlIsExist(user.getId(), url)) {
			returnJson(response, false, "您收藏的链接已经存在!");
			return;
		}
		favorites.setFavorite_tags(favorite_tags);
		favoritesService.addFavorites(favorites);
		returnJson(response, true);
	}
	
	
	/**
	 * 修改收藏信息
	 * @param id
	 * @param title
	 * @param url
	 * @param tags
	 * @param map
	 * @param response
	 */
	@RequestMapping(value="/favorites/edit",method=RequestMethod.POST)
	public void editFavorite(@RequestParam(value="id",required=false) Integer id,@RequestParam(value="title",required=false) String title,@RequestParam(value="url",required=false) String url,@RequestParam(value="tags",required=false) String tags,ModelMap map,HttpServletResponse response) {
		Users user = getSessionUsers();
		//判断用户是否已经登录
		if(user==null) {
			returnJson(response, false, "您尚未登录,请先登录!");
			return;
		}
		Set<Favorite_tags> favorite_tags = new HashSet<Favorite_tags>();
		//标签校验
		Set<String> tagsStr = checkTags(tags, response);
		Favorites favorites = favoritesService.getFavorites(id);
		Favorites oldFavorites = new Favorites(favorites.getId(), favorites.getTitle(), favorites.getUrl(), favorites.getIsOuterLink(), favorites.getFavorite_tags());
		if(favorites!=null && favorites.getUsers().getId().intValue()==user.getId().intValue()) {
			//外链
			if(favorites.getIsOuterLink()==SystemConstant.FAVORITE_OUTER_LINK) {
				//标题和url校验
				if(!checkFavorite(user,title, url,response)) {
					return;
				}
				favorites.setTitle(HtmlUtils.htmlEscape(title));
				favorites.setUrl(url);
			}
			if(tagsStr!=null) {
				Iterator<String> iter = tagsStr.iterator();
				while(iter.hasNext()) {
					Favorite_tags favorite_tag = new Favorite_tags(iter.next(), user, favorites);
					favorite_tags.add(favorite_tag);
				}
				favorites.setFavorite_tags(favorite_tags);
			}
			short modifyNo = checkIsNeedToModify(favorites, oldFavorites,tagsStr);
			//只需要更新收藏
			if(modifyNo==1) {
				favoritesService.modifyFavoritesIgnoreTags(favorites);
			}
			//需要更新收藏和标签
			if(modifyNo==2) {
				favoritesService.modifyFavorites(favorites);
			}
			returnJson(response, true);
		}
	}
	
	/**
	 * 删除收藏
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/admin/favorites/del",method=RequestMethod.POST)
	public void delFavorite(@RequestParam(value="id",required=false) Integer id,HttpServletResponse response) {
		Users user = getSessionUsers();
		if(user!=null) {
			Favorites favorites = favoritesService.getFavorites(id);
			if(user.getId().intValue()==favorites.getUsers().getId().intValue()) {
				favoritesService.delFavorites(id);
				returnJson(response, true);
			}
		}
	}
	
	/**
	 * 删除标签
	 * @param tag
	 * @return
	 */
	@RequestMapping(value="/admin/favorite_tags/del",method=RequestMethod.POST)
	public void delFavorite_tag(@RequestParam(value="tag",required=false) String tag,HttpServletResponse response) {
		Users user = getSessionUsers();
		if(user!=null) {
			favorite_tagsService.delFavorite_tag(user.getId(), tag.trim());
			returnJson(response, true);
		}
	}
	
	/**
	 * 修改标签名称
	 * @param tag
	 * @param totag
	 * @param response
	 */
	@RequestMapping(value="/admin/favorite_tags/save",method=RequestMethod.POST)
	public void saveFavorite_tag(@RequestParam(value="tag",required=false) String tag,@RequestParam(value="totag",required=false) String totag,HttpServletResponse response) {
		Users user = getSessionUsers();
		if(user!=null && null!=totag && !"".equals(totag.trim()) && totag.trim().length()<=15) {
			favorite_tagsService.modify(user.getId(), tag.trim(), totag.trim());
			returnJson(response, true);
		}
	}
	
	/**
	 * 获取用户的索引收藏标签
	 * @param map
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/favorite_tags/list",method=RequestMethod.POST)
	public String listFavorite_tags(ModelMap map,HttpServletResponse response) {
		Users user = getSessionUsers();
		if(user!=null) {
			Pager<Favorite_tags> tagsPager = new Pager<Favorite_tags>();
			tagsPager =	favorite_tagsService.getFavorite_tagsPagert(user.getId());
			map.put("tagsPager", tagsPager);
		}
		return "admin/admin-favorite_tags";
	}
	
	/**
	 * jbox添加收藏显示
	 * @param map
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/favorite/add",method=RequestMethod.POST)
	public String addFavorite(@RequestParam(value="type",required=false) Integer type,@RequestParam(value="id",required=false) Integer id,ModelMap map,HttpServletResponse response,HttpServletRequest request) {
		map.put("id", id);
		map.put("type", type);
		return "share/ajax-favorite_tags-add";
	}
	
	/**
	 * jbox添加收藏处理
	 * @param map
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/favorite/adddo",method=RequestMethod.POST)
	public void addFavoriteDo(@RequestParam(value="type",required=false) short type,@RequestParam(value="id",required=false) Integer id,@RequestParam(value="tags",required=false) String tags,ModelMap map,HttpServletResponse response,HttpServletRequest request) {
		Users user = getSessionUsers();
		if(user==null) {
			returnJson(response, false, "您尚未登录,请先登录!");
			return;
		}
		Favorites favorites = new Favorites();
		Set<Favorite_tags> favorite_tags = new HashSet<Favorite_tags>();
		String title = "";
		String url = "";
		//收藏的是博客文章
		if(type==SystemConstant.FAVORITE_TYPE_POSTS) {
			Posts posts;
			try {
				posts = postsService.getPost(id);
				if(posts!=null) {
					title = posts.getTitle();
					url = "/blog/"+posts.getBlogs().getName()+"/"+posts.getId();
					favorites.setPosts(posts);
					if(favoritesService.checkUrlIsExist(user.getId(), url)) {
						returnJson(response, false, "您收藏的链接已经存在!");
						return;
					}
					Set<String> tagsStr = checkTags(tags, response);
					if(tagsStr!=null) {
						Iterator<String> iter = tagsStr.iterator();
						while(iter.hasNext()) {
							Favorite_tags favorite_tag = new Favorite_tags(iter.next(), user, favorites);
							favorite_tags.add(favorite_tag);
						}
					}
					favorites.setTitle(HtmlUtils.htmlEscape(title));
					favorites.setUrl(url);
					favorites.setType(type);
					favorites.setIsOuterLink(SystemConstant.FAVORITE_INNER_LINK);
					favorites.setUsers(user);
					favorites.setFavorite_tags(favorite_tags);
					favoritesService.addFavorites(favorites);
					returnJson(response, true);
				}
			} catch (Exception e) {
				returnJson(response, false, "收藏操作失败!");
				return;
			}
		}
	}
	
	
	/**
	 * 校验收藏信息
	 * @param title
	 * @param url
	 * @param tags
	 * @param response
	 * @return
	 */
	private boolean checkFavorite(Users user,String title,String url,HttpServletResponse response) {
		if(title==null || "".equals(title.trim())) {
			returnJson(response, false, "标题不能为空!");
			return false;
		}
		StringBuffer sbReg = new StringBuffer();
		sbReg.append("^((https|http|ftp|rtsp|mms)://)(([0-9a-z_!~*'().&=+$%-]+: )?")
			 .append("[0-9a-z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([0-9a-z_!~*'()-]+\\.)")
			 .append("*([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\.[a-z]{2,6})(:[0-9]{1,4})?((/?)|(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$");
		Pattern p = Pattern.compile(sbReg.toString(),Pattern.CASE_INSENSITIVE );
		Matcher matcher = p.matcher(url);
		if(!matcher.find()) {
			returnJson(response, false, "请输入正确的url地址!");
			return false;
		}
		return true;
	}
	
	/**
	 * 标签输入校验
	 * @param tags
	 * @param favorite_tags
	 * @param response
	 * @return
	 */
	private Set<String> checkTags(String tags,HttpServletResponse response) {
		if(tags!=null && !"".equals(tags.trim())) {
			Set<String> tagsStr = new TreeSet<String>();
			String[] tagsArr = tags.split(" ");
			for(String _tag:tagsArr) {
				//标签文字长度不能大于15个
				if(_tag.trim().length()>15) {
					returnJson(response, false, "标签长度不能大于15个字符!");
					return null;
				}
				//过滤重复的标签
				tagsStr.add(_tag);
			}
			//表签数不能大于5个
			if(tagsStr.size()>5) {
				returnJson(response, false, "收藏标签不能多于5个!");
				return null;
			}
			return tagsStr;
		}
		return null;
	}
	
	/**
	 * 检查是否需要进行修改操作[0:不需要修改;1:只需要修改url或title;2:全部需要修改]
	 * @param favorites
	 * @param oldFavorites
	 * @return
	 */
	private short checkIsNeedToModify(Favorites favorites,Favorites oldFavorites,Set<String> tagsStr) {
		short modifyNo = 0;
		//外部链接 需要判断title url
		if(oldFavorites.getIsOuterLink()==SystemConstant.FAVORITE_OUTER_LINK) {
			//id title url只要有一个不等 就需要修改
			if(favorites.getId()!=oldFavorites.getId() ||!favorites.getTitle().trim().equals(oldFavorites.getTitle().trim()) || !favorites.getUrl().trim().equals(oldFavorites.getUrl().trim())) {
				modifyNo = 1;
			}
		}
		//标签数量不一样 肯定需要修改
		if(favorites.getFavorite_tags().size()!=oldFavorites.getFavorite_tags().size()) {
			modifyNo = 2;
		//标签数量一样,只要有一个不一样 就需要修改
		}else if(tagsStr!=null) {
			Set<Favorite_tags> oldFavorite_tags = oldFavorites.getFavorite_tags();
			Set<String> oldTagsStr = new HashSet<String>();
			for(Favorite_tags oldFavorite_tag:oldFavorite_tags) {
				oldTagsStr.add(oldFavorite_tag.getName());
			}
			for(String tagStr:tagsStr) {
				if(!oldTagsStr.contains(tagStr)) {
					modifyNo = 2;
				}
			}
		}
		return modifyNo;
	}
}
