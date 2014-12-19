package org.cgz.oseye.service;

import org.cgz.oseye.common.Pager;
import org.cgz.oseye.model.Favorites;

public interface FavoritesService {

	public void addFavorites(Favorites favorites);
	
	public void delFavorites(Integer id);
	
	public void modifyFavorites(Favorites favorites);
	
	public void modifyFavoritesIgnoreTags(Favorites favorites);
	
	public Favorites getFavorites(Integer id);
	
	public Favorites loadFavorites(Integer id);
	
	public Pager<Favorites> getFavoritesPager(Integer userId,Integer pageNo,Integer pageSize);
	
	public Pager<Favorites> getFavoritesPagerByTag(Integer userId,String tag,Integer pageNo,Integer pageSize);
	
	public boolean checkUrlIsExist(Integer userId,String url);
}
