package org.cgz.oseye.service.impl;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import org.cgz.oseye.common.BaseDao;
import org.cgz.oseye.common.Pager;
import org.cgz.oseye.common.QLBuilder;
import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.Favorite_tags;
import org.cgz.oseye.model.Favorites;
import org.cgz.oseye.service.Favorite_tagsService;
import org.cgz.oseye.service.FavoritesService;
import org.cgz.oseye.service.FeedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("favoritesService")
@Transactional
public class FavoritesServiceImpl implements FavoritesService{

	@Resource
	private BaseDao baseDao;
	@Resource
	private Favorite_tagsService favorite_tagsService;
	@Resource
	private FeedService feedService;
	
	/**
	 * 添加收藏
	 */
	@Override
	public void addFavorites(Favorites favorites) {
		baseDao.save(favorites);
		addFavorite_tagsBatch(favorites);
		if(favorites.getIsOuterLink()==SystemConstant.FAVORITE_INNER_LINK) {
			feedService.addFeed(favorites);
		}
	}
	
	/**
	 * 删除收藏,同时删除其标签
	 */
	@Override
	public void delFavorites(Integer id) {
		favorite_tagsService.delFavorite_tag(id);
		baseDao.delete(Favorites.class, id);
	}
	
	/**
	 * 修改收藏
	 */
	@Override
	public void modifyFavorites(Favorites favorites) {
		favorite_tagsService.delFavorite_tag(favorites.getId());
		addFavorite_tagsBatch(favorites);
		baseDao.update(favorites);
	}
	
	/**
	 * 修改收藏(忽略标签的修改)
	 */
	@Override
	public void modifyFavoritesIgnoreTags(Favorites favorites) {
		baseDao.update(favorites);
	}
	
	/**
	 * 通过id获取收藏
	 */
	@Override
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Favorites getFavorites(Integer id) {
		return baseDao.get(Favorites.class, id);
	}
	
	/**
	 * 通过id获取收藏
	 */
	@Override
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Favorites loadFavorites(Integer id) {
		return baseDao.find(Favorites.class, id);
	}
	
	/**
	 * 分页
	 */
	@Override
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Pager<Favorites> getFavoritesPager(Integer userId,Integer pageNo,Integer pageSize) {
		return baseDao.getScrollData(QLBuilder.select(Favorites.class).where("users.id").desc("id").setQlParams(userId), pageNo, pageSize);
	}
	
	/**
	 * 根据tag查询出收藏
	 * @param userId
	 * @param tag
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Pager<Favorites> getFavoritesPagerByTag(Integer userId,String tag,Integer pageNo,Integer pageSize) {
		return baseDao.getScrollData(QLBuilder.queryByHql("select fa from Favorites fa where fa.id in(select ft.favorites.id from Favorite_tags ft where ft.users.id=? and ft.name=?) order by fa.id desc").setQlParams(userId,tag), pageNo, pageSize);
	}
	
	/**
	 * 查询链接是否已经存在
	 * @param userId
	 * @param url
	 * @return
	 */
	public boolean checkUrlIsExist(Integer userId,String url) {
		return baseDao.isExistedByWhere(QLBuilder.count(Favorites.class).where("users.id").and("url").setQlParams(userId,url));
	}
	
	/**
	 * 批量添加收藏标签
	 * @param favorites
	 */
	private void addFavorite_tagsBatch(Favorites favorites) {
		Set<Favorite_tags> fTags = favorites.getFavorite_tags();
		if(fTags!=null && fTags.size()>0) {
			Iterator<Favorite_tags> iter = fTags.iterator();
			while (iter.hasNext()) {
				Favorite_tags favorite_tags = iter.next();
				if(!"".equals(favorite_tags.getName().trim())) {
					favorite_tags.setFavorites(favorites);
					//favorite_tagsService.addFavorite_tags(favorite_tags);
					baseDao.save(favorite_tags);
				}
			}
		}
	}
}
