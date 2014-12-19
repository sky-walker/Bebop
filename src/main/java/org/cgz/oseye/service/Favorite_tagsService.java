package org.cgz.oseye.service;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.Favorite_tags;

public interface Favorite_tagsService {

	public void addFavorite_tags(Favorite_tags favorite_tags);
	
	public Favorite_tags getFavorite_tags(String id);
	
	public Favorite_tags getFavorite_tagsByName(Integer userId,String tagName);
	
	public void delFavorite_tag(Integer favoriteId);
	
	public void delFavorite_tag(Integer userId,String tagName);
	
	public void modify(Integer userId,String tagName,String toTagName);
	
	public Pager<Favorite_tags> getFavorite_tagsPagert(Integer userId);
}
