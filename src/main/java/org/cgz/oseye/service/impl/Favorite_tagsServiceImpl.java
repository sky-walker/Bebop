package org.cgz.oseye.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cgz.oseye.common.BaseDao;
import org.cgz.oseye.common.Pager;
import org.cgz.oseye.common.QLBuilder;
import org.cgz.oseye.model.Favorite_tags;
import org.cgz.oseye.service.Favorite_tagsService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("favorite_tagsService")
@Transactional
public class Favorite_tagsServiceImpl implements Favorite_tagsService{

	@Resource
	private BaseDao baseDao;
	
	/**
	 * 添加标签
	 */
	public void addFavorite_tags(Favorite_tags favorite_tags) {
		baseDao.save(favorite_tags);
	}
	
	/**
	 * 根据id查询标签
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Favorite_tags getFavorite_tags(String id) {
		return baseDao.find(Favorite_tags.class, id);
	}
	
	/**
	 * 根据标签名查询标签
	 */
	@Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
	public Favorite_tags getFavorite_tagsByName(Integer userId,String tagName) {
		return baseDao.findByWhere(QLBuilder.select(Favorite_tags.class).where("users.id").and("name").setQlParams(userId,tagName));
	}

	/**
	 * 根据收藏id删除标签
	 */
	public void delFavorite_tag(Integer favoriteId) {
		baseDao.deleteByWhere(QLBuilder.delete(Favorite_tags.class).where("favorites.id").setQlParams(favoriteId));
	}
	
	/**
	 * 根据标签名删除标签
	 */
	public void delFavorite_tag(Integer userId,String tagName) {
		baseDao.deleteByWhere(QLBuilder.delete(Favorite_tags.class).where("users.id").and("name").setQlParams(userId,tagName));
	}
	
	/**
	 * 修改标签
	 */
	public void modify(Integer userId,String tagName,String toTagName) {
		baseDao.update(QLBuilder.update(Favorite_tags.class, "name").where("users.id").and("name").setQlParams(toTagName,userId,tagName));
	}
	
	/**
	 * 查询出用户所有的收藏标签
	 */
	@Override
	public Pager<Favorite_tags> getFavorite_tagsPagert(Integer userId) {
		Session session = baseDao.getCurrentSession();
		Query query = session.createQuery("select count(o),o.name from Favorite_tags o where o.users.id=? group by o.name order by count(o) desc");
		query.setParameter(0, userId);
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.list();
		long totalRecords = resultList.size(); 
		List<Favorite_tags> fTags = new ArrayList<Favorite_tags>();
		for(Object[] objects:resultList) {
			Long count_id = (Long) objects[0];
			String name = (String) objects[1];
			Favorite_tags favorite_tags = new Favorite_tags(count_id.intValue(), name);
			fTags.add(favorite_tags);
		}
		Pager<Favorite_tags> pager = new Pager<Favorite_tags>();
		pager.setResultList(fTags);
		pager.setTotalRecords(totalRecords);
		return pager;
	}
}
